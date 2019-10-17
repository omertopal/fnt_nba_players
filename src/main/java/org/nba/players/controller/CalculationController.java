package org.nba.players.controller;

import java.util.ArrayList;
import java.util.List;

import org.nba.players.common.CommonUtils;
import org.nba.players.dto.CalculationIdDTO;
import org.nba.players.model.GameDateRosterModel;
import org.nba.players.service.ICalcService;
import org.nba.players.service.IPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calc")
public class CalculationController {
	
	@Autowired
	private IPermService permService;
	
	@Autowired
	private ICalcService calcService;
	
	@PostMapping(path="/fillPermutations/{size}",produces = "application/json")
	public ResponseEntity<Void> fillPermutations(@PathVariable(name="size",required=true) int size) {
	try{
		permService.generatePermutations(size);
	}
	catch(Exception e ){
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/calculationIdList")
	public ResponseEntity<List<CalculationIdDTO>>  calculationIdList(){
		return new ResponseEntity<List<CalculationIdDTO>>(calcService.calculationIdList(), HttpStatus.OK);
	}
	
	@PostMapping("/calcUsage/{method}")
	public ResponseEntity<List<GameDateRosterModel>> calcUsage(@PathVariable(name="method",required=true) String method) {
		
		if(StringUtils.isEmpty(method)){
			method = CommonUtils.STANDART_METHOD;
		}else {
			method = method.toUpperCase();
		}
		
		List<GameDateRosterModel> list = new ArrayList<GameDateRosterModel>();
		try {
			list = calcService.getGameDateRosters(method);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return new ResponseEntity<List<GameDateRosterModel>>(list, HttpStatus.OK);
	}	
	
	@GetMapping("/calculations/{calcId}")
	public ResponseEntity<List<GameDateRosterModel>> getAllGameDateRosters(@PathVariable(name="calcId",required=true) Integer calcId) throws Exception {
		List<GameDateRosterModel> list = calcService.getAllGameDateRosters(calcId);		
		return new ResponseEntity<List<GameDateRosterModel>>(list, HttpStatus.OK);
	}
}
