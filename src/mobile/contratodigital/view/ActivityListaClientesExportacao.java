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
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.util.TelaBuilder;
import sharedlib.contratodigital.model.*;
import sharedlib.contratodigital.util.Generico;

public class ActivityListaClientesExportacao extends Activity {

	private Context context;
	//private ActionBar actionBar;
	//private ListView listView;
	private List<Movimento> listaComMovimentos;	
	//private Dao dao;
	private ArrayAdapterCliente adapterCliente;
	//private Layout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = ActivityListaClientesExportacao.this;
			
		Dao dao = new Dao(context);
		
		Layout layout = (Layout) dao.devolveObjeto(Layout.class, 
											Layout.COLUMN_INTEGER_OBRIGATORIO, Generico.LAYOUT_OBRIGATORIO_SIM.getValor());
		
		if(layout == null){
			
			listaComMovimentos = new ArrayList<Movimento>();
		}
		else{
			listaComMovimentos = dao.listaTodaTabela_GroupBy_NrVisita(Movimento.class, layout.getNr_layout());
		}	
		
	 	adapterCliente = new ArrayAdapterCliente(context, R.layout.adapter_cliente, listaComMovimentos);
	 	
	 	ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		actionBar.setTitle("Exportar Informação dos Clientes");		

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

				for (Movimento movimento : listaComMovimentos) {

					if (movimento.getNr_visita() == view.getId()) {
						
						abreActivityExportar(movimento, view.getId());
						
						break;
					}
				}
			}
		});
		linearLayoutTela.addView(listView);
		
		return linearLayoutTela;
	}
	
	private void abreActivityExportar(Movimento movimento, int viewId){
		
		Bundle bundle = new Bundle();	
	   	       bundle.putSerializable("movimento", movimento);

	   	Intent intent = new Intent(context, ActivityExportar.class);
    	   	   intent.putExtras(bundle);

    	startActivityForResult(intent, viewId);		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (intent != null) {
			//adapter.notifyDataSetChanged();			
		}
	}

	@Override
	public void onBackPressed() {

		startActivity(new Intent(ActivityListaClientesExportacao.this, ActivityDashboard.class));
		finish();
	}

}
