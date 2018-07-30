package mobile.contratodigital.enums;

public enum IpURL {
	
	// Local:
	// URL_SERVER_REST("http://172.16.4.151:8080/ContratoDigital_rest_service");
	 //URL_SERVER_REST("http://10.1.1.83:8080/ContratoDigital_rest_service");
	// server: DBSQLPRD
    //	URL_SERVER_REST("http://172.16.0.48:8080/ContratoDigital_rest_service");

	// teste com WIFI server:SRVWS01
	//URL_SERVER_REST("http://172.16.0.22:8080/ContratoDigital_rest_service");
	 //URL_SERVER_REST("http://siva.consigaz.com.br:9090/ContratoDigital_rest_service");

	// teste com 3G server:SRVWS01
     URL_SERVER_REST("http://siva.consigaz.com.br:9090/ContratoDigital_rest_service");

	private String valor;

	public String getValor() {
		return valor;
	}

	private IpURL(String valor) {
		this.valor = valor;
	}
}
