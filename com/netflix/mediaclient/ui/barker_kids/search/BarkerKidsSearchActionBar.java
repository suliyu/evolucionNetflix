// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.barker_kids.search;

import com.netflix.mediaclient.android.activity.NetflixActivity;
import com.netflix.mediaclient.android.widget.SearchActionBar;

public class BarkerKidsSearchActionBar extends SearchActionBar
{
    public BarkerKidsSearchActionBar(final NetflixActivity netflixActivity) {
        super(netflixActivity);
    }
    
    @Override
    protected int getActiveSearchIconResId() {
        return 2130837971;
    }
    
    @Override
    protected int getLayoutId() {
        return 2130903069;
    }
    
    @Override
    protected Integer getSearchCloseButtonTint() {
        return this.activity.getResources().getColor(2131755133);
    }
    
    @Override
    protected int getSearchViewBgResId() {
        return 2130838151;
    }
    
    @Override
    protected int getSearchViewRightBgResId() {
        return 2130838153;
    }
    
    @Override
    protected int getSearchViewTextColorResId() {
        return 2131755038;
    }
    
    @Override
    protected Integer getSearchVoiceButtonTint() {
        return this.activity.getResources().getColor(2131755133);
    }
}
