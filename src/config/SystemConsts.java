package config;

public final class SystemConsts {
	final static public String errorConnectionMessage= "Error, Connection with socket timeout, potential problems:"
		+ "a) The IP/domain or port is incorrect\n"
		+ "b) The IP/domain or port (i.e service) is down\n"
		+ "c) The IP/domain is taking longer than your default timeout to respond\n"
		+ "d) You have a firewall that is blocking requests or responses on whatever port you are using\n"
		+ "e) You have a firewall that is blocking requests to that particular host\n"
		+ "f) Your internet access is down\n"
		+ "Try again !";
	final static public String multicastGroupIp = "239.0.0.225";
}
