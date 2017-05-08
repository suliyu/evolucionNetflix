// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.mdx;

import android.support.v7.widget.RecyclerView$LayoutManager;
import android.support.v7.widget.RecyclerView$Adapter;
import com.netflix.mediaclient.ui.common.SimilarItemsGridViewAdapter;
import com.netflix.mediaclient.util.ThreadUtils;
import com.netflix.mediaclient.android.app.Status;
import com.netflix.mediaclient.ui.common.PlaybackLauncher;
import android.view.LayoutInflater;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import com.netflix.mediaclient.util.ViewUtils;
import android.app.FragmentTransaction;
import com.netflix.mediaclient.ui.details.RoleDetailsFrag;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import com.netflix.mediaclient.servicemgr.interface_.Playable;
import com.netflix.mediaclient.ui.common.PlayContext;
import com.netflix.mediaclient.servicemgr.ServiceManager;
import com.netflix.falkor.CachedModelProxy$CmpTaskDetails;
import com.netflix.falkor.CachedModelProxy$FetchTurboCollectionVideosTask;
import android.view.KeyEvent;
import com.netflix.mediaclient.servicemgr.ManagerCallback;
import com.netflix.mediaclient.servicemgr.interface_.details.EpisodeDetails;
import com.netflix.mediaclient.servicemgr.ServiceManagerUtils;
import com.netflix.mediaclient.servicemgr.interface_.VideoType;
import com.netflix.mediaclient.service.configuration.PersistentConfig;
import android.support.v7.widget.RecyclerView$ItemDecoration;
import com.netflix.mediaclient.util.ItemDecorationUniformPadding;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.app.Fragment;
import com.netflix.mediaclient.util.AndroidUtils;
import android.app.FragmentManager;
import android.app.DialogFragment;
import com.netflix.mediaclient.Log;
import com.netflix.mediaclient.util.DeviceUtils;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.netflix.mediaclient.android.widget.RecyclerViewHeaderAdapter;
import com.netflix.mediaclient.service.mdx.MdxKeyEventHandler;
import com.netflix.mediaclient.service.mdx.MdxKeyEventHandler$MdxKeyEventCallbacks;
import com.netflix.mediaclient.service.mdx.MdxErrorHandler;
import com.netflix.mediaclient.ui.common.LanguageSelector$LanguageSelectorCallback;
import com.netflix.mediaclient.ui.common.LanguageSelector;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;
import com.netflix.mediaclient.servicemgr.IMdx;
import com.netflix.mediaclient.servicemgr.interface_.details.VideoDetails;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import com.netflix.mediaclient.util.MdxUtils$MdxTargetSelectionDialogInterface;
import com.netflix.mediaclient.ui.details.NetflixRatingBar$RatingBarDataProvider;
import com.netflix.mediaclient.ui.details.IHandleBackPress;
import com.netflix.mediaclient.ui.details.AbsEpisodeView$EpisodeRowListener;
import com.netflix.mediaclient.android.fragment.NetflixFrag;
import android.view.View;
import android.content.Context;
import com.netflix.mediaclient.ui.barker.BarkerUtils;
import com.netflix.mediaclient.android.widget.RecyclerViewHeaderAdapter$IViewCreator;

class MiniPlayerControlsFrag$2 implements RecyclerViewHeaderAdapter$IViewCreator
{
    final /* synthetic */ MiniPlayerControlsFrag this$0;
    
    MiniPlayerControlsFrag$2(final MiniPlayerControlsFrag this$0) {
        this.this$0 = this$0;
        this.calculateViewDimensions();
    }
    
    private void calculateViewDimensions() {
        this.this$0.relatedViewWidth = (BarkerUtils.getDetailsPageContentWidth((Context)this.this$0.getActivity()) - this.this$0.relatedRecyclerView.getPaddingLeft() - this.this$0.relatedRecyclerView.getPaddingRight() - (this.this$0.numColumns + 1) * this.this$0.getActivity().getResources().getDimensionPixelOffset(2131427411)) / this.this$0.numColumns;
        this.this$0.relatedViewHeight = (int)(this.this$0.relatedViewWidth * 1.43f);
    }
    
    @Override
    public View createItemView() {
        return (View)new MiniPlayerControlsFrag$MementoRelatedView(this.this$0, this.this$0.relatedRecyclerView.getContext());
    }
}
