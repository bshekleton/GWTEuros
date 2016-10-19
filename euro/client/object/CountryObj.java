package com.bryan.euro.client.object;

public class CountryObj
{

	private String name;
	private String nameId;
	private char group;
	private String urlflag;
	private teamOddsObj odds;

	public CountryObj(String name, String namei)
	{
		this.nameId = namei;
		this.name = name;
		this.group = 'A';
		this.odds = new teamOddsObj();
	}

	public char getGroup() {
		return group;
	}

	public void setGroup(char group) {
		this.group = group;
	}

	public teamOddsObj getOdds() {
		return odds;
	}

	public void setOdds(teamOddsObj odds) {
		this.odds = odds;
	}

	public String getName() {
		return name;
	}

	public String getUrlflag() {
		return urlflag;
	}

	public void setUrlflag(String urlflag) {
		this.urlflag = urlflag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

}
