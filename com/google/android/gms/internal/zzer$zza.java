// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.internal;

import android.os.Bundle;
import java.util.List;
import com.google.android.gms.dynamic.zzd$zza;
import android.os.Parcel;
import android.os.IInterface;
import android.os.IBinder;
import android.os.Binder;

public abstract class zzer$zza extends Binder implements zzer
{
    public static zzer zzJ(final IBinder binder) {
        if (binder == null) {
            return null;
        }
        final IInterface queryLocalInterface = binder.queryLocalInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
        if (queryLocalInterface != null && queryLocalInterface instanceof zzer) {
            return (zzer)queryLocalInterface;
        }
        return new zzer$zza$zza(binder);
    }
    
    public IBinder asBinder() {
        return (IBinder)this;
    }
    
    public boolean onTransact(int n, final Parcel parcel, final Parcel parcel2, final int n2) {
        final int n3 = 0;
        final int n4 = 0;
        switch (n) {
            default: {
                return super.onTransact(n, parcel, parcel2, n2);
            }
            case 1598968902: {
                parcel2.writeString("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                return true;
            }
            case 2: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                final String headline = this.getHeadline();
                parcel2.writeNoException();
                parcel2.writeString(headline);
                return true;
            }
            case 3: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                final List images = this.getImages();
                parcel2.writeNoException();
                parcel2.writeList(images);
                return true;
            }
            case 4: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                final String body = this.getBody();
                parcel2.writeNoException();
                parcel2.writeString(body);
                return true;
            }
            case 5: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                final zzcm zzdA = this.zzdA();
                parcel2.writeNoException();
                IBinder binder;
                if (zzdA != null) {
                    binder = zzdA.asBinder();
                }
                else {
                    binder = null;
                }
                parcel2.writeStrongBinder(binder);
                return true;
            }
            case 6: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                final String callToAction = this.getCallToAction();
                parcel2.writeNoException();
                parcel2.writeString(callToAction);
                return true;
            }
            case 7: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                final String advertiser = this.getAdvertiser();
                parcel2.writeNoException();
                parcel2.writeString(advertiser);
                return true;
            }
            case 8: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                this.recordImpression();
                parcel2.writeNoException();
                return true;
            }
            case 9: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                this.zzc(zzd$zza.zzbk(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            case 10: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                this.zzd(zzd$zza.zzbk(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            case 11: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                final boolean overrideImpressionRecording = this.getOverrideImpressionRecording();
                parcel2.writeNoException();
                n = n4;
                if (overrideImpressionRecording) {
                    n = 1;
                }
                parcel2.writeInt(n);
                return true;
            }
            case 12: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                final boolean overrideClickHandling = this.getOverrideClickHandling();
                parcel2.writeNoException();
                n = n3;
                if (overrideClickHandling) {
                    n = 1;
                }
                parcel2.writeInt(n);
                return true;
            }
            case 13: {
                parcel.enforceInterface("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
                final Bundle extras = this.getExtras();
                parcel2.writeNoException();
                if (extras != null) {
                    parcel2.writeInt(1);
                    extras.writeToParcel(parcel2, 1);
                    return true;
                }
                parcel2.writeInt(0);
                return true;
            }
        }
    }
}