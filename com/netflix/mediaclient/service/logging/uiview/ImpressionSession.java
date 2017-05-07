// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.logging.uiview;

import com.netflix.mediaclient.service.logging.uiview.model.ImpressionSessionEndedEvent;
import com.netflix.mediaclient.service.logging.client.model.Error;
import com.netflix.mediaclient.servicemgr.IClientLogging;

public class ImpressionSession extends BaseUIViewSession
{
    public static final String NAME = "impression";
    
    public ImpressionSession(final IClientLogging.ModalView modalView) {
        super(modalView);
    }
    
    public ImpressionSessionEndedEvent createEndedEvent(final boolean b, final Error error) {
        final ImpressionSessionEndedEvent impressionSessionEndedEvent = new ImpressionSessionEndedEvent(this.mId, System.currentTimeMillis() - this.mStarted, this.mView, b, error);
        impressionSessionEndedEvent.setCategory(this.getCategory());
        impressionSessionEndedEvent.setSessionId(this.mId);
        return impressionSessionEndedEvent;
    }
    
    @Override
    public String getName() {
        return "impression";
    }
}