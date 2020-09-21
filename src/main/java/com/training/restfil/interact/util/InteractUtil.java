package com.training.restfil.interact.util;

import java.rmi.RemoteException;

import org.springframework.stereotype.Component;

import com.training.restfil.interact.model.Member;
import com.training.restfil.interact.model.PostEvent;
import com.unicacorp.interact.api.Command;
import com.unicacorp.interact.api.CommandImpl;
import com.unicacorp.interact.api.NameValuePair;
import com.unicacorp.interact.api.NameValuePairImpl;

@Component("interactUtil")
public class InteractUtil {
	
	String test;
	
	public Command createStartSessionCommand(String icName, Member member)  throws RemoteException{
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_STARTSESSION);
		cmd.setInteractiveChannel(icName);
		cmd.setAudienceLevel("MDM_Person");
		cmd.setAudienceID(new NameValuePairImpl[] {
				new NameValuePairImpl("MDM_PERSON_ID", NameValuePair.DATA_TYPE_STRING, member.getMdm_person_id()) });

		NameValuePair userId = new NameValuePairImpl();
		userId.setName("user_id");
		userId.setValueAsString(member.getUser_Id());
		userId.setValueDataType(NameValuePair.DATA_TYPE_STRING);

		// NameValuePair userId = new NameValuePairImpl("user_id","string","hkolukul");

		NameValuePair policyId = new NameValuePairImpl();
		policyId.setName("policy_id");
		policyId.setValueAsString(member.getPolicy_id());
		policyId.setValueDataType(NameValuePair.DATA_TYPE_STRING);

		NameValuePairImpl[] eventParameters = { (NameValuePairImpl) userId, (NameValuePairImpl) policyId };

		cmd.setEventParameters(eventParameters);
		return cmd;
	}

	public Command createGetOffersCommand(String ipName, int numberRequested) {
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_GETOFFERS);
		cmd.setInteractionPoint(ipName);
		cmd.setNumberRequested(numberRequested);
		return cmd;
	}

	public Command createPostEventCommand(PostEvent postEvent){
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_POSTEVENT);
		cmd.setEvent(postEvent.getEventName());
		
	
		NameValuePair treatmentCode = new NameValuePairImpl();
		treatmentCode.setName("UACIOfferTrackingCode");
		treatmentCode.setValueAsString(postEvent.getTreatmentCode());
		treatmentCode.setValueDataType(NameValuePair.DATA_TYPE_STRING);
		
		NameValuePairImpl[] eventParameters =  { (NameValuePairImpl) treatmentCode};		
		cmd.setEventParameters(eventParameters);		

		return cmd;
	}

	
	public Command createResponseEventCommand(PostEvent postEvent){
			
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_POSTEVENT);
		cmd.setEvent(postEvent.getEventName());
		
		NameValuePair treatmentCode = new NameValuePairImpl();
		treatmentCode.setName("UACIOfferTrackingCode");
		treatmentCode.setValueAsString(postEvent.getTreatmentCode());
		treatmentCode.setValueDataType(NameValuePair.DATA_TYPE_STRING);
		
		NameValuePair eventName = new NameValuePairImpl();
		eventName.setName("eventName");
		eventName.setValueAsString(postEvent.getEventName());
		eventName.setValueDataType(NameValuePair.DATA_TYPE_STRING);
		
		NameValuePairImpl[] eventParameters =  { (NameValuePairImpl) treatmentCode,(NameValuePairImpl) eventName};		
		cmd.setEventParameters(eventParameters);
		
		if(postEvent.getEventName().equals("reject")) {
		NameValuePair responseTypeEXP = new NameValuePairImpl();
		responseTypeEXP.setName("UACIResponseTypeCode");
		responseTypeEXP.setValueAsString("RCT");
		responseTypeEXP.setValueDataType(NameValuePair.DATA_TYPE_STRING);
		
		NameValuePairImpl[] eventParameters2  = {(NameValuePairImpl) treatmentCode,(NameValuePairImpl) eventName, (NameValuePairImpl) responseTypeEXP};
		cmd.setEventParameters(eventParameters2);
		}

		
		return cmd;
	}
	
	
	public Command createGetProfileCommand() {
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_GETPROFILE);
		return cmd;
	}


	public Command createEndSessionCommand() {
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_ENDSESSION);
		return cmd;
	}


}
