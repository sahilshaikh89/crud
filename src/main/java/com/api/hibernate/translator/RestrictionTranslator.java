package com.api.hibernate.translator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.JoinType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.TransactionException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.api.hibernate.model.Alias;
import com.api.hibernate.model.Order;
import com.api.hibernate.model.Projection;
import com.api.hibernate.model.Restriction;
import com.api.hibernate.model.Restriction.OPERATORS;
import com.google.gson.Gson;
import com.api.hibernate.model.Restrictions;

public class RestrictionTranslator {

	static Class clazz = org.hibernate.criterion.Restrictions.class;

	public static Criteria translate(Restrictions restrictions, Map<String, Object> props, Criteria cr) throws TransactionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{

		props = sanitizeMap(props);


		for(Alias alias : restrictions.getAliases()){
			if(alias.getJoinType() != null)
				cr.createAlias(alias.getProp(), alias.getAlias(), alias.getJoinType().getValue());
			else
				cr.createAlias(alias.getProp(), alias.getAlias());
		}

		if(restrictions.getRestrictions() != null && !restrictions.getRestrictions().isEmpty())
			cr = translateRestrictions(restrictions, props, cr);
		
		if(restrictions.getProjectionList() != null && !restrictions.getProjectionList().isEmpty())
			cr = translateProjections(restrictions.getProjectionList(), cr);
		
		if(restrictions.getOrderList() != null && !restrictions.getOrderList().isEmpty())
			cr = translateOrders(restrictions.getOrderList(), cr);

		return cr;
	}

	private static Criteria translateOrders(Collection<Order> orderList, Criteria cr) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Method method = null;
		
		for(Order order : orderList){
			
			switch (order.getOrder()) {
			case asc:
			case desc:{
				method = org.hibernate.criterion.Order.class.getMethod(order.getOrder().name(), String.class);		
				cr.addOrder(
					(org.hibernate.criterion.Order) method.invoke(null, order.getProperty())
					);
				
				break;
			}

			default:
				throw new NoSuchMethodException("No Order of type " + order.getOrder().name() + " exists.");
			}
		}		
		
		return cr;
	}

	private static Criteria translateProjections(Collection<Projection> projectionList, Criteria cr) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ProjectionList list = Projections.projectionList();	
		
		Method method = null;
		
		for(Projection projection : projectionList){
			
			switch (projection.getProjection()) {
			case count:
			case rowCount:
			case max:
			case min:
			case avg:
			case sum:
			case groupProperty:
			case property: {
				method = Projections.class.getMethod(projection.getProjection().name(), String.class);			
				list.add(
						(org.hibernate.criterion.Projection) method.invoke( null, projection.getProp())
				);			
				
				break;
			}
				
			default:
				throw new NoSuchMethodException("No method with Projection " + projection.getProjection().name() + " exists.");				
			}
			
		}				
		
		return cr.setProjection(list);
	}

	private static Criteria translateRestrictions(Restrictions restrictions, Map<String, Object> props, Criteria cr) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		List<Restriction> restrictionList = (List<Restriction>) restrictions.getRestrictions();

		for(Restriction restrict : restrictionList){
			switch (restrict.getOperator()) {
			case distinct:{
				cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
				break;
			}
			
			default:
				cr.add( (Criterion) createCriterion(restrict, props) );	
				break;
			}		
		}
		return cr;
	}

	private static Object createCriterion(Restriction restrict, Map<String, Object> props) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		Method method = null;
		Object criterion = null;
		Restriction.OPERATORS op = restrict.getOperator();		

		switch (op) {
		case eq :
		case ne:
		case like:
		case ilike:
		case gt:
		case lt:
		case le:
		case ge:
		case between: {
			method = clazz.getMethod(op.name(), String.class, Object.class);			
			criterion = method.invoke( null, restrict.getProperty(), 
					props.get(restrict.getProperty()) == null ? restrict.getValue() : props.get(restrict.getProperty())
					);

			break;
		}
		case in: {
			
			//Get String Array
			String strArray = props.get(restrict.getProperty()).toString();
			
			Gson gson = new Gson();
			Object[] objArr = gson.fromJson(strArray, Object[].class);
			
			for(Object obj : objArr){
				System.out.println(obj.toString());
			}
			
			method = clazz.getMethod(op.name(), String.class, Object[].class);
			criterion = method.invoke( null, restrict.getProperty(), objArr );

			break;
		}
		case isEmpty:
		case isNotEmpty:{
			method = clazz.getMethod(op.name(), String.class);
			criterion = method.invoke( null, restrict.getProperty());
			break;
		}
		case sizeEq:
		case sizeNe:
		case sizeGt:
		case sizeLt:
		case sizeGe:
		case sizeLe:{
			method = clazz.getMethod(op.name(), String.class, int.class);
			criterion = method.invoke( null, restrict.getProperty(), 
					props.get(restrict.getProperty()) == null ? restrict.getValue() : props.get(restrict.getProperty()) 
					);
			break;
		}
		case and:{

			Junction conditionGroup = org.hibernate.criterion.Restrictions.conjunction();
			List<Restriction> restrictionList = (List<Restriction>) restrict.getRestriction();

			for(Restriction restrictLogical : restrictionList){			
				conditionGroup.add( (Criterion) createCriterion(restrictLogical, props) );			
			}

			criterion = conditionGroup;			
			break;
		}
		case or: {
			Junction conditionGroup = org.hibernate.criterion.Restrictions.disjunction();
			List<Restriction> restrictionList = (List<Restriction>) restrict.getRestriction();

			for(Restriction restrictLogical : restrictionList){			
				conditionGroup.add( (Criterion) createCriterion(restrictLogical, props) );			
			}

			criterion = conditionGroup;			
			break;
		}
		case not: {			
			List<Restriction> restrictionList = (List<Restriction>) restrict.getRestriction();

			if(restrictionList.size() > 1)
				throw new NoSuchMethodException("NOT logical operator must have only one restriction.");

			method = clazz.getMethod(op.name(), Criterion.class);

			criterion = method.invoke( null, 
					(Criterion) createCriterion(restrictionList.get(0), props)
					);			

			break;
		}
		case isNull:
		case isNotNull:{
			method = clazz.getMethod(op.name(), String.class);
			criterion = method.invoke( null, restrict.getProperty() );
			
			break;
		}
		
		default:
			throw new NoSuchMethodException("No method with operator " + op + " exists.");			
		}

		return criterion;		
	}

	protected static Map<String, Object> sanitizeMap(Map<String, Object> props){

		Map<String, Object> sanitizedProp = new HashMap<String, Object>();

		Iterator itr = props.entrySet().iterator();

		while( itr.hasNext() ){
			Entry<String, Object> entry = (Entry<String, Object>) itr.next();

			String key = entry.getKey().replace("->", ".");			
			sanitizedProp.put(key, entry.getValue());
		}
		return sanitizedProp;

	}

}
