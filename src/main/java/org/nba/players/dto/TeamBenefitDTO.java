package org.nba.players.dto;

public class TeamBenefitDTO {

	public String team;
	
	public int pgCount;
	
	public int sgCount;
	
	public int sfCount;
	
	public int pfCount;
	
	public int cCount;
	
	public int utCount;
	
	public TeamBenefitDTO() {
		
	}

	public TeamBenefitDTO(String team, int pgCount, int sgCount, int sfCount, int pfCount, int cCount, int utCount) {
		super();
		this.team = team;
		this.pgCount = pgCount;
		this.sgCount = sgCount;
		this.sfCount = sfCount;
		this.pfCount = pfCount;
		this.cCount = cCount;
		this.utCount = utCount;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public int getPgCount() {
		return pgCount;
	}

	public void setPgCount(int pgCount) {
		this.pgCount = pgCount;
	}

	public int getSgCount() {
		return sgCount;
	}

	public void setSgCount(int sgCount) {
		this.sgCount = sgCount;
	}

	public int getSfCount() {
		return sfCount;
	}

	public void setSfCount(int sfCount) {
		this.sfCount = sfCount;
	}

	public int getPfCount() {
		return pfCount;
	}

	public void setPfCount(int pfCount) {
		this.pfCount = pfCount;
	}

	public int getcCount() {
		return cCount;
	}

	public void setcCount(int cCount) {
		this.cCount = cCount;
	}

	public int getUtCount() {
		return utCount;
	}

	public void setUtCount(int utCount) {
		this.utCount = utCount;
	}
	
}
