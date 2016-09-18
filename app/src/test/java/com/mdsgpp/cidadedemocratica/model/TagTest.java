package com.mdsgpp.cidadedemocratica.model;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * Created by guilherme on 15/09/16.
 */
public class TagTest extends AndroidTestCase {

    Tag tag = newTag();
    final long tagId = 100;
    final long tagRelevance = 100;
    final String tagName = "name";
    final long tagNumberOfAppearances = 10;

    @Test
    public void testGetName() {
        assertNotNull(tag.getName());
        assertEquals(tag.getName(), tagName);
    }

    @Test
    public void testGetNumberOfAppearances() {
        assertEquals(tag.getNumberOfAppearances(), tagNumberOfAppearances);
    }

    @Test
    public void testToString() {
        assertEquals(tag.toString(), tag.getName());
    }

    @Test
    public void testGetRelevance() {
        assertEquals(tag.getRelevance(), tagRelevance);
    }

    private Tag newTag() {
        return new Tag(tagId, tagName, tagNumberOfAppearances, tagRelevance);
    }
}
