package com.thankjava.wqq.core.request.api;

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
import com.thankjava.wqq.util.WqqEncryptor;

public class CheckLoginQRcodeStatus extends BaseHttpService {

	@Override
	@Before(cutClass = DoRequest.class, cutMethod = "doRequest")
	public ResponseParams doRequest(CallBackListener listener) {
		return null;
	}

	@Override
	protected RequestParams buildRequestParams() {
		
		Parameters params = new Parameters("webqq_type", "10");
		params.append("ptqrtoken", WqqEncryptor.hashForCheckQrStatus(asyncHttpClient.getCookie("qrsig").getValue()));
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
		
		Headers headers = new Headers(HeaderName.referer.name, RequestUrls.referer_check_qrcode_status.url);
		return new RequestParams(
				RequestUrls.check_qrcode_status.url, 
				HttpMethod.get, 
				params,
				headers
		);
	}

}
