package com.example.onotes.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.onotes.App;

/**
 * Created by cwj Apr.08.2017 10:37 AM
 */

public class ToastUtil {

    private static volatile ToastUtil sToastUtil = null;

    private static Toast mToast = null;

    /**
     * 获取实例
     *
     * @return
     */
    public static ToastUtil getInstance() {
        if (sToastUtil == null) {
            synchronized (ToastUtil.class) {
                if (sToastUtil == null) {
                    sToastUtil = new ToastUtil();
                }
            }
        }
        return sToastUtil;
    }

    protected static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容
     * 持续时间默认为short
     * @param tips 要显示的内容
     *            {@link Toast#LENGTH_LONG}
     */
  /*  public static void showToast(final String tips){
        showToast(tips);
    }

    public static void showToast(final int tips){
        showToast(tips);
    }*/
    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容
     *
     * @param tips 要显示的内容
     *
     */
    public static void showToast(final String tips) {
        if (android.text.TextUtils.isEmpty(tips)) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(App.getContext(), tips,Toast.LENGTH_SHORT);
                    mToast.show();
                } else {
                    //mToast.cancel();
                    //mToast.setView(mToast.getView());
                    mToast.setText(tips);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                    mToast.show();
                }
            }
        });
    }

  /*  public static void showToast(final int tips, final int duration) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(App.getContext(), tips, duration);
                    mToast.show();
                } else {
                    mToast.setText(tips);
                    mToast.setDuration(duration);
                    mToast.show();
                }
            }
        });
    }*/
}
