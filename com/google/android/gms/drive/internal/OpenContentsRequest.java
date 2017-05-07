// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.drive.internal;

import android.os.Parcel;
import com.google.android.gms.drive.DriveId;
import android.os.Parcelable$Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class OpenContentsRequest implements SafeParcelable
{
    public static final Parcelable$Creator<OpenContentsRequest> CREATOR;
    final int kg;
    final int qF;
    final DriveId rr;
    
    static {
        CREATOR = (Parcelable$Creator)new w();
    }
    
    OpenContentsRequest(final int kg, final DriveId rr, final int qf) {
        this.kg = kg;
        this.rr = rr;
        this.qF = qf;
    }
    
    public OpenContentsRequest(final DriveId driveId, final int n) {
        this(1, driveId, n);
    }
    
    public int describeContents() {
        return 0;
    }
    
    public void writeToParcel(final Parcel parcel, final int n) {
        w.a(this, parcel, n);
    }
}