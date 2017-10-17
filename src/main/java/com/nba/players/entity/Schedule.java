package com.nba.players.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="SCHEDULE")
@IdClass(SchedulePK.class)
public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Date gameDate;
	
	@Id
	private String team;
	
	@Column(name="MATCH")
	private String match;

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public Date getGameDate() {
		return gameDate;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}	
	
}
