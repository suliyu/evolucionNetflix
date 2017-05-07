// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.ui.login;

import android.net.Uri;
import android.view.View$OnClickListener;
import android.view.KeyEvent;
import android.widget.TextView$OnEditorActionListener;
import com.netflix.mediaclient.service.logging.client.model.UIError;
import com.netflix.mediaclient.util.LogUtils;
import com.netflix.mediaclient.util.AndroidUtils;
import android.os.Bundle;
import com.netflix.mediaclient.ui.profiles.ProfileSelectionActivity;
import com.netflix.mediaclient.servicemgr.IClientLogging;
import com.netflix.mediaclient.android.widget.AlertDialogFactory;
import android.app.Activity;
import com.netflix.mediaclient.ui.ServiceErrorsHandler;
import com.netflix.mediaclient.util.StringUtils;
import android.content.Intent;
import com.netflix.mediaclient.servicemgr.ServiceManager;
import com.netflix.mediaclient.servicemgr.ManagerCallback;
import com.netflix.mediaclient.util.DeviceUtils;
import android.content.Context;
import com.netflix.mediaclient.util.ConnectivityUtils;
import com.netflix.mediaclient.Log;
import android.os.Handler;
import android.widget.TextView;
import com.netflix.mediaclient.servicemgr.SimpleManagerCallback;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AccountActivity
{
    private static final int MIN_PASSWORD_CHARS = 4;
    private static final String TAG = "LoginActivity";
    private EditText emailView;
    private View loginButton;
    private View loginForm;
    private final SimpleManagerCallback loginQueryCallback;
    private EditText passwordView;
    private View statusGroup;
    private TextView statusMessageView;
    
    public LoginActivity() {
        this.loginQueryCallback = new SimpleManagerCallback() {
            @Override
            public void onLoginComplete(final int n, final String s) {
                LoginActivity.this.runOnUiThread((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        LoginActivity.this.handleLoginComplete(n, s);
                    }
                });
            }
        };
    }
    
    private void attemptLogin() {
        this.emailView.setError((CharSequence)null);
        this.passwordView.setError((CharSequence)null);
        final String string = this.emailView.getText().toString();
        final String string2 = this.passwordView.getText().toString();
        boolean b = false;
        Object o = null;
        if (this.passwordIsInvalid(string2)) {
            this.passwordView.setError((CharSequence)this.getString(2131493148));
            o = this.passwordView;
            b = true;
        }
        if (this.emailIsInvalid(string)) {
            this.emailView.setError((CharSequence)this.getString(2131493147));
            o = this.emailView;
            b = true;
        }
        if (b) {
            Log.i("LoginActivity", "There was an error - skipping login and showing error msg");
            ((View)o).requestFocus();
            return;
        }
        final ServiceManager serviceManager = this.getServiceManager();
        if (!ConnectivityUtils.isConnectedOrConnecting((Context)this) || serviceManager == null || !serviceManager.isReady()) {
            this.noConnectivityWarning();
            return;
        }
        final int screenSensorOrientation = DeviceUtils.getScreenSensorOrientation((Context)this);
        Log.i("LoginActivity", "Locking orientation to: " + screenSensorOrientation);
        this.setRequestedOrientation(screenSensorOrientation);
        this.statusMessageView.setText(2131493146);
        this.showProgress(true);
        serviceManager.loginUser(string, string2, this.loginQueryCallback);
    }
    
    public static Intent createStartIntent(final Context context) {
        return new Intent(context, (Class)LoginActivity.class);
    }
    
    private boolean emailIsInvalid(final String s) {
        return StringUtils.isEmpty(s);
    }
    
    private void handleAccountActivateFailures(final int n, String string) {
        if (string == null) {
            string = "";
        }
        switch (n) {
            default: {
                string = this.getString(2131493207) + " (" + n + ")";
                this.showDebugToast(2131493158);
                this.provideDialog(string);
                break;
            }
            case -201: {
                this.provideDialog(this.getString(2131493204) + " (" + n + ")");
                break;
            }
            case -202: {
                this.provideDialog(string);
                break;
            }
            case -207:
            case -203: {
                this.passwordView.setError((CharSequence)this.getString(2131493149));
                break;
            }
            case -208: {
                this.showDebugToast(this.getString(2131493156) + ": " + n + " " + string);
                ServiceErrorsHandler.handleManagerResponse(this, -5);
                break;
            }
            case -211:
            case -210:
            case -209:
            case -206:
            case -205:
            case -204:
            case -200: {
                this.provideDialog(this.getString(2131493203) + " (" + n + ")");
                break;
            }
            case -3: {
                this.provideDialog(this.getString(2131493206) + " (" + n + ")");
                break;
            }
        }
        this.showProgress(false);
    }
    
    private void handleLoginComplete(final int n, final String s) {
        Log.d("LoginActivity", "Login Complete - Status: " + n + " msg:" + s);
        this.setRequestedOrientation(-1);
        if (n == 0 || n == -41) {
            this.showDebugToast(2131493153);
            return;
        }
        this.handleAccountActivateFailures(n, s);
    }
    
    private void noConnectivityWarning() {
        this.runOnUiThread((Runnable)new Runnable() {
            @Override
            public void run() {
                LoginActivity.this.displayDialog(AlertDialogFactory.createDialog((Context)LoginActivity.this, LoginActivity.this.handler, new AlertDialogFactory.AlertDialogDescriptor(null, LoginActivity.this.getString(2131493202), LoginActivity.this.getString(17039370), null)));
            }
        });
    }
    
    private boolean passwordIsInvalid(final String s) {
        return StringUtils.isEmpty(s) || s.length() < 4;
    }
    
    private void provideDialog(final String s) {
        this.displayDialog(AlertDialogFactory.createDialog((Context)this, this.handler, new AlertDialogFactory.AlertDialogDescriptor(null, s, this.getString(17039370), null)));
    }
    
    private void showDebugToast(final int n) {
        this.showDebugToast(this.getString(n));
    }
    
    private void showDebugToast(final String s) {
    }
    
    private void showProgress(final boolean b) {
        final int n = 8;
        boolean enabled = false;
        final View statusGroup = this.statusGroup;
        int visibility;
        if (b) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        statusGroup.setVisibility(visibility);
        final View loginForm = this.loginForm;
        int visibility2;
        if (b) {
            visibility2 = n;
        }
        else {
            visibility2 = 0;
        }
        loginForm.setVisibility(visibility2);
        final View loginButton = this.loginButton;
        if (!b) {
            enabled = true;
        }
        loginButton.setEnabled(enabled);
    }
    
    @Override
    public IClientLogging.ModalView getUiScreen() {
        return IClientLogging.ModalView.login;
    }
    
    @Override
    protected void handleProfileReadyToSelect() {
        Log.i("LoginActivity", "New profile requested - starting profile selection activity...");
        this.startActivity(ProfileSelectionActivity.createStartIntent(this));
        AccountActivity.finishAllAccountActivities((Context)this);
    }
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        AndroidUtils.logIntent("LoginActivity", this.getIntent());
        this.setContentView(2130903076);
        LogUtils.reportLoginActionEnded((Context)this, IClientLogging.CompletionReason.success, null);
        (this.emailView = (EditText)this.findViewById(2131099774)).requestFocus();
        (this.passwordView = (EditText)this.findViewById(2131099775)).setOnEditorActionListener((TextView$OnEditorActionListener)new TextView$OnEditorActionListener() {
            private boolean isLoginId(final int n) {
                return n == 2131099776 || n == 0 || n == 6;
            }
            
            public boolean onEditorAction(final TextView textView, final int n, final KeyEvent keyEvent) {
                Log.v("LoginActivity", "Editor action: " + n + ", keyevent: " + keyEvent);
                if (this.isLoginId(n)) {
                    LoginActivity.this.attemptLogin();
                    return true;
                }
                return false;
            }
        });
        this.loginForm = this.findViewById(2131099773);
        this.loginButton = this.findViewById(2131099771);
        this.statusGroup = this.findViewById(2131099777);
        this.statusMessageView = (TextView)this.findViewById(2131099778);
        this.findViewById(2131099771).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                LoginActivity.this.attemptLogin();
            }
        });
        this.findViewById(2131099772).setOnClickListener((View$OnClickListener)new View$OnClickListener() {
            public void onClick(final View view) {
                LoginActivity.this.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse("http://signup.netflix.com/loginhelp")));
            }
        });
    }
}
