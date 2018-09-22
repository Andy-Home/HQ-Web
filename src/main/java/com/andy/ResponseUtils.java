package com.andy;

/**
 * Created by Andy on 2018/9/22 11:02.
 */
public class ResponseUtils {
    static int CODE_SUCCESS = 0;
    static int CODE_PARAMETER_ERROR = 1;
    static int CODE_SERVER_ERROR = 2;
    static int CODE_FAIL = 3;

    static String MSG_SUCCESS = "success";
    static String MSG_PARAMETER_ERROR = "parameter error";
    static String MSG_SERVER_ERROR = "server error";
    static String MSG_FAIL = "fail";

    public static Response getResponse(int code, String msg) {
        return getResponse(code, msg, null);
    }

    public static Response getResponse(int code, String msg, Object result) {
        Response response = new Response();
        response.setCode(code);
        if (msg != null) {
            response.setMsg(msg);
        }
        if (result != null) {
            response.setResult(result);
        }
        return response;
    }

    public static Response getSuccess() {
        return getSuccess(null);
    }

    public static Response getSuccess(Object result) {
        return getResponse(CODE_SUCCESS, MSG_SUCCESS, result);
    }

    public static Response getParameterError() {
        return getResponse(CODE_PARAMETER_ERROR, MSG_PARAMETER_ERROR);
    }

    public static Response getParameterError(Object result) {
        return getResponse(CODE_PARAMETER_ERROR, MSG_PARAMETER_ERROR, result);
    }

    public static Response getServerError(Exception e) {
        e.printStackTrace();
        return getResponse(CODE_SERVER_ERROR, MSG_SERVER_ERROR);
    }

    public static Response getServerError(Object result) {
        return getResponse(CODE_SERVER_ERROR, MSG_SERVER_ERROR, result);
    }

    public static Response getFail() {
        return getSuccess(null);
    }

    public static Response getFail(Object result) {
        return getResponse(CODE_FAIL, MSG_FAIL, result);
    }

}
