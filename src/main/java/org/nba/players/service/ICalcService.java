package org.nba.players.service;

import java.util.List;

import org.nba.players.dto.CalcUsageResult;
import org.nba.players.dto.CalculationIdDTO;
import org.nba.players.dto.TeamBenefitDTO;
import org.nba.players.model.GameDateRosterModel;

public interface ICalcService {
	
	CalcUsageResult getGameDateRosters(String method) throws Exception;
	
	public CalcUsageResult getAllGameDateRosters(int calcId) throws Exception;
	
	public List<CalculationIdDTO> calculationIdList();
	
	public List<TeamBenefitDTO> getTeamBenefitList(int calcId);
	
}
