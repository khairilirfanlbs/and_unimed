package com.appnext.core;

import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;

public class j extends q {
    String bj;
    String bk;
    String bl;
    String bm;
    String bn;
    ResultReceiver bo;
    String dq;
    String dr;
    String guid;

    public j(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, ResultReceiver resultReceiver) {
        this.bj = str;
        this.bk = str2;
        this.guid = str3;
        this.bl = str4;
        this.bm = str5;
        this.bo = resultReceiver;
        this.bn = str6;
        this.dq = str7;
        this.dr = str8;
    }

    protected void a(Intent intent) {
        intent.putExtra("added_info", AdsService.ADD_PACK);
        intent.putExtra("package", this.bj);
        intent.putExtra("link", this.bk);
        intent.putExtra("guid", this.guid);
        intent.putExtra("bannerid", this.bl);
        intent.putExtra("placementid", this.bm);
        intent.putExtra("receiver", this.bo);
        intent.putExtra("vid", this.bn);
        intent.putExtra("tid", this.dq);
        intent.putExtra("auid", this.dr);
    }

    public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, ResultReceiver resultReceiver) {
        this.bj = str;
        this.bk = str2;
        this.guid = str3;
        this.bl = str4;
        this.bm = str5;
        this.bo = resultReceiver;
        this.bn = str6;
        this.dq = str7;
        this.dr = str8;
    }

    public void d(Context context) {
        super.d(context);
        this.bo = null;
    }
}
