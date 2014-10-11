package com.koockoo.chat.model.db;

import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Table(name = "operator", indexes = {
        @Index(name="operator_email_idx", columnList="email" )
})
public class Operator {

    @Id
    private String id = UUID.randomUUID().toString();
    private String displayName;
    private String email;
    private String topicRef;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
}
