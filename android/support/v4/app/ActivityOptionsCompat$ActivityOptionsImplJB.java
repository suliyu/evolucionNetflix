// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v4.app;

import android.os.Bundle;
import android.annotation.TargetApi;

@TargetApi(16)
class ActivityOptionsCompat$ActivityOptionsImplJB extends ActivityOptionsCompat
{
    private final ActivityOptionsCompatJB mImpl;
    
    ActivityOptionsCompat$ActivityOptionsImplJB(final ActivityOptionsCompatJB mImpl) {
        this.mImpl = mImpl;
    }
    
    @Override
    public Bundle toBundle() {
        return this.mImpl.toBundle();
    }
    
    @Override
    public void update(final ActivityOptionsCompat activityOptionsCompat) {
        if (activityOptionsCompat instanceof ActivityOptionsCompat$ActivityOptionsImplJB) {
            this.mImpl.update(((ActivityOptionsCompat$ActivityOptionsImplJB)activityOptionsCompat).mImpl);
        }
    }
}
