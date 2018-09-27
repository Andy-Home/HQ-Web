package com.andy.controller;

import com.andy.Response;
import com.andy.ResponseUtils;
import com.andy.service.user.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService mUserService;

    @ResponseBody
    @RequestMapping("/login")
    public Response login(@RequestParam String data) {
        JSONObject json;
        try {
            json = JSONObject.fromObject(data);
        } catch (Exception e) {
            return ResponseUtils.getParameterError("check data is Json data");
        }

        int id;
        try {
            id = mUserService.login(json);
        } catch (Exception e) {
            return ResponseUtils.getServerError(e);
        }

        if (id > 0) {
            return ResponseUtils.getSuccess(id);
        } else {
            return ResponseUtils.getFail();
        }
    }
}
