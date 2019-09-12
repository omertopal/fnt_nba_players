package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.PERM_6_3;
import org.nba.players.model.PermModel;

public interface IPERM_6_3DAO {
	List<PermModel> getAllPerm();
	void save(PERM_6_3 perm63);
}
