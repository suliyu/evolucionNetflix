// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.user;

import com.netflix.mediaclient.javabridge.ui.ActivationTokens;
import com.netflix.mediaclient.service.user.volley.FriendForRecommendation;
import java.util.Set;
import com.netflix.mediaclient.ui.profiles.RestrictedProfilesReceiver;
import com.netflix.mediaclient.util.AndroidUtils;
import com.netflix.mediaclient.ui.experience.BrowseExperience;
import com.netflix.mediaclient.util.StatusUtils;
import com.netflix.mediaclient.service.logging.client.model.RootCause;
import com.netflix.mediaclient.android.app.CommonStatus;
import com.netflix.mediaclient.repository.UserLocale;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import java.util.Iterator;
import android.support.v4.content.LocalBroadcastManager;
import com.netflix.mediaclient.android.app.BackgroundTask;
import com.netflix.mediaclient.util.PreferenceUtils;
import com.netflix.mediaclient.android.app.NetflixStatus;
import com.netflix.mediaclient.StatusCode;
import com.netflix.mediaclient.ui.profiles.ProfileSelectionActivity;
import com.netflix.mediaclient.NetflixApplication;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import android.content.Intent;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.JSONArray;
import java.util.ArrayList;
import com.netflix.mediaclient.util.StringUtils;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.android.app.Status;
import com.netflix.mediaclient.service.NetflixService;
import com.netflix.mediaclient.service.webclient.model.leafs.User;
import com.netflix.mediaclient.service.player.subtitles.TextStyle;
import com.netflix.mediaclient.javabridge.ui.Registration;
import com.netflix.mediaclient.javabridge.ui.Nrdp;
import java.util.List;
import com.netflix.mediaclient.service.webclient.model.leafs.UserProfile;
import com.netflix.mediaclient.javabridge.ui.DeviceAccount;
import com.netflix.mediaclient.javabridge.ui.EventListener;
import com.netflix.mediaclient.service.configuration.ConfigurationAgentWebCallback;
import com.netflix.mediaclient.service.webclient.UserCredentialRegistry;
import com.netflix.mediaclient.service.ServiceAgent$UserAgentInterface;
import com.netflix.mediaclient.service.ServiceAgent;

class UserAgent$FetchFriendsForRecommendationsTask implements Runnable
{
    private final int fromIndex;
    UserAgent$UserAgentCallback mCallback;
    private final String searchTerm;
    final /* synthetic */ UserAgent this$0;
    private final String videoId;
    private final UserAgentWebCallback webClientCallback;
    
    public UserAgent$FetchFriendsForRecommendationsTask(final UserAgent this$0, final String videoId, final int fromIndex, final String searchTerm, final UserAgent$UserAgentCallback mCallback) {
        this.this$0 = this$0;
        this.webClientCallback = new UserAgent$FetchFriendsForRecommendationsTask$1(this);
        this.mCallback = mCallback;
        this.videoId = videoId;
        this.fromIndex = fromIndex;
        this.searchTerm = searchTerm;
    }
    
    @Override
    public void run() {
        this.this$0.mUserWebClient.fetchFriendsForRecommendations(this.videoId, this.fromIndex, this.searchTerm, this.webClientCallback);
    }
}