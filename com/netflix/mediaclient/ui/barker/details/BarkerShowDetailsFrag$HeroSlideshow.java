// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.barker.details;

import com.netflix.mediaclient.util.StringUtils;
import com.netflix.mediaclient.servicemgr.interface_.details.SeasonDetails;
import com.netflix.mediaclient.android.app.CommonStatus;
import com.netflix.mediaclient.servicemgr.interface_.Video;
import android.support.v7.widget.GridLayoutManager$SpanSizeLookup;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView$ItemDecoration;
import android.annotation.SuppressLint;
import com.netflix.mediaclient.ui.experience.BrowseExperience;
import com.netflix.mediaclient.ui.kids.KidsUtils;
import android.view.View$OnClickListener;
import android.support.v7.widget.RecyclerView$OnScrollListener;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView$Adapter;
import android.support.v7.widget.RecyclerView$LayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.FrameLayout$LayoutParams;
import com.netflix.mediaclient.android.widget.NetflixActionBar;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.servicemgr.interface_.details.Similarable;
import com.netflix.mediaclient.ui.details.DetailsActivity;
import android.widget.AdapterView$OnItemSelectedListener;
import com.netflix.mediaclient.util.DeviceUtils;
import com.netflix.mediaclient.ui.barker.BarkerUtils;
import android.graphics.drawable.Drawable;
import com.netflix.mediaclient.util.api.Api16Util;
import android.os.Parcelable;
import com.netflix.mediaclient.android.fragment.NetflixDialogFrag;
import com.netflix.mediaclient.servicemgr.interface_.details.VideoDetails;
import com.netflix.mediaclient.servicemgr.interface_.details.ShowDetails;
import com.netflix.mediaclient.android.app.Status;
import android.view.ViewGroup;
import com.netflix.mediaclient.ui.details.VideoDetailsViewGroup;
import com.netflix.mediaclient.android.widget.LoadingAndErrorWrapper;
import com.netflix.mediaclient.android.widget.RecyclerViewHeaderAdapter;
import com.netflix.mediaclient.android.widget.RecyclerViewHeaderAdapter$IViewCreator;
import android.support.v7.widget.RecyclerView;
import java.util.Stack;
import com.netflix.mediaclient.ui.details.DetailsPageParallaxScrollListener;
import com.netflix.mediaclient.util.ItemDecorationBarkerGrid;
import android.view.View;
import com.netflix.mediaclient.servicemgr.interface_.details.EpisodeDetails;
import com.netflix.mediaclient.ui.details.CopyrightView;
import com.netflix.mediaclient.ui.mdx.CastPlayerHelper$CastPlayerDialog;
import com.netflix.mediaclient.ui.details.ServiceManagerProvider;
import com.netflix.mediaclient.ui.details.IHandleBackPress;
import com.netflix.mediaclient.servicemgr.ManagerStatusListener;
import com.netflix.mediaclient.android.widget.ErrorWrapper$Callback;
import com.netflix.mediaclient.ui.details.EpisodesFrag;
import android.text.TextUtils;
import java.util.Collection;
import com.netflix.mediaclient.servicemgr.interface_.details.KubrickShowDetails;
import com.netflix.mediaclient.util.gfx.ImageLoader$ImageLoaderListener;
import com.netflix.mediaclient.servicemgr.IClientLogging$AssetType;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import com.netflix.mediaclient.android.widget.AdvancedImageView;
import android.os.Handler;
import com.netflix.mediaclient.android.activity.NetflixActivity;

public class BarkerShowDetailsFrag$HeroSlideshow
{
    private static final int FADE_DURATION_2_SECS = 2000;
    private static final int INTERVAL_4_SECS = 4000;
    private static final int START_DELAY_3_SECS = 3000;
    private final NetflixActivity context;
    private int currentSlideshowIndex;
    private final Handler handler;
    private AdvancedImageView horzDispImg;
    private AdvancedImageView horzDispImg2;
    private boolean isHorzDispImgShowing;
    private final List<String> slideShowImageUrls;
    private final Runnable slideshowTask;
    private boolean stopRequested;
    final /* synthetic */ BarkerShowDetailsFrag this$0;
    
    private BarkerShowDetailsFrag$HeroSlideshow(final BarkerShowDetailsFrag this$0, final NetflixActivity context) {
        this.this$0 = this$0;
        this.handler = new Handler();
        this.slideShowImageUrls = new ArrayList<String>();
        this.isHorzDispImgShowing = true;
        this.slideshowTask = new BarkerShowDetailsFrag$HeroSlideshow$1(this);
        this.context = context;
    }
    
    private void animateSlideshow() {
        if (this.horzDispImg != null && this.context != null && this.this$0.getActivity() != null && this.slideShowImageUrls != null && this.currentSlideshowIndex < this.slideShowImageUrls.size()) {
            NetflixActivity.getImageLoader((Context)this.context).getImg((String)this.slideShowImageUrls.get(this.currentSlideshowIndex), IClientLogging$AssetType.boxArt, this.horzDispImg.getMeasuredWidth(), this.horzDispImg.getMeasuredHeight(), (ImageLoader$ImageLoaderListener)new BarkerShowDetailsFrag$HeroSlideshow$2(this));
            ++this.currentSlideshowIndex;
            if (this.currentSlideshowIndex >= this.slideShowImageUrls.size()) {
                this.currentSlideshowIndex = 0;
            }
        }
    }
    
    public void start() {
        if (this.this$0.showDetailsOnLaunch && this.this$0.showDetails instanceof KubrickShowDetails && !BarkerHelper.isInTest((Context)this.this$0.getActivity())) {
            this.horzDispImg = this.this$0.detailsViewGroup.getHeroImage();
            this.horzDispImg2 = ((BarkerVideoDetailsViewGroup)this.this$0.detailsViewGroup).getHeroImage2();
            this.stopRequested = false;
            this.slideShowImageUrls.clear();
            final KubrickShowDetails kubrickShowDetails = (KubrickShowDetails)this.this$0.showDetails;
            final List<String> heroImages = kubrickShowDetails.getHeroImages();
            if (heroImages != null && heroImages.size() > 0) {
                this.slideShowImageUrls.addAll(heroImages);
            }
            final String kubrickStoryImgUrl = kubrickShowDetails.getKubrickStoryImgUrl();
            if (!TextUtils.isEmpty((CharSequence)kubrickStoryImgUrl)) {
                this.slideShowImageUrls.add(kubrickStoryImgUrl);
            }
            if (this.slideShowImageUrls.size() > 0) {
                this.currentSlideshowIndex = 0;
                this.handler.removeCallbacks(this.slideshowTask);
                this.handler.postDelayed(this.slideshowTask, 3000L);
            }
        }
    }
    
    public void stop(final boolean b) {
        if (!this.stopRequested) {
            this.stopRequested = true;
            this.handler.removeCallbacks(this.slideshowTask);
            if (b) {
                this.slideShowImageUrls.clear();
            }
        }
    }
}
