package org.nba.players.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.nba.players.common.CommonUtils;
import org.nba.players.entity.PERM_6_10;
import org.nba.players.entity.PERM_6_7;
import org.nba.players.entity.Player;
import org.nba.players.model.PermModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class PERM_6_7DAO implements IPERM_6_7DAO {
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Override
	public void save(PERM_6_7 perm67) {
		entityManager.persist(perm67);
	}
	
	
    
    @Override
	public void deleteAll() {    	
        String hql = " delete from  perm_6_7 ";
		entityManager.createNativeQuery(hql).executeUpdate();
		entityManager.close();
	}
    
    @Override
	public void saveAll (List<PermModel> modelList) {
		Iterator<PermModel> iterator = modelList.iterator();
		int cont = 0;
		while (iterator.hasNext()) {
			PermModel model = iterator.next();
			PERM_6_7 entity = new PERM_6_7(model.getPg(),model.getSg(),model.getSf(),model.getPf(),model.getC(),model.getUt());
		    entityManager.persist(entity);
		    cont++;
		    if (cont % 1000 == 0) {
		        entityManager.flush();
		        entityManager.clear();
		    }
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PermModel> getAllPerm() {
		String hql = "FROM PERM_6_7";// order by if(pg=0,99,pg),if(sg=0,99,sg),if(sf=0,99,sf),if(pf=0,99,pf),if(c=0,99,c),if(ut=0,99,ut) ";
		try {
			List<PermModel> returnValue = CommonUtils.mapFromPermEntity(PERM_6_7.class,(ArrayList<PERM_6_7>) entityManager.createQuery(hql).getResultList());
			
			entityManager.close();
			return returnValue;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
