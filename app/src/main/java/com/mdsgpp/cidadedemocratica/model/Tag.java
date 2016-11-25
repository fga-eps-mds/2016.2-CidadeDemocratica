package com.mdsgpp.cidadedemocratica.model;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class Tag extends Entity {

    private String name = "";
    private long numberOfAppearances = 0;
    private long relevance = 0;
    private ArrayList<Proposal> proposals = new ArrayList<Proposal>();

    public Tag(long id, String name, long numberOfAppearances, long relevance) {
        super(id);
        this.name = name;
        this.numberOfAppearances = numberOfAppearances;
        this.relevance = relevance;
    }

    public long getRelevance() {
        return this.relevance;
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
    public int compareTo(Entity entity) {
        if (entity.getClass() == Tag.class) {
            if (this.getRelevance() > ((Tag) entity).getRelevance()) {
                return -1;
            } else if (this.getRelevance() < ((Tag) entity).getRelevance()) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return super.compareTo(entity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass()==Tag.class){
            boolean equals = name.equals(((Tag)o).name);
            equals &= numberOfAppearances == ((Tag)o).numberOfAppearances;
            equals &= relevance == ((Tag)o).relevance;
            return equals;
        } else {
            return super.equals(o);
        }
    }
}
