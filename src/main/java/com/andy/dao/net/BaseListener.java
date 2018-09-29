package com.andy.dao.net;

public interface BaseListener<T> {

    void onSuccess(T t);

    void onError(String msg);
}
