package com.nba.players.service;

import java.util.List;

import com.nba.players.entity.MyPlayers;
import com.nba.players.entity.Player;

public interface IPlayerService {
	List<Player> getAllPlayers();
	List<MyPlayers> getMyPlayers();
	Player getPlayerById(int id);
	boolean addPlayer(Player player);
    void updatePlayer(Player player);
    void deletePlayer(int playerId);
}
