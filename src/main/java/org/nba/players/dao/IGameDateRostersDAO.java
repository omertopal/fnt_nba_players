package com.nba.players.dao;

import java.util.List;

import com.nba.players.model.GameDateRosterModel;

public interface IGameDateRostersDAO {
	public void persist(GameDateRosterModel roster);
	public void persistAll(List<GameDateRosterModel> rosters);
	public void update(GameDateRosterModel roster);
	public void updateAll(GameDateRosterModel roster);
	public void remove(GameDateRosterModel roster);
	void removeAll();
}
