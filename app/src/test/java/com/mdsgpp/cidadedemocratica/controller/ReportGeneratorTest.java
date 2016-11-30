package com.mdsgpp.cidadedemocratica.controller;

import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.Report;

import org.junit.Test;

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
