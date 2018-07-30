package mobile.contratodigital.enums;

public enum TipoActivity {
	
	CONTRATO_PADRAO(1),
	CONTRATO_CONTA_SIM(2),
	ANEXO_PADRAO(3),
	ANEXO_CONTA_SIM(4);

	private int resultado;
	
	public int getValor(){
		return resultado;
	}
	
	private TipoActivity(int _resultado){
		this.resultado = _resultado;
	}
	
}
