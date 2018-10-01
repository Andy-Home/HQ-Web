package com.andy.service.record;

import com.andy.dao.db.CatalogInfoDao;
import com.andy.dao.db.RecordInfoDao;
import com.andy.dao.db.UserInfoDao;
import com.andy.dao.entity.Catalog;
import com.andy.dao.entity.Record;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 2018/9/22 10:28.
 */
@Service
public class RecordService {

    @Autowired
    private RecordInfoDao mRecordInfoDao;

    public List<Map<String, Object>> getRecordList(int userId, long time, int minNum) {
        List<Map<String, Object>> result = new ArrayList<>();
        //默认三天内
        long period = 259200000;
        long startTime;
        long endTime = time;
        while (result.size() < minNum) {
            startTime = endTime;
            endTime = time - period;
            List<Map<String, Object>> map = mRecordInfoDao.queryRecords(userId, new Timestamp(startTime), new Timestamp(endTime));
            if (map.size() == 0) {
                break;
            }
            result.addAll(map);
        }

        return result;
    }

    @Autowired
    private UserInfoDao mUserInfoDao;
    @Autowired
    private CatalogInfoDao mCatalogInfoDao;

    public int insertNewRecord(JSONObject jsonObject) {
        int userId = jsonObject.getInt("userId");
        double amount = jsonObject.getDouble("amount");
        int catalogId = jsonObject.getInt("catalogId");
        long time = jsonObject.getLong("recordTime");

        Catalog catalog = mCatalogInfoDao.queryCatalog(catalogId);
        int type = catalog.getType();

        Timestamp recordTime = new Timestamp(time);
        Record record = new Record();
        record.setUserId(userId);
        record.setAmount(amount);
        record.setCatalogId(catalogId);
        record.setType(type);
        record.setRecordTime(recordTime);

        mRecordInfoDao.insertRecord(record);
        int recordId = record.getId();

        if (recordId > 0) {
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
            return true;
        }
        return false;
    }

    public boolean deleteRecord(int recordId, int userId) {
        int result = mRecordInfoDao.deleteRecordById(recordId);
        if (result > 0) {
            return true;
        }
        return false;
    }

    public List<Map<String,Object>> getSyncRecords(long startTime,long endTime,int userId){
        List<Integer> mUser = mUserInfoDao.queryRelationUserId(userId);

        if(mUser == null || mUser.size() == 0){
            return null;
        }else {

            int[] a = new int[mUser.size()];
            for (int i = 0; i < mUser.size(); i++){
                a[i] = mUser.get(i);
            }

            return mRecordInfoDao.querySyncRecords(new Timestamp(startTime), new Timestamp(endTime), a);
        }
    }
}
