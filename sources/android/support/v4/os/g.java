package android.support.v4.os;

import android.os.Bundle;

class g implements Runnable {
    final int a;
    final Bundle b;
    final /* synthetic */ ResultReceiver c;

    g(ResultReceiver resultReceiver, int i, Bundle bundle) {
        this.c = resultReceiver;
        this.a = i;
        this.b = bundle;
    }

    public void run() {
        this.c.a(this.a, this.b);
    }
}
