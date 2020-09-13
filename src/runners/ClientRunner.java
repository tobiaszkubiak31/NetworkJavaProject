package runners;

import client.tcpclient.TCPClient;
import client.udpsender.MulticastAddressService;
import config.SystemConsts;
import java.util.HashMap;
import java.util.Scanner;

public class ClientRunner {

	private static final int UDP_PORT = 7;
	private static final String ipAddress = SystemConsts.multicastGroupIp;
	private static final String message = "DISCOVERY";
	private static final long TIME_10S = 10000;
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		MulticastAddressService multicastAddressService = new MulticastAddressService();
		HashMap<String, Integer> addresses = multicastAddressService
			.requestForAddresses(message, ipAddress, UDP_PORT);
		if (addresses.isEmpty()) {
			System.out.println("Server ip not found");
			waitForReconnect(TIME_10S);
		}
		printAllRoutes(addresses);

		System.out.println("Podaj ip serwera:");
		String selectedIp = scan.nextLine();
		Integer selectedPort = addresses.get(selectedIp);

		multicastAddressService = null;
		System.gc();
		TCPClient TCPClient = new TCPClient(selectedIp, selectedPort);
		TCPClient.run();
	}

	private static void printAllRoutes(HashMap<String, Integer> addresses) {
		for (String ipServer : addresses.keySet()) {
			System.out.println("ip: " + ipServer + " port: " + addresses.get(ipServer));
		}
	}

	private static void waitForReconnect(long timeMs) {
		try {
			Thread.sleep(timeMs);
		} catch (InterruptedException e) {
			System.out.println("Client Runner :Problem With ThreadSleep");
		}
	}
}
