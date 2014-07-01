package com.benwaffle.model;

import im.tox.jtoxcore.ToxFriend;
import im.tox.jtoxcore.ToxUserStatus;

/**
 * Represents a Tox friend, including:
 * - name
 * - status message (custom string)
 * - status (none/away/busy/invalid)
 * - friend number (for tox core)
 * - online (boolean)
 * - typing (boolean)
 * 
 * @author Ben
 */
public class Friend implements ToxFriend {
	private int friendnumber;
	private String id, name, statusMessage;
	private ToxUserStatus status;
	private boolean online, typing;

	public Friend(int id) {
		friendnumber = id;
	}
	
	@Override
	public String toString() {
		return "Friend [name=" + name + ", id=" + id + ", status=" + statusMessage + ", online=" + online + "]";
	}
	
	public int getFriendnumber() {
		return friendnumber;
	}

	public void setFriendnumber(int friendnumber) {
		this.friendnumber = friendnumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public ToxUserStatus getStatus() {
		return status;
	}

	public void setStatus(ToxUserStatus status) {
		this.status = status;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isTyping() {
		return typing;
	}

	public void setTyping(boolean typing) {
		this.typing = typing;
	}

}
