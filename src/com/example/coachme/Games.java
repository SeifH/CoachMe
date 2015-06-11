package com.example.coachme;

/**
 * Class manages the statistics of each game
 * 
 * @author Seif Hassan and Olivia Perryman
 * @since Friday, May 15 2015
 *
 */
public class Games implements Comparable<Games> {

	//name of game
	private String name;
	
	//values of statistics
	private int homePossession, homeShots, homeGoals, homeYellow, homeRed;
	private int awayPossession, awayShots, awayGoals, awayYellow, awayRed;

	/**
	 * Constructor creates a game with initial statistics values
	 * 
	 * @param n name of game
	 * @param hPossession percent possession of home
	 * @param hShots number of home shots
	 * @param hGoals number of home goals
	 * @param hYellow number of home yellow cards
	 * @param hRed number of home red cars
	 * @param aPossession percent possession of away
	 * @param aShots number of away shots
	 * @param aGoals number of away goals
	 * @param aYellow number of away yellow cards
	 * @param aRed number of away red cards
	 */
	public Games(String n, int hPossession, int hShots, int hGoals,
			int hYellow, int hRed, int aPossession, int aShots, int aGoals,
			int aYellow, int aRed) {

		super();
		//sets game's name
		setName(n);

		//sets game values
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

	/**
	 * Constructor creates game with no initial values
	 */
	public Games() {
		this(null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	/**
	 * Returns the name of the game
	 * @return name of game (String)
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the game
	 * @param name of game (String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the percent possession for home side
	 * @return home possession (int)
	 */
	public int getHomePossession() {
		return homePossession;
	}

	/**
	 * Sets the percent possession for home side
	 * @param homePossession (int)
	 */
	public void setHomePossession(int homePossession) {
		this.homePossession = homePossession;
	}

	/**
	 * Returns the number of shots for home side
	 * @return homeShots (int)
	 */
	public int getHomeShots() {
		return homeShots;
	}

	/**
	 * Sets the number of shots for home side
	 * @param homeShots (int)
	 */
	public void setHomeShots(int homeShots) {
		this.homeShots = homeShots;
	}

	/**
	 * Returns the number of goals for home side
	 * 
	 * @return homeGoals (int)
	 */
	public int getHomeGoals() {
		return homeGoals;
	}

	/**
	 * Sets the number of goals for home side
	 * 
	 * @param homeGoals (int)
	 */
	public void setHomeGoals(int homeGoals) {
		this.homeGoals = homeGoals;
	}

	/**
	 * Returns the number of yellow cards for home side
	 * @return homeYellow (int)
	 */
	public int getHomeYellow() {
		return homeYellow;
	}

	/**
	 * Sets the number of yellow cards for home side
	 * @param homeYellow (int)
	 */
	public void setHomeYellow(int homeYellow) {
		this.homeYellow = homeYellow;
	}

	/**
	 * Returns the number of red cards for home side
	 * @return homeRed (int)
	 */
	public int getHomeRed() {
		return homeRed;
	}

	/**
	 * Sets the number of red cards for home side
	 * @param homeRed
	 */
	public void setHomeRed(int homeRed) {
		this.homeRed = homeRed;
	}

	/**
	 * Returns the percent possession for away side
	 * @return away possession (int)
	 */
	public int getAwayPossession() {
		return awayPossession;
	}

	/**
	 * Sets the percent possession for away side
	 * @param awayPossession (int)
	 */
	public void setAwayPossession(int awayPossession) {
		this.awayPossession = awayPossession;
	}
	
	/**
	 * Returns the number of shots for away side
	 * @return awayShots (int)
	 */
	public int getAwayShots() {
		return awayShots;
	}

	/**
	 * Sets the number of shots for away side
	 * @param awayShots (int)
	 */
	public void setAwayShots(int awayShots) {
		this.awayShots = awayShots;
	}

	/**
	 * Returns the number of goals for away side
	 * @return awayGoals (int)
	 */
	public int getAwayGoals() {
		return awayGoals;
	}

	/**
	 * Sets the number of goals for away side
	 * @param awayGoals (int)
	 */
	public void setAwayGoals(int awayGoals) {
		this.awayGoals = awayGoals;
	}

	/**
	 * Returns the number of yellow cards for away side
	 * @return awayYellow (int)
	 */
	public int getAwayYellow() {
		return awayYellow;
	}

	/**
	 * Sets the number of yellow cards for away side
	 * @param awayYellow (int)
	 */
	public void setAwayYellow(int awayYellow) {
		this.awayYellow = awayYellow;
	}

	/**
	 * Returns the number of red cards for away side
	 * @return awatRed (int)
	 */
	public int getAwayRed() {
		return awayRed;
	}

	/**
	 * Sets the number of red cards for away side
	 * @param awayRed (int)
	 */
	public void setAwayRed(int awayRed) {
		this.awayRed = awayRed;
	}

	
	/*
	 * Compares games based on their names
	 */
	@Override
	public int compareTo(Games o) {
		return getName().compareToIgnoreCase(o.getName());

	}
}//end class
