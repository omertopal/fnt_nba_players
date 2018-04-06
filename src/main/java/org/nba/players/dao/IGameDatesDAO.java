package com.nba.players.dao;

import java.util.List;

import com.nba.players.entity.GameDates;

public interface IGameDatesDAO {
	List<GameDates> getGameDates();
}
