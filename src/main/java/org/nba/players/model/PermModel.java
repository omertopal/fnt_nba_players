package org.nba.players.model;

public class PermModel {
	public PermModel() {
		
	}
	public PermModel(int pg, int sg, int sf, int pf, int c, int ut) {
		this.pg = pg;
		this.sg = sg;
		this.sf = sf;
		this.pf = pf;
		this.c = c;
		this.ut = ut;
	}
	
    private int pg;  
	
    private int sg;  
	
    private int sf;  
	
    private int pf;  
	
    private int c;  
	
    private int ut;
    
    private int id;

	public int getPg() {
		return pg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
