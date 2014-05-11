package br.com.unirio.moodle.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import br.com.unirio.moodle.Constants;

/**
 * Created by davi.alves on 11/05/2014.
 */
public class SessionService {
    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UnirioMoodle";

    private static final String KEY_USER_NAME = "userName";

    @SuppressLint("CommitPrefEdits")
    public SessionService(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void insertLogin(String username) {
        editor.putString(KEY_USER_NAME, username);
        editor.commit();
    }

    public String getKeyUserName() {
        return pref.getString(KEY_USER_NAME, Constants.EMPTY);
    }

}
