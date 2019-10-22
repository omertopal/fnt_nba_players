package org.nba.players.dto;

public class PlayerUsageDTO {
	
	public PlayerUsageDTO(){
		
	}

	int playerId;
	
	String playerName;
	
	int mustHaveUsage;
	
	int totalUsage;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getMustHaveUsage() {
		return mustHaveUsage;
	}

	public void setMustHaveUsage(int mustHaveUsage) {
		this.mustHaveUsage = mustHaveUsage;
	}

	public int getTotalUsage() {
		return totalUsage;
	}

	public void setTotalUsage(int totalUsage) {
		this.totalUsage = totalUsage;
	}
}
