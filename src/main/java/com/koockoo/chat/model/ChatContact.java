package com.koockoo.chat.model;

import javax.persistence.Id;

public class ChatContact {
	
    @Id
    protected String id;    
	protected String displayName;	
	protected String topicId;
	
	public String getTopicId() {
        return topicId;
    }
    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
    public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
				
}
