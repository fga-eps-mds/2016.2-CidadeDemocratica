package com.mdsgpp.cidadedemocratica.model;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class User extends Entity {

    private String name = "";
    private String description = "";
    private long proposalCount = 0;
    private long relevance = 0;
    private ArrayList<Proposal> proposals = new ArrayList<>();
    private ArrayList<Tag> mostUsedTags = new ArrayList<>();

    public User(String name, long proposalCount, long id, long relevance) {
        super(id);
        this.name = name;
        this.proposalCount = proposalCount;
        this.relevance = relevance;
    }

    public User(String name, String description, long proposalCount, long id, long relevance) {
        super(id);
        this.name = name;
        this.description = description;
        this.proposalCount = proposalCount;
        this.relevance = relevance;
    }

    public String getName() {
        return this.name;
    }

    public long getProposalCount() {
        return this.proposalCount;
    }

    public ArrayList<Proposal> getProposals() {
        return this.proposals;
    }

    public ArrayList<Tag> getMostUsedTags() {
        return this.mostUsedTags;
    }

    public void setMostUsedTags(ArrayList<Tag> mostUsedTags) {
        this.mostUsedTags = mostUsedTags;
    }

    public void setProposals(ArrayList<Proposal> proposals) {
        this.proposals = proposals;
    }

    public long getRelevance() {
        return relevance;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return  this.getName();
    }

    @Override
    public int compareTo(Entity entity) {
        if (entity.getClass() == User.class) {
            if (this.getRelevance() < ((User) entity).getRelevance()) {
                return 1;
            } else if (this.getRelevance() > ((User) entity).getRelevance()) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return super.compareTo(entity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == User.class) {
            return ((User) o).getId() == getId();
        } else {
            return super.equals(o);
        }
    }
}
