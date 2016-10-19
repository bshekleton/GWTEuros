package com.bryan.euro.client.parsing;

import java.util.HashMap;

import com.bryan.euro.client.Euros;
import com.bryan.euro.client.match.MatchWindow;
import com.bryan.euro.client.object.CountryObj;
import com.bryan.euro.client.object.MatchObj;
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

public class JsonParsingFixtures
{
	private MatchWindow matchWindow;
	private Element countryCodes;
	private HashMap<String, CountryObj> countries;
	public HashMap<String, MatchObj> matches;
	private Euros main;

	public JsonParsingFixtures(Euros main1)
	{
		this.main = main1;
		matches = main1.getMatches();
		countryCodes = main1.getXmlCountriesCodes().getCountryCodes();
		countries = main1.getCountries();
		readinFixtures();
	}

	public void readinFixtures() {
		String fix = "http://api.football-data.org/v1/soccerseasons/424/fixtures";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, fix);
		builder.setHeader("X-Auth-Token", "8a16830d996e4b34a77315abb47c4266");
		try {
			builder.sendRequest("", new RequestCallback()
			{
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 0)
						offlineMode();
					else
						parseFixtures(response.getText());
				}

				@Override
				public void onError(Request request, Throwable exception) {
					offlineMode();
				}
			});
		} catch (RequestException e) { // TODD Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void offlineMode() {
		String fix = "fixtures.json";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, fix);
		try {
			builder.sendRequest("", new RequestCallback()
			{
				@Override
				public void onResponseReceived(Request request, Response response) {
					parseFixtures(response.getText());
				}

				@Override
				public void onError(Request request, Throwable exception) {
				}
			});
		} catch (RequestException e) { // TODD Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void parseFixtures(String text) {

		JSONValue jsonValue = JSONParser.parseStrict(text);
		JSONObject allinfo = jsonValue.isObject();
		Double noOfixtures = allinfo.get("count").isNumber().doubleValue();
		JSONArray fixturesArray = allinfo.get("fixtures").isArray();

		for (int i = 0; i < noOfixtures; i++) {
			JSONObject matchData = fixturesArray.get(i).isObject();
			double round = matchData.get("matchday").isNumber().doubleValue();

			// get the date
			String matchDate = matchData.get("date").isString().stringValue();
			String homeTeam = "";
			String awayTeam = "";
			CountryObj homeTeamObj = null;
			CountryObj awayTeamObj = null;

			try {
				homeTeam = matchData.get("homeTeamName").isString().stringValue();
				homeTeam = homeTeam.replaceAll("\\s+", "");
				homeTeamObj = countries.get(homeTeam);

				awayTeam = matchData.get("awayTeamName").isString().stringValue();
				awayTeam = awayTeam.replaceAll("\\s+", "");
				awayTeamObj = countries.get(awayTeam);

			} catch (Exception e) {

			}
			String key = homeTeam + "v" + awayTeam;
			MatchObj value = new MatchObj(homeTeamObj, awayTeamObj, matchDate, 'A', round);
			matches.put(key, value);

		}
		main.updateodds();
	}
}
