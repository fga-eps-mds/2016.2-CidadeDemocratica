package com.mdsgpp.cidadedemocratica;

/**
 * Created by luisresende on 21/09/16.
 */

import android.os.Bundle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.support.test.runner.AndroidJUnitRunner;

import com.mdsgpp.cidadedemocratica.BuildConfig;

public class JUnitJacocoTestRunner extends AndroidJUnitRunner {
    static {
        System.setProperty("jacoco-agent.destfile", "/data/data/"+ BuildConfig.APPLICATION_ID+"/coverage.ec");
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        try {
            Class rt = Class.forName("org.jacoco.agent.rt.RT");
            Method getAgent = rt.getMethod("getAgent");
            Method dump = getAgent.getReturnType().getMethod("dump", boolean.class);
            Object agent = getAgent.invoke(null);
            dump.invoke(agent, false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        super.finish(resultCode, results);
    }
}
