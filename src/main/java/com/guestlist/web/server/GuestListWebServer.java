package com.guestlist.web.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import io.dropwizard.assets.AssetsBundle;

public class GuestListWebServer extends Application<GuestListConfiguration>{

	public static void main(String[] args) throws Exception {
		new GuestListWebServer().run(args);
	}
	
    @Override
    public String getName() {
        return "guestlist-web-server";
    } 
	
	@Override
	public void initialize(final Bootstrap<GuestListConfiguration> bootstrap) {
		//TODO: figure out why this didn't work before
		AssetsBundle bundle = new AssetsBundle("/html");
		bootstrap.addBundle(bundle);
	}
	
	@Override
	public void run(final GuestListConfiguration configuration, final Environment environment) throws Exception {
		final ManagedEsClient managedClient = new ManagedEsClient(configuration.getEsConfiguration());
		environment.jersey().register(new GuestListResource(managedClient.getClient()));
	}
	
}
