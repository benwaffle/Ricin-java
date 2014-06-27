package com.benwaffle;

import im.tox.jtoxcore.*;
import im.tox.jtoxcore.callbacks.CallbackHandler;
import im.tox.jtoxcore.callbacks.OnFriendRequestCallback;

import java.io.*;
import java.net.URL;
import java.security.cert.*;

import javax.net.ssl.*;
import javax.swing.JOptionPane;

import com.benwaffle.Servers.Server;
import com.benwaffle.toximpl.*;
import com.google.gson.Gson;

public class Test {
	static Servers getServersFromJson() throws Exception {
		HttpsURLConnection conn = (HttpsURLConnection)
				new URL("https://kirara.ca/poison/Nodefile.json").openConnection();

		SSLContext ctx = SSLContext.getInstance("SSL");
		ctx.init(null, new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {return null;}
			public void checkServerTrusted(X509Certificate[] a, String b) throws CertificateException {}
			public void checkClientTrusted(X509Certificate[] a, String b) throws CertificateException {}
		}}, null);
		conn.setSSLSocketFactory(ctx.getSocketFactory());

		InputStreamReader read = new InputStreamReader(conn.getInputStream());
		return (Servers) new Gson().fromJson(read, Servers.class);
	}

	private static FriendList<Friend> friends = new FriendsList(); 
	private static CallbackHandler<Friend> cbHandler = new CallbackHandler<>(friends);
	private static JTox<Friend> api;
	
	// because i can't into UI
	static void registerCallbacks(){
		cbHandler.registerOnMessageCallback((friend, msg) -> {
			System.out.println(friend.getName() + " -> " + msg);
		});
		
		cbHandler.registerOnFriendRequestCallback((pubkey, msg) -> {
			System.out.println("frq -> " + pubkey);
			if (JOptionPane.showConfirmDialog(null,
					"accept friend request?\n" + msg) == JOptionPane.YES_OPTION)
				try {
					api.confirmRequest(pubkey);
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
	}
	
	public static void main(String[] args) {
		Server s;
		try {
			s = getServersFromJson().servers[0];
		} catch (Exception e) {
			System.err.println("Error: failed to get bootstrap servers");
			return;
		}
		
		try {
			api = new JTox<>(friends, cbHandler);
		} catch (ToxException e) {
			System.err.println("Error: something happened in libtoxcore");
			return;
		}
		
		try {
			api.bootstrap(s.ipv4, s.port, s.pubkey);
		} catch (Exception e) {
			System.err.println("Error: couldn't bootstrap to tox network");
			return;
		}
		
		registerCallbacks();
		
		new Thread(() -> {
			try {
				System.out.println("my address: " + api.getAddress());
			} catch (ToxException e1) {
				e1.printStackTrace();
			}
			
			while (true) {
				try {
					api.doTox();
				} catch (Exception e) {
					System.err.println("Error: tox loop was interrupted");
				}
			}
		}).start();
	}
}
