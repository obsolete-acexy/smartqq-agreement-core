package com.thankjava.wqq.core.request.http;

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

import com.alibaba.fastjson.JSONObject;

public class GetRecentList2 extends BaseHttpService {

	@Override
	@Before(cutClass = DoRequest.class, cutMethod = "doRequest")
	public ResponseParams doRequest(CallBackListener listener) {
		return null;
	}

	@Override
	protected RequestParams buildRequestParams() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("vfwebqq", session.getVfwebqq());
		jsonObject.put("clientid", ConstsParams.CLIENT_ID);
		jsonObject.put("psessionid", session.getPsessionid());
		
		Parameters params = new Parameters("r", jsonObject.toJSONString());		
		Headers headers = new Headers(HeaderName.referer.name, RequestUrls.referer_common.url);
		return new RequestParams(
				RequestUrls.get_recent_list2.url, 
				HttpMethod.post, 
				params,
				headers
		);	
	}

}
