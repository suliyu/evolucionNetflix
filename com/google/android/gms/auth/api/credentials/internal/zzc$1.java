// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;

class zzc$1 extends zzd<CredentialRequestResult>
{
    final /* synthetic */ CredentialRequest zzPb;
    final /* synthetic */ zzc zzPc;
    
    zzc$1(final zzc zzPc, final GoogleApiClient googleApiClient, final CredentialRequest zzPb) {
        this.zzPc = zzPc;
        this.zzPb = zzPb;
        super(googleApiClient);
    }
    
    @Override
    protected void zza(final Context context, final ICredentialsService credentialsService) {
        credentialsService.performCredentialsRequestOperation(new zzc$1$1(this), this.zzPb);
    }
    
    protected CredentialRequestResult zzk(final Status status) {
        return zzb.zzj(status);
    }
}