// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.player;

import com.netflix.mediaclient.servicemgr.IPlayer$PlayerListener;

class PlayerListenerManager$PlayerListenerOnCompletionHandler implements PlayerListenerManager$PlayerListenerHandler
{
    final /* synthetic */ PlayerListenerManager this$0;
    
    private PlayerListenerManager$PlayerListenerOnCompletionHandler(final PlayerListenerManager this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public void handle(final IPlayer$PlayerListener player$PlayerListener, final Object... array) {
        if (player$PlayerListener.isListening()) {
            player$PlayerListener.onCompletion();
        }
    }
}
