package mobile.contratodigital.enums;

public enum NomeLayout {

	CONTRATO_DIGITAL(20), 
	INFORMACOES_CLIENTE(21),
	DADOS_CADASTRO(22),
	REPRESENTACAO(23),
	ENDERECO_PADRAO(24),
	ENDERECO_ENTREGA(25),
	CONSUMO_CLIENTE(26),
	CLIENTE_CONTASIM(30),
	OBSERVACOES_GERAIS(31), 
	//EQUIPAMENTOS_CONTRATADOS(32),
	EQUIPAMENTOS_SIMULADOS(666),
	SIMULADOR2(667),
	PECAS(668),
	DADOS_DATASUL(669);

	private int numero;

	public int getNumero() {
		return numero;
	}

	private NomeLayout(int numero) {
		this.numero = numero;
	}
	
}
