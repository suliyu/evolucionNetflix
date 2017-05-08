// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v4.app;

import android.os.Bundle;
import android.annotation.TargetApi;

@TargetApi(21)
class ActivityOptionsCompat$ActivityOptionsImpl21 extends ActivityOptionsCompat
{
    private final ActivityOptionsCompat21 mImpl;
    
    ActivityOptionsCompat$ActivityOptionsImpl21(final ActivityOptionsCompat21 mImpl) {
        this.mImpl = mImpl;
    }
    
    @Override
    public Bundle toBundle() {
        return this.mImpl.toBundle();
    }
    
    @Override
    public void update(final ActivityOptionsCompat activityOptionsCompat) {
        if (activityOptionsCompat instanceof ActivityOptionsCompat$ActivityOptionsImpl21) {
            this.mImpl.update(((ActivityOptionsCompat$ActivityOptionsImpl21)activityOptionsCompat).mImpl);
        }
    }
}
