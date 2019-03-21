package com.mks.proxylib.DynamicProxy;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class C {
    public static String SPF_SES_SAVED_PROXYS = "ses_saved_proxys"; //Сессия для покоторой считываются сохраненные прокси
    public static String SPF_KEY_SAVED_PROXYS = "key_saved_proxys"; //Ключ по покоторому считываются сохраненные прокси
    public static String SPF_SES_SAVED_LINKS = "ses_saved_links";   //Сессия для покоторой считываются сохраненные ссылки для полуучения конфигурационного файла
    public static String SPF_KEY_SAVED_LINKS = "key_saved_links";   //Ключ по покоторому считываются сохраненные  ссылки для полуучения конфигурационного файла
    public static String URL_TO_CHECK = "https://ya.ru/";           // URL по которому проверяем соединение с интернетом ( нужно ли включать проксирование)

    //Список URL по которому проверяем соединение с интернетом ( нужно ли включать проксирование)
    public static String [] ARR_URL_TO_CHECK = {/*"https://prestable.voiceservices.yandex.net",*/ "https://voicestation.yandex.net",/* "https://voiceservices.yandex.net"/*, "https://uniproxy.alice.yandex.net"*/};

    public static String ConfigFilePathZip(Context cnt) {return  "" + getBasePath(cnt) + CONFIG_FILE_NAME_ZIP;}
    public static String ConfigFilePath(Context cnt)    {return  "" + getBasePath(cnt) + CONFIG_FILE_NAME;}
    public static String CONFIG_FILE_NAME_ZIP = "config.zip";
    public static String CONFIG_FILE_NAME     = "proxy_config.json";


    public static String getBasePath(Context cnt) //путь по которому сохраняются скаченные файлы (настроечные, dex-файлы, динамические файлы маски...)
    {
        return  "" + cnt.getCacheDir()+ File.separator;
//       return  "" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator;
    }


    //Получение списка прокси серверов по умолчанию
    static public List<ProxyParams> getDefaultProxys()
    {
        List<ProxyParams> proxys = new ArrayList<ProxyParams>();

        proxys.add(new ProxyParams().setHost("212.129.1.232").setPort("80").setUsername("").setPassword("").setTimeout(0)); //Наш
        proxys.add(new ProxyParams().setHost("195.201.251.246").setPort("65233").setUsername("").setPassword("").setTimeout(0)); //Берлин
        proxys.add(new ProxyParams().setHost("195.201.252.9").setPort("65233").setUsername("").setPassword("").setTimeout(0)); //Берлин
        proxys.add(new ProxyParams().setHost("195.201.252.13").setPort("65233").setUsername("").setPassword("").setTimeout(0));//Берлин
        proxys.add(new ProxyParams().setHost("195.201.252.40").setPort("65233").setUsername("").setPassword("").setTimeout(0));//Берлин
        proxys.add(new ProxyParams().setHost("195.201.252.55").setPort("65233").setUsername("").setPassword("").setTimeout(0));//Берлин
        proxys.add(new ProxyParams().setHost("195.201.252.93").setPort("65233").setUsername("").setPassword("").setTimeout(0));//Берлин
        proxys.add(new ProxyParams().setHost("195.201.252.234").setPort("65233").setUsername("").setPassword("").setTimeout(0));//Берлин
        proxys.add(new ProxyParams().setHost("195.201.252.251").setPort("65233").setUsername("").setPassword("").setTimeout(0));//Берлин
        proxys.add(new ProxyParams().setHost("195.201.253.10").setPort("65233").setUsername("").setPassword("").setTimeout(0));//Берлин
        proxys.add(new ProxyParams().setHost("195.201.253.15").setPort("65233").setUsername("").setPassword("").setTimeout(0));//Берлин
        proxys.add(new ProxyParams().setHost("195.201.23.138").setPort("65233").setUsername("").setPassword("").setTimeout(0)); //Спб

//        proxys.add(new ProxyParams().setHost("191.101.148.97").setPort("65233").setUsername("pmorgay").setPassword("V3u5ZkW").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("181.215.83.183").setPort("65233").setUsername("pmorgay").setPassword("V3u5ZkW").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("144.208.126.38").setPort("65233").setUsername("pmorgay").setPassword("V3u5ZkW").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("146.71.87.241").setPort("65233").setUsername("pmorgay").setPassword("V3u5ZkW").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("154.16.127.139").setPort("65233").setUsername("pmorgay").setPassword("V3u5ZkW").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("198.8.86.75").setPort("65233").setUsername("pmorgay").setPassword("V3u5ZkW").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("192.252.210.94").setPort("65233").setUsername("pmorgay").setPassword("V3u5ZkW").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("216.21.9.64").setPort("65233").setUsername("pmorgay").setPassword("V3u5ZkW").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("107.181.175.52").setPort("65233").setUsername("pmorgay").setPassword("V3u5ZkW").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("45.63.14.157").setPort("65233").setUsername("pmorgay").setPassword("V3u5ZkW").setTimeout(0));

//        proxys.add(new ProxyParams().setHost("proxy01.appclick.org").setPort("80").setUsername("fakegaid").setPassword("jMs1pfIw9tz?").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("149.56.69.5").setPort("1080").setUsername("").setPassword("").setTimeout(0));
//        proxys.add(new ProxyParams().setHost("proxy02.appclick.org").setPort("8080").setUsername("fakegaid").setPassword("jMs1pfIw9tz?").setTimeout(0));
        return proxys;
    }

    //Получить ссылки на хранение конфигурационных файлов по умолчанию
    static public List<String> getDefaultConfLink()
    {
        List<String> links = new ArrayList<String>();
        links.add("https://files.smartgroup.ua/SettingsElari/proxy_config.zip");
        links.add("https://drive.google.com/a/adviator.com/uc?authuser=0&id=1sOiBIj0Dd_hXW5hNn00vsNjAsaoUfmpf&export=download");

        return links;
    }
}
