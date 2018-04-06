package com.nba.players.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nba.players.dao.IScheduleDAO;
import com.nba.players.entity.Schedule;

@Service
public class ScheduleService implements IScheduleService{
	@Autowired
	private IScheduleDAO scheduleDAO;

	@Override
	public List<Schedule> getAllSchedule() {
		return scheduleDAO.getAllSchedule();
	}
}
