package org.nba.players.controller;

import java.util.ArrayList;
import java.util.List;

import org.nba.players.common.CommonUtils;
import org.nba.players.dto.CalcUsageResult;
import org.nba.players.dto.CalculationIdDTO;
import org.nba.players.dto.TeamBenefitDTO;
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
	
	@GetMapping("/teamBenefitList/{calcId}/{days}")
	public ResponseEntity<List<TeamBenefitDTO>>  getTeamBenefitList(@PathVariable(name="calcId",required=true) Integer calcId,
																	@PathVariable(name="days",required=true) Integer days){
		return new ResponseEntity<List<TeamBenefitDTO>>(calcService.getTeamBenefitList(calcId,days), HttpStatus.OK);
	}
	
	@PostMapping("/calcUsage/{method}")
	public ResponseEntity<CalcUsageResult> calcUsage(@PathVariable(name="method",required=true) String method) {
		
		if(StringUtils.isEmpty(method)){
			method = CommonUtils.STANDART_METHOD;
		}else {
			method = method.toUpperCase();
		}
		CalcUsageResult result = new CalcUsageResult();
		try {
			result = calcService.getGameDateRosters(method);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return new ResponseEntity<CalcUsageResult>(result, HttpStatus.OK);
	}	
	
	@GetMapping("/calculations/{calcId}/{days}")
	public ResponseEntity<CalcUsageResult> getAllGameDateRosters(@PathVariable(name="calcId",required=true) Integer calcId,
																 @PathVariable(name="days",required=true) Integer days) throws Exception {
		CalcUsageResult result = calcService.getAllGameDateRosters(calcId,days);		
		return new ResponseEntity<CalcUsageResult>(result, HttpStatus.OK);
	}
}
