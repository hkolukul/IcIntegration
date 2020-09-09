package com.training.restfil.interact.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component
@XmlRootElement
public class EventResponse {
	
	int peStatus;
	int endStatus;
	public int getPeStatus() {
		return peStatus;
	}
	public void setPeStatus(int peStatus) {
		this.peStatus = peStatus;
	}
	public int getEndStatus() {
		return endStatus;
	}
	public void setEndStatus(int endStatus) {
		this.endStatus = endStatus;
	}
	

}
