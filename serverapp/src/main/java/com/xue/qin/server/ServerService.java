package com.xue.qin.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by xue.qin on 2017/6/1.
 */

public class ServerService extends Service {
    private static final String TAG = "QX_ServerService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind()");
       return  new ServerBinder();
    }

}
