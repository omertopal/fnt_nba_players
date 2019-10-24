package org.nba.players.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.transform.ResultTransformer;
import org.nba.players.dto.GameDayCountDTO;
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
		String hql = "FROM Schedule where  gameDate >= CURRENT_DATE ORDER BY gameDate,home";
		List<Schedule> returnValue = (List<Schedule>) entityManager.createQuery(hql).getResultList();
		entityManager.close();
		return returnValue;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Schedule> getTeamSchedule(String teamCode) {
		String hql = " FROM Schedule where ( home=?1 or away=?2 ) and gameDate >= CURRENT_DATE order by gameDate";
		
		List<Schedule> returnList = (List<Schedule>) entityManager.createQuery(hql).setParameter(1, teamCode)
	              .setParameter(2, teamCode).getResultList();
		entityManager.close();
		
		return returnList;
	}

	@Override
	public List<GameDayCountDTO> getGameDayCounts() {		
		
		String hql = " SELECT game_date, COUNT(*) FROM schedule where  game_date >= CURRENT_DATE group by game_date ";
		Query query = entityManager.createNativeQuery(hql);
		
		return query.unwrap( org.hibernate.query.Query.class )
		.setResultTransformer(
			    new ResultTransformer() {
			        @Override
			        public Object transformTuple(
			            Object[] tuple,
			            String[] aliases) {
			            return new GameDayCountDTO(			            					                
			                (java.sql.Date) tuple[0],
			                ((BigInteger) tuple[1]).intValue()
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

}
