package org.nba.players.dao;

import java.util.List;

import org.nba.players.model.PermModel;
import org.nba.players.entity.PERM_6_10;

public interface IPERM_6_10DAO {
	List<PermModel> getAllPerm();
	void save(PERM_6_10 perm610);
	public void deleteAll();
}
