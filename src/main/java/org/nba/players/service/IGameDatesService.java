package org.nba.players.service;

import java.util.List;

import org.nba.players.entity.GameDates;

public interface IGameDatesService {
	List<GameDates> getGameDates();
}
