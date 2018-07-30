package mobile.contratodigital.model;

import java.util.ArrayList;

public class Material {
	
	private ArrayList<Double> listaComCustos1 = new ArrayList<Double>();
	private ArrayList<Double> listaComCustosTotalAdicionais = new ArrayList<Double>();

	public void adicionaSubTotalCusto1(double materialCustoUmaQTD){
		
		listaComCustos1.add(new Double(materialCustoUmaQTD));
	}
	
	public ArrayList<Double> getListaComCustos1() {
		
		return listaComCustos1;
	}

	public void removeSubTotalCusto1(double custo1){
		
		listaComCustos1.remove(new Double(custo1));		
	}
		
	public void adicionaSubTotalCustoAdicional(double materialTotalAdicional){
		
		listaComCustosTotalAdicionais.add(new Double(materialTotalAdicional));
	}
	
	public ArrayList<Double> getListaComCustosTotalAdicionais() {
		
		return listaComCustosTotalAdicionais;
	}
	
	public void removeSubTotalAdicional(double totalAdicional){
		
		listaComCustosTotalAdicionais.remove(new Double(totalAdicional));		
	}
	
	public double totalMaterial(){
		
		double totalCusto1 = 0;
		
		for(double custo : getListaComCustos1()){
			
			totalCusto1 = totalCusto1 + custo;
		}
	
		double totalCustoAdicional = 0;
		
		for(double custoAdicional : getListaComCustosTotalAdicionais()){
			
			totalCustoAdicional = totalCustoAdicional + custoAdicional;
		}

		
		double totalMaterial = totalCusto1 + totalCustoAdicional;
		
		return totalMaterial;
	}

}
