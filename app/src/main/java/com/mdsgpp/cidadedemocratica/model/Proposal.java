package com.mdsgpp.cidadedemocratica.model;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class    Proposal {

    private String title = "";
    private String content = "";
    private ArrayList<Tag> tags = new ArrayList<Tag>();

    public Proposal(String title, String content, ArrayList<Tag> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
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
}
