package org.nba.players.controller;

import java.util.ArrayList;
import java.util.List;

import org.nba.players.common.CommonUtils;
import org.nba.players.entity.Team;
import org.nba.players.model.GameDateRosterModel;
import org.nba.players.service.IPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calc")
public class CalculationController {
	@Autowired
	private IPermService permService;
	
	@GetMapping("/calcUsage/{method}")
	public ResponseEntity<List<GameDateRosterModel>> calcUsage(@PathVariable(name="method",required=true) String method) {
		
		if(StringUtils.isEmpty(method)){
			method = CommonUtils.STANDART_METHOD;
		}else {
			method = method.toUpperCase();
		}
		
		List<GameDateRosterModel> list = new ArrayList<GameDateRosterModel>();
		try {
			list = permService.getGameDateRosters(method);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return new ResponseEntity<List<GameDateRosterModel>>(list, HttpStatus.OK);
	}	
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<GameDateRosterModel>> getAllGameDateRosters() throws Exception {
		List<GameDateRosterModel> list = permService.getAllGameDateRosters();		
		return new ResponseEntity<List<GameDateRosterModel>>(list, HttpStatus.OK);
	}
}
