// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.servicemgr.interface_.details;

import android.content.Context;
import com.netflix.mediaclient.servicemgr.interface_.BasicVideo;

public interface SeasonDetails extends BasicVideo
{
    int getNumOfEpisodes();
    
    int getSeasonNumber();
    
    String getSeasonNumberTitle(final Context p0);
    
    int getYear();
}