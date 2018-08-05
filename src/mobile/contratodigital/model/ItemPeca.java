package mobile.contratodigital.model;

public class ItemPeca {

	private String quantidade;
	private String codigo;
	private String nome;
	
	public ItemPeca(String quantidade, String codigo, String nome) {
		this.quantidade = quantidade;
		this.codigo = codigo;
		this.nome = nome;	
	}

	public String getNome() {
		return nome;
	}	
	
	public String getCodigo() {
		return codigo;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public String toString() {
		
		return this.codigo;
	}
	
}
