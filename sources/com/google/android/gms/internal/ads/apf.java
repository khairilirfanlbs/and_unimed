package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.a;
import java.util.List;

public abstract class apf extends afb implements zzqs {
    public apf() {
        super("com.google.android.gms.ads.internal.formats.client.INativeCustomTemplateAd");
    }

    public static zzqs a(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeCustomTemplateAd");
        return queryLocalInterface instanceof zzqs ? (zzqs) queryLocalInterface : new apg(iBinder);
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        String zzao;
        IInterface zzap;
        switch (i) {
            case 1:
                zzao = zzao(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(zzao);
                break;
            case 2:
                zzap = zzap(parcel.readString());
                parcel2.writeNoException();
                afc.a(parcel2, zzap);
                break;
            case 3:
                List availableAssetNames = getAvailableAssetNames();
                parcel2.writeNoException();
                parcel2.writeStringList(availableAssetNames);
                break;
            case 4:
                zzao = getCustomTemplateId();
                parcel2.writeNoException();
                parcel2.writeString(zzao);
                break;
            case 5:
                performClick(parcel.readString());
                parcel2.writeNoException();
                break;
            case 6:
                recordImpression();
                parcel2.writeNoException();
                break;
            case 7:
                zzap = getVideoController();
                parcel2.writeNoException();
                afc.a(parcel2, zzap);
                break;
            case 8:
                destroy();
                parcel2.writeNoException();
                break;
            case 9:
                zzap = zzkh();
                parcel2.writeNoException();
                afc.a(parcel2, zzap);
                break;
            case 10:
                boolean zzh = zzh(a.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                afc.a(parcel2, zzh);
                break;
            case 11:
                zzap = zzka();
                parcel2.writeNoException();
                afc.a(parcel2, zzap);
                break;
            default:
                return false;
        }
        return true;
    }
}
