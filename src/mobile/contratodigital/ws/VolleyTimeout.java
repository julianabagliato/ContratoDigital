package mobile.contratodigital.ws;

import com.android.volley.DefaultRetryPolicy;
/**
 * Classe para determinar o timeout das requisições
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class VolleyTimeout {

	//+- 2.30 min
	private static int IMEOUT_MS = 5000;
	
	
	public static DefaultRetryPolicy recuperarTimeout(){
		
		return (new DefaultRetryPolicy(
				IMEOUT_MS, 
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
	}
	
}
