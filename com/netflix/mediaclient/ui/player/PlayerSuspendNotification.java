// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.player;

import com.netflix.mediaclient.servicemgr.ManagerCallback;
import com.netflix.mediaclient.servicemgr.model.details.MovieDetails;
import com.netflix.mediaclient.android.app.Status;
import com.netflix.mediaclient.servicemgr.model.details.EpisodeDetails;
import com.netflix.mediaclient.servicemgr.SimpleManagerCallback;
import com.netflix.mediaclient.ui.Asset;
import android.app.Notification;
import android.text.TextUtils;
import android.app.Notification$Builder;
import android.support.v4.app.NotificationCompat;
import com.netflix.mediaclient.util.AndroidUtils;
import android.content.IntentFilter;
import android.widget.RemoteViews;
import com.netflix.mediaclient.util.gfx.ImageLoader;
import com.netflix.mediaclient.servicemgr.IClientLogging;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.util.StringUtils;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import java.util.concurrent.atomic.AtomicBoolean;
import com.netflix.mediaclient.servicemgr.ServiceManager;
import android.app.NotificationManager;
import android.annotation.SuppressLint;

@SuppressLint({ "InlinedApi" })
public final class PlayerSuspendNotification
{
    private static final int NOTIFICATION_ID = 2;
    private static final String PLAYER_SUSPEND_NOTIFICATION_ACTION_DELETE = "com.netflix.mediaclient.intent.action.PLAYER_DELETE";
    private static final String TAG;
    private final NotificationManager mNotificationManager;
    private final PlayerActivity mPlayerActivity;
    private String mSecondaryTitle;
    private final ServiceManager mServiceManager;
    private AtomicBoolean mShowNotification;
    private String mTitle;
    
    static {
        TAG = PlayerSuspendNotification.class.getSimpleName();
    }
    
    PlayerSuspendNotification(final PlayerActivity mPlayerActivity, final ServiceManager mServiceManager) {
        this.mShowNotification = new AtomicBoolean(false);
        this.mPlayerActivity = mPlayerActivity;
        this.mServiceManager = mServiceManager;
        this.mNotificationManager = (NotificationManager)mPlayerActivity.getSystemService("notification");
    }
    
    private PendingIntent createNotificationPendingIntentDelete() {
        return PendingIntent.getBroadcast((Context)this.mPlayerActivity, 0, new Intent("com.netflix.mediaclient.intent.action.PLAYER_DELETE"), 134217728);
    }
    
    private PendingIntent createNotificationPendingIntentResume() {
        return PendingIntent.getActivity((Context)this.mPlayerActivity, 0, this.mPlayerActivity.getIntent(), 268435456);
    }
    
    private void fetchImageWithLoader(final String s) {
        if (StringUtils.isEmpty(s)) {
            Log.e(PlayerSuspendNotification.TAG, "Loader url empty");
            return;
        }
        final ImageLoader imageLoader = this.mServiceManager.getImageLoader();
        if (imageLoader != null) {
            imageLoader.getImg(s, IClientLogging.AssetType.boxArt, 0, 0, (ImageLoader.ImageLoaderListener)new ImageLoader.ImageLoaderListener() {
                @Override
                public void onErrorResponse(final String s) {
                    Log.e(PlayerSuspendNotification.TAG, "failed to downlod " + s);
                }
                
                @Override
                public void onResponse(Bitmap copy, final String s) {
                    if (copy != null && !copy.isRecycled()) {
                        if (Log.isLoggable(PlayerSuspendNotification.TAG, 3)) {
                            Log.d(PlayerSuspendNotification.TAG, "downloaded image from " + s);
                        }
                        copy = copy.copy(copy.getConfig(), copy.isMutable());
                        PlayerSuspendNotification.this.show(copy);
                        return;
                    }
                    Log.e(PlayerSuspendNotification.TAG, "bitmap is not valid " + copy);
                }
            });
            return;
        }
        Log.e(PlayerSuspendNotification.TAG, "ImageLoader is null!");
    }
    
