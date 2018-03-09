package com.thankjava.wqq.core.event;

import com.thankjava.wqq.extend.ActionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thankjava.toolkit3d.http.async.entity.AsyncResponse;
import com.thankjava.wqq.WQQClient;
import com.thankjava.wqq.core.request.RequestBuilder;
import com.thankjava.wqq.core.request.api.Poll2;
import com.thankjava.wqq.entity.Session;
import com.thankjava.wqq.entity.enums.PullMsgStatus;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.entity.sys.MonitoringData;
import com.thankjava.wqq.extend.CallBackListener;
import com.thankjava.wqq.factory.RequestFactory;
import com.thankjava.wqq.util.JSON2Entity;

/**
 * 消息事件处理器
 */
public class MsgPollEvent {

	private static final Logger logger = LoggerFactory.getLogger(MsgPollEvent.class);

	private RequestBuilder poll2 = RequestFactory.getInstance(Poll2.class);

	private static Session session = Session.getSession();

	public void poll() {

		poll2.doRequest(new CallBackListener() {

			@Override
			public void onListener(ActionListener actionListener) {
				PullMsgStatus pullMsgStatus;
				if (actionListener.getData() != null) {
					AsyncResponse response = (AsyncResponse) actionListener.getData();
					logger.debug("msgPoll Event > httpStatus: " + response);
					if (response.getHttpCode() == 200) {
						PollMsg pollMsg = JSON2Entity.pollMsg(response.getDataString());
						if (pollMsg != null) {
							notifyMsgEvent(pollMsg);
							pullMsgStatus = PullMsgStatus.normal;
						} else {
							pullMsgStatus = PullMsgStatus.http_response_error;
						}
					} else {
						pullMsgStatus = PullMsgStatus.http_status_error;
					}
					poll();
				} else {
					pullMsgStatus = PullMsgStatus.http_exception;
				}

				doExceptionCheck(pullMsgStatus);
			}
		});
	}

	private void notifyMsgEvent(PollMsg pollMsg) {
		WQQClient.getNotifyListener().handler(WQQClient.getInstance(), pollMsg);
	}

	private void doExceptionCheck(PullMsgStatus pullMsgStatus) {
		MonitoringData monitoringData = session.getMonitoringData(pullMsgStatus);
		monitoringData.addData();
		double avaValue = monitoringData.getAverageValueOfOneSecound();
		if (avaValue >= 1) {
			logger.info(pullMsgStatus + " 类型平均值超出，可能处于登录异常");
		}
	}
}
