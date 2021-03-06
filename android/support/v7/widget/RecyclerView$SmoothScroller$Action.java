// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v7.widget;

import android.util.Log;
import android.view.animation.Interpolator;

public class RecyclerView$SmoothScroller$Action
{
    private boolean changed;
    private int consecutiveUpdates;
    private int mDuration;
    private int mDx;
    private int mDy;
    private Interpolator mInterpolator;
    private int mJumpToPosition;
    
    public RecyclerView$SmoothScroller$Action(final int n, final int n2) {
        this(n, n2, Integer.MIN_VALUE, null);
    }
    
    public RecyclerView$SmoothScroller$Action(final int mDx, final int mDy, final int mDuration, final Interpolator mInterpolator) {
        this.mJumpToPosition = -1;
        this.changed = false;
        this.consecutiveUpdates = 0;
        this.mDx = mDx;
        this.mDy = mDy;
        this.mDuration = mDuration;
        this.mInterpolator = mInterpolator;
    }
    
    private void validate() {
        if (this.mInterpolator != null && this.mDuration < 1) {
            throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
        }
        if (this.mDuration < 1) {
            throw new IllegalStateException("Scroll duration must be a positive number");
        }
    }
    
    boolean hasJumpTarget() {
        return this.mJumpToPosition >= 0;
    }
    
    public void jumpTo(final int mJumpToPosition) {
        this.mJumpToPosition = mJumpToPosition;
    }
    
    void runIfNecessary(final RecyclerView recyclerView) {
        if (this.mJumpToPosition >= 0) {
            final int mJumpToPosition = this.mJumpToPosition;
            this.mJumpToPosition = -1;
            recyclerView.jumpToPositionForSmoothScroller(mJumpToPosition);
            this.changed = false;
            return;
        }
        if (this.changed) {
            this.validate();
            if (this.mInterpolator == null) {
                if (this.mDuration == Integer.MIN_VALUE) {
                    recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
                }
                else {
                    recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
                }
            }
            else {
                recyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
            }
            ++this.consecutiveUpdates;
            if (this.consecutiveUpdates > 10) {
                Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
            }
            this.changed = false;
            return;
        }
        this.consecutiveUpdates = 0;
    }
    
    public void update(final int mDx, final int mDy, final int mDuration, final Interpolator mInterpolator) {
        this.mDx = mDx;
        this.mDy = mDy;
        this.mDuration = mDuration;
        this.mInterpolator = mInterpolator;
        this.changed = true;
    }
}