    private RemoteViews getContentView(final String s, final String s2, final Bitmap bitmap, final boolean b) {
        final String packageName = this.mPlayerActivity.getPackageName();
        final boolean notEmpty = StringUtils.isNotEmpty(s2);
        RemoteViews remoteViews;
        if (b) {
            if (notEmpty) {
                remoteViews = new RemoteViews(packageName, 2130903184);
            }
            else {
                remoteViews = new RemoteViews(packageName, 2130903186);
            }
        }
        else if (notEmpty) {
            remoteViews = new RemoteViews(packageName, 2130903183);
        }
        else {
            remoteViews = new RemoteViews(packageName, 2130903185);
        }
        if (bitmap != null) {
            remoteViews.setImageViewBitmap(2131165640, bitmap);
        }
        if (StringUtils.isNotEmpty(s)) {
            remoteViews.setTextViewText(2131165641, (CharSequence)s);
        }
        else {
            remoteViews.setTextViewText(2131165641, (CharSequence)"");
        }
        if (notEmpty) {
            remoteViews.setTextViewText(2131165642, (CharSequence)s2);
        }
        return remoteViews;
    }
    
    public static IntentFilter getNotificationIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.netflix.mediaclient.intent.action.PLAYER_DELETE");
        return intentFilter;
    }
    
    public static boolean isDelete(final String s) {
        return "com.netflix.mediaclient.intent.action.PLAYER_DELETE".equalsIgnoreCase(s);
    }
    
    private void show(final Bitmap largeIcon) {
        if (!this.mShowNotification.get()) {
            return;
        }
        Notification notification2;
        if (AndroidUtils.getAndroidVersion() < 21) {
            final Notification notification = notification2 = new NotificationCompat.Builder((Context)this.mPlayerActivity).setOngoing(0 != 0).setOnlyAlertOnce(1 != 0).setSmallIcon(2130837768).setTicker(this.mTitle).setContentIntent(this.createNotificationPendingIntentResume()).setDeleteIntent(this.createNotificationPendingIntentDelete()).setContent(this.getContentView(this.mTitle, this.mSecondaryTitle, largeIcon, (boolean)(0 != 0))).setWhen(System.currentTimeMillis()).build();
            if (AndroidUtils.getAndroidVersion() >= 16) {
                notification.bigContentView = this.getContentView(this.mTitle, this.mSecondaryTitle, largeIcon, true);
                notification2 = notification;
            }
        }
        else {
            final int color = this.mPlayerActivity.getResources().getColor(2131296356);
            final String string = this.mPlayerActivity.getResources().getString(2131493359);
            final Notification$Builder setVisibility = new Notification$Builder((Context)this.mPlayerActivity).setOngoing(false).setOnlyAlertOnce(true).setSmallIcon(2130837768).setLargeIcon(largeIcon).setTicker((CharSequence)this.mTitle).setContentTitle((CharSequence)this.mTitle).setColor(color).setContentIntent(this.createNotificationPendingIntentResume()).setDeleteIntent(this.createNotificationPendingIntentDelete()).setWhen(System.currentTimeMillis()).setVisibility(-1);
            if (TextUtils.isEmpty((CharSequence)this.mSecondaryTitle)) {
                setVisibility.setContentText((CharSequence)string);
            }
            else {
                setVisibility.setContentText((CharSequence)this.mSecondaryTitle);
                setVisibility.setSubText((CharSequence)string);
            }
            notification2 = setVisibility.build();
        }
        this.mNotificationManager.notify(2, notification2);
    }
    
    void cancelNotification() {
        this.mShowNotification.set(false);
        this.mNotificationManager.cancel(2);
    }
    
    void showNotification(final Asset asset) {
        if (asset == null) {
            return;
        }
        this.mShowNotification.set(true);
        final SimpleManagerCallback simpleManagerCallback = new SimpleManagerCallback() {
            @Override
            public void onEpisodeDetailsFetched(final EpisodeDetails episodeDetails, final Status status) {
                if (status.isSucces()) {
                    PlayerSuspendNotification.this.fetchImageWithLoader(episodeDetails.getHighResolutionPortraitBoxArtUrl());
                }
            }
            
            @Override
            public void onMovieDetailsFetched(final MovieDetails movieDetails, final Status status) {
                if (status.isSucces()) {
                    PlayerSuspendNotification.this.fetchImageWithLoader(movieDetails.getHighResolutionPortraitBoxArtUrl());
                }
            }
        };
        if (asset.isEpisode()) {
            this.mSecondaryTitle = this.mPlayerActivity.getApplicationContext().getString(2131493256, new Object[] { asset.getSeasonNumber(), asset.getEpisodeNumber(), asset.getTitle() });
            this.mTitle = asset.getParentTitle();
            this.mServiceManager.getBrowse().fetchEpisodeDetails(String.valueOf(asset.getPlayableId()), simpleManagerCallback);
            return;
        }
        this.mServiceManager.getBrowse().fetchMovieDetails(String.valueOf(asset.getPlayableId()), simpleManagerCallback);
        this.mSecondaryTitle = null;
        this.mTitle = asset.getTitle();
    }
}