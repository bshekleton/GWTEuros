package com.bryan.euro.client.parsing;

import com.bryan.euro.client.Euros;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

public class XmlParsingCountryCodes
{
	private Element countryCodes;
	private String url;
	private Euros mainC;
	XmlParsingCountryCodes thisClass;

	public XmlParsingCountryCodes(String n_url, Euros main)
	{
		thisClass = this;
		this.mainC = main;
		url = n_url;
		setCountryCodes();
	}

	public Element getCountryCodes() {
		return countryCodes;
	}

	private void setCountryCodes() {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		try {
			builder.sendRequest("", new RequestCallback()
			{
				@Override
				public void onResponseReceived(Request request, Response response) {
					Document xmlDoc = XMLParser.parse(response.getText());
					countryCodes = xmlDoc.getDocumentElement();
					XMLParser.removeWhitespace(countryCodes);
					new JsonParsingTeams(countryCodes, mainC);
				}

				@Override
				public void onError(Request request, Throwable exception) {

				}
			});
		} catch (RequestException e) { // TODD Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String setFlag(final String countryCode) {
		String url;
		// remove any whitespace
		String cc = countryCode.replaceAll("\\s+", "");

		// find the countrys name in the xml sheet
		String code = countryCodes.getElementsByTagName(cc).item(0).getChildNodes().item(0).getFirstChild().getNodeValue();

		if (code.length() > 3) {
			url = code;
		} else {
			code = code.toLowerCase();
			url = "http://www.geonames.org/flags/x/" + code + ".gif";
		}
		return url;
	}

}
