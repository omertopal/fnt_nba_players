package org.nba.players.service;

import java.util.List;

import org.nba.players.dao.IPERM_6_10DAO;
import org.nba.players.dao.IPERM_6_11DAO;
import org.nba.players.dao.IPERM_6_12DAO;
import org.nba.players.dao.IPERM_6_1DAO;
import org.nba.players.dao.IPERM_6_2DAO;
import org.nba.players.dao.IPERM_6_3DAO;
import org.nba.players.dao.IPERM_6_4DAO;
import org.nba.players.dao.IPERM_6_5DAO;
import org.nba.players.dao.IPERM_6_6DAO;
import org.nba.players.dao.IPERM_6_7DAO;
import org.nba.players.dao.IPERM_6_8DAO;
import org.nba.players.dao.IPERM_6_9DAO;
import org.nba.players.model.PermModel;
import org.nba.players.model.PlayerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermutationService {
	
	@Autowired
	private IPERM_6_1DAO perm6_1DAO;
	@Autowired
	private IPERM_6_2DAO perm6_2DAO;
	@Autowired
	private IPERM_6_3DAO perm6_3DAO;
	@Autowired
	private IPERM_6_4DAO perm6_4DAO;
	@Autowired
	private IPERM_6_5DAO perm6_5DAO;
	@Autowired
	private IPERM_6_6DAO perm6_6DAO;
	@Autowired
	private IPERM_6_7DAO perm6_7DAO;
	@Autowired
	private IPERM_6_8DAO perm6_8DAO;
	@Autowired
	private IPERM_6_9DAO perm6_9DAO;
	@Autowired
	private IPERM_6_10DAO perm6_10DAO;
	@Autowired
	private IPERM_6_11DAO perm6_11DAO;
	@Autowired
	private IPERM_6_12DAO perm6_12DAO;
	
	public List<PermModel>  getPermutations (List<PlayerModel> myPlayersToday){
		if(myPlayersToday.size()==1){
			return perm6_1DAO.getAllPerm();				
		}else if(myPlayersToday.size()==2){
			return perm6_2DAO.getAllPerm();				
		}else if(myPlayersToday.size()==3){
			return perm6_3DAO.getAllPerm();				
		}else if(myPlayersToday.size()==4){
			return perm6_4DAO.getAllPerm();				
		}else if(myPlayersToday.size()==5){
			return perm6_5DAO.getAllPerm();				
		}else if(myPlayersToday.size()==6){
			return perm6_6DAO.getAllPerm();				
		}else if(myPlayersToday.size()==7){
			return perm6_7DAO.getAllPerm();				
		}else if(myPlayersToday.size()==8){
			return perm6_8DAO.getAllPerm();				
		}else if(myPlayersToday.size()==9){
			return perm6_9DAO.getAllPerm();				
		}else if(myPlayersToday.size()==10){
			return perm6_10DAO.getAllPerm();				
		}else if(myPlayersToday.size()==11){
			return perm6_11DAO.getAllPerm();				
		}else if(myPlayersToday.size()==12){
			return perm6_12DAO.getAllPerm();				
		}else{
			return null;
		}			
	}
}
