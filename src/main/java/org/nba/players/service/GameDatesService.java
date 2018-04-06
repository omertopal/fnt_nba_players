package org.nba.players.service;

import java.util.List;

import org.nba.players.dao.IGameDatesDAO;
import org.nba.players.entity.GameDates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameDatesService implements IGameDatesService {
	@Autowired
	private IGameDatesDAO gameDatesDAO;
	
	@Override
	public List<GameDates> getGameDates() {
		return gameDatesDAO.getGameDates();
	}

}
