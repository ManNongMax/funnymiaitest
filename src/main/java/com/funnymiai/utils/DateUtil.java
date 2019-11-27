 	package com.funnymiai.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间工具类
 */
public final class DateUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
	private DateUtil() {
	}

	/** 日期格式 **/
	public interface DATE_PATTERN {
		String HHMMSS = "HHmmss";
		String HH_MM_SS = "HH:mm:ss";
		String YYYY = "yyyy";
		String YYYYMMDD = "yyyyMMdd";
		String YYYY_MM_DD = "yyyy-MM-dd";
		String YYYY_MM = "yyyy-MM";
		String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
		String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
		String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
		String MM = "MM";
		String YYYYMM = "yyyyMM";
	}

	/**
	 * 将Date类型转换成String类型
	 * 
	 * @param date
	 *            Date对象
	 * @return 形如:"yyyy-MM-dd HH:mm:ss"
	 */
	public static String date2String(Date date) {
		return date2String(date, DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 将Date按格式转化成String
	 * 
	 * @param date
	 *            Date对象
	 * @param pattern
	 *            日期类型
	 * @return String
	 */
	public static String date2String(Date date, String pattern) {
		if (date == null || pattern == null){
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 将String类型转换成Date类型
	 * 
	 * @param date
	 *            Date对象
	 * @return
	 */
	public static Date string2Date(String date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			LOGGER.error("", "", e);
			return null;
		}
	}
	
	/**
	 * 获取某日期N天后的日期
	 * @param datestr
	 * @param day
	 * @return
	 * @throws DedicException 
	 */
	public static Date getBeforeAfterDate(String datestr, int day) throws DedicException {  
        SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);  
        java.sql.Date olddate = null;  
        try {  
            df.setLenient(false);  
            olddate = new java.sql.Date(df.parse(datestr).getTime());  
        } catch (ParseException e) {  
        	LOGGER.error("", "", e);
            throw new DedicException("日期转换错误");  
        }  
        Calendar cal = new GregorianCalendar();  
        cal.setTime(olddate);  
  
        int year = cal.get(Calendar.YEAR);  
        int month = cal.get(Calendar.MONTH);  
        int Day = cal.get(Calendar.DAY_OF_MONTH);  
  
        int NewDay = day + Day;  
  
        cal.set(Calendar.YEAR, year);  
        cal.set(Calendar.MONTH, month);  
        cal.set(Calendar.DAY_OF_MONTH, NewDay);  
  
        return new Date(cal.getTimeInMillis());  
    }  

    /**
     * 计算两个日期差的天数
     * 
     * @param fDate
     * @param oDate
     * @return
     */
    public static int daysBetween(Date fDate, Date oDate) {
        Calendar cNow = Calendar.getInstance();
        Calendar cReturnDate = Calendar.getInstance();
        cNow.setTime(oDate);
        cReturnDate.setTime(fDate);
        cNow.set(Calendar.HOUR_OF_DAY, 0);
        cNow.set(Calendar.MINUTE, 0);
        cNow.set(Calendar.SECOND, 0);
        cReturnDate.set(Calendar.HOUR_OF_DAY, 0);
        cReturnDate.set(Calendar.MINUTE, 0);
        cReturnDate.set(Calendar.SECOND, 0);
        long todayMs = cNow.getTimeInMillis();
        long returnMs = cReturnDate.getTimeInMillis();
        long intervalMs = todayMs - returnMs;
        return (int)(intervalMs / (1000 * 86400));
    }
    public static Date getNextHour(String dateStr){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.string2Date(dateStr));
		calendar.add(Calendar.HOUR_OF_DAY, 1);
    	return calendar.getTime();
    }
    
    /**
     * @Description: 获取当前日期的前一天
     * @return
     * @ReturnType String
     * @author: liyl
     * @Created 2015年11月13日 下午5:11:14
     */
    public static Date currentBeforeDay(){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
    	return calendar.getTime();
    }
    
    
    /**
     * @Description: 获取当前日期的后一天
     * @return
     * @ReturnType Date
     * @author: liyl
     * @Created 2015年11月13日 下午5:14:54
     */
    public static Date currentNextDay(){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
    	return calendar.getTime();
    }
 
    
    public static String firstDay(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
    	return date2String(calendar.getTime(),DATE_PATTERN.YYYY_MM_DD).concat(" 00:00:00");
    }
    public static String lastDay(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
		return date2String(calendar.getTime(),DATE_PATTERN.YYYY_MM_DD).concat(" 24:00:00");
	}
    /**
    * @author 黄承文
    * @Title: currentMonthNext
    * @Description: 当前时间N个月前后时间
    * @param @param i
    * @param @return    设定文件
    * @return Date    返回类型
    * @throws
    */
    public static Date monthDisplace(int i){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, i);
    	return calendar.getTime();
    }
    public static Date monthDisplace(Date date,int i){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, i);
    	return calendar.getTime();
    }
    /**
	 * 将Date类型转换成String类型
	 * 
	 * @param date
	 *            Date对象
	 * @return 形如:"MM"
	 */
	public static String date2MonthString(Date date) {
		return date2String(date, DATE_PATTERN.MM);
	}
	/**
	 * 将String类型转换成Date类型
	 * 
	 * @param date
	 *            Date对象
	 * @return
	 */
	public static Date stringToDate(String date,String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	public static int countMonths(String startTime,String endTime){
		Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        c1.setTime(string2Date(startTime));
        c2.setTime(string2Date(endTime));
        int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
        if(year<0){
            year=-year;
            return year*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
        }
        return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
	}
	public static Long getTime(Date date){
		return date.getTime();
	}
	public static String getYearMon(String dateStr){
		Date dt= string2Date(dateStr);
		String year=String.format("%tY", dt);
		String mon=String .format("%tm", dt);
		return year+mon;
	}
	
	/**
	 * 取一段时间的每一天
	 * 1、开始时间大于结束时间返回空
	 * 2、开始时间大于当天 返回空
	 * 3、开始时间等于结束时间 小于等于的当天返回一天
	 * 4、（1 2 成立）结束时间小于当天返回所有列表数据
	 * @param dateStartStr
	 * @param dateEndStr
	 * @param i i0 正序 1倒序 默认0
	 * @return
	 */
	public static List<String> getTwoDaysDay(String dateStartStr, String dateEndStr,String dateString,int i) {
		//空
		if(null==dateStartStr || "".equals(dateStartStr)) {
			return null;
		}
		//空
		if(null ==dateEndStr  || "".equals(dateEndStr)) {
			return null;
		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> dateList = new ArrayList<String>();
		try {
//			Date nowDay =new Date();
//			String nowDayStr = date2String(nowDay, DATE_PATTERN.YYYY_MM_DD);
			String nowDayStr = dateString; 
			Date startDateTemp = stringToDate(dateStartStr, DATE_PATTERN.YYYY_MM_DD);
			Date endDateTemp = stringToDate(dateEndStr, DATE_PATTERN.YYYY_MM_DD);
			//格式化一下字符串
			dateStartStr = date2String(startDateTemp, DATE_PATTERN.YYYY_MM_DD);
			dateEndStr = date2String(endDateTemp, DATE_PATTERN.YYYY_MM_DD);
			//开始时间大于结束时间返回空
			if(dateStartStr.compareTo(nowDayStr)==0) {
				dateList.add(dateStartStr);
				return dateList;
			}else if(dateStartStr.compareTo(nowDayStr)>0){
				return null;
			}
			if(dateStartStr.compareTo(dateEndStr)>0) {
				return null;
			}else if(dateStartStr.compareTo(dateEndStr)==0){
				if(dateEndStr.compareTo(nowDayStr)<=0) {
					dateList.add(dateStartStr);
					return dateList;
				}else {
					return null;
				}
			}else {
				Calendar endcalendar = Calendar.getInstance();
				if(dateEndStr.compareTo(nowDayStr)>=0) {
					dateEndStr=nowDayStr;
					Date nowDateTemp = stringToDate(nowDayStr, DATE_PATTERN.YYYY_MM_DD);
					endDateTemp=nowDateTemp;
				}
				endcalendar.setTime(endDateTemp);
				dateList.add(dateEndStr);
				while (endcalendar.getTime().after(startDateTemp)) { // 倒序时间,顺序after改before其他相应的改动。
					endcalendar.add(Calendar.DAY_OF_MONTH, -1);
					dateList.add(date2String(endcalendar.getTime(), DATE_PATTERN.YYYY_MM_DD));
				}
				//正序
				if(i !=1) {
					Collections.sort(dateList, new Comparator<String>() {
			            public int compare(String o1, String o2) {
			                return o1.compareTo(o2);
			            }
			        });
				}
			}
			
		} catch (Exception e) {
			return null;
		}
		return dateList;
	}
	
	/*public static void main(String[] args) {
		 Date dt=new Date();

		   String year=String.format("%tY", dt);

		   String mon=String .format("%tm", dt);
		   System.err.println(year+"-"+mon);
	}*/
}
