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
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.model.ItemPeca;
import mobile.contratodigital.util.Animacao;
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
	private TelaBuilder telaBuilder;
	private Animacao animacao;
	private List<ItemPeca> listaDePecasDoCadPecas;
	private List<ItemPeca> listaDePecasDoMovimento;
	private static final String LISTA_ADICIONAR_ITEM = "lista1";
	private static final String LISTA_ALTERAR_REMOVER_ITEM = "lista2";
	private ArrayAdapterItemPeca adapterPecasDoCadPecas;
	private ArrayAdapterItemPeca adapterPecasDoMovimento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = ActivityPecasNew.this;
		
		Bundle bundle = getIntent().getExtras();
			
		mov_informacoesCliente = (Movimento) bundle.getSerializable("mov_informacoesCliente");

		telaBuilder = new TelaBuilder(context);
		animacao = new Animacao();
				
		dao = new Dao(context);
		
		mov_pecas = (Movimento)dao.devolveObjeto(Movimento.class,
												 Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.PECAS.getNumero(), 
												 Movimento.COLUMN_INTEGER_NR_VISITA, mov_informacoesCliente.getNr_visita());
		
		if(mov_pecas == null) {
		
			mov_pecas = new Movimento();
			mov_pecas.setNr_layout(NomeLayout.PECAS.getNumero());
			mov_pecas.setNr_visita(mov_informacoesCliente.getNr_visita());
			//talves status
			mov_pecas.setCod_rep(mov_informacoesCliente.getCod_rep());
			mov_pecas.setData_cadastro(new DataPersonalizada().pegaDataAtual_DDMMYYYY_HHMMSS());
			mov_pecas.setNr_contrato(mov_informacoesCliente.getNr_contrato());
		}
		
		listaDePecasDoMovimento = new ArrayList<ItemPeca>();			
	
		adicionaListaPecasDoMovimento(dao, mov_pecas.getInformacao_1(), listaDePecasDoMovimento);
		
		listaDePecasDoCadPecas = new ArrayList<ItemPeca>();

		adicionaListaPecasDoCadPecas(dao, listaDePecasDoCadPecas);
		
		removeItemDoCadPecasSeExistirNoMovimento(listaDePecasDoCadPecas, listaDePecasDoMovimento);

		setContentView(constroiTela(listaDePecasDoCadPecas, listaDePecasDoMovimento));
	}
	
	private void adicionaListaPecasDoMovimento(Dao dao, String info_1, List<ItemPeca> listaDePecasDoMovimento) {
		
		for(ItemPeca itemPeca : devolveCodigos(info_1)) {
			
			Cad_pecas cad_pecas = (Cad_pecas)dao.devolveObjeto(Cad_pecas.class, Cad_pecas.COLUMN_TEXT_it_codigo, itemPeca.getCodigo());
			
			if(cad_pecas != null) {
				
				listaDePecasDoMovimento.add(new ItemPeca(itemPeca.getQuantidade(), cad_pecas.getIt_codigo(), cad_pecas.getDesc_item()));
			}
		}	
	}

	private void adicionaListaPecasDoCadPecas(Dao dao, List<ItemPeca> listaDeItemPecasDoCadPecas){
		
		for (Cad_pecas cad_pecas : dao.listaTodaTabela(Cad_pecas.class)) {

			listaDeItemPecasDoCadPecas.add(new ItemPeca("", cad_pecas.getIt_codigo(), cad_pecas.getDesc_item()));
		}
	}
	
	private void removeItemDoCadPecasSeExistirNoMovimento(List<ItemPeca> listaDePecasDoCadPecas, List<ItemPeca> listaDePecasDoMovimento) {
		
		for(ItemPeca itemPecaDoMovimento : listaDePecasDoMovimento) {
			
			for(ItemPeca itemPecaDoCadastroDePecas : listaDePecasDoCadPecas) {
				
				if(itemPecaDoMovimento.getCodigo().equals(itemPecaDoCadastroDePecas.getCodigo())) {
					
					listaDePecasDoCadPecas.remove(itemPecaDoCadastroDePecas);
					break;			
				}				
			}
		}					
	}

	private List<ItemPeca> devolveCodigos(String info1){
		
		List<ItemPeca> listaComCodigos = new ArrayList<ItemPeca>();

		if(info1 == null || info1.isEmpty()) {
			return listaComCodigos;
		}

		if(info1.contains(";")) {

			String lista[] = info1.split(";");
		
			for(int i=0; i<lista.length; i++) {
			
				String qtdCodigo = lista[i];
				
				if(qtdCodigo.contains("#")) {
	
					String lista2[] = qtdCodigo.split("#");
			
					String quantidade = lista2[0];
					String codigo = lista2[1];
				
					listaComCodigos.add(new ItemPeca(quantidade, codigo, ""));
				}
			}
		}

		return listaComCodigos;
	}
	
	private LinearLayout constroiTela(List<ItemPeca> listaDeItemPecasDoCadPecas, List<ItemPeca> listaDePecasDoMovimento) {
		
		adapterPecasDoCadPecas = new ArrayAdapterItemPeca(context, 0, listaDeItemPecasDoCadPecas);
		adapterPecasDoMovimento = new ArrayAdapterItemPeca(context, 0, listaDePecasDoMovimento);

		LinearLayout llTela100 = telaBuilder.cria_LL_HOLDER(1f);
		
						  LinearLayout llTela90 = telaBuilder.cria_LL_HOLDER(0.90f);

										   LinearLayout llHolderEditText = telaBuilder.cria_LL_HOLDER(1f);
													    llHolderEditText.addView(criaEditTextPesquisa(adapterPecasDoCadPecas));
									   llTela90.addView(llHolderEditText);
					 llTela100.addView(llTela90);
					 
						  LinearLayout llTela10 = telaBuilder.cria_LL_HOLDER(0.10f);

										   LinearLayout llHolderListView = telaBuilder.cria_LL_HOLDER(0.50f);
					llHolderListView.addView(criaListViewPecas(adapterPecasDoCadPecas, listaDeItemPecasDoCadPecas, LISTA_ADICIONAR_ITEM));
					 				   llTela10.addView(llHolderListView);
					 				   
					 				   LinearLayout llHolderAdicionados = telaBuilder.cria_LL_HOLDER(0.50f);
					 				   				llHolderAdicionados.setBackgroundColor(Color.LTGRAY);
					 					   						TextView tvTitulo = telaBuilder.cria_TV_titulo("Peças Adicionadas:");
					 					   								 	tvTitulo.setTextSize(25);
					 					   										tvTitulo.setTextColor(Color.BLUE);
					 								llHolderAdicionados.addView(tvTitulo);	
  llHolderAdicionados.addView(criaListViewPecas(adapterPecasDoMovimento, listaDePecasDoMovimento, LISTA_ALTERAR_REMOVER_ITEM));
	 								   llTela10.addView(llHolderAdicionados);
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
	
	private ListView criaListViewPecas(ArrayAdapterItemPeca arrayAdapterItemPeca, final List<ItemPeca> lista, final String nomeDaLista) {
		
		ListView listView = new ListView(context);
		listView.setAdapter(arrayAdapterItemPeca);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				acaoAposClickNaLista(view, lista, nomeDaLista);				
			}
		});
		return listView;
	}

	private void acaoAposClickNaLista(View view, List<ItemPeca> lista, String nomeDaLista) {
		
		for (ItemPeca itemPeca : lista) {

			if (itemPeca.getCodigo() == view.getTag()) {
		
				if(nomeDaLista.equals(LISTA_ADICIONAR_ITEM)) {
					
					solicitaConfirmacaoAdicionarItem(itemPeca);
				}else {					
					solicitaAlterarOuRemoverItem(itemPeca);
				}
				
				break;
			}
		}
	}

	private void solicitaAlterarOuRemoverItem(final ItemPeca itemPeca) {
	
		ArrayList<String> listaAlterarRemover = new ArrayList<String>();
						  listaAlterarRemover.add("Alterar quantidade");
						  listaAlterarRemover.add("Remover item");
						
		ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.item_menu_geral, listaAlterarRemover);

		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setTitle("Alterar ou Remover Item?");
		builder1.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int posicao) {

				if (posicao == 0) {
			
					alterarItem(itemPeca);
				}
				
				if(posicao == 1) {
					
					removerItem(itemPeca);
				}
				
				dialogInterface.dismiss();
			}
		});
		builder1.show();
	}
	
	private void alterarItem(ItemPeca itemPeca) {
		
		solicitaQuantidade(itemPeca);
	}
	
	private void removerItem(ItemPeca itemPeca) {
		
		itemPeca.setQuantidade("");
		
		listaDePecasDoMovimento.remove(itemPeca);				
		adapterPecasDoMovimento.notifyDataSetChanged();

		listaDePecasDoCadPecas.add(itemPeca);				
		adapterPecasDoCadPecas.notifyDataSetChanged();

		
		insereOUatualiza(dao, listaDePecasDoMovimento);
	}
	
	private void solicitaConfirmacaoAdicionarItem(final ItemPeca itemPeca) {
		
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
	    						
	    						animacao.piscaView(tvErro);
	    					}else {
	    						itemPeca.setQuantidade(quantidade);
			
	    						adicionaOuAlteraItem(itemPeca);
	    				
	    						alertDialog.dismiss();
	    					}
	                    }
	                });
	            }
	        });
	        alertDialog.show();	        
	}
		
	private void adicionaOuAlteraItem(ItemPeca itemPecaInformado) {
			
		boolean achouItem = false;
		
		for(ItemPeca itemPecaDentroDaLista : listaDePecasDoMovimento) {
			
			if(itemPecaDentroDaLista.equals(itemPecaInformado)) {
				
				listaDePecasDoMovimento.remove(itemPecaDentroDaLista);				
				adapterPecasDoMovimento.notifyDataSetChanged();

				listaDePecasDoMovimento.add(new ItemPeca(itemPecaInformado.getQuantidade(), itemPecaInformado.getCodigo(), itemPecaInformado.getNome()));
				adapterPecasDoMovimento.notifyDataSetChanged();
		
				achouItem = true;
				break;
			}
		}
		
		if(!achouItem) {
			
			listaDePecasDoCadPecas.remove(itemPecaInformado);				
			adapterPecasDoCadPecas.notifyDataSetChanged();
			
			listaDePecasDoMovimento.add(new ItemPeca(itemPecaInformado.getQuantidade(), itemPecaInformado.getCodigo(), itemPecaInformado.getNome()));
			adapterPecasDoMovimento.notifyDataSetChanged();
			
		}
		
		insereOUatualiza(dao, listaDePecasDoMovimento);
	}
	
	private void insereOUatualiza(Dao dao, List<ItemPeca> listaDePecasDoMovimento) {

		StringBuilder stringBuilderQtdECodigo = new StringBuilder();
		
		for(ItemPeca itemPeca : listaDePecasDoMovimento) {
			
			stringBuilderQtdECodigo.append(itemPeca.getQuantidade()+"#"+itemPeca.getCodigo()+";");
		}
		
		mov_pecas.setInformacao_1(stringBuilderQtdECodigo.toString());
		
		//new MeuAlerta(""+mov_pecas.getInformacao_1(), null, context).meuAlertaOk();

		dao.insereOUatualiza(mov_pecas,
							 Movimento.COLUMN_INTEGER_NR_LAYOUT, mov_pecas.getNr_layout(), 
							 Movimento.COLUMN_INTEGER_NR_VISITA, mov_pecas.getNr_visita());
	}
	
	//@Override
	//public void onBackPressed() {	
		//setResult(444, new Intent());
		//finish();
	//}
}

