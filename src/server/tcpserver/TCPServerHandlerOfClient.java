package server.tcpserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServerHandlerOfClient implements Runnable {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private Socket socket;
	private AtomicInteger clientConnectedToServer;
	private DataSenderTCP dataSenderTCP;

	public TCPServerHandlerOfClient(Socket socket, AtomicInteger clientConnectedToServer) {
		try {
			this.socket = socket;
			this.clientConnectedToServer = clientConnectedToServer;
			this.dataInputStream = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));
			this.dataOutputStream = new DataOutputStream(
				new BufferedOutputStream(socket.getOutputStream()));
			dataSenderTCP = new DataSenderTCP(this.dataOutputStream,socket);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error in accepting socket, client rejected");
			closeResources();
		}
	}

	@Override
	public void run() {
		receiveAndSendMessage();
	}

	private void receiveAndSendMessage() {
		while (true) {
			try {
				byte[] inputBytes = new byte[1024];
				dataInputStream.read(inputBytes);

				String response = getSystemTime();
				dataSenderTCP.sendResponse(response, response.getBytes().length);
			} catch (IOException i) {
				LOGGER.log(Level.INFO, "Client disconnected");
				break;
			}
		}
		closeResources();
	}

	private String getSystemTime() {
		return Long.toString(System.currentTimeMillis());
	}

	private void closeResources() {
		synchronized (clientConnectedToServer) {
			clientConnectedToServer.decrementAndGet();
		}
		if (socket != null) {
			try {
				this.socket.close();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.toString());
			}
		}
	}

}

//	private void sendResponse(String line, int receivedBytes) throws IOException {
//		byte[] outputbytes = line.getBytes(StandardCharsets.UTF_8);
//		LOGGER.log(Level.INFO,
//			"Client  " + serverHandlerOfClient.getSocket().getInetAddress() + " port:"
//				+ serverHandlerOfClient.getSocket().getPort()
//				+ " message: " + line);
//		serverHandlerOfClient.getLOGGER().log(Level.INFO, "I received " + receivedBytes + " bytes");
////        dataOutputStream.write(inputBytes);
//		serverHandlerOfClient.getDataOutputStream().write(outputbytes, 0, outputbytes.length);
//		serverHandlerOfClient.getDataOutputStream().flush();
//	}
