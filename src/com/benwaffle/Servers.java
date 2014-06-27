package com.benwaffle;

import java.util.Arrays;

public class Servers {
	Server[] servers;

	public static class Server {
		public String owner, pubkey, ipv4, ipv6;
		public int port;

		public String toString() {
			return "Server [owner=" + owner + ", pubkey=" + pubkey + ", ipv4="
					+ ipv4 + ", ipv6=" + ipv6 + ", port=" + port + "]\n";
		}
	}

	public String toString() {
		return Arrays.toString(servers);
	}
}
