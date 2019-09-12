package org.nba.players.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.nba.players.entity.SeqGameDateRosters;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class SeqGameDateRostersDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	public synchronized int getNextCalcId() {
		
		int calcId = 0;
		
		String hql = "SELECT max(g.CALC_ID) FROM seq_game_date_rosters g ";
		List<Integer> maxCalcId = entityManager.createNativeQuery(hql).getResultList();
		entityManager.close();
		if(maxCalcId==null || maxCalcId.get(0) ==null) {
			calcId = 1;
		}else {
			calcId = maxCalcId.get(0) +1;
		}
		
		//insert to db
		SeqGameDateRosters rosters = new SeqGameDateRosters(calcId);
		entityManager.persist(rosters);
		entityManager.close();
		
		return calcId;
	}
}
