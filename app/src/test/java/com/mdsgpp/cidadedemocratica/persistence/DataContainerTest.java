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
        Tag tag = new Tag("CidadeDemocratica", 0);

        dataContainer.addTag(tag);

        assertTrue(DataContainer.getInstance().getTags().contains(tag));

        ArrayList<Tag> tags = new ArrayList<Tag>();

        for (int i = 0; i < 10; i++) {
            Tag tagn = new Tag("Tag" + i, 0);
            tags.add(tagn);
        }

        dataContainer.addTags(tags);

        assertTrue(DataContainer.getInstance().getTags().containsAll(tags));
    }

    @Test
    public void testAddProposal() {
        Proposal proposal = new Proposal("Title", "Content", null);

        dataContainer.addProposal(proposal);

        assertTrue(DataContainer.getInstance().getProposals().contains(proposal));

        ArrayList<Proposal> proposals = new ArrayList<Proposal>();

        for (int i = 0; i < 10; i++) {
            Proposal propn = new Proposal("Title" + i, "Content", null);
            proposals.add(propn);
        }

        dataContainer.addProposals(proposals);

        assertTrue(DataContainer.getInstance().getProposals().containsAll(proposals));
    }

    @Test
    public void testAddUser() {
        User user = new User("Name", 0, 0);

        dataContainer.addUser(user);

        assertTrue(DataContainer.getInstance().getUsers().contains(user));

        ArrayList<User> users = new ArrayList<User>();

        for (int i = 0; i < 10; i++) {
            User usern = new User("Name" + i, 0, 0);
            users.add(usern);
        }

        dataContainer.addUsers(users);

        assertTrue(DataContainer.getInstance().getUsers().containsAll(users));
    }

    @Test
    public void testUpdateListener() {

        final boolean[] tagsUpdated = {false};
        final boolean[] proposalsUpdated = {false};
        final boolean[] usersUpdated = {false};

        dataContainer.setDataUpdateListener(new DataUpdateListener() {
            @Override
            public void tagsUpdated() {
                tagsUpdated[0] = true;
            }

            @Override
            public void proposalsUpdated() {
                proposalsUpdated[0] = true;
            }

            @Override
            public void usersUpdated() {
                usersUpdated[0] = true;
            }
        });

        Tag tag = new Tag("Name", 0);
        dataContainer.addTag(tag);

        assertTrue(tagsUpdated[0]);


        Proposal proposal = new Proposal("Proposal", "Content", null);
        dataContainer.addProposal(proposal);

        assertTrue(proposalsUpdated[0]);


        User user = new User("User", 0, 0);
        dataContainer.addUser(user);

        assertTrue(usersUpdated[0]);
    }
}
