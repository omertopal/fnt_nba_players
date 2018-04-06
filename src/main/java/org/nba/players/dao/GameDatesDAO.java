package com.nba.players.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nba.players.entity.GameDates;

@Transactional
@Repository
public class GameDatesDAO implements IGameDatesDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GameDates> getGameDates(){
		String hql = "FROM GameDates where gameDate >= trim(sysdate-(1/3)) ORDER BY gameDate ";
		return (List<GameDates>) entityManager.createQuery(hql).getResultList();
	}

}
