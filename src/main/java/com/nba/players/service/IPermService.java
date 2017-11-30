package com.nba.players.service;

import java.util.List;

import com.nba.players.model.GameDateRosterModel;
import com.nba.players.model.PermModel;

public interface IPermService {
	List<PermModel> getAllPERM_6_7();
	
	List<GameDateRosterModel> getGameDateRosters();
}
