package org.nba.players.service;

import java.util.List;

import org.nba.players.dto.PlayerDTO;
import org.nba.players.entity.Player;

public interface IPlayerService {
	List<Player> getAllPlayers();
	List<Player> getMyPlayers();
	Player getPlayerById(int id);
	boolean addPlayer(PlayerDTO player);
    void updatePlayer(PlayerDTO player);
    void deletePlayer(int playerId);
}
