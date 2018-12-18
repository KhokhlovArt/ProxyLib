package com.mks.proxylib.DynamicProxy;

import android.content.Context;

public class SharedPreferencesServicer {
    public static void setPreferences(Context cnt, String session, String key, String value)
    {
        if (value == null){return;}
        CryptoProviderServicer criptObj = new CryptoProviderServicer();
        String codeText = criptObj.cript(value);
        cnt.getSharedPreferences(session, Context.MODE_PRIVATE).edit().putString(key, codeText).apply();
    }

    //Метод чтения из SharedPreferences с раскодированием строки
    public static String getPreferences(Context cnt, String session, String key, String default_res)
    {
        String res = cnt.getSharedPreferences(session, Context.MODE_PRIVATE).getString(key, default_res);
        if ((res == null)||(res.equals(""))) {return null;}
        CryptoProviderServicer criptObj = new CryptoProviderServicer();
        return criptObj.deCript(res);
    }

    //Метод сохранения в SharedPreferences без кодированием строки
    public static void setSimplePreferences(Context cnt, String session, String key, String value)
    {
        if (value == null){return;}
        cnt.getSharedPreferences(session, Context.MODE_PRIVATE).edit().putString(key, value).apply();
    }

    //Метод чтения из SharedPreferences без раскодированием строки
    public static String getSimplePreferences(Context cnt, String session, String key, String default_res)
    {
        String res = cnt.getSharedPreferences(session, Context.MODE_PRIVATE).getString(key, default_res);
        if (res == null){return null;}
        return res;
    }
}
