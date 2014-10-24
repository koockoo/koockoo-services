package com.koockoo.chat.model.db;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Index;

@Entity
@Table(name="account", indexes = {
	    @Index(name="account_owner_email_idx", columnList="ownerEmail" )
	})
public class Account {
	
	@Id
	private String id = UUID.randomUUID().toString();
	
	private String ownerRef;
	private String ownerEmail;
	private String topicRef;
	private int ttl = 300;
	
	@Transient
	private Operator owner;
	
	public Operator getOwner() {
		return owner;
	}
	public void setOwner(Operator owner) {
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
    public String getTopicRef() {
        return topicRef;
    }
    public void setTopicRef(String topicRef) {
        this.topicRef = topicRef;
    }
    public String getOwnerEmail() {
        return ownerEmail;
    }
    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
    public int getTtl() {
        return ttl;
    }
    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
	
}
