package com.training.restfil.interact.util;

import java.rmi.RemoteException;

import org.springframework.stereotype.Component;

import com.training.restfil.interact.model.Member;
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

	public Command createPostEventCommand(String eventName){
		CommandImpl cmd = new CommandImpl();
		cmd.setMethodIdentifier(Command.COMMAND_POSTEVENT);
		cmd.setEvent(eventName);
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
