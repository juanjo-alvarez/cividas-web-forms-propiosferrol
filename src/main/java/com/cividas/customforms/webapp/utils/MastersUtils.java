package com.cividas.customforms.webapp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MastersUtils {

	@SuppressWarnings({ "unused", "unchecked" })
	public static Map<String, String> parseMasterMap(Map<String, Object> originMap, String keyColumnName, String valueColumnName) {
		Map<String, String> parsedMap = new HashMap<String, String>();
		Object keyColumn = originMap.get(keyColumnName);
		Object valueColumn = originMap.get(valueColumnName);

		if (keyColumn instanceof String) {
			parsedMap.put(String.valueOf(keyColumn), String.valueOf(valueColumn));
		} else if (keyColumn instanceof List<?> && valueColumn instanceof List<?>) {
			List<?> keyList = (List<?>) keyColumn;
			List<?> valueList = (List<?>) valueColumn;

			for (int index = 0; index < keyList.size(); index++) {
				parsedMap.put(String.valueOf(keyList.get(index)), String.valueOf(valueList.get(index)));
			}
		} else if (keyColumn instanceof Long) {
			parsedMap.put(String.valueOf(keyColumn), String.valueOf(valueColumn));
		}
		return parsedMap;
	}

	public static Map<String, Object> parseMasterMapObj (Map<String, Object> originMap, String keyColumnName, String valueColumnName){
		Map<String, Object> parsedMap = new HashMap<>();
		Object keyColumn = originMap.get(keyColumnName);
		if (keyColumn instanceof String){
			parsedMap.put((String)keyColumn, originMap.get(valueColumnName));
		}else if (keyColumn instanceof List<?>){
			for (int index = 0; index < ((List<String>)keyColumn).size(); index++){
				parsedMap.put(String.valueOf(((List<String>)keyColumn).get(index)), ((List<Object>)originMap.get(valueColumnName)).get(index));
			}
		}
		return parsedMap;
	}
	
	
	public static LinkedHashMap<String,String> parseMasterLinkedHashMap (Map<String, Object> originMap, String keyColumnName, String valueColumnName){
		LinkedHashMap<String, String> sortedMap = new LinkedHashMap<String, String>();
		if (originMap != null) {
			LinkedHashMap <String, String> parsedMap = new LinkedHashMap<String, String>();
			Object keyColumn = originMap.get(keyColumnName);
			if (keyColumn instanceof String){
				parsedMap.put((String)keyColumn, (String)originMap.get(valueColumnName));
			}else if (keyColumn instanceof List<?>){
				for (int index = 0; index < ((List<String>)keyColumn).size(); index++){
					parsedMap.put(String.valueOf(((List<String>)keyColumn).get(index)), ((List<String>)originMap.get(valueColumnName)).get(index));
				}
			}
			
			// Ordenamos alfabéticamente la lista
			List<Map.Entry<String,String>> entries = new ArrayList<Map.Entry<String,String>>(parsedMap.entrySet());
			Collections.sort(entries,new Comparator<Map.Entry<String,String>>() {
				        public int compare(Map.Entry<String,String> a, Map.Entry<String,String> b) {
				            return a.getValue().compareTo(b.getValue());
				        }
				    }
				);
						
			for (Map.Entry<String, String> entry : entries) {
			  sortedMap.put(entry.getKey(), entry.getValue());
			}
		}
		return sortedMap;
	}
}
