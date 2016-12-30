package com.thankjava.wqq.core.request.http;

import com.thankjava.toolkit3d.aop.anno.Before;
import com.thankjava.toolkit3d.http.async.consts.HttpMethod;
import com.thankjava.toolkit3d.http.async.entity.Parameters;
import com.thankjava.toolkit3d.http.async.entity.RequestParams;
import com.thankjava.toolkit3d.http.async.entity.ResponseParams;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.consts.RequestUrls;
import com.thankjava.wqq.core.request.aop.DoRequest;
import com.thankjava.wqq.extend.CallBackListener;

/**
 * 获取登录二维码
* <p>Function: GetLoginQRcode</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2016年12月13日 下午5:53:53
* @version 1.0
 */
public class GetLoginQRcode extends BaseHttpService{

	@Override
	protected RequestParams buildRequestParams() {
		Parameters params = new Parameters("appid", String.valueOf(ConstsParams.APP_ID));
		params.append("e", "0");
		params.append("l", "M");
		params.append("s", "5");
		params.append("d", "72");
		params.append("v", "4");
		params.append("4", String.valueOf(Math.random()));
		
		return new RequestParams(
			RequestUrls.get_login_qrcode.url, 
			HttpMethod.get, 
			params
		);
	}

	@Override
	@Before(cutClass = DoRequest.class, cutMethod = "doRequest")
	public ResponseParams doRequest(CallBackListener listener) {
		return null;
	}
}
