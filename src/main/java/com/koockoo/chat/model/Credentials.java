package com.koockoo.chat.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Credentials")
public class Credentials {
	
	@Id
	private String login;
	private String password;
	private String operatorRef;
	
	@Transient
	private ChatOperator chatOperator;
	
	public Credentials() {}
	
	public Credentials(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}
	
	public boolean matches(String login, String password) {
		if (this.login == null || this.password == null) {
			return false;
		}
		return this.login.equals(login) && this.password.equals(password);
	}
		
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public void setChatOperator(ChatOperator chatContact) {
		this.chatOperator = chatContact;
	}

	public String getOperatorRef() {
		return operatorRef;
	}
	public void setOperatorRef(String operatorRef) {
		this.operatorRef = operatorRef;
	}	
	
	public ChatOperator getChatOperator() {
		return chatOperator;
	}	

}
