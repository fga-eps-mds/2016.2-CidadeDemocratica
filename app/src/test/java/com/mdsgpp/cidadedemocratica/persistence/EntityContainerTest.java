package com.mdsgpp.cidadedemocratica.persistence;

import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.User;

import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by andreanmasiro on 08/11/16.
 */

public class EntityContainerTest extends AndroidTestCase {

    EntityContainer<User> userContainer = EntityContainer.getInstance(User.class);
    EntityContainer<Proposal> proposalContainer = EntityContainer.getInstance(Proposal.class);

    @Override
    protected void setUp() throws Exception {
        userContainer.clear();
    }

    @Override
    protected void tearDown() throws Exception {
        userContainer.clear();
    }

    @Test
    public void testGetAll() {

        assertTrue(userContainer.getAll().isEmpty());

        ArrayList<User> users = new ArrayList<>(Arrays.asList(newUser(), newUser(), newUser()));
        userContainer.setData(users);

        assertEquals(users, userContainer.getAll());
    }

    @Test
    public void testGetForId() {
        assertTrue(userContainer.getAll().isEmpty());

        ArrayList<User> users = new ArrayList<>(Arrays.asList(newUser(), newUser(), newUser()));
        userContainer.setData(users);

        long id = 90;

        assertNull(userContainer.getForId(id));

        userContainer.add(newUser(id));

        assertNotNull(userContainer.getForId(id));
    }

    @Test
    public void testSetData() {

        ArrayList<User> users1 = new ArrayList<>(Arrays.asList(newUser(), newUser(), newUser()));
        ArrayList<User> users2 = new ArrayList<>(Arrays.asList(newUser(), newUser(), newUser()));

        userContainer.setData(users1);
        assertTrue(userContainer.getAll().containsAll(users1));

        userContainer.setData(users2);
        assertTrue(userContainer.getAll().containsAll(users2));
        assertFalse(userContainer.getAll().containsAll(users1));
    }

    @Test
    public void testGetForField() {
        Proposal p1 = newProposal("AC");
        Proposal p2 = newProposal("DF");
        Proposal p3 = newProposal("SP");

        proposalContainer.add(p1);
        proposalContainer.add(p2);
        proposalContainer.add(p3);

        try {
            String stateAbbrev = "AC";
            ArrayList<Proposal> proposals = proposalContainer.getForField("stateAbbrev", stateAbbrev);
            assertEquals(proposals.get(0).getStateAbbrev(), stateAbbrev);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    long id = 0;
    private User newUser() {
        return new User("", 0, id++, 0);
    }

    private User newUser(long id) {
        return new User("", 0, id, 0);
    }

    private Proposal newProposal(String stateAbbrev) {
        return new Proposal(id++, "", "", 0, 0, "", stateAbbrev);
    }
}
