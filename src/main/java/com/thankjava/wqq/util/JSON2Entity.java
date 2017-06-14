package com.thankjava.wqq.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thankjava.toolkit3d.fastjson.FastJson;
import com.thankjava.wqq.entity.wqq.CategorieInfo;
import com.thankjava.wqq.entity.wqq.FriendInfo;
import com.thankjava.wqq.entity.wqq.FriendsList;

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
	* @param jsonContent
	* @return
	 */
	public static FriendsList userFriends2(String jsonContent){
		FriendsList friendsList = new FriendsList();
		JSONObject userFriends2Json;
		try {
			userFriends2Json = JSONObject.parseObject(jsonContent);
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
	
	public static FriendsList onlineStatus(FriendsList friendsList, String jsonContent){
		try {
			Map<Long, FriendInfo> friendsInfo = friendsList.getFriendsInfo();
			JSONObject onlineBuddiesJson = JSONObject.parseObject(jsonContent);
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
}
