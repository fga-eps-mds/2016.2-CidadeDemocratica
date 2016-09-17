package com.mdsgpp.cidadedemocratica.model;

import android.test.AndroidTestCase;

import com.mdsgpp.cidadedemocratica.model.Report;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by guilherme on 15/09/16.
 */
public class ReportTest extends AndroidTestCase {

    Report report;

    @Override
    public void setUp() {
        report = new Report("Title","Description");

    }

    @Test
    public void testGetTitle() {
        assertTrue(report.getTitle().equals("Title"));
    }

    @Test
    public void testGetDescription() {
        assertTrue(this.report.getDescription().equals("Description"));
    }

}
