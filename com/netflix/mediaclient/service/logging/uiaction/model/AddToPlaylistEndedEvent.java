// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.logging.uiaction.model;

import org.json.JSONException;
import com.netflix.mediaclient.util.JsonUtils;
import org.json.JSONObject;
import com.netflix.mediaclient.service.logging.client.model.UIError;
import com.netflix.mediaclient.servicemgr.UserActionLogging;
import com.netflix.mediaclient.servicemgr.IClientLogging;
import com.netflix.mediaclient.service.logging.client.model.DeviceUniqueId;

public class AddToPlaylistEndedEvent extends BaseUIActionSessionEndedEvent
{
    public static final String TITLE_RANK = "titleRank";
    public static final String UIA_SESSION_NAME = "addToPlaylist";
    private int mTitleRank;
    
    public AddToPlaylistEndedEvent(final DeviceUniqueId deviceUniqueId, final long n, final IClientLogging.ModalView modalView, final UserActionLogging.CommandName commandName, final IClientLogging.CompletionReason completionReason, final UIError uiError, final int mTitleRank) {
        super("addToPlaylist", deviceUniqueId, n, modalView, commandName, completionReason, uiError);
        this.mTitleRank = mTitleRank;
    }
    
    public AddToPlaylistEndedEvent(final JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        this.mTitleRank = JsonUtils.getInt(jsonObject, "titleRank", 0);
    }
    
    @Override
    protected JSONObject getData() throws JSONException {
        final JSONObject data = super.getData();
        data.put("titleRank", this.mTitleRank);
        return data;
    }
    
    @Override
    public boolean isMemberEvent() {
        return true;
    }
}
