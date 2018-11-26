package com.thankjava.wqq.core.request.api;

import com.thankjava.toolkit.bean.aop.anno.Before;
import com.thankjava.toolkit3d.bean.http.AsyncRequest;
import com.thankjava.toolkit3d.bean.http.AsyncResponse;
import com.thankjava.toolkit3d.bean.http.HttpMethod;
import com.thankjava.toolkit3d.bean.http.Parameters;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.consts.RequestUrls;
import com.thankjava.wqq.core.request.aop.DoRequest;
import com.thankjava.wqq.extend.CallBackListener;

/**
 * 获取登录二维码
 * <p>Function: GetLoginQRcode</p>
 * <p>Description: </p>
 *
 * @author acexy@thankjava.com
 * @version 1.0
 * @date 2016年12月13日 下午5:53:53
 */
public class GetLoginQRcode extends BaseHttpService {


    @Override
    @Before(cutClass = DoRequest.class, cutMethod = "doRequest")
    public AsyncResponse doRequest(CallBackListener listener) {
        return null;
    }

    @Override
    protected AsyncRequest buildRequestParams() {
        Parameters params = new Parameters("appid", String.valueOf(ConstsParams.APP_ID));
        params.append("e", "0");
        params.append("l", "M");
        params.append("s", "5");
        params.append("d", "72");
        params.append("v", "4");
        params.append("4", String.valueOf(Math.random()));

        return new AsyncRequest(
                RequestUrls.get_login_qrcode.url,
                HttpMethod.get,
                params
        );
    }
}
