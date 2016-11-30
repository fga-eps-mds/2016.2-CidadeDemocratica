package com.mdsgpp.cidadedemocratica.model;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * Created by gabriel on 25/10/16.
 */
public class TaggingTest {
    private Tagging tagging = newTagging();



    private final long tagId = 1;
    private final long taggableId = 1;
    private final long taggerId = 1;

    @Test
    public  void testGetID(){
        assertEquals(tagging.getTagId(),tagId);
    }

    @Test
    public void testGetTaggableID(){
        assertEquals(tagging.getTaggableId(),taggableId);
    }

    @Test
    public void testGetTaggerID(){
        assertEquals(tagging.getTaggerId(),taggerId);
    }


    private Tagging newTagging(){
        return new Tagging(tagId,taggableId,taggerId);
    }

}
