package com.andy.dao.db;

import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 2018/9/21 23:20.
 */
public interface SyncRecordDao {

    List<Map<String, Object>> queryAllRecordByUserId(@Param("userId") int userId);

    int insertRecord(@Param("toUserId") Integer toUserId,
                     @Param("userId") Integer userId,
                     @Param("recordId") Integer recordId,
                     @Param("amount") Double amount,
                     @Param("catalogId") Integer catalogId,
                     @Param("type") Integer type,
                     @Param("recordTime") Timestamp recordTime,
                     @Param("status") String status);

    int deleteRecordById(@Param("id") int... id);
}
