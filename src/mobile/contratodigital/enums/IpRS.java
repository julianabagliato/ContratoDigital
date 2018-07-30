package mobile.contratodigital.enums;

import android.view.Menu;

public class IpRS {

	public static final String URL_SIVA_REST = "http://siva.consigaz.com.br:9090/ContratoDigital_rest_service";
	private static final String URL_SIVA_REST_LOCAL = "http://172.16.0.22:9090/ContratoDigital_rest_service";
	private static final String URL_SIVA_REST_Vogel = "http://siva2.consigaz.com.br:9090/ContratoDigital_rest_service";
	private static final String URL_SIVA_REST_WCS = "http://siva3.consigaz.com.br:9090/ContratoDigital_rest_service";


/**
 * Metodo acaoLinkOi para mudar o link para o link da oi (siva)
 * 
 * @param acaoLinkOi
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 
 */
	public static String acaoLinkOi(Menu menu){
		menu.getItem(0).setEnabled(false);
		menu.getItem(1).setEnabled(true);
		menu.getItem(2).setEnabled(true);
		menu.getItem(3).setEnabled(true);
		return URL_SIVA_REST;
	}
	/**
	 * Metodo acaoLinkvogel para mudar o link para o link da vogel (siva2)
	 * 
	 * @param acaoLinkvogel
	 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 
	 */
	public static String acaoLinkvogel(Menu menu){
		menu.getItem(0).setEnabled(true);
		menu.getItem(1).setEnabled(false);
		menu.getItem(2).setEnabled(true);
		menu.getItem(3).setEnabled(true);
		return URL_SIVA_REST_Vogel;
	}
	/**
	 * Metodo acaoLinWCS para mudar o link para o link WCS (siva3)
	 * 
	 * @param acaoLinWCS
	 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 
	 */
	public static String acaoLinWCS(Menu menu){
		menu.getItem(0).setEnabled(true);
		menu.getItem(1).setEnabled(true);
		menu.getItem(2).setEnabled(false);
		menu.getItem(3).setEnabled(true);
		return URL_SIVA_REST_WCS;
	}	
	/**
	 * Metodo acaoLinkLocal para mudar o link para o link local  (172.16.0.22)
	 * 
	 * @param acaoLinkLocal
	 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 
	 */
	public static String acaoLinkLocal(Menu menu){
		menu.getItem(0).setEnabled(true);
		menu.getItem(1).setEnabled(true);
		menu.getItem(2).setEnabled(true);
		menu.getItem(3).setEnabled(false);
		return URL_SIVA_REST_LOCAL;
	}
	

}
