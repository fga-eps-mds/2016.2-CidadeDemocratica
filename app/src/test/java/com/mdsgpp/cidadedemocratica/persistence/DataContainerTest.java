package com.mdsgpp.cidadedemocratica.persistence;

import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.model.User;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/13/16.
 */
public class DataContainerTest extends AndroidTestCase {

    protected DataContainer dataContainer;

    @Override
    protected void setUp() throws Exception {
        this.dataContainer = DataContainer.getInstance();
    }

    @Test
    public void testAddTag() {
        Tag tag = newTag();

        dataContainer.addTag(tag);

        assertTrue(DataContainer.getInstance().getTags().contains(tag));

        ArrayList<Tag> tags = new ArrayList<Tag>();

        for (int i = 0; i < 10; i++) {
            Tag tagn = newTag();
            tags.add(tagn);
        }

        dataContainer.addTags(tags);

        assertTrue(DataContainer.getInstance().getTags().containsAll(tags));
    }

    @Test
    public void testAddProposal() {
        Proposal proposal = newProposal();

        dataContainer.addProposal(proposal);

        assertTrue(DataContainer.getInstance().getProposals().contains(proposal));

        ArrayList<Proposal> proposals = new ArrayList<Proposal>();

        for (int i = 0; i < 10; i++) {
            Proposal propn = newProposal();
            proposals.add(propn);
        }

        dataContainer.addProposals(proposals);

        assertTrue(DataContainer.getInstance().getProposals().containsAll(proposals));
    }

    @Test
    public void testAddUser() {
        User user = newUser();

        dataContainer.addUser(user);

        assertTrue(DataContainer.getInstance().getUsers().contains(user));

        ArrayList<User> users = new ArrayList<User>();

        for (int i = 0; i < 10; i++) {
            User usern = newUser();
            users.add(usern);
        }

        dataContainer.addUsers(users);

        assertTrue(DataContainer.getInstance().getUsers().containsAll(users));
    }

    @Test
    public void testSetTags() {

        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(newTag());
        tags.add(newTag());
        tags.add(newTag());

        dataContainer.setTags(tags);

        assertEquals(tags, dataContainer.getTags());
    }

    @Test
    public void testSetProposals() {

        ArrayList<Proposal> proposals = new ArrayList<Proposal>();
        proposals.add(newProposal());
        proposals.add(newProposal());
        proposals.add(newProposal());

        dataContainer.setProposals(proposals);

        assertEquals(proposals, dataContainer.getProposals());
    }

    @Test
    public void testSetUsers() {

        ArrayList<User> users = new ArrayList<User>();
        users.add(newUser());
        users.add(newUser());
        users.add(newUser());

        dataContainer.setUsers(users);

        assertEquals(users, dataContainer.getUsers());
    }

    @Test
    public void testClearTags() {

        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(newTag());
        tags.add(newTag());
        tags.add(newTag());

        dataContainer.setTags(tags);

        assertEquals(tags, dataContainer.getTags());

        dataContainer.clearTags();
        assertEquals(dataContainer.getTags().size(), 0);
    }

    @Test
    public void testClearProposals() {

        ArrayList<Proposal> proposals = new ArrayList<Proposal>();
        proposals.add(newProposal());
        proposals.add(newProposal());
        proposals.add(newProposal());

        dataContainer.setProposals(proposals);

        assertEquals(proposals, dataContainer.getProposals());

        dataContainer.clearProposals();
        assertEquals(dataContainer.getProposals().size(), 0);
    }

    @Test
    public void testClearUsers() {

        ArrayList<User> users = new ArrayList<User>();
        users.add(newUser());
        users.add(newUser());
        users.add(newUser());

        dataContainer.setUsers(users);

        assertEquals(users, dataContainer.getUsers());

        dataContainer.clearUsers();
        assertEquals(dataContainer.getUsers().size(), 0);
    }

    private Tag newTag() {
        return new Tag(0, "name", 0, 0);
    }

    private Proposal newProposal() {
        return new Proposal("title", "content", null);
    }

    private User newUser() {
        return new User("name", 0, "location", "url", null, null);
    }
}
