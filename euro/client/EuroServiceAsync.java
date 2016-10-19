package com.bryan.euro.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EuroServiceAsync {

	void getFeed(String feed, AsyncCallback<String> callback);

}
