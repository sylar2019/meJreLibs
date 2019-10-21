package me.java.library.common.model.po;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Preconditions;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;

/**
 * File Name             :  BaseEnumDeserializer
 *
 * @author :  sylar
 * @create :  2018/10/7
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
public class BaseEnumDeserializer extends JsonDeserializer<BaseEnum> {

    @Override
    @SuppressWarnings("unchecked")
    public BaseEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String currentName = jp.getCurrentName();
        Object currentValue = jp.getCurrentValue();

        @SuppressWarnings("rawtypes")
        Class enumType = null;

        try {
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(currentName, currentValue.getClass());
            enumType = propertyDescriptor.getPropertyType();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        Preconditions.checkNotNull(enumType, String.format("枚举转换异常。【%s】:【%s】", currentName, currentValue));

        JsonNode node = jp.getCodec().readTree(jp);
        int enumValue = node.intValue();
        return BaseEnum.fromValue(enumType, enumValue);
    }


}