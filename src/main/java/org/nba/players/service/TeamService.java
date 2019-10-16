package org.nba.players.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.nba.players.dao.IScheduleDAO;
import org.nba.players.dao.ITeamDAO;
import org.nba.players.dto.GameDayCountDTO;
import org.nba.players.entity.Schedule;
import org.nba.players.entity.Team;
import org.nba.players.model.TeamModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService implements ITeamService {
	
	@Autowired
	private ITeamDAO teamDAO;
	
	@Autowired
	private IScheduleDAO schduleDAO;
	
	@Override
	public List<TeamModel> getAllTeams() {		
		return convertToTeamModelList(teamDAO.getAllTeams());
	}

	@Override
	public Team getTeamByCode(String code) {
		return teamDAO.getTeamByCode(code);
	}	
	
	public List<TeamModel> convertToTeamModelList(List<Team> teamList) {
		List<TeamModel> teamModelList = new ArrayList<TeamModel>();
		for (Team team : teamList) {
			TeamModel newTeamModel = new TeamModel(team.getCode(), team.getName());
			addScheduleToTeamModel(newTeamModel);
			teamModelList.add(newTeamModel);
		}
		return teamModelList;
	}
	
	public void addScheduleToTeamModel(TeamModel model) {
		
		List<Schedule> returnList = schduleDAO.getTeamSchedule(model.getCode());
		List<GameDayCountDTO> gameDayCounts = schduleDAO.getGameDayCounts();
		
		Map<String,Integer> resultMap = new HashMap<String,Integer>();
		
		for(Schedule schedule : returnList){
			
			List<GameDayCountDTO> thatDayInfo = gameDayCounts.stream().filter(gameDay -> gameDay.getGameDate().equals(schedule.getGameDate())).collect(Collectors.toList());
			int thatDayCount = thatDayInfo.get(0).getCount();
			
			Integer newCount = 1;
			
			if(resultMap.get("_"+thatDayCount+"_GAME")!=null) {
				newCount = resultMap.get("_"+thatDayCount+"_GAME")+1;
			}
			resultMap.put("_"+thatDayCount+"_GAME", newCount);
		} 
		model.setGameDays(resultMap);
	}	

	@Override
	public boolean addTeam(Team team) {
		if (teamDAO.teamExists(team.getCode())) {
            return false;
        } else {
        	teamDAO.addTeam(team);
            return true;
        }
	}

	@Override
	public void updateTeam(Team team) {
		teamDAO.updateTeam(team);

	}

	@Override
	public void deleteTeam(String code) {
		teamDAO.deleteTeam(code);

	}
}
