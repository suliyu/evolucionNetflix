// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v7.media;

import android.os.Message;
import android.os.Handler;

final class MediaRouteProvider$ProviderHandler extends Handler
{
    final /* synthetic */ MediaRouteProvider this$0;
    
    MediaRouteProvider$ProviderHandler(final MediaRouteProvider this$0) {
        this.this$0 = this$0;
    }
    
    public void handleMessage(final Message message) {
        switch (message.what) {
            default: {}
            case 1: {
                this.this$0.deliverDescriptorChanged();
            }
            case 2: {
                this.this$0.deliverDiscoveryRequestChanged();
            }
        }
    }
}
