package org.nba.players.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embeddable;

@Embeddable
public class SchedulePK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Date gameDate;
    protected String home;

    public SchedulePK() {}

    public SchedulePK(Date gameDate, String home) {
        this.gameDate = (Date) gameDate.clone();
        this.home = home;
    }

	public Date getGameDate() {
		return new Date ( gameDate.getTime());
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = (Date)gameDate.clone();
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gameDate == null) ? 0 : gameDate.hashCode());
		result = prime * result + ((home == null) ? 0 : home.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchedulePK other = (SchedulePK) obj;
		if (gameDate == null) {
			if (other.gameDate != null)
				return false;
		} else if (!gameDate.equals(other.gameDate))
			return false;
		if (home == null) {
			if (other.home != null)
				return false;
		} else if (!home.equals(other.home))
			return false;
		return true;
	}
}