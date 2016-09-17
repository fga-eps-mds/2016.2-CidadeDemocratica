package com.mdsgpp.cidadedemocratica;

import com.mdsgpp.cidadedemocratica.controller.ReportGenerator;
import com.mdsgpp.cidadedemocratica.model.Report;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by guilherme on 15/09/16.
 */
public class ReportGeneratorTest {
    ReportGenerator reportGenerator;
    @Test
    public void testSetReport(){
        Report report = new Report("Title","Description");
        reportGenerator = new ReportGenerator(report);
        reportGenerator.setReport(report);
        Assert.assertEquals(reportGenerator.getReport(),report);
    }
}
