package com.progresssoft.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public final class StringHelper
{
	private StringHelper() {	
	}
	
	public static final String WhiteSpace = " \n\r\f\t";
	
	public static final Locale INAVARIANT_LOCALE = Locale.US; 

	public static final String DOT = ".";
    
    public static final String SEMI_COLON = ";";
    
    public static final String EQUAL = "=";

    public static final String SPACE = " ";

	public static final String UNDER_SCORE = "_";
	
	public static final String COMMA_SPACE = ", ";

	public static final String COMMA = ",";

	public static final String OPEN_PAREN = "(";

	public static final String CLOSED_PAREN = ")";

	public static final String SINGLE_QUOTE = "'";
	
	public static final String EMPTY = "";

    public static boolean isEmpty(String str){
    	
        return (str == null) || (str.length() == 0);
    }

    public static boolean isNotEmpty(String str){
    	
        return (str != null) && (str.length() > 0);
    }

    public static String nullSafeToString(Object obj){
    	
        return obj == null ? "(null)" : obj.toString();
    }
    
    public static String nullSafeString(String str, String defaultVal){
        
        return (str == null || "".equalsIgnoreCase(str)) ? defaultVal : str;
    }
    
    public static int indexOfIgnoreCase(String value, String find) {
    	
    	String valueTemp = value.toUpperCase(Locale.US);
    	String findTemp = find.toUpperCase(Locale.US);
    	
    	return valueTemp.indexOf(findTemp);
    }
    
    public static boolean startsWithIgnoreCase(String value, String find) {
    	
    	String valueTemp = value.toUpperCase(Locale.US);
    	String findTemp = find.toUpperCase(Locale.US);
    	
    	return valueTemp.startsWith(findTemp);
    }
    
    public static String getCombinedKeyFrom(String[] keys, int size) {
        if (keys == null || keys.length <= 0) return "";
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i <= size; i++) {
            String key = keys[i];
            sb.append(key);
            if (i + 1 <= size) sb.append(DOT);
        }
        return sb.toString();
    }
    
    public static String getCombinedKeyFrom(String[] keys) {

        return getCombinedKeyFrom(keys, keys.length - 1);
    }    
    
    public static Map<String, String> getPropertiesMapFrom(String paramString) {
        
        Map<String, String> propsMap = new HashMap<String, String>();
        String[] paramTokens = paramString.split(SEMI_COLON);

        for(String param : paramTokens) {
            int idx = param.indexOf(EQUAL);
            propsMap.put(param.substring(0, idx), param.substring(idx + 1));
        }
        
        return propsMap;
    }
    
    public static String getPropertiesString(Map<String, String> propsMap) {

        StringBuilder propsStr = new StringBuilder();
        Iterator<Entry<String, String>> iter = propsMap.entrySet().iterator(); 
        while(iter.hasNext()) {
            Entry<String, String> prop = iter.next();
            propsStr.append(prop.getKey()).append(EQUAL).append(prop.getValue());
            if (iter.hasNext()) propsStr.append(SEMI_COLON);
        }
        
        return propsStr.toString();
    }
    
    public static StringBuilder getLeadingZeros(int count){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<count; i++) {
            sb.append("0");
        }
        return sb;
    }
    
	public static String padLeft(String value, char pad, int size) {
		
		return StringHelper.innerPad(value, pad, size, true);
	}
	
	public static String padRight(String value, char pad, int size) {
		
		return StringHelper.innerPad(value, pad, size, false);
	}
	
	public static String rTrim(String value, char trimVal) {
		
		if(StringHelper.isEmpty(value))
			return value;
		        
        int idx = value.length() - 1;
        
        for (; idx >= 0; idx--) {
        	
            if (value.charAt(idx) != trimVal)
                break;
        }

        if(idx < 0)
        	return StringHelper.EMPTY;
        
        if(idx >= 0)
        	value = value.substring(0, idx+1);
        
        return value;		
		
	}
	
    public static String lTrim(String value, char trimVal) {

    	if(StringHelper.isEmpty(value))
    		return value;
    	
        int idx = 0;
        for(;idx < value.length(); idx++) {
        	
            if (value.charAt(idx) != trimVal)
                break;
        }

        if(idx >= value.length())
        	return StringHelper.EMPTY;
        
        if(idx > 0)
        	value = value.substring(idx);
        
        return value;
    }
		
	private static String innerPad(String value, char pad, int size, boolean padLeft) {
		
		if(value == null)
			value = StringHelper.EMPTY;
		
		int strLen = value.length();
		
		if(strLen >= size)
			return value;
		
		int padLen = size - strLen;
		
		StringBuilder builder = new StringBuilder(size);

		if(!padLeft)
			builder.append(value);
		
		for(int cnt = 0; cnt < padLen; cnt++) {
			
			builder.append(pad);
		}
		
		if(padLeft)
			builder.append(value);
		
		
		
		return builder.toString();		
	}       
}