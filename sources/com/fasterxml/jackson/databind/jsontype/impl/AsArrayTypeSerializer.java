package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;

public class AsArrayTypeSerializer extends TypeSerializerBase {
    public AsArrayTypeSerializer(TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        super(typeIdResolver, beanProperty);
    }

    public AsArrayTypeSerializer forProperty(BeanProperty beanProperty) {
        return this._property == beanProperty ? this : new AsArrayTypeSerializer(this._idResolver, beanProperty);
    }

    public As getTypeInclusion() {
        return As.WRAPPER_ARRAY;
    }

    public void writeCustomTypePrefixForArray(Object obj, JsonGenerator jsonGenerator, String str) {
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(str);
        } else if (str != null) {
            jsonGenerator.writeTypeId(str);
        }
        jsonGenerator.writeStartArray();
    }

    public void writeCustomTypePrefixForObject(Object obj, JsonGenerator jsonGenerator, String str) {
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(str);
        } else if (str != null) {
            jsonGenerator.writeTypeId(str);
        }
        jsonGenerator.writeStartObject();
    }

    public void writeCustomTypeSuffixForArray(Object obj, JsonGenerator jsonGenerator, String str) {
        if (!jsonGenerator.canWriteTypeId()) {
            writeTypeSuffixForArray(obj, jsonGenerator);
        }
    }

    public void writeCustomTypeSuffixForObject(Object obj, JsonGenerator jsonGenerator, String str) {
        if (!jsonGenerator.canWriteTypeId()) {
            writeTypeSuffixForObject(obj, jsonGenerator);
        }
    }

    public void writeTypePrefixForArray(Object obj, JsonGenerator jsonGenerator) {
        String idFromValue = idFromValue(obj);
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(idFromValue);
        } else if (idFromValue != null) {
            jsonGenerator.writeTypeId(idFromValue);
        }
        jsonGenerator.writeStartArray();
    }

    public void writeTypePrefixForObject(Object obj, JsonGenerator jsonGenerator) {
        String idFromValue = idFromValue(obj);
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(idFromValue);
        } else if (idFromValue != null) {
            jsonGenerator.writeTypeId(idFromValue);
        }
        jsonGenerator.writeStartObject();
    }

    public void writeTypePrefixForScalar(Object obj, JsonGenerator jsonGenerator) {
        String idFromValue = idFromValue(obj);
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(idFromValue);
        } else if (idFromValue != null) {
            jsonGenerator.writeTypeId(idFromValue);
        }
    }

    public void writeTypePrefixForScalar(Object obj, JsonGenerator jsonGenerator, Class<?> cls) {
        String idFromValueAndType = idFromValueAndType(obj, cls);
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(idFromValueAndType);
        } else if (idFromValueAndType != null) {
            jsonGenerator.writeTypeId(idFromValueAndType);
        }
    }

    public void writeTypeSuffixForArray(Object obj, JsonGenerator jsonGenerator) {
        jsonGenerator.writeEndArray();
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeEndArray();
        }
    }

    public void writeTypeSuffixForObject(Object obj, JsonGenerator jsonGenerator) {
        jsonGenerator.writeEndObject();
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeEndArray();
        }
    }

    public void writeTypeSuffixForScalar(Object obj, JsonGenerator jsonGenerator) {
        if (!jsonGenerator.canWriteTypeId()) {
            jsonGenerator.writeEndArray();
        }
    }
}
