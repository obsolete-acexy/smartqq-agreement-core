package com.thankjava.wqq.core.request.api;

import com.thankjava.toolkit.bean.aop.anno.Before;
import com.thankjava.toolkit3d.bean.http.*;
import com.thankjava.wqq.consts.RequestUrls;
import com.thankjava.wqq.core.request.aop.DoRequest;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.util.WqqEncryptor;

public class CheckLoginQRcodeStatus extends BaseHttpService {

    @Override
    @Before(cutClass = DoRequest.class, cutMethod = "doRequest")
    public AsyncResponse doRequest(CallBackListener listener) {
        return null;
    }

    @Override
    protected AsyncRequest buildRequestParams() {

        Parameters params = new Parameters("webqq_type", "10");
        params.append("ptqrtoken", WqqEncryptor.hashForCheckQrStatus(asyncHttpClient.getCookieFromClientContext("qrsig").getValue()));
        params.append("webqq_type", "10");
        params.append("remember_uin", "1");
        params.append("login2qq", "1");
        params.append("aid", "501004106");
        params.append("u1", RequestUrls.else_proxy.url);
        params.append("ptredirect", "0");
        params.append("ptlang", "2052");
        params.append("daid", "164");
        params.append("from_ui", "1");
        params.append("pttype", "1");
        params.append("dumy", "");
        params.append("fp", "loginerroralert");
        params.append("action", "0-0-2177");
        params.append("mibao_css", "m_webqq");
        params.append("t", "undefined");
        params.append("g", "1");
        params.append("js_type", "0");
        params.append("js_ver", "10185");
        params.append("login_sig", "");
        params.append("pt_randsalt", "0");

        Headers headers = new Headers("Referer", RequestUrls.referer_check_qrcode_status.url);
        return new AsyncRequest(
                RequestUrls.check_qrcode_status.url,
                HttpMethod.get,
                params,
                headers
        );
    }

}
