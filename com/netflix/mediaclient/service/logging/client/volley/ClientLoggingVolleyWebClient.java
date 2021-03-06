// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.logging.client.volley;

import com.netflix.mediaclient.service.logging.client.ClientLoggingWebCallback;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.service.webclient.volley.FalkorVolleyWebClient;
import com.netflix.mediaclient.service.logging.client.ClientLoggingWebClient;

public class ClientLoggingVolleyWebClient implements ClientLoggingWebClient
{
    private static final String TAG = "nf_log";
    private final FalkorVolleyWebClient mWebClient;
    
    public ClientLoggingVolleyWebClient(final FalkorVolleyWebClient mWebClient) {
        this.mWebClient = mWebClient;
    }
    
    @Override
    public boolean isSynchronous() {
        return this.mWebClient.isSynchronous();
    }
    
    @Override
    public void sendLoggingEvents(final String s) {
        Log.d("nf_log", "sendLoggingEvents without callback starts...");
        this.mWebClient.sendLoggingRequest(new LoggingEventsRequest(null, s, null));
        Log.d("nf_log", "sendLoggingEvents without callback  done.");
    }
    
    @Override
    public void sendLoggingEvents(final String s, final String s2, final ClientLoggingWebCallback clientLoggingWebCallback) {
        Log.d("nf_log", "sendLoggingEvents starts...");
        this.mWebClient.sendLoggingRequest(new LoggingEventsRequest(s, s2, clientLoggingWebCallback));
        Log.d("nf_log", "sendLoggingEvents done.");
    }
}
