package org.nba.players.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="SCHEDULE")
@IdClass(SchedulePK.class)
public class Schedule{

	@Id
	private Date gameDate;
	
	@Id
	@Column(name="HOME",length = 50)
	private String home;
	
	@Column(name="AWAY")
	private String away;

	public String getAway() {
		return away;
	}

	public void setAway(String away) {
		this.away = away;
	}

	public Date getGameDate() {
		return new Date(gameDate.getTime());
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = (Date) gameDate.clone();
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}	
	
}
