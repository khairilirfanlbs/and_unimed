package com.google.android.gms.internal.ads;

import android.os.Parcel;

public abstract class akh extends afb implements zzkh {
    public akh() {
        super("com.google.android.gms.ads.internal.client.IAdListener");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                onAdClosed();
                break;
            case 2:
                onAdFailedToLoad(parcel.readInt());
                break;
            case 3:
                onAdLeftApplication();
                break;
            case 4:
                onAdLoaded();
                break;
            case 5:
                onAdOpened();
                break;
            case 6:
                onAdClicked();
                break;
            case 7:
                onAdImpression();
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
