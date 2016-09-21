package com.mdsgpp.cidadedemocratica.controller;

import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.controller.ReportGenerator;
import com.mdsgpp.cidadedemocratica.model.Proposal;
import com.mdsgpp.cidadedemocratica.model.Report;
import com.mdsgpp.cidadedemocratica.view.ListProposalFragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by guilherme on 15/09/16.
 */
public class ReportGeneratorTest extends AndroidTestCase {

    ReportGenerator reportGenerator;

    @Test
    public void testSetReport() {
        Report report = new Report("Title","Description");
        reportGenerator = new ReportGenerator(null);

        assertNull(reportGenerator.getReport());

        reportGenerator.setReport(report);
        assertEquals(reportGenerator.getReport(), report);
    }
}
