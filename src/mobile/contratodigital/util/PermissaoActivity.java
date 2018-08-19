package mobile.contratodigital.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;

public class PermissaoActivity extends Activity{
	
	private static final int REQUISICAO_PERMISSAO_ESCRITA = 333;
	private static final int REQUISICAO_PERMISSAO_LEITURA = 222;
	private static final int REQUISICAO_PERMISSAO_TIRAR_FOTO = 111;

	
	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {	
	    	  
    	  if (requestCode == REQUISICAO_PERMISSAO_TIRAR_FOTO) {
	    	  
    		  //tirarFoto();  	  	    	
	      }
    	  
    	  if (requestCode == REQUISICAO_PERMISSAO_ESCRITA) {
   
    		  //salvarFoto();
    	  }
    	  
    	  if (requestCode == REQUISICAO_PERMISSAO_LEITURA) {
    			  
    		  //buscarFoto();		  
    	  }
    	  
	  }	 
	}


}
