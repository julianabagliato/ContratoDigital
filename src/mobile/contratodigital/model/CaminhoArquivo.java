package mobile.contratodigital.model;

public class CaminhoArquivo {

	private String arquivo;
	private String caminho;

	public CaminhoArquivo(String arquivo, String caminho) {
		this.arquivo = arquivo;
		this.caminho = caminho;
	}

	public String getArquivo() {
		return arquivo;
	}

	public String getCaminho() {
		return caminho;
	}

	@Override
	public String toString() {

		return this.arquivo;
	}
}
