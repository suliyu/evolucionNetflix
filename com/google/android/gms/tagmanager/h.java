// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.tagmanager;

import android.content.pm.PackageManager$NameNotFoundException;
import com.google.android.gms.internal.d;
import java.util.Map;
import com.google.android.gms.internal.a;
import android.content.Context;

class h extends aj
{
    private static final String ID;
    private final Context mContext;
    
    static {
        ID = a.y.toString();
    }
    
    public h(final Context mContext) {
        super(h.ID, new String[0]);
        this.mContext = mContext;
    }
    
    @Override
    public boolean jX() {
        return true;
    }
    
    @Override
    public d.a x(final Map<String, d.a> map) {
        try {
            return dh.r(this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionCode);
        }
        catch (PackageManager$NameNotFoundException ex) {
            bh.w("Package name " + this.mContext.getPackageName() + " not found. " + ex.getMessage());
            return dh.lT();
        }
    }
}