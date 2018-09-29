package com.andy.service.user;

import com.andy.dao.db.UserInfoDao;
import com.andy.dao.entity.User;
import com.andy.dao.net.BaseListener;
import com.andy.dao.net.NetManager;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    private Logger mLog = Logger.getLogger(UserService.class);
    @Autowired
    UserInfoDao mUserInfoDao;
    private NetManager mNetManager = NetManager.getInstance();

    private int QQRegister(JSONObject jsonObject) {
        String openId = jsonObject.getString("openId");
        String accessToken = jsonObject.getString("accessToken");

        User user = new User();
        user.setQqOpenId(openId);
        user.setQqAccessToken(accessToken);

        mUserInfoDao.registerQQ(user);
        final int id = user.getId();

        mNetManager.requestQQUserInfo(openId, accessToken, new BaseListener<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                String nickName = (String) stringObjectMap.get("nickname");
                String headUrl = (String) stringObjectMap.get("headUrl");
                int sex = (int) stringObjectMap.get("sex");

                mUserInfoDao.updateUserInfoById(id, nickName, headUrl, sex);
            }

            @Override
            public void onError(String msg) {
                mLog.error("qqRegister : " + msg);
            }
        });
        return id;
    }

    private int WeiXinRegister(JSONObject jsonObject) {
        String openId = jsonObject.getString("openId");
        String accessToken = jsonObject.getString("accessToken");
        String refreshToken = jsonObject.getString("refreshToken");

        User user = new User();
        user.setWeiXinOpenId(openId);
        user.setWeiXinAccessToken(accessToken);
        user.setWeiXinRefreshToken(refreshToken);

        mUserInfoDao.registerWeiXin(user);
        return user.getId();
    }

    public int login(JSONObject jsonObject) {
        String channel = jsonObject.getString("channel");

        Integer id = 0;
        if (channel.equals("QQ")) {

            String openId = jsonObject.getString("openId");
            id = mUserInfoDao.queryIdByQQOpenId(openId);

            if (id == null) {
                id = QQRegister(jsonObject);
            }

            mUserInfoDao.updateLoginStatus(id, 2);
        } else if (channel.equals("WeiXin")) {

            String openId = jsonObject.getString("openId");
            id = mUserInfoDao.queryIdByWeiXinOpenId(openId);

            if (id == null) {
                id = WeiXinRegister(jsonObject);
            }
            mUserInfoDao.updateLoginStatus(id, 1);
        }
        return id;
    }
}
