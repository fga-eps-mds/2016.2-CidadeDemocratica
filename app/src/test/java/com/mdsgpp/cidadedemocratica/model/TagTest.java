package com.mdsgpp.cidadedemocratica.model;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * Created by guilherme on 15/09/16.
 */
public class TagTest extends AndroidTestCase {
    Tag tags;

    @Override
    public void setUp() {
        tags = newTag();
    }

    @Test
    public void testGetName() {
        assertTrue(this.tags.getName().equals("name"));
    }

    @Test
    public void testGetNumberOfAppearances() {
        assertEquals(17, this.tags.getNumberOfAppearances());
    }

    @Test
    public void testToString() {
        assertTrue(this.tags.toString().equals("name"));
    }

    private Tag newTag() {
        return new Tag(0, "name", 17, 0);
    }
}
