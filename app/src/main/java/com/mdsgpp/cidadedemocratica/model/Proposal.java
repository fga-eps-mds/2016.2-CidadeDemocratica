package com.mdsgpp.cidadedemocratica.model;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Proposal implements Comparable<Proposal> {

    private long id = 0;
    private String title = "";
    private String content = "";
    private long relevance = 0;
    private long userId = 0;
    private String slug ="";
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    public Proposal(long id, String title, String content, long relevance, long userId, String slug) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.relevance = relevance;
        this.slug = slug;
        this.userId = userId;
    }

    public long getId() {
        return this.id;
    }

    public long getUserId() {
        return this.userId;
    }

    public String getSlug() {
        return this.slug;
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

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int compareTo(Proposal proposal) {
        if (this.getRelevance() > proposal.getRelevance()) {
            return -1;
        } else if (this.getRelevance() < proposal.getRelevance()) {
            return 1;
        } else {
            return 0;
        }
    }
}