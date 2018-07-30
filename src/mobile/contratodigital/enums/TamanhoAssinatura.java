package mobile.contratodigital.enums;

/**
 * Classe criada para criar e armazernar os valores referentes a altura e largura das assinaturas;
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public enum TamanhoAssinatura {

	ALTURA(45),
	LARGURA(75);
	
	private float resultado;
	
	public float getTamanho(){
		return resultado;
	}
	
	private TamanhoAssinatura(float _resultado){
		this.resultado = _resultado;
	}
	
}
