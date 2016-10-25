package com.mdsgpp.cidadedemocratica.model;

/**
 * Created by andreanmasiro on 10/25/16.
 */

public class Tagging {

    private int tagId = 0;
    private int taggableId = 0;
    private int taggerId = 0;

    public Tagging(int tagId, int taggableId, int taggerId) {
        this.tagId = tagId;
        this.taggableId = taggableId;
        this.taggerId = taggerId;
    }
}
