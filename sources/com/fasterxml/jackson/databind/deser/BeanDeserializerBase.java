package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty.Std;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;
import com.fasterxml.jackson.databind.deser.impl.InnerClassProperty;
import com.fasterxml.jackson.databind.deser.impl.ManagedReferenceProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReferenceProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.impl.TypeWrappedDeserializer;
import com.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BeanDeserializerBase extends StdDeserializer<Object> implements ContextualDeserializer, ResolvableDeserializer, Serializable {
    protected static final PropertyName TEMP_PROPERTY_NAME = new PropertyName("#temporary-name");
    private static final long serialVersionUID = 1;
    protected SettableAnyProperty _anySetter;
    protected JsonDeserializer<Object> _arrayDelegateDeserializer;
    protected final Map<String, SettableBeanProperty> _backRefs;
    protected final BeanPropertyMap _beanProperties;
    protected final JavaType _beanType;
    private final transient Annotations _classAnnotations;
    protected JsonDeserializer<Object> _delegateDeserializer;
    protected ExternalTypeHandler _externalTypeIdHandler;
    protected final HashSet<String> _ignorableProps;
    protected final boolean _ignoreAllUnknown;
    protected final ValueInjector[] _injectables;
    protected final boolean _needViewProcesing;
    protected boolean _nonStandardCreation;
    protected final ObjectIdReader _objectIdReader;
    protected PropertyBasedCreator _propertyBasedCreator;
    protected final Shape _serializationShape;
    protected transient HashMap<ClassKey, JsonDeserializer<Object>> _subDeserializers;
    protected UnwrappedPropertyHandler _unwrappedPropertyHandler;
    protected final ValueInstantiator _valueInstantiator;
    protected boolean _vanillaProcessing;

    protected BeanDeserializerBase(BeanDeserializerBase beanDeserializerBase) {
        this(beanDeserializerBase, beanDeserializerBase._ignoreAllUnknown);
    }

    public BeanDeserializerBase(BeanDeserializerBase beanDeserializerBase, ObjectIdReader objectIdReader) {
        super(beanDeserializerBase._beanType);
        this._classAnnotations = beanDeserializerBase._classAnnotations;
        this._beanType = beanDeserializerBase._beanType;
        this._valueInstantiator = beanDeserializerBase._valueInstantiator;
        this._delegateDeserializer = beanDeserializerBase._delegateDeserializer;
        this._propertyBasedCreator = beanDeserializerBase._propertyBasedCreator;
        this._backRefs = beanDeserializerBase._backRefs;
        this._ignorableProps = beanDeserializerBase._ignorableProps;
        this._ignoreAllUnknown = beanDeserializerBase._ignoreAllUnknown;
        this._anySetter = beanDeserializerBase._anySetter;
        this._injectables = beanDeserializerBase._injectables;
        this._nonStandardCreation = beanDeserializerBase._nonStandardCreation;
        this._unwrappedPropertyHandler = beanDeserializerBase._unwrappedPropertyHandler;
        this._needViewProcesing = beanDeserializerBase._needViewProcesing;
        this._serializationShape = beanDeserializerBase._serializationShape;
        this._objectIdReader = objectIdReader;
        if (objectIdReader == null) {
            this._beanProperties = beanDeserializerBase._beanProperties;
            this._vanillaProcessing = beanDeserializerBase._vanillaProcessing;
            return;
        }
        this._beanProperties = beanDeserializerBase._beanProperties.withProperty(new ObjectIdValueProperty(objectIdReader, PropertyMetadata.STD_REQUIRED));
        this._vanillaProcessing = false;
    }

    protected BeanDeserializerBase(BeanDeserializerBase beanDeserializerBase, NameTransformer nameTransformer) {
        super(beanDeserializerBase._beanType);
        this._classAnnotations = beanDeserializerBase._classAnnotations;
        this._beanType = beanDeserializerBase._beanType;
        this._valueInstantiator = beanDeserializerBase._valueInstantiator;
        this._delegateDeserializer = beanDeserializerBase._delegateDeserializer;
        this._propertyBasedCreator = beanDeserializerBase._propertyBasedCreator;
        this._backRefs = beanDeserializerBase._backRefs;
        this._ignorableProps = beanDeserializerBase._ignorableProps;
        boolean z = nameTransformer != null || beanDeserializerBase._ignoreAllUnknown;
        this._ignoreAllUnknown = z;
        this._anySetter = beanDeserializerBase._anySetter;
        this._injectables = beanDeserializerBase._injectables;
        this._objectIdReader = beanDeserializerBase._objectIdReader;
        this._nonStandardCreation = beanDeserializerBase._nonStandardCreation;
        UnwrappedPropertyHandler unwrappedPropertyHandler = beanDeserializerBase._unwrappedPropertyHandler;
        if (nameTransformer != null) {
            if (unwrappedPropertyHandler != null) {
                unwrappedPropertyHandler = unwrappedPropertyHandler.renameAll(nameTransformer);
            }
            this._beanProperties = beanDeserializerBase._beanProperties.renameAll(nameTransformer);
        } else {
            this._beanProperties = beanDeserializerBase._beanProperties;
        }
        this._unwrappedPropertyHandler = unwrappedPropertyHandler;
        this._needViewProcesing = beanDeserializerBase._needViewProcesing;
        this._serializationShape = beanDeserializerBase._serializationShape;
        this._vanillaProcessing = false;
    }

    public BeanDeserializerBase(BeanDeserializerBase beanDeserializerBase, HashSet<String> hashSet) {
        super(beanDeserializerBase._beanType);
        this._classAnnotations = beanDeserializerBase._classAnnotations;
        this._beanType = beanDeserializerBase._beanType;
        this._valueInstantiator = beanDeserializerBase._valueInstantiator;
        this._delegateDeserializer = beanDeserializerBase._delegateDeserializer;
        this._propertyBasedCreator = beanDeserializerBase._propertyBasedCreator;
        this._backRefs = beanDeserializerBase._backRefs;
        this._ignorableProps = hashSet;
        this._ignoreAllUnknown = beanDeserializerBase._ignoreAllUnknown;
        this._anySetter = beanDeserializerBase._anySetter;
        this._injectables = beanDeserializerBase._injectables;
        this._nonStandardCreation = beanDeserializerBase._nonStandardCreation;
        this._unwrappedPropertyHandler = beanDeserializerBase._unwrappedPropertyHandler;
        this._needViewProcesing = beanDeserializerBase._needViewProcesing;
        this._serializationShape = beanDeserializerBase._serializationShape;
        this._vanillaProcessing = beanDeserializerBase._vanillaProcessing;
        this._objectIdReader = beanDeserializerBase._objectIdReader;
        this._beanProperties = beanDeserializerBase._beanProperties;
    }

    protected BeanDeserializerBase(BeanDeserializerBase beanDeserializerBase, boolean z) {
        super(beanDeserializerBase._beanType);
        this._classAnnotations = beanDeserializerBase._classAnnotations;
        this._beanType = beanDeserializerBase._beanType;
        this._valueInstantiator = beanDeserializerBase._valueInstantiator;
        this._delegateDeserializer = beanDeserializerBase._delegateDeserializer;
        this._propertyBasedCreator = beanDeserializerBase._propertyBasedCreator;
        this._beanProperties = beanDeserializerBase._beanProperties;
        this._backRefs = beanDeserializerBase._backRefs;
        this._ignorableProps = beanDeserializerBase._ignorableProps;
        this._ignoreAllUnknown = z;
        this._anySetter = beanDeserializerBase._anySetter;
        this._injectables = beanDeserializerBase._injectables;
        this._objectIdReader = beanDeserializerBase._objectIdReader;
        this._nonStandardCreation = beanDeserializerBase._nonStandardCreation;
        this._unwrappedPropertyHandler = beanDeserializerBase._unwrappedPropertyHandler;
        this._needViewProcesing = beanDeserializerBase._needViewProcesing;
        this._serializationShape = beanDeserializerBase._serializationShape;
        this._vanillaProcessing = beanDeserializerBase._vanillaProcessing;
    }

    protected BeanDeserializerBase(BeanDeserializerBuilder beanDeserializerBuilder, BeanDescription beanDescription, BeanPropertyMap beanPropertyMap, Map<String, SettableBeanProperty> map, HashSet<String> hashSet, boolean z, boolean z2) {
        boolean z3 = true;
        Shape shape = null;
        super(beanDescription.getType());
        this._classAnnotations = beanDescription.getClassInfo().getAnnotations();
        this._beanType = beanDescription.getType();
        this._valueInstantiator = beanDeserializerBuilder.getValueInstantiator();
        this._beanProperties = beanPropertyMap;
        this._backRefs = map;
        this._ignorableProps = hashSet;
        this._ignoreAllUnknown = z;
        this._anySetter = beanDeserializerBuilder.getAnySetter();
        List injectables = beanDeserializerBuilder.getInjectables();
        ValueInjector[] valueInjectorArr = (injectables == null || injectables.isEmpty()) ? null : (ValueInjector[]) injectables.toArray(new ValueInjector[injectables.size()]);
        this._injectables = valueInjectorArr;
        this._objectIdReader = beanDeserializerBuilder.getObjectIdReader();
        boolean z4 = this._unwrappedPropertyHandler != null || this._valueInstantiator.canCreateUsingDelegate() || this._valueInstantiator.canCreateFromObjectWith() || !this._valueInstantiator.canCreateUsingDefault();
        this._nonStandardCreation = z4;
        Value findExpectedFormat = beanDescription.findExpectedFormat(null);
        if (findExpectedFormat != null) {
            shape = findExpectedFormat.getShape();
        }
        this._serializationShape = shape;
        this._needViewProcesing = z2;
        if (this._nonStandardCreation || this._injectables != null || this._needViewProcesing || this._objectIdReader != null) {
            z3 = false;
        }
        this._vanillaProcessing = z3;
    }

    private JsonDeserializer<Object> _findDelegateDeserializer(DeserializationContext deserializationContext, JavaType javaType, AnnotatedWithParams annotatedWithParams) {
        BeanProperty std = new Std(TEMP_PROPERTY_NAME, javaType, null, this._classAnnotations, annotatedWithParams, PropertyMetadata.STD_OPTIONAL);
        TypeDeserializer typeDeserializer = (TypeDeserializer) javaType.getTypeHandler();
        if (typeDeserializer == null) {
            typeDeserializer = deserializationContext.getConfig().findTypeDeserializer(javaType);
        }
        JsonDeserializer<Object> findDeserializer = findDeserializer(deserializationContext, javaType, std);
        return typeDeserializer != null ? new TypeWrappedDeserializer(typeDeserializer.forProperty(std), findDeserializer) : findDeserializer;
    }

    private Throwable throwOrReturnThrowable(Throwable th, DeserializationContext deserializationContext) {
        Throwable th2 = th;
        while ((th2 instanceof InvocationTargetException) && th2.getCause() != null) {
            th2 = th2.getCause();
        }
        if (th2 instanceof Error) {
            throw ((Error) th2);
        }
        Object obj = (deserializationContext == null || deserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS)) ? 1 : null;
        if (th2 instanceof IOException) {
            if (obj == null || !(th2 instanceof JsonProcessingException)) {
                throw ((IOException) th2);
            }
        } else if (obj == null && (th2 instanceof RuntimeException)) {
            throw ((RuntimeException) th2);
        }
        return th2;
    }

    protected Object _convertObjectId(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, JsonDeserializer<Object> jsonDeserializer) {
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser, deserializationContext);
        if (obj instanceof String) {
            tokenBuffer.writeString((String) obj);
        } else if (obj instanceof Long) {
            tokenBuffer.writeNumber(((Long) obj).longValue());
        } else if (obj instanceof Integer) {
            tokenBuffer.writeNumber(((Integer) obj).intValue());
        } else {
            tokenBuffer.writeObject(obj);
        }
        JsonParser asParser = tokenBuffer.asParser();
        asParser.nextToken();
        return jsonDeserializer.deserialize(asParser, deserializationContext);
    }

    protected abstract Object _deserializeUsingPropertyBased(JsonParser jsonParser, DeserializationContext deserializationContext);

    protected JsonDeserializer<Object> _findSubclassDeserializer(DeserializationContext deserializationContext, Object obj, TokenBuffer tokenBuffer) {
        JsonDeserializer<Object> jsonDeserializer;
        synchronized (this) {
            jsonDeserializer = this._subDeserializers == null ? null : (JsonDeserializer) this._subDeserializers.get(new ClassKey(obj.getClass()));
        }
        if (jsonDeserializer == null) {
            jsonDeserializer = deserializationContext.findRootValueDeserializer(deserializationContext.constructType(obj.getClass()));
            if (jsonDeserializer != null) {
                synchronized (this) {
                    if (this._subDeserializers == null) {
                        this._subDeserializers = new HashMap();
                    }
                    this._subDeserializers.put(new ClassKey(obj.getClass()), jsonDeserializer);
                }
            }
        }
        return jsonDeserializer;
    }

    protected Object _handleTypedObjectId(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, Object obj2) {
        JsonDeserializer deserializer = this._objectIdReader.getDeserializer();
        if (deserializer.handledType() != obj2.getClass()) {
            obj2 = _convertObjectId(jsonParser, deserializationContext, obj2, deserializer);
        }
        deserializationContext.findObjectId(obj2, this._objectIdReader.generator, this._objectIdReader.resolver).bindItem(obj);
        SettableBeanProperty settableBeanProperty = this._objectIdReader.idProperty;
        return settableBeanProperty != null ? settableBeanProperty.setAndReturn(obj, obj2) : obj;
    }

    protected SettableBeanProperty _resolveInnerClassValuedProperty(DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty) {
        JsonDeserializer valueDeserializer = settableBeanProperty.getValueDeserializer();
        if (!(valueDeserializer instanceof BeanDeserializerBase) || ((BeanDeserializerBase) valueDeserializer).getValueInstantiator().canCreateUsingDefault()) {
            return settableBeanProperty;
        }
        Class rawClass = settableBeanProperty.getType().getRawClass();
        Class outerClass = ClassUtil.getOuterClass(rawClass);
        if (outerClass == null || outerClass != this._beanType.getRawClass()) {
            return settableBeanProperty;
        }
        for (Constructor constructor : rawClass.getConstructors()) {
            Class[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0] == outerClass) {
                if (deserializationContext.canOverrideAccessModifiers()) {
                    ClassUtil.checkAndFixAccess(constructor, deserializationContext.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
                }
                return new InnerClassProperty(settableBeanProperty, constructor);
            }
        }
        return settableBeanProperty;
    }

    protected SettableBeanProperty _resolveManagedReferenceProperty(DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty) {
        String managedReferenceName = settableBeanProperty.getManagedReferenceName();
        if (managedReferenceName == null) {
            return settableBeanProperty;
        }
        SettableBeanProperty findBackReference = settableBeanProperty.getValueDeserializer().findBackReference(managedReferenceName);
        if (findBackReference == null) {
            throw new IllegalArgumentException("Can not handle managed/back reference '" + managedReferenceName + "': no back reference property found from type " + settableBeanProperty.getType());
        }
        JavaType javaType = this._beanType;
        JavaType type = findBackReference.getType();
        boolean isContainerType = settableBeanProperty.getType().isContainerType();
        if (type.getRawClass().isAssignableFrom(javaType.getRawClass())) {
            return new ManagedReferenceProperty(settableBeanProperty, managedReferenceName, findBackReference, this._classAnnotations, isContainerType);
        }
        throw new IllegalArgumentException("Can not handle managed/back reference '" + managedReferenceName + "': back reference type (" + type.getRawClass().getName() + ") not compatible with managed type (" + javaType.getRawClass().getName() + ")");
    }

    protected SettableBeanProperty _resolveUnwrappedProperty(DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty) {
        AnnotatedMember member = settableBeanProperty.getMember();
        if (member != null) {
            NameTransformer findUnwrappingNameTransformer = deserializationContext.getAnnotationIntrospector().findUnwrappingNameTransformer(member);
            if (findUnwrappingNameTransformer != null) {
                JsonDeserializer valueDeserializer = settableBeanProperty.getValueDeserializer();
                JsonDeserializer unwrappingDeserializer = valueDeserializer.unwrappingDeserializer(findUnwrappingNameTransformer);
                if (!(unwrappingDeserializer == valueDeserializer || unwrappingDeserializer == null)) {
                    return settableBeanProperty.withValueDeserializer(unwrappingDeserializer);
                }
            }
        }
        return null;
    }

    protected SettableBeanProperty _resolvedObjectIdProperty(DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty) {
        ObjectIdInfo objectIdInfo = settableBeanProperty.getObjectIdInfo();
        return (objectIdInfo == null && settableBeanProperty.getValueDeserializer().getObjectIdReader() == null) ? settableBeanProperty : new ObjectIdReferenceProperty(settableBeanProperty, objectIdInfo);
    }

    protected abstract BeanDeserializerBase asArrayDeserializer();

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00b5  */
    public com.fasterxml.jackson.databind.JsonDeserializer<?> createContextual(com.fasterxml.jackson.databind.DeserializationContext r11, com.fasterxml.jackson.databind.BeanProperty r12) {
        /*
        r10 = this;
        r9 = 0;
        r6 = 0;
        r0 = r10._objectIdReader;
        r8 = r11.getAnnotationIntrospector();
        if (r12 == 0) goto L_0x000c;
    L_0x000a:
        if (r8 != 0) goto L_0x0062;
    L_0x000c:
        r7 = r6;
    L_0x000d:
        if (r7 == 0) goto L_0x0081;
    L_0x000f:
        if (r8 == 0) goto L_0x0081;
    L_0x0011:
        r1 = r8.findObjectIdInfo(r7);
        if (r1 == 0) goto L_0x0081;
    L_0x0017:
        r1 = r8.findObjectReferenceInfo(r7, r1);
        r0 = r1.getGeneratorType();
        r5 = r11.objectIdResolverInstance(r7, r1);
        r2 = com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator.class;
        if (r0 != r2) goto L_0x00ba;
    L_0x0027:
        r0 = r1.getPropertyName();
        r4 = r10.findProperty(r0);
        if (r4 != 0) goto L_0x0068;
    L_0x0031:
        r1 = new java.lang.IllegalArgumentException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Invalid Object Id definition for ";
        r2 = r2.append(r3);
        r3 = r10.handledType();
        r3 = r3.getName();
        r2 = r2.append(r3);
        r3 = ": can not find property with name '";
        r2 = r2.append(r3);
        r0 = r2.append(r0);
        r2 = "'";
        r0 = r0.append(r2);
        r0 = r0.toString();
        r1.<init>(r0);
        throw r1;
    L_0x0062:
        r1 = r12.getMember();
        r7 = r1;
        goto L_0x000d;
    L_0x0068:
        r0 = r4.getType();
        r2 = new com.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator;
        r3 = r1.getScope();
        r2.<init>(r3);
    L_0x0075:
        r3 = r11.findRootValueDeserializer(r0);
        r1 = r1.getPropertyName();
        r0 = com.fasterxml.jackson.databind.deser.impl.ObjectIdReader.construct(r0, r1, r2, r3, r4, r5);
    L_0x0081:
        if (r0 == 0) goto L_0x00d4;
    L_0x0083:
        r1 = r10._objectIdReader;
        if (r0 == r1) goto L_0x00d4;
    L_0x0087:
        r1 = r10.withObjectIdReader(r0);
    L_0x008b:
        if (r7 == 0) goto L_0x00a0;
    L_0x008d:
        r0 = r8.findPropertiesToIgnore(r7, r9);
        if (r0 == 0) goto L_0x00a0;
    L_0x0093:
        r2 = r0.length;
        if (r2 == 0) goto L_0x00a0;
    L_0x0096:
        r2 = r1._ignorableProps;
        r0 = com.fasterxml.jackson.databind.util.ArrayBuilders.setAndArray(r2, r0);
        r1 = r1.withIgnorableProperties(r0);
    L_0x00a0:
        if (r7 == 0) goto L_0x00d2;
    L_0x00a2:
        r0 = r8.findFormat(r7);
        if (r0 == 0) goto L_0x00d2;
    L_0x00a8:
        r6 = r0.getShape();
        r0 = r6;
    L_0x00ad:
        if (r0 != 0) goto L_0x00b1;
    L_0x00af:
        r0 = r10._serializationShape;
    L_0x00b1:
        r2 = com.fasterxml.jackson.annotation.JsonFormat.Shape.ARRAY;
        if (r0 != r2) goto L_0x00d0;
    L_0x00b5:
        r0 = r1.asArrayDeserializer();
    L_0x00b9:
        return r0;
    L_0x00ba:
        r0 = r11.constructType(r0);
        r2 = r11.getTypeFactory();
        r3 = com.fasterxml.jackson.annotation.ObjectIdGenerator.class;
        r0 = r2.findTypeParameters(r0, r3);
        r0 = r0[r9];
        r2 = r11.objectIdGeneratorInstance(r7, r1);
        r4 = r6;
        goto L_0x0075;
    L_0x00d0:
        r0 = r1;
        goto L_0x00b9;
    L_0x00d2:
        r0 = r6;
        goto L_0x00ad;
    L_0x00d4:
        r1 = r10;
        goto L_0x008b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.deser.BeanDeserializerBase.createContextual(com.fasterxml.jackson.databind.DeserializationContext, com.fasterxml.jackson.databind.BeanProperty):com.fasterxml.jackson.databind.JsonDeserializer<?>");
    }

    public Object deserializeFromArray(JsonParser jsonParser, DeserializationContext deserializationContext) {
        Object createUsingArrayDelegate;
        if (this._arrayDelegateDeserializer != null) {
            try {
                createUsingArrayDelegate = this._valueInstantiator.createUsingArrayDelegate(deserializationContext, this._arrayDelegateDeserializer.deserialize(jsonParser, deserializationContext));
                if (this._injectables == null) {
                    return createUsingArrayDelegate;
                }
                injectValues(deserializationContext, createUsingArrayDelegate);
                return createUsingArrayDelegate;
            } catch (Throwable e) {
                wrapInstantiationProblem(e, deserializationContext);
            }
        }
        if (this._delegateDeserializer != null) {
            try {
                createUsingArrayDelegate = this._valueInstantiator.createUsingArrayDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
                if (this._injectables == null) {
                    return createUsingArrayDelegate;
                }
                injectValues(deserializationContext, createUsingArrayDelegate);
                return createUsingArrayDelegate;
            } catch (Throwable e2) {
                wrapInstantiationProblem(e2, deserializationContext);
            }
        }
        if (deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            if (jsonParser.nextToken() == JsonToken.END_ARRAY && deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
                return null;
            }
            createUsingArrayDelegate = deserialize(jsonParser, deserializationContext);
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                return createUsingArrayDelegate;
            }
            throw deserializationContext.wrongTokenException(jsonParser, JsonToken.END_ARRAY, "Attempted to unwrap single value array for single '" + this._valueClass.getName() + "' value but there was more than a single value in the array");
        } else if (!deserializationContext.isEnabled(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)) {
            throw deserializationContext.mappingException(handledType());
        } else if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            return null;
        } else {
            throw deserializationContext.mappingException(handledType(), JsonToken.START_ARRAY);
        }
    }

    public Object deserializeFromBoolean(JsonParser jsonParser, DeserializationContext deserializationContext) {
        if (this._delegateDeserializer == null || this._valueInstantiator.canCreateFromBoolean()) {
            return this._valueInstantiator.createFromBoolean(deserializationContext, jsonParser.getCurrentToken() == JsonToken.VALUE_TRUE);
        }
        Object createUsingDelegate = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        if (this._injectables == null) {
            return createUsingDelegate;
        }
        injectValues(deserializationContext, createUsingDelegate);
        return createUsingDelegate;
    }

    public Object deserializeFromDouble(JsonParser jsonParser, DeserializationContext deserializationContext) {
        NumberType numberType = jsonParser.getNumberType();
        if (numberType == NumberType.DOUBLE || numberType == NumberType.FLOAT) {
            if (this._delegateDeserializer == null || this._valueInstantiator.canCreateFromDouble()) {
                return this._valueInstantiator.createFromDouble(deserializationContext, jsonParser.getDoubleValue());
            }
            Object createUsingDelegate = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
            if (this._injectables == null) {
                return createUsingDelegate;
            }
            injectValues(deserializationContext, createUsingDelegate);
            return createUsingDelegate;
        } else if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        } else {
            throw deserializationContext.instantiationException(handledType(), "no suitable creator method found to deserialize from JSON floating-point number");
        }
    }

    public Object deserializeFromEmbedded(JsonParser jsonParser, DeserializationContext deserializationContext) {
        return this._objectIdReader != null ? deserializeFromObjectId(jsonParser, deserializationContext) : jsonParser.getEmbeddedObject();
    }

    public Object deserializeFromNumber(JsonParser jsonParser, DeserializationContext deserializationContext) {
        if (this._objectIdReader != null) {
            return deserializeFromObjectId(jsonParser, deserializationContext);
        }
        Object createUsingDelegate;
        switch (jsonParser.getNumberType()) {
            case INT:
                if (this._delegateDeserializer == null || this._valueInstantiator.canCreateFromInt()) {
                    return this._valueInstantiator.createFromInt(deserializationContext, jsonParser.getIntValue());
                }
                createUsingDelegate = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
                if (this._injectables == null) {
                    return createUsingDelegate;
                }
                injectValues(deserializationContext, createUsingDelegate);
                return createUsingDelegate;
            case LONG:
                if (this._delegateDeserializer == null || this._valueInstantiator.canCreateFromInt()) {
                    return this._valueInstantiator.createFromLong(deserializationContext, jsonParser.getLongValue());
                }
                createUsingDelegate = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
                if (this._injectables == null) {
                    return createUsingDelegate;
                }
                injectValues(deserializationContext, createUsingDelegate);
                return createUsingDelegate;
            default:
                if (this._delegateDeserializer != null) {
                    createUsingDelegate = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
                    if (this._injectables == null) {
                        return createUsingDelegate;
                    }
                    injectValues(deserializationContext, createUsingDelegate);
                    return createUsingDelegate;
                }
                throw deserializationContext.instantiationException(handledType(), "no suitable creator method found to deserialize from JSON integer number");
        }
    }

    public abstract Object deserializeFromObject(JsonParser jsonParser, DeserializationContext deserializationContext);

    protected Object deserializeFromObjectId(JsonParser jsonParser, DeserializationContext deserializationContext) {
        Object readObjectReference = this._objectIdReader.readObjectReference(jsonParser, deserializationContext);
        ReadableObjectId findObjectId = deserializationContext.findObjectId(readObjectReference, this._objectIdReader.generator, this._objectIdReader.resolver);
        Object resolve = findObjectId.resolve();
        if (resolve != null) {
            return resolve;
        }
        throw new UnresolvedForwardReference(jsonParser, "Could not resolve Object Id [" + readObjectReference + "] (for " + this._beanType + ").", jsonParser.getCurrentLocation(), findObjectId);
    }

    protected Object deserializeFromObjectUsingNonDefault(JsonParser jsonParser, DeserializationContext deserializationContext) {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._propertyBasedCreator != null) {
            return _deserializeUsingPropertyBased(jsonParser, deserializationContext);
        }
        if (this._beanType.isAbstract()) {
            throw JsonMappingException.from(jsonParser, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
        }
        throw JsonMappingException.from(jsonParser, "No suitable constructor found for type " + this._beanType + ": can not instantiate from JSON object (missing default constructor or creator, or perhaps need to add/enable type information?)");
    }

    public Object deserializeFromString(JsonParser jsonParser, DeserializationContext deserializationContext) {
        if (this._objectIdReader != null) {
            return deserializeFromObjectId(jsonParser, deserializationContext);
        }
        if (this._delegateDeserializer == null || this._valueInstantiator.canCreateFromString()) {
            return this._valueInstantiator.createFromString(deserializationContext, jsonParser.getText());
        }
        Object createUsingDelegate = this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        if (this._injectables == null) {
            return createUsingDelegate;
        }
        injectValues(deserializationContext, createUsingDelegate);
        return createUsingDelegate;
    }

    protected Object deserializeWithObjectId(JsonParser jsonParser, DeserializationContext deserializationContext) {
        return deserializeFromObject(jsonParser, deserializationContext);
    }

    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) {
        if (this._objectIdReader != null) {
            if (jsonParser.canReadObjectId()) {
                Object objectId = jsonParser.getObjectId();
                if (objectId != null) {
                    return _handleTypedObjectId(jsonParser, deserializationContext, typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext), objectId);
                }
            }
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken != null) {
                if (currentToken.isScalarValue()) {
                    return deserializeFromObjectId(jsonParser, deserializationContext);
                }
                if (currentToken == JsonToken.START_OBJECT) {
                    currentToken = jsonParser.nextToken();
                }
                if (currentToken == JsonToken.FIELD_NAME && this._objectIdReader.maySerializeAsObject() && this._objectIdReader.isValidReferencePropertyName(jsonParser.getCurrentName(), jsonParser)) {
                    return deserializeFromObjectId(jsonParser, deserializationContext);
                }
            }
        }
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    public SettableBeanProperty findBackReference(String str) {
        return this._backRefs == null ? null : (SettableBeanProperty) this._backRefs.get(str);
    }

    protected JsonDeserializer<Object> findConvertingDeserializer(DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty) {
        AnnotationIntrospector annotationIntrospector = deserializationContext.getAnnotationIntrospector();
        if (annotationIntrospector != null) {
            Object findDeserializationConverter = annotationIntrospector.findDeserializationConverter(settableBeanProperty.getMember());
            if (findDeserializationConverter != null) {
                Converter converterInstance = deserializationContext.converterInstance(settableBeanProperty.getMember(), findDeserializationConverter);
                JavaType inputType = converterInstance.getInputType(deserializationContext.getTypeFactory());
                return new StdDelegatingDeserializer(converterInstance, inputType, deserializationContext.findContextualValueDeserializer(inputType, settableBeanProperty));
            }
        }
        return null;
    }

    public SettableBeanProperty findProperty(PropertyName propertyName) {
        return findProperty(propertyName.getSimpleName());
    }

    public SettableBeanProperty findProperty(String str) {
        SettableBeanProperty find = this._beanProperties == null ? null : this._beanProperties.find(str);
        return (find != null || this._propertyBasedCreator == null) ? find : this._propertyBasedCreator.findCreatorProperty(str);
    }

    public Collection<Object> getKnownPropertyNames() {
        Collection arrayList = new ArrayList();
        Iterator it = this._beanProperties.iterator();
        while (it.hasNext()) {
            arrayList.add(((SettableBeanProperty) it.next()).getName());
        }
        return arrayList;
    }

    public ObjectIdReader getObjectIdReader() {
        return this._objectIdReader;
    }

    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    protected void handleIgnoredProperty(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, String str) {
        if (deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)) {
            throw IgnoredPropertyException.from(jsonParser, obj, str, getKnownPropertyNames());
        }
        jsonParser.skipChildren();
    }

    protected Object handlePolymorphic(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, TokenBuffer tokenBuffer) {
        JsonDeserializer _findSubclassDeserializer = _findSubclassDeserializer(deserializationContext, obj, tokenBuffer);
        Object deserialize;
        if (_findSubclassDeserializer != null) {
            if (tokenBuffer != null) {
                tokenBuffer.writeEndObject();
                JsonParser asParser = tokenBuffer.asParser();
                asParser.nextToken();
                deserialize = _findSubclassDeserializer.deserialize(asParser, deserializationContext, obj);
            } else {
                deserialize = obj;
            }
            return jsonParser != null ? _findSubclassDeserializer.deserialize(jsonParser, deserializationContext, deserialize) : deserialize;
        } else {
            deserialize = tokenBuffer != null ? handleUnknownProperties(deserializationContext, obj, tokenBuffer) : obj;
            return jsonParser != null ? deserialize(jsonParser, deserializationContext, deserialize) : deserialize;
        }
    }

    protected Object handleUnknownProperties(DeserializationContext deserializationContext, Object obj, TokenBuffer tokenBuffer) {
        tokenBuffer.writeEndObject();
        JsonParser asParser = tokenBuffer.asParser();
        while (asParser.nextToken() != JsonToken.END_OBJECT) {
            String currentName = asParser.getCurrentName();
            asParser.nextToken();
            handleUnknownProperty(asParser, deserializationContext, obj, currentName);
        }
        return obj;
    }

    protected void handleUnknownProperty(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, String str) {
        if (this._ignoreAllUnknown) {
            jsonParser.skipChildren();
            return;
        }
        if (this._ignorableProps != null && this._ignorableProps.contains(str)) {
            handleIgnoredProperty(jsonParser, deserializationContext, obj, str);
        }
        super.handleUnknownProperty(jsonParser, deserializationContext, obj, str);
    }

    protected void handleUnknownVanilla(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, String str) {
        if (this._ignorableProps != null && this._ignorableProps.contains(str)) {
            handleIgnoredProperty(jsonParser, deserializationContext, obj, str);
        } else if (this._anySetter != null) {
            try {
                this._anySetter.deserializeAndSet(jsonParser, deserializationContext, obj, str);
            } catch (Throwable e) {
                wrapAndThrow(e, obj, str, deserializationContext);
            }
        } else {
            handleUnknownProperty(jsonParser, deserializationContext, obj, str);
        }
    }

    public Class<?> handledType() {
        return this._beanType.getRawClass();
    }

    protected void injectValues(DeserializationContext deserializationContext, Object obj) {
        for (ValueInjector inject : this._injectables) {
            inject.inject(deserializationContext, obj);
        }
    }

    public boolean isCachable() {
        return true;
    }

    public void resolve(DeserializationContext deserializationContext) {
        SettableBeanProperty[] fromObjectArguments;
        Builder builder;
        int i;
        JavaType delegateType;
        UnwrappedPropertyHandler unwrappedPropertyHandler = null;
        if (this._valueInstantiator.canCreateFromObjectWith()) {
            fromObjectArguments = this._valueInstantiator.getFromObjectArguments(deserializationContext.getConfig());
            builder = null;
            for (SettableBeanProperty settableBeanProperty : fromObjectArguments) {
                if (settableBeanProperty.hasValueTypeDeserializer()) {
                    TypeDeserializer valueTypeDeserializer = settableBeanProperty.getValueTypeDeserializer();
                    if (valueTypeDeserializer.getTypeInclusion() == As.EXTERNAL_PROPERTY) {
                        if (builder == null) {
                            builder = new Builder();
                        }
                        builder.addExternal(settableBeanProperty, valueTypeDeserializer);
                    }
                }
            }
        } else {
            fromObjectArguments = null;
            builder = null;
        }
        Iterator it = this._beanProperties.iterator();
        Builder builder2 = builder;
        while (it.hasNext()) {
            SettableBeanProperty withValueDeserializer;
            SettableBeanProperty settableBeanProperty2 = (SettableBeanProperty) it.next();
            JsonDeserializer valueDeserializer;
            if (settableBeanProperty2.hasValueDeserializer()) {
                valueDeserializer = settableBeanProperty2.getValueDeserializer();
                JsonDeserializer handlePrimaryContextualization = deserializationContext.handlePrimaryContextualization(valueDeserializer, settableBeanProperty2, settableBeanProperty2.getType());
                withValueDeserializer = handlePrimaryContextualization != valueDeserializer ? settableBeanProperty2.withValueDeserializer(handlePrimaryContextualization) : settableBeanProperty2;
            } else {
                valueDeserializer = findConvertingDeserializer(deserializationContext, settableBeanProperty2);
                if (valueDeserializer == null) {
                    valueDeserializer = findDeserializer(deserializationContext, settableBeanProperty2.getType(), settableBeanProperty2);
                }
                withValueDeserializer = settableBeanProperty2.withValueDeserializer(valueDeserializer);
            }
            withValueDeserializer = _resolveManagedReferenceProperty(deserializationContext, withValueDeserializer);
            if (!(withValueDeserializer instanceof ManagedReferenceProperty)) {
                withValueDeserializer = _resolvedObjectIdProperty(deserializationContext, withValueDeserializer);
            }
            SettableBeanProperty _resolveUnwrappedProperty = _resolveUnwrappedProperty(deserializationContext, withValueDeserializer);
            if (_resolveUnwrappedProperty != null) {
                UnwrappedPropertyHandler unwrappedPropertyHandler2 = unwrappedPropertyHandler == null ? new UnwrappedPropertyHandler() : unwrappedPropertyHandler;
                unwrappedPropertyHandler2.addProperty(_resolveUnwrappedProperty);
                this._beanProperties.remove(_resolveUnwrappedProperty);
                unwrappedPropertyHandler = unwrappedPropertyHandler2;
            } else {
                _resolveUnwrappedProperty = _resolveInnerClassValuedProperty(deserializationContext, withValueDeserializer);
                if (_resolveUnwrappedProperty != settableBeanProperty2) {
                    this._beanProperties.replace(_resolveUnwrappedProperty);
                    if (fromObjectArguments != null) {
                        int length = fromObjectArguments.length;
                        for (i = 0; i < length; i++) {
                            if (fromObjectArguments[i] == settableBeanProperty2) {
                                fromObjectArguments[i] = _resolveUnwrappedProperty;
                                break;
                            }
                        }
                    }
                }
                if (_resolveUnwrappedProperty.hasValueTypeDeserializer()) {
                    TypeDeserializer valueTypeDeserializer2 = _resolveUnwrappedProperty.getValueTypeDeserializer();
                    if (valueTypeDeserializer2.getTypeInclusion() == As.EXTERNAL_PROPERTY) {
                        builder = builder2 == null ? new Builder() : builder2;
                        builder.addExternal(_resolveUnwrappedProperty, valueTypeDeserializer2);
                        this._beanProperties.remove(_resolveUnwrappedProperty);
                        builder2 = builder;
                    }
                }
            }
        }
        if (!(this._anySetter == null || this._anySetter.hasValueDeserializer())) {
            this._anySetter = this._anySetter.withValueDeserializer(findDeserializer(deserializationContext, this._anySetter.getType(), this._anySetter.getProperty()));
        }
        if (this._valueInstantiator.canCreateUsingDelegate()) {
            delegateType = this._valueInstantiator.getDelegateType(deserializationContext.getConfig());
            if (delegateType == null) {
                throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._beanType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
            }
            this._delegateDeserializer = _findDelegateDeserializer(deserializationContext, delegateType, this._valueInstantiator.getDelegateCreator());
        }
        if (this._valueInstantiator.canCreateUsingArrayDelegate()) {
            delegateType = this._valueInstantiator.getArrayDelegateType(deserializationContext.getConfig());
            if (delegateType == null) {
                throw new IllegalArgumentException("Invalid array-delegate-creator definition for " + this._beanType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingArrayDelegate()', but null for 'getArrayDelegateType()'");
            }
            this._arrayDelegateDeserializer = _findDelegateDeserializer(deserializationContext, delegateType, this._valueInstantiator.getArrayDelegateCreator());
        }
        if (fromObjectArguments != null) {
            this._propertyBasedCreator = PropertyBasedCreator.construct(deserializationContext, this._valueInstantiator, fromObjectArguments);
        }
        if (builder2 != null) {
            this._externalTypeIdHandler = builder2.build();
            this._nonStandardCreation = true;
        }
        this._unwrappedPropertyHandler = unwrappedPropertyHandler;
        if (unwrappedPropertyHandler != null) {
            this._nonStandardCreation = true;
        }
        boolean z = this._vanillaProcessing && !this._nonStandardCreation;
        this._vanillaProcessing = z;
    }

    public abstract JsonDeserializer<Object> unwrappingDeserializer(NameTransformer nameTransformer);

    public abstract BeanDeserializerBase withIgnorableProperties(HashSet<String> hashSet);

    public abstract BeanDeserializerBase withObjectIdReader(ObjectIdReader objectIdReader);

    public void wrapAndThrow(Throwable th, Object obj, String str, DeserializationContext deserializationContext) {
        throw JsonMappingException.wrapWithPath(throwOrReturnThrowable(th, deserializationContext), obj, str);
    }

    protected void wrapInstantiationProblem(Throwable th, DeserializationContext deserializationContext) {
        Throwable th2 = th;
        while ((th2 instanceof InvocationTargetException) && th2.getCause() != null) {
            th2 = th2.getCause();
        }
        if (th2 instanceof Error) {
            throw ((Error) th2);
        }
        Object obj = (deserializationContext == null || deserializationContext.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS)) ? 1 : null;
        if (th2 instanceof IOException) {
            throw ((IOException) th2);
        } else if (obj == null && (th2 instanceof RuntimeException)) {
            throw ((RuntimeException) th2);
        } else {
            throw deserializationContext.instantiationException(this._beanType.getRawClass(), th2);
        }
    }
}
