package com.training.restfil.interact.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Member {
	
	String mdm_person_id;
	String user_Id;
	String policy_id;
	
	public String getMdm_person_id() {
		return mdm_person_id;
	}
	public void setMdm_person_id(String mdm_person_id) {
		this.mdm_person_id = mdm_person_id;
	}
	public String getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}
	public String getPolicy_id() {
		return policy_id;
	}
	public void setPolicy_id(String policy_id) {
		this.policy_id = policy_id;
	}	

}
