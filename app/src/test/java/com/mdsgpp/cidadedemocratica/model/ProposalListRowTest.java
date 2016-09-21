package com.mdsgpp.cidadedemocratica.model;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * Created by guilherme on 21/09/16.
 */
public class ProposalListRowTest extends AndroidTestCase {

    @Test
    public void testBuider(){
        ProposalListRow proposalListRow = new ProposalListRow();
        assertTrue(proposalListRow != null);
    }
}
