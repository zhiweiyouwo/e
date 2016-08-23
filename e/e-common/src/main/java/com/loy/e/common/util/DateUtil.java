package com.loy.e.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
public class DateUtil {

	public static final long ONE_DAY = 24*60*60*1000;
	public static Date addOneDay(Date date){
		Calendar   calendar=Calendar.getInstance(); 
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
}
