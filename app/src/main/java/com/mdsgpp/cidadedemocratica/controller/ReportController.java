package com.mdsgpp.cidadedemocratica.controller;

import com.mdsgpp.cidadedemocratica.model.Report;

/**
 * Created by andreanmasiro on 9/8/16.
 */
public abstract class ReportController {

    private Report report;
    abstract void generateGraphic();
}
