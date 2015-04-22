package com.guestlist.web.server;

import java.util.ArrayList;
import java.util.Collection;

public class Event extends AbstractEvent {
	private Collection<Event> subEvents;

	public Event() {
        // Jackson deserialization
    }
	
	public Event(String eventName) {
		this.eventName = eventName;
		this.eventKey = eventName.toUpperCase();
		this.subEvents = new ArrayList<Event>();
	}
		
	public void populateEventKey() {
		this.eventKey = eventName.toUpperCase();
	}
	
	public void addSubEvent(Event event) {
		subEvents.add(event);
	}
	
	public Collection<Event> getSubEvents() {
		return subEvents;
		
	}
}
