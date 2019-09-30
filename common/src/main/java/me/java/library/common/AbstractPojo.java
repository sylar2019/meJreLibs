package me.java.library.common;

import me.java.library.utils.base.JsonUtils;

import java.io.Serializable;

/**
 * File Name             :  AbstractPojo
 *
 * @author :  sylar
 * <p>
 * Create Date           :  2018/4/19
 * Description           :
 * Reviewed By           :
 * Reviewed On           :
 * Version History       :
 * Modified By           :
 * Modified Date         :
 * Comments              :
 * CopyRight             : COPYRIGHT(c) xxx.com   All Rights Reserved
 * *******************************************************************************************
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractPojo implements Serializable {

    @Override
    public String toString() {
        return JsonUtils.toJSONString(this);
    }
}
