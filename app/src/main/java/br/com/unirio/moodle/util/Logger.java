package br.com.unirio.moodle.util;

import android.util.Log;

import retrofit.RestAdapter;

/**
 * Created by davi.alves on 17/04/2014.
 */
public class Logger implements RestAdapter.Log{

    private static final String TAG = "MOODLE_UNIRIO";

    /**
     *
     * @param msg
     */
    public static void d(String msg)
    {
        Log.d(TAG, msg);
    }

    /**
     *
     * @param msg
     * @param args
     */
    public static void d(String msg, Object... args)
    {
        Log.d(TAG, String.format(msg, args));
    }

    /**
     *
     * @param msg
     */
    public static void i(String msg)
    {
        Log.i(TAG, msg);
    }

    /**
     *
     * @param msg
     * @param args
     */
    public static void i(String msg, Object... args)
    {
        Log.i(TAG, String.format(msg, args));
    }

    /**
     *
     * @param msg
     * @param args
     */
    public static void w(String msg, Object... args) { Log.w(TAG, String.format(msg, args)); }

    /**
     *
     * @param msg
     */
    public static void w(String msg) { Log.w(TAG, msg); }

    /**
     *
     * @param msg
     */
    public static void e(String msg)
    {
        Log.e(TAG, msg);
    }

    /**
     *
     * @param msg
     * @param e
     */
    public static void e(String msg, Throwable e)
    {
        Log.e(TAG, msg, e);
    }

    public static void e(String msg, Throwable e, Object... args)
    {
        Log.e(TAG, String.format(msg, args), e);
    }

    @Override
    public void log(String message) {
        d(message);
    }
}
