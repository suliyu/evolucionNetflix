// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.player;

import android.view.MenuItem$OnMenuItemClickListener;
import android.view.Menu;
import android.view.ViewGroup$LayoutParams;
import android.widget.RelativeLayout$LayoutParams;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.util.ViewUtils;
import android.view.MenuItem;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View$OnTouchListener;

class Social$2 implements View$OnTouchListener
{
    final /* synthetic */ Social this$0;
    
    Social$2(final Social this$0) {
        this.this$0 = this$0;
    }
    
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        this.this$0.doNotShare();
        return true;
    }
}