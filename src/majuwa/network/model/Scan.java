package majuwa.network.model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Scan {
	AddressContainer container;

	public Scan() {
		container = AddressContainer.instance();
	}

	public void scan() throws UnknownHostException,IOException {
		boolean hosts = Configuration.instance().getShowAllHosts();
		for (IPAddress ip : container) {
			InetAddress inet = InetAddress.getByName(ip.getHost());
			if (inet.isReachable(500))
				ip.setStatus(true);
			else
				ip.setStatus(false);
		}
	}
}
