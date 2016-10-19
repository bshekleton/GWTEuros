package com.bryan.euro.client.parsing;

import java.util.HashMap;

import com.bryan.euro.client.Euros;
import com.bryan.euro.client.object.CountryObj;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.xml.client.Element;

public class JsonParsingTeams
{
	private Element counrtyCodes;
	private HashMap<String, CountryObj> countries;
	private Euros main;

	public JsonParsingTeams(Element counrtyCode, Euros maino)
	{
		main = maino;
		countries = main.getCountries();
		counrtyCodes = counrtyCode;
		String url = "http://api.football-data.org/v1/soccerseasons/424/teams";

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		builder.setHeader("X-Auth-Token", "8a16830d996e4b34a77315abb47c4266");
		try {
			builder.sendRequest("", new RequestCallback()
			{
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 0)
						offlineMode();
					else
						parseTeams(response.getText());
				}

				@Override
				public void onError(Request request, Throwable exception) {

				}

			});
		} catch (RequestException e) {

			e.printStackTrace();
		} catch (Exception e) {

		}
	}

	private void offlineMode() {
		String url = "teams.json";

		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			builder.sendRequest("", new RequestCallback()
			{
				@Override
				public void onResponseReceived(Request request, Response response) {
					parseTeams(response.getText());
				}

				@Override
				public void onError(Request request, Throwable exception) {
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	protected void parseTeams(String text) {
		JSONValue jsonValue = JSONParser.parseStrict(text);
		JSONObject allinfo = jsonValue.isObject();

		double noTeams = allinfo.get("count").isNumber().doubleValue();
		JSONArray teamArray = allinfo.get("teams").isArray();

		for (int i = 0; i < noTeams; i++) {
			String countryname = teamArray.get(i).isObject().get("name").isString().stringValue();
			String countrynameId = countryname.replaceAll("\\s+", "");
			String flagurl = flagsUrl(countryname);
			CountryObj c = new CountryObj(countryname, countrynameId);
			c.setUrlflag(flagurl);
			countries.put(countrynameId, c);
		}

		new JsonParsingFixtures(main);

	}

	private String flagsUrl(String countryname) {
		String cc = countryname.replaceAll("\\s+", "");
		String flagurl = "http://pngimg.com/upload/football_PNG1082.png";
		String code = counrtyCodes.getElementsByTagName(cc).item(0).getChildNodes().item(0).getFirstChild().getNodeValue();

		if (code.length() > 3) {
			flagurl = code;
		} else {
			code = code.toLowerCase();
			flagurl = "http://www.geonames.org/flags/x/" + code + ".gif";
		}
		return flagurl;
	}

}
