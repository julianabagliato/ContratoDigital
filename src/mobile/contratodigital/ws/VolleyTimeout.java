package mobile.contratodigital.ws;

import com.android.volley.DefaultRetryPolicy;

public class VolleyTimeout {

	private static int IMEOUT_MS = 15000;
	
	public static DefaultRetryPolicy recuperarTimeout(){
		
		return (new DefaultRetryPolicy(
				IMEOUT_MS, 
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
	}
	
}
