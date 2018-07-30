package mobile.contratodigital.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.util.Log;

public class Carrinho {

	private HashMap<String, Produto> listaComProdutos = new HashMap<String, Produto>();

	public void adicionaProduto(String key, Produto produto) {
		
		listaComProdutos.put(key, produto);
	}

	public void removeProduto(String key) {
		
		listaComProdutos.remove(key);
	}

	private HashMap<String, Produto> getListaComProdutos() {
		
		return listaComProdutos;
	}
	
	public double devolveCustoTotal() {

		double custoTotal = 0;

		HashMap<String, Produto> hashMap = getListaComProdutos();

		@SuppressWarnings("rawtypes")
		Iterator iterator = hashMap.entrySet().iterator();

		while (iterator.hasNext()) {

			@SuppressWarnings("unchecked")
			Map.Entry<String, Produto> pair = (Map.Entry<String, Produto>) iterator.next();
	
			Produto produto = pair.getValue();

			custoTotal = custoTotal + produto.getValorTotal();
		}

		return custoTotal;
	}
	
	public ArrayList<Produto> devolveProdutosAdicionados() {

		ArrayList<Produto> lista = new ArrayList<Produto>();

		HashMap<String, Produto> hashMap = getListaComProdutos();

		@SuppressWarnings("rawtypes")
		Iterator iterator = hashMap.entrySet().iterator();

		while (iterator.hasNext()) {

			@SuppressWarnings("unchecked")
			Map.Entry<String, Produto> pair = (Map.Entry<String, Produto>) iterator.next();

			Produto produto = pair.getValue();

			lista.add(produto);
		}

		return lista;
	}

}
