// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.player;

import android.os.Bundle;
import com.netflix.mediaclient.Log;
import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;

class PlayParamsReceiver extends BroadcastReceiver
{
    public static final String BUFFERSIZE_INTENT = "com.netflix.mediaclient.intent.action.TWEAK_BUFFERSIZE";
    private static final String TAG = "Bandwidth_Rcvr";
    public static final String TWEAKBW_INTENT = "com.netflix.mediaclient.intent.action.TWEAKBW";
    PlayerAgent mPlayController;
    
    PlayParamsReceiver(final PlayerAgent mPlayController) {
        this.mPlayController = mPlayController;
    }
    
    private Runnable getHandlerForBufferSize(final Context context, final Intent intent) {
        return new Runnable() {
            @Override
            public void run() {
                PlayParamsReceiver.this.handleBufferSize(context, intent);
            }
        };
    }
    
    private Runnable getHandlerForTweakBandwith(final Context context, final Intent intent) {
        return new Runnable() {
            @Override
            public void run() {
                PlayParamsReceiver.this.handleTweakBandwith(context, intent);
            }
        };
    }
    
    private void handleBufferSize(final Context context, final Intent intent) {
        try {
            final Bundle extras = intent.getExtras();
            if (extras != null) {
                int int1;
                if ((int1 = extras.getInt("bufferSizeInMS")) <= 0) {
                    int1 = 120000;
                }
                this.mPlayController.setVideoStreamingBufferSize(int1);
                Log.d("Bandwidth_Rcvr", "Video Buffer Size in MS: " + int1);
            }
        }
        catch (Exception ex) {
            Log.e("Bandwidth_Rcvr", "Unintented Exception thrown ", ex);
        }
    }
    
    private void handleTweakBandwith(final Context context, final Intent intent) {
        while (true) {
            while (true) {
                Label_0112: {
                    try {
                        final Bundle extras = intent.getExtras();
                        if (extras != null) {
                            int int1;
                            if ((int1 = extras.getInt("maxbw")) <= 0) {
                                int1 = 4800;
                            }
                            final int int2 = extras.getInt("minbw");
                            if (int2 < 0) {
                                break Label_0112;
                            }
                            final int n;
                            if ((n = int2) > int1) {
                                break Label_0112;
                            }
                            this.mPlayController.setVideoBitrateRange(n, int1);
                            Log.d("Bandwidth_Rcvr", "MaxBw set: " + int1 + "MinBw set: " + n);
                        }
                        return;
                    }
                    catch (Exception ex) {
                        Log.e("Bandwidth_Rcvr", "Unintented Exception thrown ", ex);
                        return;
                    }
                }
                int n = 0;
                continue;
            }
        }
    }
    
    public void onReceive(final Context context, final Intent intent) {
        Log.d("Bandwidth_Rcvr", "Received an action: " + intent.getAction());
        Runnable runnable = null;
        if ("com.netflix.mediaclient.intent.action.TWEAKBW".equals(intent.getAction())) {
            runnable = this.getHandlerForTweakBandwith(context, intent);
        }
        else if ("com.netflix.mediaclient.intent.action.TWEAK_BUFFERSIZE".equals(intent.getAction())) {
            runnable = this.getHandlerForBufferSize(context, intent);
        }
        this.mPlayController.excuteOnPlayExecutor(runnable);
    }
}
