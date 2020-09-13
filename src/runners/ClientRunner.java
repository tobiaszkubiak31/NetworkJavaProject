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
	private static final int SECONDS_10 = 10000;
	private static final int ONE_SECOND = 1000;
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		while(true) {
			MulticastAddressService multicastAddressService = new MulticastAddressService();
			HashMap<String, Integer> addresses = multicastAddressService
				.requestForAddresses(message, ipAddress, UDP_PORT);
			if (addresses.isEmpty()) {
				System.out.println("Server ip not found, client will try to reconnect after 10 seconds");
				waitForReconnect(SECONDS_10);
				continue;
			}
			printAllRoutes(addresses);

			System.out.println("Podaj ip serwera:");
			String selectedIp = scan.nextLine();
			Integer selectedPort = addresses.get(selectedIp);

			multicastAddressService = null;
			System.gc();
			TCPClient TCPClient = new TCPClient(selectedIp, selectedPort);
			TCPClient.connectToServer();
		}
	}

	private static void printAllRoutes(HashMap<String, Integer> addresses) {
		for (String ipServer : addresses.keySet()) {
			System.out.println("ip: " + ipServer + " port: " + addresses.get(ipServer));
		}
	}

	private static void waitForReconnect(long timeMs) {
		for (int i = SECONDS_10; i > 0; i--) {
			try {
				Thread.sleep(ONE_SECOND);
				System.out.print(i + "...");
			} catch (InterruptedException e) {
				System.out.println("Client Runner :Problem With ThreadSleep");
			}
		}
		System.out.println("Reconnecting");

	}
}
