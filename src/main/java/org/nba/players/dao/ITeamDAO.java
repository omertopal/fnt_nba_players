package com.nba.players.dao;

import java.util.List;

import com.nba.players.entity.Team;

public interface ITeamDAO {
	List<Team> getAllTeams();
	Team getTeamById(String id);
	void addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(String code);
    boolean teamExists(String code);
}
