package com.bryan.euro.client;

import java.util.HashMap;

import com.bryan.euro.client.LeftMenu.LeftWindow;
import com.bryan.euro.client.header.HeaderWindow;
import com.bryan.euro.client.match.MatchWindow;
import com.bryan.euro.client.object.CountryObj;
import com.bryan.euro.client.object.MatchObj;
import com.bryan.euro.client.parsing.XmlParsingCountryCodes;
import com.bryan.euro.client.parsing.XmlParsingFile;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


public class Euros implements EntryPoint
{
	private MatchWindow mainVpannel;
	EuroServiceAsync euroXml = (EuroServiceAsync) GWT.create(EuroService.class);
	HashMap<String, CountryObj> countries;
	HashMap<String, MatchObj>   matches;
	private XmlParsingCountryCodes xmlCountriesCodes;

	public HashMap<String, CountryObj> getCountries() {
		return countries;
	}

	public HashMap<String, MatchObj> getMatches() {
		return matches;
	}

	public XmlParsingCountryCodes getXmlCountriesCodes() {
		return xmlCountriesCodes;
	}

	public void onModuleLoad() {
		RootPanel.get().add(new Image("loadin.gif"));
		countries = new HashMap<String, CountryObj>();
		matches = new HashMap<String, MatchObj>();
		((ServiceDefTarget) euroXml).setServiceEntryPoint(GWT.getModuleBaseURL() + "EuroService");
		xmlCountriesCodes = new XmlParsingCountryCodes("countrycodes.xml", this);
	
		
	}
	
	public void updateodds()
	{		
		new XmlParsingFile(euroXml, countries, matches,this);
	}

	public void onReady() {
		RootPanel.get().remove(0);
		RootPanel.get().setStyleName("background");
		mainVpannel = new MatchWindow(countries, xmlCountriesCodes, matches);
		mainVpannel.setStyleName("matchwindow");
		
		DockPanel mainPanel = new DockPanel();
		mainPanel.setStyleName("dockpanel");
		mainPanel.setBorderWidth(2);
		mainPanel.setSize("1000px", "750px");
		mainPanel.setVerticalAlignment(HasAlignment.ALIGN_TOP);
		mainPanel.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);

		LeftWindow leftWindow = new LeftWindow(mainVpannel, countries, euroXml);
		leftWindow.setStyleName("leftwindow");
		HeaderWindow header = new HeaderWindow(leftWindow);

		mainPanel.add(header, DockPanel.NORTH);
		mainPanel.setCellHeight(header, "60px");
		

		mainPanel.add(mainVpannel, DockPanel.EAST);

		mainVpannel.add(new Label("number " + matches.size()));

		mainPanel.add(leftWindow, DockPanel.WEST);
		mainPanel.setCellWidth(leftWindow, "200px");

		RootPanel.get().add(mainPanel);
	}

}
