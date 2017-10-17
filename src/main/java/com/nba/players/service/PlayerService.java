package com.nba.players.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nba.players.dao.IPlayerDAO;
import com.nba.players.entity.Player;


@Service
public class PlayerService implements IPlayerService {
	@Autowired
	private IPlayerDAO playerDAO;
	
	@Override
	public List<Player> getAllPlayers() {
		return playerDAO.getAllPlayers();
	}

	@Override
	public Player getPlayerById(int id) {
		Player obj = playerDAO.getPlayerById(id);
		return obj;
	}
	
	@Override
	public synchronized boolean addPlayer(Player player){
        if (playerDAO.playerExists(player.getName(), player.getTeam())) {
            return false;
        } else {
            playerDAO.addPlayer(player);
            return true;
        }
	}
	@Override
	public void updatePlayer(Player player) {
		playerDAO.updatePlayer(player);
	}
	@Override
	public void deletePlayer(int playerId) {
		playerDAO.deletePlayer(playerId);
	}

}
