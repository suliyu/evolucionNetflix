// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.player.subtitles;

import android.content.Context;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import com.netflix.mediaclient.ui.player.PlayerFragment;
import com.netflix.mediaclient.servicemgr.ISubtitleDef$SubtitleProfile;

public final class SubtitleManagerFactory
{
    public static SubtitleManager createInstance(final ISubtitleDef$SubtitleProfile subtitleDef$SubtitleProfile, final PlayerFragment playerFragment) {
        if (playerFragment == null) {
            throw new IllegalArgumentException("Player fragment is null!");
        }
        if (playerFragment.getActivity() == null) {
            throw new IllegalArgumentException("Player fragment's activity is null!");
        }
        if (subtitleDef$SubtitleProfile == null) {
            throw new IllegalArgumentException("Subtitle profile can not be null!");
        }
        if (ISubtitleDef$SubtitleProfile.IMAGE.equals(subtitleDef$SubtitleProfile) || ISubtitleDef$SubtitleProfile.IMAGE_ENC.equals(subtitleDef$SubtitleProfile)) {
            return new ImageBasedSubtitleManager(playerFragment);
        }
        return new EnhancedSubtitleManager(playerFragment);
    }
    
    public static String getSubtitleManagerLabel(final SubtitleManager subtitleManager) {
        String string = "";
        final NetflixActivity context = subtitleManager.getContext();
        if (subtitleManager instanceof EnhancedSubtitleManager) {
            string = ((Context)context).getString(2131297092);
        }
        else if (subtitleManager instanceof ImageBasedSubtitleManager) {
            return ((Context)context).getString(2131297091);
        }
        return string;
    }
}
