package org.nba.players.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.nba.players.dto.PlayerDTO;
import org.nba.players.entity.Player;
import org.nba.players.entity.Team;
import org.nba.players.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class PlayerDAO implements IPlayerDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Autowired
	public ITeamService teamService;
	
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
		//String hql = "FROM MyPlayers ORDER BY avgPts desc";
		//return (List<MyPlayers>) entityManager.createQuery(hql).getResultList();
		
		return (List<Player>) entityManager.createNamedQuery("getMyAllPlayers").getResultList();
	}
	
	@Override
	public void addPlayer(PlayerDTO player) {
		
		Player newPlayer = new Player();
		newPlayer.setAvgPts(player.getAvgPts());
		newPlayer.setGamesPlayed(player.getGamesPlayed());
		newPlayer.setInjEnd(player.getInjEnd());
		newPlayer.setIsC(player.getIsC());
		newPlayer.setIsMy(player.getIsMy());
		newPlayer.setIsPF(player.getIsPF());
		newPlayer.setIsPG(player.getIsPG());
		newPlayer.setIsPotential(player.getIsPotential());
		newPlayer.setIsSF(player.getIsSF());
		newPlayer.setIsSG(player.getIsSG());
		newPlayer.setName(player.getName());
		newPlayer.setSira(player.getSira());
		
		Team team = teamService.getTeamByCode(player.getTeam());
		newPlayer.setTeam(team);
		entityManager.persist(newPlayer);
	}
	@Override
	public void updatePlayer(PlayerDTO player) {
		Player plyr = getPlayerById(player.getId());
		plyr.setSira(player.getSira());		
		plyr.setName(player.getName());
		plyr.setAvgPts(player.getAvgPts());
		plyr.setIsPG(player.getIsPG());
		plyr.setIsSG(player.getIsSG());
		plyr.setIsSF(player.getIsSF());
		plyr.setIsPF(player.getIsPF());
		plyr.setIsC(player.getIsC());
		plyr.setIsMy(player.getIsMy());
		plyr.setGamesPlayed(player.getGamesPlayed());
		plyr.setInjEnd(player.getInjEnd());
		plyr.setIsPotential(player.getIsPotential());
		
		Team team = teamService.getTeamByCode(player.getTeam());
		plyr.setTeam(team);
		
		entityManager.flush();
	}
	@Override
	public void deletePlayer(int id) {
		entityManager.remove(getPlayerById(id));
	}
	@Override
	public boolean playerExists(String name, String team) {
		String hql = "FROM Player  WHERE name = ?1 and team.code = ?2";
		int count = entityManager.createQuery(hql).setParameter(1, name)
		              .setParameter(2, team).getResultList().size();
		return count > 0 ? true : false;
	}
}
