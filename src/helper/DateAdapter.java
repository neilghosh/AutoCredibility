package helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateAdapter {
	public static Date FbStrToDate(String str) {
		Date date = null;
		//String str = "2011-02-25T21:35:30+0000";
		str = str.replace('T', ' ');
		//str = str.replace('+', 'N');
		//String[] token = str.split("N");
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd h:m:s");
			date = (Date) formatter.parse(str);
		} catch (ParseException e) {
			System.out.println("Exception :" + e);
		}
		System.out.println(date.toGMTString());
		return date;
	}
}