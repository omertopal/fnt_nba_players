package org.nba.players.service;

import java.util.List;

import org.nba.players.dao.ITeamDAO;
import org.nba.players.dto.PlayerDTO;
import org.nba.players.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService implements ITeamService {
	
	@Autowired
	private ITeamDAO teamDAO;
	
	@Override
	public List<Team> getAllTeams() {
		return teamDAO.getAllTeams();
	}

	@Override
	public Team getTeamByCode(String code) {
		return teamDAO.getTeamByCode(code);
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
