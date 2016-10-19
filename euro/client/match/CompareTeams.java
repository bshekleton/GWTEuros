package com.bryan.euro.client.match;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.bryan.euro.client.object.CountryObj;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CompareTeams
{

	public static Widget compareTeamsList(HashMap<String, CountryObj> countriesToCompare, final MatchWindow mw) {
		int gridWidth = 6;
		Grid g = new Grid(countriesToCompare.size() + 1, gridWidth);
		Label l1 = new Label("Team");
		g.setWidget(0, 0, l1);

		Label l2 = new Label("Group");
		g.setWidget(0, 1, l2);

		Label l3 = new Label("Outright");
		g.setWidget(0, 2, l3);

		Label l4 = new Label("Reach Final");
		g.setWidget(0, 3, l4);

		Label l5 = new Label("Reach Semi");
		g.setWidget(0, 4, l5);

		Label l6 = new Label("Top Group");
		g.setWidget(0, 5, l6);

		int i = -1;
		double favourite = 2000;
		int favpos = -1;
		TreeMap<Double, CountryObj> sorted = sortHashMap(countriesToCompare);
		for (Entry<Double, CountryObj> entry : sorted.entrySet())
		{
			i++;
			final CountryObj co = entry.getValue();
			String name = co.getName();
			char group = co.getGroup();
			String outrightodds = co.getOdds().getOutrightS();
			String finalodds = co.getOdds().getFinalS();
			String semiodds = co.getOdds().getSemiS();
			String groupodds = co.getOdds().getGroupS();

			if (co.getOdds().getOutrightD() < favourite) {
				favourite = co.getOdds().getOutrightD();
				favpos = i + 1;
			}

			l1 = new Label(name);
			g.setWidget(i + 1, 0, l1);
			l2 = new Label(String.valueOf(group));
			g.setWidget(i + 1, 1, l2);
			l3 = new Label(String.valueOf(outrightodds));
			g.setWidget(i + 1, 2, l3);
			l4 = new Label(String.valueOf(finalodds));
			g.setWidget(i + 1, 3, l4);
			l5 = new Label(String.valueOf(semiodds));
			g.setWidget(i + 1, 4, l5);
			l6 = new Label(String.valueOf(groupodds));
			g.setWidget(i + 1, 5, l6);
		}

		for (int y = 0; y < i + 2; y++) {
			for (int x = 0; x < gridWidth; x++) {
				if (y == 0) {
					g.getCellFormatter().addStyleName(y, x, "oddscomparefav");
				}else
					g.getCellFormatter().addStyleName(y, x, "oddscompare");
			}
		}
		g.setCellPadding(5);
		g.setStyleName("matchhorpan");
		return g;
	}

	private static TreeMap<Double, CountryObj> sortHashMap(HashMap<String, CountryObj> hm) {

		TreeMap<Double, CountryObj> tm = new TreeMap<Double, CountryObj>(); 
		for (Entry<String, CountryObj> entry : hm.entrySet())
		{
			tm.put(entry.getValue().getOdds().getOutrightD(), entry.getValue());
		}
		return tm;
	}

		

}
