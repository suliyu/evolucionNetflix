// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.servicemgr;

import java.util.List;

public interface PresentationTracking
{
    void reportPresentation(final Trackable p0, final List<String> p1, final int p2, final UiLocation p3);
}
