package org.nba.players.controller;

import java.util.List;

import org.nba.players.entity.Team;
import org.nba.players.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("teams")
public class TeamController {
	@Autowired
	private ITeamService teamService;
	
	@GetMapping("allteams")
	public ResponseEntity<List<Team>> getAllTeams() {
		List<Team> list = teamService.getAllTeams();
		return new ResponseEntity<List<Team>>(list, HttpStatus.OK);
	}
}
