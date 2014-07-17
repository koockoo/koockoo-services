package com.koockoo.chat.model;

import java.util.List;

public class ChatGroup {
	private List<ChatGroup> groups; 
	private List<ChatOperator> operators;
	
	public List<ChatGroup> getGroups() {
		return groups;
	}
	public void setGroups(List<ChatGroup> groups) {
		this.groups = groups;
	}
	public List<ChatOperator> getOperators() {
		return operators;
	}
	public void setOperators(List<ChatOperator> operators) {
		this.operators = operators;
	}
}
