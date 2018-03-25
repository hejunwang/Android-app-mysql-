package com.example.administrator.myapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;
import android.view.KeyEvent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private String tag  =ExampleInstrumentedTest.class.getSimpleName();

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.administrator.myapplication", appContext.getPackageName());

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressBack();
        Log.e(tag,"pressback");
        Thread.sleep(2000);

        device.pressHome();
        Thread.sleep(2000);
        device.pressMenu();
        Thread.sleep(2000);
        device.pressRecentApps();
        Thread.sleep(2000);
        device.pressKeyCode(KeyEvent.KEYCODE_VOLUME_DOWN);
        Thread.sleep(2000);

        device.pressBack();
        Thread.sleep(2000);


    }
}
