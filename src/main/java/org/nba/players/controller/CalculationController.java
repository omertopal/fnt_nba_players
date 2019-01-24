package org.nba.players.controller;

import java.util.ArrayList;
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
public class CalculationController {
	@Autowired
	private IPermService permService;
	
	
	@GetMapping("calcUsage")
	public ResponseEntity<List<GameDateRosterModel>> calcUsage() {
		List<GameDateRosterModel> list = new ArrayList<GameDateRosterModel>();
		try {
			list = permService.getGameDateRosters();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return new ResponseEntity<List<GameDateRosterModel>>(list, HttpStatus.OK);
	}	
}
