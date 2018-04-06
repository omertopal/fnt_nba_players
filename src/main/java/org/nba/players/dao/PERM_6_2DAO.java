package org.nba.players.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.nba.players.common.CommonUtils;
import org.nba.players.entity.PERM_6_1;
import org.nba.players.entity.PERM_6_2;
import org.nba.players.model.PermModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class PERM_6_2DAO implements IPERM_6_2DAO {
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PermModel> getAllPerm() {
		String hql = "FROM PERM_6_2 order by decode(pg,0,99,pg),decode(sg,0,99,sg),decode(sf,0,99,sf),decode(pf,0,99,pf),decode(c,0,99,c),decode(ut,0,99,ut) ";
		try {
			return CommonUtils.mapFromPermEntity(PERM_6_2.class,(ArrayList<PERM_6_2>) entityManager.createQuery(hql).getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
