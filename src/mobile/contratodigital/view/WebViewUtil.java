package mobile.contratodigital.view;

import android.content.Context;
import android.webkit.WebView;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.model.ContratoUtil;
import mobile.contratodigital.util.TrabalhaComFotos;
import sharedlib.contratodigital.model.Movimento;

public class WebViewUtil {

	public void copiaImagemDaTelaAtual(WebView webView, Dao dao, Context context, Movimento mov_informacoesCliente, String nomeDoArquivo) {

		ContratoUtil contratoUtil = new ContratoUtil(dao, context);

		String diretorioDestino = contratoUtil.devolveDiretorioAserUtilizado(mov_informacoesCliente.getNr_visita());

		String diretorioDestinoComNomeDoArquivo = diretorioDestino+nomeDoArquivo;
		
		TrabalhaComFotos trabalhaComFotos = new TrabalhaComFotos();	
						 trabalhaComFotos.capturar(webView, mov_informacoesCliente, dao, context, diretorioDestinoComNomeDoArquivo);
	}

}
