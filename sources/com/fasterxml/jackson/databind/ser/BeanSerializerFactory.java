package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.AnnotationIntrospector.ReferenceProperty;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty.Std;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.impl.FilteredBeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.ser.std.AtomicReferenceSerializer;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BeanSerializerFactory extends BasicSerializerFactory implements Serializable {
    public static final BeanSerializerFactory instance = new BeanSerializerFactory(null);
    private static final long serialVersionUID = 1;

    protected BeanSerializerFactory(SerializerFactoryConfig serializerFactoryConfig) {
        super(serializerFactoryConfig);
    }

    protected BeanPropertyWriter _constructWriter(SerializerProvider serializerProvider, BeanPropertyDefinition beanPropertyDefinition, PropertyBuilder propertyBuilder, boolean z, AnnotatedMember annotatedMember) {
        PropertyName fullName = beanPropertyDefinition.getFullName();
        if (serializerProvider.canOverrideAccessModifiers()) {
            annotatedMember.fixAccess(serializerProvider.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
        }
        JavaType type = annotatedMember.getType();
        BeanProperty std = new Std(fullName, type, beanPropertyDefinition.getWrapperName(), propertyBuilder.getClassAnnotations(), annotatedMember, beanPropertyDefinition.getMetadata());
        JsonSerializer findSerializerFromAnnotation = findSerializerFromAnnotation(serializerProvider, annotatedMember);
        if (findSerializerFromAnnotation instanceof ResolvableSerializer) {
            ((ResolvableSerializer) findSerializerFromAnnotation).resolve(serializerProvider);
        }
        JsonSerializer handlePrimaryContextualization = serializerProvider.handlePrimaryContextualization(findSerializerFromAnnotation, std);
        TypeSerializer typeSerializer = null;
        if (ClassUtil.isCollectionMapOrArray(type.getRawClass()) || type.isCollectionLikeType() || type.isMapLikeType()) {
            typeSerializer = findPropertyContentTypeSerializer(type, serializerProvider.getConfig(), annotatedMember);
        }
        return propertyBuilder.buildWriter(serializerProvider, beanPropertyDefinition, type, handlePrimaryContextualization, findPropertyTypeSerializer(type, serializerProvider.getConfig(), annotatedMember), typeSerializer, annotatedMember, z);
    }

    protected JsonSerializer<?> _createSerializer2(SerializerProvider serializerProvider, JavaType javaType, BeanDescription beanDescription, boolean z) {
        JsonSerializer<?> jsonSerializer = null;
        SerializationConfig config = serializerProvider.getConfig();
        if (javaType.isContainerType()) {
            if (!z) {
                z = usesStaticTyping(config, beanDescription, null);
            }
            jsonSerializer = buildContainerSerializer(serializerProvider, javaType, beanDescription, z);
            if (jsonSerializer != null) {
                return jsonSerializer;
            }
        }
        if (!javaType.isReferenceType()) {
            for (Serializers findSerializer : customSerializers()) {
                jsonSerializer = findSerializer.findSerializer(config, javaType, beanDescription);
                if (jsonSerializer != null) {
                    break;
                }
            }
        }
        jsonSerializer = findReferenceSerializer(serializerProvider, (ReferenceType) javaType, beanDescription, z);
        if (jsonSerializer == null) {
            jsonSerializer = findSerializerByAnnotations(serializerProvider, javaType, beanDescription);
        }
        if (jsonSerializer == null) {
            jsonSerializer = findSerializerByLookup(javaType, config, beanDescription, z);
            if (jsonSerializer == null) {
                jsonSerializer = findSerializerByPrimaryType(serializerProvider, javaType, beanDescription, z);
                if (jsonSerializer == null) {
                    jsonSerializer = findBeanSerializer(serializerProvider, javaType, beanDescription);
                    if (jsonSerializer == null) {
                        jsonSerializer = findSerializerByAddonType(config, javaType, beanDescription, z);
                        if (jsonSerializer == null) {
                            jsonSerializer = serializerProvider.getUnknownTypeSerializer(beanDescription.getBeanClass());
                        }
                    }
                }
            }
        }
        if (jsonSerializer == null || !this._factoryConfig.hasSerializerModifiers()) {
            return jsonSerializer;
        }
        Iterator it = this._factoryConfig.serializerModifiers().iterator();
        while (true) {
            JsonSerializer<?> jsonSerializer2 = jsonSerializer;
            if (!it.hasNext()) {
                return jsonSerializer2;
            }
            jsonSerializer = ((BeanSerializerModifier) it.next()).modifySerializer(config, beanDescription, jsonSerializer2);
        }
    }

    protected JsonSerializer<Object> constructBeanSerializer(SerializerProvider serializerProvider, BeanDescription beanDescription) {
        if (beanDescription.getBeanClass() == Object.class) {
            return serializerProvider.getUnknownTypeSerializer(Object.class);
        }
        Iterator it;
        List list;
        BeanSerializerBuilder beanSerializerBuilder;
        MapperConfig config = serializerProvider.getConfig();
        BeanSerializerBuilder constructBeanSerializerBuilder = constructBeanSerializerBuilder(beanDescription);
        constructBeanSerializerBuilder.setConfig(config);
        List findBeanProperties = findBeanProperties(serializerProvider, beanDescription, constructBeanSerializerBuilder);
        findBeanProperties = findBeanProperties == null ? new ArrayList() : removeOverlappingTypeIds(serializerProvider, beanDescription, constructBeanSerializerBuilder, findBeanProperties);
        serializerProvider.getAnnotationIntrospector().findAndAddVirtualProperties(config, beanDescription.getClassInfo(), findBeanProperties);
        if (this._factoryConfig.hasSerializerModifiers()) {
            it = this._factoryConfig.serializerModifiers().iterator();
            while (true) {
                list = findBeanProperties;
                if (!it.hasNext()) {
                    break;
                }
                findBeanProperties = ((BeanSerializerModifier) it.next()).changeProperties(config, beanDescription, list);
            }
        } else {
            list = findBeanProperties;
        }
        findBeanProperties = filterBeanProperties(config, beanDescription, list);
        if (this._factoryConfig.hasSerializerModifiers()) {
            it = this._factoryConfig.serializerModifiers().iterator();
            while (true) {
                list = findBeanProperties;
                if (!it.hasNext()) {
                    break;
                }
                findBeanProperties = ((BeanSerializerModifier) it.next()).orderProperties(config, beanDescription, list);
            }
        } else {
            list = findBeanProperties;
        }
        constructBeanSerializerBuilder.setObjectIdWriter(constructObjectIdHandler(serializerProvider, beanDescription, list));
        constructBeanSerializerBuilder.setProperties(list);
        constructBeanSerializerBuilder.setFilterId(findFilterId(config, beanDescription));
        Annotated findAnyGetter = beanDescription.findAnyGetter();
        if (findAnyGetter != null) {
            if (config.canOverrideAccessModifiers()) {
                findAnyGetter.fixAccess(config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
            }
            JavaType type = findAnyGetter.getType();
            boolean isEnabled = config.isEnabled(MapperFeature.USE_STATIC_TYPING);
            JavaType contentType = type.getContentType();
            TypeSerializer createTypeSerializer = createTypeSerializer(config, contentType);
            JsonSerializer findSerializerFromAnnotation = findSerializerFromAnnotation(serializerProvider, findAnyGetter);
            constructBeanSerializerBuilder.setAnyGetter(new AnyGetterWriter(new Std(PropertyName.construct(findAnyGetter.getName()), contentType, null, beanDescription.getClassAnnotations(), findAnyGetter, PropertyMetadata.STD_OPTIONAL), findAnyGetter, findSerializerFromAnnotation == null ? MapSerializer.construct(null, type, isEnabled, createTypeSerializer, null, null, null) : findSerializerFromAnnotation));
        }
        processViews(config, constructBeanSerializerBuilder);
        if (this._factoryConfig.hasSerializerModifiers()) {
            beanSerializerBuilder = constructBeanSerializerBuilder;
            for (BeanSerializerModifier updateBuilder : this._factoryConfig.serializerModifiers()) {
                beanSerializerBuilder = updateBuilder.updateBuilder(config, beanDescription, beanSerializerBuilder);
            }
        } else {
            beanSerializerBuilder = constructBeanSerializerBuilder;
        }
        JsonSerializer<Object> build = beanSerializerBuilder.build();
        return (build == null && beanDescription.hasKnownClassAnnotations()) ? beanSerializerBuilder.createDummy() : build;
    }

    protected BeanSerializerBuilder constructBeanSerializerBuilder(BeanDescription beanDescription) {
        return new BeanSerializerBuilder(beanDescription);
    }

    protected BeanPropertyWriter constructFilteredBeanWriter(BeanPropertyWriter beanPropertyWriter, Class<?>[] clsArr) {
        return FilteredBeanPropertyWriter.constructViewBased(beanPropertyWriter, clsArr);
    }

    protected ObjectIdWriter constructObjectIdHandler(SerializerProvider serializerProvider, BeanDescription beanDescription, List<BeanPropertyWriter> list) {
        ObjectIdInfo objectIdInfo = beanDescription.getObjectIdInfo();
        if (objectIdInfo == null) {
            return null;
        }
        Type generatorType = objectIdInfo.getGeneratorType();
        if (generatorType == PropertyGenerator.class) {
            String simpleName = objectIdInfo.getPropertyName().getSimpleName();
            int size = list.size();
            for (int i = 0; i != size; i++) {
                BeanPropertyWriter beanPropertyWriter = (BeanPropertyWriter) list.get(i);
                if (simpleName.equals(beanPropertyWriter.getName())) {
                    if (i > 0) {
                        list.remove(i);
                        list.add(0, beanPropertyWriter);
                    }
                    return ObjectIdWriter.construct(beanPropertyWriter.getType(), (PropertyName) null, new PropertyBasedObjectIdGenerator(objectIdInfo, beanPropertyWriter), objectIdInfo.getAlwaysAsId());
                }
            }
            throw new IllegalArgumentException("Invalid Object Id definition for " + beanDescription.getBeanClass().getName() + ": can not find property with name '" + simpleName + "'");
        }
        return ObjectIdWriter.construct(serializerProvider.getTypeFactory().findTypeParameters(serializerProvider.constructType(generatorType), ObjectIdGenerator.class)[0], objectIdInfo.getPropertyName(), serializerProvider.objectIdGeneratorInstance(beanDescription.getClassInfo(), objectIdInfo), objectIdInfo.getAlwaysAsId());
    }

    protected PropertyBuilder constructPropertyBuilder(SerializationConfig serializationConfig, BeanDescription beanDescription) {
        return new PropertyBuilder(serializationConfig, beanDescription);
    }

    public JsonSerializer<Object> createSerializer(SerializerProvider serializerProvider, JavaType javaType) {
        MapperConfig config = serializerProvider.getConfig();
        BeanDescription introspect = config.introspect(javaType);
        JsonSerializer<Object> findSerializerFromAnnotation = findSerializerFromAnnotation(serializerProvider, introspect.getClassInfo());
        if (findSerializerFromAnnotation != null) {
            return findSerializerFromAnnotation;
        }
        boolean z;
        AnnotationIntrospector annotationIntrospector = config.getAnnotationIntrospector();
        JavaType refineSerializationType = annotationIntrospector == null ? javaType : annotationIntrospector.refineSerializationType(config, introspect.getClassInfo(), javaType);
        if (refineSerializationType == javaType) {
            z = false;
        } else if (refineSerializationType.hasRawClass(javaType.getRawClass())) {
            z = true;
        } else {
            introspect = config.introspect(refineSerializationType);
            z = true;
        }
        Converter findSerializationConverter = introspect.findSerializationConverter();
        if (findSerializationConverter == null) {
            return _createSerializer2(serializerProvider, refineSerializationType, introspect, z);
        }
        JsonSerializer findSerializerFromAnnotation2;
        JavaType outputType = findSerializationConverter.getOutputType(serializerProvider.getTypeFactory());
        if (outputType.hasRawClass(refineSerializationType.getRawClass())) {
            JsonSerializer<Object> findSerializerFromAnnotation22 = findSerializerFromAnnotation;
        } else {
            introspect = config.introspect(outputType);
            findSerializerFromAnnotation22 = findSerializerFromAnnotation(serializerProvider, introspect.getClassInfo());
        }
        if (findSerializerFromAnnotation22 == null && !outputType.isJavaLangObject()) {
            findSerializerFromAnnotation22 = _createSerializer2(serializerProvider, outputType, introspect, true);
        }
        return new StdDelegatingSerializer(findSerializationConverter, outputType, findSerializerFromAnnotation22);
    }

    protected Iterable<Serializers> customSerializers() {
        return this._factoryConfig.serializers();
    }

    protected List<BeanPropertyWriter> filterBeanProperties(SerializationConfig serializationConfig, BeanDescription beanDescription, List<BeanPropertyWriter> list) {
        String[] findPropertiesToIgnore = serializationConfig.getAnnotationIntrospector().findPropertiesToIgnore(beanDescription.getClassInfo(), true);
        if (findPropertiesToIgnore != null && findPropertiesToIgnore.length > 0) {
            HashSet arrayToSet = ArrayBuilders.arrayToSet(findPropertiesToIgnore);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (arrayToSet.contains(((BeanPropertyWriter) it.next()).getName())) {
                    it.remove();
                }
            }
        }
        return list;
    }

    protected List<BeanPropertyWriter> findBeanProperties(SerializerProvider serializerProvider, BeanDescription beanDescription, BeanSerializerBuilder beanSerializerBuilder) {
        List<BeanPropertyDefinition> findProperties = beanDescription.findProperties();
        SerializationConfig config = serializerProvider.getConfig();
        removeIgnorableTypes(config, beanDescription, findProperties);
        if (config.isEnabled(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS)) {
            removeSetterlessGetters(config, beanDescription, findProperties);
        }
        if (findProperties.isEmpty()) {
            return null;
        }
        boolean usesStaticTyping = usesStaticTyping(config, beanDescription, null);
        PropertyBuilder constructPropertyBuilder = constructPropertyBuilder(config, beanDescription);
        ArrayList arrayList = new ArrayList(findProperties.size());
        boolean canOverrideAccessModifiers = config.canOverrideAccessModifiers();
        boolean z = canOverrideAccessModifiers && config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS);
        for (BeanPropertyDefinition beanPropertyDefinition : findProperties) {
            AnnotatedMember accessor = beanPropertyDefinition.getAccessor();
            if (!beanPropertyDefinition.isTypeId()) {
                ReferenceProperty findReferenceType = beanPropertyDefinition.findReferenceType();
                if (findReferenceType == null || !findReferenceType.isBackReference()) {
                    if (accessor instanceof AnnotatedMethod) {
                        arrayList.add(_constructWriter(serializerProvider, beanPropertyDefinition, constructPropertyBuilder, usesStaticTyping, (AnnotatedMethod) accessor));
                    } else {
                        arrayList.add(_constructWriter(serializerProvider, beanPropertyDefinition, constructPropertyBuilder, usesStaticTyping, (AnnotatedField) accessor));
                    }
                }
            } else if (accessor != null) {
                if (canOverrideAccessModifiers) {
                    accessor.fixAccess(z);
                }
                beanSerializerBuilder.setTypeId(accessor);
            }
        }
        return arrayList;
    }

    public JsonSerializer<Object> findBeanSerializer(SerializerProvider serializerProvider, JavaType javaType, BeanDescription beanDescription) {
        return (isPotentialBeanType(javaType.getRawClass()) || javaType.isEnumType()) ? constructBeanSerializer(serializerProvider, beanDescription) : null;
    }

    public TypeSerializer findPropertyContentTypeSerializer(JavaType javaType, SerializationConfig serializationConfig, AnnotatedMember annotatedMember) {
        JavaType contentType = javaType.getContentType();
        TypeResolverBuilder findPropertyContentTypeResolver = serializationConfig.getAnnotationIntrospector().findPropertyContentTypeResolver(serializationConfig, annotatedMember, javaType);
        return findPropertyContentTypeResolver == null ? createTypeSerializer(serializationConfig, contentType) : findPropertyContentTypeResolver.buildTypeSerializer(serializationConfig, contentType, serializationConfig.getSubtypeResolver().collectAndResolveSubtypesByClass(serializationConfig, annotatedMember, contentType));
    }

    public TypeSerializer findPropertyTypeSerializer(JavaType javaType, SerializationConfig serializationConfig, AnnotatedMember annotatedMember) {
        TypeResolverBuilder findPropertyTypeResolver = serializationConfig.getAnnotationIntrospector().findPropertyTypeResolver(serializationConfig, annotatedMember, javaType);
        return findPropertyTypeResolver == null ? createTypeSerializer(serializationConfig, javaType) : findPropertyTypeResolver.buildTypeSerializer(serializationConfig, javaType, serializationConfig.getSubtypeResolver().collectAndResolveSubtypesByClass(serializationConfig, annotatedMember, javaType));
    }

    public JsonSerializer<?> findReferenceSerializer(SerializerProvider serializerProvider, ReferenceType referenceType, BeanDescription beanDescription, boolean z) {
        JavaType contentType = referenceType.getContentType();
        TypeSerializer typeSerializer = (TypeSerializer) contentType.getTypeHandler();
        SerializationConfig config = serializerProvider.getConfig();
        TypeSerializer createTypeSerializer = typeSerializer == null ? createTypeSerializer(config, contentType) : typeSerializer;
        JsonSerializer jsonSerializer = (JsonSerializer) contentType.getValueHandler();
        for (Serializers findReferenceSerializer : customSerializers()) {
            JsonSerializer<?> findReferenceSerializer2 = findReferenceSerializer.findReferenceSerializer(config, referenceType, beanDescription, createTypeSerializer, jsonSerializer);
            if (findReferenceSerializer2 != null) {
                return findReferenceSerializer2;
            }
        }
        return referenceType.isTypeOrSubTypeOf(AtomicReference.class) ? new AtomicReferenceSerializer(referenceType, z, createTypeSerializer, jsonSerializer) : null;
    }

    protected boolean isPotentialBeanType(Class<?> cls) {
        return ClassUtil.canBeABeanType(cls) == null && !ClassUtil.isProxyType(cls);
    }

    protected void processViews(SerializationConfig serializationConfig, BeanSerializerBuilder beanSerializerBuilder) {
        List properties = beanSerializerBuilder.getProperties();
        boolean isEnabled = serializationConfig.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
        int size = properties.size();
        BeanPropertyWriter[] beanPropertyWriterArr = new BeanPropertyWriter[size];
        int i = 0;
        int i2 = 0;
        while (i < size) {
            BeanPropertyWriter beanPropertyWriter = (BeanPropertyWriter) properties.get(i);
            Class[] views = beanPropertyWriter.getViews();
            if (views != null) {
                i2++;
                beanPropertyWriterArr[i] = constructFilteredBeanWriter(beanPropertyWriter, views);
            } else if (isEnabled) {
                beanPropertyWriterArr[i] = beanPropertyWriter;
            }
            i++;
            i2 = i2;
        }
        if (!isEnabled || i2 != 0) {
            beanSerializerBuilder.setFilteredProperties(beanPropertyWriterArr);
        }
    }

    protected void removeIgnorableTypes(SerializationConfig serializationConfig, BeanDescription beanDescription, List<BeanPropertyDefinition> list) {
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        HashMap hashMap = new HashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AnnotatedMember accessor = ((BeanPropertyDefinition) it.next()).getAccessor();
            if (accessor == null) {
                it.remove();
            } else {
                Class rawType = accessor.getRawType();
                Boolean bool = (Boolean) hashMap.get(rawType);
                if (bool == null) {
                    bool = annotationIntrospector.isIgnorableType(serializationConfig.introspectClassAnnotations(rawType).getClassInfo());
                    if (bool == null) {
                        bool = Boolean.FALSE;
                    }
                    hashMap.put(rawType, bool);
                }
                if (bool.booleanValue()) {
                    it.remove();
                }
            }
        }
    }

    protected List<BeanPropertyWriter> removeOverlappingTypeIds(SerializerProvider serializerProvider, BeanDescription beanDescription, BeanSerializerBuilder beanSerializerBuilder, List<BeanPropertyWriter> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            BeanPropertyWriter beanPropertyWriter = (BeanPropertyWriter) list.get(i);
            TypeSerializer typeSerializer = beanPropertyWriter.getTypeSerializer();
            if (typeSerializer != null && typeSerializer.getTypeInclusion() == As.EXTERNAL_PROPERTY) {
                PropertyName construct = PropertyName.construct(typeSerializer.getPropertyName());
                for (BeanPropertyWriter beanPropertyWriter2 : list) {
                    if (beanPropertyWriter2 != beanPropertyWriter && beanPropertyWriter2.wouldConflictWithName(construct)) {
                        beanPropertyWriter.assignTypeSerializer(null);
                        break;
                    }
                }
            }
        }
        return list;
    }

    protected void removeSetterlessGetters(SerializationConfig serializationConfig, BeanDescription beanDescription, List<BeanPropertyDefinition> list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            BeanPropertyDefinition beanPropertyDefinition = (BeanPropertyDefinition) it.next();
            if (!(beanPropertyDefinition.couldDeserialize() || beanPropertyDefinition.isExplicitlyIncluded())) {
                it.remove();
            }
        }
    }
}
