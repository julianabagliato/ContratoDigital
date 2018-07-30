package mobile.contratodigital.model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.util.Item;
import sharedlib.contratodigital.model.Cad_eqpto;

public class Equipamento implements Item{

	//private ArrayList<Produto> lista = new ArrayList<Produto>();
	private String equipamentoEscolhido;
	private int quantidadeEscolhida;
	private Context context;
	private List<Cad_eqpto> listaComCad_eqpto;


	public Equipamento(Context context) {
		this.context = context;
	}
		
	@Override
	public ArrayList<Produto> getLista() {
		
		 ArrayList<Produto> lista = new ArrayList<Produto>();
		 
		 Dao dao = new Dao(context);

         listaComCad_eqpto = dao.listaTodaTabela(Cad_eqpto.class);

         for(Cad_eqpto cad_eqpto : listaComCad_eqpto) {

                   if (cad_eqpto.getTipo().equals("3")){

                     lista.add(new Produto(cad_eqpto.getDesc_eqpto(),Float.parseFloat(cad_eqpto.getValor())));

                   }

         }	
		
		
		return lista;		
	}
	
	@Override
	public String getItemEscolhido() {
		return equipamentoEscolhido;
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
	public void setItemEscolhido(String itemEscolhido) {
	
		this.equipamentoEscolhido = itemEscolhido;		
	}

	@Override
	public double materiaCustoInstalacao1() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double materiaCustoInstalacaoAdicional() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double materiaCustoTotalAdicional() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double maoDeObrCustoInstalacao1() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double maoDeObrCustoInstalacaoAdicional() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double maoDeObrCustoTotalAdicional() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int quantidadAdicional() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
