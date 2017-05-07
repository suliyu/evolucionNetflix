// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.social.notifications.types;

import android.text.Html;
import android.text.format.DateUtils;
import com.netflix.mediaclient.servicemgr.IClientLogging$AssetType;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import com.netflix.mediaclient.util.StringUtils;
import android.view.View;
import com.netflix.model.leafs.social.SocialNotificationSummary$NotificationTypes;
import android.widget.TextView;
import com.netflix.mediaclient.ui.social.notifications.SocialNotificationViewHolder;
import android.content.Context;
import com.netflix.model.leafs.social.SocialNotificationSummary;
import android.support.v4.app.NotificationCompat$BigPictureStyle;
import android.support.v4.app.NotificationCompat$Builder;

public class NewSeasonAlert extends SocialNotification
{
    @Override
    protected void addNotificationText(final NotificationCompat$Builder notificationCompat$Builder, final NotificationCompat$BigPictureStyle notificationCompat$BigPictureStyle, final SocialNotificationSummary socialNotificationSummary, final Context context) {
    }
    
    @Override
    public TextView getAddToMyListButton(final SocialNotificationViewHolder socialNotificationViewHolder) {
        return (TextView)socialNotificationViewHolder.getRightButton();
    }
    
    @Override
    public SocialNotificationSummary$NotificationTypes getNotificationType() {
        return SocialNotificationSummary$NotificationTypes.NEW_SEASON_ALERT;
    }
    
    @Override
    public View getPlayMovieButton(final SocialNotificationViewHolder socialNotificationViewHolder) {
        return (View)socialNotificationViewHolder.getNSAArtImage();
    }
    
    @Override
    public void initView(final View view, final SocialNotificationViewHolder socialNotificationViewHolder, final SocialNotificationSummary socialNotificationSummary, final Context context) {
        socialNotificationViewHolder.getTopTextView().setVisibility(8);
        socialNotificationViewHolder.getFriendImage().setVisibility(8);
        int backgroundResource;
        if (socialNotificationSummary.getWasRead()) {
            backgroundResource = 2130837877;
        }
        else {
            backgroundResource = 2131230889;
        }
        view.setBackgroundResource(backgroundResource);
        socialNotificationViewHolder.getMovieArtImage().setVisibility(8);
        socialNotificationViewHolder.getNSAArtImage().setVisibility(0);
        String s;
        if (StringUtils.isEmpty(socialNotificationSummary.getNSABoxartUrl())) {
            s = socialNotificationSummary.getVideo().getHorzDispUrl();
        }
        else {
            s = socialNotificationSummary.getNSABoxartUrl();
        }
        NetflixActivity.getImageLoader(context).showImg(socialNotificationViewHolder.getNSAArtImage(), s, IClientLogging$AssetType.boxArt, socialNotificationSummary.getVideo().getTitle(), true, true);
        socialNotificationViewHolder.getBottomTextView().setVisibility(8);
        socialNotificationViewHolder.getTimeStampView().setVisibility(0);
        socialNotificationViewHolder.getTimeStampView().setText(DateUtils.getRelativeTimeSpanString(context, socialNotificationSummary.getNSATimestamp()));
        socialNotificationViewHolder.getPlayButton().setVisibility(8);
        socialNotificationViewHolder.getNSAPlayButton().setVisibility(0);
        socialNotificationViewHolder.getLeftButton().setVisibility(8);
        socialNotificationViewHolder.getRightButton().setVisibility(8);
        final int resourceStringId = socialNotificationSummary.getShowType().getResourceStringId();
        socialNotificationViewHolder.getMiddleTextView().setGravity(3);
        String s2;
        if (socialNotificationSummary.getNSASeasonsCount() > 1) {
            s2 = context.getResources().getQuantityString(resourceStringId, socialNotificationSummary.getNSASeasonsCount(), new Object[] { socialNotificationSummary.getNSASeasonsCount(), socialNotificationSummary.getVideo().getTitle() });
        }
        else {
            s2 = context.getResources().getQuantityString(resourceStringId, 1, new Object[] { socialNotificationSummary.getVideo().getTitle(), socialNotificationSummary.getNSASeasonIndex() });
        }
        socialNotificationViewHolder.getMiddleTextView().setText((CharSequence)Html.fromHtml(s2));
        socialNotificationViewHolder.getRightButton().setVisibility(0);
        socialNotificationViewHolder.getLeftButton().setVisibility(4);
    }
    
    @Override
    public boolean supportsStatusBar() {
        return false;
    }
}