package org.nba.players.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TEAMS")
public class Team{	
	
	@Id
	@Column(name="CODE",length = 50)
	private String code;
	
	@Column(name="NAME")
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
