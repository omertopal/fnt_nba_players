package org.nba.players.dao;

import java.util.List;

import org.nba.players.entity.PERM_6_8;
import org.nba.players.model.PermModel;

public interface IPERM_6_8DAO {
	List<PermModel> getAllPerm();
	void save(PERM_6_8 perm68);
	public void deleteAll();
	public void saveAll (List<PermModel> modelList);
}
