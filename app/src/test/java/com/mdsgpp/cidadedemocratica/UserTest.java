package com.mdsgpp.cidadedemocratica;

import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.model.User;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by thiagoMB on 9/15/16.
 */
public class UserTest {

    private User user;

    @Before
    public void setup(){

        ArrayList<Proposal> proposals = new ArrayList(0);

        ArrayList<Tag> tags = new ArrayList(0);

        this.user = new User("Thiago",5,"Location","www.google.com",proposals, tags);
    }

    @Test
    public void testGetName() {

        assertNotNull(this.user.getName());
        assertTrue(this.user.getName().equals("Thiago"));
    }

    @Test
    public void testGetLocation() {

    }
}
