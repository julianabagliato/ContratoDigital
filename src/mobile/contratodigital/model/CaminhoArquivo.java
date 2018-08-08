package mobile.contratodigital.model;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import mobile.contratodigital.R;
import mobile.contratodigital.util.MeuAlerta;
import sharedlib.contratodigital.model.Movimento;

public class CaminhoArquivo {

	private String arquivo;
	private String caminho;
	private Context context;
	
	public CaminhoArquivo(Context context) {
		this.context = context;
	}
	public CaminhoArquivo(String arquivo, String caminho) {
		this.arquivo = arquivo;
		this.caminho = caminho;
	}

	public String getArquivo() {
		return arquivo;
	}

	public String getCaminho() {
		return caminho;
	}

	@Override
	public String toString() {

		return this.arquivo;
	}
	
	public void mostraListaDeArquivos(Movimento mov_informacoesCliente, String srcContrato) {
		
		ArrayList<CaminhoArquivo> lista = populaListaComNomeDeArquivosBaseadoEmDiretorio(srcContrato);

		if (lista.isEmpty()) {
			
			informaQnaoTemArquivos();
		} else {
			escolheApenasUmItemDaLista("Arquivos Gerados", lista);
		}
	}

	private ArrayList<CaminhoArquivo> populaListaComNomeDeArquivosBaseadoEmDiretorio(String diretorioAserProcurado) {

		ArrayList<CaminhoArquivo> lista = new ArrayList<CaminhoArquivo>();

		File file2 = new File(diretorioAserProcurado);

		File[] listaComArquivos = file2.listFiles();

		if (listaComArquivos == null) {
			
			informaQnaoTemArquivos();
		} else {
			for (File arquivo : listaComArquivos) {

				String arq = arquivo.toString();

				if (arq.contains(".pdf") 
						|| arq.contains(".jpg") 
							|| arq.contains(".doc") 
								|| arq.contains(".docx")
									|| arq.contains(".png")
										|| arq.contains(".txt")) {

					int posicaoDaUltimaBarra = arq.lastIndexOf("/");
					int tamanhoTotal = arq.length();

					String caminho = arq.substring(0, posicaoDaUltimaBarra + 1);
					String nomeDoArquivo = arq.substring(posicaoDaUltimaBarra + 1, tamanhoTotal);

					lista.add(new CaminhoArquivo(nomeDoArquivo, caminho));
				}
			}
		}
		return lista;
	}

	private void informaQnaoTemArquivos() {
		
		new MeuAlerta("Não contem arquivos", null, context).meuAlertaOk();	
	}

	private void escolheApenasUmItemDaLista(String titulo, final ArrayList<CaminhoArquivo> lista) {

		ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.item_menu_geral, lista);

		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setTitle(titulo);
		builder1.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int posicao) {

				for (int i = 0; i < lista.size(); i++) {

					if (posicao == i) {
							
							CaminhoArquivo caminhoArquivo = lista.get(i);
							
							String caminho = caminhoArquivo.getCaminho();
							String arquivo = caminhoArquivo.getArquivo();
							
							String caminhoComArquivo = caminho + arquivo;
							
							int posicaoOndeEstaOPonto = caminhoComArquivo.indexOf(".");
							
							String diretorioDoArquivoSemExtensao = caminhoComArquivo.substring(0, posicaoOndeEstaOPonto);
							
							String apenasExtensao = caminhoComArquivo.substring(posicaoOndeEstaOPonto + 1, caminhoComArquivo.length());
							
							chamaVisualizadorBaseadoExtensao(context, diretorioDoArquivoSemExtensao, apenasExtensao);
					}
				}
				dialogInterface.dismiss();
			}
		});

		builder1.show();
	}

	private void chamaVisualizadorBaseadoExtensao(Context context, String SRC_CONTRATO, String extensaoPDFouDOC) {

		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extensaoPDFouDOC);
	
	    Log.i("tag","mimeType: "+mimeType);	    				  
		
		String caminhoComExtensao = SRC_CONTRATO +"."+ extensaoPDFouDOC;
		
		try {
			Uri path = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName(), new File(caminhoComExtensao));
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(path, mimeType);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	
			context.startActivity(intent);
		} catch (Exception ex) {
			Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
		}        
	}

}
