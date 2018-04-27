package com.imerchantech.michaeljackson.utils.mvp;

import java.util.HashMap;
import java.util.Map;

public class PresenterHolder {
    private static volatile PresenterHolder singleton = null;
    private Map<Class, BasePresenter> presenterMap;

    public static PresenterHolder getInstance() {
        if (singleton == null) {
            synchronized (PresenterHolder.class) {
                if (singleton == null) {
                    singleton = new PresenterHolder();
                }
            }
        }
        return singleton;
    }


    private PresenterHolder() {
        this.presenterMap = new HashMap<>();
    }

    public void putPresenter(Class c, BasePresenter p) {
        presenterMap.put(c, p);
    }

    public <T extends BasePresenter> T getPresenter(Class c) {
        return (T) presenterMap.get(c);
    }

    public void remove(Class c) {
        presenterMap.remove(c);
    }
}
