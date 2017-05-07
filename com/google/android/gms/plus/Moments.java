// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.plus;

import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.common.api.Status;
import android.net.Uri;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.GoogleApiClient;

public interface Moments
{
    PendingResult<LoadMomentsResult> load(final GoogleApiClient p0);
    
    PendingResult<LoadMomentsResult> load(final GoogleApiClient p0, final int p1, final String p2, final Uri p3, final String p4, final String p5);
    
    PendingResult<Status> remove(final GoogleApiClient p0, final String p1);
    
    PendingResult<Status> write(final GoogleApiClient p0, final Moment p1);
    
    public interface LoadMomentsResult extends Releasable, Result
    {
        MomentBuffer getMomentBuffer();
        
        String getNextPageToken();
        
        String getUpdated();
    }
}