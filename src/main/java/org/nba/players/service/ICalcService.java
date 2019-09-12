package org.nba.players.service;

import java.util.List;

import org.nba.players.dto.CalculationIdDTO;
import org.nba.players.model.GameDateRosterModel;

public interface ICalcService {
	
	List<GameDateRosterModel> getGameDateRosters(String method) throws Exception;
	
	public List<GameDateRosterModel> getAllGameDateRosters(int calcId) throws Exception;
	
	public List<CalculationIdDTO> calculationIdList();
	
}
