package com.bryan.euro.client.parsing;

import com.bryan.euro.client.match.MatchWindow;
import com.bryan.euro.client.object.RssObject;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.xml.client.CharacterData;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class RssParsing
{
	private MatchWindow mainvp;

	public void parseXMLNOW(String xml, MatchWindow mainvp2) {
		mainvp = mainvp2;
		try {
			NodeList b = XMLParser.parse(xml).getElementsByTagName("item");
			for (int i = 0; i < b.getLength(); i++) {
				String info = "<?xml version='1.0' encoding='utf-8'?><rss version='2.0'>" + b.item(i).toString() + "</rss>";
				Document data = XMLParser.parse(info);
				parseRssItem(data);
			}

		} catch (Exception e) {
			mainvp.add(new Label("Error parsing data " + xml));
		}
	}

	public void parseRssItem(Document data) {
		Node node = data.getElementsByTagName("title").item(0);
		String title = getCharacterDataFromElement(node);

		Node n2 = data.getElementsByTagName("link").item(0);
		String url = getCharacterDataFromElement(n2);

		Node n3 = data.getElementsByTagName("description").item(0);
		String description = getCharacterDataFromElement(n3);
		HTML des = new HTML(description);

		String category = data.getElementsByTagName("category").item(0).getFirstChild().getNodeValue();

		String imageUrl = data.getElementsByTagName("enclosure").item(0).getAttributes().item(0).getNodeValue();
		String publishDate = data.getElementsByTagName("pubDate").item(0).getFirstChild().getNodeValue();

		RssObject m1 = new RssObject(title, url, des, imageUrl, category, publishDate);

		mainvp.add(m1.toWidgit());
	}

	public static String getCharacterDataFromElement(Node title) {
		Node child = title.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

}
