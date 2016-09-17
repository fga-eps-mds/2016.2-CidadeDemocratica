package com.mdsgpp.cidadedemocratica;

import com.mdsgpp.cidadedemocratica.model.Report;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by guilherme on 15/09/16.
 */
public class ReportTest {
    Report report;
    @Before
    public void setUp(){
        report = new Report("Title","Description");

    }
    @Test
    public void testGetTitle(){
        Assert.assertTrue(report.getTitle().equals("Title"));
    }
    @Test
    public void testGetDescription(){
        Assert.assertTrue(this.report.getDescription().equals("Description"));
    }

}
