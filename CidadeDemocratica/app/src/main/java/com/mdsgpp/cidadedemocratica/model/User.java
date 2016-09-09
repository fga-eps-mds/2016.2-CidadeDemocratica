package com.mdsgpp.cidadedemocratica.model;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class User {

    private String name = "";
    private int proposalCount = 0;
    private String location = "";
    private String pictureURL = "";
    private ArrayList<Proposal> proposals = new ArrayList<Proposal>();
    private ArrayList<Tag> mostUsedTags = new ArrayList<Tag>();

    public User(String name, int proposalCount, String location, String pictureURL, ArrayList<Proposal> proposals, ArrayList<Tag> mostUsedTags) {
        this.name = name;
        this.proposalCount = proposalCount;
        this.location = location;
        this.pictureURL = pictureURL;
        this.proposals = proposals;
        this.mostUsedTags = mostUsedTags;
    }

    public String getName() {
        return this.name;
    }

    public int getProposalCount() {
        return this.proposalCount;
    }

    public String getLocation() {
        return this.location;
    }

    public String getPictureURL() {
        return this.pictureURL;
    }

    public ArrayList<Proposal> getProposals() {
        return this.proposals;
    }

    public ArrayList<Tag> getMostUsedTags() {
        return this.mostUsedTags;
    }
}
