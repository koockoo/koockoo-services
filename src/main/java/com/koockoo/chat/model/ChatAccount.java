package com.koockoo.chat.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Index;

@Entity
@Table(name="account", indexes = {
	    @Index(name="account_owner_idx", columnList="ownerRef" )
	})
public class ChatAccount {
	
	@Id
	private String id = UUID.randomUUID().toString();
	
	private String ownerRef;
	
	@Transient
	private ChatOperator owner;
	
	public ChatOperator getOwner() {
		return owner;
	}
	public void setOwner(ChatOperator owner) {
		this.owner = owner;
	}
	public String getOwnerRef() {
		return ownerRef;
	}
	public void setOwnerRef(String ownerRef) {
		this.ownerRef = ownerRef;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
