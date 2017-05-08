// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.lomo;

import android.widget.FrameLayout$LayoutParams;
import com.netflix.mediaclient.util.l10n.LocalizationUtils;
import com.netflix.mediaclient.service.configuration.ABTestUtils.AimLowTextPlaceholderABTestUtils;
import com.netflix.mediaclient.util.StringUtils;
import com.netflix.mediaclient.servicemgr.ServiceManagerUtils;
import com.netflix.mediaclient.ui.common.PlayContextImp;
import com.netflix.mediaclient.servicemgr.interface_.trackable.Trackable;
import java.util.HashMap;
import com.netflix.mediaclient.util.log.ConsolidatedLoggingUtils;
import com.netflix.mediaclient.servicemgr.interface_.details.VideoDetails;
import android.content.res.Resources;
import android.view.ViewGroup$LayoutParams;
import com.netflix.model.leafs.originals.BillboardDateBadge;
import com.netflix.model.leafs.originals.BillboardAwardsHeadline;
import com.netflix.mediaclient.util.gfx.ImageLoader$StaticImgConfig;
import com.netflix.mediaclient.servicemgr.interface_.VideoType;
import android.net.Uri;
import com.netflix.mediaclient.ui.common.MediaPlayerWrapper$PlaybackEventsListener;
import com.netflix.model.leafs.originals.BillboardBackground;
import android.graphics.drawable.Drawable;
import com.netflix.mediaclient.service.webclient.model.leafs.ABTestConfig$Cell;
import com.netflix.mediaclient.service.configuration.PersistentConfig;
import android.widget.RelativeLayout$LayoutParams;
import com.netflix.mediaclient.util.ViewUtils;
import com.netflix.mediaclient.util.DeviceUtils;
import android.view.ViewGroup;
import com.netflix.mediaclient.ui.common.PlayContextProvider;
import com.netflix.model.leafs.originals.BillboardSummary;
import java.util.List;
import com.netflix.model.leafs.originals.BillboardCTA;
import java.util.ArrayList;
import com.netflix.mediaclient.servicemgr.ManagerCallback;
import com.netflix.mediaclient.servicemgr.IClientLogging$AssetType;
import com.netflix.mediaclient.Log;
import android.util.AttributeSet;
import com.netflix.mediaclient.util.log.UIViewLogUtils;
import android.content.Context;
import android.widget.FrameLayout;
import com.netflix.mediaclient.android.widget.TopCropImageView;
import com.netflix.mediaclient.ui.common.PlayContext;
import android.view.TextureView;
import com.netflix.mediaclient.ui.common.MediaPlayerWrapper;
import com.netflix.mediaclient.android.widget.TitleDrawable;
import android.widget.TextView;
import com.netflix.mediaclient.android.widget.VideoDetailsClickListener;
import android.widget.Button;
import com.netflix.mediaclient.android.widget.AdvancedImageView;
import com.netflix.mediaclient.servicemgr.AddToListData$StateListener;
import com.netflix.mediaclient.servicemgr.interface_.Billboard;
import android.widget.RelativeLayout;
import com.netflix.mediaclient.ui.common.PlaybackLauncher;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import android.text.TextUtils;
import java.util.Map;
import com.netflix.mediaclient.servicemgr.interface_.Video;
import com.netflix.mediaclient.servicemgr.BillboardInteractionType;
import android.view.View;
import com.netflix.mediaclient.servicemgr.ServiceManager;
import com.netflix.mediaclient.servicemgr.interface_.Playable;
import android.view.View$OnClickListener;

class BillboardView$2 implements View$OnClickListener
{
    final /* synthetic */ BillboardView this$0;
    final /* synthetic */ String val$bookmarkPosition;
    final /* synthetic */ Playable val$playable;
    final /* synthetic */ ServiceManager val$serviceMan;
    
    BillboardView$2(final BillboardView this$0, final ServiceManager val$serviceMan, final String val$bookmarkPosition, final Playable val$playable) {
        this.this$0 = this$0;
        this.val$serviceMan = val$serviceMan;
        this.val$bookmarkPosition = val$bookmarkPosition;
        this.val$playable = val$playable;
    }
    
    public void onClick(final View view) {
        if (this.val$serviceMan != null && this.val$serviceMan.isReady()) {
            this.val$serviceMan.getBrowse().logBillboardActivity(this.this$0.video, BillboardInteractionType.ACTION, this.this$0.impressionParams);
        }
        int int1;
        if (TextUtils.isEmpty((CharSequence)this.val$bookmarkPosition)) {
            int1 = -1;
        }
        else {
            int1 = Integer.parseInt(this.val$bookmarkPosition);
        }
        PlaybackLauncher.startPlaybackAfterPIN((NetflixActivity)this.this$0.getContext(), this.val$playable, this.this$0.playContext, int1);
    }
}
