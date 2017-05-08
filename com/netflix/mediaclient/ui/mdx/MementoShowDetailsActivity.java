// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.mdx;

import android.view.Menu;
import android.content.Intent;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import com.netflix.mediaclient.ui.home.HomeActivity;
import android.content.Context;
import com.netflix.mediaclient.ui.barker.BarkerUtils;
import com.netflix.mediaclient.ui.details.ShowDetailsActivity;

public class MementoShowDetailsActivity extends ShowDetailsActivity
{
    private static final String TAG = "MementoShowDetailsActivity";
    
    @Override
    protected boolean allowTransitionAnimation() {
        return false;
    }
    
    @Override
    protected void initSlidingPanel() {
        super.initSlidingPanel();
        this.slidingPanel.setPanelHeight(0);
        this.getWindow().getAttributes().width = BarkerUtils.getDetailsPageContentWidth((Context)this);
        this.getNetflixActionBar().hidelogo();
    }
    
    @Override
    public void notifyMdxMiniPlayerShown(final boolean b) {
        if (b) {
            final Intent showIntent = HomeActivity.createShowIntent(this);
            showIntent.putExtra("expandMinPlayer", true);
            this.startActivity(showIntent);
        }
    }
    
    @Override
    protected void onCreateOptionsMenu(final Menu menu, final Menu menu2) {
    }
    
    @Override
    protected void showMiniPlayer() {
    }
}
