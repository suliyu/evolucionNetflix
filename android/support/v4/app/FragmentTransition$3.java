// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v4.app;

import android.os.Build$VERSION;
import android.support.v4.view.ViewCompat;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import android.util.SparseArray;
import java.util.ArrayList;
import android.view.ViewGroup;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.graphics.Rect;
import android.view.ViewTreeObserver$OnPreDrawListener;

final class FragmentTransition$3 implements ViewTreeObserver$OnPreDrawListener
{
    final /* synthetic */ Rect val$epicenter;
    final /* synthetic */ View val$epicenterView;
    final /* synthetic */ Fragment val$inFragment;
    final /* synthetic */ boolean val$inIsPop;
    final /* synthetic */ ArrayMap val$inSharedElements;
    final /* synthetic */ Fragment val$outFragment;
    final /* synthetic */ ViewGroup val$sceneRoot;
    
    FragmentTransition$3(final ViewGroup val$sceneRoot, final Fragment val$inFragment, final Fragment val$outFragment, final boolean val$inIsPop, final ArrayMap val$inSharedElements, final View val$epicenterView, final Rect val$epicenter) {
        this.val$sceneRoot = val$sceneRoot;
        this.val$inFragment = val$inFragment;
        this.val$outFragment = val$outFragment;
        this.val$inIsPop = val$inIsPop;
        this.val$inSharedElements = val$inSharedElements;
        this.val$epicenterView = val$epicenterView;
        this.val$epicenter = val$epicenter;
    }
    
    public boolean onPreDraw() {
        this.val$sceneRoot.getViewTreeObserver().removeOnPreDrawListener((ViewTreeObserver$OnPreDrawListener)this);
        callSharedElementStartEnd(this.val$inFragment, this.val$outFragment, this.val$inIsPop, this.val$inSharedElements, false);
        if (this.val$epicenterView != null) {
            FragmentTransitionCompat21.getBoundsOnScreen(this.val$epicenterView, this.val$epicenter);
        }
        return true;
    }
}
