package com.thankjava.wqq.core.request.api;

import com.alibaba.fastjson.JSONObject;
import com.thankjava.toolkit.bean.aop.anno.Before;
import com.thankjava.toolkit3d.bean.http.*;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.consts.RequestUrls;
import com.thankjava.wqq.core.request.aop.DoRequest;
import com.thankjava.wqq.extend.CallBackListener;

public class Poll2 extends BaseHttpService {

    @Override
    @Before(cutClass = DoRequest.class, cutMethod = "doRequest")
    public AsyncResponse doRequest(CallBackListener listener) {
        return null;
    }

    @Override
    protected AsyncRequest buildRequestParams() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ptwebqq", session.getPtwebqq());
        jsonObject.put("clientid", ConstsParams.CLIENT_ID);
        jsonObject.put("psessionid", session.getPsessionid());
        jsonObject.put("key", "");
        Parameters params = new Parameters("r", jsonObject.toJSONString());
        Headers headers = new Headers("Referer", RequestUrls.referer_about_msg.url);
        return new AsyncRequest(RequestUrls.poll2.url, HttpMethod.post, params, headers);
    }

}
