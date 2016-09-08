package com.mdsgpp.cidadedemocratica.model;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Report {

    private String title = "";
    private String description = "";

    public Report(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
