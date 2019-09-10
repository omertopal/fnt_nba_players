package org.nba.players.controller;

import java.util.List;

import org.nba.players.entity.Team;
import org.nba.players.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teams")
public class TeamController {
	@Autowired
	private ITeamService teamService;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Team>> getAllTeams() {
		List<Team> list = teamService.getAllTeams();
		return new ResponseEntity<List<Team>>(list, HttpStatus.OK);
	}
	
	@GetMapping(path="/{id}",produces = "application/json")
	public ResponseEntity<Team> getTeamByCode(@PathVariable("id") String code) {
		Team team = teamService.getTeamByCode(code);
		return new ResponseEntity<Team>(team, HttpStatus.OK);
	}
	
	@PostMapping(produces = "application/json")
	public ResponseEntity<Void> addTeam(@RequestBody Team team) {
        boolean flag = teamService.addTeam(team);
        if (flag == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	@PutMapping(produces = "application/json")
	public ResponseEntity<Void> updateTeam(@RequestBody Team team) {
		teamService.updateTeam(team);
		return new ResponseEntity<Void>( HttpStatus.OK);
	}
	@DeleteMapping("team/{code}")
	public ResponseEntity<Void> deleteTeam(@PathVariable("code") String code) {
		teamService.deleteTeam(code);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
