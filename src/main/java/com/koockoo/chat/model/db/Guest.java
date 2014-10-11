package com.koockoo.chat.model.db;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "guest")
public class Guest {

    @Id
    private String id = UUID.randomUUID().toString();
    private String displayName;
    private String topicRef;
    private String location;
    private String env;
    
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTopicRef() {
        return topicRef;
    }

    public void setTopicRef(String topicRef) {
        this.topicRef = topicRef;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
