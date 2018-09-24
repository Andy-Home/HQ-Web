package com.andy.controller;

import com.andy.Response;
import com.andy.ResponseUtils;
import com.andy.service.catalog.CatalogService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Andy on 2018/9/24 10:20.
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    CatalogService mCatalogService;

    @ResponseBody
    @RequestMapping("/sync")
    public Response syncCatalog(@RequestParam int userId) {
        List<Map<String, Object>> list;
        try {
            list = mCatalogService.syncCatalogs(userId);
        } catch (Exception e) {
            return ResponseUtils.getServerError(e);
        }
        return ResponseUtils.getSuccess(list);
    }

    @ResponseBody
    @RequestMapping("/syncCallback")
    public void syncSuccessCallback(@RequestParam int... catalogId) {
        mCatalogService.syncSuccess(catalogId);
    }

    @ResponseBody
    @RequestMapping("/new")
    public Response newCatalog(@RequestParam String data) {
        JSONObject json;
        try {
            json = JSONObject.fromObject(data);
        } catch (Exception e) {
            return ResponseUtils.getParameterError("check data is Json data");
        }

        int id = mCatalogService.insertNewCatalog(json);
        if (id > 0) {
            return ResponseUtils.getSuccess(id);
        }
        return ResponseUtils.getFail();
    }

    @ResponseBody
    @RequestMapping("/update")
    public Response updateCatalog(@RequestParam String data) {
        JSONObject json;
        try {
            json = JSONObject.fromObject(data);
        } catch (Exception e) {
            return ResponseUtils.getParameterError("check data is Json data");
        }

        boolean result = mCatalogService.updateCatalog(json);
        if (result) {
            return ResponseUtils.getSuccess();
        }
        return ResponseUtils.getFail();
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Response deleteCatalog(@RequestParam int catalogId,
                                  @RequestParam int userId) {
        boolean result = mCatalogService.deleteCatalog(catalogId, userId);
        if (result) {
            return ResponseUtils.getSuccess();
        }
        return ResponseUtils.getFail();
    }

    @ResponseBody
    @RequestMapping("/getCatalogs")
    public Response getCatalogList(@RequestParam int userId,
                                   @RequestParam int parentId) {
        List<Map<String, Object>> list;
        try {
            list = mCatalogService.getCatalogList(userId, parentId);
        } catch (Exception e) {
            return ResponseUtils.getServerError(e);
        }
        return ResponseUtils.getSuccess(list);
    }
}
