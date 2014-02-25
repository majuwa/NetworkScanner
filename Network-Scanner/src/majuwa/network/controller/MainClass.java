package majuwa.network.controller;
import java.net.InetAddress;


public class MainClass {
	public static void main(String[] args) throws Exception{
		/*
		String ipAddress = "127.0.0.1";
	    InetAddress inet = InetAddress.getByName(ipAddress);

	    System.out.println("Sending Ping Request to " + ipAddress);
	    System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");

	    ipAddress = "192.168.178.2";
	    inet = InetAddress.getByName(ipAddress);

	    System.out.println("Sending Ping Request to " + ipAddress);
	    System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");
	    */
		new MainController();
	}
}
