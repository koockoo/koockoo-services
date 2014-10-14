package com.koockoo.chat.model.ui;

public class AuthUI {
    private String id;
    private OperatorUI operator;
    private GuestUI guest;
    private String topicRef;
    
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

    public OperatorUI getOperator() {
        return operator;
    }

    public void setOperator(OperatorUI operator) {
        this.operator = operator;
    }

    public GuestUI getGuest() {
        return guest;
    }

    public void setGuest(GuestUI guest) {
        this.guest = guest;
    }

}
