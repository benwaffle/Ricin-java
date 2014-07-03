package com.benwaffle;

import com.benwaffle.model.Friend;
import com.benwaffle.model.FriendsList;
import com.benwaffle.view.RicinController;
import com.benwaffle.util.NetJSON;
import im.tox.jtoxcore.*;
import im.tox.jtoxcore.callbacks.CallbackHandler;

import javax.swing.JOptionPane;

import com.benwaffle.Servers.Server;

public class Test {
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
		Server s = NetJSON.getServersFromNet().get(0);
		if (s == null) {
            //error
            System.exit(1);
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

        RicinController.setApi(api);
        Ricin.main(args);
	}
}
