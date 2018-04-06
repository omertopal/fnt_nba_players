package org.nba.players.service;

import java.util.List;

import org.nba.players.entity.Team;

public interface ITeamService {
	List<Team> getAllTeams();
	Team getTeamById(String code);
	boolean addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(String code);
}
