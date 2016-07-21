package com.hxp.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shanks on 15/4/21.
 */
public class DateUtil {

    /*
     *获取当天是本年第几周
     */
    public static int getCurrentWeek(){

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
      return   calendar.get(Calendar.WEEK_OF_YEAR)-2;
    }

    public static int getStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(df.format(date));

    }

    public static int getendDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(df.format(date));

    }
    //获取当前日期的字符串表达式
    public static String getCurrentDate(){
    	 Calendar calendar = Calendar.getInstance();
    	 Date date=calendar.getTime();
    	 SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
    	 String sdate=df.format(date);
    	 return sdate;
    }
	//获取当前日期的字符串表达式
	public static String getCurrentMonth(){
		Calendar calendar = Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		String sdate=df.format(date);
		return sdate;
	}
	//获取当前日期的字符串表达式
	public static String getCurrentMonthDataCable(){
		Calendar calendar = Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM");
		String sdate=df.format(date);
		return sdate;
	}
	//获取当前日期的字符串表达式
	public static String getCurrentSimpleDate(){
		Calendar calendar = Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ndate=df.format(date);
		return ndate;
	}

	//获取当前日期的字符串表达式
	public static Date getCurrentTime(){
		Calendar calendar = Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(date);
		try {
			return df.parse(format);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//获取当前日期的字符串表达式
	public static String getCurrentSimpleDateNoFormat(){
		Calendar calendar = Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String ndate=df.format(date);
		return ndate;
	}
	//获取当前日期的字符串表达式
	public static String getCurrentSimpleDateCable(){
		Calendar calendar = Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String ndate=df.format(date);
		return ndate;
	}
	/**
	 * 得到几天前的时间
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateTimeBefore(Date d, int day) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return sdf.format(now.getTime());
	}
	/**
	 * 得到几天前
	 *
	 * @param d
	 * @param day
	 * @return
	 */
	public static String getDateBefore(Date d, int day) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return sdf.format(now.getTime());
	}

	/**
	 *  d的基础上增加或减少分钟数.
	 *
	 * @param d
	 * @param minute
	 * @return
	 */
	public static String addMinute(Date d, int minute) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - minute);
		return sdf.format(now.getTime());
	}

	//获取当前日期的字符串表达式
    public static Date getDate(){
    	 Calendar calendar = Calendar.getInstance();
    	 Date date=calendar.getTime();
    	 return date;
    }
    //指定日期格式参数，给定字符串的日期参数，转换成该格式的日期
    public static Date toDate(String format,String time){
	   	 SimpleDateFormat df = new SimpleDateFormat(format);
//	   	 System.out.println(format);
	   	 Date date=null;
		
			if(time==null||"".equals(time)){
				return null;
			}
			try {
			date = df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	   	 return date;
    }
	//日期格式化为时间 形如:20150818215722
	public static Date stringToDate(String dateString,String format){
		Date date=null;
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		String newdate=dateString.substring(0,4)+"-"+dateString.substring(4,6)+"-"+dateString.substring(6,8)+" "+dateString.substring(8,10)+":"+dateString.substring(10,12)+":"+dateString.substring(12,14);
		try {
			date = sdf.parse(newdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	//日期格式化为时间  形如:2015-08-18
	public static Date stringToShortDate(String dateString,String format){
		Date date=null;
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		String newdate=dateString.substring(0,4)+"-"+dateString.substring(5,7)+"-"+dateString.substring(8,10);
		try {
			date = sdf.parse(newdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取当前日期，精确到日
	 * @author niudan
	 * @return
	 */
	public static Date getCurrDate(){
		Calendar calendar = Calendar.getInstance();
		Date currdate = calendar.getTime();
	    DateFormat dateFormat =	DateFormat.getDateInstance();
//		System.out.println("当前日期：" + formatDate("yyyy-MM-dd", currdate));
		return new Date(currdate.getYear(),currdate.getMonth(),currdate.getDate());
	}

	/**
	 * 获取增加天数后的日期
	 * @author niudan
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		Date currdate = calendar.getTime();
		return new Date(currdate.getYear(),currdate.getMonth(),currdate.getDate());
	}


	/**
	 * 返回时间间隔天数
	 * @param day
	 * @return
	 */
	public static int dayNum(String day){
		java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Calendar c1=java.util.Calendar.getInstance();
		java.util.Calendar c2=java.util.Calendar.getInstance();
		try
		{
			c1.setTime(df.parse(getCurrentSimpleDate()));
			c2.setTime(df.parse(day));
		}catch(java.text.ParseException e){
			System.err.println("格式不正确");
		}
		int result=c1.compareTo(c2);
		return result;
	}

	/**
	 * 格式化日期
	 * @author niudan
	 * @param format
	 * @param date
	 * @return
	 */
	public static String formatDate(String format, Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 获取当前月第一天
	 * @return
	 */
	public static String getCurrFirstDay(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append("000000");
		return str.toString();
	}
	public static String getCurrFirstDay2(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		return day_first;
	}
	/**
	 * 获取当前月最后一天
	 * @return
	 */
	public static String getCurrLastDay(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String s = df.format(calendar.getTime());
		StringBuffer str = new StringBuffer().append(s).append("235959");
		return str.toString();
	}

	/**
	 * 获取当前月最后一天
	 * @return
	 */
	public static Date getLastDay(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}
	/**
	 * 获取当前月的后一个月的第一天
	 * @return
	 */
	public static String getLaterFirstDay(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
//		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(date);
		gcLast.add(Calendar.MONTH, +1);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append("000000");
		return str.toString();
	}
	/**
	 * 获取当前月的后一个月的最后一天
	 * @return
	 */
	public static String getLaterLastDay(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
//		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(date);
		gcLast.add(Calendar.MONTH, +2);
		gcLast.set(Calendar.DAY_OF_MONTH, 0);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append("235959");
		return str.toString();
	}

	/**
	 * 根据传入日期得到前一个月的日期
	 * @param date
	 * @return
	 */
	public static Date getLastMonth(Date date){
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(date);
		gcLast.add(Calendar.MONTH, -1);
		return gcLast.getTime();
	}

	/**
	 * 获取星期几
	 * @param date
	 * @return
	 */
	public static Integer getDayofweek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Integer week = calendar.get(Calendar.DAY_OF_WEEK)-1;
		return week==0?7:week;
	}

    public static void main(String[] args) {
         System.out.println(isDate("2007-19-8"));
		//System.out.println(formatDate("yyyy-MM-dd HH:mm:ss", getLastDay(toDate("yyyy-MM-dd", "2015-12-15"))));

/*
		String billdate= DateUtil.formatDate("yyyyMMdd",new Date());
//billdate=billdate.replaceAll("\\-","");
		System.out.println(billdate);*/
		/*String oidflag="1120";
		String oid=oidflag.substring(2,oidflag.length());
		System.out.println(oid);
		String otag=oidflag.substring(0,2);
		System.out.println(otag);*/

//		System.out.println(getDayofweek(toDate("yyyy-MM-dd","2015-11-9")));

	/*	System.out.println(getCurrFirstDay(new Date()));
		System.out.println(getCurrLastDay(new Date()));*/
//		System.out.println(getCurrFirstDay(toDate("yyyy-MM-dd", "2015-9-9")));
//		System.out.println(getCurrLastDay(toDate("yyyy-MM-dd", "2015-9-9")));
//		System.out.println(getLaterFirstDay(toDate("yyyy-MM-dd", "2015-9-9")));
//		System.out.println(getLaterLastDay(toDate("yyyy-MM-dd", "2015-9-9")));

//		System.out.println(getDateTimeBefore(getDate(), 3));
//		System.out.println(getDateBefore(getDate(),1));
		/*HashMap<String ,Object>  x=new HashMap<String, Object>();
		x.put("a","123");
		x.put("b","456");

		System.out.println(x.containsKey("a"));*/

//		System.out.println(getCurrentDate());
//		Date date = new Date();
		/*Date date = toDate("yyyy-MM-dd HH:mm:ss","2015-8-1 1:1:0");
		System.out.println(toDate("yyyy-MM-dd",formatDate("yyyy-MM-dd",date)));
		System.out.println(DateUtil.formatDate("HH", date));
		System.out.println(Integer.parseInt(DateUtil.formatDate("HH", date)));
		System.out.println(Integer.parseInt(formatDate("mm", date)));*/
//		System.out.println();
		/*System.out.println(getDate());
		System.out.println(stringToDate(getCurrentSimpleDateNoFormat(), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(getCurrentSimpleDateNoFormat());
		*//*System.out.println(getCurrDate());
		System.out.println(Calendar.getInstance().getTime());*//*
		System.out.println(formatDate("yyyy/MM", getCurrDate()));
//		System.out.println(formatDate("yyyy-MM-dd", addDays(getCurrDate(),30)));
		System.out.println(DateUtil.getDateBefore(DateUtil.getDate(), 1));

		System.out.println("当月第一天: "+getCurrFirstDay());
		System.out.println("当月最后一天: "+getCurrLastDay());
		System.out.println("后一个月第一天: "+getLaterFirstDay());
		System.out.println("后一个月最后一天: "+getLaterLastDay());
		System.out.println(stringToDate(getCurrFirstDay(), "yyyy-MM-dd HH:mm:ss"));*/
//		System.out.println(Math.pow(10,6));s
//		System.out.println(2147483647+2);

		/*System.out.println(new Date());
		String a = "Mon Dec 21 15:20:11 CST 2015";
		System.out.println(convertStrToDate(a, "EEE MMM dd HH:mm:ss zzz yyyy","yyyy-MM-dd HH:mm:ss", Locale.US));*/

		/*Date d=new Date();
		System.out.println(addMinute(d,-10));*/


	}


	/**
	 * 根据传入的格式，日期化字符串
	 */
	public static String convertStrToDate(String dateStr, String source,String target,Locale locale) {
		SimpleDateFormat format = new SimpleDateFormat(source, locale);
		SimpleDateFormat formatNew = new SimpleDateFormat(target, locale);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (Exception e) {

		}
		return formatNew.format(date);
	}

	/**
	 * 将日期字符串按指定格式转换成日期类型
	 * @author dylan_xu
	 * @date Mar 11, 2012
	 * @param aMask 指定的日期格式，如:yyyy-MM-dd
	 * @param strDate 待转换的日期字符串
	 * @return
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);


		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {

			throw pe;
		}
		return (date);
	}

	/**
	 * 正则表达式验证日期格式
	 * @param str
	 * @return
     */
	public static final boolean isDate(String str)
	{

		boolean flag=false;
		//String eL = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";

		String eL= "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		if(b)
		{

			flag=true;
		}
		return flag;
	}



}
