package org.nba.players.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="GAME_DATE_ROSTERS")
public class GameDateRosters implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GAME_DATE_ROSTERS_SEQ")
	@SequenceGenerator(name="GAME_DATE_ROSTERS_SEQ", sequenceName="GAME_DATE_ROSTERS_SEQ", allocationSize=1)
	@Column(name="id")
    private int id;
	
	@Column(name="GAME_DATE")
	private Date gameDate;
	
	@Column(name="ACTIVE_PLAYERS_COUNT")
    private int activePlayersCount;  
	
	@Column(name="PERM_ID")
    private int permId;  
	
	@Column(name="RUN_TIME")
	private Timestamp runTime; 
	
	@Column(name="TOTAL_PTS")
	private Double totalPts; 
	
	@Column(name="pg")
    private int pg;  
	
	@Column(name="sg")
    private int sg;  
	
	@Column(name="sf")
    private int sf;  
	
	@Column(name="pf")
    private int pf;  
	
	@Column(name="c")
    private int c;  
	
	@Column(name="ut")
    private int ut;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getGameDate() {
		return new Date(gameDate.getTime());
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = (Date) gameDate.clone();
	}

	public int getActivePlayersCount() {
		return activePlayersCount;
	}

	public void setActivePlayersCount(int activePlayersCount) {
		this.activePlayersCount = activePlayersCount;
	}

	public int getPermId() {
		return permId;
	}

	public void setPermId(int permId) {
		this.permId = permId;
	}

	public Timestamp getRunTime() {
		return new Timestamp(runTime.getTime());
	}

	public void setRunTime(Timestamp runTime) {
		this.runTime = (Timestamp) runTime.clone();
	}

	public Double getTotalPts() {
		return totalPts;
	}

	public void setTotalPts(Double totalPts) {
		this.totalPts = totalPts;
	}

	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
		this.pg = pg;
	}

	public int getSg() {
		return sg;
	}

	public void setSg(int sg) {
		this.sg = sg;
	}

	public int getSf() {
		return sf;
	}

	public void setSf(int sf) {
		this.sf = sf;
	}

	public int getPf() {
		return pf;
	}

	public void setPf(int pf) {
		this.pf = pf;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getUt() {
		return ut;
	}

	public void setUt(int ut) {
		this.ut = ut;
	}
}
