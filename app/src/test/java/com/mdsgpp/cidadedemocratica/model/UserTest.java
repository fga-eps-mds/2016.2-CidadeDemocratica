package com.mdsgpp.cidadedemocratica.model;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import org.junit.Test;

/**
 * Created by guilherme on 15/09/16.
 */
public class UserTest extends AndroidTestCase {

    User user;
    Tag ciclismo;
    Proposal proposal;
    ArrayList<Tag> tags = new ArrayList<>(0);
    ArrayList<Proposal> proposals  = new ArrayList<>(0);

    @Override
    public void setUp() {
        user = newUser();
        ciclismo = newTag();
        tags.add(ciclismo);
        proposal = newProposal();
        user.setProposals(proposals);
        user.setMostUsedTags(tags);
    }

    @Test
    public void testBuilder() {
        assertNotNull(user);
    }

    @Test
    public void testGetName() {
        assertTrue(user.getName().equals("Name"));
    }

    @Test
    public void testGetProposalCount() {
        assertEquals(0,user.getProposalCount());
    }

    @Test
    public void testGetsTags() {
        tags.add(ciclismo);
        assertTrue(this.user.getMostUsedTags().equals(tags));
    }

    @Test
    public void testGetProposal(){
        proposals.add(proposal);
        assertTrue(this.user.getProposals().equals(proposals));
    }

    private Tag newTag() {
        return new Tag(0, "name", 0, 0);
    }

    private Proposal newProposal() {
        return new Proposal(0, "title", "content", 0);
    }
    private User newUser() {return new User("Name", 0, 0);}
}
