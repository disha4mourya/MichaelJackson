package com.disha.michaeljackson.utils.mvp;

public interface LoadCallback<T> {

    void onSuccess(T response);

    void onFailure(Throwable throwable);
}
