package org.nba.players.dto;

import java.util.List;

import org.nba.players.model.GameDateRosterModel;

public class CalcUsageResult {
	
	public CalcUsageResult() {
		
	}
	
	List<GameDateRosterModel> gameDateRosterList;
	
	List<PlayerUsageDTO> playerUsageList;

	public List<GameDateRosterModel> getGameDateRosterList() {
		return gameDateRosterList;
	}

	public void setGameDateRosterList(List<GameDateRosterModel> gameDateRosterList) {
		this.gameDateRosterList = gameDateRosterList;
	}

	public List<PlayerUsageDTO> getPlayerUsageList() {
		return playerUsageList;
	}

	public void setPlayerUsageList(List<PlayerUsageDTO> playerUsageList) {
		this.playerUsageList = playerUsageList;
	}
}
