package com.wangzunbin.quartz.common.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Junhua.Zhang on 2016/7/7.
 */
public class Params extends HashMap<String, Object> implements Serializable {

    /** serialVersionUID */
    private static final long  serialVersionUID = 1L;
    public static final Params EMPTY            = new Params(0);

    public Params() {
        super(8);
    }

    public Params(int initialCapacity) {
        super(initialCapacity);
    }

    public Params(String key, Object value) {
        super(4);
        this.put(key, value);
    }

    public static Params create() {
        return new Params();
    }

    public static Params create(String key, Object value) {
        return new Params(key, value);
    }

    public Params set(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public <T> T getValue(String key) {
        return (T) get(key);
    }
}
