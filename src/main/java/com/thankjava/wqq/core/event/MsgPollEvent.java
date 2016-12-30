package com.thankjava.wqq.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thankjava.toolkit3d.fastjson.FastJson;
import com.thankjava.toolkit3d.http.async.entity.ResponseParams;
import com.thankjava.wqq.WQQClient;
import com.thankjava.wqq.core.request.Request;
import com.thankjava.wqq.core.request.http.Poll2;
import com.thankjava.wqq.entity.msg.Content;
import com.thankjava.wqq.entity.msg.Font;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.entity.msg.Value;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.extend.ListenerAction;
import com.thankjava.wqq.factory.RequestFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class MsgPollEvent {

	private static final Logger logger = LoggerFactory.getLogger(MsgPollEvent.class);
	
	Request poll2 = RequestFactory.getInstance(Poll2.class);
	
	public void poll(){
		
		poll2.doRequest(new CallBackListener() {
			@Override
			public void onListener(ListenerAction listenerAction) {
				ResponseParams respones = (ResponseParams) listenerAction.data;
				if(respones.getHttpCode() == 200){
					MsgPollEvent.this.notify(respones.getContent());
				}
				poll();
			}
		});
		
	}
	
	void notify(String content){
		
		logger.debug("拉取到通知的信息: " + content);
		JSONObject contentJson = JSONObject.parseObject(content);
		if(contentJson.getInteger("retcode") != 0){
			logger.error("拉取到的通知信息状态不正常,丢弃该信息");
			return;
		}
		
		// 处理数据
		JSONArray array = (JSONArray) contentJson.get("result");
		JSONObject pollMsgJson = (JSONObject)array.get(0);
		JSONObject valueJson = pollMsgJson.getJSONObject("value");
		JSONArray contentArray = valueJson.getJSONArray("content");
		valueJson.remove("content");
		
		PollMsg pollMsg = FastJson.toObject(array.get(0).toString(), PollMsg.class);
		Value value = pollMsg.getValue();
		int contentSize = contentArray.size();
		Content contentMsg = new Content();
		value.setContent(contentMsg);
		Font font = FastJson.toObject(contentArray.getJSONArray(0).get(1).toString(), Font.class);
		contentMsg.setFont(font);
		
		Object[] objs = new Object[contentSize - 1];
		Object obj;
		for (int index = 1; index < contentSize; index ++){
			obj = contentArray.get(index);
			if(JSONArray.class == obj.getClass()){
				objs[index - 1] = Integer.parseInt(((JSONArray)obj).get(1).toString());
			} else if(String.class == obj.getClass()){
				objs[index - 1] = obj.toString();
			}
		}
		contentMsg.setMsg(objs);
		
		WQQClient.getNotifyListener().hander(pollMsg);
	}
}
