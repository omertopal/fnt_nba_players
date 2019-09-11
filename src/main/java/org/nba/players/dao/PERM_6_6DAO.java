package org.nba.players.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.nba.players.common.CommonUtils;
import org.nba.players.entity.PERM_6_1;
import org.nba.players.entity.PERM_6_6;
import org.nba.players.model.PermModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class PERM_6_6DAO implements IPERM_6_6DAO {
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PermModel> getAllPerm() {
		String hql = "FROM PERM_6_6";// order by if(pg=0,99,pg),if(sg=0,99,sg),if(sf=0,99,sf),if(pf=0,99,pf),if(c=0,99,c),if(ut=0,99,ut) ";
		try {
			List<PermModel> returnValue = CommonUtils.mapFromPermEntity(PERM_6_6.class,(ArrayList<PERM_6_6>) entityManager.createQuery(hql).getResultList());
			
			entityManager.close();
			return returnValue;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
