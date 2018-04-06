package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.GameDates;

public interface IGameDatesDAO {
	List<GameDates> getGameDates();
}
