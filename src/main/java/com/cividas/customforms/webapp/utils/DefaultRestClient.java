package com.cividas.customforms.webapp.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class DefaultRestClient {

	private static Client INSTANCE = null;
	
	private DefaultRestClient(){
		ClientConfig config = new DefaultClientConfig();
		config.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		INSTANCE = Client.create(config);
	}
	
	
	public synchronized static Client getInstance() {
		if (INSTANCE == null){
			new DefaultRestClient();
		}
		return INSTANCE;
		
		
	}
}
