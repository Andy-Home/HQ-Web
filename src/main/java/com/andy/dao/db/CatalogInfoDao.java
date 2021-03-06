package com.andy.dao.db;

import com.andy.dao.entity.Catalog;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 2018/9/24 10:30.
 */
public interface CatalogInfoDao {

    int insertCatalog(Catalog catalog);

    int updateCatalog(@Param("id") int id,
                      @Param("parentId") int parentId,
                      @Param("userId") int userId,
                      @Param("name") String name,
                      @Param("type") int type);

    int deleteCatalog(@Param("id") int id);

    List<Map<String, Object>> queryCatalogs(@Param("parentId") int parentId,
                                            @Param("userId") int userId);

    Catalog queryCatalog(@Param("id") int id);

    List<Map<String,Object>> querySyncCatalogs(@Param("updateTime") Timestamp updateTime,
                                               @Param("userId") int... userId);
}
