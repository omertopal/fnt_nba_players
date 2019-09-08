package org.nba.players.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="GAME_DATE_ROSTERS")
public class GameDateRosters extends BaseEntity {
	
	@Column(name="GAME_DATE")
	private Date gameDate;
	
	@Column(name="ACTIVE_PLAYERS_COUNT")
    private int activePlayersCount;  
	
	@Column(name="PERM_ID")
    private int permId;  
	
	@Column(name="CALC_ID")
    private int calcId;  
	
	@Column(name="RUN_TIME")
	private Timestamp runTime; 
	
	@Column(name="TOTAL_PTS")
	private Double totalPts; 
	
	@OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="gameDateRosterId", orphanRemoval = true)
    private List<GameDateRostersEq> equivalentList = new ArrayList<>();
	
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
	
	public void addChild(GameDateRostersEq equivalentEntity) {
		equivalentList.add(equivalentEntity);
		equivalentEntity.setGameDateRosterId(this);
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

	public int getCalcId() {
		return calcId;
	}

	public void setCalcId(int calcId) {
		this.calcId = calcId;
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

	public List<GameDateRostersEq> getEquivalentList() {
		return equivalentList;
	}

	public void setEquivalentList(List<GameDateRostersEq> equivalentList) {
		this.equivalentList = equivalentList;
	}
}
