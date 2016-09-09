package com.mdsgpp.cidadedemocratica.controller;

import com.mdsgpp.cidadedemocratica.model.Report;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public class ReportGenerator {

    private Report report;

    public ReportGenerator(Report report) {
        this.report = report;
    }

    void generateReport() {

    }

    public Report getReport() {
        return this.report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
