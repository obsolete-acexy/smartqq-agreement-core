package com.thankjava.wqq.core.request.api;

import com.thankjava.toolkit3d.aop.anno.Before;
import com.thankjava.toolkit3d.http.async.consts.HeaderName;
import com.thankjava.toolkit3d.http.async.consts.HttpMethod;
import com.thankjava.toolkit3d.http.async.entity.Headers;
import com.thankjava.toolkit3d.http.async.entity.Parameters;
import com.thankjava.toolkit3d.http.async.entity.RequestParams;
import com.thankjava.toolkit3d.http.async.entity.ResponseParams;
import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.consts.RequestUrls;
import com.thankjava.wqq.core.request.aop.DoRequest;
import com.thankjava.wqq.extend.CallBackListener;

public class GetOnlineBuddies2 extends BaseHttpService{

	@Override
	@Before(cutClass = DoRequest.class, cutMethod = "doRequest")
	public ResponseParams doRequest(CallBackListener listener) {
		return null;
	}

	@Override
	protected RequestParams buildRequestParams() {
		
		Parameters params = new Parameters("vfwebqq", session.getVfwebqq());
		params.append("clientid", ConstsParams.CLIENT_ID.toString());
		params.append("psessionid", session.getPsessionid());
		params.append("t", String.valueOf(System.currentTimeMillis() / 1000));
		Headers headers = new Headers(HeaderName.referer.name, RequestUrls.referer_common.url);
		return new RequestParams(
				RequestUrls.get_online_buddies2.url, 
				HttpMethod.get, 
				params,
				headers
		);	
	}

}
