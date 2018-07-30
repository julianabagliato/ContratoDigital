package mobile.contratodigital.model;

public class Produto {

	private String nome;
	private String apelido;
	private double valorUnitario;
	private int quantidade;
	private double valorTotal;
	
	
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double custoTotal) {
		this.valorTotal = custoTotal;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Produto(String nome, float _valorUnitario) {	
		this.nome = nome;
		this.valorUnitario = _valorUnitario;
	}

	public String getNome() {
		return nome;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}
	
	@Override
	public String toString() {
		
		return nome;
	}
}
