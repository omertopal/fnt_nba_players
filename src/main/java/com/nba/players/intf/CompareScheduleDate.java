package com.nba.players.intf;

import java.util.Date;

import com.nba.players.entity.Schedule;

@FunctionalInterface
public interface CompareScheduleDate {	
	
	public boolean compare (Schedule schedule, Date date);
}
