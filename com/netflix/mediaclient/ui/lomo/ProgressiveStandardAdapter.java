// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.lomo;

import com.netflix.mediaclient.servicemgr.ManagerCallback;
import com.netflix.mediaclient.servicemgr.interface_.LoMoType;
import com.netflix.mediaclient.ui.experience.BrowseExperience;
import com.netflix.mediaclient.servicemgr.FetchVideosHandler$FetchCallback;
import com.netflix.mediaclient.servicemgr.FetchVideosHandler;
import com.netflix.mediaclient.servicemgr.interface_.LoMo;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.android.widget.ObjectRecycler$ViewRecycler;
import com.netflix.mediaclient.servicemgr.ServiceManager;
import com.netflix.mediaclient.servicemgr.interface_.Video;

public class ProgressiveStandardAdapter<V extends Video> extends BaseProgressiveRowAdapter<V>
{
    public ProgressiveStandardAdapter(final BasePaginatedAdapter<V> basePaginatedAdapter, final ServiceManager serviceManager, final RowAdapterCallbacks rowAdapterCallbacks, final ObjectRecycler$ViewRecycler objectRecycler$ViewRecycler) {
        super(basePaginatedAdapter, serviceManager, rowAdapterCallbacks, objectRecycler$ViewRecycler);
    }
    
    @Override
    protected void fetchMoreData(final int n, final int n2) {
        boolean b = true;
        if (this.getLoMo() == null) {
            Log.w("BaseProgressiveRowAdapter", "standard lomo pager - no lomo data to use for fetch request");
            return;
        }
        final LoMo loMo = (LoMo)this.getLoMo();
        if (Log.isLoggable()) {
            Log.v("BaseProgressiveRowAdapter", String.format("fetching videos for: Title: %s, Type: %s, Total Vids: %d, Id: %s, start: %d, end: %d", loMo.getTitle(), loMo.getType(), loMo.getNumVideos(), loMo.getId(), n, n2));
        }
        final FetchVideosHandler fetchVideosHandler = new FetchVideosHandler("BaseProgressiveRowAdapter", this, loMo.getTitle(), n, n2);
        if (!BrowseExperience.isKubrickKids() || loMo.getType() != LoMoType.POPULAR_TITLES) {
            b = false;
        }
        if (b) {
            Log.v("BaseProgressiveRowAdapter", "For Kubrick Kids POPULAR_TITLES row, doing fetchVideos via lomo type");
        }
        this.getManager().getBrowse().fetchVideos(loMo, n, n2, false, BrowseExperience.shouldLoadKubrickLeaves(), b, fetchVideosHandler);
    }
}