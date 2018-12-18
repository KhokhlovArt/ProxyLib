package com.mks.proxylib.DynamicProxy;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsConnectionServicer {
    int timeout = 0;

    TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers(){return null;}
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType){}
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType){}
            }
    };

    private static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }
    //Метод который возвращает HttpsURLConnection, при этом не переключает Proxy
    public HttpsURLConnection getSimpleHttpsConnection(URL url) throws CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        HttpsURLConnection connection = null;
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

//        String pass = "";
//        KeyStore clientStore = null;
//        clientStore = KeyStore.getInstance("PKCS12");
//        String cert = "";
//        InputStream is = new ByteArrayInputStream(toByte(cert));
//
//        clientStore.load(is, pass.toCharArray());
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        kmf.init(clientStore, pass.toCharArray());

//        KeyManager[] kms = kmf.getKeyManagers();
        SSLContext sc = SSLContext.getInstance("SSL");
//        sc.init(kms, trustAllCerts, new java.security.SecureRandom());
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        connection = (HttpsURLConnection) url.openConnection();
        if (timeout != 0){connection.setConnectTimeout(timeout);}
        connection.setHostnameVerifier(hostnameVerifier);

        String authString = "pmorgay" + ":" + "V3u5ZkW";
        String authStringEnc = Base64.encodeToString( authString.getBytes(), Base64.DEFAULT );
        connection.addRequestProperty("Proxy-Authorization", "Basic " + authStringEnc);

        return connection;
    }

    public boolean checkInternetConnection(String path) {
        Boolean result = false;
        HttpsURLConnection connection = null;
        try {
            connection = getSimpleHttpsConnection(new URL(path));
            if (connection != null) {
                connection.connect();
                int resCode = connection.getResponseCode();
                Logger.log("code = " + connection.getResponseCode());
                result = ((resCode == HttpURLConnection.HTTP_OK) || (resCode == HttpURLConnection.HTTP_FORBIDDEN) || (resCode == HttpURLConnection.HTTP_BAD_METHOD));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
