// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.service.logging;

public class UserData
{
    public String accountCountry;
    public String currentProfileUserId;
    public String deviceCategory;
    public String esn;
    public String geoLocationCountry;
    public String languages;
    public String netflixId;
    public String secureNetflixId;
    public String userId;
    
    @Override
    public String toString() {
        return "UserData [userId=" + this.userId + ", currentProfileUserId=" + this.currentProfileUserId + ", netflixId=" + this.netflixId + ", secureNetflixId=" + this.secureNetflixId + ", esn=" + this.esn + ", deviceCategory=" + this.deviceCategory + ", accountCountry=" + this.accountCountry + ", geoLocationCountry=" + this.geoLocationCountry + ", languages=" + this.languages + "]";
    }
}
