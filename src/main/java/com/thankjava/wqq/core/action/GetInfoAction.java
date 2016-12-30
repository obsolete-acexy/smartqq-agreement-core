package com.thankjava.wqq.core.action;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thankjava.wqq.core.request.Request;
import com.thankjava.wqq.core.request.http.GetDiscusList;
import com.thankjava.wqq.core.request.http.GetGroupNameListMask2;
import com.thankjava.wqq.core.request.http.GetOnlineBuddies2;
import com.thankjava.wqq.core.request.http.GetRecentList2;
import com.thankjava.wqq.core.request.http.GetSelfInfo2;
import com.thankjava.wqq.core.request.http.GetUserFriends2;
import com.thankjava.wqq.entity.Session;
import com.thankjava.wqq.entity.wqq.CategorieInfo;
import com.thankjava.wqq.entity.wqq.DetailedInfo;
import com.thankjava.wqq.entity.wqq.DiscuInfo;
import com.thankjava.wqq.entity.wqq.DiscusList;
import com.thankjava.wqq.entity.wqq.FriendInfo;
import com.thankjava.wqq.entity.wqq.FriendsList;
import com.thankjava.wqq.entity.wqq.GroupInfo;
import com.thankjava.wqq.entity.wqq.GroupsList;
import com.thankjava.wqq.factory.RequestFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thankjava.toolkit3d.fastjson.FastJson;

public class GetInfoAction {

private static final Logger logger = LoggerFactory.getLogger(GetInfoAction.class);
	
	Session session = Session.getSession();
	
	Request getUserFriends2 = RequestFactory.getInstance(GetUserFriends2.class);
	Request getGroupNameListMask2 = RequestFactory.getInstance(GetGroupNameListMask2.class);
	Request getDiscusList = RequestFactory.getInstance(GetDiscusList.class);
	Request getSelfInfo2 = RequestFactory.getInstance(GetSelfInfo2.class);
	Request getOnlineBuddies2 = RequestFactory.getInstance(GetOnlineBuddies2.class);
	Request getRecentList2 = RequestFactory.getInstance(GetRecentList2.class);
	
	/**
	 * 获取好友列表
	* <p>Function: getFriendsList</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2016年12月22日 下午1:34:35
	* @version 1.0
	* @return
	 */
	public FriendsList getFriendsList(){
		String friendsListContent = getUserFriends2.doRequest(null).getContent();
		String onlienBuddiesContent = getOnlineBuddies2.doRequest(null).getContent();
		return analysisFriendsList(friendsListContent, onlienBuddiesContent);
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
	
	private FriendsList analysisFriendsList(String friendsListContent, String onlienBuddiesContent){
		
		logger.debug("获取好友列表返回数据: " + friendsListContent);
		logger.debug("获取好友状态返回数据: " + onlienBuddiesContent);
		
		FriendsList friendsList = new FriendsList();
		
		JSONObject friendsListJson = JSONObject.parseObject(friendsListContent);
		
//		if(friendsListJson.getIntValue("retcode") != 0){
////			friendsList = session.getFriendsList();
////			if(friendsList == null){
////				// 本地的好友列表信息不存在,则当前对于第一次获取好友列表的情况 一直重试
////				return getFriendsList();
////			}
//		}else{
			JSONObject result = (JSONObject) friendsListJson.get("result");
			Map<Integer, CategorieInfo> categories = new HashMap<>();
			Map<Long, FriendInfo> friendInfos = new HashMap<>();
			
			// 处理分组信息
			JSONArray array = (JSONArray)result.get("categories");
			CategorieInfo categorie;
			Object[] objs = array.toArray();
			for (Object obj : objs) {
				categorie = FastJson.toObject(obj.toString(), CategorieInfo.class);
				categories.put(categorie.getIndex(), categorie);
			}
			// 设置默认分组
			if(categories.size() == 0){
				categorie = new CategorieInfo();
				categorie.setIndex(0);
				categorie.setSort(1);
				categorie.setName("默认分组");
			}
			friendsList.setCategories(categories);
			
			// 处理好友信息
			FriendInfo friendInfo;
			long uin = -1L;
			
			// 好友信息
			array = (JSONArray)result.get("info");
			objs = array.toArray();
			for (Object obj : objs) {
				friendInfo = FastJson.toObject(obj.toString(), FriendInfo.class);
				friendInfos.put(friendInfo.getUin(), friendInfo);
			}
			
			// 备注信息
			array = (JSONArray)result.get("marknames");
			objs = array.toArray();
			for (Object obj : objs) {
				uin = ((JSONObject)obj).getLongValue("uin");
				friendInfo = friendInfos.get(uin);
				friendInfo = FastJson.appendObject(obj.toString(), friendInfo);
			}
			
			// 分组
			array = (JSONArray)result.get("friends");
			objs = array.toArray();
			for (Object obj : objs) {
				uin = ((JSONObject)obj).getLongValue("uin");
				friendInfo = friendInfos.get(uin);
				friendInfo = FastJson.appendObject(obj.toString(), friendInfo);
			}
			
			// 会员信息
			array = (JSONArray)result.get("vipinfo");
			objs = array.toArray();
			for (Object obj : objs) {
				uin = ((JSONObject)obj).getLongValue("u");
				friendInfo = friendInfos.get(uin);
				friendInfo = FastJson.appendObject(obj.toString(), friendInfo);
			}
			// 在线状态 
			JSONObject onlineBuddiesJson = JSONObject.parseObject(onlienBuddiesContent);
			array = (JSONArray)onlineBuddiesJson.get("result");
			objs = array.toArray();
			for (Object obj : objs) {
				uin = ((JSONObject)obj).getLongValue("uin");
				friendInfo = friendInfos.get(uin);
				friendInfo = FastJson.appendObject(obj.toString(), friendInfo);
			}
			friendsList.setFriends(friendInfos);
			session.setFriendsList(friendsList);
//		}
		
		return friendsList;
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
