package com.google.android.gms.internal.ads;

import android.os.Bundle;

@zzadh
public final class ary {
    private static ary a = new ary();
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;

    public static ary a() {
        return a;
    }

    final void a(int i) {
        this.b += i;
    }

    final void b() {
        this.c++;
    }

    final void c() {
        this.d++;
    }

    final void d() {
        this.e++;
    }

    final void e() {
        this.f++;
    }

    public final int f() {
        return this.c;
    }

    public final int g() {
        return this.d;
    }

    public final int h() {
        return this.e;
    }

    public final int i() {
        return this.f;
    }

    public final Bundle j() {
        Bundle bundle = new Bundle();
        bundle.putInt("ipl", this.b);
        bundle.putInt("ipds", this.c);
        bundle.putInt("ipde", this.d);
        bundle.putInt("iph", this.e);
        bundle.putInt("ipm", this.f);
        return bundle;
    }
}
