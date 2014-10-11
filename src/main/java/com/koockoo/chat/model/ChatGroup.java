package com.koockoo.chat.model;

import java.util.List;

import com.koockoo.chat.model.db.Operator;

public class ChatGroup {
	private List<ChatGroup> groups; 
	private List<Operator> operators;
	
	public List<ChatGroup> getGroups() {
		return groups;
	}
	public void setGroups(List<ChatGroup> groups) {
		this.groups = groups;
	}
	public List<Operator> getOperators() {
		return operators;
	}
	public void setOperators(List<Operator> operators) {
		this.operators = operators;
	}
}
