// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v4.media.session;

import android.media.Rating;
import android.media.RemoteControlClient$OnMetadataUpdateListener;

class MediaSessionCompatApi19$OnMetadataUpdateListener<T extends MediaSessionCompatApi19$Callback> implements RemoteControlClient$OnMetadataUpdateListener
{
    protected final T mCallback;
    
    public MediaSessionCompatApi19$OnMetadataUpdateListener(final T mCallback) {
        this.mCallback = mCallback;
    }
    
    public void onMetadataUpdate(final int n, final Object o) {
        if (n == 268435457 && o instanceof Rating) {
            this.mCallback.onSetRating(o);
        }
    }
}
