package org.nba.players.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.nba.players.entity.GameDateRosters;
import org.nba.players.entity.GameDateRostersEq;
import org.nba.players.model.GameDateRosterEqModel;
import org.nba.players.model.GameDateRosterModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class GameDateRostersDAO implements IGameDateRostersDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public int getNextCalcId() {
		String hql = "select ROSTER_CALC_SEQ.nextval FROM dual";
		int returnValue = ((BigDecimal) entityManager.createNativeQuery(hql).getResultList().get(0)).intValue();
		entityManager.close();
		return returnValue;
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

	@Override
	@Transactional
	public void removeAll() {
		
		Query query = entityManager.createNativeQuery("DELETE FROM GAME_DATE_ROSTERS");
		query.executeUpdate();
		
		entityManager.flush();
        entityManager.clear();
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
			roster.setRunTime(new Timestamp(System.currentTimeMillis()));
			
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
