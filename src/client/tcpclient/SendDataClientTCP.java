package client.tcpclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.Charset;

public class SendDataClientTCP {

	private String timeFromServer ="";
	private DataInputStream input;
	private DataOutputStream output;
	private long t1;
	private long t2;

	public SendDataClientTCP(DataInputStream input, DataOutputStream output) {
		this.input = input;
		this.output = output;
	}

	public void requestServerOnFrequency(long requestFrequency) throws IOException, InterruptedException {
		while (!timeFromServer.equals("close")) {
			t1 = getSystemTime();

			byte[] outputBytes = "getServerTime".getBytes(Charset.forName("UTF-8"));
			output.write(outputBytes);

			byte[] inputBytes = new byte[1024];
			int receivedBytes = input.read(inputBytes);
			if (receivedBytes == -1) {
				throw new SocketException("");
			}

			System.out.println("I received " + receivedBytes + "bytes");
			t2 = Long.valueOf(new String(inputBytes, 0, receivedBytes));
			long difference = t2 - t1;
			System.out.println("Diffrence : " + difference);

			Thread.sleep(requestFrequency);
		}
	}

	private long getSystemTime() {
		return System.currentTimeMillis();
	}
}
