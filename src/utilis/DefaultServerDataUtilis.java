package utilis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

public class DefaultServerDataUtilis {

	//get root path of project
	private static String directory = System.getProperty("user.home");
	private static String fileName = "default_server_data.txt";
	private static Path path = Paths.get(directory, fileName);

	public static void saveLastConnectedServer(String selectedIp, Integer selectedPort) {

		String ipAndPortToSave = selectedIp + "\n" + selectedPort;
		try {
			Files.write(path, ipAndPortToSave.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.out.println("Problem with saving default server data");
		}
	}

	public static Optional<ServerData> readDefaultServerData() {
		List<String> ipAndPortList;
		try {
			ipAndPortList = Files.readAllLines(path);
		} catch (IOException e) {
			System.out.println("Last connected server data not available");
			return Optional.empty();
		}
		return Optional.of(new ServerData(getIp(ipAndPortList), getPort(ipAndPortList, 1)));

	}

	private static Integer getPort(List<String> ipAndPortList, int i) {
		return Integer.valueOf(ipAndPortList.get(i));
	}

	private static String getIp(List<String> ipAndPortList) {
		return ipAndPortList.get(0);
	}


}
