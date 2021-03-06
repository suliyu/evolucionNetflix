// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.pushnotification;

import android.content.Context;
import com.netflix.mediaclient.util.AndroidUtils;
import com.netflix.mediaclient.NetflixApplication;
import com.netflix.mediaclient.util.StringUtils;
import com.netflix.mediaclient.util.gfx.ImageLoader;
import com.netflix.mediaclient.service.NetflixService;
import com.netflix.mediaclient.Log;
import android.content.Intent;

public final class NotificationFactory
{
    public static final String DATA = "data";
    private static final String TAG = "nf_push_notificationFactory";
    
    public static Intent createDummyGcmInfoIntent(final Payload$ActionInfoType payload$ActionInfoType) {
        final Intent intent = new Intent();
        intent.putExtra("profileId", "W6HQ56YURRBVHKDXMUT6FBHWRY");
        intent.putExtra("type", payload$ActionInfoType.getValue());
        intent.putExtra("when", "1408065095164");
        intent.putExtra("defaultActionKey", "INFO");
        Log.d("nf_push_notificationFactory", String.format("Built gcmInfoEvent for type:%s, intent: %s", payload$ActionInfoType, intent));
        return intent;
    }
    
    public static void createDummyNotification(final NetflixService netflixService) {
    }
    
    public static void createNotification(final NetflixService netflixService, final Intent intent, final ImageLoader imageLoader, final int n) {
        if (intent == null) {
            Log.e("nf_push_notificationFactory", "NotificationFactory.createNotification:: Intent is null!");
        }
        else {
            if (netflixService == null) {
                Log.e("nf_push_notificationFactory", "NotificationFactory.createNotification:: service is null!");
                return;
            }
            final Context applicationContext = netflixService.getApplicationContext();
            if (applicationContext != null) {
                final Payload payload = new Payload(intent);
                if (isValid(payload)) {
                    if (netflixService.getCurrentProfile() != null && StringUtils.isNotEmpty(payload.profileGuid)) {
                        final String profileGuid = netflixService.getCurrentProfile().getProfileGuid();
                        if (!StringUtils.safeEquals(profileGuid, payload.profileGuid)) {
                            Log.d("nf_push_notificationFactory", String.format("drop push event - currentProfile :%s != profileGuid:%s", profileGuid, payload.profileGuid));
                            return;
                        }
                    }
                    else {
                        Log.w("nf_push_notificationFactory", "processing gcm message, payload has no profileId or currentProfile is null ");
                    }
                    if ("INFO".equals(payload.defaultActionKey)) {
                        if (netflixService.getCurrentProfile() == null) {
                            Log.d("nf_push_notificationFactory", String.format("currentProfile null dropping gcm event payload:%s", payload));
                            return;
                        }
                        InfoEventHandler.getInstance().handleEvent(netflixService, payload, intent);
                    }
                    else if (!handleSocialAction(netflixService, payload, intent)) {
                        if (NetflixApplication.isActivityVisible()) {
                            Log.i("nf_push_notificationFactory", "App is active - don't send a notification to Android status bar");
                            return;
                        }
                        final int androidVersion = AndroidUtils.getAndroidVersion();
                        if (androidVersion >= 16) {
                            NotificationBuilderJellyBean.createNotification(applicationContext, payload, imageLoader, n);
                            return;
                        }
                        if (androidVersion >= 11) {
                            NotificationBuilderHoneycomb.createNotification(applicationContext, payload, imageLoader, n);
                            return;
                        }
                        throw new IllegalStateException("Invalid api level");
                    }
                }
            }
        }
    }
    
    private static boolean handleSocialAction(final NetflixService netflixService, final Payload payload, final Intent intent) {
        if (!"SOCIAL".equals(payload.defaultActionKey)) {
            return false;
        }
        if (netflixService.getCurrentProfile() == null) {
            if (Log.isLoggable()) {
                Log.e("nf_push_notificationFactory", String.format("currentProfile null dropping social event payload:%s", payload));
            }
            return true;
        }
        if (Log.isLoggable()) {
            Log.d("nf_push_notificationFactory", String.format("rcvd visible push social notification: payload: %s", payload));
        }
        final MessageData instance = MessageData.createInstance(intent);
        if (Log.isLoggable()) {
            Log.d("nf_push_notificationFactory", "refreshIrisNotifications with " + instance);
        }
        netflixService.getBrowse().refreshIrisNotifications(true, true, instance);
        return true;
    }
    
    private static boolean isValid(final Payload payload) {
        final String valid = Payload.isValid(payload);
        if (StringUtils.isNotEmpty(valid)) {
            if (Log.isLoggable()) {
                Log.e("nf_push_notificationFactory", "NotificationFactory.createNotification:: Not correct payload: " + valid);
            }
            return false;
        }
        return true;
    }
}
