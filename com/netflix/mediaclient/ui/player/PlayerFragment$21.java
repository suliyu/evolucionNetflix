// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.player;

import com.netflix.mediaclient.util.LanguageChoice;
import com.netflix.mediaclient.media.AudioSubtitleDefaultOrderInfo;
import com.netflix.mediaclient.media.AudioSource;
import com.netflix.mediaclient.media.Subtitle;
import com.netflix.mediaclient.ui.home.HomeActivity;
import com.netflix.mediaclient.util.log.UIViewLogUtils;
import com.netflix.mediaclient.servicemgr.UIViewLogging$UIViewCommandName;
import com.netflix.mediaclient.service.player.subtitles.SubtitleScreen;
import com.netflix.mediaclient.service.logging.client.model.DeepErrorElement;
import java.util.List;
import java.io.Serializable;
import android.media.AudioManager;
import com.netflix.mediaclient.media.Watermark;
import com.netflix.mediaclient.servicemgr.IPlayer$PlaybackError;
import android.view.MenuItem;
import com.netflix.mediaclient.util.NflxProtocolUtils;
import android.view.Surface;
import android.widget.FrameLayout;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.support.v4.media.session.MediaSessionCompat$Callback;
import android.content.res.Configuration;
import com.netflix.mediaclient.ui.verifyplay.PinVerifier;
import com.netflix.mediaclient.ui.coppola.details.CoppolaDetailsActivity;
import android.content.Intent;
import com.netflix.mediaclient.servicemgr.UserActionLogging$CommandName;
import com.netflix.mediaclient.util.MdxUtils;
import com.netflix.mediaclient.util.DeviceUtils;
import com.netflix.mediaclient.ui.bandwidthsetting.BandwidthUtility;
import com.netflix.mediaclient.service.net.LogMobileType;
import android.view.Window;
import com.netflix.mediaclient.util.l10n.LocalizationUtils;
import com.netflix.mediaclient.ui.mdx.MdxTargetSelection;
import android.util.Pair;
import com.netflix.mediaclient.ui.verifyplay.PinAndAgeVerifier;
import com.netflix.mediaclient.ui.verifyplay.PlayVerifierVault;
import com.netflix.mediaclient.ui.verifyplay.PlayVerifierVault$RequestedBy;
import android.annotation.SuppressLint;
import android.view.TextureView;
import android.content.IntentFilter;
import com.netflix.mediaclient.ui.details.DPPrefetchABTestUtils;
import android.support.v7.widget.Toolbar;
import com.netflix.mediaclient.servicemgr.ISubtitleDef$SubtitleProfile;
import com.netflix.mediaclient.service.configuration.SubtitleConfiguration;
import com.netflix.mediaclient.media.PlayoutMetadata;
import com.netflix.mediaclient.service.configuration.drm.DrmManagerRegistry;
import com.netflix.mediaclient.ui.player.subtitles.SubtitleManager;
import com.netflix.mediaclient.ui.player.subtitles.SubtitleManagerFactory;
import android.os.Debug;
import com.netflix.mediaclient.util.AndroidManifestUtils;
import com.netflix.mediaclient.util.PreferenceUtils;
import android.app.Activity;
import android.os.SystemClock;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import android.app.DialogFragment;
import com.netflix.mediaclient.ui.details.EpisodesFrag;
import com.netflix.mediaclient.ui.barker.details.BarkerShowDetailsFrag;
import com.netflix.mediaclient.ui.barker.details.BarkerHelper;
import com.netflix.mediaclient.util.AndroidUtils;
import android.view.ViewGroup$LayoutParams;
import android.widget.LinearLayout$LayoutParams;
import com.netflix.mediaclient.ui.experience.BrowseExperience;
import com.netflix.mediaclient.servicemgr.ManagerCallback;
import com.netflix.mediaclient.servicemgr.interface_.VideoType;
import com.netflix.mediaclient.util.StringUtils;
import com.netflix.mediaclient.android.app.NetflixImmutableStatus;
import com.netflix.mediaclient.servicemgr.interface_.offline.realm.RealmVideoDetails;
import com.netflix.mediaclient.android.app.CommonStatus;
import com.netflix.mediaclient.servicemgr.interface_.offline.realm.RealmUtils;
import com.netflix.mediaclient.ui.common.PlayContextImp;
import com.netflix.mediaclient.service.logging.client.model.UIError;
import com.netflix.mediaclient.util.log.UserActionLogUtils;
import com.netflix.mediaclient.util.StatusUtils;
import com.netflix.mediaclient.util.log.ConsolidatedLoggingUtils;
import com.netflix.mediaclient.servicemgr.IClientLogging$ModalView;
import com.netflix.mediaclient.util.log.OfflineLogUtils;
import com.netflix.mediaclient.servicemgr.IClientLogging$CompletionReason;
import java.util.Iterator;
import com.netflix.mediaclient.util.ThreadUtils;
import com.netflix.mediaclient.ui.common.PlaybackLauncher;
import com.netflix.mediaclient.media.PlayerType;
import com.netflix.mediaclient.servicemgr.interface_.Playable;
import com.netflix.mediaclient.servicemgr.interface_.PlaybackBookmark;
import com.netflix.mediaclient.util.TimeUtils;
import com.netflix.mediaclient.media.BookmarkStore;
import android.widget.Toast;
import com.netflix.mediaclient.event.nrdp.media.NccpActionId;
import android.view.View;
import android.view.KeyEvent;
import com.netflix.mediaclient.ui.common.PlayLocationType;
import com.netflix.mediaclient.android.widget.AlertDialogFactory;
import com.netflix.mediaclient.util.ConnectivityUtils;
import com.netflix.mediaclient.android.widget.UpdateDialog$Builder;
import com.netflix.mediaclient.servicemgr.interface_.offline.WatchState;
import org.json.JSONException;
import org.json.JSONObject;
import com.netflix.mediaclient.util.Coppola1Utils;
import com.netflix.mediaclient.service.configuration.PlayerTypeFactory;
import com.netflix.mediaclient.servicemgr.interface_.offline.OfflinePlayableViewData;
import com.netflix.mediaclient.servicemgr.interface_.offline.DownloadState;
import com.netflix.mediaclient.event.nrdp.media.Error;
import com.netflix.mediaclient.servicemgr.ServiceManager;
import com.netflix.mediaclient.android.widget.AlertDialogFactory$AlertDialogDescriptor;
import com.netflix.mediaclient.service.error.ErrorDescriptor;
import com.netflix.mediaclient.service.logging.client.model.ActionOnUIError;
import com.netflix.mediaclient.service.logging.client.model.RootCause;
import com.netflix.mediaclient.event.nrdp.media.MediaEvent;
import com.netflix.mediaclient.ui.player.error.PlayerErrorDialogDescriptorFactory;
import com.netflix.mediaclient.event.nrdp.media.NccpError;
import com.netflix.mediaclient.android.app.Status;
import com.netflix.mediaclient.servicemgr.interface_.details.VideoDetails;
import com.netflix.mediaclient.android.widget.TappableSurfaceView$TapListener;
import com.netflix.mediaclient.android.widget.TappableSurfaceView$SurfaceMeasureListener;
import android.view.SurfaceHolder$Callback;
import java.util.ArrayList;
import com.netflix.mediaclient.media.JPlayer.SecondSurface;
import com.netflix.mediaclient.service.player.subtitles.SafeSubtitleManager;
import android.view.ViewGroup;
import android.view.Menu;
import com.netflix.mediaclient.servicemgr.IPlayer;
import com.netflix.mediaclient.servicemgr.IPlayer$PlaybackType;
import android.content.BroadcastReceiver;
import android.os.Handler;
import com.netflix.mediaclient.android.fragment.NetflixDialogFrag;
import com.netflix.mediaclient.android.fragment.NetflixDialogFrag$DialogCanceledListener;
import com.netflix.mediaclient.service.ServiceAgent$ConfigurationAgentInterface;
import com.netflix.mediaclient.media.Language;
import android.view.View$OnClickListener;
import android.widget.SeekBar$OnSeekBarChangeListener;
import com.netflix.mediaclient.ui.details.DetailsActivity$Reloader;
import com.netflix.mediaclient.ui.common.PlayContextProvider;
import com.netflix.mediaclient.servicemgr.IPlayer$PlayerListener;
import com.netflix.mediaclient.media.JPlayer.JPlayer$JplayerListener;
import com.netflix.mediaclient.android.widget.ErrorWrapper$Callback;
import com.netflix.mediaclient.android.fragment.NetflixDialogFrag$DialogCanceledListenerProvider;
import android.media.AudioManager$OnAudioFocusChangeListener;
import com.netflix.mediaclient.android.fragment.NetflixFrag;
import android.os.Parcelable;
import android.os.Bundle;
import com.netflix.mediaclient.service.logging.error.ErrorLoggingManager;
import com.netflix.mediaclient.service.webclient.model.leafs.ABTestConfig$Cell;
import android.content.Context;
import com.netflix.mediaclient.service.configuration.PersistentConfig;
import com.netflix.mediaclient.servicemgr.Asset;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.ui.common.PlayContext;
import com.netflix.mediaclient.servicemgr.interface_.details.EpisodeDetails;
import com.netflix.mediaclient.ui.details.AbsEpisodeView$EpisodeRowListener;

