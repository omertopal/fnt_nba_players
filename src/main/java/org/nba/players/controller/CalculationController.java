package org.nba.players.controller;

import java.util.List;

import org.nba.players.model.GameDateRosterModel;
import org.nba.players.service.IPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("calc")
public class CalcController {
	@Autowired
	private IPermService permService;
	
	
	@GetMapping("calcUsage")
	public ResponseEntity<List<GameDateRosterModel>> calcUsage() {
		List<GameDateRosterModel> list = permService.getGameDateRosters();
		return new ResponseEntity<List<GameDateRosterModel>>(list, HttpStatus.OK);
	}	
}
