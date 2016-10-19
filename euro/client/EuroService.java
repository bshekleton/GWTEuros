package com.bryan.euro.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface EuroService extends RemoteService {
	public String getFeed(String feed);
}
