package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.concurrent.Callable;

final class fd implements Callable<List<zzed>> {
    private final /* synthetic */ String a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;
    private final /* synthetic */ eu d;

    fd(eu euVar, String str, String str2, String str3) {
        this.d = euVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public final /* synthetic */ Object call() {
        this.d.a.D();
        return this.d.a.z().b(this.a, this.b, this.c);
    }
}
