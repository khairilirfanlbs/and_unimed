package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Typing;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

public abstract class AnnotationIntrospector implements Versioned, Serializable {

    public class ReferenceProperty {
        private final String _name;
        private final Type _type;

        public enum Type {
            MANAGED_REFERENCE,
            BACK_REFERENCE
        }

        public ReferenceProperty(Type type, String str) {
            this._type = type;
            this._name = str;
        }

        public static ReferenceProperty back(String str) {
            return new ReferenceProperty(Type.BACK_REFERENCE, str);
        }

        public static ReferenceProperty managed(String str) {
            return new ReferenceProperty(Type.MANAGED_REFERENCE, str);
        }

        public String getName() {
            return this._name;
        }

        public boolean isBackReference() {
            return this._type == Type.BACK_REFERENCE;
        }

        public boolean isManagedReference() {
            return this._type == Type.MANAGED_REFERENCE;
        }
    }

    public static AnnotationIntrospector nopInstance() {
        return NopAnnotationIntrospector.instance;
    }

    protected <A extends Annotation> A _findAnnotation(Annotated annotated, Class<A> cls) {
        return annotated.getAnnotation(cls);
    }

    protected boolean _hasAnnotation(Annotated annotated, Class<? extends Annotation> cls) {
        return annotated.hasAnnotation(cls);
    }

    protected boolean _hasOneOf(Annotated annotated, Class<? extends Annotation>[] clsArr) {
        return annotated.hasOneOf(clsArr);
    }

    public void findAndAddVirtualProperties(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, List<BeanPropertyWriter> list) {
    }

