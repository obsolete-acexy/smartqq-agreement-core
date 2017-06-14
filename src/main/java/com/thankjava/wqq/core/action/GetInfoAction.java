package com.thankjava.wqq.core.action;

import java.util.HashMap;
import java.util.Map;

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
import com.thankjava.wqq.entity.wqq.DiscuInfo;
import com.thankjava.wqq.entity.wqq.DiscusList;
import com.thankjava.wqq.entity.wqq.FriendsList;
import com.thankjava.wqq.entity.wqq.GroupInfo;
import com.thankjava.wqq.entity.wqq.GroupsList;
import com.thankjava.wqq.factory.RequestFactory;
import com.thankjava.wqq.util.JSON2Entity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thankjava.toolkit3d.fastjson.FastJson;
import com.thankjava.toolkit3d.http.async.entity.ResponseParams;

public class GetInfoAction {

	private static final Logger logger = LoggerFactory.getLogger(GetInfoAction.class);
	
	private static Session session = Session.getSession();
	
	RequestBuilder getUserFriends2 = RequestFactory.getInstance(GetUserFriends2.class);
	RequestBuilder getGroupNameListMask2 = RequestFactory.getInstance(GetGroupNameListMask2.class);
	RequestBuilder getDiscusList = RequestFactory.getInstance(GetDiscusList.class);
	RequestBuilder getSelfInfo2 = RequestFactory.getInstance(GetSelfInfo2.class);
	RequestBuilder getOnlineBuddies2 = RequestFactory.getInstance(GetOnlineBuddies2.class);
	RequestBuilder getRecentList2 = RequestFactory.getInstance(GetRecentList2.class);
	
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

		ResponseParams response = null;
		FriendsList friendsList = null;
		
		for(int i = 1; i <= ConstsParams.EXCEPTION_RETRY_MAX_TIME; i ++){
			response = getUserFriends2.doRequest(null);
			if(!response.isEmptyContent()){
				friendsList = JSON2Entity.userFriends2(response.getContent());
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
	* <p>Function: appendOnlineStatus</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2017年6月14日 下午2:46:17
	* @version 1.0
	* @return
	 */
	public FriendsList appendOnlineStatus(){
		
		FriendsList friendsList = session.getFriendsList();
		if(friendsList == null){
			return null;
		}
		
		ResponseParams response = null;
		
		for(int i = 1; i <= ConstsParams.EXCEPTION_RETRY_MAX_TIME; i ++){
			response = getOnlineBuddies2.doRequest(null);
			if(!response.isEmptyContent()){
				friendsList = JSON2Entity.onlineStatus(friendsList, response.getContent());
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
		return analysisDiscusList(getDiscusList.doRequest(null).getContent());
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
		return analysisGroupsList(getGroupNameListMask2.doRequest(null).getContent());
	}
	
	public DetailedInfo getSelfInfo(){
		return analysisSelfInfo(getSelfInfo2.doRequest(null).getContent());
	}
	
	public void getRecentList(){
		String content = getRecentList2.doRequest(null).getContent();
		logger.debug("获取会话列表:" + content);
	}
	
	
	
	private GroupsList analysisGroupsList(String content){
		logger.debug("获取群列表返回的数据:" + content);
		GroupsList groupsList = new GroupsList();
		JSONObject groupsListJson = JSONObject.parseObject(content);
//		if(groupsListJson.getIntValue("retcode") != 0){
//			groupsList = session.getGroupsList();
//			if(groupsList == null){
//				// 本地的好友列表信息不存在,则当前对于第一次获取好友列表的情况 一直重试
//				return getGroupsList();
//			}
//		}else{
			JSONObject result = (JSONObject) groupsListJson.get("result");
			
			Map<Long, GroupInfo> groups = new HashMap<Long, GroupInfo>();
			
			// 群信息
			JSONArray array = (JSONArray)result.get("gnamelist");
			GroupInfo group;
			long gid = -1;
			Object[] objs = array.toArray();
			for (Object obj : objs) {
				group = FastJson.toObject(obj.toString(), GroupInfo.class);
				groups.put(group.getGid(), group);
			}
			// 备注信息
			array = (JSONArray)result.get("gmarklist");
			objs = array.toArray();
			for (Object obj : objs) {
				gid = ((JSONObject)obj).getLongValue("uin");
				group = FastJson.appendObject(obj.toString(), groups.get(gid));
			}
			groupsList.setGroups(groups);
			session.setGroupsList(groupsList);
//		}
		return groupsList;
	}

	private DiscusList analysisDiscusList(String content){
		logger.debug("获取讨论组列表返回的数据:" + content);
		DiscusList discusList = new DiscusList();
		JSONObject discusListJson = JSONObject.parseObject(content);
//		if(groupsListJson.getIntValue("retcode") != 0){
//			groupsList = session.getGroupsList();
//			if(groupsList == null){
//				// 本地的好友列表信息不存在,则当前对于第一次获取好友列表的情况 一直重试
//				return getGroupsList();
//			}
//		}else{
			JSONObject result = (JSONObject) discusListJson.get("result");
			
			Map<Long, DiscuInfo> discuInfo = new HashMap<Long, DiscuInfo>();
			
			JSONArray array = (JSONArray)result.get("dnamelist");
			DiscuInfo discu;
			Object[] objs = array.toArray();
			for (Object obj : objs) {
				discu = FastJson.toObject(obj.toString(), DiscuInfo.class);
				discuInfo.put(discu.getDid(), discu);
			}
			discusList.setDiscus(discuInfo);
			session.setDiscusList(discusList);
//		}
		return discusList;
	}

	private DetailedInfo analysisSelfInfo(String content){
		logger.debug("获取个人信息返回的数据:" + content);
		DetailedInfo selfInfo = null;
		JSONObject discusListJson = JSONObject.parseObject(content);
//		if(groupsListJson.getIntValue("retcode") != 0){
//			groupsList = session.getGroupsList();
//			if(groupsList == null){
//				// 本地的好友列表信息不存在,则当前对于第一次获取好友列表的情况 一直重试
//				return getGroupsList();
//			}
//		}else{
			JSONObject result = (JSONObject) discusListJson.get("result");
			selfInfo = FastJson.toObject(result.toJSONString(), DetailedInfo.class);
			session.setSelfInfo(selfInfo);
//		}
		return selfInfo;
	}
}
