package com.google.android.gms.internal.ads;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.ac;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzadh
public final class am implements Callable<gr> {
    @VisibleForTesting
    private static long a = 10;
    private final Context b;
    private final jb c;
    private final ac d;
    private final ada e;
    private final zzacm f;
    private final Object g = new Object();
    private final gs h;
    private final ana i;
    private boolean j;
    private int k;
    private List<String> l;
    private JSONObject m;
    private String n;
    @Nullable
    private String o;

    public am(Context context, ac acVar, jb jbVar, ada ada, gs gsVar, ana ana) {
        this.b = context;
        this.d = acVar;
        this.c = jbVar;
        this.h = gsVar;
        this.e = ada;
        this.i = ana;
        this.f = acVar.l();
        this.j = false;
        this.k = -2;
        this.l = null;
        this.n = null;
        this.o = null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b5 A:{Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }} */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0075 A:{Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0093 A:{Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }} */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0197  */
    /* JADX WARNING: Missing block: B:11:0x003d, code:
            if (r2.length() != 0) goto L_0x0043;
     */
    private final com.google.android.gms.internal.ads.gr a() {
        /*
        r15 = this;
        r12 = 0;
        r11 = 0;
        r2 = r15.d;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r10 = r2.j();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r15.b();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 != 0) goto L_0x00b3;
    L_0x000e:
        r3 = new org.json.JSONObject;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r15.h;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.b;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.b;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3.<init>(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = new org.json.JSONObject;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = r15.h;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = r4.b;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = r4.b;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2.<init>(r4);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = r2.length();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r4 == 0) goto L_0x003f;
    L_0x002a:
        r4 = "ads";
        r2 = r2.optJSONArray(r4);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 == 0) goto L_0x00b1;
    L_0x0032:
        r4 = 0;
        r2 = r2.optJSONObject(r4);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
    L_0x0037:
        if (r2 == 0) goto L_0x003f;
    L_0x0039:
        r2 = r2.length();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 != 0) goto L_0x0043;
    L_0x003f:
        r2 = 3;
        r15.a(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
    L_0x0043:
        r2 = r15.f;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.zzh(r3);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = a;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.get(r4, r3);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = (org.json.JSONObject) r2;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = "success";
        r4 = 0;
        r3 = r2.optBoolean(r3, r4);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r3 == 0) goto L_0x00b3;
    L_0x005c:
        r3 = "json";
        r2 = r2.getJSONObject(r3);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = "ads";
        r2 = r2.optJSONArray(r3);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = 0;
        r7 = r2.getJSONObject(r3);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
    L_0x006d:
        r2 = "enable_omid";
        r14 = r7.optBoolean(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r14 != 0) goto L_0x00b5;
    L_0x0075:
        r2 = 0;
        r2 = com.google.android.gms.internal.ads.kq.a(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r13 = r2;
    L_0x007b:
        r2 = r15.b();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 != 0) goto L_0x0083;
    L_0x0081:
        if (r7 != 0) goto L_0x00e8;
    L_0x0083:
        r4 = r11;
    L_0x0084:
        r2 = r15.b();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 != 0) goto L_0x008e;
    L_0x008a:
        if (r4 == 0) goto L_0x008e;
    L_0x008c:
        if (r7 != 0) goto L_0x01a7;
    L_0x008e:
        r3 = r11;
    L_0x008f:
        r2 = r3 instanceof com.google.android.gms.internal.ads.ans;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 == 0) goto L_0x00a3;
    L_0x0093:
        r0 = r3;
        r0 = (com.google.android.gms.internal.ads.ans) r0;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r0;
        r4 = new com.google.android.gms.internal.ads.aq;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4.<init>(r15, r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r15.f;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r5 = "/nativeAdCustomClick";
        r2.zza(r5, r4);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
    L_0x00a3:
        r2 = r15.a(r3, r14);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = r15.d;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = b(r13);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3.b(r4);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
    L_0x00b0:
        return r2;
    L_0x00b1:
        r2 = r11;
        goto L_0x0037;
    L_0x00b3:
        r7 = r11;
        goto L_0x006d;
    L_0x00b5:
        r2 = "omid_settings";
        r2 = r7.optJSONObject(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 != 0) goto L_0x00c4;
    L_0x00bd:
        r2 = 0;
        r2 = com.google.android.gms.internal.ads.kq.a(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r13 = r2;
        goto L_0x007b;
    L_0x00c4:
        r3 = "omid_html";
        r3 = r2.optString(r3);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = android.text.TextUtils.isEmpty(r3);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 == 0) goto L_0x00d7;
    L_0x00d0:
        r2 = 0;
        r2 = com.google.android.gms.internal.ads.kq.a(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r13 = r2;
        goto L_0x007b;
    L_0x00d7:
        r2 = new com.google.android.gms.internal.ads.lk;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2.<init>();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = com.google.android.gms.internal.ads.lf.a;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r5 = new com.google.android.gms.internal.ads.an;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r5.<init>(r15, r2, r3);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4.execute(r5);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r13 = r2;
        goto L_0x007b;
    L_0x00e8:
        r2 = "template_id";
        r5 = r7.getString(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r15.h;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.a;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.y;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 == 0) goto L_0x0124;
    L_0x00f6:
        r2 = r15.h;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.a;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.y;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.b;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = r2;
    L_0x00ff:
        r2 = r15.h;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.a;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.y;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 == 0) goto L_0x0126;
    L_0x0107:
        r2 = r15.h;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.a;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.y;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r2.d;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = r2;
    L_0x0110:
        r2 = "2";
        r2 = r2.equals(r5);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 == 0) goto L_0x0128;
    L_0x0118:
        r2 = new com.google.android.gms.internal.ads.bc;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r5 = r15.h;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r5 = r5.j;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2.<init>(r4, r3, r5);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = r2;
        goto L_0x0084;
    L_0x0124:
        r4 = r12;
        goto L_0x00ff;
    L_0x0126:
        r3 = r12;
        goto L_0x0110;
    L_0x0128:
        r2 = "1";
        r2 = r2.equals(r5);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 == 0) goto L_0x013c;
    L_0x0130:
        r2 = new com.google.android.gms.internal.ads.bd;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r5 = r15.h;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r5 = r5.j;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2.<init>(r4, r3, r5);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = r2;
        goto L_0x0084;
    L_0x013c:
        r2 = "3";
        r2 = r2.equals(r5);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 == 0) goto L_0x01a0;
    L_0x0144:
        r2 = "custom_template_id";
        r2 = r7.getString(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = new com.google.android.gms.internal.ads.lk;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3.<init>();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r5 = com.google.android.gms.internal.ads.ht.a;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r6 = new com.google.android.gms.internal.ads.ap;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r6.<init>(r15, r3, r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r5.post(r6);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r8 = a;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = r3.get(r8, r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r2 == 0) goto L_0x016b;
    L_0x0163:
        r2 = new com.google.android.gms.internal.ads.be;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2.<init>(r4);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = r2;
        goto L_0x0084;
    L_0x016b:
        r3 = "No handler for custom template: ";
        r2 = "custom_template_id";
        r2 = r7.getString(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = java.lang.String.valueOf(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = r2.length();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r4 == 0) goto L_0x0187;
    L_0x017d:
        r2 = r3.concat(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
    L_0x0181:
        com.google.android.gms.internal.ads.kk.c(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
    L_0x0184:
        r4 = r11;
        goto L_0x0084;
    L_0x0187:
        r2 = new java.lang.String;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2.<init>(r3);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        goto L_0x0181;
    L_0x018d:
        r2 = move-exception;
    L_0x018e:
        r3 = "Malformed native JSON response.";
        com.google.android.gms.internal.ads.kk.c(r3, r2);
    L_0x0193:
        r2 = r15.j;
        if (r2 != 0) goto L_0x019a;
    L_0x0197:
        r15.a(r12);
    L_0x019a:
        r2 = r15.a(r11, r12);
        goto L_0x00b0;
    L_0x01a0:
        r2 = 0;
        r15.a(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        goto L_0x0184;
    L_0x01a5:
        r2 = move-exception;
        goto L_0x018e;
    L_0x01a7:
        r2 = "tracking_urls_and_actions";
        r5 = r7.getJSONObject(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = "impression_tracking_urls";
        r6 = r5.optJSONArray(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r6 != 0) goto L_0x01f0;
    L_0x01b5:
        r2 = r11;
    L_0x01b6:
        if (r2 != 0) goto L_0x0206;
    L_0x01b8:
        r2 = r11;
    L_0x01b9:
        r15.l = r2;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = "active_view";
        r2 = r5.optJSONObject(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r15.m = r2;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = "debug_signals";
        r2 = r7.optString(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r15.n = r2;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = "omid_settings";
        r2 = r7.optString(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r15.o = r2;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r8 = r4.zza(r15, r7);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = new com.google.android.gms.internal.ads.anz;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = r15.b;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r4 = r15.d;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r5 = r15.f;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r6 = r15.e;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r9 = r15.h;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r9 = r9.a;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r9 = r9.k;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r8.zzb(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = r8;
        goto L_0x008f;
    L_0x01f0:
        r2 = r6.length();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2 = new java.lang.String[r2];	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = r12;
    L_0x01f7:
        r8 = r6.length();	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        if (r3 >= r8) goto L_0x01b6;
    L_0x01fd:
        r8 = r6.getString(r3);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r2[r3] = r8;	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        r3 = r3 + 1;
        goto L_0x01f7;
    L_0x0206:
        r2 = java.util.Arrays.asList(r2);	 Catch:{ CancellationException -> 0x018d, ExecutionException -> 0x01a5, InterruptedException -> 0x021a, JSONException -> 0x021d, TimeoutException -> 0x020b, Exception -> 0x0212 }
        goto L_0x01b9;
    L_0x020b:
        r2 = move-exception;
        r3 = "Timeout when loading native ad.";
        com.google.android.gms.internal.ads.kk.c(r3, r2);
        goto L_0x0193;
    L_0x0212:
        r2 = move-exception;
        r3 = "Error occured while doing native ads initialization.";
        com.google.android.gms.internal.ads.kk.c(r3, r2);
        goto L_0x0193;
    L_0x021a:
        r2 = move-exception;
        goto L_0x018e;
    L_0x021d:
        r2 = move-exception;
        goto L_0x018e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.am.a():com.google.android.gms.internal.ads.gr");
    }

    private final gr a(zzpb zzpb, boolean z) {
        int i;
        synchronized (this.g) {
            i = this.k;
            if (zzpb == null && this.k == -2) {
                i = 0;
            }
        }
        return new gr(this.h.a.c, null, this.h.b.c, i, this.h.b.e, this.l, this.h.b.k, this.h.b.j, this.h.a.i, false, null, null, null, null, null, 0, this.h.d, this.h.b.f, this.h.f, this.h.g, this.h.b.n, this.m, i != -2 ? null : zzpb, null, null, null, this.h.b.D, this.h.b.E, null, this.h.b.H, this.n, this.h.i, this.h.b.O, this.h.j, z, this.h.b.R, this.h.b.S, this.o);
    }

    private final zzanz<ann> a(JSONObject jSONObject, boolean z, boolean z2) {
        String string = z ? jSONObject.getString("url") : jSONObject.optString("url");
        double optDouble = jSONObject.optDouble("scale", 1.0d);
        boolean optBoolean = jSONObject.optBoolean("is_transparent", true);
        if (!TextUtils.isEmpty(string)) {
            return z2 ? kq.a(new ann(null, Uri.parse(string), optDouble)) : this.c.a(string, new as(this, z, optDouble, optBoolean, string));
        } else {
            a(0, z);
            return kq.a(null);
        }
    }

    static zzaqw a(zzanz<zzaqw> zzanz) {
        Throwable e;
        try {
            return (zzaqw) zzanz.get((long) ((Integer) akc.f().a(amn.cc)).intValue(), TimeUnit.SECONDS);
        } catch (Throwable e2) {
            kk.c("InterruptedException occurred while waiting for video to load", e2);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e3) {
            e2 = e3;
            kk.c("Exception occurred while waiting for video to load", e2);
        } catch (TimeoutException e4) {
            e2 = e4;
            kk.c("Exception occurred while waiting for video to load", e2);
        } catch (CancellationException e5) {
            e2 = e5;
            kk.c("Exception occurred while waiting for video to load", e2);
        }
        return null;
    }

    private final void a(int i) {
        synchronized (this.g) {
            this.j = true;
            this.k = i;
        }
    }

    private final void a(zzqs zzqs, String str) {
        try {
            zzrc zzr = this.d.zzr(zzqs.getCustomTemplateId());
            if (zzr != null) {
                zzr.zzb(zzqs, str);
            }
        } catch (Throwable e) {
            kk.c(new StringBuilder(String.valueOf(str).length() + 40).append("Failed to call onCustomClick for asset ").append(str).append(".").toString(), e);
        }
    }

    private static zzaqw b(zzanz<zzaqw> zzanz) {
        Throwable e;
        try {
            return (zzaqw) zzanz.get((long) ((Integer) akc.f().a(amn.cd)).intValue(), TimeUnit.MILLISECONDS);
        } catch (Throwable e2) {
            kk.c("", e2);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e3) {
            e2 = e3;
            kk.c("", e2);
        } catch (TimeoutException e4) {
            e2 = e4;
            kk.c("", e2);
        } catch (CancellationException e5) {
            e2 = e5;
            kk.c("", e2);
        }
        return null;
    }

    private static Integer b(JSONObject jSONObject, String str) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            return Integer.valueOf(Color.rgb(jSONObject2.getInt("r"), jSONObject2.getInt("g"), jSONObject2.getInt("b")));
        } catch (JSONException e) {
            return null;
        }
    }

    private static <V> List<V> b(List<zzanz<V>> list) {
        List<V> arrayList = new ArrayList();
        for (zzanz zzanz : list) {
            Object obj = zzanz.get();
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final boolean b() {
        boolean z;
        synchronized (this.g) {
            z = this.j;
        }
        return z;
    }

    public final zzanz<anj> a(JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONObject("attribution");
        if (optJSONObject == null) {
            return kq.a(null);
        }
        String optString = optJSONObject.optString("text");
        int optInt = optJSONObject.optInt("text_size", -1);
        Integer b = b(optJSONObject, "text_color");
        Integer b2 = b(optJSONObject, "bg_color");
        int optInt2 = optJSONObject.optInt("animation_ms", 1000);
        int optInt3 = optJSONObject.optInt("presentation_ms", 4000);
        int i = (this.h.a.y == null || this.h.a.y.a < 2) ? 1 : this.h.a.y.e;
        boolean optBoolean = optJSONObject.optBoolean("allow_pub_rendering");
        List arrayList = new ArrayList();
        List a;
        if (optJSONObject.optJSONArray("images") != null) {
            a = a(optJSONObject, "images", false, false, true);
        } else {
            arrayList.add(a(optJSONObject, "image", false, false));
            a = arrayList;
        }
        zzanz lkVar = new lk();
        int size = a.size();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (zzanz zza : a) {
            zza.zza(new at(atomicInteger, size, lkVar, a), hr.a);
        }
        return kq.a(lkVar, new ar(this, optString, b2, b, optInt, optInt3, optInt2, i, optBoolean), hr.a);
    }

    public final zzanz<zzaqw> a(JSONObject jSONObject, String str) {
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        if (optJSONObject == null) {
            return kq.a(null);
        }
        if (TextUtils.isEmpty(optJSONObject.optString("vast_xml"))) {
            kk.e("Required field 'vast_xml' is missing");
            return kq.a(null);
        }
        au auVar = new au(this.b, this.e, this.h, this.i, this.d);
        lk lkVar = new lk();
        lf.a.execute(new av(auVar, optJSONObject, lkVar));
        return lkVar;
    }

    public final zzanz<ann> a(JSONObject jSONObject, String str, boolean z, boolean z2) {
        JSONObject jSONObject2 = z ? jSONObject.getJSONObject(str) : jSONObject.optJSONObject(str);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return a(jSONObject2, z, z2);
    }

    public final List<zzanz<ann>> a(JSONObject jSONObject, String str, boolean z, boolean z2, boolean z3) {
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        List<zzanz<ann>> arrayList = new ArrayList();
        if (optJSONArray == null || optJSONArray.length() == 0) {
            a(0, false);
            return arrayList;
        }
        int length = z3 ? optJSONArray.length() : 1;
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
            if (jSONObject2 == null) {
                jSONObject2 = new JSONObject();
            }
            arrayList.add(a(jSONObject2, false, z2));
        }
        return arrayList;
    }

    public final Future<ann> a(JSONObject jSONObject, String str, boolean z) {
        JSONObject jSONObject2 = jSONObject.getJSONObject(str);
        boolean optBoolean = jSONObject2.optBoolean("require", true);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return a(jSONObject2, optBoolean, z);
    }

    public final void a(int i, boolean z) {
        if (z) {
            a(i);
        }
    }
}
