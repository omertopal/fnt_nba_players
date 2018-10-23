package org.nba.players.service;

import java.util.List;

import org.nba.players.entity.Player;

public interface IPlayerService {
	List<Player> getAllPlayers();
	List<Player> getMyPlayers();
	Player getPlayerById(int id);
	boolean addPlayer(Player player);
    void updatePlayer(Player player);
    void deletePlayer(int playerId);
}
