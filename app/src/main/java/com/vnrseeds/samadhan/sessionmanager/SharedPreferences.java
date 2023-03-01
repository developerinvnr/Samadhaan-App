package com.vnrseeds.samadhan.sessionmanager;

import android.content.Context;

import com.google.gson.Gson;

public class SharedPreferences {
    private static final String TAG_TOKEN = "tagtoken";
    private final android.content.SharedPreferences pref;
    // Editor reference for Shared preferences
    private final android.content.SharedPreferences.Editor editor;
    private static final String PREFER_NAME = "ACapPref";
    public static final String KEY_LOGIN_OBJ = "key_login_obj";
    public static final String KEY_ADD_ASDD_OBJ = "asset_category_obj";
    public static final String KEY_CPUMODELS_OBJ = "cpu_models_obj";
    public static final String KEY_TICKET_OBJ = "ticket_obj";
    public static final String KEY_ROLES_OBJ = "roles_obj";
    public static final String KEY_NOTIFICATION_OBJ = "roles_obj";
    public static final String KEY_NOTIFICATIONLIST_OBJ = "notificationlist_obj";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_NOTIFICATION_TOKEN = "NotificationToken";
    public static final String KEY_TICKET_INFO = "ticketInfo";

    private static SharedPreferences sSharedPrefs;

    public SharedPreferences(Context context) {
        pref = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }


    public static SharedPreferences getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new SharedPreferences(context.getApplicationContext());
        }
        return sSharedPrefs;
    }

    public static SharedPreferences getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }

        //Option 1:
        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
        //Option 2:
        // Alternatively, you can create a new instance here
        // with something like this:
        // getInstance(MyCustomApplication.getAppContext());
    }

    public void storeObject(String key, Object object) {
        Gson gson = new Gson();
        String json = null;
        if (object != null)
            json = gson.toJson(object);
        editor.putString(key, json);
        editor.commit();
    }

    public Object getObject(String key, Class cls) {
        Gson gson = new Gson();
        String json = pref.getString(key, "");
        if (!json.equals("")) {
            return gson.fromJson(json, cls);
        } else {
            return null;
        }
    }

    public void createString(String string, String key) {
        editor.putString(key, string);
        editor.commit();
    }

    public String getString(String key) {
        return pref.getString(key, null);
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        android.content.SharedPreferences sharedPreferences = pref;
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        android.content.SharedPreferences sharedPreferences = pref;
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }

    public void clearSharedPref(){
        editor.clear();
        editor.commit();
    }
}
