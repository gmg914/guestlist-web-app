package com.guestlist.web.server;

public class SubEvent extends AbstractEvent {
	private String parentEvent;
	
	public SubEvent() {
        // Jackson deserialization
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
		eventKey = parentEvent + "_" + eventName.toUpperCase();
	}
}
