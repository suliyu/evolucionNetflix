// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v7.widget;

import android.view.View;
import android.graphics.drawable.Drawable;
import android.annotation.TargetApi;

@TargetApi(9)
interface CardViewDelegate
{
    Drawable getCardBackground();
    
    View getCardView();
    
    boolean getPreventCornerOverlap();
    
    boolean getUseCompatPadding();
    
    void setCardBackground(final Drawable p0);
    
    void setMinWidthHeightInternal(final int p0, final int p1);
    
    void setShadowPadding(final int p0, final int p1, final int p2, final int p3);
}
