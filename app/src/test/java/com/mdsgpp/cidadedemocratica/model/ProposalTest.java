package com.mdsgpp.cidadedemocratica.model;

import android.test.AndroidTestCase;

import org.junit.Test;

import java.net.PortUnreachableException;
import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/17/16.
 */
public class ProposalTest extends AndroidTestCase {

    private Proposal proposal = newProposal();
    private final long proposalId = 10;
    private final String proposalTitle = "title";
    private final String proposalContent = "content";
    private final long proposalRelevance = 11000;
    private final long proposalIdOfUser = 0;
    Proposal proposalDefault = newProposal();
    Proposal proposalLower = new Proposal(12,"Title","superContent",10999,32);
    Proposal proposalIgual = new Proposal(32,"SomeTitle","important",11000,1);
    Proposal proposalLarger = new Proposal(2,"Important title","content",12000,3);


    @Test
    public void testGetId() {
        assertEquals(proposal.getId(), proposalId);
    }

    @Test
    public void testGetTitle() {
        assertNotNull(proposal.getTitle());
        assertEquals(proposal.getTitle(), proposalTitle);
    }

    @Test
    public void testGetContent() {
        assertNotNull(proposal.getContent());
        assertEquals(proposal.getContent(), proposalContent);
    }

    @Test
    public void testGetRelevance() {
        assertEquals(proposal.getRelevance(), proposalRelevance);
    }

    @Test
    public void testSetTags() {

        assertNotNull(proposal.getTags());
        assertEquals(proposal.getTags().size(), 0);

        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags.add(newTag());
        tags.add(newTag());
        tags.add(newTag());

        proposal.setTags(tags);

        assertEquals(tags, proposal.getTags());
    }
    @Test
    public void testCompareTo(){
        assertEquals(-1,proposalDefault.compareTo(proposalLower));
        assertEquals(0,proposalDefault.compareTo(proposalIgual));
        assertEquals(1,proposalDefault.compareTo(proposalLarger));

    }

    @Test
    public void testToString(){
        assertTrue(proposalDefault.toString().equals("title"));
    }

    private Proposal newProposal() {
        return new Proposal(proposalId, proposalTitle, proposalContent, proposalRelevance, proposalIdOfUser);
    }



    private Tag newTag() {
        return new Tag(0, "", 0, 0);
    }
}
