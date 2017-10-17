package com.nba.players.dao;

import java.util.List;

import com.nba.players.entity.Player;

public interface IPlayerDAO {
	List<Player> getAllPlayers();
	public List<Player> getMyPlayers();	
	Player getPlayerById(int id);
	void addPlayer(Player player);
    void updatePlayer(Player player);
    void deletePlayer(int playerId);
    boolean playerExists(String title, String category);
}
