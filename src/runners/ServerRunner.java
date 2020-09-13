package runners;

import utilis.SystemConsts;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import server.tcpserver.InterfacesFinder;
import server.tcpserver.ServerTCP;
import server.udpresponder.MulticastAddressResponder;

public class ServerRunner {

	private static final Random RANDOM = new Random();
	private static final String MULTICAST_GROUP_IP = SystemConsts.multicastGroupIp;
	private static int tcpPort;
	private static int udpPort = 7;

	public static void main(String[] args) throws IOException {
		List<String> routableIps = InterfacesFinder.getAllRoutableInterfacesIp();
		System.out.println(routableIps);
		Map<String, String> createdServersData = new HashMap();
		for (String routableIp : routableIps) {
			tcpPort = randomTcpPort();
			ServerTCP serverTcp = new ServerTCP(tcpPort, routableIp);
//			serverIpAddress = serverTcp.getServerIp();
			Thread t1 = new Thread(serverTcp);
			t1.start();
			createdServersData.put(routableIp,String.valueOf(tcpPort));
		}

		MulticastAddressResponder multicastResponder = new MulticastAddressResponder(
			MULTICAST_GROUP_IP, udpPort,
			tcpPort, createdServersData);
		multicastResponder.startResponding();
	}

	private static int randomTcpPort() {
		int low = 10;
		int high = 30;
		return RANDOM.nextInt(high - low) + low;
	}

}
