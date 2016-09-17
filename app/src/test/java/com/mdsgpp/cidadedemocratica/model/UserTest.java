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
        user = new User("Lucas", 3, "distrito federal", "www.google.com", proposals, tags);
        ciclismo = newTag();
        tags.add(ciclismo);
        proposal = newProposal();
    }

    @Test
    public void testBuilder() {
        assertNotNull(user);
    }

    @Test
    public void testGetName() {
        assertTrue(user.getName().equals("Lucas"));
    }

    @Test
    public void testGetProposalCount() {
        assertEquals(3,user.getProposalCount());
    }

    @Test
    public void testGetLocation() {
        assertTrue(user.getLocation().equals("distrito federal"));
    }

    @Test
    public void testGetPictureUrl() {
        assertTrue(this.user.getPictureURL().equals("www.google.com"));
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
}
