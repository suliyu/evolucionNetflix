// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.cast.internal;

import android.os.Parcelable;
import com.google.android.gms.common.internal.BinderWrapper;
import android.text.TextUtils;
import com.google.android.gms.cast.JoinOptions;
import android.os.IInterface;
import android.os.IBinder;
import com.google.android.gms.common.ConnectionResult;
import android.os.RemoteException;
import java.util.HashMap;
import com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks;
import com.google.android.gms.common.internal.zzf;
import android.os.Looper;
import android.content.Context;
import com.google.android.gms.cast.Cast$ApplicationConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzlb$zzb;
import android.os.Bundle;
import java.util.concurrent.atomic.AtomicLong;
import com.google.android.gms.cast.Cast$MessageReceivedCallback;
import java.util.Map;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast$Listener;
import com.google.android.gms.common.internal.zzj;

class zze$zzb$1 implements Runnable
{
    final /* synthetic */ zze zzZd;
    final /* synthetic */ int zzZe;
    final /* synthetic */ zze$zzb zzZf;
    
    zze$zzb$1(final zze$zzb zzZf, final zze zzZd, final int zzZe) {
        this.zzZf = zzZf;
        this.zzZd = zzZd;
        this.zzZe = zzZe;
    }
    
    @Override
    public void run() {
        if (this.zzZd.zzUZ != null) {
            this.zzZd.zzUZ.onApplicationDisconnected(this.zzZe);
        }
    }
}
