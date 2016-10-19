package com.bryan.euro.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.bryan.euro.client.EuroService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EuroServiceImpl extends RemoteServiceServlet implements EuroService
{

	@Override
	public String getFeed(String feed) {
		String url = feed;// "http://www.uefa.com/rssfeed/uefaeuro/rss.xml";
		String output = "";
		try {
			String actURL = url.replace("http", "http");
			URL aUrl = new URL(actURL);
			URLConnection conn = aUrl.openConnection();
			conn.connect();
			InputStream in = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				output += line;
			}
			return output;
		} catch (Exception e) {

		}
		return null;
	}
}
