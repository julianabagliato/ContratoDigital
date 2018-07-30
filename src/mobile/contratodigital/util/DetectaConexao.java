package mobile.contratodigital.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * Classe criada para detectar a conexão com a internet
 *  @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class DetectaConexao {

	private Context context;

	public DetectaConexao(Context _context) {
		this.context = _context;
	}
	
	public boolean estaConectadoNaInternet() {

		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager != null) {

			NetworkInfo[] arrayNetworkInfo = connectivityManager.getAllNetworkInfo();

			if (arrayNetworkInfo != null) {

				for (int i = 0; i < arrayNetworkInfo.length; i++) {

					if (arrayNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED) {

						return true;
					}
				}
			}
		}
		return false;
	}
}