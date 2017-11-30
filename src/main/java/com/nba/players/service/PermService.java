package com.nba.players.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nba.players.dao.IGameDatesDAO;
import com.nba.players.dao.IPERM_6_10DAO;
import com.nba.players.dao.IPERM_6_11DAO;
import com.nba.players.dao.IPERM_6_12DAO;
import com.nba.players.dao.IPERM_6_1DAO;
import com.nba.players.dao.IPERM_6_2DAO;
import com.nba.players.dao.IPERM_6_3DAO;
import com.nba.players.dao.IPERM_6_4DAO;
import com.nba.players.dao.IPERM_6_5DAO;
import com.nba.players.dao.IPERM_6_6DAO;
import com.nba.players.dao.IPERM_6_7DAO;
import com.nba.players.dao.IPERM_6_8DAO;
import com.nba.players.dao.IPERM_6_9DAO;
import com.nba.players.dao.IPlayerDAO;
import com.nba.players.dao.IScheduleDAO;
import com.nba.players.entity.GameDates;
import com.nba.players.entity.MyPlayers;
import com.nba.players.entity.Schedule;
import com.nba.players.model.GameDateRosterModel;
import com.nba.players.model.MyPlayersModel;
import com.nba.players.model.PermModel;

@Service
public class PermService implements IPermService {
	
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
	
	@Autowired
	private IScheduleDAO scheduleDAO;
	
	@Autowired
	private IGameDatesDAO gameDatesDAO;
	
	@Autowired
	private IPlayerDAO playerDAO;
	
	@Override
	public List<PermModel> getAllPERM_6_7() {
		return perm6_7DAO.getAllPERM_6_7();
	}

	@Override
	public List<GameDateRosterModel> getGameDateRosters() {
		List<GameDateRosterModel> gamedateRoster = new ArrayList<GameDateRosterModel>();				
		
		List<Schedule> schedule = scheduleDAO.getAllSchedule();
		
		List<GameDates> gameDates = gameDatesDAO.getGameDates();
		
		List<MyPlayers> myPlayers = playerDAO.getMyPlayers();
		
		List<MyPlayersModel> myPlayersToday = new ArrayList<>();
		
		List<PermModel> permutations  = new ArrayList<>();
		
		for (GameDates currGameDate : gameDates) {
			myPlayersToday.clear();
			int playerOrder = 1;
			
			for (MyPlayers currPlayer : myPlayers) {
				if(currPlayer.getInjEnd()==null || currPlayer.getInjEnd().before(currGameDate.getGameDate())){
					List<Schedule> filteredSchedule= schedule.stream().filter(scheduleIns -> scheduleIns.getGameDate().equals(currGameDate.getGameDate()))
						.filter(scheduleIns -> scheduleIns.getMatch()!=null)					
						.filter(scheduleIns -> scheduleIns.getTeam().equals(currPlayer.getTeam())).collect(Collectors.toList());
					if (filteredSchedule.size()> 0){
						MyPlayersModel myPly = new MyPlayersModel();
						myPly.setOrder(playerOrder);
						myPly.setId(currPlayer.getId());
						myPly.setTeam(currPlayer.getTeam());
						myPly.setName(currPlayer.getName());
						myPly.setAvgPts(currPlayer.getAvgPts());
						myPly.setIsPG(currPlayer.getIsPG());
						myPly.setIsSG(currPlayer.getIsSG());
						myPly.setIsSF(currPlayer.getIsSF());
						myPly.setIsPF(currPlayer.getIsPF());						
						myPly.setIsC(currPlayer.getIsC());
						myPlayersToday.add(myPly);
						playerOrder ++;
					}
				}
			}	
			if(myPlayersToday.size()==1){
				permutations  = perm6_1DAO.getAllPERM_6_1();				
			}else if(myPlayersToday.size()==2){
				permutations  = perm6_2DAO.getAllPERM_6_2();				
			}else if(myPlayersToday.size()==3){
				permutations  = perm6_3DAO.getAllPERM_6_3();				
			}else if(myPlayersToday.size()==4){
				permutations  = perm6_4DAO.getAllPERM_6_4();				
			}else if(myPlayersToday.size()==5){
				permutations  = perm6_5DAO.getAllPERM_6_5();				
			}else if(myPlayersToday.size()==6){
				permutations  = perm6_6DAO.getAllPERM_6_6();				
			}else if(myPlayersToday.size()==7){
				permutations  = perm6_7DAO.getAllPERM_6_7();				
			}else if(myPlayersToday.size()==8){
				permutations  = perm6_8DAO.getAllPERM_6_8();				
			}else if(myPlayersToday.size()==9){
				permutations  = perm6_9DAO.getAllPERM_6_9();				
			}else if(myPlayersToday.size()==10){
				permutations  = perm6_10DAO.getAllPERM_6_10();				
			}else if(myPlayersToday.size()==11){
				permutations  = perm6_11DAO.getAllPERM_6_11();				
			}else if(myPlayersToday.size()==12){
				permutations  = perm6_12DAO.getAllPERM_6_12();				
			}
			
			gamedateRoster.add(fillGameDateRoster(permutations, currGameDate, myPlayersToday));
			permutations.clear();
			myPlayersToday.clear();
		}
		
		return gamedateRoster;
		
	}
	
