package client.tcpclient;

import utilis.SystemConsts;
import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPClient {

	private final static Logger LOGGER = Logger.getLogger(TCPClient.class.getName());
	final static private String errorConnectionMessage = SystemConsts.errorConnectionMessage;
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private String address;
	private String requestFrequency;
	private Scanner scanner = new Scanner(System.in);

	public TCPClient(String ip, int port) {
		this.address = ip;
		initSocket(port);
	}

	public void connectToServer() {
		try {
			System.out.println("How frequent request to server(10-100hz)?:");
			requestFrequency = scanner.nextLine();

			SendDataClientTCP SendDataClientTCP = new SendDataClientTCP(input, output);
			SendDataClientTCP.requestServerOnFrequency(Long.parseLong(requestFrequency));

		} catch (SocketException e) {
			LOGGER.log(Level.SEVERE, "Error, server canceled connection");
		} catch (InterruptedException e) {
			LOGGER.log(Level.SEVERE, e.toString());
		} catch (IOException i) {
			LOGGER.log(Level.SEVERE, i.toString());
		}
		closeResources();

	}

	private void initSocket(int port) {
		try {
			Socket socket = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(address, port);
			socket.connect(socketAddress);
			LOGGER.log(Level.INFO, "Connected to server");
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException u) {
			closeResources();
			LOGGER.log(Level.SEVERE,
				"Error, exception message :\n" + u.toString() + "\n " + "Solution: "
					+ errorConnectionMessage);
			System.exit(1);
		} catch (ConnectException e) {
			closeResources();
			LOGGER.log(Level.SEVERE,
				"Error, exception message :\n" + e.toString() + "\n " + "Solution:"
					+ errorConnectionMessage);
			System.exit(1);
		} catch (IOException i) {
			i.printStackTrace();
			System.exit(1);
		}
	}

	private void closeResources() {
		try {
			if (socket != null) {
				this.socket.close();
			}
			if (this.input != null) {
				this.input.close();
			}
			if (this.output != null) {
				this.output.close();
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Problem in closing resources");
		}
	}

}

//
//			while (!timeFromServer.equals("close")) {
//
//                byte[] outputBytes = "getServerTime".getBytes(Charset.forName("UTF-8"));
//                output.write(outputBytes);
//
//                byte[] inputBytes = new byte[1024];
//                int receivedBytes = input.read(inputBytes);
//                if (receivedBytes == -1) {
//                throw new SocketException("");
//                }
//
//                System.out.println("I received " + receivedBytes + "bytes");
//                timeFromServer = new String(inputBytes, 0, receivedBytes);
//                System.out.println("Message from server: " + timeFromServer);
//
//                }
