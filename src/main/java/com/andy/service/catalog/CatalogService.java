package com.andy.service.catalog;

import com.andy.dao.db.CatalogInfoDao;
import com.andy.dao.db.SyncCatalogDao;
import com.andy.dao.db.UserInfoDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 2018/9/24 10:29.
 */
@Service
public class CatalogService {

    @Autowired
    CatalogInfoDao mCatalogInfoDao;

    @Autowired
    SyncCatalogDao mSyncCatalogDao;

    @Autowired
    UserInfoDao mUserInfoDao;

    public int insertNewCatalog(JSONObject jsonObject) {
        int parentId = jsonObject.getInt("parentId");
        int userId = jsonObject.getInt("userId");
        String name = jsonObject.getString("name");
        int type = jsonObject.getInt("type");

        int catalogId = mCatalogInfoDao.insertCatalog(parentId, userId, name, type);

        if (catalogId > 0) {
            List<Integer> users = mUserInfoDao.queryRelationUserId(userId);
            if (users != null && users.size() > 0) {

                for (Integer id : users) {
                    mSyncCatalogDao.insertCatalog(id, catalogId, parentId, userId, name, type, "i");
                }
            }
            return catalogId;
        } else {
            return 0;
        }
    }

    public boolean updateCatalog(JSONObject jsonObject) {
        int catalogId = jsonObject.getInt("catalogId");
        int parentId = jsonObject.getInt("parentId");
        int userId = jsonObject.getInt("userId");
        String name = jsonObject.getString("name");
        int type = jsonObject.getInt("type");

        int result = mCatalogInfoDao.updateCatalog(catalogId, parentId, userId, name, type);

        if (result > 0) {
            List<Integer> users = mUserInfoDao.queryRelationUserId(userId);
            if (users != null && users.size() > 0) {

                for (Integer id : users) {
                    mSyncCatalogDao.insertCatalog(id, catalogId, parentId, userId, name, type, "u");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteCatalog(int catalogId, int userId) {
        int result = mCatalogInfoDao.deleteCatalog(catalogId);

        if (result > 0) {
            List<Integer> users = mUserInfoDao.queryRelationUserId(userId);
            if (users != null && users.size() > 0) {

                for (Integer id : users) {
                    mSyncCatalogDao.insertCatalog(id, catalogId, null, userId, null, null, "d");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public List<Map<String, Object>> getCatalogList(int userId, int parentId) {
        return mCatalogInfoDao.queryCatalogs(parentId, userId);
    }

    public List<Map<String, Object>> syncCatalogs(int userId) {
        return mSyncCatalogDao.queryAllCatalogByUserId(userId);
    }

    /**
     * 同步成功，删除表sync_record中同步成功的数据
     *
     * @param catalogId 同步成功的数据ID
     */
    public void syncSuccess(int... catalogId) {
        mSyncCatalogDao.deleteCatalogById(catalogId);
    }
}
