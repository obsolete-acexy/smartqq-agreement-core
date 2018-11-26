package com.thankjava.wqq.core.request.api;

import com.thankjava.toolkit3d.bean.http.AsyncRequest;
import com.thankjava.toolkit3d.bean.http.CookieCheckLevel;
import com.thankjava.toolkit3d.core.http.AsyncHttpClient;
import com.thankjava.toolkit3d.core.http.AsyncHttpClientBuilder;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.core.request.RequestBuilder;
import com.thankjava.wqq.entity.Session;

import java.util.concurrent.atomic.AtomicLong;

public abstract class BaseHttpService implements RequestBuilder {

    public final static AsyncHttpClient asyncHttpClient = new AsyncHttpClientBuilder()
            .setWithoutSSLCheck()
            .setCookiePolicyLevel(CookieCheckLevel.BROWSER_COMPATIBILITY)
            .setCloseWarnLogger()
            .create();
    protected static final AtomicLong msgId = new AtomicLong(ConstsParams.INIT_MSG_ID);
    protected final Session session = Session.getSession();

    /**
     * 关闭AsyncHttpClient
     */
    public static void shutdownAsyncHttpClient() {
        asyncHttpClient.shutdown();
    }

    /**
     * 组装请求参数
     * <p>Function: buildRequestParams</p>
     * <p>Description: </p>
     *
     * @return
     * @author acexy@thankjava.com
     * @date 2016年12月19日 上午11:43:02
     * @version 1.0
     */
    protected abstract AsyncRequest buildRequestParams();

}
