package runners;

import client.tcpclient.TCPClient;
import client.udpsender.MulticastAddressService;
import config.SystemConsts;
import java.util.HashMap;
import java.util.Map.Entry;

public class ClientRunner {

	private static final int UDP_PORT = 7;
	private static final String ipAddress = SystemConsts.multicastGroupIp;
	private static final String message = "DISCOVERY";

	public static void main(String[] args) {
		MulticastAddressService multicastAddressService = new MulticastAddressService();
		HashMap<String, String> addresses = multicastAddressService
			.requestForAddresses(message, ipAddress, UDP_PORT);
		if (addresses.isEmpty()) {
			//wait 10 sec and retry
			System.out.println("didnt found server");
		}
		multicastAddressService = null;
		System.gc();

		Entry<String, String> next = addresses.entrySet().iterator().next();

		TCPClient TCPClient = new TCPClient("192.168.56.1", Integer.valueOf(next.getValue()));
		TCPClient.run();


	}
}
