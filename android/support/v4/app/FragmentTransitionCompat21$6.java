// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v4.app;

import android.transition.Transition;
import android.graphics.Rect;
import android.transition.Transition$EpicenterCallback;

final class FragmentTransitionCompat21$6 extends Transition$EpicenterCallback
{
    final /* synthetic */ Rect val$epicenter;
    
    FragmentTransitionCompat21$6(final Rect val$epicenter) {
        this.val$epicenter = val$epicenter;
    }
    
    public Rect onGetEpicenter(final Transition transition) {
        if (this.val$epicenter == null || this.val$epicenter.isEmpty()) {
            return null;
        }
        return this.val$epicenter;
    }
}