	public Double maxPointsToday (List<MyPlayersModel> myPlayersToday){
		Double maxPointsToday = 0.0;
		int count = 0;
		int maxCount = 6;
		for(MyPlayersModel player : myPlayersToday){
			if (count >= maxCount) 
				return maxPointsToday;
			maxPointsToday += player.getAvgPts();
			count ++;
		}
		return maxPointsToday;
	}
	
	public GameDateRosterModel fillGameDateRoster (List<PermModel> permutations,GameDates currGameDate,List<MyPlayersModel> myPlayersToday){
		GameDateRosterModel currGameDateRoster = new GameDateRosterModel();
		currGameDateRoster.setGameDate(currGameDate.getGameDate());
		currGameDateRoster.setTotalPts(new Double(0));
		Double totalPts = new Double(0);
		Double maxPointsToday = maxPointsToday(myPlayersToday);
		for(PermModel perm : permutations){						
			Double currTotalPts = 0.0;
			List<MyPlayersModel> currPG = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getPg()).collect(Collectors.toList());
			if(currPG != null && currPG.size()>0 && currPG.get(0).getIsPG()==1) currTotalPts += currPG.get(0).getAvgPts();
			
			List<MyPlayersModel>  currSG = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getSg()).collect(Collectors.toList());
			if(currSG != null && currSG.size()>0 && currSG.get(0).getIsSG()==1) currTotalPts += currSG.get(0).getAvgPts();
			
			List<MyPlayersModel>  currSF = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getSf()).collect(Collectors.toList());
			if(currSF != null && currSF.size()>0 && currSF.get(0).getIsSF()==1) currTotalPts += currSF.get(0).getAvgPts();
			
			List<MyPlayersModel>  currPF = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getPf()).collect(Collectors.toList());
			if(currPF != null && currPF.size()>0 && currPF.get(0).getIsPF()==1) currTotalPts += currPF.get(0).getAvgPts();
			
			List<MyPlayersModel>  currC = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getC()).collect(Collectors.toList());
			if(currC != null && currC.size()>0 && currC.get(0).getIsC()==1) currTotalPts += currC.get(0).getAvgPts();
			
			List<MyPlayersModel>  currUT = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getUt()).collect(Collectors.toList());
			if(currUT != null && currUT.size()>0 ) currTotalPts += currUT.get(0).getAvgPts();
			
			if(currTotalPts > totalPts){						
				totalPts = currTotalPts;
				if(currPG != null && currPG.size()>0) currGameDateRoster.setPg(currPG.get(0).getId());
				if(currSG != null && currSG.size()>0) currGameDateRoster.setSg(currSG.get(0).getId());
				if(currSF != null && currSF.size()>0) currGameDateRoster.setSf(currSF.get(0).getId());
				if(currPF != null && currPF.size()>0) currGameDateRoster.setPf(currPF.get(0).getId());
				if(currC != null && currC.size()>0) currGameDateRoster.setC(currC.get(0).getId());
				if(currUT != null && currUT.size()>0) currGameDateRoster.setUt(currUT.get(0).getId());
				currGameDateRoster.setPermId(perm.getId());
				currGameDateRoster.setActivePlayersCount(myPlayersToday.size());
				currGameDateRoster.setTotalPts(currTotalPts);
			}
			if(currTotalPts >= maxPointsToday) return currGameDateRoster;
		}
		return currGameDateRoster;
	}
	
	public  List<List<MyPlayers>> generatePerm(List<MyPlayers> original) {
	     if (original.size() == 0) {
	       List<List<MyPlayers>> result = new ArrayList<List<MyPlayers>>(); 
	       result.add(new ArrayList<MyPlayers>()); 
	       return result; 
	     }
	     MyPlayers firstElement = original.remove(0);
	     List<List<MyPlayers>> returnValue = new ArrayList<List<MyPlayers>>();
	     List<List<MyPlayers>> permutations = generatePerm(original);
	     for (List<MyPlayers> smallerPermutated : permutations) {
	       for (int index=0; index <= smallerPermutated.size(); index++) {
	         List<MyPlayers> temp = new ArrayList<MyPlayers>(smallerPermutated);
	         temp.add(index, firstElement);
	         returnValue.add(temp);
	       }
	     }
	     return returnValue;
	   }
	
	boolean best6HasGame(Date gameDate,List<MyPlayers> players,List<Schedule> schedule){		
		List<Schedule> filteredSchedule= schedule.stream().filter(scheduleIns -> scheduleIns.getGameDate().equals(gameDate))
														  .filter(scheduleIns -> scheduleIns.getMatch()!=null).collect(Collectors.toList());
		
		boolean playerHasGame;
		
		for(int i=0; i<6;i++){
			
			playerHasGame = false;
			MyPlayers currPlayer = players.get(i);			
			for (Schedule currSchedule : filteredSchedule) {
				if(currSchedule.getTeam().equals(currPlayer.getTeam())){
					playerHasGame = true;
					break;
				}
			}
			if(!playerHasGame) return false;
		}
			
		return true;	
	}

}
