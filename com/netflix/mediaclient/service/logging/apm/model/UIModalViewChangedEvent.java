// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.logging.apm.model;

import com.netflix.mediaclient.servicemgr.IClientLogging;
import org.json.JSONException;
import com.netflix.mediaclient.util.JsonUtils;
import org.json.JSONObject;
import com.netflix.mediaclient.service.logging.client.model.DiscreteEvent;

public final class UIModalViewChangedEvent extends DiscreteEvent
{
    protected static final String CATEGORY = "uiQOE";
    protected static final String NAME = "uiModalViewChanged";
    public static final String ORIENTATION = "orientation";
    private Orientation orientation;
    
    public UIModalViewChangedEvent(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        jsonObject = JsonUtils.getJSONObject(jsonObject, "data", null);
        if (jsonObject != null) {
            final String string = JsonUtils.getString(jsonObject, "orientation", null);
            if (string == null) {
                this.orientation = Orientation.portrait;
                return;
            }
            this.orientation = Orientation.valueOf(string);
        }
    }
    
    public UIModalViewChangedEvent(final boolean b, final IClientLogging.ModalView modalView) {
        Orientation orientation;
        if (b) {
            orientation = Orientation.portrait;
        }
        else {
            orientation = Orientation.landscape;
        }
        this.orientation = orientation;
        this.modalView = modalView;
        this.category = "uiQOE";
        this.name = "uiModalViewChanged";
    }
    
    @Override
    protected JSONObject getData() throws JSONException {
        JSONObject data;
        if ((data = super.getData()) == null) {
            data = new JSONObject();
        }
        data.put("orientation", (Object)this.orientation.name());
        return data;
    }
    
    public Orientation getOrientation() {
        return this.orientation;
    }
}
