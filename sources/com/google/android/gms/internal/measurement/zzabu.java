package com.google.android.gms.internal.measurement;

public enum zzabu {
    INT(Integer.valueOf(0)),
    LONG(Long.valueOf(0)),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf(0.0d)),
    BOOLEAN(Boolean.valueOf(false)),
    STRING(""),
    BYTE_STRING(zzyw.a),
    ENUM(null),
    MESSAGE(null);
    
    private final Object j;

    private zzabu(Object obj) {
        this.j = obj;
    }
}
