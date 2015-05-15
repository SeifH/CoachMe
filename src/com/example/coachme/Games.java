package com.example.coachme;

public class Games implements Comparable<Games>{

	private String name;
	private int homePossession, homeShots, homeGoals, homeYellow, homeRed;
	private int awayPossession, awayShots, awayGoals, awayYellow, awayRed;

	public Games(String n, int hPossession, int hShots, int hGoals,
			int hYellow, int hRed, int aPossession, int aShots, int aGoals,
			int aYellow, int aRed) {

		super();
		setName(n);
		
		setHomePossession(hPossession);
		setHomeShots(hShots);
		setHomeGoals(hGoals);
		setHomeYellow(hYellow);
		setHomeRed(hRed);
		
		setAwayPossession(aPossession);
		setAwayShots(aShots);
		setAwayGoals(aGoals);
		setAwayYellow(aYellow);
		setAwayRed(aRed);

	}
	
	public Games (){
		this (null,0,0,0,0,0,0,0,0,0,0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHomePossession() {
		return homePossession;
	}

	public void setHomePossession(int homePossession) {
		this.homePossession = homePossession;
	}

	public int getHomeShots() {
		return homeShots;
	}

	public void setHomeShots(int homeShots) {
		this.homeShots = homeShots;
	}

	public int getHomeGoals() {
		return homeGoals;
	}

	public void setHomeGoals(int homeGoals) {
		this.homeGoals = homeGoals;
	}

	public int getHomeYellow() {
		return homeYellow;
	}

	public void setHomeYellow(int homeYellow) {
		this.homeYellow = homeYellow;
	}

	public int getHomeRed() {
		return homeRed;
	}

	public void setHomeRed(int homeRed) {
		this.homeRed = homeRed;
	}

	public int getAwayPossession() {
		return awayPossession;
	}

	public void setAwayPossession(int awayPossession) {
		this.awayPossession = awayPossession;
	}

	public int getAwayShots() {
		return awayShots;
	}

	public void setAwayShots(int awayShots) {
		this.awayShots = awayShots;
	}

	public int getAwayGoals() {
		return awayGoals;
	}

	public void setAwayGoals(int awayGoals) {
		this.awayGoals = awayGoals;
	}

	public int getAwayYellow() {
		return awayYellow;
	}

	public void setAwayYellow(int awayYellow) {
		this.awayYellow = awayYellow;
	}

	public int getAwayRed() {
		return awayRed;
	}

	public void setAwayRed(int awayRed) {
		this.awayRed = awayRed;
	}
	
	/*
	 * Compares games based on their names
	 */
	@Override
	public int compareTo(Games o) {
		return getName().compareTo(o.getName());
		
//		if (getName() > o.getName())
//			return 1;
//		else if ((getName() < o.getName()))
//			return -1;
//		else
//			return 0;
	}
}
