package mobile.contratodigital.view;

import java.io.Serializable;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.util.TelaBuilder;
import sharedlib.contratodigital.model.*;
import sharedlib.contratodigital.util.Generico;

/**
 * Classe do tipo activity  usada para mostrar e liberar seleção de clientes ainda não sincronizados
 * @author Edição - Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Criação- Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class ActivityListaClientes extends Activity {

	private Context context;
	private ActionBar actionBar;
	private ListView listView;
	private List<Movimento> listaComMovimentos;	
	private Dao dao;
	private ArrayAdapterCliente adapterCliente;
	private Layout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = ActivityListaClientes.this;
			
		dao = new Dao(context);
		
		layout = (Layout) dao.devolveObjeto(Layout.class, 
											Layout.COLUMN_INTEGER_OBRIGATORIO, Generico.LAYOUT_OBRIGATORIO_SIM.getValor());
		
		if(layout == null){
			
			listaComMovimentos = new ArrayList<Movimento>();
		}
		else{
			listaComMovimentos = dao.listaTodaTabela_GroupBy_NrVisita(Movimento.class, layout.getNr_layout());
		}	
		
	 	adapterCliente = new ArrayAdapterCliente(context, R.layout.adapter_cliente, listaComMovimentos);

	 	
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		actionBar.setTitle("Lista de Clientes");		

		setContentView(constroiTelaInicial());
	}
	
	private LinearLayout constroiTelaInicial(){

		TelaBuilder telaBuilder = new TelaBuilder(context);
		
		LayoutParams layoutParams_MATCH_MATCH = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		LinearLayout linearLayoutTela = telaBuilder.cria_LL(layoutParams_MATCH_MATCH, R.color.plano_de_fundo_layout);

		
		listView = telaBuilder.cria_LV();
		listView.setAdapter(adapterCliente);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				for (Movimento movimento : listaComMovimentos) {

					if (movimento.getNr_visita() == view.getId()) {
						
						abreFragActivityOcorrencia(movimento, view.getId());
						
						break;
					}
				}
			}
		});
				
		LinearLayout linearLayout_holderListView = telaBuilder.cria_LL_HOLDER(0.06f);		
					 		linearLayout_holderListView.addView(listView);
		linearLayoutTela.addView(linearLayout_holderListView);
		
		LinearLayout linearLayout_holderButton = telaBuilder.cria_LL_HOLDER(0.94f);
		
		Button button_cadastrarNovoCliente = telaBuilder.cria_BT_paraListaDeVendas("Cadastrar Novo Cliente");
			   button_cadastrarNovoCliente.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				acaoAposCliqueNoBotao();
			}
		});
					 linearLayout_holderButton.addView(button_cadastrarNovoCliente);
		linearLayoutTela.addView(linearLayout_holderButton);
		
		return linearLayoutTela;
	}

	private void acaoAposCliqueNoBotao(){
	
		Movimento movimento = new Movimento();
		 
		if(listaComMovimentos.size() == 0){
	  		
			movimento.setNr_programacao(1);				
		}else{					
			movimento.setNr_programacao(listaComMovimentos.get(0).getNr_programacao());
		}
		
		int ultimoNrVisita = listaComMovimentos.size(); //dao.devolveUltimo_NrVisita(Movto_visita_repres.class);
		
		int ultimoNrVisita_mais1 = ultimoNrVisita + 1;
					  	
		  	movimento.setNr_visita(ultimoNrVisita_mais1);

		abreFragActivityOcorrencia(movimento, ultimoNrVisita_mais1);
	}
	
	private void abreFragActivityOcorrencia(Movimento movimento, int viewId){
		
		Bundle bundle = new Bundle();	
	   	       bundle.putSerializable("movimento", movimento);

	   	       

	   	Intent intent = new Intent(context, FragActivityOcorrencia.class);
    	   	   intent.putExtras(bundle);

    	startActivityForResult(intent, viewId);		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		listaComMovimentos = dao.listaTodaTabela_GroupBy_NrVisita(Movimento.class, layout.getNr_layout());	

		if (intent != null) {
		
			for (Movimento movimento : listaComMovimentos) {

				if (movimento.getNr_visita() == requestCode) {
					
					if (resultCode == Generico.PREENCHEU_FORMULARIO_OBRIGATRORIOS_SIM.getValor()) {

						movimento.setStatus(Generico.REALIZADA.getValor());

						dao.insereOUatualiza(movimento,
											 //Movimento.COLUMN_INTEGER_NR_PROGRAMACAO, movimento.getNr_programacao(), 
											 Movimento.COLUMN_INTEGER_NR_LAYOUT, movimento.getNr_layout(), 
											 Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita());
						
						finish();
						startActivity(getIntent());
					}
					
					if (resultCode == Generico.PREENCHEU_FORMULARIO_OBRIGATRORIOS_NAO.getValor()) {

						movimento.setStatus(Generico.NAO_REALIZADA.getValor());

						dao.insereOUatualiza(movimento, 
								 			 //Movimento.COLUMN_INTEGER_NR_PROGRAMACAO, movimento.getNr_programacao(), 
								 			 Movimento.COLUMN_INTEGER_NR_LAYOUT, movimento.getNr_layout(), 
								 			 Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita());						
					}
					
					break;
				}			
			}
			//adapter.notifyDataSetChanged();			
		}
	}

	@Override
	public void onBackPressed() {

		startActivity(new Intent(ActivityListaClientes.this, ActivityDashboard.class));
		finish();
	}

}
