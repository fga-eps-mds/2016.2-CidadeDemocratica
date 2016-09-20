package com.mdsgpp.cidadedemocratica.model;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class User {

    private String name = "";
    private int proposalCount = 0;
    private int id = 0;
    private ArrayList<Proposal> proposals = new ArrayList<Proposal>();
    private ArrayList<Tag> mostUsedTags = new ArrayList<Tag>();

    public User(String name, int proposalCount,int id) {
        this.name = name;
        this.proposalCount = proposalCount;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public int getProposalCount() {
        return this.proposalCount;
    }

    public ArrayList<Proposal> getProposals() {
        return this.proposals;
    }

    public ArrayList<Tag> getMostUsedTags() {
        return this.mostUsedTags;
    }

    public void setProposals(ArrayList<Proposal> proposals) {
        this.proposals = proposals;
    }

    public void setMostUsedTags(ArrayList<Tag> mostUsedTags) {
        this.mostUsedTags = mostUsedTags;
    }

    @Override
    public String toString(){return  this.getName();}
}
