package org.nba.players.service;

import java.util.List;

import org.nba.players.dao.IPlayerDAO;
import org.nba.players.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PlayerService implements IPlayerService {
	@Autowired
	private IPlayerDAO playerDAO;
	
	@Override
	public List<Player> getAllPlayers() {
		return playerDAO.getAllPlayers();
	}
	
	@Override
	public List<Player> getMyPlayers() {
		return playerDAO.getMyPlayers();
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

	public void evictCache() {
		// TODO Auto-generated method stub
		
	}

}
