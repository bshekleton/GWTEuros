package com.bryan.euro.client.object;


public class MatchObj
{
	private CountryObj homeTeam;
	private CountryObj awayTeam;
	private char group;
	private double round;
	private String date;
	private String time;
	
	private MatchOdds matchOdds;

	public MatchObj(CountryObj homeTeam, CountryObj awayTeam, String matchDate, char group, double round)
	{
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.group = group;
		this.round = round;
		this.date  = matchDate.substring(0, 10);
		this.time  = matchDate.substring(11, 16);
		matchOdds = new MatchOdds();
	}

	public CountryObj getHomeTeam() {
		return homeTeam;
	}

	public CountryObj getAwayTeam() {
		return awayTeam;
	}

	public char getGroup() {
		return group;
	}

	public double getRound() {
		return round;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}
	

	public MatchOdds getMatchOdds() {
		return matchOdds;
	}


}
