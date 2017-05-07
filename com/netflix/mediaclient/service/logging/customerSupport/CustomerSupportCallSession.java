// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.logging.customerSupport;

import com.netflix.mediaclient.service.logging.customerSupport.model.CustomerSupportCallSessionEndedEvent;
import com.netflix.mediaclient.service.logging.client.model.Error;
import com.netflix.mediaclient.servicemgr.IClientLogging$CompletionReason;
import java.util.ArrayList;
import java.util.List;
import com.netflix.mediaclient.servicemgr.CustomerServiceLogging$CallQuality;

public final class CustomerSupportCallSession extends BaseCustomerSupportSession
{
    public static final String NAME = "customerSupportCall";
    private long mCallStartedTime;
    private CustomerServiceLogging$CallQuality mCurrentCallQuality;
    private long mCurrentCallQualitySegmentStartTimeInMs;
    private List<CustomerSupportCallSession$CallQualitySegment> mStates;
    
    public CustomerSupportCallSession() {
        this.mStates = new ArrayList<CustomerSupportCallSession$CallQualitySegment>();
    }
    
    private void addCurrentQualitySegment() {
        final long currentTimeMillis = System.currentTimeMillis();
        this.mStates.add(new CustomerSupportCallSession$CallQualitySegment((int)((currentTimeMillis - this.mCurrentCallQualitySegmentStartTimeInMs) / 1000L), this.mCurrentCallQuality));
        this.mCurrentCallQualitySegmentStartTimeInMs = currentTimeMillis;
    }
    
    public void callConnected(final CustomerServiceLogging$CallQuality mCurrentCallQuality) {
        this.mCallStartedTime = System.currentTimeMillis();
        this.mCurrentCallQualitySegmentStartTimeInMs = this.mCallStartedTime;
        this.mCurrentCallQuality = mCurrentCallQuality;
    }
    
    public CustomerSupportCallSessionEndedEvent createCustomerSupportCallSessionEndedEvent(final IClientLogging$CompletionReason clientLogging$CompletionReason, final Error error) {
        this.addCurrentQualitySegment();
        return new CustomerSupportCallSessionEndedEvent(this, (int)((System.currentTimeMillis() - this.mCallStartedTime) / 1000L), clientLogging$CompletionReason, error);
    }
    
    @Override
    public String getName() {
        return "customerSupportCall";
    }
    
    public List<CustomerSupportCallSession$CallQualitySegment> getQualityStates() {
        return this.mStates;
    }
    
    public void setCallQuality(final CustomerServiceLogging$CallQuality mCurrentCallQuality) {
        if (this.mCurrentCallQuality == mCurrentCallQuality) {
            return;
        }
        this.addCurrentQualitySegment();
        this.mCurrentCallQuality = mCurrentCallQuality;
    }
}