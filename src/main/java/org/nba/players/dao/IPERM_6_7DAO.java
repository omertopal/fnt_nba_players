package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.PERM_6_7;
import org.nba.players.model.PermModel;

public interface IPERM_6_7DAO {
	List<PermModel> getAllPerm();
	void save(PERM_6_7 perm67);
	public void saveAll (List<PermModel> modelList);
	void deleteAll();
}
