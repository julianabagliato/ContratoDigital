package mobile.contratodigital.model;

public class ItemPeca {
	
	private String nome;
	private String codigo;
	
	
	public ItemPeca(String nome,  String codigo) {
		this.nome = nome;
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}	
	
	public String getCodigo() {
		return codigo;
	}
	
}
