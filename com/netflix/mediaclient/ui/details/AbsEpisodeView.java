// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.details;

import android.support.v4.content.ContextCompat;
import com.netflix.mediaclient.ui.experience.BrowseExperience;
import com.netflix.mediaclient.servicemgr.interface_.Playable;
import android.content.res.Resources;
import java.util.List;
import com.netflix.mediaclient.ui.lomo.LoMoUtils;
import com.netflix.mediaclient.ui.kids.KidsUtils;
import com.netflix.mediaclient.ui.common.PlayLocationType;
import com.netflix.mediaclient.ui.common.PlayContextProvider;
import com.netflix.mediaclient.ui.common.PlayContext;
import com.netflix.mediaclient.Log;
import android.view.ViewGroup;
import android.app.Activity;
import com.netflix.mediaclient.util.StringUtils;
import com.netflix.mediaclient.ui.barker.details.BarkerHelper;
import com.netflix.mediaclient.util.TimeUtils;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.ImageView;
import com.netflix.mediaclient.ui.offline.DownloadButton;
import android.widget.TextView;
import com.netflix.mediaclient.servicemgr.interface_.details.EpisodeDetails;
import android.widget.Checkable;
import android.widget.RelativeLayout;

public abstract class AbsEpisodeView extends RelativeLayout implements Checkable, IEpisodeView<EpisodeDetails>
{
    private static final String TAG = "EpisodeRowView";
    protected boolean checked;
    protected TextView episodeBadge;
    protected DownloadButton episodeDownloadButton;
    protected TextView episodeNumber;
    protected TextView episodeTime;
    private boolean isCheckable;
    protected boolean isCurrentEpisode;
    protected boolean isNSRE;
    protected ImageView playButton;
    protected ProgressBar progressBar;
    protected int progressVal;
    private final int resId;
    protected TextView synopsis;
    protected TextView title;
    
    public AbsEpisodeView(final Context context, final int resId) {
        super(context);
        this.isNSRE = false;
        this.resId = resId;
        this.init();
    }
    
    public static String createEpisodeDuration(final EpisodeDetails episodeDetails, final Context context) {
        return TimeUtils.getFormattedTime(episodeDetails.getPlayable().getRuntime(), context);
    }
    
    public static String createEpisodeNumber(final EpisodeDetails episodeDetails, final Context context) {
        String s = Integer.toString(episodeDetails.getEpisodeNumber());
        if (BarkerHelper.isInTest(context)) {
            s = context.getResources().getString(2131296459, new Object[] { s });
        }
        return s;
    }
    
    public static String createTitleText(final EpisodeDetails episodeDetails, final Context context) {
        if (episodeDetails.isNSRE() || episodeDetails.isAvailableToStream()) {
            return episodeDetails.getTitle();
        }
        if (StringUtils.isEmpty(episodeDetails.getAvailabilityDateMessage())) {
            return context.getString(2131296704);
        }
        return episodeDetails.getAvailabilityDateMessage();
    }
    
    private void init() {
        this.isCheckable = true;
        ((Activity)this.getContext()).getLayoutInflater().inflate(this.resId, (ViewGroup)this);
        this.findViews();
    }
    
    private void updateCheckability(final EpisodeDetails episodeDetails) {
        this.isCheckable = (episodeDetails.isAvailableToStream() && StringUtils.isNotEmpty(episodeDetails.getSynopsis()));
    }
    
    protected CharSequence createTitleText(final EpisodeDetails episodeDetails) {
        return createTitleText(episodeDetails, this.getContext());
    }
    
    protected void findViews() {
        this.episodeBadge = (TextView)this.findViewById(2131820725);
        this.title = (TextView)this.findViewById(2131820727);
        this.episodeNumber = (TextView)this.findViewById(2131820726);
        this.synopsis = (TextView)this.findViewById(2131820728);
        this.playButton = (ImageView)this.findViewById(2131820720);
        this.episodeDownloadButton = (DownloadButton)this.findViewById(2131820723);
        this.progressBar = (ProgressBar)this.findViewById(2131820722);
        this.episodeTime = (TextView)this.findViewById(2131820729);
    }
    
    protected int getDefaultSynopsisVisibility() {
        return 8;
    }
    
    public boolean isChecked() {
        return this.checked;
    }
    
    protected void playEpisode(final EpisodeDetails episodeDetails) {
        if (!(this.getContext() instanceof AbsEpisodeView$EpisodeRowListenerProvider)) {
            Log.w("EpisodeRowView", "Context is not an EpisodeRowListenerProvider, context: " + this.getContext());
            return;
        }
        final AbsEpisodeView$EpisodeRowListener episodeRowListener = ((AbsEpisodeView$EpisodeRowListenerProvider)this.getContext()).getEpisodeRowListener();
        if (episodeRowListener != null) {
            PlayContext playContext = PlayContext.EMPTY_CONTEXT;
            if (this.getContext() instanceof PlayContextProvider) {
                playContext = ((PlayContextProvider)this.getContext()).getPlayContext();
            }
            playContext.setPlayLocation(PlayLocationType.EPISODE);
            episodeRowListener.onEpisodeSelectedForPlayback(episodeDetails, playContext);
            return;
        }
        Log.e("EpisodeRowView", "No EpisodeRowListener provided: " + this.getContext());
    }
    
