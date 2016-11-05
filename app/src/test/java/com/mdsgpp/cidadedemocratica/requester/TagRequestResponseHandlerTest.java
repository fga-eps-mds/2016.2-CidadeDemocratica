package com.mdsgpp.cidadedemocratica.requester;

import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.Tag;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 04/11/16.
 */

public class TagRequestResponseHandlerTest extends AndroidTestCase implements RequestUpdateListener {

    TagRequestResponseHandler handler = new TagRequestResponseHandler();
    ArrayList<Tag> response = null;
    Tag tag;
    int id = 1;
    String tagName = "internet";
    String errorMessage = null;

    @Override
    protected void setUp() {
        handler.setRequestUpdateListener(this);
    }

    @Override
    protected void tearDown() throws Exception {
        response = null;
        errorMessage = null;
    }

    @Test
    public void testOnSuccess() throws JSONException {
        JSONArray jsonTag = new JSONArray("[{\"id\":" + id + ",\"name\":\"" + tagName + "\",\"relevancia\":982100}]");
        handler.onSuccess(200, null, jsonTag);

        assertEquals(id, tag.getId());
        assertEquals(tagName, tag.getName());
    }

    @Test
    public void testOnFailure() {
        int errorCode = 500;
        handler.onFailure(errorCode, null, null);

        assertEquals(errorMessage, String.valueOf(errorCode));
    }

    @Test
    public void testCompareTags() {
        Tag t1 = new Tag(0, "", 0, 0);
        Tag t2 = new Tag(0, "", 0, 0);

        assertEquals(t1.compareTo(t2), handler.compare(t1, t2));
    }

    @Override
    public void afterSuccess(RequestResponseHandler handler, Object response) {
        ArrayList<Tag> tags = (ArrayList<Tag>) response;
        tag = tags.get(0);
    }

    @Override
    public void afterError(RequestResponseHandler handler, String message) {
        errorMessage = message;
    }
}
