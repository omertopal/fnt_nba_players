package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.PERM_6_12;
import org.nba.players.model.PermModel;

public interface IPERM_6_12DAO {
	List<PermModel> getAllPerm();
	void save(PERM_6_12 perm612);
	public void deleteAll();
}
