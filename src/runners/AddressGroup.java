package runners;

public enum  AddressGroup {
	A("126.255.255.255"),
	B("191.255.255.255"),
	C("223.255.255.255"),
	D("239.255.255.255"),
	E("247.255.255.255");

	private String address;

	AddressGroup(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
}
//	Klasa A
//
//Zakres: 1.0.0.1 - 126.255.255.254
//
//	Adres rozgłoszenia: 126.255.255.255
//
//	Maska: 255.0.0.0
//
//	Adresy zarezerwowane: 10.0.0.1 - 10.255.255.254 i 127.0.0.1 - 127.255.255.254
//
//	Klasa B
//
//	Zakres: 128.0.0.1 - 191.255.255.254
//
//	Adres rozgłoszenia: 191.255.255.255
//
//	Maska: 255.255.0.0
//
//	Adresy zarezerwowane: 172.16.0.1 - 172.31.255.254 i 169.254.0.1 - 169.254.255.254
//
//	Klasa C
//
//	Zakres: 192.0.0.1 - 223.255.255.254
//
//	Adres rozgłoszenia: 223.255.255.255
//
//	Maska: 255.255.255.0
//
//	Adresy zarezerwowane: 192.168.0.1 - 192.168.255.254
//
//	Klasa D
//
//	Zakres: 224.0.0.1 - 239.255.255.254
//
//	Adres rozgłoszenia: 239.255.255.255
//
//	Maska: 255.255.255.0
//
//	Adresy zarezerwowane: 224.0.0.1 - 224.0.0.254
//
//	Klasa E
//
//	Zakres: 240.0.0.1 - 247.255.255.254
//
//	Adres rozgłoszenia: 247.255.255.255
//
//	Maska: 255.255.255.255
//
//	Klasa F
//
//	Zakres: 248.0.0.1 - 255.255.255.254
//
//	Adres rozgłoszenia: 255.255.255.255
//
//	Maska: 255.255.255.255
