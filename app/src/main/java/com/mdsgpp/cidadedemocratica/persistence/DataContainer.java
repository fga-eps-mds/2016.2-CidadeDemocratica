package com.mdsgpp.cidadedemocratica.persistence;

import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.model.User;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/9/16.
 */
public class DataContainer {

    private ArrayList<Tag> tags = new ArrayList<Tag>();
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Proposal> proposals = new ArrayList<Proposal>();
    private DataUpdateListener dataUpdateListener;

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

    public void setDataUpdateListener(DataUpdateListener dataUpdateListener) {
        this.dataUpdateListener = dataUpdateListener;
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

    public void addTag(Tag tag) {
        this.tags.add(tag);
        this.notifyTagsUpdate();
    }

    public void addUser(User user) {
        this.users.add(user);
        this.notifyUsersUpdate();
    }

    public void addProposal(Proposal proposal) {
        this.proposals.add(proposal);
        this.notifyProposalsUpdate();
    }

    public void addTags(ArrayList<Tag> tags) {
        this.tags.addAll(tags);
        this.notifyTagsUpdate();
    }

    public void addUsers(ArrayList<User> users) {
        this.users.addAll(users);
        this.notifyUsersUpdate();
    }

    public void addProposals(ArrayList<Proposal> proposals) {
        this.proposals.addAll(proposals);
        this.notifyProposalsUpdate();
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
        this.notifyTagsUpdate();
    }

    public void setProposals(ArrayList<Proposal> proposals) {
        this.proposals = proposals;
        this.notifyProposalsUpdate();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
        this.notifyUsersUpdate();
    }

    public void clearTags() {
        this.tags.clear();
        this.notifyTagsUpdate();
    }

    public void clearProposals() {
        this.proposals.clear();
        this.notifyProposalsUpdate();
    }

    public void clearUsers() {
        this.users.clear();
        this.notifyUsersUpdate();
    }

    private void notifyTagsUpdate() {
        if (this.dataUpdateListener != null) {
            this.dataUpdateListener.tagsUpdated();
        }
    }

    private void notifyProposalsUpdate() {
        if (this.dataUpdateListener != null) {
            this.dataUpdateListener.proposalsUpdated();
        }
    }

    private void notifyUsersUpdate() {
        if (this.dataUpdateListener != null) {
            this.dataUpdateListener.usersUpdated();
        }
    }
}
