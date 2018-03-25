package com.example.administrator.myapplication;

import android.app.Instrumentation;
import android.content.Context;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.view.KeyEvent;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/11/12 0012.
 */

@RunWith(AndroidJUnit4.class)        //使用哪个库
public class UiTestDemo {

    public  void  pressKey() throws RemoteException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.administrator.myapplication", appContext.getPackageName());


        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressBack();
        device.pressHome();
        device.pressMenu();

        device.pressRecentApps();

        device.pressKeyCode(KeyEvent.KEYCODE_VOLUME_UP);

    }
}
