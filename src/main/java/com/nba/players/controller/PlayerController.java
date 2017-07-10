package com.nba.players.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nba.players.bean.Player;
import com.nba.players.service.IPlayerService;

@RestController
public class PlayerController {

    @Autowired
    private IPlayerService playerService;

    @RequestMapping("/getPlayers")
    public List<Player> listCountries() {
        
        return playerService.findAll();
    }    
}