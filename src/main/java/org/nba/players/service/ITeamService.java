package org.nba.players.service;

import java.util.List;

import org.nba.players.entity.Team;
import org.nba.players.model.TeamModel;

public interface ITeamService {
	List<TeamModel> getAllTeams();
	Team getTeamByCode(String code);
	boolean addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(String code);
}
