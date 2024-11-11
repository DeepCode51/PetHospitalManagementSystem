package com.phms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * class name:MyUtils <BR>
 */
public class MyUtils {
	public static String START_HOUR = " 00:00:00";
	public static String END_HOUR = " 23:59:59";

	private MyUtils() {
		throw new IllegalStateException("Utility class");
	}

	/** logback日志记录 */
	private static final Logger logger = LoggerFactory.getLogger(MyUtils.class);

	/**
	 * Method name: isEmail <BR>
	 * Description: 判断是不是邮箱,是就返回true <BR>
	 * Remark: <BR>
	 * 
	 * @param email
	 * @return boolean<BR>
	 */
	public static boolean isEmail(String email) {
		String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		if (email.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method name: isPhoneNum <BR>
	 * Description: 判断手机号是不是正确,是就返回true <BR>
	 * Remark: <BR>
	 * 
	 * @param phoneNume
	 * @return boolean<BR>
	 */
	public static boolean isPhoneNum(String phoneNume) {
		String pattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
		if (phoneNume.matches(pattern)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method name: nowDate <BR>
	 * Description: 返回当前日期和时间yyyy-MM-dd HH:mm:ss <BR>
	 * Remark: <BR>
	 * 
	 * @return String<BR>
	 */
	public static String getNowDateTime() {
		String dateTime = "";
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		dateTime = sdf.format(date);
		return dateTime;
	}

	/**
	 * Method name: getNowDateYMD <BR>
	 * Description: 返回当前日期和时间 yyyy-MM-dd <BR>
	 * Remark: <BR>
	 * 
	 * @return String<BR>
	 */
	public static String getNowDateYMD() {
		String dateTime = "";
		String pattern = "yyyy-MM-dd";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		dateTime = sdf.format(date);
		return dateTime;
	}

	/**
	 * Method name: getNowDateCHYMD <BR>
	 * Description: 返回当前日期和时间 yyyy年MM月dd日<BR>
	 * Remark: <BR>
	 * 
	 * @return String<BR>
	 */
	public static String getNowDateCHYMD() {
		String dateTime = "";
		String pattern = "yyyy年MM月dd日";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		dateTime = sdf.format(date);
		return dateTime;
	}

	/**
	 * Method name: getAutoNumber <BR>
	 * Description: 根据时间获取编号:年月日+4位数字 <BR>
	 * Remark: 格式:201809200001 <BR>
	 * 
	 * @return String<BR>
	 */
	public static synchronized String getAutoNumber() {
		String autoNumber = "";
		int number = 0;
		String oldDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowDate = sdf.format(new Date());
		File f2 = new File(MyUtils.class.getResource("").getPath());
		String path = f2.getAbsolutePath();

		File f = new File(path + "/date.txt");

		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = "";
			try {
				line = br.readLine();
				String[] sb = line.split(",");
				oldDate = sb[0];
				if (oldDate.equals(nowDate)) {
					number = Integer.parseInt(sb[1]);
				} else {
					number = 0;
				}
				br.close();
			} catch (IOException e) {
				logger.error("根据时间获取编号出现异常", e);
			}

			autoNumber += nowDate;
			number++;
			int i = 1;
			int n = number;
			while ((n = n / 10) != 0) {
				i++;
			}
			for (int j = 0; j < 4 - i; j++) {
				autoNumber += "0";
			}
			autoNumber += number;

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				bw.write(nowDate + "," + number);
				bw.close();
			} catch (IOException e) {
				logger.error("根据时间获取编号出现异常", e);
			}

		} catch (FileNotFoundException e) {
			logger.error("根据时间获取编号出现异常", e);
		}

		return autoNumber;
	}

	/**
	 * Method name: get2DateDay <BR>
	 * Description: 获取两个日期之间的天数 <BR>
	 * Remark: 如2018-09-01 和 2018-09-017 返回就是17天<BR>
	 * 
	 * @param startDate
	 * @param endDate
	 * @return int<BR>
	 */
	public static int get2DateDay(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date date2 = null;
		long days = 0;
		try {
			date1 = sdf.parse(startDate);
			date2 = sdf.parse(endDate);
			days = (date2.getTime() - date1.getTime()) / (24 * 3600 * 1000);

		} catch (ParseException e) {
			logger.error("获取两个日期之间的天数出现异常", e);
		}

		return (int) days + 1;
	}

	/**
	 * Method name: toLowCase <BR>
	 * Description: 第一个字母小写 <BR>
	 * Remark: <BR>
	 * 
	 * @param s
	 * @return String<BR>
	 */
	public static String toLowCase(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1, s.length());
	}

	/**
	 * Method name: setStartUP <BR>
	 * Description: 第一个字母大写 <BR>
	 * Remark: <BR>
	 * 
	 * @param s
	 * @return String<BR>
	 */
	public static String setStartUP(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
	}

	/**
	 * Method name: getUp_ClassName <BR>
	 * Description: 根据表名获取类名不带后缀Bean <BR>
	 * Remark: <BR>
	 * 
	 * @param s
	 * @return String<BR>
	 */
	public static String getUp_ClassName(String s) {
		String cName = "";
		// 首字母大写
		cName = s.substring(1, 2).toUpperCase() + s.substring(2, s.length());

		String[] tem = cName.split("_");
		int len = tem.length;
		cName = tem[0];
		for (int i = 1; i < len; i++) {
			cName += setStartUP(tem[i]);
		}
		// tables.add(cName);//把所有的表添加到这里
		return cName;
	}

	/**
	 * Method name: getFiled2Pro <BR>
	 * Description: 根据字段名获取属性 <BR>
	 * Remark: <BR>
	 * 
	 * @return String<BR>
	 */
	public static String getFiled2Pro(String s) {
		String pName = "";
		String[] tem = s.split("_");

		int len = tem.length;
		pName = tem[0];
		for (int i = 1; i < len; i++) {
			pName += setStartUP(tem[i]);
		}
		return pName;
	}


	/**
	 * Method name: getStringDate <BR>
	 * Description: 根据字符串转成日期类型yyyt-MM-dd <BR>
	 * 
	 * @param time
	 * @return Date<BR>
	 */
	public static Date getStringDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			logger.error("日期转换出错：", e);
		}
		return date;
	}

	/**
	 * Method name: getStringDate <BR>
	 * Description: 根据字符串转成日期类型yyyt-MM-dd HH:mm:ss<BR>
	 * 
	 * @param time
	 * @return Date<BR>
	 */
	public static Date getStringDateTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			logger.error("日期转换出错：", e);
		}
		return date;
	}

	/**
	 * Method name: getNowDateFirstDay <BR>
	 * Description: 根据系统时间获取当月第一天 <BR>
	 * 
	 * @return String<BR>
	 */
	public static String getNowDateFirstDay() {
		Format format = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return format.format(c.getTime());
	}

	/**
	 * Method name: getNowDateLastDay <BR>
	 * Description: 根据系统时间获取当月最后一天 <BR>
	 * 
	 * @return String<BR>
	 */
	public static String getNowDateLastDay() {
		Format format = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当前月最后一天
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(ca.getTime());
	}
	
	/**
	 * 根据日期对象获取yyyy年MM月dd字符串
	 * @param date
	 * @return
	 */
	public static String getDate2String(Date date) {
		Format format = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		return format.format(date);
	}

	/**
	 * 根据日期对象获取yyyy年MM月dd字符串
	 * @param date
	 * @return
	 */
	public static String getDate2String(Date date, String f) {
		Format format = new SimpleDateFormat(f);
		return format.format(date);
	}

}
