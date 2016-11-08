package com.mdsgpp.cidadedemocratica.persistence;

import android.test.AndroidTestCase;


import com.mdsgpp.cidadedemocratica.model.Entity;
import com.mdsgpp.cidadedemocratica.model.Tag;

import org.junit.Test;

/**
 * Created by andreanmasiro on 9/15/16.
 */
public class DataUpdateListenerTest extends AndroidTestCase {

    private EntityContainer<Tag> dataContainer = EntityContainer.getInstance(Tag.class);

    @Test
    public void testUpdateData() {

        final boolean[] updated = {false};
        dataContainer.setDataUpdateListener(new DataUpdateListener() {
            @Override
            public void dataUpdated(Class<? extends Entity> entityType) {
                updated[0] = true;
            }
        });

        Tag tag = newTag();
        dataContainer.add(tag);

        assertTrue(updated[0]);
    }

    private Tag newTag() {
        return new Tag(0, "name", 0, 0);
    }
}
