package com.thankjava.wqq.core.request.http;

import com.thankjava.toolkit3d.aop.anno.Before;
import com.thankjava.toolkit3d.http.async.consts.HeaderName;
import com.thankjava.toolkit3d.http.async.consts.HttpMethod;
import com.thankjava.toolkit3d.http.async.entity.Headers;
import com.thankjava.toolkit3d.http.async.entity.Parameters;
import com.thankjava.toolkit3d.http.async.entity.RequestParams;
import com.thankjava.toolkit3d.http.async.entity.ResponseParams;
import com.thankjava.wqq.consts.RequestUrls;
import com.thankjava.wqq.core.request.aop.DoRequest;
import com.thankjava.wqq.extend.CallBackListener;

public class GetVfWebqq extends BaseHttpService {

	@Override
	@Before(cutClass = DoRequest.class, cutMethod = "doRequest")
	public ResponseParams doRequest(CallBackListener listener) {
		return null;
	}

	@Override
	protected RequestParams buildRequestParams() {
		Parameters params = new Parameters("ptwebqq", session.getPtwebqq());
		params.append("clientid", "53999199");
		params.append("psessionid", "");
		params.append("t", String.valueOf(System.currentTimeMillis() / 1000));
		Headers header = new Headers(HeaderName.referer.name, RequestUrls.referer_getvfwebqq.url);
		return new RequestParams(RequestUrls.getvfwebqq.url, HttpMethod.get, params, header);
	}
}
