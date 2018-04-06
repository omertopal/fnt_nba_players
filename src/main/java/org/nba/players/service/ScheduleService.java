package org.nba.players.service;

import java.util.List;

import org.nba.players.dao.IScheduleDAO;
import org.nba.players.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService implements IScheduleService{
	@Autowired
	private IScheduleDAO scheduleDAO;

	@Override
	public List<Schedule> getAllSchedule() {
		return scheduleDAO.getAllSchedule();
	}
}
