// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.logging.perf;

import com.netflix.mediaclient.ui.home.HomeActivity;
import android.view.View;
import com.android.volley.Request$Priority;
import android.widget.ImageView;

public class InteractiveTracker$TTRTracker extends InteractiveTracker
{
    public static final String ID;
    
    static {
        ID = Sessions.TTR.toString();
    }
    
    @Override
    public String getId() {
        return InteractiveTracker$TTRTracker.ID;
    }
    
    public boolean shouldTrack(final ImageView imageView, final Request$Priority request$Priority) {
        return super.shouldTrack((View)imageView, request$Priority) && imageView.getContext() instanceof HomeActivity && request$Priority == Request$Priority.NORMAL;
    }
}
