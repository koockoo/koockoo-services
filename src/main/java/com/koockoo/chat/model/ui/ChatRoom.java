package com.koockoo.chat.model.ui;

import java.util.Date;
import java.util.List;

public class ChatRoom {
    private String id;
    private Date startDate;
    private Date endDate;
    private Guest guest;
    private List<Operator> operators;
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
    public Guest getGuest() {
        return guest;
    }
    public void setGuest(Guest guest) {
        this.guest = guest;
    }
    public List<Operator> getOperators() {
        return operators;
    }
    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }
}
