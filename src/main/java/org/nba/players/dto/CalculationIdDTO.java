package org.nba.players.dto;

import java.sql.Timestamp;

public class CalculationIdDTO {
	
	public CalculationIdDTO (int calcId,Timestamp runTime) {
		this.calcId =calcId;
		this.runTime =runTime;
	}
	
	private int calcId;
	private Timestamp runTime;
	
	public int getCalcId() {
		return calcId;
	}
	public void setCalcId(int calcId) {
		this.calcId = calcId;
	}
	public Timestamp getRunTime() {
		return runTime;
	}
	public void setRunTime(Timestamp runTime) {
		this.runTime = runTime;
	}
}
