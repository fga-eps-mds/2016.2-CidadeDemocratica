package com.mdsgpp.cidadedemocratica.model;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * Created by luisresende on 29/11/16.
 */

public class EntityTest extends AndroidTestCase {

    @Test
    public void testCompare() {

        Entity e1 = newEntity();
        Entity e2 = newEntity();

        assertEquals(e1.compareTo(e2), e1.getId() > e2.getId() ? (e1.getId() == e2.getId() ? 0 : -1) : 1);
    }

    long id = 0;
    private Entity newEntity() {
        return new Entity(id++);
    }
}
