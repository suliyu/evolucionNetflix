// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

class DrawableCompat$HoneycombDrawableImpl extends DrawableCompat$BaseDrawableImpl
{
    @Override
    public void jumpToCurrentState(final Drawable drawable) {
        DrawableCompatHoneycomb.jumpToCurrentState(drawable);
    }
    
    @Override
    public Drawable wrap(final Drawable drawable) {
        return DrawableCompatHoneycomb.wrapForTinting(drawable);
    }
}
