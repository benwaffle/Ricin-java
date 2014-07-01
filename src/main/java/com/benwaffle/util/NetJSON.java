package com.benwaffle.util;

import com.benwaffle.Servers;
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

    public static Servers getServersFromNet() {
        try {
            HttpsURLConnection conn = // open connection
                    (HttpsURLConnection) new URL("https://kirara.ca/poison/Nodefile.json").openConnection();

            conn.setSSLSocketFactory(getDummySSLCtx().getSocketFactory()); // accept any SSL cert
            InputStreamReader read = new InputStreamReader(conn.getInputStream()); // reader for connection
            return (Servers) new Gson().fromJson(read, Servers.class); // parse json to Servers object
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
