package org.nba.players.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SEQ_GAME_DATE_ROSTERS")
public class SeqGameDateRosters {
	
	public SeqGameDateRosters(int calcId) {
		this.calcId = calcId;
	}
	
	@Id
	private int calcId;
}
