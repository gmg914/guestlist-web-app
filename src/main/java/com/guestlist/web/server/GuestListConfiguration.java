package com.guestlist.web.server;

import io.dropwizard.Configuration;

//TODO: Make this actually useful
public class GuestListConfiguration extends Configuration {
    
    public EsConfiguration getEsConfiguration() {
    	return new EsConfiguration();
    }
}
