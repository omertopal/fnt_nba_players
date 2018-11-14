package org.nba.players.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.nba.players.entity.Schedule;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ScheduleDAO implements IScheduleDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Schedule> getAllSchedule() {
		String hql = "FROM Schedule where  gameDate >= trim(sysdate-(1/3)) ORDER BY gameDate,home";
		List<Schedule> returnValue = (List<Schedule>) entityManager.createQuery(hql).getResultList();
		entityManager.close();
		return returnValue;
	}

}
