// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v4.app;

import java.util.Iterator;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.app.PendingIntent;
import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.app.Notification$Builder;

public class NotificationCompatApi21$Builder implements NotificationBuilderWithActions, NotificationBuilderWithBuilderAccessor
{
    private Notification$Builder b;
    private RemoteViews mBigContentView;
    private RemoteViews mContentView;
    private Bundle mExtras;
    private RemoteViews mHeadsUpContentView;
    
    public NotificationCompatApi21$Builder(final Context context, final Notification notification, final CharSequence contentTitle, final CharSequence contentText, final CharSequence contentInfo, final RemoteViews remoteViews, final int number, final PendingIntent contentIntent, final PendingIntent pendingIntent, final Bitmap largeIcon, final int n, final int n2, final boolean b, final boolean showWhen, final boolean usesChronometer, final int priority, final CharSequence subText, final boolean localOnly, final String category, final ArrayList<String> list, final Bundle bundle, final int color, final int visibility, final Notification publicVersion, final String group, final boolean groupSummary, final String sortKey, final RemoteViews mContentView, final RemoteViews mBigContentView, final RemoteViews mHeadsUpContentView) {
        this.b = new Notification$Builder(context).setWhen(notification.when).setShowWhen(showWhen).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 0x2) != 0x0).setOnlyAlertOnce((notification.flags & 0x8) != 0x0).setAutoCancel((notification.flags & 0x10) != 0x0).setDefaults(notification.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent, (notification.flags & 0x80) != 0x0).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(usesChronometer).setPriority(priority).setProgress(n, n2, b).setLocalOnly(localOnly).setGroup(group).setGroupSummary(groupSummary).setSortKey(sortKey).setCategory(category).setColor(color).setVisibility(visibility).setPublicVersion(publicVersion);
        this.mExtras = new Bundle();
        if (bundle != null) {
            this.mExtras.putAll(bundle);
        }
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.b.addPerson((String)iterator.next());
        }
        this.mContentView = mContentView;
        this.mBigContentView = mBigContentView;
        this.mHeadsUpContentView = mHeadsUpContentView;
    }
    
    @Override
    public void addAction(final NotificationCompatBase$Action notificationCompatBase$Action) {
        NotificationCompatApi20.addAction(this.b, notificationCompatBase$Action);
    }
    
    @Override
    public Notification build() {
        this.b.setExtras(this.mExtras);
        final Notification build = this.b.build();
        if (this.mContentView != null) {
            build.contentView = this.mContentView;
        }
        if (this.mBigContentView != null) {
            build.bigContentView = this.mBigContentView;
        }
        if (this.mHeadsUpContentView != null) {
            build.headsUpContentView = this.mHeadsUpContentView;
        }
        return build;
    }
    
    @Override
    public Notification$Builder getBuilder() {
        return this.b;
    }
}
