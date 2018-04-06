package com.nba.players.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nba.players.entity.Schedule;

@Transactional
@Repository
public class ScheduleDAO implements IScheduleDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Schedule> getAllSchedule() {
		String hql = "FROM Schedule where match is not null and gameDate >= trim(sysdate-(1/3)) ORDER BY gameDate,team";
		return (List<Schedule>) entityManager.createQuery(hql).getResultList();
	}

}
