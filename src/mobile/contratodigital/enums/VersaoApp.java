package mobile.contratodigital.enums;

public enum VersaoApp {

	VERSAO_NUMERO(201804261);

	private int numero;

	public int getNumero() {
		return numero;
	}

	private VersaoApp(int numero) {
		this.numero = numero;
	}
}
