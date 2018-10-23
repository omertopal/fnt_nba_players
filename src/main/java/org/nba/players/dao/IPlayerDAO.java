package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.Player;

public interface IPlayerDAO {
	List<Player> getAllPlayers();
	public List<Player> getMyPlayers();	
	Player getPlayerById(int id);
	void addPlayer(Player player);
    void updatePlayer(Player player);
    void deletePlayer(int playerId);
    boolean playerExists(String title, String category);
}
