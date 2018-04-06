package org.nba.players.intf;

import java.util.Date;

import org.nba.players.entity.Schedule;

@FunctionalInterface
public interface CompareScheduleDate {	
	
	public boolean compare (Schedule schedule, Date date);
}
