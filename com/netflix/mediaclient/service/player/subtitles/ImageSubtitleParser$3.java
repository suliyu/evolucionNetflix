// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.player.subtitles;

import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.android.app.Status;
import com.netflix.mediaclient.service.player.subtitles.image.v1.SegmentIndex;
import com.netflix.mediaclient.service.resfetcher.LoggingResourceFetcherCallback;

class ImageSubtitleParser$3 extends LoggingResourceFetcherCallback
{
    final /* synthetic */ ImageSubtitleParser this$0;
    final /* synthetic */ SegmentIndex val$si;
    
    ImageSubtitleParser$3(final ImageSubtitleParser this$0, final SegmentIndex val$si) {
        this.this$0 = this$0;
        this.val$si = val$si;
    }
    
    @Override
    public void onResourceRawFetched(final String s, final byte[] array, final Status status) {
        super.onResourceRawFetched(s, array, status);
        if (status.isError()) {
            if (Log.isLoggable()) {
                Log.e("nf_subtitles", "Failed to download segment" + this.val$si);
            }
            return;
        }
        this.this$0.parseSegment(array, this.val$si);
    }
}
