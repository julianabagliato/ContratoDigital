package mobile.contratodigital.util;

import java.util.ArrayList;

import mobile.contratodigital.model.Produto;
import mobile.contratodigital.view.ActivitySimulador;

/**
 * Classe criada para retornar os item que serão trabalhados no simulador
 * @see ActivitySimulador
 *  @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public interface Item {

	public double materiaCustoInstalacao1();
	public double materiaCustoInstalacaoAdicional();
	public double materiaCustoTotalAdicional();
	public double maoDeObrCustoInstalacao1();
	public double maoDeObrCustoInstalacaoAdicional();
	public double maoDeObrCustoTotalAdicional();
	public int quantidadAdicional();
	public ArrayList<Produto> getLista();
	public void setItemEscolhido(String itemEscolhido);
	public void setQuantidadeEscolhida(int quantidadeEscolhida);
	public String getItemEscolhido();
	public int getQuantidadeEscolhida();
	
	
}
