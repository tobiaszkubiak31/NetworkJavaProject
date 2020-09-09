package server.udpresponder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastAddressResponder implements Runnable {

    protected MulticastSocket socket = null;
    private int port;
    private int tcpPort;
    private InetAddress serverIpAddress;
    private String ipAddress;

    public MulticastAddressResponder(String ipAddress, int port, int tcpPort) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.tcpPort = tcpPort;
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
    @Override
    public void run() {
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

    private void sendResponsesWithIp(DatagramPacket packet) throws IOException, InterruptedException {
        waitForClientReceiver();
        String serverInformation = "";
        serverInformation= serverInformation + serverIpAddress +"," + tcpPort;

        byte[] buf = serverInformation.getBytes();
        packet = new DatagramPacket(buf, buf.length, packet.getAddress(), 8);
        socket.send(packet);
    }

    private void waitForClientReceiver() throws InterruptedException {
        Thread.sleep((long)500);
    }

    public void setServerIpAddress(InetAddress serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }
}
