package com.google.firebase.analytics.connector;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.List;
import java.util.Map;

public interface AnalyticsConnector {

    @KeepForSdk
    public interface AnalyticsConnectorHandle {
        @KeepForSdk
        void unregister();
    }

    @KeepForSdk
    public interface AnalyticsConnectorListener {
        @KeepForSdk
        void onMessageTriggered(int i, @Nullable Bundle bundle);
    }

    @KeepForSdk
    void clearConditionalUserProperty(@Size(max = 24, min = 1) @NonNull String str, @Nullable String str2, @Nullable Bundle bundle);

    @WorkerThread
    @KeepForSdk
    List<a> getConditionalUserProperties(@NonNull String str, @Nullable @Size(max = 23, min = 1) String str2);

    @WorkerThread
    @KeepForSdk
    int getMaxUserProperties(@Size(min = 1) @NonNull String str);

    @WorkerThread
    @KeepForSdk
    Map<String, Object> getUserProperties(boolean z);

    @WorkerThread
    @KeepForSdk
    void logEvent(@NonNull String str, @NonNull String str2, Bundle bundle);

    @KeepForSdk
    AnalyticsConnectorHandle registerAnalyticsConnectorListener(String str, AnalyticsConnectorListener analyticsConnectorListener);

    @KeepForSdk
    void setConditionalUserProperty(@NonNull a aVar);

    @KeepForSdk
    void setUserProperty(@NonNull String str, @NonNull String str2, Object obj);
}
