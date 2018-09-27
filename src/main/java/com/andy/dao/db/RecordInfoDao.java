package com.andy.dao.db;

import com.andy.dao.entity.Record;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 2018/9/21 23:20.
 */
public interface RecordInfoDao {

    int insertRecord(Record record);

    int updateRecord(@Param("id") Integer id,
                     @Param("userId") Integer userId,
                     @Param("amount") Double amount,
                     @Param("catalogId") Integer catalogId,
                     @Param("type") Integer type,
                     @Param("recordTime") Timestamp recordTime);

    int deleteRecordById(@Param("id") int id);

    List<Map<String, Object>> queryRecords(@Param("userId") int userId,
                                           @Param("time") long time,
                                           @Param("num") int num);
}
