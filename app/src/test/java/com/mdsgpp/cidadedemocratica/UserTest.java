package com.mdsgpp.cidadedemocratica;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.model.User;

import org.junit.Assert;
import org.junit.Before;

import java.util.ArrayList;
import java.util.PropertyPermission;
import org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by guilherme on 15/09/16.
 */
public class UserTest {

    User user;
    Tag ciclismo;
    Proposal proposal;
    ArrayList<Tag> tags = new ArrayList<>(0);
    ArrayList<Proposal> proposals  = new ArrayList<>(0);

    @Before
    public void setup()
    {
        user = new User("Lucas",3,"distrito federal","www.google.com",proposals,tags);
        ciclismo = new Tag("ciclismo",15);
        tags.add(ciclismo);
        proposal = new Proposal("Title","Content",tags);
    }

    @Test
    public void testBuilder(){
        Assert.assertNotNull(user);
    }

    @Test
    public void testGetName(){
        Assert.assertTrue(user.getName().equals("Lucas"));

    }

    @Test
    public void testGetProposalCount() {
        Assert.assertEquals(3,user.getProposalCount());
    }

    @Test
    public void testGetLocation(){
        Assert.assertTrue(user.getLocation().equals("distrito federal"));
    }
    @Test
    public void testGetPictureUrl(){
        Assert.assertTrue(this.user.getPictureURL().equals("www.google.com"));
    }
    @Test
    public void testGetsTags(){
        tags.add(ciclismo);
        Assert.assertTrue(this.user.getMostUsedTags().equals(tags));
    }
    @Test
    public void testGetProposal(){
        proposals.add(proposal);
        Assert.assertTrue(this.user.getProposals().equals(proposals));

    }

}
