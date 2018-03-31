package com.nba.players.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.nba.players.model.PermModel;

public class CommonUtils {	
		
	
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
