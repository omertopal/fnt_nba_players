package org.nba.players.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.nba.players.entity.Team;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class TeamDAO implements ITeamDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Team> getAllTeams() {
		String hql = "FROM Team ";
		return (List<Team>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public Team getTeamByCode(String code) {
		return entityManager.find(Team.class, code);
	}

	@Override
	public void addTeam(Team team) {
		entityManager.persist(team);
	}

	@Override
	public void updateTeam(Team team) {
		Team updatedTeam = getTeamByCode(team.getCode());
		updatedTeam.setName(team.getName());
		entityManager.flush();
	}

	@Override
	public void deleteTeam(String code) {
		entityManager.remove(getTeamByCode(code));
	}

	@Override
	public boolean teamExists(String code) {
		String hql = "FROM Team  WHERE code = ?1 ";
		int count = entityManager.createQuery(hql).setParameter(1, code).getResultList().size();
		return count > 0 ? true : false;
	}

}
