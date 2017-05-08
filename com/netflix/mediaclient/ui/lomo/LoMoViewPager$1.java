// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.lomo;

import android.view.ViewParent;
import android.view.ViewGroup$LayoutParams;
import android.widget.LinearLayout$LayoutParams;
import com.netflix.mediaclient.ui.barker.BarkerUtils;
import com.netflix.mediaclient.servicemgr.interface_.LoMoType;
import android.view.MotionEvent;
import com.netflix.mediaclient.service.webclient.model.leafs.KubrickLoMoHeroDuplicate;
import com.netflix.mediaclient.servicemgr.interface_.BasicLoMo;
import android.app.Activity;
import com.netflix.mediaclient.util.DeviceUtils;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import com.netflix.mediaclient.ui.experience.BrowseExperience;
import android.support.v4.view.PagerAdapter;
import com.netflix.mediaclient.util.ThreadUtils;
import android.view.View;
import com.netflix.mediaclient.android.widget.ObjectRecycler$ViewRecycler;
import com.netflix.mediaclient.servicemgr.ServiceManager;
import com.netflix.mediaclient.ui.lolomo.LoLoMoFrag;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import com.viewpagerindicator.CirclePageIndicator;
import android.os.Handler;
import android.annotation.SuppressLint;
import com.netflix.mediaclient.ui.lolomo.BaseLoLoMoAdapter$LoMoRowContent;
import com.netflix.mediaclient.android.fragment.CustomViewPager;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.util.l10n.LocalizationUtils;
import android.content.Context;
import com.netflix.mediaclient.util.AndroidUtils;

class LoMoViewPager$1 implements Runnable
{
    final /* synthetic */ LoMoViewPager this$0;
    
    LoMoViewPager$1(final LoMoViewPager this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public void run() {
        if (AndroidUtils.isActivityFinishedOrDestroyed((Context)this.this$0.getActivity())) {
            return;
        }
        int n;
        if (LocalizationUtils.isCurrentLocaleRTL()) {
            if ((n = this.this$0.getCurrentItem() - 1) < 0) {
                n = this.this$0.adapter.getCount() - 1;
            }
        }
        else if ((n = this.this$0.getCurrentItem() + 1) >= this.this$0.adapter.getCount()) {
            n = 0;
        }
        if (Log.isLoggable()) {
            Log.v("LoMoViewPager", "Auto-rotating to next view, id: " + n);
        }
        this.this$0.setCurrentItem(n, true, true);
        this.this$0.handler.postDelayed((Runnable)this, LoMoViewPager.ROTATE_TO_NEXT_VIEW_DELAY_MS);
    }
}
