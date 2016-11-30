package com.mdsgpp.cidadedemocratica.model;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * Created by guilherme on 15/09/16.
 */
public class ReportTest extends AndroidTestCase {

    Report report;
    final String reportTitle = "Title";
    final String reportDescription = "Description";

    @Override
    public void setUp() {
        report = new Report(reportTitle, reportDescription);
    }

    @Test
    public void testGetTitle() {
        assertNotNull(report.getTitle());
        assertEquals(report.getTitle(), reportTitle);
    }

    @Test
    public void testGetDescription() {
        assertNotNull(report.getDescription());
        assertEquals(report.getDescription(), reportDescription);
    }

}
