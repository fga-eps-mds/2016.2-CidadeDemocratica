package com.mdsgpp.cidadedemocratica.persistence;

/**
 * Created by andreanmasiro on 9/9/16.
 */
public interface DataUpdateListener {

    public void tagsUpdated();
    public void proposalsUpdated();
    public void usersUpdated();
    public void taggingsUpdated();
}
