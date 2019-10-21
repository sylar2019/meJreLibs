package me.java.library.common.model.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * File Name             :  NullStringJsonSerializer
 *
 * @Author :  sylar
 * @Create :  2018/12/12
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
public class NullStringJsonSerializer extends JsonSerializer<Object> {
    static final NullStringJsonSerializer INSTANCE = new NullStringJsonSerializer();

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeString("");
        } else {
            gen.writeObject(value);
        }
    }

    @Override
    public Class<Object> handledType() {
        return Object.class;
    }
}
