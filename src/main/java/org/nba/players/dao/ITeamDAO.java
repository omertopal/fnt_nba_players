package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.Team;

public interface ITeamDAO {
	List<Team> getAllTeams();
	Team getTeamById(String id);
	void addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(String code);
    boolean teamExists(String code);
}
