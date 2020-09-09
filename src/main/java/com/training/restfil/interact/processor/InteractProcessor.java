package com.training.restfil.interact.processor;

import java.rmi.RemoteException;

import org.springframework.stereotype.Component;

import com.unicacorp.interact.api.BatchResponse;
import com.unicacorp.interact.api.Command;
import com.unicacorp.interact.api.rest.RestClientConnector;

@Component
public class InteractProcessor {
	
	public BatchResponse executeBatchMethod (String sessionId,Command[] cmd, RestClientConnector connector) throws RemoteException {
		
		BatchResponse batchResponse = connector.executeBatch(sessionId, cmd, null, null);;
		
		return batchResponse;
		
	}

}
