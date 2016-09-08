package com.mdsgpp.cidadedemocratica.model;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public abstract class Report {

    private String title = "";
    private String description = "";

    abstract void generateGraphic();
}
