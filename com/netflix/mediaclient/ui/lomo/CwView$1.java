// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.lomo;

import com.netflix.mediaclient.util.gfx.ImageLoader;
import com.netflix.mediaclient.servicemgr.interface_.Video;
import com.netflix.mediaclient.servicemgr.ServiceManagerUtils;
import com.netflix.mediaclient.servicemgr.interface_.VideoType;
import com.netflix.mediaclient.ui.common.PlayContextImp;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.servicemgr.interface_.trackable.Trackable;
import com.netflix.mediaclient.servicemgr.IClientLogging$AssetType;
import com.netflix.mediaclient.ui.common.PlayContextProvider;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import android.widget.ProgressBar;
import com.netflix.mediaclient.ui.common.PlayContext;
import android.widget.ImageView;
import com.netflix.mediaclient.android.widget.AdvancedImageView;
import com.netflix.mediaclient.android.widget.VideoDetailsClickListener;
import android.widget.RelativeLayout;
import com.netflix.mediaclient.servicemgr.interface_.Playable;
import com.netflix.mediaclient.ui.common.PlaybackLauncher;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import android.view.View;
import com.netflix.mediaclient.servicemgr.interface_.CWVideo;
import android.view.View$OnClickListener;

class CwView$1 implements View$OnClickListener
{
    final /* synthetic */ CwView this$0;
    final /* synthetic */ CWVideo val$video;
    
    CwView$1(final CwView this$0, final CWVideo val$video) {
        this.this$0 = this$0;
        this.val$video = val$video;
    }
    
    public void onClick(final View view) {
        PlaybackLauncher.startPlaybackAfterPIN((NetflixActivity)this.this$0.getContext(), this.val$video, this.this$0.playContext);
    }
}