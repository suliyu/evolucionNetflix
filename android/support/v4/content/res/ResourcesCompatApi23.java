// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v4.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources$Theme;
import android.content.res.Resources;
import android.annotation.TargetApi;

@TargetApi(23)
class ResourcesCompatApi23
{
    public static int getColor(final Resources resources, final int n, final Resources$Theme resources$Theme) {
        return resources.getColor(n, resources$Theme);
    }
    
    public static ColorStateList getColorStateList(final Resources resources, final int n, final Resources$Theme resources$Theme) {
        return resources.getColorStateList(n, resources$Theme);
    }
}
