// 
// Decompiled by Procyon v0.5.30
// 

package com.crittercism.internal;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;

public final class by extends bo
{
    private by(final File file) {
        super(file);
    }
    
    @Override
    public final Object a() {
        try {
            return new JSONObject((String)super.a());
        }
        catch (JSONException ex) {
            return null;
        }
    }
}
