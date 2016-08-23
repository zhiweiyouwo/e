package com.loy.e.core.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.loy.e.core.annotation.ConditionParam;
import com.loy.e.core.annotation.IgnoreField;
import com.loy.e.core.annotation.Order;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class QueryParamHelper {
	public final  static String delimiter = ".";
	public final  static String rootAlias = "x";
	
	public static  StringBuilder build(MapQueryParam mapQueryParam){
		Object param  = mapQueryParam.getParam();
		Map<String,Object> values  = mapQueryParam.getValues();
		StringBuilder ql = new StringBuilder("");
		ql.append(" where 1=1 ");
		Class<?> clazz = param.getClass();
		Field[] fields = clazz.getDeclaredFields();

		List<Order> orders = new ArrayList<Order>();
		for(Field f: fields) {		
			IgnoreField ignoreField = f.getAnnotation(IgnoreField.class);
			if(ignoreField != null){
				continue;
			}
			Object value = null;
			f.setAccessible(true);
			try {
				value = f.get(param);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			String attrName = f.getName();
			ConditionParam conditionParam = f.getAnnotation(ConditionParam.class);
			if(conditionParam != null){
				String paramName = conditionParam.name();
				Op opEnum = conditionParam.op();
				String op = opEnum.getInfo();
				if(value != null && value.toString() !=""){
					ql.append(" and ").append(QueryParamHelper.rootAlias).append(QueryParamHelper.delimiter)
					.append(paramName).append(" ").append( op).append(" :"+paramName);
					if(opEnum==Op.like){
						value = "%"+value+"%";
						values.put(paramName, value);
					}
				}
			}else{
				StringBuilder paramName  = new StringBuilder();
				char[] chars = attrName.toCharArray();
				for(Character k : chars){
					if(Character.isUpperCase(k)){
						Character c = Character.toLowerCase(k);
						paramName.append(".").append(c);
					}else{
						paramName.append(k);
					}
				}
				attrName = paramName.toString();
				ql.append(" and ").append(QueryParamHelper.rootAlias).append(QueryParamHelper.delimiter).append(attrName).append( " =:");

			}
			
			Order order = f.getAnnotation(Order.class);
			if(order != null){
				orders.add(order);
			}
		}
		
		if(orders.size()>0){
			ql.append(" order by ");
			List<String> tempList = new ArrayList<String>();
			for(Order o : orders){	
				String orderStr =  QueryParamHelper.rootAlias+QueryParamHelper.delimiter+o.name() + " " +o.direction().getInfo();
				tempList.add(orderStr);
			}
			Object orderProperityObj = values.get("orderProperty");
			if(orderProperityObj != null){
				String orderProperity = (String)orderProperityObj;
				if(StringUtils.isNotEmpty(orderProperity)){
					String direction =  (String)values.get("direction");
					if(direction == null){
						direction = "ASC";
					}
					String orderStr =  QueryParamHelper.rootAlias+QueryParamHelper.delimiter+orderProperity + " " +direction;
					tempList.add(orderStr);
				}
			}
			ql.append(StringUtils.join(tempList, ","));
		}else{
			Object orderProperityObj = values.get("orderProperty");
			if(orderProperityObj != null){
				String orderProperity = (String)orderProperityObj;
				if(StringUtils.isNotEmpty(orderProperity)){
					ql.append(" order by ");
					String direction =  (String)values.get("direction");
					if(direction == null){
						direction = "ASC";
					}
					String orderStr =  QueryParamHelper.rootAlias+QueryParamHelper.delimiter+orderProperity + " " +direction;
					ql.append(orderStr);
				}
			}
		}
		return ql;
	}

}