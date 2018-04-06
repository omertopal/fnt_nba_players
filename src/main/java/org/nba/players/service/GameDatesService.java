package com.nba.players.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nba.players.dao.IGameDatesDAO;
import com.nba.players.entity.GameDates;

@Service
public class GameDatesService implements IGameDatesService {
	@Autowired
	private IGameDatesDAO gameDatesDAO;
	
	@Override
	public List<GameDates> getGameDates() {
		return gameDatesDAO.getGameDates();
	}

}
