package com.koockoo.chat.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents conversation between contact and operator(s)
 * */
@XmlRootElement
public class ChatSession implements Comparable<ChatSession> {
	private String id = UUID.randomUUID().toString();
	private String initiatorId;
	private String displayName;
	private transient List<ChatContact> contacts = new ArrayList<ChatContact>();
	
	
	/** add subscriber for current chat session */
	public void addContact(ChatContact contact){
		contacts.add(contact);
	}

	/** remove subscriber for current chat session */
	public void removeContact(ChatContact contact){
		contacts.remove(contact);
	}	
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public void setInitiator(ChatContact initiator) {
		setInitiatorId(initiator.getId());
		setDisplayName(initiator.getDisplayName());
		addContact(initiator);
	}
	public List<ChatContact> getContacts() {
		return contacts;
	}
	public void setContacts(List<ChatContact> contacts) {
		this.contacts = contacts;
	}

	public String getInitiatorId() {
		return initiatorId;
	}

	public void setInitiatorId(String initiatorId) {
		this.initiatorId = initiatorId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatSession other = (ChatSession) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(ChatSession o) {
		return 0;
	}
	
}
