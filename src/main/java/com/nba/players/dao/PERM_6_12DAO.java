package com.nba.players.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nba.players.entity.PERM_6_11;
import com.nba.players.entity.PERM_6_12;
import com.nba.players.model.PermModel;

@Transactional
@Repository
public class PERM_6_12DAO implements IPERM_6_12DAO {
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PermModel> getAllPERM_6_12() {
		String hql = "FROM PERM_6_12  order by decode(pg,0,99,pg),decode(sg,0,99,sg),decode(sf,0,99,sf),decode(pf,0,99,pf),decode(c,0,99,c),decode(ut,0,99,ut) ";
		return mapFromPERM_6_12((List<PERM_6_12>) entityManager.createQuery(hql).getResultList());
	}
	
	public List<PermModel> mapFromPERM_6_12 (List<PERM_6_12> permutations){
		List<PermModel> resultMap = new ArrayList<>();
		for (PERM_6_12 curInstance: permutations) {
			PermModel currModel = new PermModel();
			currModel.setPg(curInstance.getPg());
			currModel.setSg(curInstance.getSg());
			currModel.setSf(curInstance.getSf());
			currModel.setPf(curInstance.getPf());
			currModel.setC(curInstance.getC());
			currModel.setUt(curInstance.getUt());
			currModel.setId(curInstance.getId());
			resultMap.add(currModel);
		}
		return resultMap;
	}
}
