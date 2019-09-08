package org.nba.players.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="PLAYERS")
@NamedNativeQuery(name="getMyAllPlayers", query = "select * from players where is_my = 1 or is_potential = 1 order by avg_pts ", resultClass = Player.class)
public class Player extends BaseEntity {	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "TEAM", nullable = false)
	private Team team;
	
	@Column(name="name")	
	private String name;
	
	@Column(name="AVG_PTS")	
	private Double avgPts;
	
	@Column(name="SIRA")	
	private int sira;
	
	@Column(name="IS_PG")	
	private int isPG;
	
	@Column(name="IS_SG")	
	private int isSG;
	
	@Column(name="IS_SF")	
	private int isSF;
	
	@Column(name="IS_PF")	
	private int isPF;
	
	@Column(name="IS_C")	
	private int isC;
	
	@Column(name="IS_POTENTIAL")	
	private int isPotential;
	
	@Column(name="IS_MY")	
	private int isMy;
	
	@Column(name="INJ_END")	
	private Date injEnd;
	
	@Column(name="GAMES_PLAYED")	
	private int gamesPlayed;

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAvgPts() {
		return avgPts;
	}

	public void setAvgPts(Double avgPts) {
		this.avgPts = avgPts;
	}

	public int getSira() {
		return sira;
	}

	public void setSira(int sira) {
		this.sira = sira;
	}

	public int getIsPG() {
		return isPG;
	}

	public void setIsPG(int isPG) {
		this.isPG = isPG;
	}

	public int getIsSG() {
		return isSG;
	}

	public void setIsSG(int isSG) {
		this.isSG = isSG;
	}

	public int getIsSF() {
		return isSF;
	}

	public void setIsSF(int isSF) {
		this.isSF = isSF;
	}

	public int getIsPF() {
		return isPF;
	}

	public void setIsPF(int isPF) {
		this.isPF = isPF;
	}

	public int getIsC() {
		return isC;
	}

	public void setIsC(int isC) {
		this.isC = isC;
	}

	public int getIsPotential() {
		return isPotential;
	}

	public void setIsPotential(int isPotential) {
		this.isPotential = isPotential;
	}

	public int getIsMy() {
		return isMy;
	}

	public void setIsMy(int isMy) {
		this.isMy = isMy;
	}

	public Date getInjEnd() {
		return injEnd;
	}

	public void setInjEnd(Date injEnd) {
		this.injEnd = injEnd;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

}
