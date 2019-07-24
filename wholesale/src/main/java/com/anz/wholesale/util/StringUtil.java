package com.anz.wholesale.util;

/**
 * String Utility class holding helper functions
 * @author sumit
 *
 */
public class StringUtil {
	
	/**
	 * Function to check whether the string is null or is blank
	 * @param str Input string which needs to be checked for null and blank
	 * @return boolean value indicating whether string is null or blank
	 */
	public static boolean isNullOrEmpty(String str) {
		return (str == null || str.isEmpty());
	}

}
