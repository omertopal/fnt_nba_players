package org.nba.players.service;

import java.util.ArrayList;
import java.util.List;

import org.nba.players.dao.IPERM_6_10DAO;
import org.nba.players.dao.IPERM_6_11DAO;
import org.nba.players.dao.IPERM_6_12DAO;
import org.nba.players.dao.IPERM_6_1DAO;
import org.nba.players.dao.IPERM_6_2DAO;
import org.nba.players.dao.IPERM_6_3DAO;
import org.nba.players.dao.IPERM_6_4DAO;
import org.nba.players.dao.IPERM_6_5DAO;
import org.nba.players.dao.IPERM_6_6DAO;
import org.nba.players.dao.IPERM_6_7DAO;
import org.nba.players.dao.IPERM_6_8DAO;
import org.nba.players.dao.IPERM_6_9DAO;
import org.nba.players.entity.PERM_6_11;
import org.nba.players.entity.PERM_6_12;
import org.nba.players.entity.PERM_6_7;
import org.nba.players.entity.PERM_6_8;
import org.nba.players.entity.PERM_6_9;
import org.nba.players.model.PermModel;
import org.nba.players.model.PlayerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermutationService implements IPermService{
	
	@Autowired
	private IPERM_6_1DAO perm6_1DAO;
	@Autowired
	private IPERM_6_2DAO perm6_2DAO;
	@Autowired
	private IPERM_6_3DAO perm6_3DAO;
	@Autowired
	private IPERM_6_4DAO perm6_4DAO;
	@Autowired
	private IPERM_6_5DAO perm6_5DAO;
	@Autowired
	private IPERM_6_6DAO perm6_6DAO;
	@Autowired
	private IPERM_6_7DAO perm6_7DAO;
	@Autowired
	private IPERM_6_8DAO perm6_8DAO;
	@Autowired
	private IPERM_6_9DAO perm6_9DAO;
	@Autowired
	private IPERM_6_10DAO perm6_10DAO;
	@Autowired
	private IPERM_6_11DAO perm6_11DAO;
	@Autowired
	private IPERM_6_12DAO perm6_12DAO;
	
	@Override
	public void generatePermutations(int size){
		List<PermModel> modelList = new ArrayList<PermModel>();
		if(size == 7) {
			perm6_7DAO.deleteAll();
			permute(modelList,new int[]{1,2,3,4,5,6,7},6,7);
		}else if(size == 8) {
			perm6_8DAO.deleteAll();
			permute(modelList,new int[]{1,2,3,4,5,6,7,8},6,8);
		}else if (size == 9) {
			perm6_9DAO.deleteAll();
			permute(modelList,new int[]{1,2,3,4,5,6,7,8,9},6,9);
		}else if (size == 10) {
			perm6_10DAO.deleteAll();
			permute(modelList,new int[]{1,2,3,4,5,6,7,8,9,10},6,10);
			perm6_10DAO.saveAll(modelList);
		}else if(size == 11){
			perm6_11DAO.deleteAll();
			permute(modelList,new int[]{1,2,3,4,5,6,7,8,9,10,11},6,11);
		}else if(size == 12){
			perm6_12DAO.deleteAll();
			permute(modelList,new int[]{1,2,3,4,5,6,7,8,9,10,11,12},6,12);
		}
		
	}

	private void writeToDB(List<PermModel> modelList, int originalSize, int a[]) {
		modelList.add(new PermModel(a[0],a[1],a[2],a[3],a[4],a[5]));
		
		if(modelList.size() >= 5000) {	
			if(originalSize == 7) {
				perm6_7DAO.save(new PERM_6_7(a[0],a[1],a[2],a[3],a[4],a[5]));
			}else if(originalSize == 8) {
				perm6_8DAO.save(new PERM_6_8(a[0],a[1],a[2],a[3],a[4],a[5]));
			}else if (originalSize == 9) {
				perm6_9DAO.save(new PERM_6_9(a[0],a[1],a[2],a[3],a[4],a[5]));
			}else if (originalSize == 10) {
				perm6_10DAO.saveAll(modelList);
				modelList.clear();
			}else if(originalSize == 11){
				perm6_11DAO.save(new PERM_6_11(a[0],a[1],a[2],a[3],a[4],a[5]));
			}else if(originalSize == 12){
				perm6_12DAO.save(new PERM_6_12(a[0],a[1],a[2],a[3],a[4],a[5]));
			}
		}
	}
	
	
	private static void swap(int[] v, int i, int j)
    {
        int t = v[i];
        v[i] = v[j];
        v[j] = t;
    }
 
    public void permute(List<PermModel> modelList,int[] v, int n,int originalSize)
    {
        if (n == 1)
        {
        	writeToDB(modelList,originalSize,v);
        }
        else
        {
            for (int i = 0; i < n; i++)
            {
                permute(modelList,v, n - 1,originalSize);
                if (n % 2 == 1)
                {
                    swap(v, 0, n - 1);
                }
                else
                {
                    swap(v, i, n - 1);
                }
            }
        }
    }
	
	public List<PermModel>  getPermutations (List<PlayerModel> myPlayersToday){
		if(myPlayersToday.size()==1){
			return perm6_1DAO.getAllPerm();				
		}else if(myPlayersToday.size()==2){
			return perm6_2DAO.getAllPerm();				
		}else if(myPlayersToday.size()==3){
			return perm6_3DAO.getAllPerm();				
		}else if(myPlayersToday.size()==4){
			return perm6_4DAO.getAllPerm();				
		}else if(myPlayersToday.size()==5){
			return perm6_5DAO.getAllPerm();				
		}else if(myPlayersToday.size()==6){
			return perm6_6DAO.getAllPerm();				
		}else if(myPlayersToday.size()==7){
			return perm6_7DAO.getAllPerm();				
		}else if(myPlayersToday.size()==8){
			return perm6_8DAO.getAllPerm();				
		}else if(myPlayersToday.size()==9){
			return perm6_9DAO.getAllPerm();				
		}else if(myPlayersToday.size()==10){
			return perm6_10DAO.getAllPerm();				
		}else if(myPlayersToday.size()==11){
			return perm6_11DAO.getAllPerm();				
		}else if(myPlayersToday.size()==12){
			return perm6_12DAO.getAllPerm();				
		}else{
			return null;
		}			
	}
}
