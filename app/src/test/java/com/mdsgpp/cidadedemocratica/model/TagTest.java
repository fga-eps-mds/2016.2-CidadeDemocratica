package com.mdsgpp.cidadedemocratica.model;

import android.test.AndroidTestCase;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by guilherme on 15/09/16.
 */
public class TagTest extends AndroidTestCase {

    Tag tag = newTag();
    Tag tagTwo = new Tag(0,"tagName",15,200);
    Tag tagIgual = new Tag(0,"TAGNAME",34,100);
    final long tagId = 100;
    final long tagRelevance = 100;
    final String tagName = "name";
    final long tagNumberOfAppearances = 10;
    Proposal proposalOne = newProposal();
    Proposal proposalTwo = new Proposal(0,"title","content",2,2, "", "");
    ArrayList<Proposal> proposalsList = new ArrayList<Proposal>();

    @Test
    public void testGetName() {
        assertNotNull(tag.getName());
        assertEquals(tag.getName(), tagName);
    }

    @Test
    public void testGetNumberOfAppearances() {
        assertEquals(tag.getNumberOfAppearances(), tagNumberOfAppearances);
    }

    @Test
    public void testToString() {
        assertEquals(tag.toString(), tag.getName());
    }

    @Test
    public void testGetRelevance() {
        assertEquals(tag.getRelevance(), tagRelevance);
    }

    @Test
    public void testGetProposals(){
        proposalsList.add(proposalOne);
        proposalsList.add(proposalTwo);

        tag.setProposals(proposalsList);

        assertTrue(tag.getProposals().equals(proposalsList));
    }
    @Test
    public void testCompareTo(){
        assertEquals(1,tag.compareTo(tagTwo));
        assertEquals(-1,tagTwo.compareTo(tag));
        assertEquals(0,tag.compareTo(tagIgual));
    }

    @Test
    public void testIsEqual() {
        Tag t1 = newTagRand();
        Tag t2 = newTagRand();

        assertEquals(t1.equals(t2), t1.getId() == t2.getId());

        Object o = new Object();

        assertFalse(t1.equals(o));
    }

    long id = 0;
    private Tag newTagRand() {
        return new Tag(id++, "", 0, 0);
    }
    private Tag newTag() {
        return new Tag(tagId, tagName, tagNumberOfAppearances, tagRelevance);
    }
    private User newUser() {return new User("Name", 0, 0,0);}
    private Proposal newProposal() {
        return new Proposal(0, "title", "content", 0, 0, "", "");
    }
}
