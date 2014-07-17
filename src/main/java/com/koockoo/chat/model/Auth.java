package com.koockoo.chat.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.datastax.driver.mapping.annotation.Ttl;

@Entity
@Table(name="Auth")
@Ttl(300) /* expires after 5 mins of inactivity */
public class Auth {
	@Id
	private String token = UUID.randomUUID().toString();
	private String credentialsRef;
	
	@Transient
	private Credentials credentials;

	public String getToken() {
		return token;
	}

	public String getCredentialsRef() {
		return credentialsRef;
	}


	public Credentials getCredentials() {
		return credentials;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setCredentialsRef(String credentialsRef) {
		this.credentialsRef = credentialsRef;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
}
