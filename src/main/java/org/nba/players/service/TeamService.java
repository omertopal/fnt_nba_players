package org.nba.players.service;

import java.util.List;

import org.nba.players.dao.ITeamDAO;
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
	public Team getTeamById(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addTeam(Team team) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateTeam(Team team) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTeam(String code) {
		// TODO Auto-generated method stub

	}

}
