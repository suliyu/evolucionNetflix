// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.javabridge.invoke.mdx;

import java.util.Iterator;
import com.netflix.mediaclient.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Map;
import com.netflix.mediaclient.javabridge.invoke.BaseInvoke;

public class Init extends BaseInvoke
{
    private static final String METHOD = "init";
    private static final String PROPERTY_commandMap = "commandMap";
    private static final String PROPERTY_dialBlacklist = "dialBlacklist";
    private static final String PROPERTY_disableWebSocket = "disableWebSocket";
    private static final String PROPERTY_role = "role";
    private static final String TAG = "nf_invoke";
    private static final String TARGET = "mdx";
    
    public Init(final boolean b, final Map<String, String> map, final boolean b2, final JSONArray jsonArray) {
        super("mdx", "init");
        this.setArguments(b, map, b2, jsonArray);
    }
    
    private void setArguments(final boolean b, final Map<String, String> ex, final boolean b2, final JSONArray jsonArray) {
        while (true) {
            try {
                final JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject2 = null;
                Label_0083: {
                    try {
                        jsonObject2 = new JSONObject();
                        for (final String s : ((Map<String, Object>)ex).keySet()) {
                            jsonObject2.put(s, ((Map<String, Object>)ex).get((Object)s));
                        }
                        break Label_0083;
                    }
                    catch (JSONException ex2) {}
                    Log.e("nf_invoke", "Failed to create JSON object", (Throwable)ex);
                    return;
                }
                jsonObject.put("commandMap", (Object)jsonObject2);
                if (b) {
                    jsonObject.put("role", (Object)"CONTROLLER");
                }
                else {
                    jsonObject.put("role", (Object)"TARGET");
                }
                jsonObject.put("disableWebSocket", b2);
                jsonObject.put("dialBlacklist", (Object)jsonArray);
                this.arguments = jsonObject.toString();
            }
            catch (JSONException ex) {
                continue;
            }
            break;
        }
    }
}
