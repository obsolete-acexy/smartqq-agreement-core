package com.thankjava.wqq.core.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thankjava.wqq.consts.ConstsParams;
import com.thankjava.wqq.core.request.RequestBuilder;
import com.thankjava.wqq.core.request.api.GetDiscusList;
import com.thankjava.wqq.core.request.api.GetGroupNameListMask2;
import com.thankjava.wqq.core.request.api.GetOnlineBuddies2;
import com.thankjava.wqq.core.request.api.GetRecentList2;
import com.thankjava.wqq.core.request.api.GetSelfInfo2;
import com.thankjava.wqq.core.request.api.GetUserFriends2;
import com.thankjava.wqq.entity.Session;
import com.thankjava.wqq.entity.wqq.DetailedInfo;
import com.thankjava.wqq.entity.wqq.DiscusList;
import com.thankjava.wqq.entity.wqq.FriendsList;
import com.thankjava.wqq.entity.wqq.GroupsList;
import com.thankjava.wqq.factory.RequestFactory;
import com.thankjava.wqq.util.JSON2Entity;
import com.thankjava.toolkit3d.http.async.entity.AsyncResponse;

public class GetInfoAction {

	private static final Logger logger = LoggerFactory.getLogger(GetInfoAction.class);
	
	private static Session session = Session.getSession();
	
	private RequestBuilder getUserFriends2 = RequestFactory.getInstance(GetUserFriends2.class);
	private RequestBuilder getGroupNameListMask2 = RequestFactory.getInstance(GetGroupNameListMask2.class);
	private RequestBuilder getDiscusList = RequestFactory.getInstance(GetDiscusList.class);
	private RequestBuilder getSelfInfo2 = RequestFactory.getInstance(GetSelfInfo2.class);
	private RequestBuilder getOnlineBuddies2 = RequestFactory.getInstance(GetOnlineBuddies2.class);
	private RequestBuilder getRecentList2 = RequestFactory.getInstance(GetRecentList2.class);
	
	/**
	 * 获取所有好友信息
	* <p>Function: getFriendsList</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2016年12月22日 下午1:34:35
	* @version 1.0
	* @return
	 */
	public FriendsList getFriendsList(){

		AsyncResponse response = null;
		FriendsList friendsList = null;
		
		for(int i = 1; i <= ConstsParams.EXCEPTION_RETRY_MAX_TIME; i ++){
			response = getUserFriends2.doRequest(null);
			if(!response.isEmptyDataString()){
				friendsList = JSON2Entity.userFriends2(response.getDataString());
				if(friendsList != null){
					session.setFriendsList(friendsList);
					return friendsList;
				}
			}
		}
		logger.error("getUserFriends2失败(已尝试重试)");
		return null;
	}
	
	/**
	 * 为当前好友列表数据查询在线状态
	* <p>Function: getOnlineStatus</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2017年6月14日 下午2:46:17
	* @version 1.0
	* @return
	 */
	public FriendsList getOnlineStatus(){
		
		FriendsList friendsList = session.getFriendsList();
		if(friendsList == null){
			return null;
		}
		
		AsyncResponse response = null;
		
		for(int i = 1; i <= ConstsParams.EXCEPTION_RETRY_MAX_TIME; i ++){
			response = getOnlineBuddies2.doRequest(null);
			if(!response.isEmptyDataString()){
				friendsList = JSON2Entity.onlineStatus(friendsList, response.getDataString());
				if(friendsList != null){
					session.setFriendsList(friendsList);
					return friendsList;
				}
			}
		}
		logger.error("getOnlineBuddies2失败(已尝试重试)");
		
		return null;
	}
	
	/**
	 * 获取讨论组列表
	* <p>Function: getDiscusList</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2016年12月22日 下午4:42:58
	* @version 1.0
	* @return
	 */
	public DiscusList getDiscusList(){
		AsyncResponse response = null;
		DiscusList discusList = null;
		for(int i = 1; i <= ConstsParams.EXCEPTION_RETRY_MAX_TIME; i ++){
			response = getDiscusList.doRequest(null);
			if(!response.isEmptyDataString()){
				discusList = JSON2Entity.getDiscusList(response.getDataString());
				if(discusList != null){
					session.setDiscusList(discusList);
					return discusList;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取群列表
	* <p>Function: getGroupsList</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2016年12月22日 下午1:56:17
	* @version 1.0
	* @return
	 */
	public GroupsList getGroupsList(){
		AsyncResponse response = null;
		GroupsList groupList = null;
		for(int i = 1; i <= ConstsParams.EXCEPTION_RETRY_MAX_TIME; i ++){
			response = getGroupNameListMask2.doRequest(null);
			if(!response.isEmptyDataString()){
				groupList = JSON2Entity.getGroupsList(response.getDataString());
				if(groupList != null){
					session.setGroupsList(groupList);
					return groupList;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取个人信息(登录人)
	* <p>Function: getSelfInfo</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2017年6月14日 下午4:13:01
	* @version 1.0
	* @return
	 */
	public DetailedInfo getSelfInfo(){
		AsyncResponse response = null;
		DetailedInfo detailedInfo = null;
		for(int i = 1; i <= ConstsParams.EXCEPTION_RETRY_MAX_TIME; i ++){
			response = getSelfInfo2.doRequest(null);
			if(!response.isEmptyDataString()){
				detailedInfo = JSON2Entity.getSelfInfo(response.getDataString());
				if(detailedInfo != null){
					session.setSelfInfo(detailedInfo);
					return detailedInfo;
				}
			}
		}
		return null;
	}
	
	void getRecentList(){
		getRecentList2.doRequest(null).isEmptyDataString();
	}
}
