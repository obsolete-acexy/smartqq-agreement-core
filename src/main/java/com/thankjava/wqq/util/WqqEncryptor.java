package com.thankjava.wqq.util;

import com.thankjava.toolkit.core.utils.SourceLoaderUtil;
import com.thankjava.wqq.core.action.LoginAction;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class WqqEncryptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginAction.class);

    /**
     * 计算一些接口的hash值</p>
     * Function: hash</p>
     * <p>Description:</p>
     *
     * @param uin
     * @param ptwebqq
     * @return
     * @author acexy@thankjava.com
     * @date 2016年12月21日 下午6:25:04
     * @version 1.0
     */
    public static String hash(String uin, String ptwebqq) {

        try {
            String jsSource = IOUtils.toString(SourceLoaderUtil.getResourceAsInputStream("hash.js"), "utf-8");
            ScriptEngineManager scriptEMgr = new ScriptEngineManager();
            ScriptEngine engine = scriptEMgr.getEngineByMimeType("application/javascript");
            engine.eval(jsSource);
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
     *
     * @param str
     * @return
     * @author acexy@thankjava.com
     * @date 2017年2月13日 下午6:11:06
     * @version 1.0
     */
    public static String hashForCheckQrStatus(String str) {
        long hash = 0;
        for (int i = 0, length = str.length(); i < length; i++) {
            hash += hash * 32 + str.charAt(i);
        }
        return String.valueOf(2147483647 & hash);
    }
}
