package com.bryan.euro.client.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.bryan.euro.client.object.CountryObj;
import com.bryan.euro.client.object.MatchObj;
import com.bryan.euro.client.parsing.XmlParsingCountryCodes;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Element;

public class MatchWindow extends VerticalPanel
{

	public Element countryCodes;
	public HashMap<String, CountryObj> countries;
	public HashMap<String, MatchObj> matches;

	public MatchWindow(HashMap<String, CountryObj> countries2, XmlParsingCountryCodes countryCode,
			HashMap<String, MatchObj> match)
	{
		countries = countries2;
		matches = match;
		ArrayList<String> al = new ArrayList<String>();
		al.add("France");
		readinFixtures();
	}

	public void readinFixtures() {
		for (Entry<String, MatchObj> entry : matches.entrySet()) {
			addMatch(entry.getKey());
		}
	}

	public void readinFixtures(ArrayList<String> mat) {
		for (Entry<String, MatchObj> entry : matches.entrySet()) {
			if (mat.contains(entry.getValue().getAwayTeam().getNameId())
					|| mat.contains(entry.getValue().getHomeTeam().getNameId()))
				addMatch(entry.getKey());
		}
	}

	private HashMap<String, CountryObj>  readinTeamFixtures(String team) {
		HashMap<String, CountryObj> hm = new HashMap<String, CountryObj>();
		for (Entry<String, MatchObj> entry : matches.entrySet()) {
			if (team.equals(entry.getValue().getAwayTeam().getNameId())) {
				hm.put(entry.getValue().getHomeTeam().getNameId(), entry.getValue().getHomeTeam());
				addMatch(entry.getKey());
			} else if (team.equals(entry.getValue().getHomeTeam().getNameId())) {
				hm.put(entry.getValue().getHomeTeam().getNameId(), entry.getValue().getHomeTeam());
				hm.put(entry.getValue().getAwayTeam().getNameId(), entry.getValue().getAwayTeam());
				addMatch(entry.getKey());
			}
		}
		return hm;

	}

	public void readinFixtures(int matchday) {
		for (Entry<String, MatchObj> entry : matches.entrySet()) {

			if (entry.getValue().getRound() == matchday)
				addMatch(entry.getKey());
		}
	}