    public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass annotatedClass, VisibilityChecker<?> visibilityChecker) {
        return visibilityChecker;
    }

    public Object findContentDeserializer(Annotated annotated) {
        return null;
    }

    public Object findContentSerializer(Annotated annotated) {
        return null;
    }

    public Mode findCreatorBinding(Annotated annotated) {
        return null;
    }

    public Object findDeserializationContentConverter(AnnotatedMember annotatedMember) {
        return null;
    }

    @Deprecated
    public Class<?> findDeserializationContentType(Annotated annotated, JavaType javaType) {
        return null;
    }

    public Object findDeserializationConverter(Annotated annotated) {
        return null;
    }

    @Deprecated
    public Class<?> findDeserializationKeyType(Annotated annotated, JavaType javaType) {
        return null;
    }

    @Deprecated
    public Class<?> findDeserializationType(Annotated annotated, JavaType javaType) {
        return null;
    }

    public Object findDeserializer(Annotated annotated) {
        return null;
    }

    public String findEnumValue(Enum<?> enumR) {
        return enumR.name();
    }

    public String[] findEnumValues(Class<?> cls, Enum<?>[] enumArr, String[] strArr) {
        int length = enumArr.length;
        for (int i = 0; i < length; i++) {
            strArr[i] = findEnumValue(enumArr[i]);
        }
        return strArr;
    }

    public Object findFilterId(Annotated annotated) {
        return null;
    }

    public Value findFormat(Annotated annotated) {
        return null;
    }

    public Boolean findIgnoreUnknownProperties(AnnotatedClass annotatedClass) {
        return null;
    }

    public String findImplicitPropertyName(AnnotatedMember annotatedMember) {
        return null;
    }

    public Object findInjectableValueId(AnnotatedMember annotatedMember) {
        return null;
    }

    public Object findKeyDeserializer(Annotated annotated) {
        return null;
    }

    public Object findKeySerializer(Annotated annotated) {
        return null;
    }

    public PropertyName findNameForDeserialization(Annotated annotated) {
        return null;
    }

    public PropertyName findNameForSerialization(Annotated annotated) {
        return null;
    }

    public Object findNamingStrategy(AnnotatedClass annotatedClass) {
        return null;
    }

    public Object findNullSerializer(Annotated annotated) {
        return null;
    }

    public ObjectIdInfo findObjectIdInfo(Annotated annotated) {
        return null;
    }

    public ObjectIdInfo findObjectReferenceInfo(Annotated annotated, ObjectIdInfo objectIdInfo) {
        return objectIdInfo;
    }

    public Class<?> findPOJOBuilder(AnnotatedClass annotatedClass) {
        return null;
    }

    public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass annotatedClass) {
        return null;
    }

    public String[] findPropertiesToIgnore(Annotated annotated, boolean z) {
        return null;
    }

    public Access findPropertyAccess(Annotated annotated) {
        return null;
    }

    public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        return null;
    }

    public String findPropertyDefaultValue(Annotated annotated) {
        return null;
    }

    public String findPropertyDescription(Annotated annotated) {
        return null;
    }

    public JsonInclude.Value findPropertyInclusion(Annotated annotated) {
        return JsonInclude.Value.empty();
    }

    public Integer findPropertyIndex(Annotated annotated) {
        return null;
    }

    public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> mapperConfig, AnnotatedMember annotatedMember, JavaType javaType) {
        return null;
    }

    public ReferenceProperty findReferenceType(AnnotatedMember annotatedMember) {
        return null;
    }

    public PropertyName findRootName(AnnotatedClass annotatedClass) {
        return null;
    }

    public Object findSerializationContentConverter(AnnotatedMember annotatedMember) {
        return null;
    }

    @Deprecated
    public Class<?> findSerializationContentType(Annotated annotated, JavaType javaType) {
        return null;
    }

    public Object findSerializationConverter(Annotated annotated) {
        return null;
    }

    @Deprecated
    public Class<?> findSerializationKeyType(Annotated annotated, JavaType javaType) {
        return null;
    }

    public String[] findSerializationPropertyOrder(AnnotatedClass annotatedClass) {
        return null;
    }

    public Boolean findSerializationSortAlphabetically(Annotated annotated) {
        return null;
    }

    @Deprecated
    public Class<?> findSerializationType(Annotated annotated) {
        return null;
    }

    public Typing findSerializationTyping(Annotated annotated) {
        return null;
    }

    public Object findSerializer(Annotated annotated) {
        return null;
    }

    public List<NamedType> findSubtypes(Annotated annotated) {
        return null;
    }

    public String findTypeName(AnnotatedClass annotatedClass) {
        return null;
    }

    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, JavaType javaType) {
        return null;
    }

    public NameTransformer findUnwrappingNameTransformer(AnnotatedMember annotatedMember) {
        return null;
    }

    public Object findValueInstantiator(AnnotatedClass annotatedClass) {
        return null;
    }

    public Class<?>[] findViews(Annotated annotated) {
        return null;
    }

    public PropertyName findWrapperName(Annotated annotated) {
        return null;
    }

    public boolean hasAnyGetterAnnotation(AnnotatedMethod annotatedMethod) {
        return false;
    }

    public boolean hasAnySetterAnnotation(AnnotatedMethod annotatedMethod) {
        return false;
    }

    public boolean hasAsValueAnnotation(AnnotatedMethod annotatedMethod) {
        return false;
    }

    public boolean hasCreatorAnnotation(Annotated annotated) {
        return false;
    }

    public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
        return false;
    }

    public Boolean hasRequiredMarker(AnnotatedMember annotatedMember) {
        return null;
    }

    public boolean isAnnotationBundle(Annotation annotation) {
        return false;
    }

    public Boolean isIgnorableType(AnnotatedClass annotatedClass) {
        return null;
    }

    public Boolean isTypeId(AnnotatedMember annotatedMember) {
        return null;
    }

    public JavaType refineDeserializationType(MapperConfig<?> mapperConfig, Annotated annotated, JavaType javaType) {
        MapLikeType constructSpecializedType;
        JavaType keyType;
        Class findDeserializationKeyType;
        TypeFactory typeFactory = mapperConfig.getTypeFactory();
        Class findDeserializationType = findDeserializationType(annotated, javaType);
        if (findDeserializationType == null || javaType.hasRawClass(findDeserializationType)) {
            JavaType constructSpecializedType2 = javaType;
        } else {
            try {
                constructSpecializedType2 = typeFactory.constructSpecializedType(javaType, findDeserializationType);
            } catch (Throwable e) {
                throw new JsonMappingException(null, String.format("Failed to narrow type %s with annotation (value %s), from '%s': %s", new Object[]{javaType, findDeserializationType.getName(), annotated.getName(), e.getMessage()}), e);
            }
        }
        if (constructSpecializedType2.isMapLikeType()) {
            keyType = constructSpecializedType2.getKeyType();
            findDeserializationKeyType = findDeserializationKeyType(annotated, keyType);
            if (findDeserializationKeyType != null) {
                try {
                    constructSpecializedType2 = constructSpecializedType2.withKeyType(typeFactory.constructSpecializedType(keyType, findDeserializationKeyType));
                } catch (Throwable e2) {
                    throw new JsonMappingException(null, String.format("Failed to narrow key type of %s with concrete-type annotation (value %s), from '%s': %s", new Object[]{constructSpecializedType2, findDeserializationKeyType.getName(), annotated.getName(), e2.getMessage()}), e2);
                }
            }
        }
        keyType = constructSpecializedType2.getContentType();
        if (keyType == null) {
            return constructSpecializedType2;
        }
        findDeserializationKeyType = findDeserializationContentType(annotated, keyType);
        if (findDeserializationKeyType == null) {
            return constructSpecializedType2;
        }
        try {
            return constructSpecializedType2.withContentType(typeFactory.constructSpecializedType(keyType, findDeserializationKeyType));
        } catch (Throwable e22) {
            throw new JsonMappingException(null, String.format("Failed to narrow value type of %s with concrete-type annotation (value %s), from '%s': %s", new Object[]{constructSpecializedType2, findDeserializationKeyType.getName(), annotated.getName(), e22.getMessage()}), e22);
        }
    }

    public JavaType refineSerializationType(MapperConfig<?> mapperConfig, Annotated annotated, JavaType javaType) {
        JavaType javaType2;
        JavaType keyType;
        Class findSerializationKeyType;
        TypeFactory typeFactory = mapperConfig.getTypeFactory();
        Class findSerializationType = findSerializationType(annotated);
        if (findSerializationType == null) {
            javaType2 = javaType;
        } else if (javaType.hasRawClass(findSerializationType)) {
            javaType2 = javaType.withStaticTyping();
        } else {
            try {
                javaType2 = typeFactory.constructGeneralizedType(javaType, findSerializationType);
            } catch (Throwable e) {
                throw new JsonMappingException(null, String.format("Failed to widen type %s with annotation (value %s), from '%s': %s", new Object[]{javaType, findSerializationType.getName(), annotated.getName(), e.getMessage()}), e);
            }
        }
        if (javaType2.isMapLikeType()) {
            keyType = javaType2.getKeyType();
            findSerializationKeyType = findSerializationKeyType(annotated, keyType);
            if (findSerializationKeyType != null) {
                if (keyType.hasRawClass(findSerializationKeyType)) {
                    keyType = keyType.withStaticTyping();
                } else {
                    try {
                        keyType = typeFactory.constructGeneralizedType(keyType, findSerializationKeyType);
                    } catch (Throwable e2) {
                        throw new JsonMappingException(null, String.format("Failed to widen key type of %s with concrete-type annotation (value %s), from '%s': %s", new Object[]{javaType2, findSerializationKeyType.getName(), annotated.getName(), e2.getMessage()}), e2);
                    }
                }
                javaType2 = ((MapLikeType) javaType2).withKeyType(keyType);
            }
        }
        keyType = javaType2.getContentType();
        if (keyType == null) {
            return javaType2;
        }
        findSerializationKeyType = findSerializationContentType(annotated, keyType);
        if (findSerializationKeyType == null) {
            return javaType2;
        }
        if (keyType.hasRawClass(findSerializationKeyType)) {
            keyType = keyType.withStaticTyping();
        } else {
            try {
                keyType = typeFactory.constructGeneralizedType(keyType, findSerializationKeyType);
            } catch (Throwable e22) {
                throw new JsonMappingException(null, String.format("Failed to widen value type of %s with concrete-type annotation (value %s), from '%s': %s", new Object[]{javaType2, findSerializationKeyType.getName(), annotated.getName(), e22.getMessage()}), e22);
            }
        }
        return javaType2.withContentType(keyType);
    }

    public AnnotatedMethod resolveSetterConflict(MapperConfig<?> mapperConfig, AnnotatedMethod annotatedMethod, AnnotatedMethod annotatedMethod2) {
        return null;
    }

    public abstract Version version();
}
