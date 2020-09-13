package client.udpsender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;

public class MulticastAddressService {

	public static final int SECONDS_5 = 5000;
	private static DatagramSocket datagramSocket;

	public static HashMap<String, Integer> requestForAddresses(String message, String groupAddress,
		int port) {
		try {
			System.out.println("MulticastAddressService :Send message for port 7 and udp group address");
			sendMulticastMessage(message, InetAddress.getByName(groupAddress), port);
			System.out.println("MulticastAddressService: Wait for response from server...");
			return getResponseWithAddresses();
		} catch (UnknownHostException e) {
			System.out.println("Unknown host problem");
			datagramSocket.close();
		} catch (SocketException e) {
			System.out.println("Problem with connection");
			datagramSocket.close();
		} catch (IOException e) {
			System.out.println(e);
			datagramSocket.close();
		}
		return new HashMap<>();
	}

	private static void sendMulticastMessage(String message, InetAddress groupAddres, int port)
		throws IOException {
		datagramSocket = new DatagramSocket();
		byte[] buf = message.getBytes(Charset.forName("UTF-8"));
		DatagramPacket packet = new DatagramPacket(buf, buf.length, groupAddres, port);

		datagramSocket.send(packet);
		datagramSocket.close();
	}

	private static HashMap<String, Integer> getResponseWithAddresses() throws IOException {
		HashMap<String, Integer> ips;
		byte[] buf = new byte[256];

		datagramSocket = new DatagramSocket(8);
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		datagramSocket.setSoTimeout(SECONDS_5);

		datagramSocket.receive(packet);
		String receivedServersRoutes = new String(
			packet.getData(), 0, packet.getLength());
		ips = parseIpAndPortToMap(new StringReader(receivedServersRoutes));
		datagramSocket.close();
		System.out.println("MulticastAddressService: Respond received");
		return ips;
	}

	private static HashMap<String, Integer> parseIpAndPortToMap(Reader routesCsv)
		throws IOException {
		HashMap<String,Integer> serversAdresses = new HashMap();
		BufferedReader routesBuffer = new BufferedReader(routesCsv);
		String line;
		while ((line = routesBuffer.readLine()) != null) {
			String[] ipAndPort = line.split(",");
			String ip = ipAndPort[0];
			Integer port = Integer.valueOf(ipAndPort[1]);
			serversAdresses.put(ip, port);
		}
		return serversAdresses;
	}


	private static boolean validateIp(final String ip) {
		String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
		return ip.matches(PATTERN);
	}
}