class PlayerFragment$21 implements AbsEpisodeView$EpisodeRowListener
{
    final /* synthetic */ PlayerFragment this$0;
    
    PlayerFragment$21(final PlayerFragment this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public void onEpisodeSelectedForPlayback(final EpisodeDetails episodeDetails, final PlayContext playContext) {
        if (this.this$0.isActivityValid()) {
            if (Log.isLoggable()) {
                Log.d("PlayerFragment", "Start playback from episode selector " + episodeDetails);
            }
            if (this.this$0.isCoppolaWithOldPlayer()) {
                this.this$0.mAsset = Asset.create(episodeDetails.getPlayable(), playContext, false);
                this.this$0.launchPlayback();
            }
            if (this.this$0.isCoppolaPlayback() && !this.this$0.handleConnectivityCheck()) {
                Log.w("PlayerFragment", "Playback is disabled for current network");
                return;
            }
            this.this$0.removeDialogFragmentIfShown();
            if (PersistentConfig.getCoppola1ABTestCell((Context)this.this$0.getActivity()).ordinal() != ABTestConfig$Cell.CELL_THREE.ordinal() && this.this$0.mAsset != null && this.this$0.mAsset.getPlayableId() != null && this.this$0.mAsset.getPlayableId().equals(episodeDetails.getPlayable().getPlayableId())) {
                Log.d("PlayerFragment", "Request to play same episode, do nothing");
                this.this$0.startScreenUpdateTask();
                this.this$0.doUnpause();
                return;
            }
            if (this.this$0.mScreen == null) {
                Log.w("PlayerFragment", "SPY-8951 - mScreen is null inside onEpisodeSelectedForPlayback. Ignoring playback.");
                ErrorLoggingManager.logHandledException("SPY-8951 - mScreen is null inside onEpisodeSelectedForPlayback. Ignoring playback.");
                return;
            }
            if (this.this$0.isCoppolaPlayback() && !this.this$0.mState.videoLoaded) {
                this.this$0.notifyOthersOfPlayStop();
                this.this$0.mAsset = Asset.create(episodeDetails.getPlayable(), playContext, false);
                this.this$0.launchPlayback();
                this.this$0.mScreen.getPostPlay().reset();
                this.this$0.mScreen.getPostPlay().hide();
                if (this.this$0.mPlaybackStateListener != null) {
                    this.this$0.mPlaybackStateListener.onPlaybackRestarting();
                }
            }
            else if (!this.this$0.willstartPlaybackInAnotherActivity(episodeDetails.getPlayable().getPlayableId(), playContext)) {
                if (PersistentConfig.getCoppola1ABTestCell((Context)this.this$0.getActivity()).ordinal() != ABTestConfig$Cell.CELL_THREE.ordinal()) {
                    this.this$0.allowCoppolaAutoplay = true;
                }
                this.this$0.doUnpause();
                this.this$0.resetCurrentPlayback();
                this.this$0.notifyOthersOfPlayStop();
                this.this$0.notifyPlayPauseToListener(true);
                this.this$0.mScreen.changeActionState(false);
                this.this$0.mScreen.setSeekbarTrackingEnabled(false);
                this.this$0.setCoppolaSeekbarEnabled(false);
                this.this$0.mAsset = Asset.create(episodeDetails.getPlayable(), playContext, false);
                this.this$0.mExternalBundle = new Bundle();
                this.this$0.mExternalBundle.putParcelable("AssetExtra", (Parcelable)this.this$0.mAsset);
                this.this$0.continueInitAfterPlayVerify();
                final PostPlay postPlay = this.this$0.mScreen.getPostPlay();
                if (postPlay.isInPostPlay()) {
                    postPlay.postPlayDismissed();
                }
                postPlay.reset();
                postPlay.hide();
            }
        }
    }
}