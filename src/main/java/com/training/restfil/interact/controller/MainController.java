package com.training.restfil.interact.controller;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.training.restfil.interact.constants.ChannelConstants;
import com.training.restfil.interact.model.EventResponse;
import com.training.restfil.interact.model.Member;
import com.training.restfil.interact.model.Offers;
import com.training.restfil.interact.model.PostEvent;
import com.training.restfil.interact.model.RestResponse;
import com.training.restfil.interact.processor.InteractProcessor;
import com.training.restfil.interact.util.InteractUtil;


import com.unicacorp.interact.api.AdvisoryMessage;
import com.unicacorp.interact.api.BatchResponse;
import com.unicacorp.interact.api.Command;
import com.unicacorp.interact.api.NameValuePair;
import com.unicacorp.interact.api.Offer;
import com.unicacorp.interact.api.OfferList;
import com.unicacorp.interact.api.Response;
import com.unicacorp.interact.api.rest.RestClientConnector;

@Path("/offers")
public class MainController implements ChannelConstants {

	@PostConstruct
	public void init() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Autowired
	@Qualifier("interactUtil")
	InteractUtil interactUtil;
	
	@Autowired
	InteractProcessor interactProcessor;
	
	@Autowired
	RestResponse restResponse;
	
	@Autowired
	EventResponse eventResponse;
	
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public RestResponse getOffers(Member member) throws IOException, JSONException{
		
		String sessionId = String.valueOf(System.currentTimeMillis());

//		System.out.println("inside getoffer method");
		List<Command> cmds = new ArrayList<Command>();
		Date EffectiveDate = new Date();
		Date ExpirationDate = new Date();
		
		cmds.add(0, interactUtil.createStartSessionCommand(icName,member));
		cmds.add(1, interactUtil.createGetOffersCommand(ipName, numberRequested));
		cmds.add(2, interactUtil.createGetProfileCommand());
		cmds.add(3, interactUtil.createEndSessionCommand());
		cmds.add(4, interactUtil.createPostEventCommand(eventName));
		

		try {

			RestClientConnector.initialize();
			RestClientConnector connector = new RestClientConnector(url);

			//Start session
			Command[] cmd = { cmds.get(0) };
			BatchResponse batchResponse = interactProcessor.executeBatchMethod(sessionId,cmd,connector);
			
//			System.out.println("Start session " + start_response.getBatchStatusCode());
			Response[] responses = batchResponse.getResponses();

			
			//Handle errors
			if (batchResponse.getBatchStatusCode() > 0) {
				for (Response res : responses) {
					AdvisoryMessage[] ams = res.getAdvisoryMessages();
					for (AdvisoryMessage am : ams) {
						System.out.println(am.getMessage());
					}
				}
			}

			//Start session, PostEvent
			Command[] cmd1 = { cmds.get(1), cmds.get(4) };
			BatchResponse response = connector.executeBatch(sessionId, cmd1, null, null);
			System.out.println("Execute batch " + response.getBatchStatusCode());
			responses = response.getResponses(); // load response into responses[] array
			
			List<Offers> offersList = new ArrayList<Offers>();

			//process each response
			for (Response res : responses) {
				OfferList oflist = res.getOfferList();
				if (oflist != null) {
					for (int i = 0; i < oflist.getRecommendedOffers().length; i++) {
						if (oflist.getRecommendedOffers()[i] != null) {
							System.out.println("value is i " + i);
							Offer ofs = oflist.getRecommendedOffers()[i];
							
							
//							System.out.println(ofs.getOfferName());
//							System.out.println(ofs.getTreatmentCode());
//							System.out.println("score " + ofs.getScore());
							
							NameValuePair[] attributes = ofs.getAdditionalAttributes();
							for (NameValuePair attribute : attributes) {
								if (attribute.getValueDataType().equalsIgnoreCase("datetime"))
									System.out.println(attribute.getName() + " " + attribute.getValueAsDate());
								if (attribute.getValueDataType().equalsIgnoreCase("numeric"))
									System.out.println(attribute.getName() + " " + attribute.getValueAsNumeric());
								if (attribute.getValueDataType().equalsIgnoreCase("string"))
									System.out.println(attribute.getName() + " " + attribute.getValueAsString());
								if (attribute.getName().equalsIgnoreCase("EffectiveDate"))
									EffectiveDate=attribute.getValueAsDate();
								if (attribute.getName().equalsIgnoreCase("ExpirationDate"))
									ExpirationDate=attribute.getValueAsDate();								
							}
//							System.out.println("----------------------------------------------");
							offersList.add(new Offers(ofs.getOfferName(),ofs.getScore(),ofs.getTreatmentCode(),EffectiveDate.toString(),ExpirationDate.toString()));
						}
					}
				}
			}

			Command[] cmd_profile = { cmds.get(2) };
			response = connector.executeBatch(sessionId, cmd_profile, null, null);
			System.out.println("Profile " + response.getBatchStatusCode());
			responses = response.getResponses();
			for (Response res : responses) {
				NameValuePair[] npvs = res.getProfileRecord();
				for (NameValuePair attribute : npvs) {
//					if (attribute.getValueDataType().equalsIgnoreCase("datetime"))
//						System.out.println(attribute.getName() + " " + attribute.getValueAsDate());
//					if (attribute.getValueDataType().equalsIgnoreCase("numeric"))
//						System.out.println(attribute.getName() + " " + attribute.getValueAsNumeric());
//					if (attribute.getValueDataType().equalsIgnoreCase("string"))
//						System.out.println(attribute.getName() + " " + attribute.getValueAsString());
					if (attribute.getName().equalsIgnoreCase("mdm_person_id"))
						restResponse.setMdm_person_id(attribute.getValueAsString());
					if (attribute.getName().equalsIgnoreCase("user_id"))
						restResponse.setUser_id(attribute.getValueAsString());
					if (attribute.getName().equalsIgnoreCase("policy_id"))
						restResponse.setPolicy_id(attribute.getValueAsString());
					if (attribute.getName().equalsIgnoreCase("city"))
						restResponse.setCity(attribute.getValueAsString());
					if (attribute.getName().equalsIgnoreCase("firstname"))
						restResponse.setFirstname(attribute.getValueAsString());
					if (attribute.getName().equalsIgnoreCase("lastname"))
						restResponse.setLastname(attribute.getValueAsString());
				}
				
				restResponse.setOffers(offersList);
				restResponse.setSession_id(sessionId);

			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return restResponse;
	}
	
	
	
	
	@Path("/response")
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})	
	public EventResponse postEvent(PostEvent postEvent) throws IOException, JSONException{
		
		List<Command> cmds = new ArrayList<Command>();
		
		RestClientConnector connector = new RestClientConnector(url);
		
		cmds.add(0, interactUtil.createEndSessionCommand());
		cmds.add(1, interactUtil.createPostEventCommand2(postEvent));
		
		Command[] cmd_pe = { cmds.get(1) };
		BatchResponse batchResponse = interactProcessor.executeBatchMethod(postEvent.getSessionId(),cmd_pe,connector);
		System.out.println("Post event status " + batchResponse.getBatchStatusCode());
		int peStatus= batchResponse.getBatchStatusCode();
		
		
		Command[] cmd_end = { cmds.get(0) };
		batchResponse = interactProcessor.executeBatchMethod(postEvent.getSessionId(),cmd_end,connector);
		System.out.println("End " + batchResponse.getBatchStatusCode());
		int endStatus= batchResponse.getBatchStatusCode();
		
		eventResponse.setPeStatus(peStatus);
		eventResponse.setEndStatus(endStatus);
				
		return eventResponse;	
	}

}
