package com.bryan.euro.client.object;

public class teamOddsObj
{

	private String outrightS;
	private double outrightD;
	private String finalS;
	private double finalD;
	private String semiS;
	private double semiD;
	private String groupS;
	private double groupD;

	public teamOddsObj()
	{

	}

	public teamOddsObj(String outrightS, double outrightD, String finalS, double finalD, String groupS, double groupD)
	{
		this.outrightS = outrightS;
		this.outrightD = outrightD;
		this.finalS = finalS;
		this.finalD = finalD;
		this.groupS = groupS;
		this.groupD = groupD;
	}

	public String getOutrightS() {
		return outrightS;
	}

	public double getOutrightD() {
		return outrightD;
	}

	public String getFinalS() {
		return finalS;
	}

	public double getFinalD() {
		return finalD;
	}

	public String getGroupS() {
		return groupS;
	}

	public void setOutrightS(String outrightS) {
		this.outrightS = outrightS;
	}

	public void setOutrightD(double outrightD) {
		this.outrightD = outrightD;
	}

	public void setFinalS(String finalS) {
		this.finalS = finalS;
	}

	public void setFinalD(double finalD) {
		this.finalD = finalD;
	}

	public void setGroupS(String groupS) {
		this.groupS = groupS;
	}

	public void setGroupD(double groupD) {
		this.groupD = groupD;
	}

	public double getGroupD() {
		return groupD;
	}

	public String getSemiS() {
		return semiS;
	}

	public void setSemiS(String semiS) {
		this.semiS = semiS;
	}

	public double getSemiD() {
		return semiD;
	}

	public void setSemiD(double semiD) {
		this.semiD = semiD;
	}

	@Override
	public String toString() {
		return "teamOddsObj [outrightS=" + outrightS + ", outrightD=" + outrightD + ", finalS=" + finalS + ", finalD="
				+ finalD + ", groupS=" + groupS + ", groupD=" + groupD + "]";
	}

}
