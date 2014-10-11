package com.koockoo.chat.model.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Version;

import com.datastax.driver.mapping.annotation.Ttl;

@Table(name="chatroom", indexes = {
        @Index(name="chatroom_topic_idx", columnList="topicRef" ),
        @Index(name="chatroom_state_idx", columnList="state" )
})
@Ttl(3600*24) // 24 hrs
public class ChatRoom {
    public static enum States {PENDING, ACTIVE, CLOSED}
    
    @Id
    private String id = UUID.randomUUID().toString();
    
    @Version
    private long version;
    
    private String topicRef;    
    private Date startDate;
    private Date endDate;
    private String guestRef;
    private List<String> operatorRefs;
    private States state = States.PENDING;
    
    public void addOperatorRef(String operatorRef) {
        if (operatorRefs == null) {
            operatorRefs = new ArrayList<>();
        }
        operatorRefs.add(operatorRef);
    }
    
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
    public String getGuestRef() {
        return guestRef;
    }
    public void setGuestRef(String guestRef) {
        this.guestRef = guestRef;
    }
    public States getState() {
        return state;
    }
    public void setState(States state) {
        this.state = state;
    }
    public String getTopicRef() {
        return topicRef;
    }
    public void setTopicRef(String topicRef) {
        this.topicRef = topicRef;
    }
    public List<String> getOperatorRefs() {
        return operatorRefs;
    }
    public void setOperatorRefs(List<String> operatorRefs) {
        this.operatorRefs = operatorRefs;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
