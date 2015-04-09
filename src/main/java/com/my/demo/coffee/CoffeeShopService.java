package com.my.demo.coffee;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.Configuration;

import io.dropwizard.assets.AssetsBundle;

public class CoffeeShopService extends Application<Configuration>{

	public static void main(String[] args) throws Exception {
		new CoffeeShopService().run(args);
	}
	
    @Override
    public String getName() {
        return "coffeeshop";
    } 
	
	@Override
	public void initialize(final Bootstrap<Configuration> bootstrap) {
		//TODO: figure out why this didn't work before
		AssetsBundle bundle = new AssetsBundle("/html");
		bootstrap.addBundle(bundle);
	}
	
	@Override
	public void run(final Configuration configuration, final Environment environment) throws Exception {
		environment.jersey().register(new CoffeeShopResource());
	}
	
}
