package com.xue.qin.aidldemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by xue.qin on 2017/6/1.
 */

public class ClientActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "QX_ClientActivity";
    private TextView mTextView;
    private Button mButton;
    private Client mService = null;
    private ServiceConnection mConntection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected() ");
            mService = new Client(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected()");
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        mTextView = (TextView) findViewById(R.id.result);
        mButton = (Button) findViewById(R.id.switchmodel);
        mButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent("remoteService");
        intent.setPackage("com.xue.qin.server");
        Log.i(TAG, "bindService()");
        boolean success = bindService(intent, mConntection, Context.BIND_AUTO_CREATE);
    }

    private volatile int mCount = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switchmodel:
                mCount++;
                if (mCount > 100) {
                    mCount = 0;
                }
                try {
                    String here = mService.getCurrentTime(mCount % 3);
                    String NewYork = mService.getNewYorkCurrentTime(mCount % 3);
                    mTextView.setText(here + NewYork);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConntection);
    }
}
