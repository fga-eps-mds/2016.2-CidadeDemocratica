package com.mdsgpp.cidadedemocratica.persistence;

import android.provider.ContactsContract;
import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.Tag;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/13/16.
 */
public class DataContainerTest extends AndroidTestCase {

    protected DataContainer dataContainer;

    @Override
    protected void setUp() throws Exception {
        this.dataContainer = DataContainer.getInstance();
    }

    @Test
    public void testAddTag() {
        Tag tag = new Tag("CidadeDemocratica", 0);

        dataContainer.addTag(tag);

        assertTrue(DataContainer.getInstance().getTags().contains(tag));

        ArrayList<Tag> tags = new ArrayList<Tag>();

        for (int i = 0; i < 10; i++) {
            Tag tagn = new Tag("Tag" + i, 0);
            tags.add(tagn);
        }

        dataContainer.addTags(tags);

        assertTrue(DataContainer.getInstance().getTags().containsAll(tags));
    }
}
