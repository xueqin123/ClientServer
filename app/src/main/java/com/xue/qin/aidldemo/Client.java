package com.xue.qin.aidldemo;

import android.os.RemoteException;
import android.util.Log;

/**
 * Created by xue.qin on 2017/6/4.
 */

public class Client {
    private static final String TAG = "QX_Client";
    private static final String DESCRIPTOR = "myDescriptor";   //aidl文件的包名
    private android.os.IBinder mRemote;

    public Client(android.os.IBinder obj) {
        Log.i(TAG, "ClientBinder()");
        mRemote = obj;
    }

    public String getCurrentTime(int which) throws RemoteException {
        Log.i(TAG, "getCurrentTime()");
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        String _result;
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            _data.writeInt(which);
            mRemote.transact(TRANSACTION_getCurrentTime, _data, _reply, 0);
            _reply.readException();
            _result = _reply.readString();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return _result;
    }

    public String getNewYorkCurrentTime(int which) throws RemoteException {
        Log.i(TAG, "getNewYorkCurrentTime()");
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        String _result;
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            _data.writeInt(which);
            mRemote.transact(TRANSACTION_getNewYorkCurrentTime, _data, _reply, 0);
            _reply.readException();
            _result = _reply.readString();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
        return _result;
    }


    //    static final int TRANSACTION_getCurrentTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getCurrentTime = 100;
    static final int TRANSACTION_getNewYorkCurrentTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}

