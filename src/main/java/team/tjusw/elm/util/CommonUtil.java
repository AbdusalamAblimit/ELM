package team.tjusw.elm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.crypto.Data;

public class CommonUtil {

	
//	public static void main(String[]args)
//	{
//		System.out.println(getCurrentDate());
//	}
//	
	public static String getCurrentDate() {
		Date date = new Date();
		return getDateString(date);
	}
	
	private static String getDateString(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String dataStr = formatter.format(date);
		return dataStr;
	}
	
	/*此方法返回days天过后的日期字符串
	 * */
	public static String getDateAfterNow(int days)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH,days);
		return getDateString(calendar.getTime());
	}
	
	
	public static int compareStringDate(String date1,String date2)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		try {
			Date d1 = formatter.parse(date1);
			Date d2 = formatter.parse(date2);
			return d1.compareTo(d2);
		} catch (ParseException e) {
			System.out.println("字符串时间格式错误.");
			e.printStackTrace();
			return 0;
		}
	}

}
