package com.bryan.euro.client.header;

import com.bryan.euro.client.LeftMenu.LeftWindow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HeaderWindow extends VerticalPanel {

	private LeftWindow leftWindow;

	public HeaderWindow(LeftWindow leftWindo) {
		this.leftWindow = leftWindo;


		Image image = new Image("http://www.wegotsoccer.com/mmWGS/euro2016/banner-big.jpg");
		image.setStyleName("banner");
		add(image);

		HorizontalPanel menupannel = new HorizontalPanel();
		menupannel.setStyleName("buttonsback");

		Button match = new Button("Matches");
		match.setStyleName("headerbutton");
		match.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				leftWindow.matchTab();
			}
		});

		Button groups = new Button("Groups");
		groups.setStyleName("headerbutton");
		groups.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				leftWindow.groupTab();
			}
		});

		Button teams = new Button("Teams");
		teams.setStyleName("headerbutton");
		teams.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				leftWindow.teamTab();

			}
		});
		
		Button news = new Button("News");
		news.setStyleName("headerbutton");
		news.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				leftWindow.newsTab();
			}
		});

		menupannel.add(match);
		menupannel.add(teams);
		menupannel.add(groups);
		menupannel.add(news);

		add(menupannel);
	}

}
