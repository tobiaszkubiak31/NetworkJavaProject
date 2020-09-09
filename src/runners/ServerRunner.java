package runners;

import config.SystemConsts;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import server.tcpserver.ServerTCP;
import server.udpresponder.MulticastAddressResponder;

public class ServerRunner {

	private static final Random RANDOM = new Random();
	private static int tcpPort;
	private static int udpPort = 7;
	private static InetAddress serverIpAddress;

	public static void main(String args[]) throws IOException {
		tcpPort = randomTcpPort();
		String ipAddress = SystemConsts.multicastGroupIp;
		MulticastAddressResponder multicastResponder = new MulticastAddressResponder(ipAddress, udpPort,
			tcpPort);
		Thread t1 = new Thread(multicastResponder);
		t1.start();
		ServerTCP serverTcp = new ServerTCP(tcpPort);
		serverIpAddress = serverTcp.getServerIp();
		multicastResponder.setServerIpAddress(serverIpAddress);
		serverTcp.startListening();
	}



	private static int randomTcpPort() {
		int low = 10;
		int high = 30;
		return RANDOM.nextInt(high - low) + low;
	}

}
