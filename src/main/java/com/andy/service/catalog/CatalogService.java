package com.andy.service.catalog;

import com.andy.dao.db.CatalogInfoDao;
import com.andy.dao.db.UserInfoDao;
import com.andy.dao.entity.Catalog;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    UserInfoDao mUserInfoDao;

    public int insertNewCatalog(JSONObject jsonObject) {
        int parentId = jsonObject.getInt("parentId");
        int userId = jsonObject.getInt("userId");
        String name = jsonObject.getString("name");
        int type = jsonObject.getInt("type");
        Catalog catalog = new Catalog();
        catalog.setParentId(parentId);
        catalog.setName(name);
        catalog.setUserId(userId);
        catalog.setType(type);

        mCatalogInfoDao.insertCatalog(catalog);
        int catalogId = catalog.getId();
        if (catalogId > 0) {
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
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteCatalog(int catalogId, int userId) {
        int result = mCatalogInfoDao.deleteCatalog(catalogId);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Map<String, Object>> getCatalogList(int userId, int parentId) {
        return mCatalogInfoDao.queryCatalogs(parentId, userId);
    }

    public List<Map<String, Object>> syncCatalogs(int userId, long updateTime) {
        List<Integer> mUser = mUserInfoDao.queryRelationUserId(userId);

        if(mUser == null || mUser.size() == 0){
            return null;
        }else {

            int[] a = new int[mUser.size()];
            for (int i = 0; i < mUser.size(); i++){
                a[i] = mUser.get(i);
            }

            return mCatalogInfoDao.querySyncCatalogs(new Timestamp(updateTime), a);
        }

    }
}
