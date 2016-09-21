package com.mdsgpp.cidadedemocratica.model;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Tag implements Comparable<Tag> {

    private long id = 0;
    private String name = "";
    private long numberOfAppearances = 0;
    private long relevance = 0;
    private ArrayList<Proposal> proposals = new ArrayList<Proposal>();

    public Tag(long id, String name, long numberOfAppearances, long relevance) {
        this.id = id;
        this.name = name;
        this.numberOfAppearances = numberOfAppearances;
        this.relevance = relevance;
    }

    public long getRelevance() {
        return this.relevance;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getNumberOfAppearances() {
        return this.numberOfAppearances;
    }

    public ArrayList<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(ArrayList<Proposal> proposals) {
        this.proposals = proposals;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Tag tag) {
        if (this.getRelevance() > tag.getRelevance()) {
            return -1;
        } else if (this.getRelevance() < tag.getRelevance()) {
            return 1;
        } else {
            return 0;
        }
    }
}
