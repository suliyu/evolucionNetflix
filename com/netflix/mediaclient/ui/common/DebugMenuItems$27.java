// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.common;

import android.view.SubMenu;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import com.netflix.mediaclient.util.PermissionUtils;
import android.os.Handler;
import android.os.Debug;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.android.debug.DebugOverlay;
import com.netflix.mediaclient.ui.home.HomeActivity;
import com.netflix.mediaclient.util.net.CronetHttpURLConnectionFactory;
import com.netflix.mediaclient.util.PreferenceUtils;
import android.view.Menu;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import android.content.Context;
import android.widget.Toast;
import com.netflix.mediaclient.service.configuration.ConfigurationAgentWebCallback;
import android.view.MenuItem;
import android.view.MenuItem$OnMenuItemClickListener;

class DebugMenuItems$27 implements MenuItem$OnMenuItemClickListener
{
    final /* synthetic */ DebugMenuItems this$0;
    
    DebugMenuItems$27(final DebugMenuItems this$0) {
        this.this$0 = this$0;
    }
    
    public boolean onMenuItemClick(final MenuItem menuItem) {
        this.this$0.activity.getServiceManager().getConfiguration().fetchAccountConfigData(new DebugMenuItems$27$1(this));
        Toast.makeText((Context)this.this$0.activity, (CharSequence)"Requesting...", 0).show();
        return true;
    }
}
