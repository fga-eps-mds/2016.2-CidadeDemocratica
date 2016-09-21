package com.mdsgpp.cidadedemocratica.model;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * Created by guilherme on 21/09/16.
 */
public class UserListRowTest extends AndroidTestCase {

    @Test
    public void testBuilder(){
        UserListRow userListRow = new UserListRow();
        assertTrue(userListRow != null);
    }
}
