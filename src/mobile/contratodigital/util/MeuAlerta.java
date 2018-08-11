package mobile.contratodigital.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MeuAlerta {

	private String titulo;
	private String conteudo;
	private Context context;
	
	public MeuAlerta(String _titulo, String _conteudo, Context _context) {
		this.titulo = _titulo;
		this.conteudo = _conteudo;
		this.context = _context;
	}
	
	public void meuAlertaOk() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context, 1);
		builder.setTitle(titulo)
			   .setMessage(conteudo)
		   	   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		   		   @Override
				public void onClick(DialogInterface dialog, int id) {
		   	
		   		   }
		   	   });
		builder.show();
	}
	
	public void meuAlertaSimNao(final AcaoAlertDialog acaoAlertDialog){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context, 1);
		builder.setTitle(titulo)
			   .setMessage(conteudo)
			   .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						acaoAlertDialog.fazAcaoSIM(context);	
					}
				})
			   .setNegativeButton("Não", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						acaoAlertDialog.fazAcaoNAO(context);			
					}
				});		
		builder.show();
	}
	
}
