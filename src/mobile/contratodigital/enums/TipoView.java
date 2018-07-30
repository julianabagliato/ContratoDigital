package mobile.contratodigital.enums;

public enum TipoView {

	CAMPO_TEXTO(1),
	CAIXA_TEXTO_EDICAO(2),
	CAIXA_OPCAO(3),
	BOTAO_OPCAO(4),
	CAIXA_VERIFICACAO(5),

	TIPO_DADO_DATA(3),
	TIPO_DADO_MONETARIO(2),	
	
	LAYOUT_CONSULTA(1),
	LAYOUT_FORMULARIO(2),
	
	VISUALIZACAO_NORMAL(1),
	VISUALIZACAO_TABELA(2);

	
	private int resultado;
	
	public int getValor(){
		return resultado;
	}
	
	private TipoView(int _resultado){
		this.resultado = _resultado;
	}
	
}
