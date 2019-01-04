package org.nba.players.service;

import java.util.List;

import org.nba.players.model.GameDateRosterModel;

public interface IPermService {
	
	List<GameDateRosterModel> getGameDateRosters() throws Exception;
}
