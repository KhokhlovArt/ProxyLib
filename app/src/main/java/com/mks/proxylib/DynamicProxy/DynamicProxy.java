package com.mks.proxylib.DynamicProxy;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DynamicProxy {

    public static ArrayList<ProxyParams> AdditionalProxys;
    int timeout = 0;

    //Метод выкачивающий с сервера конфигурационные файлы и обновляющий настройки
    public static void updateNodes(Context cnt)
    {
        new updateLoaders().setCnt(cnt).execute();
    }

    //Метод устанавливает Proxy
    public static void setProxy(Context cnt)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Включаем проксирование
        List<ProxyParams> listOfProxy = getProxys(cnt);

        for(ProxyParams p: listOfProxy)
        {

            boolean hasConnection = true;
            for (int i = 0 ; i < C.ARR_URL_TO_CHECK.length ; i++) {
                //Если доступ появился, выходим.
                if (new HttpsConnectionServicer().checkInternetConnection(C.ARR_URL_TO_CHECK[i]) == false) {
                    hasConnection = false;
                    break;
                }
            }

            if (hasConnection)
            {
                break;
            }

            Logger.log("" + p.getHost());
            String host = p.getHost();
            String port = p.getPort();
            String getHTTPUsername = p.getUsername();
            String getHTTPPassword = p.getPassword();

//Logger.log("" + host + ":" + port + " [" + getHTTPUsername + "/" + getHTTPPassword + "]");
            Properties properties  = System.getProperties();
            properties.setProperty("http.proxyHost", host);
            properties.setProperty("http.proxyPort", port);
            properties.setProperty("https.proxyHost", host);
            properties.setProperty("https.proxyPort", port);

            System.getProperties().put("http.proxyUser", getHTTPUsername);
            System.getProperties().put("http.proxyPassword", getHTTPPassword);
            System.getProperties().put("https.proxyUser", getHTTPUsername);
            System.getProperties().put("https.proxyPassword", getHTTPPassword);
            Authenticator.setDefault(new ProxyAuthenticator(getHTTPUsername, getHTTPPassword));
        }

        //Скачиваем новые настройки
        updateNodes(cnt);
    }



    private static List<ProxyParams> getProxys(Context cnt)
    {
        List<ProxyParams> proxys = C.getDefaultProxys();


        String additionalProxys = SharedPreferencesServicer.getPreferences(cnt, C.SPF_SES_SAVED_PROXYS, C.SPF_KEY_SAVED_PROXYS, "");
        if (additionalProxys != null) {
            try {
                JSONArray additionalLinksArray = new JSONArray(additionalProxys);

                for (int i = 0; i < additionalLinksArray.length(); i++) {
                    JSONObject proxy = additionalLinksArray.getJSONObject(i);
                    if(proxy != null) {
                        ProxyParams newProxy = new ProxyParams();

                        newProxy.setHost(proxy.getString("host"))
                                .setPort(proxy.getString("port"))
                                .setPassword(proxy.getString("password"))
                                .setUsername(proxy.getString("login"))
                                .setTimeout(proxy.getInt("timeout"));
                        proxys.add(newProxy);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return proxys;
    }

    private static List<String> getConfLinks(Context cnt)
    {

        List<String> links = C.getDefaultConfLink();

        String additionalLinks = SharedPreferencesServicer.getPreferences(cnt, C.SPF_SES_SAVED_LINKS, C.SPF_KEY_SAVED_LINKS, "");
        if (additionalLinks != null) {
            try {
                JSONArray additionalLinksArray = new JSONArray(additionalLinks);

                for (int i = 0; i < additionalLinksArray.length(); i++) {
                    links.add(additionalLinksArray.getString(i));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return links;
    }

    private static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            File file = new File(fileName.toString());
            FileInputStream is = new FileInputStream(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static JSONObject getJsonObj(String json_str)
    {
        if(json_str == null) {return null;}
        boolean isFileCript = false;
        JSONObject rootObj = null;
        try {
            //Если пришёл файл в не закодированном виде, то просто формируем из него JSON
            rootObj = new JSONObject(json_str);
        } catch (JSONException e) {
            //Если мы не смогли сформировать JSON, то скорее всего файл просто зашифрован, расшифровываем его и пробуем снова
            isFileCript = true;
        }

        if (isFileCript){
            try {
                CryptoProviderServicer cript = new CryptoProviderServicer();
                String decode_json_string = new String(cript.toByte(cript.deCript(json_str)));
                rootObj = new JSONObject(decode_json_string);
            } catch (JSONException e2) {
                e2.printStackTrace();
                Logger.log("Ошибка загрузки конфигурационного файла. Ошибка: " + e2.getMessage());
            }
        }

        if (rootObj == null) {//Если ничего не скачалось - выходим
            Logger.log("Не получилось разобрать JSON конфигурационного файла");
            return null;
        }
        return rootObj;
    }

//*************************************************************************************************************
    private static class updateLoaders extends AsyncTask<Void, Void, Void> {
        Context cnt;
        updateLoaders setCnt(Context _cnt){
            cnt = _cnt;
            return this;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<String> links = getConfLinks(cnt);//C.getDefaultConfLink();
            for(String path : links)
            {
                boolean res = FilesLoader.saveFile(cnt, path, true, C.ConfigFilePathZip(cnt), C.getBasePath(cnt), C.CONFIG_FILE_NAME_ZIP);
                if (res) {
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            String json_str = loadJSONFromAsset(cnt, C.ConfigFilePath(cnt));
            Logger.log(json_str);
            JSONObject rootObj = getJsonObj(json_str);
            if (rootObj == null) {
                return;
            }
            Logger.log("rootObj = " + rootObj);
            try {
                JSONArray proxys = rootObj.getJSONArray("proxys");
                Logger.log("proxys = " + proxys);
                if (proxys != null)
                {
                    SharedPreferencesServicer.setPreferences(cnt, C.SPF_SES_SAVED_PROXYS, C.SPF_KEY_SAVED_PROXYS, proxys.toString());
//                    String newProxys = "[";
//                    for (int i = 0; i< proxys.length(); i++)
//                    {
//                        JSONObject proxy = proxys.getJSONObject(i);
//                        if(proxy != null) {
//                            newProxys += "{";
//                            newProxys += "\"host\": \""+ proxy.getString("host") + "\",";
//                            newProxys += "\"port\": \""+ proxy.getString("port") + "\",";
//                            newProxys += "\"login\": \""+ proxy.getString("login") + "\",";
//                            newProxys += "\"password\": \""+ proxy.getString("password") + "\",";
//                            newProxys += "\"timeout\": \""+ proxy.getString("timeout") + "\",";
//                            newProxys += "},";
//                        }
//                    }
//                    newProxys += "]";
//                    SharedPreferencesServicer.setPreferences(cnt, C.SPF_SES_SAVED_PROXYS, C.SPF_KEY_SAVED_PROXYS, newProxys);
                }
                Logger.log("---------------");
                JSONArray paths = rootObj.getJSONArray("paths");
                if (paths != null)
                {
                    SharedPreferencesServicer.setPreferences(cnt, C.SPF_SES_SAVED_LINKS, C.SPF_KEY_SAVED_LINKS, paths.toString());
//                    String newPaths = "";
//                    for (int i = 0; i< paths.length(); i++)
//                    {
//                        newPaths += paths.getString(i) + "_!SEPARATOR!_";
//                        Logger.log("" + paths.getString(i));
//                    }
//                    SharedPreferencesServicer.setPreferences(cnt, C.SPF_SES_SAVED_LINKS, C.SPF_KEY_SAVED_LINKS, newPaths);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
