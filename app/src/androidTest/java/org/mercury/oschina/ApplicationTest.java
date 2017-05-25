package org.mercury.oschina;

import android.app.Application;
import android.os.SystemClock;
import android.test.ApplicationTestCase;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private static final String TAG = "====_ApplicationTest";
    public ApplicationTest() {

        super(Application.class);
    }

    public void test15() throws Exception {
        Log.i(TAG, "test15: ");
        SystemClock.sleep(3000);
    }
}