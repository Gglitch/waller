package com.poshtech.android.waller;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;

/**
 * Created by Punit Chhajer on 19-05-2016.
 */
public class fullTestSuite {
    public static Test suite(){
        return new TestSuiteBuilder(fullTestSuite.class)
                .includeAllPackagesUnderHere().build();
    }

    public fullTestSuite(){
        super();
    }
}