	public void teamsList(HashMap<String, CountryObj> teamsl) {
		int x = 0;
		int y = 0;
		FlexTable ft = new FlexTable();
		for (Entry<String, CountryObj> entry : teamsl.entrySet()) {
			if (x % 6 == 0) {
				x = 0;
				y += 2;
			}
			final String key = entry.getKey();
			String co = entry.getValue().getName();
			Label nl = new Label(co);
			nl.setStyleName("centerText");
			ft.setWidget(y, x, nl);
			y++;

			Image img = new Image(entry.getValue().getUrlflag());
			img.setStyleName("flagimg");
			img.addClickHandler(new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event) {
					getCountryDetails(key);
				}
			});
			ft.setWidget(y, x, img);
			x++;
			y--;
		}
		add(ft);
	}

	public void addMatch(String id) {
		MatchObj m = matches.get(id);
		HorizontalPanel hp = new HorizontalPanel();
		Label dla = new Label(m.getDate());
		dla.setStyleName("date");
		hp.add(dla);
		Label htName = new Label(m.getHomeTeam().getName());
		htName.setStyleName("righttext");
		hp.add(htName);
		hp.add(flagAsWidget(m.getHomeTeam()));

		VerticalPanel vp = new VerticalPanel();
		vp.add(new Label(m.getTime()));
		vp.add(new Label("Group " + m.getHomeTeam().getGroup()));
		hp.add(vp);

		hp.add(flagAsWidget(m.getAwayTeam()));
		Label atName = new Label(m.getAwayTeam().getName());
		atName.setStyleName("lefttext");
		hp.add(atName);
		FlexTable t = new FlexTable();
		t.setText(0, 0, htName.getText().substring(0, 8)+": ");
		t.setText(0, 1, m.getMatchOdds().getHomeWinS());
		t.setText(1, 0, "Draw:");
		t.setText(1, 1, m.getMatchOdds().getDrawS());
		t.setText(2, 0, atName.getText().substring(0, 8)+": ");
		t.setText(2, 1, m.getMatchOdds().getAwayWinS());
		t.setStyleName("oddstable");

		hp.add(t);
		hp.setStyleName("matchhorpan");
		add(hp);
	}

	private Image flagAsWidget(final CountryObj m) {
		Image img = new Image(m.getUrlflag());
		img.setStyleName("flagimg");
		img.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event) {
				getCountryDetails(m.getNameId());
			}
		});

		return img;
	}

	public void getGroups()
	{
		HorizontalPanel vpa = new HorizontalPanel();
		vpa.setStyleName("groupsText");
		HorizontalPanel vpb = new HorizontalPanel();
		vpb.setStyleName("groupsText");
		HorizontalPanel vpc = new HorizontalPanel();
		vpc.setStyleName("groupsText");
		HorizontalPanel vpd = new HorizontalPanel();
		vpd.setStyleName("groupsText");
		HorizontalPanel vpe = new HorizontalPanel();
		vpe.setStyleName("groupsText");
		HorizontalPanel vpf = new HorizontalPanel();
		vpf.setStyleName("groupsText");
		
		for (Entry<String, CountryObj> entry : countries.entrySet())
		{
			char group = entry.getValue().getGroup();
			VerticalPanel vp = new VerticalPanel();
			
			final String key = entry.getKey();
			String co = entry.getValue().getName();
			Label nl = new Label(co);
			nl.setStyleName("centerText");
			vp.add(nl);

			Image img = new Image(entry.getValue().getUrlflag());
			img.setStyleName("flagimgGroup");
			img.addClickHandler(new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event) {
					getCountryDetails(key);
				}
			});
			vp.add(img);
			
			
			switch(group)
			{
				case 'A':
					vpa.add(vp);
				break;
				case 'B':
					vpb.add(vp);
					break;
				case 'C':
					vpc.add(vp);
					break;
				case 'D':
					vpd.add(vp);
					break;
				case 'E':
					vpe.add(vp);
					break;
				case 'F':
					vpf.add(vp);
					break;
			}
		}
		
		clear();
		Label ga = new Label("Group A");
		Label gb = new Label("Group B");
		Label gc = new Label("Group C");
		Label gd = new Label("Group D");
		Label ge = new Label("Group E");
		Label gf = new Label("Group F");
		ga.setStyleName("centerboldgroup");
		gb.setStyleName("centerboldgroup");
		gc.setStyleName("centerboldgroup");
		gd.setStyleName("centerboldgroup");
		ge.setStyleName("centerboldgroup");
		gf.setStyleName("centerboldgroup");
		add(ga);
		add(vpa);
		add(gb);
		add(vpb);
		add(gc);
		add(vpc);
		add(gd);
		add(vpd);
		add(ge);
		add(vpe);
		add(gf);
		add(vpf);
	}

	public void getCountryDetails(String country) {
		clear();
		country = country.replaceAll("\\s", "");
		Label lname = new Label(countries.get(country).getName());
		lname.setStyleName("centerbold");
		HorizontalPanel hp = new HorizontalPanel();
		Image im = new Image(countries.get(country).getUrlflag());
		im.setStyleName("sqaureflag");
		hp.add(im);
		hp.add(lname);
		add(hp);
		FlexTable ft = new FlexTable();
		ft.setText(0, 0, "Outright odds: ");
		ft.setText(0, 1, countries.get(country).getOdds().getOutrightS());
		ft.setText(1, 0, "Reach final odds: ");
		ft.setText(1, 1, countries.get(country).getOdds().getFinalS());
		ft.setText(2, 0, "Reach semi's odds: ");
		ft.setText(2, 1, countries.get(country).getOdds().getSemiS());
		ft.setText(3, 0, "To win Group: ");
		ft.setText(3, 1, countries.get(country).getOdds().getGroupS());
		ft.setStyleName("countryodds");
		for (int i =0; i< 4;i++)
		{
			ft.getCellFormatter().setStyleName(i, 0, "ftcellodds");
			ft.getCellFormatter().setStyleName(i, 1, "ftcelloddsno");
		}
		
		
		add(ft);
		HashMap<String, CountryObj> teams = readinTeamFixtures(country);
		new CompareTeams();
		add(CompareTeams.compareTeamsList(teams, this));
	}

}