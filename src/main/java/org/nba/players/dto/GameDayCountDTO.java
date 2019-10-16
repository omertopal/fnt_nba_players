package org.nba.players.dto;

import java.sql.Date;

public class GameDayCountDTO {
	
	private Date gameDate;
	
	private int count;

	public GameDayCountDTO(Date gameDate, int count) {
		super();
		this.gameDate = gameDate;
		this.count = count;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
