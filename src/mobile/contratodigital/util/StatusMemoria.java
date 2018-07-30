package mobile.contratodigital.util;

import android.util.Log;
/**
 * Classe criada para analisar o status de memoria usada
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class StatusMemoria {

	public void mostraStatusMemoria(){
		
		final Runtime runtime = Runtime.getRuntime();
		final long usedMemInMB = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
		final long maxHeapSizeInMB = runtime.maxMemory() / 1048576L;
		final long availHeapSizeInMB = maxHeapSizeInMB - usedMemInMB;
		
		Log.i("tag", "Usada MB = "+usedMemInMB);
		Log.i("tag", "Max Heap MB = "+maxHeapSizeInMB);
		Log.i("tag", "Disponivel MB = "+availHeapSizeInMB);
		Log.i("tag", "------------------------------------------------------");
		
	}

}
