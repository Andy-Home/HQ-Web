package com.andy.dao.db;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserInfoDao {
    int registerWeiXin(@Param("openId") String openId,
                       @Param("accessToken") String accessToken,
                       @Param("refreshToken") String refreshToken);

    int updateWeiXinRefreshToken(@Param("openId") String openId,
                                 @Param("refreshToken") String refreshToken);

    int updateWeiXinAccessToken(@Param("openId") String openId,
                                @Param("accessToken") String accessToken);

    Map<String, Object> queryUserInfoByWeiXinOpenId(@Param("openId") String openId);

    Map<String, Object> queryUserInfoById(@Param("id") int id);

    List<Integer> queryRelationUserId(@Param("id") int id);

    int registerQQ(@Param("openId") String openId,
                   @Param("accessToken") String accessToken);

    Integer queryIdByQQOpenId(@Param("openId") String openId);

    Integer queryIdByWeiXinOpenId(@Param("openId") String openId);
}
