package com.nba.players.service;

import java.util.List;

import com.nba.players.entity.Team;

public interface ITeamService {
	List<Team> getAllTeams();
	Team getTeamById(String code);
	boolean addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(String code);
}
