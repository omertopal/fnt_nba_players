package org.nba.players.model;

import java.sql.Date;
import java.util.List;

public class GameDateRosterModel {	
	
	private Date gameDate;
	
    private int pg;  
	
    private int sg;  
	
    private int sf;  
	
    private int pf;  
	
    private int c;  
	
    private int ut;
    
    private int permId;
    
    private int calcId;
    
    private int activePlayersCount;
    
    private Double totalPts;    
    
    private List<GameDateRosterEqModel> equivalentPermutations;

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

	public Double getTotalPts() {
		return totalPts;
	}

	public void setTotalPts(Double totalPts) {
		this.totalPts = totalPts;
	}

	public List<GameDateRosterEqModel> getEquivalentPermutations() {
		return equivalentPermutations;
	}

	public void setEquivalentPermutations(List<GameDateRosterEqModel> equivalentPermutations) {
		this.equivalentPermutations = equivalentPermutations;
	}

	
}
