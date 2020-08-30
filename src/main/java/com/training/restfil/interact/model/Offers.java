package com.training.restfil.interact.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

//import org.springframework.stereotype.Component;

//@Component
@XmlRootElement
public class Offers {
	
	String offer_name;
	int score;
	String treatmentCode;
	String effectiveDate;
	String expirationDate;
	


	public Offers(String offer_name, int score, String treatmentCode, String effectiveDate, String expirationDate) {
		super();
		this.offer_name = offer_name;
		this.score = score;
		this.treatmentCode = treatmentCode;
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getOffer_name() {
		return offer_name;
	}
	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getTreatmentCode() {
		return treatmentCode;
	}
	public void setTreatmentCode(String treatmentCode) {
		this.treatmentCode = treatmentCode;
	}

}
