package server.tcpserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSenderTCP {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	private DataOutputStream serverHandlerOfClient;
	private Socket socket;

	public DataSenderTCP(DataOutputStream serverHandlerOfClient, Socket socket) {
		this.serverHandlerOfClient = serverHandlerOfClient;
		this.socket = socket;
	}

	void sendResponse(String line, int receivedBytes) throws IOException {
		byte[] outputbytes = line.getBytes(StandardCharsets.UTF_8);
		LOGGER.log(Level.INFO,
			"Client  " + socket.getInetAddress() + " port:"
				+ socket.getPort()
				+ " message: " + line);
		LOGGER.log(Level.INFO, "I received " + receivedBytes + " bytes");
//        dataOutputStream.write(inputBytes);
		serverHandlerOfClient.write(outputbytes, 0, outputbytes.length);
		serverHandlerOfClient.flush();
	}
}
