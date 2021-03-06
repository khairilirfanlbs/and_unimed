package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

public class CoreXMLDeserializers extends Base {
    static final DatatypeFactory _dataTypeFactory;

    public class Std extends FromStringDeserializer<Object> {
        private static final long serialVersionUID = 1;
        protected final int _kind;

        public Std(Class<?> cls, int i) {
            super(cls);
            this._kind = i;
        }

        protected Object _deserialize(String str, DeserializationContext deserializationContext) {
            switch (this._kind) {
                case 1:
                    return CoreXMLDeserializers._dataTypeFactory.newDuration(str);
                case 3:
                    return QName.valueOf(str);
                default:
                    throw new IllegalStateException();
            }
        }

        public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
            if (this._kind != 2) {
                return super.deserialize(jsonParser, deserializationContext);
            }
            Date _parseDate = _parseDate(jsonParser, deserializationContext);
            if (_parseDate == null) {
                return null;
            }
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(_parseDate);
            TimeZone timeZone = deserializationContext.getTimeZone();
            if (timeZone != null) {
                gregorianCalendar.setTimeZone(timeZone);
            }
            return CoreXMLDeserializers._dataTypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        }
    }

    static {
        try {
            _dataTypeFactory = DatatypeFactory.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public JsonDeserializer<?> findBeanDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription) {
        Class rawClass = javaType.getRawClass();
        return rawClass == QName.class ? new Std(rawClass, 3) : rawClass == XMLGregorianCalendar.class ? new Std(rawClass, 2) : rawClass == Duration.class ? new Std(rawClass, 1) : null;
    }
}
