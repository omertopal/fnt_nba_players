package com.nba.players.service;

import java.util.ArrayList;

import com.nba.players.bean.Player;

public interface IPlayerService {
    
    public ArrayList<Player> findAll();
}