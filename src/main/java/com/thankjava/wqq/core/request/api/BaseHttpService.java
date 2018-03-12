package com.thankjava.wqq.core.request.api;

import java.util.concurrent.atomic.AtomicLong;

import com.thankjava.toolkit3d.http.async.AsyncHttpClient;
import com.thankjava.toolkit3d.http.async.AsyncHttpClientBuilder;
import com.thankjava.toolkit3d.http.async.entity.AsyncRequest;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.core.request.RequestBuilder;
import com.thankjava.wqq.entity.Session;

public abstract class BaseHttpService implements RequestBuilder {

    protected final Session session = Session.getSession();

    protected static final AtomicLong msgId = new AtomicLong(ConstsParams.INIT_MSG_ID);

    public final static AsyncHttpClient asyncHttpClient = AsyncHttpClientBuilder.createDefault();

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
