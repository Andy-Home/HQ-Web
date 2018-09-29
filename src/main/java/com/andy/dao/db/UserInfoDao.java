package com.andy.dao.db;

import com.andy.dao.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserInfoDao {
    int registerWeiXin(User user);

    int updateWeiXinRefreshToken(@Param("openId") String openId,
                                 @Param("refreshToken") String refreshToken);

    int updateWeiXinAccessToken(@Param("openId") String openId,
                                @Param("accessToken") String accessToken);

    Map<String, Object> queryUserInfoByWeiXinOpenId(@Param("openId") String openId);

    Map<String, Object> queryUserInfoById(@Param("id") int id);

    List<Integer> queryRelationUserId(@Param("id") int id);

    int registerQQ(User user);

    Integer queryIdByQQOpenId(@Param("openId") String openId);

    Integer queryIdByWeiXinOpenId(@Param("openId") String openId);

    int updateUserInfoById(@Param("id") int id,
                           @Param("nickName") String nickName,
                           @Param("headUrl") String headUrl,
                           @Param("sex") int sex);

    int updateLoginStatus(@Param("id") int id,
                          @Param("status") int status);
}
