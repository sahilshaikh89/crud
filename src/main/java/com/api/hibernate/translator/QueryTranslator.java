package com.api.hibernate.translator;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.parsing.QualifierEntry;

import com.api.hibernate.exception.TranslationException;
import com.api.hibernate.model.Query;

public class QueryTranslator {
	
	public static org.hibernate.Query translateQuery(Query _query, Map<String, Object> props,Session session) throws TranslationException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		
		org.hibernate.Query qry = session.createQuery(_query.getHql());
		
		String[] params = qry.getNamedParameters();
		
		for(String param: params ){
			System.out.println("Param: " + param + " = " + props.get( param ));
			
			qry.setParameter(param, typeSafety(props.get( param )) );
		}
		
		if(_query.getResultTransformer() != null){
			qry.setResultTransformer((ResultTransformer) Class.forName(_query.getResultTransformer()).newInstance());
		}
		
		return qry;
	}
	public static Object typeSafety(Object param){
		
		Object check = longTypeSafe(param);
		if(!(check instanceof Double || check instanceof Long)){
			check = dateTypeSafe(param);
		}
		
		return check;
		
	}	
	private static Object dateTypeSafe(Object param){
		
		try {
			
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			  sdf.setTimeZone(TimeZone.getDefault());
			  return sdf.parse(param.toString());
			
		} catch (Exception e) {
			// NOt a Date . Return 
			return param;
		}
		
	}
	
	private static Object longTypeSafe(Object param){
		
		Double d;
		try {
			d = Double.parseDouble((String) param);
		} catch (Exception e) {
			//Double case exception. HEnce return the object as IS
			return param;
		}
		
		//CHECK IF DOUBLE CAN BE CASTED TO LONG
		if(Math.round(d) == d){
			return new Long(Math.round(d));
		}else{
			//return double
			return d;
		}
		
		
	}
	
}
