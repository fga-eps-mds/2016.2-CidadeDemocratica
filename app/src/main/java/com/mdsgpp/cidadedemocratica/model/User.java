package com.mdsgpp.cidadedemocratica.model;

import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class User implements Comparable<User> {

    private String name = "";
    private String description = "";
    private long proposalCount = 0;
    private long id = 0;
    private long relevance = 0;
    private ArrayList<Proposal> proposals = new ArrayList<Proposal>();
    private ArrayList<Tag> mostUsedTags = new ArrayList<Tag>();

    public User(String name, long proposalCount,long id, long relevance) {
        this.name = name;
        this.proposalCount = proposalCount;
        this.id = id;
        this.relevance = relevance;
    }

    public User(String name, String description, long proposalCount,long id, long relevance) {
        this.name = name;
        this.description = description;
        this.proposalCount = proposalCount;
        this.id = id;
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

    public long getId(){ return this.id; }

    public long getRelevance() {
        return relevance;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString(){return  this.getName();}

    @Override
    public int compareTo(User user) {
        if (this.getRelevance() < user.getRelevance()) {
            return 1;
        } else if (this.getRelevance() > user.getRelevance()) {
            return -1;
        } else {
            return 0;
        }
    }
}
