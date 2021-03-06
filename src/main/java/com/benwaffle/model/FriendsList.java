package com.benwaffle.model;

import im.tox.jtoxcore.*;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * A group of Tox friends
 * @author Ben
 */
public class FriendsList implements FriendList<Friend> {
	/**
	 * A mapping of (friend number -> Friend object)
	 */
	private HashMap<Integer, Friend> friends;

	/**
	 * Gets the friends from the HashMap as a stream for easy processing
	 */
	private Stream<Friend> stream() {
		return friends.values().stream();
	}

	/**
	 * Just instantiate the HashMap
	 */
	public FriendsList() {
		friends = new HashMap<>();
	}

	@Override
	public String toString() {
		return stream().map(Object::toString).collect(joining(", "));
	}
	
	public Friend addFriend(int friendNum) throws FriendExistsException {
		if (friends.get(friendNum) != null)
			throw new FriendExistsException(friendNum);

		Friend f = new Friend(friendNum);
		friends.put(friendNum, f);
		return f;
	}

	public Friend addFriendIfNotExists(int friendNum) {
		try {
			return addFriend(friendNum);
		} catch (FriendExistsException e) {
			return friends.get(friendNum);
		}
	}

	public List<Friend> all() {
		return stream().collect(toList());
	}

	public Friend getByFriendNumber(int friendNum) {
		return friends.get(friendNum);
	}
	
	public Friend getById(String id) {
		return stream()
				.filter(f -> f.getId().equals(id))
				.findFirst()
				.orElse(null); // findFirst() returns an Optional<Friend>
	}
	
	public List<Friend> getByName(String name, boolean ignorecase) {
		return stream()
				.filter(f -> {
					return ignorecase ?
							name.equalsIgnoreCase(f.getName()) :
							name.equals(f.getName());
				})
				.collect(toList());
	}

	public List<Friend> getByStatus(ToxUserStatus status) {
		return stream()
				.filter(f -> f.getStatus().equals(status))
				.collect(toList());
	}

	public List<Friend> getOfflineFriends() {
		return stream()
				.filter(f -> !f.isOnline())
				.collect(toList());
	}

	public List<Friend> getOnlineFriends() {
		return stream()
				.filter(f -> f.isOnline())
				.collect(toList());
	}

	public void removeFriend(int friendnumber) {
		friends.remove(friendnumber);
	}

	public List<Friend> searchFriend(String partial) {
		return stream()
				.filter(f -> f.getName().contains(partial))
				.collect(toList());
	}
}
