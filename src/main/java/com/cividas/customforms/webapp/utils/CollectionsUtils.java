package com.cividas.customforms.webapp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsUtils {

	
	public static Map<Object, Object> buildMap (Object... dataandkeys) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		int total = dataandkeys.length;
		for (int i = 0; i < total; i += 2)
			if(dataandkeys[i] != null && dataandkeys[i + 1] != null) {
				result.put(dataandkeys[i].toString(), dataandkeys[i + 1]);
			}

		return result;
	}


	public static List<Object> buildList(String... vectordata) {
		List<Object> result = new ArrayList<Object>();
		for (String o : vectordata)
			result.add(o);
		return result;
	}
	
}
