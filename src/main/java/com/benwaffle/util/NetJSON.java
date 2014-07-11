package com.benwaffle.util;

import com.benwaffle.model.ServerList;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class NetJSON {
    private final static String BOOTSTRAP_SERVERS_URL = "https://kirara.ca/poison/Nodefile.json";

    public static ServerList getServersFromNet() {
        try {
            HttpsURLConnection conn = // open connection
                    (HttpsURLConnection) new URL("https://kirara.ca/poison/Nodefile.json").openConnection();

            conn.setSSLSocketFactory(getDummySSLCtx().getSocketFactory()); // accept any SSL cert
            InputStreamReader read = new InputStreamReader(conn.getInputStream()); // reader for connection
            return (ServerList) new Gson().fromJson(read, ServerList.class); // parse json to ServerList object
        } catch (IOException | GeneralSecurityException e) {
            return null; // error? return null
        }
    }

    private static SSLContext getDummySSLCtx() throws GeneralSecurityException {
        SSLContext ctx = SSLContext.getInstance("SSL");
        ctx.init(null, new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {return null;}
            public void checkServerTrusted(X509Certificate[] a, String b) throws CertificateException {}
            public void checkClientTrusted(X509Certificate[] a, String b) throws CertificateException {}
        }}, null);

        return ctx;
    }
}
