package com.bryan.euro.client.object;

public class MatchOdds
{
	private String homeWinS;
	private double homeWinD;
	private String awayWinS;
	private double awayWinD;
	private String drawS;
	private double drawD;

	public MatchOdds()
	{
		homeWinS = "";
		homeWinD = 0;
		awayWinS = "";
		awayWinD = 0;
		drawS = "";
		drawD = 0;
	}

	public String getHomeWinS() {
		return homeWinS;
	}

	public void setHomeWinS(String homeWinS) {
		this.homeWinS = homeWinS;
	}

	public double getHomeWinD() {
		return homeWinD;
	}

	public void setHomeWinD(double homeWinD) {
		this.homeWinD = homeWinD;
	}

	public String getAwayWinS() {
		return awayWinS;
	}

	public void setAwayWinS(String awayWinS) {
		this.awayWinS = awayWinS;
	}

	public double getAwayWinD() {
		return awayWinD;
	}

	public void setAwayWinD(double awayWinD) {
		this.awayWinD = awayWinD;
	}

	public String getDrawS() {
		return drawS;
	}

	public void setDrawS(String drawS) {
		this.drawS = drawS;
	}

	public double getDrawD() {
		return drawD;
	}

	public void setDrawD(double drawD) {
		this.drawD = drawD;
	}

}
