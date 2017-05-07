// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.location;

import android.os.Looper;
import android.location.Location;
import com.google.android.gms.internal.eg;
import android.app.PendingIntent;
import java.util.Iterator;
import com.google.android.gms.internal.go;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.content.Context;
import com.google.android.gms.internal.gn;
import com.google.android.gms.common.GooglePlayServicesClient;

public class LocationClient implements GooglePlayServicesClient
{
    public static final String KEY_LOCATION_CHANGED = "com.google.android.location.LOCATION";
    public static final String KEY_MOCK_LOCATION = "mockLocation";
    private final gn xl;
    
    public LocationClient(final Context context, final ConnectionCallbacks connectionCallbacks, final OnConnectionFailedListener onConnectionFailedListener) {
        this.xl = new gn(context, connectionCallbacks, onConnectionFailedListener, "location");
    }
    
    public static int getErrorCode(final Intent intent) {
        return intent.getIntExtra("gms_error_code", -1);
    }
    
    public static int getGeofenceTransition(final Intent intent) {
        final int intExtra = intent.getIntExtra("com.google.android.location.intent.extra.transition", -1);
        if (intExtra != -1 && (intExtra == 1 || intExtra == 2 || intExtra == 4)) {
            return intExtra;
        }
        return -1;
    }
    
    public static List<Geofence> getTriggeringGeofences(final Intent intent) {
        final ArrayList list = (ArrayList)intent.getSerializableExtra("com.google.android.location.intent.extra.geofence_list");
        if (list == null) {
            return null;
        }
        final ArrayList list2 = new ArrayList<go>(list.size());
        final Iterator<byte[]> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add(go.f(iterator.next()));
        }
        return (List<Geofence>)list2;
    }
    
    public static boolean hasError(final Intent intent) {
        return intent.hasExtra("gms_error_code");
    }
    
    public void addGeofences(final List<Geofence> list, final PendingIntent pendingIntent, final OnAddGeofencesResultListener onAddGeofencesResultListener) {
        List<go> list2 = null;
        if (list != null) {
            list2 = new ArrayList<go>();
            for (final Geofence geofence : list) {
                eg.b(geofence instanceof go, "Geofence must be created using Geofence.Builder.");
                list2.add((go)geofence);
            }
        }
        this.xl.addGeofences(list2, pendingIntent, onAddGeofencesResultListener);
    }
    
    @Override
    public void connect() {
        this.xl.connect();
    }
    
    @Override
    public void disconnect() {
        this.xl.disconnect();
    }
    
    public Location getLastLocation() {
        return this.xl.getLastLocation();
    }
    
    @Override
    public boolean isConnected() {
        return this.xl.isConnected();
    }
    
    @Override
    public boolean isConnecting() {
        return this.xl.isConnecting();
    }
    
    @Override
    public boolean isConnectionCallbacksRegistered(final ConnectionCallbacks connectionCallbacks) {
        return this.xl.isConnectionCallbacksRegistered(connectionCallbacks);
    }
    
    @Override
    public boolean isConnectionFailedListenerRegistered(final OnConnectionFailedListener onConnectionFailedListener) {
        return this.xl.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }
    
    @Override
    public void registerConnectionCallbacks(final ConnectionCallbacks connectionCallbacks) {
        this.xl.registerConnectionCallbacks(connectionCallbacks);
    }
    
    @Override
    public void registerConnectionFailedListener(final OnConnectionFailedListener onConnectionFailedListener) {
        this.xl.registerConnectionFailedListener(onConnectionFailedListener);
    }
    
    public void removeGeofences(final PendingIntent pendingIntent, final OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
        this.xl.removeGeofences(pendingIntent, onRemoveGeofencesResultListener);
    }
    
    public void removeGeofences(final List<String> list, final OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
        this.xl.removeGeofences(list, onRemoveGeofencesResultListener);
    }
    
    public void removeLocationUpdates(final PendingIntent pendingIntent) {
        this.xl.removeLocationUpdates(pendingIntent);
    }
    
    public void removeLocationUpdates(final LocationListener locationListener) {
        this.xl.removeLocationUpdates(locationListener);
    }
    
    public void requestLocationUpdates(final LocationRequest locationRequest, final PendingIntent pendingIntent) {
        this.xl.requestLocationUpdates(locationRequest, pendingIntent);
    }
    
    public void requestLocationUpdates(final LocationRequest locationRequest, final LocationListener locationListener) {
        this.xl.requestLocationUpdates(locationRequest, locationListener);
    }
    
    public void requestLocationUpdates(final LocationRequest locationRequest, final LocationListener locationListener, final Looper looper) {
        this.xl.requestLocationUpdates(locationRequest, locationListener, looper);
    }
    
    public void setMockLocation(final Location mockLocation) {
        this.xl.setMockLocation(mockLocation);
    }
    
    public void setMockMode(final boolean mockMode) {
        this.xl.setMockMode(mockMode);
    }
    
    @Override
    public void unregisterConnectionCallbacks(final ConnectionCallbacks connectionCallbacks) {
        this.xl.unregisterConnectionCallbacks(connectionCallbacks);
    }
    
    @Override
    public void unregisterConnectionFailedListener(final OnConnectionFailedListener onConnectionFailedListener) {
        this.xl.unregisterConnectionFailedListener(onConnectionFailedListener);
    }
    
    public interface OnAddGeofencesResultListener
    {
        void onAddGeofencesResult(final int p0, final String[] p1);
    }
    
    public interface OnRemoveGeofencesResultListener
    {
        void onRemoveGeofencesByPendingIntentResult(final int p0, final PendingIntent p1);
        
        void onRemoveGeofencesByRequestIdsResult(final int p0, final String[] p1);
    }
}