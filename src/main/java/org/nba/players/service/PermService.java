package org.nba.players.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.nba.players.dao.IGameDateRostersDAO;
import org.nba.players.dao.IGameDatesDAO;
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
import org.nba.players.dao.IPlayerDAO;
import org.nba.players.dao.IScheduleDAO;
import org.nba.players.entity.GameDates;
import org.nba.players.entity.MyPlayers;
import org.nba.players.entity.Schedule;
import org.nba.players.model.GameDateRosterModel;
import org.nba.players.model.MyPlayersModel;
import org.nba.players.model.PermModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private IGameDateRostersDAO gameDateRostersDAO;	
	
	@Autowired
	private IPlayerDAO playerDAO;	
	
	@Override
	public List<GameDateRosterModel> getGameDateRosters() {
		List<GameDateRosterModel> gamedateRoster = new ArrayList<GameDateRosterModel>();				
		
		List<Schedule> schedule = scheduleDAO.getAllSchedule();
		
		List<GameDates> gameDates = gameDatesDAO.getGameDates();
		
		List<MyPlayers> myPlayers = playerDAO.getMyPlayers();
		
		List<MyPlayersModel> myPlayersToday = new ArrayList<>();
		
		
		for (GameDates currGameDate : gameDates) {			
			myPlayersToday.clear();
			int playerOrder = 1;
			 
			/*try {
				if (!currGameDate.getGameDate().equals(new SimpleDateFormat("yyyy-MM-dd").parse("2018-02-07"))) continue;
			} catch (ParseException e) {
				e.printStackTrace();
			} */
			
			for (MyPlayers currentPlayer : myPlayers) {
				if(isPlayerInjured(currentPlayer,currGameDate)) continue;
					
				List<Schedule> scheduledGamesAtCurrentDate= schedule.stream()
						.filter(scheduleIns -> (scheduleIns.getGameDate().equals(currGameDate.getGameDate())))
						.collect(Collectors.toList());
				
				List<Schedule> scheduledGamesOfPlayersTeamAtCurrentDate= scheduledGamesAtCurrentDate.stream()
						.filter(scheduleIns -> scheduleIns.getMatch()!=null && scheduleIns.getTeam()
						.equals(currentPlayer.getTeam())).collect(Collectors.toList());								
				
				if (!scheduledGamesOfPlayersTeamAtCurrentDate.isEmpty()){
					MyPlayersModel myPly = new MyPlayersModel();
					myPly.setOrder(playerOrder);
					myPly.setId(currentPlayer.getId());
					myPly.setTeam(currentPlayer.getTeam());
					myPly.setName(currentPlayer.getName());
					myPly.setAvgPts(currentPlayer.getAvgPts());
					myPly.setIsPG(currentPlayer.getIsPG());
					myPly.setIsSG(currentPlayer.getIsSG());
					myPly.setIsSF(currentPlayer.getIsSF());
					myPly.setIsPF(currentPlayer.getIsPF());						
					myPly.setIsC(currentPlayer.getIsC());
					myPlayersToday.add(myPly);
					playerOrder ++;
				}
			}
			

			if(myPlayersToday.size()>0 && myPlayersToday.size()<13){
				gamedateRoster.add(fillGameDateRoster(getPermutations(myPlayersToday), currGameDate, myPlayersToday));
			}
			myPlayersToday.clear();
			
			System.out.println("currGameDate:" + currGameDate.toString());
		}
		
		gameDateRostersDAO.removeAll();
		gameDateRostersDAO.persistAll(gamedateRoster);
		
		return gamedateRoster;
		
	}
	
	private boolean isPlayerInjured(MyPlayers currentPlayer, GameDates currGameDate) {
		if(currentPlayer.getInjEnd()==null || currentPlayer.getInjEnd().before(currGameDate.getGameDate())) return false;
		return true;
	}

	public interface SchedulePredicate {
		void compare(Schedule s, Date gameDate, String team);
	}
		
	public List<PermModel>  getPermutations (List<MyPlayersModel> myPlayersToday){
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
			/*if(perm.getId()==307){
				System.out.println("");
			}*/
			Double currTotalPts = 0.0;
			List<MyPlayersModel> currPG = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getPg()).collect(Collectors.toList());
			if(currPG != null && currPG.size()>0 && currPG.get(0).getIsPG()==1) {
				currTotalPts += currPG.get(0).getAvgPts();
			}else{
				currPG = null;
			}
			
			List<MyPlayersModel>  currSG = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getSg()).collect(Collectors.toList());
			if(currSG != null && currSG.size()>0 && currSG.get(0).getIsSG()==1){
				currTotalPts += currSG.get(0).getAvgPts();
			}else{
				currSG = null;
			}
			
			List<MyPlayersModel>  currSF = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getSf()).collect(Collectors.toList());
			if(currSF != null && currSF.size()>0 && currSF.get(0).getIsSF()==1){
				currTotalPts += currSF.get(0).getAvgPts();
			}else{
				currSF = null;
			}
			
			List<MyPlayersModel>  currPF = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getPf()).collect(Collectors.toList());
			if(currPF != null && currPF.size()>0 && currPF.get(0).getIsPF()==1){
				currTotalPts += currPF.get(0).getAvgPts();
			}else{
				currPF = null;
			}
			
			List<MyPlayersModel>  currC = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getC()).collect(Collectors.toList());
			if(currC != null && currC.size()>0 && currC.get(0).getIsC()==1){
				currTotalPts += currC.get(0).getAvgPts();
			}else{
				currC = null;
			}
			
			List<MyPlayersModel>  currUT = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==perm.getUt()).collect(Collectors.toList());
			if(currUT != null && currUT.size()>0 ){
				currTotalPts += currUT.get(0).getAvgPts();
			}else{
				currUT = null;
			}
			
			if(currTotalPts > totalPts){						
				totalPts = currTotalPts;
				currGameDateRoster = new GameDateRosterModel();
				currGameDateRoster.setGameDate(currGameDate.getGameDate());
				currGameDateRoster.setTotalPts(new Double(0));
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

}


