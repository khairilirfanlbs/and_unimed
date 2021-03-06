package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.location.h;

final class y extends l {
    private ResultHolder<Status> a;

    public y(ResultHolder<Status> resultHolder) {
        this.a = resultHolder;
    }

    private final void a(int i) {
        if (this.a == null) {
            Log.wtf("LocationClientImpl", "onRemoveGeofencesResult called multiple times");
            return;
        }
        this.a.setResult(h.b(h.a(i)));
        this.a = null;
    }

    public final void zza(int i, PendingIntent pendingIntent) {
        a(i);
    }

    public final void zza(int i, String[] strArr) {
        Log.wtf("LocationClientImpl", "Unexpected call to onAddGeofencesResult");
    }

    public final void zzb(int i, String[] strArr) {
        a(i);
    }
}
