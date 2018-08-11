package mobile.contratodigital.util;

import java.io.File;
import android.content.Context;

public class TrabalhaComArquivos {

	public void removeDiretorioDoCliente(Context context, String diretorioDoCliente) {

		boolean deletou = false;

		File pastaDoCliente = new File(diretorioDoCliente);
		if(!pastaDoCliente.exists()) {
			pastaDoCliente.mkdirs();
		}
		
		for (File arquivo : pastaDoCliente.listFiles()) {
	
			deletou = arquivo.delete();
		}
		pastaDoCliente.delete();

		if (deletou) {
			new MeuAlerta("Arquivos removidos! ", null, context).meuAlertaOk();
		}
	}
}
