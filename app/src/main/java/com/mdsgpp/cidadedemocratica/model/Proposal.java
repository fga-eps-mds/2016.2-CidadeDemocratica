package com.mdsgpp.cidadedemocratica.model;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class    Proposal {

    private String title = "";
    private String content = "";
    private String id = "";
    private String relevance = "";
    private ArrayList<Tag> tags = new ArrayList<Tag>();


    public Proposal(String title, String content, ArrayList<Tag> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }


    public Proposal(String proposalTitle, String proposalID, String proposalDescription, String proposalRelevance) {
        this.title = proposalTitle;
        this.content = proposalDescription;
        this.id = proposalID;
        this.relevance = proposalRelevance;

    }


    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    public String getId(){
        return this.id;
    }

    public String getRelevance(){
        return  this.relevance;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
