package org.nba.players.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.nba.players.entity.GameDates;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class GameDatesDAO implements IGameDatesDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GameDates> getGameDates(){
		String hql = "FROM GameDates where gameDate >= trim(sysdate-(1/3)) ORDER BY gameDate ";
		List<GameDates> returnValue = (List<GameDates>) entityManager.createQuery(hql).getResultList();
		entityManager.close();
		return returnValue;
	}

}
