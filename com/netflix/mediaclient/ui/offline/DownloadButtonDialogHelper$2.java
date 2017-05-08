// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.offline;

import android.app.Activity;
import com.netflix.mediaclient.android.activity.NetflixActivity;
import com.netflix.mediaclient.service.logging.client.model.DataContext;
import com.netflix.mediaclient.util.log.UIViewLogUtils;
import com.netflix.mediaclient.service.logging.uiview.model.CommandEndedEvent$InputMethod;
import com.netflix.mediaclient.servicemgr.IClientLogging$ModalView;
import com.netflix.mediaclient.servicemgr.UIViewLogging$UIViewCommandName;
import android.view.MenuItem;
import android.content.Context;
import android.widget.PopupMenu$OnMenuItemClickListener;

final class DownloadButtonDialogHelper$2 implements PopupMenu$OnMenuItemClickListener
{
    final /* synthetic */ Context val$context;
    final /* synthetic */ DownloadButton val$downloadButton;
    final /* synthetic */ String val$playableId;
    
    DownloadButtonDialogHelper$2(final Context val$context, final String val$playableId, final DownloadButton val$downloadButton) {
        this.val$context = val$context;
        this.val$playableId = val$playableId;
        this.val$downloadButton = val$downloadButton;
    }
    
    public boolean onMenuItemClick(final MenuItem menuItem) {
        if (menuItem.getItemId() == 2131755984) {
            UIViewLogUtils.reportUIViewCommand(this.val$context, UIViewLogging$UIViewCommandName.PauseDownloadCommand, IClientLogging$ModalView.pauseDownloadButton, CommandEndedEvent$InputMethod.gesture, null);
            ((NetflixActivity)this.val$context).getServiceManager().getOfflineAgent().pauseDownload(this.val$playableId);
            this.val$downloadButton.setState(DownloadButton$ButtonState.PAUSED, this.val$playableId);
        }
        else if (menuItem.getItemId() == 2131755987) {
            UIViewLogUtils.reportUIViewCommand(this.val$context, UIViewLogging$UIViewCommandName.RemoveCachedVideoCommand, IClientLogging$ModalView.removeCachedVideoButton, CommandEndedEvent$InputMethod.gesture, null);
            ((NetflixActivity)this.val$context).getServiceManager().getOfflineAgent().deleteOfflinePlayable(this.val$playableId);
            DownloadButton.removePreQueued(this.val$playableId);
        }
        else if (menuItem.getItemId() == 2131755988) {
            UIViewLogUtils.reportUIViewCommand(this.val$context, UIViewLogging$UIViewCommandName.ShowMyDownloads, IClientLogging$ModalView.myDownloads, CommandEndedEvent$InputMethod.gesture, null);
            this.val$context.startActivity(OfflineActivity.showAllDownloads((Activity)this.val$context));
        }
        return true;
    }
}
