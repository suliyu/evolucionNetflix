// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.player;

import com.netflix.mediaclient.Log;

class PlayerFragment$17 implements Runnable
{
    final /* synthetic */ PlayerFragment this$0;
    
    PlayerFragment$17(final PlayerFragment this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public void run() {
        Log.d("PlayerFragment", "Playback canceled when not longer on WiFi");
        this.this$0.cleanupAndExit();
    }
}
