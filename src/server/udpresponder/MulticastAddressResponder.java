package server.udpresponder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Map;

public class MulticastAddressResponder {

	public static final int TIME_500MS = 500;
	protected MulticastSocket socket = null;
	private int port;
	private int tcpPort;
	private Map<String, String> serversData;
	private String ipAddress;

	public MulticastAddressResponder(String ipAddress, int port, int tcpPort,
		Map<String, String> serversData) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.tcpPort = tcpPort;
		this.serversData = serversData;
	}


	//    public static void main(String[] args) {
////        Scanner in = new Scanner(System.in);
//        //:todo rand port, now u dont know the range
//        int port = 7;
////        String input;
////        System.out.println("Type ip:");
////        input = in.nextLine();
////        String ipAddress = input.toString();
//        String ipAddress = SystemConsts.multicastGroupIp;
//        MulticastReceiver multicastReceiver = new MulticastReceiver(ipAddress,port, tcpPort);
//        multicastReceiver.run();
//    }
	public void startResponding() {
		try {
			socket = new MulticastSocket(port);
			InetAddress group = InetAddress.getByName(this.ipAddress);
			socket.joinGroup(group);

			while (true) {
				byte[] buf = new byte[65535];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				String received = new String(
					packet.getData(), 0, packet.getLength());
				System.out.println(received);
				if ("DISCOVERY".equals(received)) {
					sendResponsesWithIp(packet);
				}
				if ("exit".equals(received)) {
					break;
				}
			}
			socket.leaveGroup(group);
			socket.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sendResponsesWithIp(DatagramPacket packetToSend)
		throws IOException, InterruptedException {
		waitForClientReceiver(TIME_500MS);
		String serverInformation = parseToCsvServerInformation(serversData);
		byte[] buf = serverInformation.getBytes();
		packetToSend = new DatagramPacket(buf, buf.length, packetToSend.getAddress(), 8);
		socket.send(packetToSend);
	}

	private String parseToCsvServerInformation(Map<String, String> serversData) {
		StringBuilder result = new StringBuilder("");
		for (String serverIp : serversData.keySet()) {
			String serverPort = serversData.get(serverIp);
			result.append(serverIp + "," + serverPort + "\n");
		}
		return result.toString();
	}

	private void waitForClientReceiver(long timeMs) throws InterruptedException {
		Thread.sleep(timeMs);
	}

}
