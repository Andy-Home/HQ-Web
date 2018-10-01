package com.andy.controller;

import com.andy.Response;
import com.andy.ResponseUtils;
import com.andy.service.record.RecordService;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordService mRecordService;

    @ResponseBody
    @RequestMapping("/getRecords")
    public Response getRecordList(@RequestParam int userId,
                                  @RequestParam long time) {
        //记录数量
        int num = 15;
        List<Map<String, Object>> list;
        try {
            list = mRecordService.getRecordList(userId, time, num);
        } catch (Exception e) {
            return ResponseUtils.getServerError(e);
        }
        return ResponseUtils.getSuccess(list);
    }

    @ResponseBody
    @RequestMapping("/new")
    public Response newRecord(@RequestParam String data) {
        JSONObject json;
        try {
            json = JSONObject.fromObject(data);
        } catch (Exception e) {
            return ResponseUtils.getParameterError("check data is Json data");
        }

        int id;
        try {
            id = mRecordService.insertNewRecord(json);
        } catch (Exception e) {
            if (e instanceof JSONException) {
                String msg = e.getMessage();
                String tips = msg.substring(msg.indexOf("["), msg.indexOf("]"));
                return ResponseUtils.getParameterError(tips + " is null");
            } else {
                return ResponseUtils.getServerError(e);
            }
        }

        if (id > 0) {
            return ResponseUtils.getSuccess(id);
        } else {
            return ResponseUtils.getFail();
        }
    }

    @ResponseBody
    @RequestMapping("/update")
    public Response updateRecord(@RequestParam String data) {
        JSONObject json;
        try {
            json = JSONObject.fromObject(data);
        } catch (Exception e) {
            return ResponseUtils.getParameterError("check data is Json data");
        }

        boolean result;
        try {
            result = mRecordService.updateRecord(json);
        } catch (Exception e) {
            if (e instanceof JSONException) {
                String msg = e.getMessage();
                String tips;
                tips = msg.substring(msg.indexOf("["), msg.indexOf("]"));
                return ResponseUtils.getParameterError(tips + " is null");
            } else {
                return ResponseUtils.getServerError(e);
            }
        }

        if (result) {
            return ResponseUtils.getSuccess();
        } else {
            return ResponseUtils.getFail();
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Response deleteRecord(@RequestParam int recordId,
                                 @RequestParam int userId) {
        boolean result;
        try {
            result = mRecordService.deleteRecord(recordId, userId);
        } catch (Exception e) {
            return ResponseUtils.getServerError(e);
        }

        if (result) {
            return ResponseUtils.getSuccess();
        } else {
            return ResponseUtils.getFail();
        }
    }

    @ResponseBody
    @RequestMapping("/sync")
    public Response sync(@RequestParam int userId,
                         @RequestParam long startTime,
                         @RequestParam long endTime) {
        List<Map<String, Object>> list;
        try {
            list = mRecordService.getSyncRecords(startTime, endTime, userId);
        } catch (Exception e) {
            return ResponseUtils.getServerError(e);
        }
        return ResponseUtils.getSuccess(list);
    }
}
