package org.nba.players.controller;

import java.util.List;

import org.nba.players.dto.PlayerDTO;
import org.nba.players.entity.Player;
import org.nba.players.service.IPlayerService;
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
@RequestMapping("/players")
public class PlayerController extends NbaController{
	@Autowired
	private IPlayerService playerService;
	
	@GetMapping(path="/{id}",produces = "application/json")
	public ResponseEntity<Player> getPlayerById(@PathVariable("id") Integer id) {
		Player player = playerService.getPlayerById(id);
		return new ResponseEntity<Player>(player, HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Player>> getAllPlayers() {
		List<Player> list = playerService.getAllPlayers();
		return new ResponseEntity<List<Player>>(list, HttpStatus.OK);
	}
	
	@GetMapping(path="/myPlayers",produces = "application/json")
	public ResponseEntity<List<Player>> getMyPlayers() {
		List<Player> list = playerService.getMyPlayers();
		return new ResponseEntity<List<Player>>(list, HttpStatus.OK);
	}
	
	@GetMapping(path="/injured",produces = "application/json")
	public ResponseEntity<List<Player>> getInjuredPlayers() {
		List<Player> list = playerService.getInjuredPlayers();
		return new ResponseEntity<List<Player>>(list, HttpStatus.OK);
	}
	
	@PostMapping(produces = "application/json")
	public ResponseEntity<Void> addPlayer(@RequestBody PlayerDTO player) {
        boolean flag = playerService.addPlayer(player);
        if (flag == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	@PutMapping(produces = "application/json")
	public ResponseEntity<Void> updatePlayer(@RequestBody PlayerDTO player) {
		playerService.updatePlayer(player);
		return new ResponseEntity<Void>( HttpStatus.OK);
	}
	@DeleteMapping("player/{id}")
	public ResponseEntity<Void> deletePlayer(@PathVariable("id") Integer id) {
		playerService.deletePlayer(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
