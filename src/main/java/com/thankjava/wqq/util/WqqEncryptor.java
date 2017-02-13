package com.thankjava.wqq.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thankjava.toolkit.resource.SourceLoader;
import com.thankjava.wqq.core.action.LoginAction;

public class WqqEncryptor {

	private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);

	/**
	 * 计算一些接口的hash值
	 * <p>
	 * Function: hash
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author zhaoxy@thankjava.com
	 * @date 2016年12月21日 下午6:25:04
	 * @version 1.0
	 * @param uin
	 * @param ptwebqq
	 * @return
	 */
	public static String hash(String uin, String ptwebqq) {

		try {
			String jsSources = IOUtils.toString(SourceLoader.getResourceAsInputStream("hash.js"), "utf-8");
			ScriptEngineManager scriptEMgr = new ScriptEngineManager();
			ScriptEngine engine = scriptEMgr.getEngineByMimeType("application/javascript");
			engine.eval(jsSources);
			Invocable invocable = (Invocable) engine;
			// 调用js
			return (String) invocable.invokeFunction("hash", uin, ptwebqq);
		} catch (Exception e) {
			logger.error("计算hash值异常", e);
		}

		return null;
	}

	/**
	 * 检查二维码时，用于计算ptqrtoken
	* <p>Function: hashForCheckQrStatus</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2017年2月13日 下午6:11:06
	* @version 1.0
	* @param str
	* @return
	 */
	public static String hashForCheckQrStatus(String str) {
		long hash = 0;
		for (int i = 0, length = str.length(); i < length; i++) {
			hash += hash * 32 + str.charAt(i);
		}
		return String.valueOf(2147483647 & hash);
	}
}
