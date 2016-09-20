package com.mdsgpp.cidadedemocratica.model;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Proposal {

    private long id = 0;
    private String title = "";
    private String content = "";
    private long relevance = 0;
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    public Proposal(long id, String title, String content, long relevance) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.relevance = relevance;
    }

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public long getRelevance() {
        return this.relevance;
    }

    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }
}
