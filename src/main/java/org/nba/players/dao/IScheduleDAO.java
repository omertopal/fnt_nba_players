package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.Schedule;

public interface IScheduleDAO {
	List<Schedule> getAllSchedule();
}
