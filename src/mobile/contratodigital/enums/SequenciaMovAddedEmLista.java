package mobile.contratodigital.enums;
	
public enum SequenciaMovAddedEmLista {
	
	mov_contratoDigital(0),
	mov_informacoesCliente(1),
	mov_dadosCadastro(2),
	mov_representacao(3),
	mov_enderecoPadrao(4),
	mov_enderecoEntrega(5),
	mov_consumoCliente(6),
	mov_clienteContaSim(7),
	mov_observacoesGerais(8),
	mov_equipamentosContratados(9),
	mov_equipamentosSimulados(10),
	mov_simulador(11),
	mov_pecas(12),
	mov_dadosDatasul(13);
	
	private int numero;

	public int getPosicao() {
		return numero;
	}

	private SequenciaMovAddedEmLista(int numero) {
		this.numero = numero;
	}

}
