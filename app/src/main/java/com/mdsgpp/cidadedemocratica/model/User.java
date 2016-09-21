package com.mdsgpp.cidadedemocratica.model;

import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class User {

    private String name = "";
    private long proposalCount = 0;
    private long id = 0;
    private long relevance = 0;
    private ArrayList<Proposal> proposals = new ArrayList<Proposal>();
    private ArrayList<Tag> mostUsedTags = new ArrayList<Tag>();

    public User(String name, long proposalCount,long id, long relevance) {
        this.name = name;
        this.proposalCount = proposalCount;
        this.id = id;
        this.relevance = relevance;
    }

    public String getName() {
        return this.name;
    }

    public long getProposalCount() {
        return this.proposalCount;
    }

    public ArrayList<Proposal> getProposals() {
        DataContainer dataContainer = DataContainer.getInstance();
        ArrayList<Proposal> proposalsList = dataContainer.getProposals();
        ArrayList<Proposal> resultProposalsList = new ArrayList<Proposal>(0);
        for (Proposal idProposals: proposalsList){
            if (idProposals.getUserId() == id){
                resultProposalsList.add(idProposals);
            }
        }
        return resultProposalsList;
    }

    public ArrayList<Tag> getMostUsedTags() {
        return this.mostUsedTags;
    }

    public void setMostUsedTags(ArrayList<Tag> mostUsedTags) {
        this.mostUsedTags = mostUsedTags;
    }
    public long getId(){ return this.id; }


    @Override
    public String toString(){return  this.getName();}
}
