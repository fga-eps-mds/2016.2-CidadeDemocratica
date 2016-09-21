package com.mdsgpp.cidadedemocratica.model;
import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.persistence.DataContainer;

import java.util.ArrayList;
import org.junit.Test;

/**
 * Created by guilherme on 15/09/16.
 */
public class UserTest extends AndroidTestCase {

    User user;
    Tag ciclismo;
    Proposal proposalTest;
    Proposal proposalTest2;
    ArrayList<Tag> tags = new ArrayList<>(0);
    ArrayList<Proposal> proposals  = new ArrayList<>(0);

    @Override
    public void setUp() {
        user = newUser();
        ciclismo = newTag();
        tags.add(ciclismo);
        proposalTest = newProposal();
        proposalTest2 = new Proposal(0,"Titulo","content_2",1,1);
        proposals.add(proposalTest);

        user.setMostUsedTags(tags);
        DataContainer.getInstance().addProposal(proposalTest);
        DataContainer.getInstance().addProposal(proposalTest2);
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
        assertTrue(this.user.getProposals().equals(proposals));
    }

    private Tag newTag() {
        return new Tag(0, "name", 0, 0);
    }

    private Proposal newProposal() {
        return new Proposal(0, "title", "content", 0, 0);
    }
    private User newUser() {return new User("Name", 0, 0,0);}
}
