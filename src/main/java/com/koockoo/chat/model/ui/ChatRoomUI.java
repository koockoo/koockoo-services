package com.koockoo.chat.model.ui;

import java.util.Date;
import java.util.List;

public class ChatRoomUI {
    private String id;
    private Date startDate;
    private Date endDate;
    private int waitTime;
    private GuestUI guest;
    private String state;
    private List<OperatorUI> operators;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public GuestUI getGuest() {
        return guest;
    }
    public void setGuest(GuestUI guest) {
        this.guest = guest;
    }
    public List<OperatorUI> getOperators() {
        return operators;
    }
    public void setOperators(List<OperatorUI> operators) {
        this.operators = operators;
    }
    public int getWaitTime() {
        return waitTime;
    }
    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
