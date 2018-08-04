package mobile.contratodigital.view;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.model.ItemPeca;
import mobile.contratodigital.util.Alert;
import mobile.contratodigital.util.Alert.AlertType;
import mobile.contratodigital.util.DataPersonalizada;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.TelaBuilder;
import sharedlib.contratodigital.model.Cad_pecas;
import sharedlib.contratodigital.model.Movimento;

public class ActivityPecasNew extends Activity {

	private Context context;
	private Movimento mov_informacoesCliente;
	private Dao dao;
	private Movimento mov_pecas;
	private LinearLayout llHolderItensAdicionados;
	private TelaBuilder telaBuilder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = ActivityPecasNew.this;
		
		Bundle bundle = getIntent().getExtras();
			
		mov_informacoesCliente = (Movimento) bundle.getSerializable("mov_informacoesCliente");

			mov_pecas = new Movimento();
			mov_pecas.setCod_rep( mov_informacoesCliente.getCod_rep());
			mov_pecas.setNr_visita(mov_informacoesCliente.getNr_visita());
			mov_pecas.setNr_contrato(mov_informacoesCliente.getNr_contrato());
			mov_pecas.setNr_layout(NomeLayout.PECAS.getNumero());
			mov_pecas.setNr_programacao(mov_informacoesCliente.getNr_programacao());
			mov_pecas.setData_cadastro(new DataPersonalizada().pegaDataAtual_DDMMYYYY_HHMMSS());

		telaBuilder = new TelaBuilder(context);

		List<ItemPeca> listaDeItemPecas = new ArrayList<ItemPeca>();
		
		dao = new Dao(context);
		
		List<Cad_pecas> listaComCad_pecas = dao.listaTodaTabela(Cad_pecas.class);
		
		for (Cad_pecas cad_pecas : listaComCad_pecas) {
			
			listaDeItemPecas.add(new ItemPeca(cad_pecas.getDesc_item(), cad_pecas.getIt_codigo()));
		}

		setContentView(constroiTela(listaDeItemPecas));
	}
	
	private LinearLayout constroiTela(List<ItemPeca> listaDeItemPecas) {
		
		final ArrayAdapterItemPeca baseAdapterPecas = new ArrayAdapterItemPeca(context, 0, listaDeItemPecas);

		LinearLayout llTela100 = telaBuilder.cria_LL_HOLDER(1f);
		
						  LinearLayout llTela90 = telaBuilder.cria_LL_HOLDER(0.90f);

										   LinearLayout llHolderEditText = telaBuilder.cria_LL_HOLDER(1f);
													    llHolderEditText.addView(criaEditTextPesquisa(baseAdapterPecas));
									   llTela90.addView(llHolderEditText);
					 llTela100.addView(llTela90);
					 
						  LinearLayout llTela10 = telaBuilder.cria_LL_HOLDER(0.10f);

										   LinearLayout llHolderListView = telaBuilder.cria_LL_HOLDER(0.50f);
					 									llHolderListView.addView(criaListViewPecas(baseAdapterPecas, listaDeItemPecas));
					 				   llTela10.addView(llHolderListView);
					 				   
					 					   				llHolderItensAdicionados = telaBuilder.cria_LL_HOLDER(0.50f);
					 					   							TextView tvTitulo = telaBuilder.cria_TV_titulo("Peças Adicionadas");
					 					   											tvTitulo.setTextColor(Color.BLUE);
					 									llHolderItensAdicionados.addView(tvTitulo);	
					 				   llTela10.addView(llHolderItensAdicionados);
					 llTela100.addView(llTela10);

		return llTela100;
	}
			
	private EditText criaEditTextPesquisa(final ArrayAdapterItemPeca baseAdapterPecas) {
		
		EditText etPesquisa = new EditText(context);
		etPesquisa.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		etPesquisa.setHint("Informe o nome da peça que procura:");
		etPesquisa.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (count < before) {
					// We're deleting char so we need to reset the adapter data
					baseAdapterPecas.resetData();
				}
				baseAdapterPecas.getFilter().filter(s.toString());
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});	
		return etPesquisa;
	} 
	
	private ListView criaListViewPecas(ArrayAdapterItemPeca baseAdapterPecas, final List<ItemPeca> listaDeItemPecas) {
		
		ListView listView = new ListView(context);
		listView.setAdapter(baseAdapterPecas);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				acaoAposClickNaLista(view, listaDeItemPecas);				
			}
		});
		return listView;
	}
	
	private void acaoAposClickNaLista(View view, List<ItemPeca> listaDeItemPecas) {
		
		for (ItemPeca itemPeca : listaDeItemPecas) {

			if (itemPeca.getCodigo() == view.getTag()) {
		
				solicitaConfirmacao(itemPeca);
				break;
			}
		}
	}
	
	private void solicitaConfirmacao(final ItemPeca itemPeca) {
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		   alertDialog.setTitle("Atenção");
	       alertDialog.setMessage("Deseja Adicionar o item "+itemPeca.getCodigo()+" na lista?");
	       alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
					solicitaQuantidade(itemPeca);
				}
			});
	        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});	        
	        alertDialog.show();
	}
	
	private void solicitaQuantidade(final ItemPeca itemPeca) {

		final EditText etQuantidade = new EditText(context);
					   etQuantidade.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					   etQuantidade.setInputType(InputType.TYPE_CLASS_NUMBER);

		final TextView tvErro = new TextView(context);
					   tvErro.setTextColor(Color.RED);
		
		LinearLayout ll = new LinearLayout(context);	
					 ll.setOrientation(LinearLayout.VERTICAL);
					 ll.addView(etQuantidade);
					 ll.addView(tvErro);
					   
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
		   .setTitle(itemPeca.getCodigo())
		   .setView(ll)
	       .setMessage(itemPeca.getNome()+"\n\nInforme a quantidade:")	       
	       .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
	       .setNegativeButton(android.R.string.cancel, null)       
           .create();
		alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
	            @Override
	            public void onShow(DialogInterface dialogInterface) {

	                Button button = ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
	                button.setOnClickListener(new View.OnClickListener() {
	                    @Override
	                    public void onClick(View view) {
	                    	
	                    	String quantidade = etQuantidade.getText().toString();
	    					
	    					if(quantidade.isEmpty() || quantidade.equals("0")) {
	    						
	    						tvErro.setText("Quantidade inválida");
	    					}else {
	    						adicionaItemNaLista(itemPeca, quantidade);
	    				
	    						alertDialog.dismiss();
	    					}
	                    }
	                });
	            }
	        });
	        alertDialog.show();	        
	}
	
	private void adicionaItemNaLista(ItemPeca itemPeca, String quantidade) {
		
		String descricao = "QTD: "+quantidade+" | "+itemPeca.getCodigo()+" | "+itemPeca.getNome();
		llHolderItensAdicionados.addView(telaBuilder.cria_TV_conteudo13(descricao));		
	}
	
	private void Finalizar() {
		//insereItemLayout(dao2,NomeLayout.PECAS.getNumero(),i, "|");

		dao.insereOUatualiza(mov_pecas,
				Movimento.COLUMN_INTEGER_NR_LAYOUT, mov_pecas.getNr_layout(), 
				Movimento.COLUMN_INTEGER_NR_VISITA, mov_pecas.getNr_visita());
		
		new MeuAlerta(  "Ação efetuda", null, context).meuAlertaOk();
		
			setResult(444, new Intent());
		 	finish();		
	}
	
	@Override
	public void onBackPressed() {
		
		setResult(444, new Intent());
		finish();
	}
}