    public void setChecked(final boolean checked) {
        final boolean b = false;
        this.checked = checked;
        boolean b2;
        if (checked && this.isCheckable) {
            b2 = true;
        }
        else {
            b2 = false;
        }
        if (this.synopsis != null) {
            final TextView synopsis = this.synopsis;
            int visibility;
            if (b2) {
                visibility = 0;
            }
            else {
                visibility = 8;
            }
            synopsis.setVisibility(visibility);
        }
        if (this.episodeTime != null) {
            final TextView episodeTime = this.episodeTime;
            int visibility2;
            if (b2) {
                visibility2 = (b ? 1 : 0);
            }
            else {
                visibility2 = 8;
            }
            episodeTime.setVisibility(visibility2);
        }
        this.updateProgressBar();
    }
    
    protected abstract void setupPlayButton(final EpisodeDetails p0);
    
    public void toggle() {
        this.setChecked(!this.checked);
    }
    
    public void update(final EpisodeDetails episodeDetails, final boolean isCurrentEpisode) {
        if (episodeDetails == null) {
            return;
        }
        this.isNSRE = episodeDetails.episodeIsNSRE();
        this.isCurrentEpisode = isCurrentEpisode;
        this.setContentDescription((CharSequence)String.format(this.getResources().getString(2131296442), episodeDetails.getEpisodeNumber(), episodeDetails.getTitle(), episodeDetails.getSynopsis(), TimeUtils.convertSecondsToMinutes(episodeDetails.getPlayable().getRuntime())));
        if (episodeDetails.isAvailableToStream() && !this.isNSRE && this.episodeNumber != null) {
            this.episodeNumber.setText((CharSequence)createEpisodeNumber(episodeDetails, this.getContext()));
            KidsUtils.setSecondaryTextColorIfApplicable(this.episodeNumber);
        }
        if (this.title != null) {
            this.title.setText((CharSequence)createTitleText(episodeDetails, this.getContext()));
            final TextView title = this.title;
            final Resources resources = this.getResources();
            int n;
            if (episodeDetails.isAvailableToStream()) {
                n = 2131755208;
            }
            else {
                n = 2131755224;
            }
            title.setTextColor(resources.getColor(n));
            this.title.setClickable(false);
            KidsUtils.setTextColorIfApplicable(this.title);
        }
        if (this.episodeBadge != null) {
            LoMoUtils.toggleEpisodeBadge((List)episodeDetails.getEpisodeBadges(), this.episodeBadge);
            KidsUtils.setTextColorIfApplicable(this.episodeBadge);
        }
        if (this.episodeTime != null) {
            if (episodeDetails.isNSRE()) {
                this.episodeTime.setText((CharSequence)episodeDetails.getAvailabilityDateMessage());
                this.episodeTime.setVisibility(0);
            }
            else {
                if (episodeDetails.isAvailableToStream() && this.episodeTime != null) {
                    this.episodeTime.setText((CharSequence)createEpisodeDuration(episodeDetails, this.getContext()));
                }
                this.episodeTime.setVisibility(8);
            }
            KidsUtils.setSecondaryTextColorIfApplicable(this.episodeTime);
        }
        this.updateSynopsis(episodeDetails);
        this.updateBookmark(episodeDetails);
        this.setupPlayButton(episodeDetails);
        this.updateDownloadButton(episodeDetails.getPlayable());
        this.setChecked(false);
        this.updateCheckability(episodeDetails);
    }
    
    protected void updateBookmark(final EpisodeDetails episodeDetails) {
        int progressVal = 0;
        final int max = Math.max(0, episodeDetails.getBookmarkPosition());
        if (episodeDetails.getPlayable().getRuntime() > 0) {
            progressVal = max * 100 / episodeDetails.getPlayable().getRuntime();
        }
        this.progressVal = progressVal;
    }
    
    protected abstract void updateDownloadButton(final Playable p0);
    
    protected void updateProgressBar() {
        if (this.progressVal <= 0) {
            this.progressBar.setVisibility(8);
        }
        else {
            this.progressBar.setVisibility(0);
            if (this.isCurrentEpisode) {
                this.progressBar.setProgress(this.progressVal);
                this.progressBar.setSecondaryProgress(0);
            }
            else {
                this.progressBar.setProgress(0);
                this.progressBar.setSecondaryProgress(this.progressVal);
            }
        }
        if (BrowseExperience.isLightTheme()) {
            this.progressBar.setProgressDrawable(ContextCompat.getDrawable(this.getContext(), 2130838018));
        }
    }
    
    protected void updateSynopsis(final EpisodeDetails episodeDetails) {
        if (this.synopsis == null) {
            return;
        }
        this.synopsis.setText((CharSequence)episodeDetails.getSynopsis());
        this.synopsis.setVisibility(this.getDefaultSynopsisVisibility());
        KidsUtils.setSecondaryTextColorIfApplicable(this.synopsis);
    }
}
