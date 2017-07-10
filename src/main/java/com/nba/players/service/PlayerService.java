package com.nba.players.service;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.nba.players.bean.Player;

@Service
public class PlayerService implements IPlayerService {

    private final ArrayList<Player> players;

	public PlayerService() {

		players = new ArrayList<Player>();
    }

    @Override
    public ArrayList<Player> findAll() {
  
        try {        	 
        	players.clear();
        	Player player = new Player("Ommer" );        	
        	players.add(player);        	
            
        }catch (Exception ex) {
            Logger.getLogger(PlayerService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return players;
    }
}