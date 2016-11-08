package com.mdsgpp.cidadedemocratica.persistence;

import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.model.Tagging;
import com.mdsgpp.cidadedemocratica.model.User;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by andreanmasiro on 9/9/16.
 */
public class DataContainer {

    private ArrayList<Tag> tags = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Proposal> proposals = new ArrayList<>();
    private ArrayList<Tagging> taggings = new ArrayList<>();
    private ArrayList<DataUpdateListener> dataUpdateListeners = new ArrayList<>();

    private static DataContainer instance;

    private DataContainer() {

    }

    public static DataContainer getInstance() {
        if (DataContainer.instance == null) {
            DataContainer.instance = new DataContainer();
        }

        return DataContainer.instance;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Proposal> getProposals() {
        return proposals;
    }

    public ArrayList<Tagging> getTaggings() {
        return taggings;
    }

    public void setDataUpdateListener(DataUpdateListener dataUpdateListener) {
        this.dataUpdateListeners.add(dataUpdateListener);
    }

    public Tag getTagForId(long id) {
        Tag tag = null;
        for (Tag t : this.tags) {
            if (t.getId() == id) {
                tag = t;
                break;
            } else { /* Not this one, continue */ }
        }
        return tag;
    }

    public Proposal getProposalForId(long id) {
        Proposal proposal = null;
        for (Proposal p : this.proposals) {
            if (p.getId() == id) {
                proposal = p;
                break;
            } else { /* Not this one, continue */ }
        }
        return proposal;
    }

    public User getUserForId(long id){
        User user = null;
        for (User userInterator : this.users){
            if(userInterator.getId() == id){
                user = userInterator;
                break;
            }
            else {
                // nothing to do
            }
        }
        return user;
    }

    public ArrayList<Proposal> getProposalsForUserId(long id) {
        ArrayList<Proposal> proposals = new ArrayList<>();
        for (Proposal p : this.proposals) {
            if (p.getUserId() == id) {
                proposals.add(p);
            } else { /* not this one */ }
        }
        return proposals;
    }

    public ArrayList<Tagging> getTaggingsForTagId(long id) {
        ArrayList<Tagging> taggings = new ArrayList<>();
        for (Tagging t : this.taggings) {
            if (t.getTagId() == id) {
                taggings.add(t);
            } else { /* not this one */ }
        }
        return taggings;
    }

    public ArrayList<Tagging> getTaggingsForProposalId(long id) {
        ArrayList<Tagging> taggings = new ArrayList<>();
        for (Tagging t : this.taggings) {
            if (t.getTaggableId() == id) {
                taggings.add(t);
            } else { /* not this one */ }
        }
        return taggings;
    }

    public ArrayList<Tagging> getTaggingsForUserId(long id) {
        ArrayList<Tagging> taggings = new ArrayList<>();
        for (Tagging t : this.taggings) {
            if (t.getTaggerId() == id) {
                taggings.add(t);
            } else { /* not this one */ }
        }
        return taggings;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        
    }

    public void addUser(User user) {
        this.users.add(user);
        
    }

    public void addProposal(Proposal proposal) {
        this.proposals.add(proposal);
        
    }

    public void addTagging(Tagging tagging) {
        this.taggings.add(tagging);
        
    }

    public void addTags(ArrayList<Tag> tags) {
        this.tags.addAll(tags);
        Collections.sort(tags);
        
    }

    public void addUsers(ArrayList<User> users) {
        this.users.addAll(users);
        Collections.sort(users);
        
    }

    public void addProposals(ArrayList<Proposal> proposals) {
        this.proposals.addAll(proposals);
        Collections.sort(proposals);
        
    }

    public void addTaggings(ArrayList<Tagging> taggings) {
        this.taggings.addAll(taggings);
        
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
        
    }

    public void setProposals(ArrayList<Proposal> proposals) {
        this.proposals = proposals;
        
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
        
    }

    public void setTaggings(ArrayList<Tagging> taggings) {
        this.taggings = taggings;
        
    }

    public void clearTags() {
        this.tags.clear();
        
    }

    public void clearProposals() {
        this.proposals.clear();
        
    }

    public void clearUsers() {
        this.users.clear();
        
    }

    public void clearTaggings() {
        this.taggings.clear();
        
    }

}
