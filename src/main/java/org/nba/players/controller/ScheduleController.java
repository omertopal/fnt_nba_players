package org.nba.players.controller;

import java.util.List;

import org.nba.players.entity.Schedule;
import org.nba.players.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("schedule")
public class ScheduleController {
	@Autowired
	private IScheduleService scheduleService;
	
	@GetMapping("allSchedule")
	public ResponseEntity<List<Schedule>> getAllSchedule() {
		List<Schedule> list = scheduleService.getAllSchedule();
		return new ResponseEntity<List<Schedule>>(list, HttpStatus.OK);
	}
}
