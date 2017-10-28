package com.thankjava.wqq.core.request.api;

import com.thankjava.toolkit3d.aop.anno.Before;
import com.thankjava.toolkit3d.http.async.consts.HeaderName;
import com.thankjava.toolkit3d.http.async.consts.HttpMethod;
import com.thankjava.toolkit3d.http.async.entity.Headers;
import com.thankjava.toolkit3d.http.async.entity.Parameters;
import com.thankjava.toolkit3d.http.async.entity.AsyncRequest;
import com.thankjava.toolkit3d.http.async.entity.AsyncResponse;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.consts.RequestUrls;
import com.thankjava.wqq.core.request.aop.DoRequest;
import com.thankjava.wqq.extend.CallBackListener;

public class GetDiscusList extends BaseHttpService{

	@Override
	@Before(cutClass = DoRequest.class, cutMethod = "doRequest")
	public AsyncResponse doRequest(CallBackListener listener) {
		return null;
	}

	@Override
	protected AsyncRequest buildRequestParams() {
		Parameters params = new Parameters("clientid", ConstsParams.CLIENT_ID.toString());
		params.append("psessionid", session.getPsessionid());
		params.append("vfwebqq", session.getVfwebqq());
		params.append("t", String.valueOf(System.currentTimeMillis() / 1000));
		
		Headers headers = new Headers(HeaderName.referer.name, RequestUrls.referer_common.url);
		return new AsyncRequest(
				RequestUrls.get_discus_list.url, 
				HttpMethod.get, 
				params,
				headers
		);
	}

}
