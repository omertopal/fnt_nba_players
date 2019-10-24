package org.nba.players.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.nba.players.common.CommonUtils;
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
import org.nba.players.dao.ITeamDAO;
import org.nba.players.dao.SeqGameDateRostersDAO;
import org.nba.players.dto.CalcUsageResult;
import org.nba.players.dto.CalculationIdDTO;
import org.nba.players.dto.PlayerUsageDTO;
import org.nba.players.dto.TeamBenefitDTO;
import org.nba.players.entity.GameDates;
import org.nba.players.entity.Player;
import org.nba.players.entity.Schedule;
import org.nba.players.entity.Team;
import org.nba.players.model.GameDateRosterEqModel;
import org.nba.players.model.GameDateRosterModel;
import org.nba.players.model.PermModel;
import org.nba.players.model.PlayerModel;
import org.nba.players.opta.NbaOptaSchedule;
import org.nba.players.opta.OptaPlayer;
import org.nba.players.opta.PlayerSlotSelection;
import org.nba.players.util.PlayerConstants;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculationService implements ICalcService {
	
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
	private SeqGameDateRostersDAO seqGameDateRostersDAO;	
	
	@Autowired
	private IPlayerDAO playerDAO;	
	
	@Autowired
	private ITeamDAO teamDAO;
	
	@Override
	public List<TeamBenefitDTO> getTeamBenefitList(int calcId, int days){
		
		List<TeamBenefitDTO> resultList = new ArrayList<TeamBenefitDTO>();
		List<Team> teamList = teamDAO.getAllTeams();
		List<Schedule> teamSchedule = new ArrayList<Schedule>();
		List<GameDateRosterModel> gameDateRosters = gameDateRostersDAO.getAllGameDateRosters(calcId);

		Date maxGameDate = new Date();
		List<GameDateRosterModel> gameDateRosterListByDay = new ArrayList<GameDateRosterModel>();		
		for(int i=0; i < days; i++) {
			gameDateRosterListByDay.add(gameDateRosters.get(i));
			maxGameDate = gameDateRosters.get(i).getGameDate();
			if(i+1 >= gameDateRosters.size()) break;
		}		
		
		try {
			for(Team team : teamList) {
				
				TeamBenefitDTO benefitDTO = new TeamBenefitDTO(team.getName(),0,0,0,0,0,0);
				
				teamSchedule = scheduleDAO.getTeamSchedule(team.getCode());		
				
				for (Schedule schedule: teamSchedule) {		
					if(schedule.getGameDate().compareTo(maxGameDate)>0) break;
					List<GameDateRosterModel> filteredList = gameDateRosterListByDay.stream()
					        .filter(roster -> roster.getGameDate().equals(schedule.getGameDate()))
					        .collect(Collectors.toList());
					
					if(filteredList.size()==1) {
						addToPositionCounts(filteredList.get(0), benefitDTO);
					}else {
						benefitDTO.setPgCount(benefitDTO.getPgCount()+ 1);
						benefitDTO.setSgCount(benefitDTO.getSgCount()+ 1);
						benefitDTO.setSfCount(benefitDTO.getSfCount()+ 1);
						benefitDTO.setPfCount(benefitDTO.getPfCount()+ 1);
						benefitDTO.setcCount(benefitDTO.getcCount()+ 1);
						benefitDTO.setUtCount(benefitDTO.getUtCount()+ 1);
					}					
				}
				
				resultList.add(benefitDTO);
			}
		}catch (Exception e) {
			System.out.print("here");
		}
		
		return resultList;
	}
	
	public void addToPositionCounts (GameDateRosterModel rosterModel, TeamBenefitDTO benefitDTO) {
		
		int pgCount=0;
		int sgCount=0;
		int sfCount=0;
		int pfCount=0;
		int cCount=0;
		int utCount=0;
		
		if(rosterModel.getPg()==0) pgCount=1;
		if(rosterModel.getSg()==0) sgCount=1;
		if(rosterModel.getSf()==0) sfCount=1;
		if(rosterModel.getPf()==0) pfCount=1;
		if(rosterModel.getC()==0) cCount=1;
		if(rosterModel.getUt()==0) utCount=1;
		
		for(GameDateRosterEqModel eqModel: rosterModel.getEquivalentPermutations()) {
			if(eqModel.getPg() ==0 ) pgCount=1;
			if(eqModel.getSg()==0) sgCount=1;
			if(eqModel.getSf()==0) sfCount=1;
			if(eqModel.getPf()==0) pfCount=1;
			if(eqModel.getC()==0) cCount=1;
			if(eqModel.getUt()==0) utCount=1;
		}
		
		benefitDTO.setPgCount(benefitDTO.getPgCount()+ pgCount);
		benefitDTO.setSgCount(benefitDTO.getSgCount()+ sgCount);
		benefitDTO.setSfCount(benefitDTO.getSfCount()+ sfCount);
		benefitDTO.setPfCount(benefitDTO.getPfCount()+ pfCount);
		benefitDTO.setcCount(benefitDTO.getcCount()+ cCount);
		benefitDTO.setUtCount(benefitDTO.getUtCount()+ utCount);
		
	}
		
	
	@Override
	public List<CalculationIdDTO> calculationIdList(){
		
		return gameDateRostersDAO.calculationIdList();
	}
	
	@Override
	public CalcUsageResult getGameDateRosters(String method) throws Exception {
		
		CalcUsageResult result = new CalcUsageResult();
		List<GameDateRosterModel> gamedateRosters = new ArrayList<GameDateRosterModel>();	
				
		List<Player> myPlayers = playerDAO.getMyPlayers();
		
		//List<Player> myPotentials = myPlayers.stream().filter( player -> (player.getIsPotential()==1)).collect(Collectors.toList());
		
		myPlayers = myPlayers.stream().filter( player -> (player.getIsMy()==1))
				 .collect(Collectors.toList());
		
		//if (myPotentials == null || myPotentials.isEmpty()) {
			
		fillGameDateRosters(gamedateRosters,myPlayers,method);		
		
		/*
		}else {
			for (Player potentialPlayer : myPotentials) {
				myPlayers.add(potentialPlayer);
				fillGameDateRosters(gamedateRosters,myPlayers);
				myPlayers.remove(potentialPlayer);
			}
		}
		*/
		//gameDateRostersDAO.removeAll();
		gameDateRostersDAO.persistAll(gamedateRosters);
		
		result.setGameDateRosterList(gamedateRosters);
		result.setPlayerUsageList(calculatePlayerUsage(myPlayers,gamedateRosters));
		
		return result;
		
	}
	
	private List<PlayerUsageDTO> calculatePlayerUsage(List<Player> myPlayers,List<GameDateRosterModel> gameDateRosters) {
		List<PlayerUsageDTO> playerUsageList = new ArrayList<PlayerUsageDTO>();
		
		for(Player player: myPlayers) {
			PlayerUsageDTO usageDto = new PlayerUsageDTO();
			usageDto.setPlayerId(player.getId());
			usageDto.setPlayerName(player.getName());
			int mustHaveUsage = 0;
			int totalUsage =0;
			
			for(GameDateRosterModel gameModel : gameDateRosters) {
				
				if(gameModel.getPg() == player.getId()) {
					totalUsage ++;
				}else if(gameModel.getSg() == player.getId()) {
					totalUsage ++;
				}else if(gameModel.getSf() == player.getId()) {
					totalUsage ++;
				}else if(gameModel.getPf() == player.getId()) {
					totalUsage ++;
				}else if(gameModel.getC() == player.getId()) {
					totalUsage ++;
				}else if(gameModel.getUt() == player.getId()) {
					totalUsage ++;
				}
			}
			
			usageDto.setMustHaveUsage(mustHaveUsage);
			usageDto.setTotalUsage(totalUsage);
			
			playerUsageList.add(usageDto);			
		}
		return playerUsageList;
	}
	
	private HashMap<java.sql.Date,Integer> getGameDatesHashMap () {
		HashMap<java.sql.Date,Integer> resultMap = new HashMap<>();
		List<GameDates> gameDatesList = gameDatesDAO.getGameDates();
		
		int gameDateId = 1;
		for (GameDates gameDate : gameDatesList) {			
			resultMap.put(gameDate.getGameDate(),gameDateId);
			gameDateId ++;
			break;
		}
		return resultMap;
	}
	
	private void addDefaultSkillsToPlayer(Set<Integer> playerSkills) {
		playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.UTIL_PLAYER));
		playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.BENCH_PLAYER_1));
		playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.BENCH_PLAYER_2));
		playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.BENCH_PLAYER_3));
		playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.BENCH_PLAYER_4));
		playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.BENCH_PLAYER_5));
		playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.BENCH_PLAYER_6));
	}
	
	private List<OptaPlayer> getOptaPlayersList (List<PlayerModel> myPlayers) {
		
		List<OptaPlayer> optaPlayersList = new ArrayList<>();
		
		for (PlayerModel player : myPlayers) {
			
			OptaPlayer optaPlayer = new OptaPlayer();
			optaPlayer.setAvgPts(player.getAvgPts());
			optaPlayer.setPlayerId(player.getId());
			optaPlayer.setName(player.getName());
			
			Set<Integer> playerSkills = new HashSet<Integer>();
			addDefaultSkillsToPlayer(playerSkills);
			if(player.hasPGSkill())  playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.POINT_GUARD));
			if(player.hasSGSkill())  playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.SHOOTING_GUARD));
			if(player.hasSFSkill())  playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.SMALL_FORWARD));
			if(player.hasPFSkill())  playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.POWER_FORWARD));
			if(player.hasCSkill())  playerSkills.add(CommonUtils.getPlayerSkills().get(PlayerConstants.CENTER));
			
			optaPlayer.setSkills(playerSkills);
			
			/*
			List<Schedule> scheduledGamesOfPlayersTeam= allNbaSchedule.stream()
					.filter(scheduleIns -> scheduleIns.getAway().equals(player.getTeam()) 
										|| scheduleIns.getHome().equals(player.getTeam()))
					.collect(Collectors.toList());								
			
			List<Integer> playerGameDates = new ArrayList<>();
			if (!scheduledGamesOfPlayersTeam.isEmpty()){
				for (Schedule schedule : scheduledGamesOfPlayersTeam) {
					if(gameDatesHashMap.get(schedule.getGameDate()) != null) {
						playerGameDates.add(gameDatesHashMap.get(schedule.getGameDate()));
					}
				}
			}
			
			
			
			OptaTeam playerTeam = new OptaTeam();
			playerTeam.setGameDates(playerGameDates);
			optaPlayer.setTeam(playerTeam);
			*/
			
			optaPlayersList.add(optaPlayer);
		}
		return optaPlayersList;
	}
	
	private void fillGameDateRosters(List<GameDateRosterModel> gamedateRosters, List<Player> myPlayers,String method) throws Exception {
		
		List<Schedule> schedule = scheduleDAO.getAllSchedule();
		
		List<GameDates> gameDates = gameDatesDAO.getGameDates();
		
		List<PlayerModel> myPlayersToday = new ArrayList<>();
		
		int optaCounter = 7;
		
		int calculationId = getNextCalculationId();
		
		Timestamp runtime = new Timestamp(System.currentTimeMillis());
		
		for (GameDates currGameDate : gameDates) {
			myPlayersToday.clear();
			int playerOrder = 1;
			
			/*
			try {
				if (!currGameDate.getGameDate().equals(new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-02"))) continue;
			} catch (ParseException e) {
				e.printStackTrace();
			}
			*/
			
			for (Player currentPlayer : myPlayers) {
				if(isPlayerInjured(currentPlayer,currGameDate)) continue;
					
				List<Schedule> scheduledGamesAtCurrentDate= schedule.stream()
						.filter(scheduleIns -> (scheduleIns.getGameDate().equals(currGameDate.getGameDate())))
						.collect(Collectors.toList());
				
				List<Schedule> scheduledGamesOfPlayersTeamAtCurrentDate= scheduledGamesAtCurrentDate.stream()
						.filter(scheduleIns -> scheduleIns.getAway().equals(currentPlayer.getTeam().getCode()) 
								|| scheduleIns.getHome().equals(currentPlayer.getTeam().getCode()))
						.collect(Collectors.toList());								
				
				if (!scheduledGamesOfPlayersTeamAtCurrentDate.isEmpty()){
					PlayerModel myPly = new PlayerModel();
					myPly.setOrder(playerOrder);
					myPly.setId(currentPlayer.getId());
					myPly.setTeam(currentPlayer.getTeam().getCode());
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
				
				if (CommonUtils.STANDART_METHOD.equals(method)) {
					gamedateRosters.add(fillTodaysGameDateRoster(getPermutations(myPlayersToday), currGameDate, myPlayersToday, calculationId, runtime));
				}else if(CommonUtils.OPTA_METHOD.equals(method)) {
					if(optaCounter > 0 ) optaFillTodaysGameDateRoster(gamedateRosters,currGameDate, myPlayersToday, calculationId, runtime);
				}
				
			}
			optaCounter --;
			myPlayersToday.clear();
			
			System.out.println("currGameDate:" + currGameDate.toString());
		}
	}
	
	protected synchronized int getNextCalculationId(){
		return seqGameDateRostersDAO.getNextCalcId();
	}
		
	public CalcUsageResult getAllGameDateRosters (int calcId,int days) {
		CalcUsageResult result = new CalcUsageResult();
		List<GameDateRosterModel> gameDateRosterList = new ArrayList<GameDateRosterModel>();
		gameDateRosterList = gameDateRostersDAO.getAllGameDateRosters(calcId);
		
		List<GameDateRosterModel> gameDateRosterListByDay = new ArrayList<GameDateRosterModel>();
		
		for(int i=0; i < days; i++) {
			gameDateRosterListByDay.add(gameDateRosterList.get(i));
			if(i+1 >= gameDateRosterList.size()) break;
		}		
		
		result.setGameDateRosterList(gameDateRosterListByDay);
		
		
		Set<Integer> myPlayerIds = new HashSet<Integer>();
		List<Player> myPlayers = new ArrayList<Player>();
		for(GameDateRosterModel model : gameDateRosterListByDay) {
			if(!myPlayerIds.contains(model.getPg()) && model.getPg()>0){
				myPlayerIds.add(model.getPg());
				myPlayers.add(playerDAO.getPlayerById(model.getPg()));
			}
			if(!myPlayerIds.contains(model.getSg()) && model.getSg()>0){
				myPlayerIds.add(model.getSg());
				myPlayers.add(playerDAO.getPlayerById(model.getSg()));
			}
			if(!myPlayerIds.contains(model.getSf()) && model.getSf()>0){
				myPlayerIds.add(model.getSf());
				myPlayers.add(playerDAO.getPlayerById(model.getSf()));
			}
			if(!myPlayerIds.contains(model.getPf()) && model.getPf()>0){
				myPlayerIds.add(model.getPf());
				myPlayers.add(playerDAO.getPlayerById(model.getPf()));
			}
			if(!myPlayerIds.contains(model.getC()) && model.getC()>0){
				myPlayerIds.add(model.getC());
				myPlayers.add(playerDAO.getPlayerById(model.getC()));
			}
			if(!myPlayerIds.contains(model.getUt()) && model.getUt()>0){
				myPlayerIds.add(model.getUt());
				myPlayers.add(playerDAO.getPlayerById(model.getUt()));
			}				
		}
		
		result.setPlayerUsageList(calculatePlayerUsage(myPlayers,gameDateRosterListByDay));
		return result;
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
	
	private void optaFillTodaysGameDateRoster(List<GameDateRosterModel> gamedateRosters, GameDates gameDate, List<PlayerModel> myPlayersToday, int calculationId,Timestamp runtime) throws Exception {
		
		NbaOptaSchedule unsolvedNbaOptaSchedule = new NbaOptaSchedule();
		
		for(int i = 0; i < myPlayersToday.size(); i++){
            unsolvedNbaOptaSchedule.getSelectionList().add(new PlayerSlotSelection());
        }
		
        unsolvedNbaOptaSchedule.getPositionSlotList().addAll(CommonUtils.getPositionSlots(myPlayersToday.size()).keySet());
        unsolvedNbaOptaSchedule.getPlayerList().addAll(getOptaPlayersList(myPlayersToday));
		
		SolverFactory<NbaOptaSchedule> solverFactory = SolverFactory.createFromXmlResource("nbaScheduleSolverConfiguration.xml");
        Solver<NbaOptaSchedule> solver = solverFactory.buildSolver();
        NbaOptaSchedule solvedNbaSchedule = solver.solve(unsolvedNbaOptaSchedule);
        
        //solvedNbaSchedule.getSelectionList().removeIf(selection -> (selection.getPositionSlot()==null));
        List<PlayerSlotSelection> filteredList = solvedNbaSchedule.getSelectionList().stream().limit(myPlayersToday.size()).collect(Collectors.toList());
        //solvedNbaSchedule.printNbaSchedule();
        
        GameDateRosterModel todayRoster = new GameDateRosterModel();
        todayRoster.setEquivalentPermutations(new ArrayList<>());
        todayRoster.setGameDate(gameDate.getGameDate());
        todayRoster.setTotalPts(new Double(0));
        todayRoster.setCalcId(calculationId);
        todayRoster.setRunTime(runtime);
        Double totalPointsOfCurrentRoster = 0.0;
        
        for (PlayerSlotSelection playerSlotSelection : filteredList) {
        	totalPointsOfCurrentRoster += playerSlotSelection.getPlayer().getAvgPts();
			if(CommonUtils.getPlayerSkills().get(PlayerConstants.POINT_GUARD).equals(playerSlotSelection.getPositionSlot())) {
				PlayerModel modelPg = new PlayerModel();
				modelPg.setName(playerSlotSelection.getPlayer().getName());
				todayRoster.setPgModel(modelPg);
				todayRoster.setPg(playerSlotSelection.getPlayer().getPlayerId());
			}
			if(CommonUtils.getPlayerSkills().get(PlayerConstants.SHOOTING_GUARD).equals(playerSlotSelection.getPositionSlot())) {
				PlayerModel modelSg = new PlayerModel();
				modelSg.setName(playerSlotSelection.getPlayer().getName());
				todayRoster.setSgModel(modelSg);
				todayRoster.setSg(playerSlotSelection.getPlayer().getPlayerId());
			}
			if(CommonUtils.getPlayerSkills().get(PlayerConstants.SMALL_FORWARD).equals(playerSlotSelection.getPositionSlot())) {
				PlayerModel modelSf = new PlayerModel();
				modelSf.setName(playerSlotSelection.getPlayer().getName());
				todayRoster.setSfModel(modelSf);
				todayRoster.setSf(playerSlotSelection.getPlayer().getPlayerId());
			}
			if(CommonUtils.getPlayerSkills().get(PlayerConstants.POWER_FORWARD).equals(playerSlotSelection.getPositionSlot())) {
				PlayerModel modelPf = new PlayerModel();
				modelPf.setName(playerSlotSelection.getPlayer().getName());
				todayRoster.setPfModel(modelPf);
				todayRoster.setPf(playerSlotSelection.getPlayer().getPlayerId());
			}
			if(CommonUtils.getPlayerSkills().get(PlayerConstants.CENTER).equals(playerSlotSelection.getPositionSlot())) {
				PlayerModel modelC = new PlayerModel();
				modelC.setName(playerSlotSelection.getPlayer().getName());
				todayRoster.setcModel(modelC);
				todayRoster.setC(playerSlotSelection.getPlayer().getPlayerId());
			}
			if(CommonUtils.getPlayerSkills().get(PlayerConstants.UTIL_PLAYER).equals(playerSlotSelection.getPositionSlot())) {
				PlayerModel modelUt = new PlayerModel();
				modelUt.setName(playerSlotSelection.getPlayer().getName());
				todayRoster.setUtModel(modelUt);
				todayRoster.setUt(playerSlotSelection.getPlayer().getPlayerId());
			}
		}
		todayRoster.setPermId(0);
		todayRoster.setActivePlayersCount(myPlayersToday.size());
		todayRoster.setTotalPts(totalPointsOfCurrentRoster);	
			
		gamedateRosters.add(todayRoster);
	}
	
	public GameDateRosterModel fillTodaysGameDateRoster (List<PermModel> permutations,GameDates currGameDate,List<PlayerModel> myPlayersToday, int calculationId,Timestamp runtime) throws Exception{
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
				currGameDateRoster.setCalcId(calculationId);
				currGameDateRoster.setRunTime(runtime);
				
				if(rosterOfCurrentPermutation.get(PlayerConstants.POINT_GUARD) != null) {
					currGameDateRoster.setPg(rosterOfCurrentPermutation.get(PlayerConstants.POINT_GUARD).getId());
					currGameDateRoster.setPgModel(rosterOfCurrentPermutation.get(PlayerConstants.POINT_GUARD));
				}
				if(rosterOfCurrentPermutation.get(PlayerConstants.SHOOTING_GUARD) != null) {
					currGameDateRoster.setSg(rosterOfCurrentPermutation.get(PlayerConstants.SHOOTING_GUARD).getId());
					currGameDateRoster.setSgModel(rosterOfCurrentPermutation.get(PlayerConstants.SHOOTING_GUARD));
				}
				if(rosterOfCurrentPermutation.get(PlayerConstants.SMALL_FORWARD) != null) {
					currGameDateRoster.setSf(rosterOfCurrentPermutation.get(PlayerConstants.SMALL_FORWARD).getId());
					currGameDateRoster.setSfModel(rosterOfCurrentPermutation.get(PlayerConstants.SMALL_FORWARD));
				}
				if(rosterOfCurrentPermutation.get(PlayerConstants.POWER_FORWARD) != null) {
					currGameDateRoster.setPf(rosterOfCurrentPermutation.get(PlayerConstants.POWER_FORWARD).getId());
					currGameDateRoster.setPfModel(rosterOfCurrentPermutation.get(PlayerConstants.POWER_FORWARD));
				}
				if(rosterOfCurrentPermutation.get(PlayerConstants.CENTER) != null) {
					currGameDateRoster.setC(rosterOfCurrentPermutation.get(PlayerConstants.CENTER).getId());
					currGameDateRoster.setcModel(rosterOfCurrentPermutation.get(PlayerConstants.CENTER));
				}
				if(rosterOfCurrentPermutation.get(PlayerConstants.UTIL_PLAYER) != null) {
					currGameDateRoster.setUt(rosterOfCurrentPermutation.get(PlayerConstants.UTIL_PLAYER).getId());
					currGameDateRoster.setUtModel(rosterOfCurrentPermutation.get(PlayerConstants.UTIL_PLAYER));
				}
				
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


