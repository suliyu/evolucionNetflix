// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.falkor;

import com.netflix.falkor.Ref;
import com.netflix.model.branches.FalkorEvidenceList;
import com.netflix.falkor.Func;

final class Falkor$Creator$14 implements Func<FalkorEvidenceList<Ref>>
{
    @Override
    public FalkorEvidenceList<Ref> call() {
        return new FalkorEvidenceList<Ref>(Falkor$Creator.Ref);
    }
}