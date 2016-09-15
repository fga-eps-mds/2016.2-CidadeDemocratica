package com.mdsgpp.cidadedemocratica;

import com.mdsgpp.cidadedemocratica.model.Tag;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by guilherme on 15/09/16.
 */
public class TagsTest {
    Tag tags;
    @Before
    public void setUp()
    {
        tags = new Tag("Tagsname",17);
    }
    @Test
    public void testGetName()
    {
        Assert.assertTrue(this.tags.getName().equals("Tagsname"));
    }
    @Test
    public void testGetNumberOfAppearances(){
        Assert.assertEquals(17,this.tags.getNumberOfAppearances());
    }
    @Test
    public void testToString(){
        Assert.assertTrue(this.tags.toString().equals("Tagsname"));
    }
}
