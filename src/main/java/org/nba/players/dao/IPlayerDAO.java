package org.nba.players.dao;

import java.util.List;

import org.nba.players.dto.PlayerDTO;
import org.nba.players.entity.Player;

public interface IPlayerDAO {
	List<Player> getAllPlayers();
	public List<Player> getMyPlayers();	
	public List<Player> getInjuredPlayers();
	Player getPlayerById(int id);
	void addPlayer(PlayerDTO player);
    void updatePlayer(PlayerDTO player);
    void deletePlayer(int playerId);
    boolean playerExists(String name, String team);
}
