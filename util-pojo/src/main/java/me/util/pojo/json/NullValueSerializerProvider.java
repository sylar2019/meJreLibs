package me.util.pojo.json;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

/**
 * File Name             :  NullValueSerializerProvider
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
public class NullValueSerializerProvider extends DefaultSerializerProvider {
    public NullValueSerializerProvider() {
        super();
    }

    public NullValueSerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
        super(src, config, f);
    }


    @Override
    public DefaultSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
        return new NullValueSerializerProvider(this, config, jsf);
    }

    @Override
    public JsonSerializer<Object> findNullValueSerializer(BeanProperty property) throws JsonMappingException {
        JavaType type = property.getType();

        if (type.getRawClass().equals(String.class)) {
            return NullStringJsonSerializer.INSTANCE;
        }

        if (type.isArrayType() || type.isCollectionLikeType()) {
            return NullCollectionJsonSerializer.INSTANCE;

        }
        return super.findNullValueSerializer(property);
    }
}
