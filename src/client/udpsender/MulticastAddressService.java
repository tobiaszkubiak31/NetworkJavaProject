package client.udpsender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.logging.Level;

public class MulticastAddressService {

	private static DatagramSocket datagramSocket;


	public static HashMap<String,String> requestForAddresses(String message, String groupAddress, int port) {
		try {
			sendMulticastMessage(message, InetAddress.getByName(groupAddress), port);
			return getResponseWithAdressess();
		} catch (UnknownHostException e) {
			System.out.println("Unknown host problem");
		} catch (SocketException e) {
			System.out.println("Problem with connection");
		} catch (IOException e) {
			System.out.println(e);
		}
		return new HashMap<>();
	}

	private static void sendMulticastMessage(String message, InetAddress groupAddres, int port) throws IOException {
		datagramSocket = new DatagramSocket();
		byte[] buf = message.getBytes(Charset.forName("UTF-8"));
		DatagramPacket packet = new DatagramPacket(buf, buf.length, groupAddres, port);

		datagramSocket.send(packet);
		datagramSocket.close();
	}

	private static HashMap<String,String> getResponseWithAdressess() throws IOException {
		HashMap<String,String> ips = new HashMap<>();
		byte[] buf = new byte[256];

		datagramSocket = new DatagramSocket(8);
		DatagramPacket packet = new DatagramPacket(buf, buf.length);

		datagramSocket.receive(packet);
		String received = new String(
			packet.getData(), 0, packet.getLength());
		System.out.printf(received);

		String[] ip = received.split(",");
		ips.put(ip[0],ip[1]);
		datagramSocket.close();
		return ips;
	}



	private static boolean validateIp(final String ip) {
		String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
		return ip.matches(PATTERN);
	}
}
