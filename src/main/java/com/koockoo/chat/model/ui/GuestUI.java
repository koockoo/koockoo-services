package com.koockoo.chat.model.ui;

public class GuestUI {
    private String id;
    private String displayName;
    private String location;
    private String env;
    private String question;
    private String connectionDate;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getConnectionDate() {
        return connectionDate;
    }
    public void setConnectionDate(String connectionDate) {
        this.connectionDate = connectionDate;
    }

}
