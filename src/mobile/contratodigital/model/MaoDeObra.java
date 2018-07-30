package mobile.contratodigital.model;

import java.util.ArrayList;

public class MaoDeObra {

	private ArrayList<Double> listaComCustos1 = new ArrayList<Double>();
	private ArrayList<Double> listaComCustosTotalAdicionais = new ArrayList<Double>();

	public void adicionaSubTotalCusto1(double maoDeObraCustoUmaQTD){
		
		listaComCustos1.add(maoDeObraCustoUmaQTD);
	}
	
	public ArrayList<Double> getListaComCustos1() {
		return listaComCustos1;
	}

	public void removeSubTotalCusto1(double custo1){
		
		listaComCustos1.remove(new Double(custo1));		
	}
		
	public void adicionaSubTotalCustoAdicional(double maoDeObraTotalAdicional){
		
		listaComCustosTotalAdicionais.add(maoDeObraTotalAdicional);
	}
	
	public ArrayList<Double> getListaComCustosTotalAdicionais() {
		return listaComCustosTotalAdicionais;
	}
	
	public void removeSubTotalAdicional(double totalAdicional){
		
		listaComCustosTotalAdicionais.remove(new Double(totalAdicional));		
	}
	
	public double totalMaoDeobra(){
		
		double totalCusto1 = 0;
		
		for(double custo : getListaComCustos1()){
			
			totalCusto1 = totalCusto1 + custo;
		}
	
		double totalCustoAdicional = 0;
		
		for(double custoAdicional : getListaComCustosTotalAdicionais()){
			
			totalCustoAdicional = totalCustoAdicional + custoAdicional;
		}

		
		double totalMaoDeobra = totalCusto1 + totalCustoAdicional;
		
		return totalMaoDeobra;
	}
	
}
