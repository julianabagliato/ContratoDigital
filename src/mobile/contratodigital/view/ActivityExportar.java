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
import mobile.contratodigital.enums.TipoView;
import mobile.contratodigital.model.ContratoUtil;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.ws.ExportaArquivosWS;
import sharedlib.contratodigital.model.Layout;
import sharedlib.contratodigital.model.Movimento;

public class ActivityExportar extends Activity {

	private Menu menu;
	private static String URLescolhida = IpRS.URL_SIVA_REST;
	private Movimento movimento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();

		movimento = (Movimento) bundle.getSerializable("movimento");

		Context context = this;

		Dao dao = new Dao(context);

		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
				  actionBar.setTitle("Comunicar");

		setContentView(criaTela(dao, context));
	}
	
	private LinearLayout criaTela(final Dao dao, final Context context) {

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

							acaoAposCliqueNoBotaoExportar(dao, context);							
						}
					});
		linearLayoutPrincipal.addView(imageButton_exportar);

		return linearLayoutPrincipal;
	}
	
	private void acaoAposCliqueNoBotaoExportar(Dao dao, Context context) {
				
		ContratoUtil contratoUtil = new ContratoUtil(dao, context);
				
		if(contratoUtil.naoTemNumeroDeContrato(movimento.getNr_visita())){
			
			new MeuAlerta("Cliente não possui contrato vinculado", null, context).meuAlertaOk();
		}
		else {
			if(contratoUtil.preencheu2LayoutsObrigatoriosAntesDeExportar(movimento.getNr_visita())) {
				
				String diretorioDoCliente = contratoUtil.devolveDiretorioAserUtilizado(movimento.getNr_visita());
				String pastaDoCliente = movimento.getNr_contrato();
				ExportaArquivosWS exportarFotos = new ExportaArquivosWS(context, URLescolhida, diretorioDoCliente, pastaDoCliente);
								  exportarFotos.exportar();
			}
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menuinterno) {
		menu = menuinterno;
		
		menu.add(0,0,0, "Oi");
		menu.add(0,1,0, "Vogel");
		menu.add(0,2,0, "WCS");
		menu.add(0,3,0, "Local");
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
		
		case 2: URLescolhida = IpRS.acaoLinWCS(menu);
			return true;
			
		case 3: URLescolhida = IpRS.acaoLinkLocal(menu);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
