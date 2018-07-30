package mobile.contratodigital.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.IpRS;
import mobile.contratodigital.enums.IpURL;
import mobile.contratodigital.enums.TipoView;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.ws.ExportaArquivosWS;
import sharedlib.contratodigital.model.Layout;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.model.Representante;

/**
 * Classe do tipo activity  usada para exportar dados
 * @author Edição - Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Criação -Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class ActivityExportar extends Activity {

	private static final String RESOURCE_REST_ARQUIVOS = "/Retorno/Arquivo/";
	private Menu menu;
	private static String URLescolhida = IpRS.URL_SIVA_REST;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Context context = this;

		final Dao dao = new Dao(context);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		actionBar.setTitle("Comunicar");
		

		LinearLayout linearLayoutPrincipal = new LinearLayout(context);
		linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
		linearLayoutPrincipal.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		TextView textView_exportar = new TextView(context);
				 textView_exportar.setText("Exportar dados e arquivos:");
				 textView_exportar.setGravity(Gravity.CENTER_HORIZONTAL);
				 textView_exportar.setTextSize(30);
				 textView_exportar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		linearLayoutPrincipal.addView(textView_exportar);

		ImageButton imageButton_exportar = new ImageButton(context);	   
					imageButton_exportar.setImageDrawable(getResources().getDrawable(R.drawable.export512));
					imageButton_exportar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					imageButton_exportar.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							int qtdDeMovimentos = 0;

							for (Layout layout : dao.listaTodaTabela(Layout.class, 
																	 Layout.COLUMN_INTEGER_IND_TIP_LAYOUT, TipoView.LAYOUT_FORMULARIO.getValor())) {

								qtdDeMovimentos = dao.selectCount_where_nrVisita_naoEhZERO(Movimento.class, layout.getNr_layout());
								
								//qtdDeMovimentos = dao.selectCount(Movimento.class);
					
								if (qtdDeMovimentos > 0) {
									
									ExportaArquivosWS exportarFotos = new ExportaArquivosWS(context, URLescolhida + RESOURCE_REST_ARQUIVOS);
								 					  exportarFotos.exportar();
									
								 	break;
								}
							}

							if (qtdDeMovimentos == 0) {
								
								new MeuAlerta( "Não possui dados para exportar", null, context).meuAlertaOk();

								//Toast.makeText(context, "Não possui dados para exportar", Toast.LENGTH_SHORT).show();
							}

						}
						
					});
		linearLayoutPrincipal.addView(imageButton_exportar);
		
/*		
		TextView textView_exportarArquivos = new TextView(context);
				 textView_exportarArquivos.setText("Exportar apenas arquivos:");
				 textView_exportarArquivos.setGravity(Gravity.CENTER_HORIZONTAL);
				 textView_exportarArquivos.setTextSize(30);
				 textView_exportarArquivos.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		linearLayoutPrincipal.addView(textView_exportarArquivos);

		ImageButton imageButton_exportarArquivos = new ImageButton(context);
			   		imageButton_exportarArquivos.setImageDrawable(getResources().getDrawable(R.drawable.import_export_icon_256));
			   		imageButton_exportarArquivos.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			   		imageButton_exportarArquivos.setOnClickListener(new OnClickListener() {
			   			@Override
			   			public void onClick(View v) {

			   				//File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/");
			   				File file = new File(Environment.getExternalStorageDirectory() + "/ContratoDigital/");

			   			 	if(!file.exists()){
			   		    		file.getParentFile().mkdirs();  	
			   		    	}
			   		   	
			   			 	if(file.listFiles() == null){
			   			 		
			   			 		Toast.makeText(context, "Não possui arquivos para exportar", Toast.LENGTH_SHORT).show();
			   			 	}
			   			 	else{
						   		   //int qtdFotos = file.listFiles().length;  
								   //if (qtdFotos > 0) {
			
			   			 		ExportaArquivosWS exportarArquivos = new ExportaArquivosWS(context, IpURL.URL_SERVER_REST.getValor() + RESOURCE_REST_ARQUIVOS);
												exportarArquivos.exportar();	
								   
								   //}else{						   
									  //Toast.makeText(context, "Não encontrou arquivos para exportar", Toast.LENGTH_SHORT).show();
								   //} 		
			   			 	}
					   
					   
				   }
		});
		linearLayoutPrincipal.addView(imageButton_exportarArquivos);*/

		setContentView(linearLayoutPrincipal);
	}
	public boolean onCreateOptionsMenu(Menu menuinterno) {
		menu = menuinterno;
		
		menu.add(0,0,0, "Oi");
		menu.add(0,1,0,"Vogel");
		menu.add(0,2,0,"WCS");
		menu.add(0,3,0,"Local");
		menu.getItem(0).setEnabled(false);
		
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		case 0: URLescolhida = IpRS.acaoLinkOi(menu);
		return true;
	
		case 1: URLescolhida = IpRS.acaoLinkvogel(menu);
		return true;
		
		case 2:
			 URLescolhida = IpRS.acaoLinWCS(menu);
			return true;
			
		case 3:
			 URLescolhida = IpRS.acaoLinkLocal(menu);
		return true;
	
	
		}
		return super.onOptionsItemSelected(item);

	}
	@Override
	public void onBackPressed() {

		Intent intent = new Intent(ActivityExportar.this, ActivityDashboard.class);
		startActivity(intent);
		finish();
	}
}
