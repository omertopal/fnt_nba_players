package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.PERM_6_4;
import org.nba.players.model.PermModel;

public interface IPERM_6_4DAO {
	List<PermModel> getAllPerm();
	void save(PERM_6_4 perm64);
}
