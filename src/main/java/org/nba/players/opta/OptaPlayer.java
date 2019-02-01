package org.nba.players.opta;

import java.util.Set;

public class OptaPlayer{
	
	private Integer playerId;
	
	private String name;
	
	private Double avgPts;
	
	private OptaTeam team;
	
	private Set<Integer> skills;
	
	public boolean hasSkillForSpot(Integer spot) {
		if(skills.contains(spot)) return true;
		return false;
	}
	
	public boolean playerHasGameThatDay(Integer currGameDate) {
		if(team.getGameDates().contains(currGameDate)) return true;
		return false;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAvgPts() {
		return avgPts;
	}

	public void setAvgPts(Double avgPts) {
		this.avgPts = avgPts;
	}

	public Set<Integer> getSkills() {
		return skills;
	}

	public void setSkills(Set<Integer> skills) {
		this.skills = skills;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public void setTeam(OptaTeam team) {
		this.team = team;
	}
	
	
}
