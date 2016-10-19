package com.bryan.euro.client.parsing;

import java.util.HashMap;

import com.bryan.euro.client.EuroServiceAsync;
import com.bryan.euro.client.Euros;
import com.bryan.euro.client.object.CountryObj;
import com.bryan.euro.client.object.MatchObj;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class XmlParsingFile
{
	private EuroServiceAsync euroXml;
	private HashMap<String, CountryObj> countries;
	private HashMap<String, MatchObj> matchesMap;
	private Euros main;

	public XmlParsingFile(EuroServiceAsync euroXm, HashMap<String, CountryObj> countrie,
			HashMap<String, MatchObj> matchesMap1, Euros main1)
	{
		this.countries = countrie;
		this.euroXml = euroXm;
		this.matchesMap = matchesMap1;
		this.main = main1;

		euroXml.getFeed("http://xml.betfred.com/Football-EuroChamps.xml", new AsyncCallback<String>()
		{
			@Override
			public void onSuccess(String res) {
				if (res != null)
					parseXMLbet(res);
				else
					offlineMode();
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("error " + caught.toString());

			}
		});

	}

	private void offlineMode() {

		String fix = "bets.xml";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, fix);
		try {
			builder.sendRequest("", new RequestCallback()
			{
				@Override
				public void onResponseReceived(Request request, Response response) {
					parseXMLbet(response.getText());
				}

				@Override
				public void onError(Request request, Throwable exception) {
				}
			});
		} catch (RequestException e) { // TODD Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void parseXMLbet(String xml) {
		String text = XMLParser.parse(xml).getElementsByTagName("event").item(0).toString();
		NodeList betType = XMLParser.parse(text).getElementsByTagName("bettype");
		String outright = betType.item(0).toString();
		String reachfinal = betType.item(3).toString();
		String reachsemi = betType.item(4).toString();

		Document outrights = XMLParser.parse(outright);
		Document reachfinals = XMLParser.parse(reachfinal);
		Document reachsemis = XMLParser.parse(reachsemi);

		for (int i = 0; i < 24; i++) {
			try {
				// odds for country to win the euros
				NamedNodeMap atributes = outrights.getElementsByTagName("bet").item(i).getAttributes();
				String countryName = atributes.item(0).toString();
				String oddsS = atributes.item(3).toString();
				Double oddsD = Double.parseDouble(atributes.item(4).toString());
				addBetOutright(countryName, oddsS, oddsD);

				// odds for country to reach the finals
				atributes = reachfinals.getElementsByTagName("bet").item(i).getAttributes();
				countryName = atributes.item(0).toString();
				oddsS = atributes.item(3).toString();
				oddsD = Double.parseDouble(atributes.item(4).toString());
				addBetFinal(countryName, oddsS, oddsD);

				// odds for country to reach the semi finals
				atributes = reachsemis.getElementsByTagName("bet").item(i).getAttributes();
				countryName = atributes.item(0).toString();
				oddsS = atributes.item(3).toString();
				oddsD = Double.parseDouble(atributes.item(4).toString());
				addBetSemi(countryName, oddsS, oddsD);
			} catch (Exception e) {

			}
		}

		parseGroupsBets(xml);
		parseMatchBets(xml);
		main.onReady();
	}

	private void parseMatchBets(String xml) {
		NodeList matches = XMLParser.parse(xml).getElementsByTagName("event");
		String allEvents = matches.toString();

		for (int i = 2; i < 38; i++) {
			try {
				String matchname = matches.item(i).getAttributes().item(0).toString();
				matchname = matchname.replaceAll("\\s", "");

				String[] teams = matchname.split("v");

				NodeList bet = XMLParser.parse(matches.item(i).toString()).getElementsByTagName("bet");
				// matches.item(i).
				String homeWinS = bet.item(0).getAttributes().item(4).toString();
				double homeWinD = Double.parseDouble(bet.item(0).getAttributes().item(5).toString());

				String drawS = bet.item(1).getAttributes().item(4).toString();
				double drawD = Double.parseDouble(bet.item(1).getAttributes().item(5).toString());

				String awayWinS = bet.item(2).getAttributes().item(4).toString();
				double awayWinD = Double.parseDouble(bet.item(2).getAttributes().item(5).toString());

				matchesMap.get(matchname).getMatchOdds().setHomeWinS(homeWinS);
				matchesMap.get(matchname).getMatchOdds().setHomeWinD(homeWinD);

				matchesMap.get(matchname).getMatchOdds().setDrawS(drawS);
				matchesMap.get(matchname).getMatchOdds().setDrawD(drawD);

				matchesMap.get(matchname).getMatchOdds().setAwayWinS(awayWinS);
				matchesMap.get(matchname).getMatchOdds().setAwayWinD(awayWinD);
			} catch (Exception e) {

			}
		}

	}

	private void parseGroupsBets(String xml) {
		String textgroups = XMLParser.parse(xml).getElementsByTagName("event").item(1).toString();
		NodeList betType = XMLParser.parse(textgroups).getElementsByTagName("bettype");

		char[] groups = { 'A', 'B', 'C', 'D', 'E', 'F' };
		for (int i = 0; i < groups.length; i++) {
			String groupDataStr = betType.item(i).toString();
			Document groupData = XMLParser.parse(groupDataStr);
			for (int p = 0; p < 4; p++) {
				try {
					NamedNodeMap atributes = groupData.getElementsByTagName("bet").item(p).getAttributes();
					String countryName = atributes.item(0).toString();
					countryName = countryName.replaceAll("\\s", "");
					String groupS = atributes.item(3).toString();
					Double groupD = Double.parseDouble(atributes.item(4).toString());

					countries.get(countryName).setGroup(groups[i]);
					countries.get(countryName).getOdds().setGroupS(groupS);
					countries.get(countryName).getOdds().setGroupD(groupD);
				} catch (Exception e) {

				}
			}

		}
	}

	private void addBetOutright(String country, String oddsS, double oddsD) {
		country = country.replaceAll("\\s+", "");
		if (country.equalsIgnoreCase("Ireland"))
			country = "RepublicofIreland";
		countries.get(country).getOdds().setOutrightS(oddsS);
		countries.get(country).getOdds().setOutrightD(oddsD);
	}

	private void addBetFinal(String country, String oddsS, double oddsD) {
		country = country.replaceAll("\\s+", "");
		if (country.equalsIgnoreCase("Ireland"))
			country = "RepublicofIreland";
		countries.get(country).getOdds().setFinalS(oddsS);
		countries.get(country).getOdds().setFinalD(oddsD);
	}

	private void addBetSemi(String country, String oddsS, double oddsD) {
		country = country.replaceAll("\\s+", "");
		if (country.equalsIgnoreCase("Ireland"))
			country = "RepublicofIreland";
		countries.get(country).getOdds().setSemiS(oddsS);
		countries.get(country).getOdds().setSemiD(oddsD);
	}
}
