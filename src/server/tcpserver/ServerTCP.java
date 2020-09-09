package server.tcpserver;// A Java program for a Lab3.Server

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerTCP {

	private static AtomicInteger connectedClients = new AtomicInteger(0);
	private final int MAXIMUM_CLIENTS_CONNECTED = 3;
	private Logger LOGGER = Logger.getLogger(this.getClass().getName());
	private ServerSocket serverSocket;
	private static int port = 7;


	public ServerTCP(int port) throws IOException {
		try {
			serverSocket = new ServerSocket(port,50,InetAddress.getByName("192.168.56.1"));
//			serverSocket = new ServerSocket(port);
			printServerInfo();
		} catch (IOException i) {

			throw new IOException("Problem while init socket: " +  i);
		}
	}
	public InetAddress getServerIp() throws UnknownHostException {
		return serverSocket.getInetAddress();
	}

	public void startListening() {
		try {
			while (true) {
				if (isServerOverloaded()) {
					LOGGER.log(Level.SEVERE, "To much clients connected, try later");
				} else {
					acceptNewConnection();
				}
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

	private void printServerInfo() throws UnknownHostException {
		System.out.println("Server listen at port: " +  port + "\nip: " + InetAddress.getLocalHost().getHostAddress());
	}


	private boolean isServerOverloaded() {
		return connectedClients.get() >= MAXIMUM_CLIENTS_CONNECTED;
	}


} 
