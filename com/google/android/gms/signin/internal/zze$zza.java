// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.signin.internal;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.ConnectionResult;
import android.os.Parcel;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Binder;

public abstract class zze$zza extends Binder implements zze
{
    public zze$zza() {
        this.attachInterface((IInterface)this, "com.google.android.gms.signin.internal.ISignInCallbacks");
    }
    
    public static zze zzdM(final IBinder binder) {
        if (binder == null) {
            return null;
        }
        final IInterface queryLocalInterface = binder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
        if (queryLocalInterface != null && queryLocalInterface instanceof zze) {
            return (zze)queryLocalInterface;
        }
        return new zze$zza$zza(binder);
    }
    
    public IBinder asBinder() {
        return (IBinder)this;
    }
    
    public boolean onTransact(final int n, final Parcel parcel, final Parcel parcel2, final int n2) {
        switch (n) {
            default: {
                return super.onTransact(n, parcel, parcel2, n2);
            }
            case 1598968902: {
                parcel2.writeString("com.google.android.gms.signin.internal.ISignInCallbacks");
                return true;
            }
            case 3: {
                parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                ConnectionResult connectionResult;
                if (parcel.readInt() != 0) {
                    connectionResult = (ConnectionResult)ConnectionResult.CREATOR.createFromParcel(parcel);
                }
                else {
                    connectionResult = null;
                }
                AuthAccountResult authAccountResult;
                if (parcel.readInt() != 0) {
                    authAccountResult = (AuthAccountResult)AuthAccountResult.CREATOR.createFromParcel(parcel);
                }
                else {
                    authAccountResult = null;
                }
                this.zza(connectionResult, authAccountResult);
                parcel2.writeNoException();
                return true;
            }
            case 4: {
                parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                Status status;
                if (parcel.readInt() != 0) {
                    status = (Status)Status.CREATOR.createFromParcel(parcel);
                }
                else {
                    status = null;
                }
                this.zzbd(status);
                parcel2.writeNoException();
                return true;
            }
            case 6: {
                parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                Status status2;
                if (parcel.readInt() != 0) {
                    status2 = (Status)Status.CREATOR.createFromParcel(parcel);
                }
                else {
                    status2 = null;
                }
                this.zzbe(status2);
                parcel2.writeNoException();
                return true;
            }
            case 7: {
                parcel.enforceInterface("com.google.android.gms.signin.internal.ISignInCallbacks");
                Status status3;
                if (parcel.readInt() != 0) {
                    status3 = (Status)Status.CREATOR.createFromParcel(parcel);
                }
                else {
                    status3 = null;
                }
                GoogleSignInAccount googleSignInAccount;
                if (parcel.readInt() != 0) {
                    googleSignInAccount = (GoogleSignInAccount)GoogleSignInAccount.CREATOR.createFromParcel(parcel);
                }
                else {
                    googleSignInAccount = null;
                }
                this.zza(status3, googleSignInAccount);
                parcel2.writeNoException();
                return true;
            }
        }
    }
}
