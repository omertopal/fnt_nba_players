package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.PERM_6_2;
import org.nba.players.model.PermModel;

public interface IPERM_6_2DAO {
	List<PermModel> getAllPerm();
	void save(PERM_6_2 perm62);
}
