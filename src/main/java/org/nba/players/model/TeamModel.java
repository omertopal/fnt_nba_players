package org.nba.players.model;

import java.util.Map;

public class TeamModel {
	
	private String code;
	
	private String name;
	
	private Map<String,Integer> gameDays;

	public TeamModel(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Integer> getGameDays() {
		return gameDays;
	}

	public void setGameDays(Map<String, Integer> gameDays) {
		this.gameDays = gameDays;
	}
}
