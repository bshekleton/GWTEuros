package com.bryan.euro.client.LeftMenu;

import java.util.HashMap;

import com.bryan.euro.client.EuroServiceAsync;
import com.bryan.euro.client.match.MatchWindow;
import com.bryan.euro.client.object.CountryObj;
import com.bryan.euro.client.parsing.RssParsing;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LeftWindow extends VerticalPanel
{
	private MatchWindow mainvp;

	HashMap<String, CountryObj> countries;
	private EuroServiceAsync euroXml;

	public LeftWindow(MatchWindow main, HashMap<String, CountryObj> countrie, EuroServiceAsync euroXm)
	{
		this.euroXml = euroXm;
		this.countries = countrie;
		mainvp = main;
		matchTab();
	}

	protected void setToMatches(int day) {
		mainvp.clear();
		mainvp.add(new Label("Matches Round " + day));
		mainvp.readinFixtures(day);
	}

	public void matchTab() {

		clear();
		Tree tree = new Tree();

		tree.addSelectionHandler(new SelectionHandler<TreeItem>()
		{
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {

				String slection = event.getSelectedItem().getText();
				switch (slection) {
				case "Match Day 1":
					setToMatches(1);
					break;

				case "Match Day 2":
					setToMatches(2);
					break;

				case "Match Day 3":
					setToMatches(3);
					break;
				}
			}
		});

		Label la1 = new Label("Match Day 1");
		la1.setStyleName("greenBackground");
		TreeItem t1 = new TreeItem(la1);
		tree.addItem(t1);
		Label la2 = new Label("Match Day 2");
		la2.setStyleName("greenBackground");
		TreeItem t2 = new TreeItem(la2);
		tree.addItem(t2);
		Label la3 = new Label("Match Day 3");
		la3.setStyleName("greenBackground");
		TreeItem t3 = new TreeItem(la3);
		tree.addItem(t3);
		add(tree);
		setToMatches(1);

	}

	public void groupTab() {
		clear();
		Label l = new Label("Groups");
		l.setStyleName("centerbig");
		add(l);
		mainvp.clear();
		mainvp.getGroups();
	}

	public void teamTab() {
		clear();
		Label l = new Label("Teams");
		l.setStyleName("centerbig");
		add(l);
		mainvp.clear();
		mainvp.teamsList(countries);
	}

	public void newsTab() {
		clear();

		Label l = new Label("News");
		l.setStyleName("centerbig");
		add(l);
		mainvp.clear();
		// mainvp.add();
		euroXml.getFeed("http://www.uefa.com/rssfeed/uefaeuro/rss.xml", new AsyncCallback<String>()
		{

			@Override
			public void onSuccess(String res) {
				if (res != null)
					new RssParsing().parseXMLNOW(res, mainvp);
				else
					offLineMOde();
			}

			@Override
			public void onFailure(Throwable caught) {
				offLineMOde();

				GWT.log("error " + caught.toString());
			}
		});

	}

	protected void offLineMOde() {
		String fix = "news.xml";
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, fix);
		try {
			builder.sendRequest("", new RequestCallback()
			{
				@Override
				public void onResponseReceived(Request request, Response response) {
					new RssParsing().parseXMLNOW(response.getText(), mainvp);
				}

				@Override
				public void onError(Request request, Throwable exception) {
				}
			});
		} catch (RequestException e) {
			e.printStackTrace();
		}

	}
}
