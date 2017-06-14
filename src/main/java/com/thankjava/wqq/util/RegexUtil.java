package com.thankjava.wqq.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thankjava.wqq.consts.DataResRegx;

public class RegexUtil {

	/**
	 * 使用正则去匹配返回的数据
	 * @param content
	 * @param regx
	 * @return
	 */
	public static String[] doRegex(String content, DataResRegx regx){
		Pattern pattern = Pattern.compile(regx.regx);
		Matcher matcher = pattern.matcher(content);
		if(matcher.find()){
			String[] values = new String[matcher.groupCount()];
			for(int i = 1 ; i < matcher.groupCount(); i ++){
				values[i - 1] = matcher.group(i);
			}
			return values;
		}
		return null;
	}
}
