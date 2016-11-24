package com.mdsgpp.cidadedemocratica.persistence;

import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.User;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by andreanmasiro on 08/11/16.
 */

public class EntityContainerTest extends AndroidTestCase {

    EntityContainer<User> container = EntityContainer.getInstance(User.class);

    @Override
    protected void setUp() throws Exception {
        container.clear();
    }

    @Override
    protected void tearDown() throws Exception {
        container.clear();
    }

    @Test
    public void testGetAll() {

        assertTrue(container.getAll().isEmpty());

        ArrayList<User> users = new ArrayList<>(Arrays.asList(newUser(), newUser(), newUser()));
        container.setData(users);

        assertEquals(users, container.getAll());
    }

    @Test
    public void testGetForId() {
        assertTrue(container.getAll().isEmpty());

        ArrayList<User> users = new ArrayList<>(Arrays.asList(newUser(), newUser(), newUser()));
        container.setData(users);

        long id = 90;

        assertNull(container.getForId(id));

        container.add(newUser(id));

        assertNotNull(container.getForId(id));
    }

    @Test
    public void testSetData() {

        ArrayList<User> users1 = new ArrayList<>(Arrays.asList(newUser(), newUser(), newUser()));
        ArrayList<User> users2 = new ArrayList<>(Arrays.asList(newUser(), newUser(), newUser()));

        container.setData(users1);
        assertTrue(container.getAll().containsAll(users1));

        container.setData(users2);
        assertTrue(container.getAll().containsAll(users2));
        assertFalse(container.getAll().containsAll(users1));
    }

    private User newUser() {
        return new User("", 0, 0, 0);
    }

    private User newUser(long id) {
        return new User("", 0, id, 0);
    }
}
