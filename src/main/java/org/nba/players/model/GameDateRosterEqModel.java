package org.nba.players.model;

public class GameDateRosterEqModel {	
	
	private int gameDateRosterId;
	
	private int equivalentPermId;

	public int getGameDateRosterId() {
		return gameDateRosterId;
	}

	public GameDateRosterEqModel(int equivalentPermId) {
		super();
		this.equivalentPermId = equivalentPermId;
	}

	public int getEquivalentPermId() {
		return equivalentPermId;
	}

	public void setGameDateRosterId(int gameDateRosterId) {
		this.gameDateRosterId = gameDateRosterId;
	}

	public void setEquivalentPermId(int equivalentPermId) {
		this.equivalentPermId = equivalentPermId;
	}
}
