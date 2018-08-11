package mobile.contratodigital.view;

import java.util.ArrayList;
import java.util.List;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.IpRS;
import mobile.contratodigital.model.ContratoUtil;
import mobile.contratodigital.util.AcaoAlertDialog;
import mobile.contratodigital.util.AcaoExportarArquivosEDados;
import mobile.contratodigital.util.Aviso;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.TelaBuilder;
import sharedlib.contratodigital.model.*;
import sharedlib.contratodigital.util.Generico;

public class ActivityListaClientesExportacao extends Activity implements Aviso{

	private Dao dao;
	private Menu menu;
	private Context context;
	private List<Movimento> listaComMovimentos;	
	private ArrayAdapterCliente adapterCliente;
	private static String URLescolhida = IpRS.URL_SIVA_REST;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = ActivityListaClientesExportacao.this;
			
		dao = new Dao(context);
		
		Layout layout = (Layout) dao.devolveObjeto(Layout.class, 
											Layout.COLUMN_INTEGER_OBRIGATORIO, Generico.LAYOUT_OBRIGATORIO_SIM.getValor());
		
		if(layout == null){
			
			listaComMovimentos = new ArrayList<Movimento>();
		}else{
			listaComMovimentos = dao.listaTodaTabela_GroupBy_NrVisita(Movimento.class, layout.getNr_layout());
		}	
		
	 	adapterCliente = new ArrayAdapterCliente(context, R.layout.adapter_cliente, listaComMovimentos);
	 	
	 	ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		actionBar.setTitle("Comunicar");		

		setContentView(constroiTelaInicial());
	}
	
	private LinearLayout constroiTelaInicial(){

		TelaBuilder telaBuilder = new TelaBuilder(context);
		
		LayoutParams layoutParams_MATCH_MATCH = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		LinearLayout linearLayoutTela = telaBuilder.cria_LL(layoutParams_MATCH_MATCH, R.color.plano_de_fundo_layout);
		
		ListView listView = telaBuilder.cria_LV();
		listView.setAdapter(adapterCliente);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				clicouEmUmItemDaLista(view);
			}
		});
		linearLayoutTela.addView(listView);
		
		return linearLayoutTela;
	}
	
	private void clicouEmUmItemDaLista(View view) {
		
		for (Movimento movimento : listaComMovimentos) {

			if (movimento.getNr_visita() == view.getId()) {
				
				AcaoAlertDialog acaoExportar = new AcaoExportarArquivosEDados(movimento, dao, context, URLescolhida);
				
				MeuAlerta meuAlerta = new MeuAlerta("Atenção", "Confirma comunicação do cliente: "
															   +movimento.getInformacao_1()+" ?", context);
						  meuAlerta.meuAlertaSimNao(acaoExportar);
				
				break;
			}
		}
	}

	@Override
	public void avisaQueTerminou(Movimento movimento) {

		ContratoUtil contratoUtil = new ContratoUtil(dao, context);
					 contratoUtil.deletaCliente(movimento, listaComMovimentos, adapterCliente);
	}
	
	@Override
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

	@Override
	public void onBackPressed() {

		startActivity(new Intent(context, ActivityDashboard.class));
		finish();
	}

}
