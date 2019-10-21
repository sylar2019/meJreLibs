package me.java.library.common.chain;

import com.google.common.collect.Maps;
import me.java.library.utils.base.JsonUtils;

import java.util.Map;

/**
 * File Name             :  Context
 *
 * @Author :  sylar
 * @Create :  2019-10-20
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) me.iot.com   All Rights Reserved
 * *******************************************************************************************
 */
public class Context {
    private Object[] args;
    private Map<String, Object> taskResults = Maps.newLinkedHashMap();

    public void clear() {
        args = null;
        taskResults = null;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Map<String, Object> getTaskResults() {
        return taskResults;
    }

    public void setTaskResults(Map<String, Object> taskResults) {
        this.taskResults = taskResults;
    }

    @SuppressWarnings("unchecked")
    public <V> V getTaskResult(String key) {
        if (taskResults.containsKey(key)) {
            return (V) taskResults.get(key);
        }
        return null;
    }

    public void setTaskResult(String key, Object value) {
        taskResults.put(key, value);
    }

    @Override
    public String toString() {
        return JsonUtils.toJSONString(this);
    }

}
