package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public interface RemovalListener<K, V> {
    void onRemoval(ar<K, V> arVar);
}
