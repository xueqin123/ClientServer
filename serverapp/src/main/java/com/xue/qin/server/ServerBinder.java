package com.xue.qin.server;

import android.os.SystemClock;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by xue.qin on 2017/6/4.
 */

public class ServerBinder extends android.os.Binder implements android.os.IInterface {
    private static final String TAG = "QX_ServerBinder";
    private static final String DESCRIPTOR = "myDescriptor";   //aidl文件的包名

    public ServerBinder() {
        //绑定唯一的DESCRIPTOR;
        this.attachInterface(this, DESCRIPTOR);
    }

    @Override
    public android.os.IBinder asBinder() {
        Log.i(TAG, "asBinder()");
        return this;
    }

    @Override
    public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
        Log.i(TAG, "onTransact()");
        switch (code) {
            case INTERFACE_TRANSACTION: {
                Log.i(TAG, "onTransact()code = INTERFACE_TRANSACTION");
                reply.writeString(DESCRIPTOR); //来自CLIENT的调用getInterfaceDescriptor（）
                return true;
            }
            case TRANSACTION_getCurrentTime: {
                Log.i(TAG, "onTransact() code = TRANSACTION_getCurrentTime");
                data.enforceInterface(DESCRIPTOR);
                int _arg0;
                _arg0 = data.readInt();
                String _result = this.getCurrentTime(_arg0, 0); //这里调用本地方法
                reply.writeNoException();
                reply.writeString(_result);
                return true;
            }
            case TRANSACTION_getNewYorkCurrentTime: {
                Log.i(TAG, "onTransact() code = TRANSACTION_getCurrentTime");
                data.enforceInterface(DESCRIPTOR);
                int _arg0;
                _arg0 = data.readInt();
                String _result = this.getCurrentTime(_arg0, -12); //这里调用本地方法
                reply.writeNoException();
                reply.writeString(_result);
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }

    public String getCurrentTime(int which, int jetlag) {
        Log.i(TAG, "getCurrentTime()");
        StringBuilder builder = new StringBuilder("");
        switch (which) {
            case 0:
                long localtime = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
                sdf.applyPattern("yyyy年MM月dd日 \nHH时mm分ss秒");
                if (jetlag == 0) {
                    builder = builder.append("\n本地\n");
                    builder.append(sdf.format(localtime));
                } else {
                    builder = builder.append("\nNewYork\n");
                    builder = builder.append(sdf.format(localtime + jetlag * 1000 * 3600));
                }
                break;
            case 1:
                builder = builder.append("\n开机之后过去了\n " + formatTime(SystemClock.elapsedRealtime()));
                break;
            case 2:
                //SystemClock.uptimeMillis() 去掉休眠的时间
                builder = builder.append("\n实际使用了\n" + formatTime(SystemClock.uptimeMillis()));
                break;
        }
        return builder.toString();
    }

    public static String formatTime(long time) {
        int total = (int) (time / 1000);
        int seconds = total % 60;
        int minute = total % 3600 / 60;
        int hours = total / 3600;
        return String.format("%d小时%d分钟 %d秒", hours, minute, seconds);
    }

//    static final int TRANSACTION_getCurrentTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_getCurrentTime = 100;
    static final int TRANSACTION_getNewYorkCurrentTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
