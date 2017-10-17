package com.nba.players.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nba.players.entity.Player;

@Transactional
@Repository
public class PlayerDAO implements IPlayerDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Override
	public Player getPlayerById(int id) {
		return entityManager.find(Player.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Player> getAllPlayers() {
		String hql = "FROM Player ORDER BY id";
		return (List<Player>) entityManager.createQuery(hql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Player> getMyPlayers() {
		String hql = "FROM MyPlayers ORDER BY id";
		return (List<Player>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public void addPlayer(Player player) {
		entityManager.persist(player);
	}
	@Override
	public void updatePlayer(Player player) {
		Player plyr = getPlayerById(player.getId());
		plyr.setTeam(player.getTeam());
		plyr.setName(player.getName());
		entityManager.flush();
	}
	@Override
	public void deletePlayer(int id) {
		entityManager.remove(getPlayerById(id));
	}
	@Override
	public boolean playerExists(String name, String team) {
		String hql = "FROM Player  WHERE name = ? and team = ?";
		int count = entityManager.createQuery(hql).setParameter(1, name)
		              .setParameter(2, team).getResultList().size();
		return count > 0 ? true : false;
	}
}
