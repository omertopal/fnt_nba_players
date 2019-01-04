package org.nba.players.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
import org.nba.players.entity.Player;
import org.nba.players.entity.Schedule;
import org.nba.players.model.GameDateRosterEqModel;
import org.nba.players.model.GameDateRosterModel;
import org.nba.players.model.PermModel;
import org.nba.players.model.PlayerModel;
import org.nba.players.util.PlayerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculationService implements IPermService {
	
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
	public List<GameDateRosterModel> getGameDateRosters() throws Exception {
		
		List<GameDateRosterModel> gamedateRosters = new ArrayList<GameDateRosterModel>();	
				
		List<Player> myPlayers = playerDAO.getMyPlayers();
		
		List<Player> myPotentials = myPlayers.stream().filter( player -> (player.getIsPotential()==1))
														 .collect(Collectors.toList());
		
		myPlayers = myPlayers.stream().filter( player -> (player.getIsMy()==1))
				 .collect(Collectors.toList());
		
		if (myPotentials == null || myPotentials.isEmpty()) {
			fillGameDateRosters(gamedateRosters,myPlayers);		
			
		}else {
			for (Player potentialPlayer : myPotentials) {
				myPlayers.add(potentialPlayer);
				fillGameDateRosters(gamedateRosters,myPlayers);
				myPlayers.remove(potentialPlayer);
			}
		}
		//gameDateRostersDAO.removeAll();
		gameDateRostersDAO.persistAll(gamedateRosters);
		
		return gamedateRosters;
		
	}
	
	private void fillGameDateRosters(List<GameDateRosterModel> gamedateRosters, List<Player> myPlayers) throws Exception {
		
		int nextCalculationId = getNextCalculationId(); 
		
		List<Schedule> schedule = scheduleDAO.getAllSchedule();
		
		List<GameDates> gameDates = gameDatesDAO.getGameDates();
		
		List<PlayerModel> myPlayersToday = new ArrayList<>();
		
		for (GameDates currGameDate : gameDates) {			
			myPlayersToday.clear();
			int playerOrder = 1;
			 
			/*
			try {
				if (!currGameDate.getGameDate().equals(new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-20"))) continue;
			} catch (ParseException e) {
				e.printStackTrace();
			} */
			
			for (Player currentPlayer : myPlayers) {
				if(isPlayerInjured(currentPlayer,currGameDate)) continue;
					
				List<Schedule> scheduledGamesAtCurrentDate= schedule.stream()
						.filter(scheduleIns -> (scheduleIns.getGameDate().equals(currGameDate.getGameDate())))
						.collect(Collectors.toList());
				
				List<Schedule> scheduledGamesOfPlayersTeamAtCurrentDate= scheduledGamesAtCurrentDate.stream()
						.filter(scheduleIns -> scheduleIns.getAway().equals(currentPlayer.getTeam()) || scheduleIns.getHome().equals(currentPlayer.getTeam()))
						.collect(Collectors.toList());								
				
				if (!scheduledGamesOfPlayersTeamAtCurrentDate.isEmpty()){
					PlayerModel myPly = new PlayerModel();
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
				gamedateRosters.add(fillGameDateRoster(getPermutations(myPlayersToday), currGameDate, myPlayersToday,nextCalculationId));
			}
			myPlayersToday.clear();
			
			System.out.println("currGameDate:" + currGameDate.toString());
		}
	}
	
	protected int getNextCalculationId(){
		return gameDateRostersDAO.getNextCalcId();
	}
	
	private boolean isPlayerInjured(Player currentPlayer, GameDates currGameDate) {
		if(currentPlayer.getInjEnd()==null || currentPlayer.getInjEnd().before(currGameDate.getGameDate())) return false;
		return true;
	}

	public interface SchedulePredicate {
		void compare(Schedule s, Date gameDate, String team);
	}
		
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
	
	public Double maxPointsToday (List<PlayerModel> myPlayersToday){
		
		List<PlayerModel> sortedmyPlayersToday = myPlayersToday.stream()
				  .sorted(Comparator.comparing(PlayerModel::getAvgPts).reversed())
				  .collect(Collectors.toList());
		
		Double maxPointsToday = 0.0;
		int count = 0;
		int maxCount = 6;
		for(PlayerModel player : sortedmyPlayersToday){
			if (count >= maxCount) 
				return maxPointsToday;
			maxPointsToday += player.getAvgPts();
			count ++;
		}
		return maxPointsToday;
	}
	
	public PlayerModel getPlayerByPositionAndPermutationPositionNo (String positionCode, List<PlayerModel> myPlayersToday, int permutationPositionNo) throws Exception{
		List<PlayerModel> filteredList = myPlayersToday.stream().filter(myPlayerIns -> myPlayerIns.getOrder()==permutationPositionNo).collect(Collectors.toList());
		if(filteredList.size()>1) {
			throw new Exception("More than one player found for permutation:" + permutationPositionNo);
		}else if (filteredList == null || filteredList.size() == 0) {
			return null;
		}
		PlayerModel playerFound = filteredList.get(0);
				
		if ( (positionCode.equals(PlayerConstants.POINT_GUARD) && playerFound.getIsPG()!=1)
			|| (positionCode.equals(PlayerConstants.SHOOTING_GUARD) &&  playerFound.getIsSG()!=1) 	
			|| (positionCode.equals(PlayerConstants.SMALL_FORWARD)  && playerFound.getIsSF()!=1)  	
			|| (positionCode.equals(PlayerConstants.POWER_FORWARD)  && playerFound.getIsPF()!=1)  	
			|| (positionCode.equals(PlayerConstants.CENTER)  && playerFound.getIsC()!=1) ) {
			return null;
		}else return playerFound;
	}
	
	public Double calculateTotalPointsOfGivenRoster (HashMap<String,PlayerModel> bestRosterMap) {
		Double currTotalPts = 0.0;
		currTotalPts += ( bestRosterMap.get(PlayerConstants.POINT_GUARD) == null ) ? 0 : bestRosterMap.get(PlayerConstants.POINT_GUARD).getAvgPts();
		currTotalPts += ( bestRosterMap.get(PlayerConstants.SHOOTING_GUARD) == null ) ? 0 : bestRosterMap.get(PlayerConstants.SHOOTING_GUARD).getAvgPts();
		currTotalPts += ( bestRosterMap.get(PlayerConstants.SMALL_FORWARD) == null ) ? 0 : bestRosterMap.get(PlayerConstants.SMALL_FORWARD).getAvgPts();
		currTotalPts += ( bestRosterMap.get(PlayerConstants.POWER_FORWARD) == null ) ? 0 : bestRosterMap.get(PlayerConstants.POWER_FORWARD).getAvgPts();
		currTotalPts += ( bestRosterMap.get(PlayerConstants.CENTER) == null ) ? 0 : bestRosterMap.get(PlayerConstants.CENTER).getAvgPts();
		currTotalPts += ( bestRosterMap.get(PlayerConstants.UTIL_PLAYER) == null ) ? 0 : bestRosterMap.get(PlayerConstants.UTIL_PLAYER).getAvgPts();
		return currTotalPts;
	}
	
	public HashMap<String,PlayerModel> getRosterOfGivenPermutation (List<PlayerModel> myPlayersToday,PermModel currentPermutation) throws Exception {
		
		HashMap<String,PlayerModel> playersMap = new HashMap<String,PlayerModel>();
		
		playersMap.put(PlayerConstants.POINT_GUARD, getPlayerByPositionAndPermutationPositionNo (PlayerConstants.POINT_GUARD,myPlayersToday,currentPermutation.getPg()));
		playersMap.put(PlayerConstants.SHOOTING_GUARD, getPlayerByPositionAndPermutationPositionNo (PlayerConstants.SHOOTING_GUARD,myPlayersToday,currentPermutation.getSg()));
		playersMap.put(PlayerConstants.SMALL_FORWARD, getPlayerByPositionAndPermutationPositionNo (PlayerConstants.SMALL_FORWARD,myPlayersToday,currentPermutation.getSf()));
		playersMap.put(PlayerConstants.POWER_FORWARD, getPlayerByPositionAndPermutationPositionNo (PlayerConstants.POWER_FORWARD,myPlayersToday,currentPermutation.getPf()));
		playersMap.put(PlayerConstants.CENTER, getPlayerByPositionAndPermutationPositionNo (PlayerConstants.CENTER,myPlayersToday,currentPermutation.getC()));
		playersMap.put(PlayerConstants.UTIL_PLAYER, getPlayerByPositionAndPermutationPositionNo (PlayerConstants.UTIL_PLAYER,myPlayersToday,currentPermutation.getUt()));
		
		return playersMap;
	}
	
	public GameDateRosterModel fillGameDateRoster (List<PermModel> permutations,GameDates currGameDate,List<PlayerModel> myPlayersToday, int calcId) throws Exception{
		GameDateRosterModel currGameDateRoster = new GameDateRosterModel();
		currGameDateRoster.setEquivalentPermutations(new ArrayList<>());
		Double highestTotalPointsOfDay = new Double(0);
		//Double maxPointsToday = maxPointsToday(myPlayersToday);
		for(PermModel currentPermutation : permutations){	
			/*if(perm.getId()==307){
				System.out.println("");
			}*/
			
			HashMap<String,PlayerModel> rosterOfCurrentPermutation = getRosterOfGivenPermutation(myPlayersToday, currentPermutation);
			Double totalPointsOfCurrentRoster = calculateTotalPointsOfGivenRoster(rosterOfCurrentPermutation);
			
			if(totalPointsOfCurrentRoster.compareTo(highestTotalPointsOfDay) > 0){						
				highestTotalPointsOfDay = totalPointsOfCurrentRoster;
				currGameDateRoster = new GameDateRosterModel();
				currGameDateRoster.setGameDate(currGameDate.getGameDate());
				currGameDateRoster.setTotalPts(new Double(0));
				currGameDateRoster.setCalcId(calcId);
				
				if(rosterOfCurrentPermutation.get(PlayerConstants.POINT_GUARD) != null) currGameDateRoster.setPg(rosterOfCurrentPermutation.get(PlayerConstants.POINT_GUARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.SHOOTING_GUARD) != null) currGameDateRoster.setSg(rosterOfCurrentPermutation.get(PlayerConstants.SHOOTING_GUARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.SMALL_FORWARD) != null) currGameDateRoster.setSf(rosterOfCurrentPermutation.get(PlayerConstants.SMALL_FORWARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.POWER_FORWARD) != null) currGameDateRoster.setPf(rosterOfCurrentPermutation.get(PlayerConstants.POWER_FORWARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.CENTER) != null) currGameDateRoster.setC(rosterOfCurrentPermutation.get(PlayerConstants.CENTER).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.UTIL_PLAYER) != null) currGameDateRoster.setUt(rosterOfCurrentPermutation.get(PlayerConstants.UTIL_PLAYER).getId());
				
				currGameDateRoster.setPermId(currentPermutation.getId());
				currGameDateRoster.setActivePlayersCount(myPlayersToday.size());
				currGameDateRoster.setTotalPts(totalPointsOfCurrentRoster);	
				List<GameDateRosterEqModel> equivalentPermList = new ArrayList<GameDateRosterEqModel>();
				GameDateRosterEqModel eqModel = new GameDateRosterEqModel(currentPermutation.getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.POINT_GUARD) != null) eqModel.setPg(rosterOfCurrentPermutation.get(PlayerConstants.POINT_GUARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.SHOOTING_GUARD) != null) eqModel.setSg(rosterOfCurrentPermutation.get(PlayerConstants.SHOOTING_GUARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.SMALL_FORWARD) != null) eqModel.setSf(rosterOfCurrentPermutation.get(PlayerConstants.SMALL_FORWARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.POWER_FORWARD) != null) eqModel.setPf(rosterOfCurrentPermutation.get(PlayerConstants.POWER_FORWARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.CENTER) != null) eqModel.setC(rosterOfCurrentPermutation.get(PlayerConstants.CENTER).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.UTIL_PLAYER) != null) eqModel.setUt(rosterOfCurrentPermutation.get(PlayerConstants.UTIL_PLAYER).getId());
				equivalentPermList.add( eqModel);
				currGameDateRoster.setEquivalentPermutations(equivalentPermList);
			}else if (totalPointsOfCurrentRoster.compareTo(highestTotalPointsOfDay) == 0) {
				
				GameDateRosterEqModel eqModel = new GameDateRosterEqModel(currentPermutation.getId());
				
				if(rosterOfCurrentPermutation.get(PlayerConstants.POINT_GUARD) != null) eqModel.setPg(rosterOfCurrentPermutation.get(PlayerConstants.POINT_GUARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.SHOOTING_GUARD) != null) eqModel.setSg(rosterOfCurrentPermutation.get(PlayerConstants.SHOOTING_GUARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.SMALL_FORWARD) != null) eqModel.setSf(rosterOfCurrentPermutation.get(PlayerConstants.SMALL_FORWARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.POWER_FORWARD) != null) eqModel.setPf(rosterOfCurrentPermutation.get(PlayerConstants.POWER_FORWARD).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.CENTER) != null) eqModel.setC(rosterOfCurrentPermutation.get(PlayerConstants.CENTER).getId());
				if(rosterOfCurrentPermutation.get(PlayerConstants.UTIL_PLAYER) != null) eqModel.setUt(rosterOfCurrentPermutation.get(PlayerConstants.UTIL_PLAYER).getId());
				
				currGameDateRoster.getEquivalentPermutations().add(eqModel);
			}
			//if(currTotalPts >= maxPointsToday) return currGameDateRoster;
		}
		return currGameDateRoster;
	}
	
	public String getEquivalentPermutations(int permId) {
		return null;
	}
	
	public  List<List<Player>> generatePerm(List<Player> original) {
	     if (original.size() == 0) {
	       List<List<Player>> result = new ArrayList<List<Player>>(); 
	       result.add(new ArrayList<Player>()); 
	       return result; 
	     }
	     Player firstElement = original.remove(0);
	     List<List<Player>> returnValue = new ArrayList<List<Player>>();
	     List<List<Player>> permutations = generatePerm(original);
	     for (List<Player> smallerPermutated : permutations) {
	       for (int index=0; index <= smallerPermutated.size(); index++) {
	         List<Player> temp = new ArrayList<Player>(smallerPermutated);
	         temp.add(index, firstElement);
	         returnValue.add(temp);
	       }
	     }
	     return returnValue;
	   }

}


