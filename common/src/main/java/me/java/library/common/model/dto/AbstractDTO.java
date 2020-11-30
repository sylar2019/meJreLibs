package me.java.library.common.model.dto;

import me.java.library.common.model.pojo.AbstractPojo;
import me.java.library.utils.base.JsonUtils;

public abstract class AbstractDTO extends AbstractPojo {

    @Override
    public String toString() {
        return JsonUtils.toJSONString(this);
    }
}
