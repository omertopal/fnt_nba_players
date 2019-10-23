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
	
		
	
	// Maintain a global counter. After finding a permutation, increment this. 
    private static int count = 0;
    
    private static List<PermModel> modelList = new ArrayList<PermModel>();

    // pos is the current index, and K is the length of permutation you want to print, and N is the number of permutation you want to print.
    private static void printPermutations(int[] arr, int[] visited, int pos, int K, int N, List<Integer> permutationResult) {

        // We have already found N number of permutations. We don't need anymore. So just return.
        if (count == N) {
            return;
        }
        if (pos == K) {
        	modelList.add(new PermModel(permutationResult.get(0), permutationResult.get(1), permutationResult.get(2), 
        								permutationResult.get(3), permutationResult.get(4), permutationResult.get(5)));  
        	if(modelList.size()%10000 == 0) {
        		System.out.println(modelList.size());        		
        	}
            count++; // we have found a valid permutation, increment counter.
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            // Only recur if the ith element is not visited.
            if (visited[i] == 0) {
                // mark ith element as visited.
                visited[i] = 1;
                List<Integer> permutationResultNew = new ArrayList<Integer>();
                permutationResultNew.addAll(permutationResult);
                permutationResultNew.add(arr[i]);
                printPermutations(arr, visited, pos + 1, K, N,permutationResultNew);
                // unmark ith element as visited.
                visited[i] = 0;
            }
        }

    }

	
	@Override
	public void generatePermutations(int size){
		modelList.clear();
		if(size == 7) {
			perm6_7DAO.deleteAll();	
			int[] arr = {1, 2, 3, 4, 5, 6, 7};
	        int[] visited = {0, 0, 0, 0, 0, 0, 0}; // same as size of input array.
	        count = 0; // make sure to reset this counter everytime you call printPermutations.
	        List<Integer> permutationResult = new ArrayList<Integer>();
	        printPermutations(arr, visited, 0, 6, 5040, permutationResult);
	        perm6_7DAO.saveAll(modelList);
	        modelList.clear();
		}else if(size == 8) {
			perm6_8DAO.deleteAll();	
			int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
	        int[] visited = {0, 0, 0, 0, 0, 0, 0, 0 }; // same as size of input array.
	        count = 0; // make sure to reset this counter everytime you call printPermutations.
	        List<Integer> permutationResult = new ArrayList<Integer>();
	        printPermutations(arr, visited, 0, 6, 20160, permutationResult);
	        perm6_8DAO.saveAll(modelList);
	        modelList.clear();
		}else if (size == 9) {
			perm6_9DAO.deleteAll();	
			int[] arr =     {1, 2, 3, 4, 5, 6, 7, 8, 9};
	        int[] visited = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // same as size of input array.
	        count = 0; // make sure to reset this counter everytime you call printPermutations.
	        List<Integer> permutationResult = new ArrayList<Integer>();
	        printPermutations(arr, visited, 0, 6, 60480, permutationResult);
	        perm6_9DAO.saveAll(modelList);
	        modelList.clear();	
		}else if (size == 10) {
			perm6_10DAO.deleteAll();	
			int[] arr =     {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	        int[] visited = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // same as size of input array.
	        count = 0; // make sure to reset this counter everytime you call printPermutations.
	        List<Integer> permutationResult = new ArrayList<Integer>();
	        printPermutations(arr, visited, 0, 6, 151200, permutationResult);
	        perm6_10DAO.saveAll(modelList);
	        modelList.clear();		
	        
	    }else if(size == 11){
	    	perm6_11DAO.deleteAll();	
			int[] arr =     {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
	        int[] visited = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0}; // same as size of input array.
	        count = 0; // make sure to reset this counter everytime you call printPermutations.
	        List<Integer> permutationResult = new ArrayList<Integer>();
	        printPermutations(arr, visited, 0, 6, 332640, permutationResult);
	        perm6_11DAO.saveAll(modelList);
	        modelList.clear();
	        
		}else if(size == 12){
			perm6_12DAO.deleteAll();	
			int[] arr =     {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	        int[] visited = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  0, 0}; // same as size of input array.
	        count = 0; // make sure to reset this counter everytime you call printPermutations.
	        List<Integer> permutationResult = new ArrayList<Integer>();
	        printPermutations(arr, visited, 0, 6, 665280, permutationResult);
	        perm6_12DAO.saveAll(modelList);
	        modelList.clear();
		}
		
	}
	
}
