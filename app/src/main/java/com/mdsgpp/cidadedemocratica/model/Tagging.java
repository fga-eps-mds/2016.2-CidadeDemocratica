package com.mdsgpp.cidadedemocratica.model;

/**
 * Created by andreanmasiro on 10/25/16.
 */

public class Tagging {

    private long tagId = 0;
    private long taggableId = 0;
    private long taggerId = 0;

    public Tagging(long tagId, long taggableId, long taggerId) {
        this.tagId = tagId;
        this.taggableId = taggableId;
        this.taggerId = taggerId;
    }

    public long getTagId() {
        return tagId;
    }

    public long getTaggableId() {
        return taggableId;
    }

    public long getTaggerId() {
        return taggerId;
    }
}
