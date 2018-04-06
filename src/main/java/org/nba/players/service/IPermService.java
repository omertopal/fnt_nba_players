package com.nba.players.service;

import java.util.List;

import com.nba.players.model.GameDateRosterModel;

public interface IPermService {
	
	List<GameDateRosterModel> getGameDateRosters();
}
