package mobile.contratodigital.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
/**
 * Classe criada para chamar o aplicativo fotoshop
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */

public class ChamaAplicativo {

	public void chamaOApp(Context context, String packageName, String chave, String valor) {
		
	    Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
	    
	    if (intent == null) {
	    	
	    	Toast.makeText(context, "Não foi encontrado o aplicativo de fotos", Toast.LENGTH_LONG).show();
	    	
	    	intent = new Intent(Intent.ACTION_VIEW);
	        intent.setData(Uri.parse("market://details?id=" + packageName));
	    }
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.putExtra(chave, valor);
	    context.startActivity(intent);
	}
}
