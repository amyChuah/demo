package com.demo.utils;

import java.util.regex.Pattern;

public class StringUtil {
	private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	
    public static boolean isBlank(String str) {
        if (null == str) {
            return true;
        }
        if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }
    
    public static boolean isNumeric(String str) {
   	 if (isBlank(str))
   	 	return false;
   	 
   	 return pattern.matcher(str).matches();
    }
    
}