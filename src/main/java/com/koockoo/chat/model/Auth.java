package com.koockoo.chat.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.datastax.driver.mapping.annotation.Ttl;

@Entity
@Table(name="auth")
@Ttl(300) /* by default expires after 5 minutes of inactivity */
public class Auth {
	
	@Id
	private String id = UUID.randomUUID().toString();
	private String operatorRef;
	private String guestRef;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperatorRef() {
		return operatorRef;
	}

	public void setOperatorRef(String operatorRef) {
		this.operatorRef = operatorRef;
	}

	public String getGuestRef() {
		return guestRef;
	}

	public void setGuestRef(String guestRef) {
		this.guestRef = guestRef;
	}
}
