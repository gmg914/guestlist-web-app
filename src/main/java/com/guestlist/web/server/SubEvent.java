package com.guestlist.web.server;

import java.util.ArrayList;
import java.util.HashSet;

public class SubEvent extends AbstractEvent {
	private String parentEvent;
	
	public SubEvent() {
        // Jackson deserialization
		//this.invitedGuests = new HashSet<Guest>();
    }
	
//	public SubEvent(String eventName) {
//		this.eventName = eventName;
//		this.eventKey = eventName.toUpperCase();
//		this.subEvents = new ArrayList<SubEvent>();
//	}
		
	public void setParentEvent(String parentEvent) {
		this.parentEvent = parentEvent;
	}
	
	public String getParentEvent() {
		return parentEvent;
	}
	
	public void populateEventKey() {
		eventKey = parentEvent + "_" + eventName.toUpperCase().replaceAll("\\s+","");
	}
}
