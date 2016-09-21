package com.mdsgpp.cidadedemocratica.model;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * Created by guilherme on 21/09/16.
 */
public class TagListRowTest extends AndroidTestCase {

    @Test
    public void testBuider(){
        TagListRow tagListRow = new TagListRow();
        assertTrue(tagListRow != null);
    }

}
