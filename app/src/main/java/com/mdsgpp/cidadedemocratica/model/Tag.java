package com.mdsgpp.cidadedemocratica.model;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Tag {

    private String name = "";
    private int numberOfAppearances = 0;

    public Tag(String name, int numberOfAppearances) {
        this.name = name;
        this.numberOfAppearances = numberOfAppearances;
    }

    public String getName() {
        return this.name;
    }

    public int getNumberOfAppearances() { return this.numberOfAppearances;}
    @Override
    public String toString()
    {
        return this.name;
    }
}
