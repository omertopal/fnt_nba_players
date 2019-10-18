package org.nba.players.model;

public class GameDateRosterEqModel {	
	
	public GameDateRosterEqModel () {
		
	}
	
	private int gameDateRosterId;
	
	private int equivalentPermId;
	
	private int pg;  
	
    private int sg;  
	
    private int sf;  
	
    private int pf;  
	
    private int c;  
	
    private int ut;

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

	public int getGameDateRosterId() {
		return gameDateRosterId;
	}

	public GameDateRosterEqModel(int equivalentPermId) {
		super();
		this.equivalentPermId = equivalentPermId;
	}

	public int getEquivalentPermId() {
		return equivalentPermId;
	}

	public void setGameDateRosterId(int gameDateRosterId) {
		this.gameDateRosterId = gameDateRosterId;
	}

	public void setEquivalentPermId(int equivalentPermId) {
		this.equivalentPermId = equivalentPermId;
	}
}
