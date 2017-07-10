package com.crud.sample.query.transformer;

import java.util.AbstractMap;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map.Entry;

import org.hibernate.transform.ResultTransformer;

public class QuerySsoAppTransformer implements ResultTransformer{

	public static enum ALIAS {APP_NAME, CAT_NAME};
	
	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> aliasList = new ArrayList<String>(Arrays.asList(aliases));
		
		map.put("app", tuple[aliasList.indexOf(ALIAS.APP_NAME.name())]);
		map.put("category", tuple[aliasList.indexOf(ALIAS.CAT_NAME.name())]);
				
		return map;
	}

	@Override
	public List transformList(List collection) {
		
		return collection;
	}

	
	
}
