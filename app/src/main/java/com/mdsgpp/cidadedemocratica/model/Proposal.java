package com.mdsgpp.cidadedemocratica.model;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Proposal extends Entity {

    private String title = "";
    private String content = "";
    private long relevance = 0;
    private long userId = 0;
    private String slug ="";
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    public Proposal(long id, String title, String content, long relevance, long userId) {
        super(id);
        this.title = title;
        this.content = content;
        this.relevance = relevance;
        this.userId = userId;
    }

    public Proposal(long id, String title, String content, long relevance, long userId, String slug) {
        super(id);
        this.title = title;
        this.content = content;
        this.relevance = relevance;
        this.slug = slug;
        this.userId = userId;
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
    public int compareTo(Entity entity) {
        if (entity.getClass() == Proposal.class) {
            if (this.getRelevance() > ((Proposal) entity).getRelevance()) {
                return -1;
            } else if (this.getRelevance() < ((Proposal) entity).getRelevance()) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return super.compareTo(entity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == Proposal.class) {
            return ((Proposal) o).getId() == getId();
        } else {
            return super.equals(o);
        }
    }
}