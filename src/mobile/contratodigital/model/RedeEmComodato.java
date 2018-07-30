package mobile.contratodigital.model;

import java.util.ArrayList;

public class RedeEmComodato {

	private ArrayList<Produto> listaDeDiametros = new ArrayList<Produto>();					
	
	public ArrayList<Produto> getListaDeDiametros() {
		
		//ArrayList<Produto> lista = new ArrayList<Produto>();
		 
		listaDeDiametros.add(new Produto("1/2 E 3/4", 55));					
		listaDeDiametros.add(new Produto("1 a 2", 95));					
		listaDeDiametros.add(new Produto("3 a 4", 145));					

		return listaDeDiametros;
	}
	
	public double devolveTotalCustoRede(double diametro, double metragem){
		
		double totalCustoRede = diametro * metragem;
		
		return totalCustoRede;
	}
	
}
