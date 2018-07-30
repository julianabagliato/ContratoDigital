package mobile.contratodigital.dao;

import java.util.ArrayList;

import android.content.Context;
import mobile.contratodigital.model.CentralEmComodato;
import mobile.contratodigital.model.Cilindro;
import mobile.contratodigital.model.Equipamento;
import mobile.contratodigital.model.Granel;
import mobile.contratodigital.model.Produto;
import mobile.contratodigital.model.RedeEmComodato;

public class ListaComTodosOsProdutos {

	private static ArrayList<Produto> listaComTodosOsProdutos = new ArrayList<Produto>();
	
	public static ArrayList<Produto> devolveListaSIM_NAO() {

		ArrayList<Produto> listaSimNao = new ArrayList<Produto>();
		listaSimNao.add(new Produto("", 0));
		listaSimNao.add(new Produto("NÃO", 1));
		listaSimNao.add(new Produto("SIM", 2));

		return listaSimNao;
	}
	
	public static Produto devolveProdutoOndeNomeEh(String nomeDoProduto, Context context) {

		Cilindro cilindro = new Cilindro(context);
		Equipamento equipamento = new Equipamento(context);
		//EquipamentoEspecial equipamentoEspecial = new EquipamentoEspecial(context);
		Granel granel = new Granel(context);
		RedeEmComodato redeEmComodato = new RedeEmComodato();
		CentralEmComodato centralEmComodato = new CentralEmComodato();

		ArrayList<Produto> listaComCilindros = cilindro.getLista();
		ArrayList<Produto> listaComEquipamentos = equipamento.getLista();
		//ArrayList<Produto> listaComEquipamentosEspeciais = equipamentoEspecial.getLista();
		ArrayList<Produto> listaComGranel = granel.getLista();
		ArrayList<Produto> listaComDiametros = redeEmComodato.getListaDeDiametros();
		ArrayList<Produto> listaCentralEmComodato = centralEmComodato.getListaCentralEmComodato();
		ArrayList<Produto> listaSimNao = devolveListaSIM_NAO();

		listaComTodosOsProdutos.addAll(listaComCilindros);
		listaComTodosOsProdutos.addAll(listaComEquipamentos);
		//listaComTodosOsProdutos.addAll(listaComEquipamentosEspeciais);
		listaComTodosOsProdutos.addAll(listaComGranel);
		listaComTodosOsProdutos.addAll(listaComDiametros);
		listaComTodosOsProdutos.addAll(listaCentralEmComodato);
		listaComTodosOsProdutos.addAll(listaSimNao);

		Produto produtoEncontrado = null;

		for (Produto produto : listaComTodosOsProdutos) {

			if (produto.getNome().equals(nomeDoProduto)) {

				produtoEncontrado = produto;

				break;
			}
		}

		return produtoEncontrado;
	}

}
