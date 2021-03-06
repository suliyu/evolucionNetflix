// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.cast;

import com.google.android.gms.common.internal.safeparcel.zza$zza;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import android.os.Parcel;
import android.os.Parcelable$Creator;

public class zzc implements Parcelable$Creator<JoinOptions>
{
    static void zza(final JoinOptions joinOptions, final Parcel parcel, int zzaq) {
        zzaq = zzb.zzaq(parcel);
        zzb.zzc(parcel, 1, joinOptions.getVersionCode());
        zzb.zzc(parcel, 2, joinOptions.getConnectionType());
        zzb.zzI(parcel, zzaq);
    }
    
    public JoinOptions zzY(final Parcel parcel) {
        int zzg = 0;
        final int zzap = zza.zzap(parcel);
        int zzg2 = 0;
        while (parcel.dataPosition() < zzap) {
            final int zzao = zza.zzao(parcel);
            switch (zza.zzbM(zzao)) {
                default: {
                    zza.zzb(parcel, zzao);
                    continue;
                }
                case 1: {
                    zzg2 = zza.zzg(parcel, zzao);
                    continue;
                }
                case 2: {
                    zzg = zza.zzg(parcel, zzao);
                    continue;
                }
            }
        }
        if (parcel.dataPosition() != zzap) {
            throw new zza$zza("Overread allowed size end=" + zzap, parcel);
        }
        return new JoinOptions(zzg2, zzg);
    }
    
    public JoinOptions[] zzaP(final int n) {
        return new JoinOptions[n];
    }
}
