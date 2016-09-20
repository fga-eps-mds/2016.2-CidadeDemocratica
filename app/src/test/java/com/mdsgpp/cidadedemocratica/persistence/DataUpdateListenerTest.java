package com.mdsgpp.cidadedemocratica.persistence;

import android.test.AndroidTestCase;


import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.model.User;

import org.junit.Test;

/**
 * Created by andreanmasiro on 9/15/16.
 */
public class DataUpdateListenerTest extends AndroidTestCase {

    private DataContainer dataContainer = DataContainer.getInstance();

    @Test
    public void testUpdateTags() {

        final boolean[] updated = {false};
        dataContainer.setDataUpdateListener(new DataUpdateListener() {
            @Override
            public void tagsUpdated() {
                updated[0] = true;
            }

            @Override
            public void proposalsUpdated() {

            }

            @Override
            public void usersUpdated() {

            }
        });

        Tag tag = new Tag("Tag", 0);
        dataContainer.addTag(tag);

        assertTrue(updated[0]);

    }

    @Test
    public void testUpdateProposals() {

        final boolean[] updated = {false};
        dataContainer.setDataUpdateListener(new DataUpdateListener() {
            @Override
            public void tagsUpdated() {

            }

            @Override
            public void proposalsUpdated() {
                updated[0] = true;
            }

            @Override
            public void usersUpdated() {

            }
        });

        Proposal proposal = new Proposal("Title", "Content", null);
        dataContainer.addProposal(proposal);

        assertTrue(updated[0]);
    }

    @Test
    public void testUpdateUsers() {

        final boolean[] updated = {false};
        dataContainer.setDataUpdateListener(new DataUpdateListener() {
            @Override
            public void tagsUpdated() {

            }

            @Override
            public void proposalsUpdated() {

            }

            @Override
            public void usersUpdated() {
                updated[0] = true;
            }
        });

        User user = new User("Name", 0, 0);
        dataContainer.addUser(user);

        assertTrue(updated[0]);
    }
}
