package org.nba.players.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.transform.ResultTransformer;
import org.nba.players.dto.CalculationIdDTO;
import org.nba.players.entity.GameDateRosters;
import org.nba.players.entity.GameDateRostersEq;
import org.nba.players.entity.Player;
import org.nba.players.model.GameDateRosterEqModel;
import org.nba.players.model.GameDateRosterModel;
import org.nba.players.model.PlayerModel;
import org.nba.players.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class GameDateRostersDAO implements IGameDateRostersDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Autowired
	IPlayerService playerService;
	
	@Override
	public List<CalculationIdDTO> calculationIdList(){
		
		String hql = "SELECT DISTINCT CALC_ID,RUN_TIME FROM game_date_rosters g order by CALC_ID desc";
		Query query = entityManager.createNativeQuery(hql);
		
		return query.unwrap( org.hibernate.query.Query.class )
		.setResultTransformer(
			    new ResultTransformer() {
			        @Override
			        public Object transformTuple(
			            Object[] tuple,
			            String[] aliases) {
			            return new CalculationIdDTO(			            		
			                (int) tuple[0],
			                (java.sql.Timestamp) tuple[1]
			            );
			        }
			 
			        @Override
			        public List transformList(List collection) {
			            return collection;
			        }
			    }
			)
			.getResultList();
	}
	
	@Override
	@Transactional
	public void persistAll(List<GameDateRosterModel> rosterModels){
		List<GameDateRosters> rosters = mapFromModel(rosterModels);
		
		for (Iterator<GameDateRosters> it = rosters.iterator(); it.hasNext();) {
			GameDateRosters roster = it.next();
            entityManager.persist(roster);   
            entityManager.flush();
            entityManager.close();
        }
        
	}

	@Override
	public void persist(GameDateRosterModel roster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameDateRosterModel roster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAll(GameDateRosterModel roster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(GameDateRosterModel roster) {
		// TODO Auto-generated method stub
		
	}
	
	public List<GameDateRosterModel> getAllGameDateRosters (int calcId){
		
		String hql = "FROM GameDateRosters where calcId="+calcId;		
		List<GameDateRosters> rostersList = (List<GameDateRosters>) entityManager.createQuery(hql).getResultList();
		
		return mapToModel(rostersList);
	}

	@Override
	@Transactional
	public void removeAll() {
		
		Query query = entityManager.createNativeQuery("DELETE FROM GAME_DATE_ROSTERS");
		query.executeUpdate();
		
		entityManager.flush();
        entityManager.clear();
	}
	
	public PlayerModel mapPlayerToModel(Player player) {
		PlayerModel model = new PlayerModel();
		if(player != null) {
			model.setAvgPts(player.getAvgPts());
			model.setId(player.getId());
			model.setIsC(player.getIsC());
			model.setIsPF(player.getIsPF());
			model.setIsPG(player.getIsPG());
			model.setIsSG(player.getIsSG());
			model.setIsSF(player.getIsSF());
			model.setName(player.getName());
			model.setTeam(player.getTeam().getCode());
		}
		return model;
	}
	
	public List<GameDateRosterModel> mapToModel (List<GameDateRosters> rosters){
		List<GameDateRosterModel> resultMap = new ArrayList<>();
		
		for (GameDateRosters curRoster: rosters) {
			GameDateRosterModel rosterModel = new GameDateRosterModel();
			rosterModel.setPg(curRoster.getPg());
			rosterModel.setPgModel(mapPlayerToModel(playerService.getPlayerById(curRoster.getPg())));
			rosterModel.setSg(curRoster.getSg());
			rosterModel.setSgModel(mapPlayerToModel(playerService.getPlayerById(curRoster.getSg())));
			rosterModel.setSf(curRoster.getSf());
			rosterModel.setSfModel(mapPlayerToModel(playerService.getPlayerById(curRoster.getSf())));
			rosterModel.setPf(curRoster.getPf());
			rosterModel.setPfModel(mapPlayerToModel(playerService.getPlayerById(curRoster.getPf())));
			rosterModel.setC(curRoster.getC());
			rosterModel.setcModel(mapPlayerToModel(playerService.getPlayerById(curRoster.getC())));
			rosterModel.setUt(curRoster.getUt());
			rosterModel.setUtModel(mapPlayerToModel(playerService.getPlayerById(curRoster.getUt())));
			rosterModel.setActivePlayersCount(curRoster.getActivePlayersCount());
			rosterModel.setGameDate(curRoster.getGameDate());
			rosterModel.setTotalPts(curRoster.getTotalPts());
			rosterModel.setPermId(curRoster.getPermId());
			rosterModel.setCalcId(curRoster.getCalcId());
			rosterModel.setRunTime(curRoster.getRunTime());
			
//			for(GameDateRosterEqModel eqModel : curRoster.getEquivalentPermutations()) {
//				GameDateRostersEq equivalentEntity = new GameDateRostersEq();
//				equivalentEntity.setEquivalentPermId(model.getEquivalentPermId());
//				equivalentEntity.setPg(model.getPg());
//				equivalentEntity.setSg(model.getSg());
//				equivalentEntity.setSf(model.getSf());
//				equivalentEntity.setPf(model.getPf());
//				equivalentEntity.setC(model.getC());
//				equivalentEntity.setUt(model.getUt());
//				roster.addChild(equivalentEntity);
//			}
			
			resultMap.add(rosterModel);
		}
		
		return resultMap;
	}
	
	public List<GameDateRosters> mapFromModel (List<GameDateRosterModel> rosters){
		List<GameDateRosters> resultMap = new ArrayList<>();
		for (GameDateRosterModel curRosterModel: rosters) {
			GameDateRosters roster = new GameDateRosters();
			roster.setPg(curRosterModel.getPg());
			roster.setSg(curRosterModel.getSg());
			roster.setSf(curRosterModel.getSf());
			roster.setPf(curRosterModel.getPf());
			roster.setC(curRosterModel.getC());
			roster.setUt(curRosterModel.getUt());
			roster.setActivePlayersCount(curRosterModel.getActivePlayersCount());
			roster.setGameDate(curRosterModel.getGameDate());
			roster.setTotalPts(curRosterModel.getTotalPts());
			roster.setPermId(curRosterModel.getPermId());
			roster.setCalcId(curRosterModel.getCalcId());
			roster.setRunTime(curRosterModel.getRunTime());
			
			//List<GameDateRostersEq> equivalentList = new ArrayList<>();
			for(GameDateRosterEqModel model : curRosterModel.getEquivalentPermutations()) {
				GameDateRostersEq equivalentEntity = new GameDateRostersEq();
				equivalentEntity.setEquivalentPermId(model.getEquivalentPermId());
				equivalentEntity.setPg(model.getPg());
				equivalentEntity.setSg(model.getSg());
				equivalentEntity.setSf(model.getSf());
				equivalentEntity.setPf(model.getPf());
				equivalentEntity.setC(model.getC());
				equivalentEntity.setUt(model.getUt());
				//equivalentList.add(equivalentEntity);
				roster.addChild(equivalentEntity);
			}
			//roster.setEquivalentList(equivalentList);
			
			resultMap.add(roster);
		}
		return resultMap;
	}

	
	
}
