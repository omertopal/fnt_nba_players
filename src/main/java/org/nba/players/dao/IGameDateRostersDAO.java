package org.nba.players.dao;

import java.util.List;

import org.nba.players.dto.CalculationIdDTO;
import org.nba.players.model.GameDateRosterModel;

public interface IGameDateRostersDAO {
	public void persist(GameDateRosterModel roster);
	public void persistAll(List<GameDateRosterModel> rosters);
	public void update(GameDateRosterModel roster);
	public void updateAll(GameDateRosterModel roster);
	public void remove(GameDateRosterModel roster);
	void removeAll();
	public List<GameDateRosterModel> getAllGameDateRosters (int calcId);
	public List<CalculationIdDTO> calculationIdList();
}
