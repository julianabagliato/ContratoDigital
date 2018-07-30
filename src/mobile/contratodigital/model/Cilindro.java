package mobile.contratodigital.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.util.Item;
import sharedlib.contratodigital.model.Cad_eqpto;

public class Cilindro implements Item {

	//private ArrayList<Produto> lista = new ArrayList<Produto>();
	private String cilindroEscolhido;
	private int quantidadeEscolhida;
	private Context context;
	private List<Cad_eqpto> listaComCad_eqpto;

	public Cilindro(Context context) {
		this.context = context;
	}

	@Override	
	public ArrayList<Produto> getLista() {
		 
		ArrayList<Produto> lista = new ArrayList<Produto>();

		Dao dao = new Dao(context);

		listaComCad_eqpto = dao.listaTodaTabela(Cad_eqpto.class);

		for (Cad_eqpto cad_eqpto : listaComCad_eqpto) {

			if (cad_eqpto.getTipo().equals("2")) {

				lista.add(new Produto(cad_eqpto.getDesc_eqpto(), Float.parseFloat((cad_eqpto.getValor()))));

			}

		}

		return lista;
	}

	@Override
	public String getItemEscolhido() {
		return cilindroEscolhido;
	}

	@Override
	public int getQuantidadeEscolhida() {
		return quantidadeEscolhida;
	}

	@Override
	public void setQuantidadeEscolhida(int quantidadeEscolhida) {
		this.quantidadeEscolhida = quantidadeEscolhida;
	}

	@Override
	public double materiaCustoInstalacao1() {

		if (getItemEscolhido().equals("P45") || getItemEscolhido().equals("P90")) {

			return 300;
		} else {
			return 0;
		}
	}

	
	@Override
	public double materiaCustoInstalacaoAdicional() {

		if (materiaCustoInstalacao1() == 300) {

			return 25;
		} else {
			return 0;
		}
	}

	@Override
	public double materiaCustoTotalAdicional() {

		return quantidadAdicional() * materiaCustoInstalacaoAdicional();
	}

	@Override
	public double maoDeObrCustoInstalacao1() {

		if (getItemEscolhido().equals("P45") || getItemEscolhido().equals("P90")) {

			return 240;
		} else {
			return 0;
		}
	}

	@Override
	public double maoDeObrCustoInstalacaoAdicional() {

		if (maoDeObrCustoInstalacao1() == 240) {

			return 80;
		} else {
			return 0;
		}
	}

	@Override
	public double maoDeObrCustoTotalAdicional() {

		return quantidadAdicional() * maoDeObrCustoInstalacaoAdicional();
	}

	@Override
	public int quantidadAdicional() {

		if (getQuantidadeEscolhida() > 1) {

			return getQuantidadeEscolhida() - 1;
		} else {
			return 0;
		}
	}

	@Override
	public void setItemEscolhido(String itemEscolhido) {

		this.cilindroEscolhido = itemEscolhido;
	}

	@SuppressWarnings("unused")
	private JSONObject devolveJsonObjectDeUmaClasse(Object objeto) {

		Class<?> classe = objeto.getClass();

		JSONObject jSONObject = new JSONObject();

		try {

			for (Field field : classe.getDeclaredFields()) {

				field.setAccessible(true);
				jSONObject.put(field.getName(), field.get(objeto));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jSONObject;
	}
}
