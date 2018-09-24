package com.andy.service.record;

import com.andy.dao.db.RecordInfoDao;
import com.andy.dao.db.SyncRecordDao;
import com.andy.dao.db.UserInfoDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 2018/9/22 10:28.
 */
@Service
public class RecordService {

    @Autowired
    private RecordInfoDao mRecordInfoDao;

    public List<Map<String, Object>> getRecordList(int userId, long time, int num) {
        return mRecordInfoDao.queryRecords(userId, time, num);
    }

    @Autowired
    private SyncRecordDao mSyncRecordDao;
    @Autowired
    private UserInfoDao mUserInfoDao;

    public int insertNewRecord(JSONObject jsonObject) {
        int userId = jsonObject.getInt("userId");
        double amount = jsonObject.getDouble("amount");
        int catalogId = jsonObject.getInt("catalogId");
        int type = jsonObject.getInt("type");
        Timestamp recordTime = (Timestamp) jsonObject.get("recordTime");

        int recordId = mRecordInfoDao.insertRecord(userId, amount, catalogId, type, recordTime);

        if (recordId > 0) {

            List<Integer> users = mUserInfoDao.queryRelationUserId(userId);
            if (users != null && users.size() > 0) {

                for (Integer id : users) {
                    mSyncRecordDao.insertRecord(id, userId, recordId, amount, catalogId, type, recordTime, "i");
                }
            }
            return recordId;
        } else {
            return 0;
        }
    }

    public boolean updateRecord(JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        int userId = jsonObject.getInt("userId");
        double amount = jsonObject.getDouble("amount");
        int catalogId = jsonObject.getInt("catalogId");
        int type = jsonObject.getInt("type");
        Timestamp recordTime = (Timestamp) jsonObject.get("recordTime");

        int result = mRecordInfoDao.updateRecord(id, userId, amount, catalogId, type, recordTime);
        if (result > 0) {

            List<Integer> users = mUserInfoDao.queryRelationUserId(userId);
            if (users != null && users.size() > 0) {

                for (Integer user : users) {
                    mSyncRecordDao.insertRecord(user, userId, id, amount, catalogId, type, recordTime, "u");
                }
            }
            return true;
        }
        return false;
    }

    public boolean deleteRecord(int recordId, int userId) {
        int result = mRecordInfoDao.deleteRecordById(recordId);
        if (result > 0) {

            List<Integer> users = mUserInfoDao.queryRelationUserId(userId);
            if (users != null && users.size() > 0) {

                for (Integer user : users) {
                    mSyncRecordDao.insertRecord(user, userId, recordId, null, null, null, null, "d");
                }
            }
            return true;
        }
        return false;
    }

    public List<Map<String, Object>> syncRecords(int userId) {
        return mSyncRecordDao.queryAllRecordByUserId(userId);
    }

    /**
     * 同步成功，删除表sync_record中同步成功的数据
     *
     * @param recordId 同步成功的数据ID
     */
    public void syncSuccess(int... recordId) {
        mSyncRecordDao.deleteRecordById(recordId);
    }
}
