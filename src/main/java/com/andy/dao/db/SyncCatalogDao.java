package com.andy.dao.db;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 2018/9/24 10:52.
 */
public interface SyncCatalogDao {

    List<Map<String, Object>> queryAllCatalogByUserId(@Param("userId") int userId);

    int insertCatalog(@Param("toUserId") Integer toUserId,
                      @Param("catalogId") Integer catalogId,
                      @Param("parentId") Integer parentId,
                      @Param("userId") Integer userId,
                      @Param("name") String name,
                      @Param("type") Integer type,
                      @Param("status") String status);

    int deleteCatalogById(@Param("id") int... id);
}
