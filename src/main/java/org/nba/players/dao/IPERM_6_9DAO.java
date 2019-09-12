package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.PERM_6_9;
import org.nba.players.model.PermModel;

public interface IPERM_6_9DAO {
	List<PermModel> getAllPerm();
	void save(PERM_6_9 perm69);
	public void deleteAll();
}
