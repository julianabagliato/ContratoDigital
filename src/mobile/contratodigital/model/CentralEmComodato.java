package mobile.contratodigital.model;

import java.util.ArrayList;

	public class CentralEmComodato {

	private ArrayList<Produto> centralEmComodato = new ArrayList<Produto>();					
	
	public ArrayList<Produto> getListaCentralEmComodato() {

		centralEmComodato.add(new Produto("CENTRAL", 1));					

		return centralEmComodato;
	}
	
	public double devolveCustoDaCentral(double d, double e){
		
		double custoDaCentral = d + e;
		
		return custoDaCentral;
	}
}
