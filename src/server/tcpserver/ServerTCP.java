package server.tcpserver;// A Java program for a Lab3.Server

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP implements Runnable{

	private static AtomicInteger connectedClients = new AtomicInteger(0);
	private static int port;
	private final String serverIp;
	private Logger LOGGER = Logger.getLogger(this.getClass().getName());
	private ServerSocket serverSocket;

	public ServerTCP(int port, String routableIp) throws IOException {
		try {
			serverSocket = new ServerSocket(port, 50, InetAddress.getByName(routableIp));
			this.port = port;
			this.serverIp = routableIp;
//			serverSocket = new ServerSocket(port);
			printServerInfo();
		} catch (IOException i) {

			throw new IOException("Problem while init socket: " + i);
		}
	}

	@Override
	public void run() {
		startListening();
	}

	private void startListening() {
		try {
			while (true) {
				acceptNewConnection();
			}
		} catch (IOException i) {
			LOGGER.log(Level.SEVERE, "Socket Interrupted");
		}

	}

	private void acceptNewConnection() throws IOException {
		Socket socket = serverSocket.accept();
		LOGGER.log(Level.INFO, "Client accepted");
		TCPServerHandlerOfClient TCPServerHandlerOfClient = new TCPServerHandlerOfClient(socket,
			connectedClients);
		Thread thread = new Thread(TCPServerHandlerOfClient);
		thread.start();
		connectedClients.incrementAndGet();
	}

	private void printServerInfo() {
		System.out.println("Server listen at port: " + port + "\nip: " + serverIp);
	}


}
