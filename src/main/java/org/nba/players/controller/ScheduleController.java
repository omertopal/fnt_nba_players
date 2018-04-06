package com.nba.players.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nba.players.entity.Schedule;
import com.nba.players.service.IScheduleService;

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
