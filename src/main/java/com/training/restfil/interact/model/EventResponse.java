package com.training.restfil.interact.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component
@XmlRootElement
public class EventResponse {
	
	int peStatus;

	public int getPeStatus() {
		return peStatus;
	}
	public void setPeStatus(int peStatus) {
		this.peStatus = peStatus;
	}


}
