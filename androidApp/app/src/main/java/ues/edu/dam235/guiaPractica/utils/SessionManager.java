package ues.edu.dam235.guiaPractica.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "session";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER = "user";

    private final SharedPreferences pref;

    public SessionManager(Context context){
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveSession(String token, String user){
        pref.edit()
                .putString(KEY_TOKEN, token)
                .putString(KEY_USER, user)
                .apply();
    }

    public String getToken(){
        return pref.getString(KEY_TOKEN, null);
    }

    public String getUser(){
        return pref.getString(KEY_USER, null);
    }

    public boolean isLoggedIn(){
        return getToken() != null;
    }

    public void logout(){
        pref.edit().clear().apply();
    }
}
