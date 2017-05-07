// 
// Decompiled by Procyon v0.5.30
// 

package com.netflix.mediaclient.servicemgr;

import java.util.List;

public interface KidsCharacterDetails extends Playable
{
    String getBifUrl();
    
    String getCatalogIdUrl();
    
    String getCharacterId();
    
    String getCharacterName();
    
    String getCharacterStoryUrl();
    
    String getCharacterSynopsis();
    
    List<Video> getGallery();
    
    String getGalleryRequestId();
    
    int getGalleryTrackId();
    
    String getHighResolutionLandscapeBoxArtUrl();
    
    String getHighResolutionPortraitBoxArtUrl();
    
    String getHorzDispUrl();
    
    String getStoryUrl();
    
    String getSynopsis();
}