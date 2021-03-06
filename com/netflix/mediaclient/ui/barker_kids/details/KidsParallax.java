// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.barker_kids.details;

import android.graphics.drawable.Drawable;
import com.netflix.mediaclient.util.api.Api16Util;
import com.netflix.mediaclient.util.ViewUtils;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.RecyclerView;
import com.netflix.mediaclient.ui.details.SeasonsSpinner;
import android.view.View;
import com.netflix.mediaclient.ui.details.DetailsPageParallaxScrollListener;

class KidsParallax extends DetailsPageParallaxScrollListener
{
    private static final int TOOLBAR_FADE_DURATION_MS = 300;
    private static final int TRACKER_VIEW_FADE_INTO_ACTIONBAR_FADEIN_DURATION_MS = 300;
    private static final int TRACKER_VIEW_FADE_INTO_ACTIONBAR_FADEOUT_DURATION_MS = 300;
    private boolean animating;
    private int finalTrackingPosition;
    private boolean isLatched;
    private View shadow;
    private View toolbarView;
    
    public KidsParallax(final SeasonsSpinner seasonsSpinner, final RecyclerView recyclerView, final View[] array, final View view, final View view2) {
        super(seasonsSpinner, recyclerView, array, view, recyclerView.getContext().getResources().getColor(2131755273), 0, 0, view2);
        this.finalTrackingPosition = recyclerView.getContext().getResources().getDimensionPixelSize(2131427757);
        this.setApplyToolBarGradientTransform(false);
    }
    
    private void fadeInActionBarColor() {
        if (!this.isLatched && !this.animating) {
            if (this.toolbarView != null) {
                final TransitionDrawable transitionDrawable = (TransitionDrawable)this.toolbarView.getBackground();
                if (transitionDrawable != null) {
                    transitionDrawable.setCrossFadeEnabled(this.animating = true);
                    this.toolbarView.postDelayed((Runnable)new KidsParallax$1(this, transitionDrawable), 300L);
                }
            }
            return;
        }
        this.toolbarView.clearAnimation();
    }
    
    private void fadeOutActionbarColor() {
        if (this.isLatched && !this.animating) {
            if (this.toolbarView != null) {
                final TransitionDrawable transitionDrawable = (TransitionDrawable)this.toolbarView.getBackground();
                if (transitionDrawable != null) {
                    transitionDrawable.setCrossFadeEnabled(true);
                    transitionDrawable.reverseTransition(300);
                    this.isLatched = false;
                }
            }
            return;
        }
        this.toolbarView.clearAnimation();
    }
    
    @Override
    protected int getLatchPosition() {
        if (this.trackingView != null && this.trackingView.getContext() != null) {
            return ViewUtils.getDefaultActionBarHeight(this.trackingView.getContext());
        }
        return 0;
    }
    
    @Override
    protected int getTrackerViewFinalXPosition() {
        return this.finalTrackingPosition;
    }
    
    @Override
    protected int getTrackerViewLatchFadeinDuration() {
        return 300;
    }
    
    @Override
    protected int getTrackerViewLatchFadeoutDuration() {
        return 300;
    }
    
    @Override
    protected void onHeaderShown() {
        if (this.trackingView == null) {
            this.fadeOutActionbarColor();
        }
        if (this.shadow != null) {
            this.shadow.setVisibility(8);
        }
    }
    
    @Override
    protected void onItemsShown() {
        if (this.trackingView == null) {
            this.fadeInActionBarColor();
        }
        if (this.shadow != null) {
            this.shadow.setVisibility(0);
        }
    }
    
    @Override
    protected void onTrackingViewLatched() {
        if (this.trackingView != null) {
            this.fadeInActionBarColor();
        }
    }
    
    @Override
    protected void onTrackingViewUnlatched() {
        if (this.trackingView != null) {
            this.fadeOutActionbarColor();
        }
    }
    
    @Override
    protected void setInitialToolbarColor() {
        this.toolbarView = this.recyclerView.getRootView().findViewById(2131820684);
        this.shadow = this.recyclerView.getRootView().findViewById(2131820699);
        final Drawable drawable = this.toolbarView.getResources().getDrawable(2130838040);
        if (drawable != null && this.toolbarView != null) {
            Api16Util.setBackgroundDrawableCompat(this.toolbarView, drawable);
        }
    }
    
    @Override
    protected void setToolbarColor() {
    }
    
    @Override
    public void setToolbarColor(final int n) {
        if (n == 0) {
            this.onHeaderShown();
        }
    }
}
