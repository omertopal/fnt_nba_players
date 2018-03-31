package com.nba.players.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nba.players.common.CommonUtils;
import com.nba.players.entity.PERM_6_10;
import com.nba.players.model.PermModel;

@Transactional
@Repository
public class PERM_6_10DAO implements IPERM_6_10DAO {
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PermModel> getAllPerm() {
		String hql = "FROM PERM_6_10 order by decode(pg,0,99,pg),decode(sg,0,99,sg),decode(sf,0,99,sf),decode(pf,0,99,pf),decode(c,0,99,c),decode(ut,0,99,ut) ";
		try {
			return CommonUtils.mapFromPermEntity(PERM_6_10.class,(ArrayList<PERM_6_10>) entityManager.createQuery(hql).getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
