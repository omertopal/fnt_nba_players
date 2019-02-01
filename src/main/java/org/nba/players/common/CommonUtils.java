package org.nba.players.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.nba.players.model.PermModel;
import org.nba.players.util.PlayerConstants;

public class CommonUtils {	
	
	public static final String STANDART_METHOD = "STD";
	public static final String OPTA_METHOD = "OPTA";
	
	public static HashMap<String,Integer> getPlayerSkills() {
    	HashMap<String,Integer> resultMap = new HashMap<String,Integer>();
    	resultMap.put(PlayerConstants.POINT_GUARD, 1 );
    	resultMap.put(PlayerConstants.SHOOTING_GUARD, 2);
    	resultMap.put(PlayerConstants.SMALL_FORWARD, 3);
    	resultMap.put(PlayerConstants.POWER_FORWARD, 4);
    	resultMap.put(PlayerConstants.CENTER, 5);
    	resultMap.put(PlayerConstants.UTIL_PLAYER, 6);
    	resultMap.put(PlayerConstants.BENCH_PLAYER_1, 7);
    	resultMap.put(PlayerConstants.BENCH_PLAYER_2, 8);
    	resultMap.put(PlayerConstants.BENCH_PLAYER_3, 9);
    	resultMap.put(PlayerConstants.BENCH_PLAYER_4, 10);
    	resultMap.put(PlayerConstants.BENCH_PLAYER_5, 11);
    	resultMap.put(PlayerConstants.BENCH_PLAYER_6, 12);
    	
        return resultMap;
    }
	
    
	public static HashMap<Integer,String> getPositionSlots(Integer myPlayersSize) {
    	HashMap<Integer,String> resultMap = new HashMap<Integer,String>();
    	resultMap.put(1, PlayerConstants.POINT_GUARD );
    	resultMap.put(2, PlayerConstants.SHOOTING_GUARD);
    	resultMap.put(3, PlayerConstants.SMALL_FORWARD);
    	resultMap.put(4, PlayerConstants.POWER_FORWARD);
    	resultMap.put(5, PlayerConstants.CENTER);
    	resultMap.put(6, PlayerConstants.UTIL_PLAYER);
    	resultMap.put(7, PlayerConstants.BENCH_PLAYER_1);
    	resultMap.put(8, PlayerConstants.BENCH_PLAYER_2);
    	resultMap.put(9, PlayerConstants.BENCH_PLAYER_3);
    	resultMap.put(10, PlayerConstants.BENCH_PLAYER_4);
    	resultMap.put(11, PlayerConstants.BENCH_PLAYER_5);
    	resultMap.put(12, PlayerConstants.BENCH_PLAYER_6);
    	
    	
        return resultMap;
    }
    
		
	
	@SuppressWarnings("rawtypes")
	public static <T> List<PermModel> mapFromPermEntity(Class<T> clazz, ArrayList<T> permutations) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class noparams[] = {};
		
		Method getPg = clazz.getDeclaredMethod("getPg", noparams);
		Method getSg = clazz.getDeclaredMethod("getSg", noparams);
		Method getSf = clazz.getDeclaredMethod("getSf", noparams);
		Method getPf = clazz.getDeclaredMethod("getPf", noparams);
		Method getC = clazz.getDeclaredMethod("getC", noparams);
		Method getUt = clazz.getDeclaredMethod("getUt", noparams);
		Method getId = clazz.getDeclaredMethod("getId", noparams);
		
	    List<PermModel> resultMap = new ArrayList<>();
		for (T curInstance: permutations) {
			PermModel currModel = new PermModel();
			Object obj = clazz.cast(curInstance);
			currModel.setPg((int) getPg.invoke(obj, null));
			currModel.setSg((int) getSg.invoke(obj, null));
			currModel.setSf((int) getSf.invoke(obj, null));
			currModel.setPf((int) getPf.invoke(obj, null));
			currModel.setC((int) getC.invoke(obj, null));
			currModel.setUt((int) getUt.invoke(obj, null));
			currModel.setId((int) getId.invoke(obj, null));
			resultMap.add(currModel);
		}
		return resultMap;
	}
}
