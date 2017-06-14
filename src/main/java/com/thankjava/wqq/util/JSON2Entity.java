package com.thankjava.wqq.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thankjava.toolkit3d.fastjson.FastJson;
import com.thankjava.wqq.entity.msg.Content;
import com.thankjava.wqq.entity.msg.Font;
import com.thankjava.wqq.entity.msg.PollMsg;
import com.thankjava.wqq.entity.msg.Value;
import com.thankjava.wqq.entity.wqq.CategorieInfo;
import com.thankjava.wqq.entity.wqq.DetailedInfo;
import com.thankjava.wqq.entity.wqq.DiscuInfo;
import com.thankjava.wqq.entity.wqq.DiscusList;
import com.thankjava.wqq.entity.wqq.FriendInfo;
import com.thankjava.wqq.entity.wqq.FriendsList;
import com.thankjava.wqq.entity.wqq.GroupInfo;
import com.thankjava.wqq.entity.wqq.GroupsList;

/**
 * 用于解析腾讯返回json格式的数据
* <p>Function: JSON2Entity</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2017年6月14日 下午2:27:34
* @version 1.0
 */
public class JSON2Entity {
	
	private static Logger logger = LoggerFactory.getLogger(JSON2Entity.class);

	/**
	 * 解析所有好友信息
	* <p>Function: userFriend2</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2017年6月14日 下午2:33:31
	* @version 1.0
	* @param content
	* @return
	 */
	public static FriendsList userFriends2(String content){
		FriendsList friendsList = new FriendsList();
		JSONObject userFriends2Json;
		try {
			userFriends2Json = JSONObject.parseObject(content);
			JSONObject result = (JSONObject) userFriends2Json.get("result");
			Map<Integer, CategorieInfo> categories = new HashMap<>();
			Map<Long, FriendInfo> friendInfos = new HashMap<>();
			// 处理分组信息
			JSONArray array = (JSONArray) result.get("categories");
			CategorieInfo categorie;
			Object[] objs = array.toArray();
			for (Object obj : objs) {
				categorie = FastJson.toObject(obj.toString(), CategorieInfo.class);
				categories.put(categorie.getIndex(), categorie);
			}
			// 设置默认分组
			if (categories.size() == 0) {
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
			array = (JSONArray) result.get("info");
			objs = array.toArray();
			for (Object obj : objs) {
				friendInfo = FastJson.toObject(obj.toString(), FriendInfo.class);
				friendInfos.put(friendInfo.getUin(), friendInfo);
			}

			// 备注名信息
			array = (JSONArray) result.get("marknames");
			objs = array.toArray();
			for (Object obj : objs) {
				uin = ((JSONObject) obj).getLongValue("uin");
				friendInfo = friendInfos.get(uin);
				friendInfo = FastJson.appendObject(obj.toString(), friendInfo);
			}

			// 分组
			array = (JSONArray) result.get("friends");
			objs = array.toArray();
			for (Object obj : objs) {
				uin = ((JSONObject) obj).getLongValue("uin");
				friendInfo = friendInfos.get(uin);
				friendInfo = FastJson.appendObject(obj.toString(), friendInfo);
			}

			// 会员信息
			array = (JSONArray) result.get("vipinfo");
			objs = array.toArray();
			for (Object obj : objs) {
				uin = ((JSONObject) obj).getLongValue("u");
				friendInfo = friendInfos.get(uin);
				friendInfo = FastJson.appendObject(obj.toString(), friendInfo);
			}
			friendsList.setFriendsInfo(friendInfos);
		} catch (Exception e) {
			logger.error("解析userFriends2返回数据失败", e);
			return null;
		}

		return friendsList;
	}
	
	/**
	 * 解析好友在线状态
	* <p>Function: onlineStatus</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2017年6月14日 下午4:05:59
	* @version 1.0
	* @param friendsList
	* @param content
	* @return
	 */
	public static FriendsList onlineStatus(FriendsList friendsList, String content){
		try {
			Map<Long, FriendInfo> friendsInfo = friendsList.getFriendsInfo();
			JSONObject onlineBuddiesJson = JSONObject.parseObject(content);
			JSONArray array = (JSONArray)onlineBuddiesJson.get("result");
			Object[] objs = array.toArray();
			Long uin = null;
			FriendInfo friendInfo;
			for (Object obj : objs) {
				uin = ((JSONObject)obj).getLongValue("uin");
				friendInfo = friendsInfo.get(uin);
				if(friendInfo != null){
					friendInfo = FastJson.appendObject(obj.toString(), friendInfo);
				}
			}
			friendsList.setFriendsInfo(friendsInfo);
		} catch (Exception e){
			logger.error("解析onlineStatus返回数据失败", e);
			return null;
		}
		return friendsList;
	}

	/**
	 * 解析讨论组信息
	* <p>Function: getDiscusList</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2017年6月14日 下午4:06:12
	* @version 1.0
	* @param content
	* @return
	 */
	public static DiscusList getDiscusList(String content){
		DiscusList discusList = new DiscusList();
		try {
			JSONObject discusListJson = JSONObject.parseObject(content);
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
		} catch (Exception e) {
			logger.error("解析getDiscusList返回数据失败", e);
			return null;
		}
		return discusList;
	}
	
	/**
	 * 解析群信息
	* <p>Function: getGroupsList</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2017年6月14日 下午4:08:47
	* @version 1.0
	* @param content
	* @return
	 */
	public static GroupsList getGroupsList(String content){
		GroupsList groupsList = new GroupsList();
		try {
			JSONObject groupsListJson = JSONObject.parseObject(content);
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
		} catch (Exception e) {
			logger.error("解析getGroupsList返回数据失败", e);
			return null;
		}
		return groupsList;
	}
	
	/**
	 * 解析个人信息
	* <p>Function: getSelfInfo</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2017年6月14日 下午4:11:42
	* @version 1.0
	* @param content
	* @return
	 */
	public static DetailedInfo getSelfInfo(String content){
		try {
			JSONObject discusListJson = JSONObject.parseObject(content);
			JSONObject result = (JSONObject) discusListJson.get("result");
			return FastJson.toObject(result.toJSONString(), DetailedInfo.class);
		} catch (Exception e) {
			logger.error("解析getSelfInfo返回数据失败", e);
		}
		return null;
	}
	
	/**
	 * 消息拉取数据解析
	* <p>Function: parseToPollMsg</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2017年6月14日 下午4:21:38
	* @version 1.0
	* @param content
	* @return
	 */
	public static PollMsg pollMsg(String content){
		try{
			JSONObject contentJson = JSONObject.parseObject(content);
			if(contentJson.getInteger("retcode") != 0){
				return null;
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
			return pollMsg;
		} catch (Exception e) {
			return null;
		}
	}
}
