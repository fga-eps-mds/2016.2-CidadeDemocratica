package com.mdsgpp.cidadedemocratica.requester;

import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.Tag;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 04/11/16.
 */

public class TagRequestResponseHandlerTest extends AndroidTestCase {

    TagRequestResponseHandler handler;
    ArrayList<Tag> response = null;
    Tag tag;
    int id = 2572;
    int userId = 6418;
    String errorMessage = null;

    @Override
    protected void setUp() {
        handler = new TagRequestResponseHandler();
    }

    @Override
    protected void tearDown() throws Exception {
        response = null;
        errorMessage = null;
    }
}
