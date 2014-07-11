package com.benwaffle.view;

import com.benwaffle.model.Friend;
import im.tox.jtoxcore.FriendList;
import im.tox.jtoxcore.callbacks.CallbackHandler;

public class RicinCallbacks {
    private CallbackHandler<Friend> cbHandler;

    public RicinCallbacks(FriendList<Friend> friends) {
        cbHandler = new CallbackHandler<>(friends);

        cbHandler.registerOnActionCallback((friend, action) ->
                System.out.println("* " + friend + " " + action));

        cbHandler.registerOnConnectionStatusCallback((friend, online) ->
                System.out.println(friend + " is now " + (online ? "online" : "offline")));

        cbHandler.registerOnFileControlCallback((friend, sending, fileNum, ctrlType, data) -> {
            System.out.printf("file transfer %d ", fileNum);
            switch (ctrlType) {
                case TOX_FILECONTROL_ACCEPT:
                    System.out.println("accepted " + (sending ? "to " : "from ") + friend);
                    break;
                case TOX_FILECONTROL_FINISHED:
                    System.out.println("finished");
                    break;
                case TOX_FILECONTROL_KILL:
                    System.out.println("killed");
                    break;
                case TOX_FILECONTROL_PAUSE:
                    System.out.println("paused");
                    break;
                case TOX_FILECONTROL_RESUME_BROKEN:
                    System.out.println("resumed");
                    break;
            }
        });

        cbHandler.registerOnFileDataCallback((friend, fileNum, data) -> {
            StringBuilder buf = new StringBuilder();
            for (byte b : data) buf.append((char) b);
            System.out.println(friend + " sent " + buf.toString());
        });

        cbHandler.registerOnFileSendRequestCallback((friend, fileNum, fileSize, fileName) -> {

        });

        cbHandler.registerOnFriendRequestCallback((pubkey, message) ->
                System.out.println("friend request: " + pubkey.substring(0, 10) + "... -> " + message));

        cbHandler.registerOnMessageCallback((friend, message) ->
                System.out.println(friend + " -> " + message));

        cbHandler.registerOnNameChangeCallback((friend, newName) ->
                System.out.println(friend + " is now known as " + newName));

        cbHandler.registerOnReadReceiptCallback((friend, receipt) ->
                System.out.println(friend + " read message " + receipt));

        cbHandler.registerOnStatusMessageCallback((friend, newStatus) ->
                System.out.println(friend + " updated status: " + newStatus));

        cbHandler.registerOnTypingChangeCallback((friend, typing) -> {
            if (typing)
                System.out.println(friend + " is typing");
        });

        cbHandler.registerOnUserStatusCallback((friend, status) ->
                System.out.println(friend + " is now " + status));
    }

    public CallbackHandler<Friend> get() {
        return cbHandler;
    }
}
