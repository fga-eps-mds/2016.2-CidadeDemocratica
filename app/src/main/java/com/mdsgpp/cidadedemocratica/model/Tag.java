package com.mdsgpp.cidadedemocratica.model;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Tag {

    private long id = 0;
    private String name = "";
    private long numberOfAppearances = 0;
    private long relevance = 0;

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

    @Override
    public String toString() {
        return this.name;
    }
}
