package com.mdsgpp.cidadedemocratica;

import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by gabriel on 15/09/16.
 */


public class ProposalTest {


    Proposal proposal;
    ArrayList<Tag> tags = new ArrayList<Tag>(0);
    Tag teste;
    Proposal proposalTag;


    @Before
    public void setUp(){
        proposal = new Proposal("title","id", "description","relevance");
        proposalTag = new Proposal("title", "content", tags);

        teste = new Tag("tag",1);
    }

    @Test
    public void testBuilder()
    {
        Assert.assertNotNull(proposal);
    }

    @Test
    public void testgetTitle()
    {
        Assert.assertTrue(proposal.getTitle().equals("title"));
    }

    @Test
    public void testgetContent()
    {
        Assert.assertTrue(proposal.getContent().equals("description"));
    }

    @Test
    public void testgetId()
    {
        Assert.assertTrue(proposal.getId().equals("id"));
    }

    @Test
    public void tesgetRelevance()
    {
        Assert.assertTrue(proposal.getRelevance().equals("relevance"));
    }

    @Test
    public void testgetTags()
    {
        tags.add(teste);
        Assert.assertTrue(proposalTag.getTags().equals(tags));
    }


}
