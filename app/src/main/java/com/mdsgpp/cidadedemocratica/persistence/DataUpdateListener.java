package com.mdsgpp.cidadedemocratica.persistence;

import com.mdsgpp.cidadedemocratica.model.Entity;

/**
 * Created by andreanmasiro on 9/9/16.
 */
public interface DataUpdateListener {

    void dataUpdated(Class<? extends Entity> entityType);
}
