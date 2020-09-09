package com.training.restfil.interact.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PostEvent {
	
	private String sessionId;
	private String eventName;
	private String treatmentCode;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEvent(String eventName) {
		this.eventName = eventName;
	}
	public String getTreatmentCode() {
		return treatmentCode;
	}
	public void setTreatmentCode(String treatmentCode) {
		this.treatmentCode = treatmentCode;
	}
	
}
