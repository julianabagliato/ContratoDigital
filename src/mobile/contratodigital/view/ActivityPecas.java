package mobile.contratodigital.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.enums.TipoView;
import mobile.contratodigital.model.ItemPeca;
import mobile.contratodigital.util.Alert;
import mobile.contratodigital.util.Alert.AlertType;
import mobile.contratodigital.util.DataPersonalizada;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.OrdenaPorPecas;
import mobile.contratodigital.util.TelaBuilder;
import sharedlib.contratodigital.model.Cad_pecas;
import sharedlib.contratodigital.model.Movimento;

public class ActivityPecas extends Activity {

	private Context context;
	private ArrayAdapterItemPeca baseAdapterPecas;
	private List<ItemPeca> listaDeItemPecas;
	private List<Cad_pecas> listaComCad_pecas;
	private Movimento movimento1;
	private Dao dao;
	private Movimento movimento;
	private ArrayList<String> listaComValores = new ArrayList<String>();
	private ArrayList<String> listaComValores2 = new ArrayList<String>();
	private EditText editText3;
	private ListView listView;
	private EditText editText_pesquiza;
	private String atual ;
	private  String item2 ;
	private int j;
	private int valorado;
	private LinearLayout linearLayout_holderListView;
	private LinearLayout linearLayoutTela ;
	private TelaBuilder telaBuilder;
	private LinearLayout linearLayout_holderEditText;
	private String validando;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = ActivityPecas.this;
		
		Bundle bundle = getIntent().getExtras();
		validando =  bundle.getString("prova");
		if (validando.equals("1")){
			movimento1 = (Movimento) bundle.getSerializable("movimento2");
		}else{
			movimento1 = (Movimento) bundle.getSerializable("movimento");
		}
		
		//final EditText edt = new EditText(context);
		//edt.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		if(validando.equals("1")){
		
			movimento = new Movimento();
			movimento = movimento1;
			movimento.setNr_layout(NomeLayout.PECAS.getNumero());
			movimento.setData_cadastro(new DataPersonalizada().pegaDataAtual_DDMMYYYY_HHMMSS());
		}else{
			movimento = new Movimento();
			movimento.setCod_rep( movimento1.getCod_rep());
			movimento.setNr_visita(movimento1.getNr_visita());
			movimento.setNr_contrato(movimento1.getNr_contrato());
			movimento.setNr_layout(NomeLayout.PECAS.getNumero());
			movimento.setNr_programacao(movimento1.getNr_programacao());
			movimento.setData_cadastro(new DataPersonalizada().pegaDataAtual_DDMMYYYY_HHMMSS());
		}
		

		listaDeItemPecas = new ArrayList<ItemPeca>();
		
		dao = new Dao(context);
		
		listaComCad_pecas = dao.listaTodaTabela(Cad_pecas.class);

		valorado = 0;
		
		for (Cad_pecas cad_pecas : listaComCad_pecas) {
			
			valorado = 0;

			Valida(movimento,1,cad_pecas.getIt_codigo());
			
			if (valorado == 0){
			listaDeItemPecas.add(new ItemPeca(cad_pecas.getDesc_item(), cad_pecas.getIt_codigo()));
			}
			 Collections.sort(listaDeItemPecas, new OrdenaPorPecas());
		baseAdapterPecas = new ArrayAdapterItemPeca(context, 0, listaDeItemPecas);

		setContentView(constroiTelaInicial());
		}
	}
	
	private void Visualizar(String titulo, final ArrayList<String> arrayList_itens, final EditText editText) {

		ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.item_menu_geral, arrayList_itens);

		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setTitle(titulo);
		builder1.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int posicao) {

				for (int i = 0; i < arrayList_itens.size(); i++) {

					if (posicao == i) {

						editText.setText(arrayList_itens.get(i));
					}
				}
				dialogInterface.dismiss();
			}
		});
		builder1.show();
	}

	public ArrayList<String> listaComValores_getLista() {
		try {

			if (movimento.getInformacao_1().trim().equals(null)) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_1());
			}
			if (movimento.getInformacao_2().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_3());
			}
			if (movimento.getInformacao_3().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_3());
			}
			if (movimento.getInformacao_4().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_4());
			}
			if (movimento.getInformacao_5().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_5());
			}
			if (movimento.getInformacao_6().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_6());
			}
			if (movimento.getInformacao_7().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_7());
			}
			if (movimento.getInformacao_8().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_8());
			}
			if (movimento.getInformacao_9().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_9());
			}
			if (movimento.getInformacao_10().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_10());
			}
			if (movimento.getInformacao_11().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_11());
			}
			if (movimento.getInformacao_12().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_12());
			}
			if (movimento.getInformacao_13().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_13());
			}
			if (movimento.getInformacao_14().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_14());
			}
			if (movimento.getInformacao_15().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_15());
			}
			if (movimento.getInformacao_16().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_16());
			}
			if (movimento.getInformacao_17().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_17());
			}
			if (movimento.getInformacao_18().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_18());
			}
			if (movimento.getInformacao_19().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_19());
			}
			if (movimento.getInformacao_20().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_20());
			}
			if (movimento.getInformacao_21().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_21());
			}
			if (movimento.getInformacao_22().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_22());
			}
			if (movimento.getInformacao_23().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_23());
			}
			if (movimento.getInformacao_24().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_24());
			}
			if (movimento.getInformacao_25().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_25());
			}
			if (movimento.getInformacao_26().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_26());
			}
			if (movimento.getInformacao_27().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_27());
			}
			if (movimento.getInformacao_28().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_28());
			}
			if (movimento.getInformacao_29().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_29());
			}
			if (movimento.getInformacao_30().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_30());
			}
			if (movimento.getInformacao_31().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_31());
			}
			if (movimento.getInformacao_32().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_32());
			}
			if (movimento.getInformacao_33().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_33());
			}
			if (movimento.getInformacao_34().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_34());
			}
			if (movimento.getInformacao_35().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_35());
			}
			if (movimento.getInformacao_36().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_36());
			}
			if (movimento.getInformacao_37().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_37());
			}
			if (movimento.getInformacao_38().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_38());
			}
			if (movimento.getInformacao_39().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_39());
			}
			if (movimento.getInformacao_40().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_40());
			}
			if (movimento.getInformacao_41().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_41());
			}
			if (movimento.getInformacao_42().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_42());
			}
			if (movimento.getInformacao_43().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_43());
			}
			if (movimento.getInformacao_44().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_44());
			}
			if (movimento.getInformacao_45().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_45());
			}
			if (movimento.getInformacao_46().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_46());
			}
			if (movimento.getInformacao_47().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_47());
			}
			if (movimento.getInformacao_48().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_48());
			}
			if (movimento.getInformacao_49().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_49());
			}
			if (movimento.getInformacao_50().trim().equals("")) {
				return listaComValores;

			} else {
				populaLista(movimento.getInformacao_50());
			}

			if (!listaComValores.isEmpty()) {
				return listaComValores;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaComValores;

	}

	private ArrayList<String> populaLista(String stringDaListaComItens) {

		String listaComItens = stringDaListaComItens.replace("|", ",");

		String[] arrayDaListaComItens = listaComItens.split(",");

		for (String itemDaLista : arrayDaListaComItens) {
				
				listaComValores.add(itemDaLista.replace(";", "-"));

			}

		return listaComValores;
	}
	private ArrayList<String> populaLista2(String stringDaListaComItens) {

		String listaComItens = stringDaListaComItens.replace("|", ",");

		String[] arrayDaListaComItens = listaComItens.split(",");

		for (String itemDaLista : arrayDaListaComItens) {
				
				for (Cad_pecas cad_pecas : listaComCad_pecas) {
					String novovalor = itemDaLista.substring(0,6);
					String novoitemcod = cad_pecas.getIt_codigo();
					if (novoitemcod.trim().equals(novovalor.trim())){
						
						String Valornovo2 ="("+cad_pecas.getIt_codigo()+itemDaLista.replace(cad_pecas.getIt_codigo()+";",")-(") +")-"+ cad_pecas.getDesc_item();
						listaComValores2.add(Valornovo2);
						

					}
				}

			}
		

		return listaComValores;
	}

	private LinearLayout constroiTelaInicial() {
		telaBuilder = new TelaBuilder(context);

		LayoutParams layoutParams_MATCH_MATCH = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		 linearLayoutTela = telaBuilder.cria_LL(layoutParams_MATCH_MATCH, R.color.plano_de_fundo_layout);

		linearLayoutTela.addView(telaBuilder.cria_TV_titulo("Itens adicionados:"));

		// pendente criar um LL para inserir os itens que serão adicionados
		// através da lista

		// linearLayoutTela.addView(telaBuilder.);

		editText_pesquiza = new EditText(context);
		editText_pesquiza.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		editText_pesquiza.setHint("Informe o nome da peça que procura:");
		editText_pesquiza.addTextChangedListener(new TextWatcher() {
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

		 linearLayout_holderEditText = telaBuilder.cria_LL_HOLDER(0.60f);
		linearLayout_holderEditText.addView(editText_pesquiza);
		linearLayoutTela.addView(linearLayout_holderEditText);

		listView = telaBuilder.cria_LV();
		listView.setAdapter(baseAdapterPecas);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				for (final ItemPeca itemPeca : listaDeItemPecas) {

					if (itemPeca.getCodigo() == view.getTag()) {
	
						final EditText etQuantidade = new EditText(context);
									   etQuantidade.setInputType(InputType.TYPE_CLASS_NUMBER);
							
						Alert.showInputDialog(itemPeca.getCodigo()
								             +"\n\n"+itemPeca.getNome()
								             +"\n\nInforme a quantidade:", 
								             context, etQuantidade, AlertType.INFO, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {

										preencheNoObjetoOcampoInformacao(movimento, 1, itemPeca.getCodigo()+";"+etQuantidade.getText());
										
											Intent intent = new Intent(ActivityPecas.this, ActivityPecas.class);

											Bundle bundle = new Bundle();
											bundle.putSerializable("movimento2", movimento);
											bundle.putString("prova", "1");
											intent.putExtras(bundle);
											
											ActivityPecas.this.finish();
											startActivityForResult(intent, 444);
										

									}
								});
					}
					

				}
				
			}

			
		});

		 linearLayout_holderListView = telaBuilder.cria_LL_HOLDER(0.05f);
		linearLayout_holderListView.addView(listView);
		linearLayoutTela.addView(linearLayout_holderListView);
		linearLayoutTela.hasOnClickListeners();
		

		editText3 = new EditText(context);
		editText3.setTag(R.id.tres, TipoView.CAIXA_OPCAO.getValor());
		editText3.setEnabled(false);

		return linearLayoutTela;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 0, 0, "Visualizar Peças");
		menu.add(0, 1, 0, "Editar item");
		menu.add(0, 2, 0, "Finalizar");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case 0:
			listaComValores = new ArrayList<String>();
			listaComValores2 = new ArrayList<String>();
			listaComValores = listaComValores_getLista2();
			
			if (!listaComValores.isEmpty()) {
				
				Visualizar("Valores", listaComValores2, editText3);

				return true;
			} else {
				new MeuAlerta("Não há itens Cadastrados!", null, context).meuAlertaOk();

				return true;

			}

		case 1:
			listaComValores = new ArrayList<String>();
			listaComValores2 = new ArrayList<String>();
			listaComValores = listaComValores_getLista2();
			if (!listaComValores2.isEmpty()) {
				Editar("Editar", editText3);
				
				return true;
			} else {
				new MeuAlerta("Não há itens Cadastrados!", null, context).meuAlertaOk();

				return true;

			}

		case 2:
			Finalizar();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	private Movimento Valida(Object objeto, int nrOrdem, String conteudo){
		try{
	Class<?> classe = objeto.getClass();

	for (Field atributo : classe.getDeclaredFields()) {

		atributo.setAccessible(true);
		
		if (atributo.getName().contains("informacao_")) {
			int tamanhoTotal = atributo.getName().length();

			String stringCapturada = atributo.getName().substring(11, tamanhoTotal);

			int inteiroCapturado = Integer.parseInt(stringCapturada);
			if (inteiroCapturado == nrOrdem) {
				if (movimento.getInformacao_1().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_2().contains(conteudo)) {
					valorado = 1;

				} else if (movimento.getInformacao_3().contains(conteudo)) {
				valorado = 1;
				} else if (movimento.getInformacao_4().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_5().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_6().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_7().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_8().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_9().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_10().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_11().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_12().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_13().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_14().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_15().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_16().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_17().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_18().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_19().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_20().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_21().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_22().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_23().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_24().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_25().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_26().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_27().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_28().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_29().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_30().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_31().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_32().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_33().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_34().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_35().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_36().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_37().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_38().contains(conteudo)) {
					valorado = 1;

				} else if (movimento.getInformacao_39().contains(conteudo)) {
					valorado = 1;

				} else if (movimento.getInformacao_40().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_41().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_42().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_43().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_44().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_45().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_46().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_47().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_48().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_49().contains(conteudo)) {
					valorado = 1;
				} else if (movimento.getInformacao_50().contains(conteudo)) {
					valorado = 1;
				}
				if (valorado == 1) {


					break;

				}

			}
		}
	}
} catch (Exception e) {
	e.printStackTrace();
}
return (Movimento) objeto;
}
		
			
	private Movimento preencheNoObjetoOcampoInformacao(Object objeto, int nrOrdem, String conteudo) {

		try {

			Class<?> classe = objeto.getClass();

			for (Field atributo : classe.getDeclaredFields()) {

				atributo.setAccessible(true);
				
				if (atributo.getName().contains("informacao_")) {
					int tamanhoTotal = atributo.getName().length();

					String stringCapturada = atributo.getName().substring(11, tamanhoTotal);

					int inteiroCapturado = Integer.parseInt(stringCapturada);
					String valor = "";
					if (inteiroCapturado == 1) {
						valor = movimento.getInformacao_1();
					} else if (inteiroCapturado == 2) {
						valor = movimento.getInformacao_2();

					} else if (inteiroCapturado == 3) {
						valor = movimento.getInformacao_3();

					} else if (inteiroCapturado == 4) {
						valor = movimento.getInformacao_4();

					} else if (inteiroCapturado == 5) {
						valor = movimento.getInformacao_5();

					} else if (inteiroCapturado == 6) {
						valor = movimento.getInformacao_6();

					} else if (inteiroCapturado == 7) {
						valor = movimento.getInformacao_7();

					} else if (inteiroCapturado == 8) {
						valor = movimento.getInformacao_8();

					} else if (inteiroCapturado == 9) {
						valor = movimento.getInformacao_9();

					} else if (inteiroCapturado == 10) {
						valor = movimento.getInformacao_10();

					} else if (inteiroCapturado == 11) {
						valor = movimento.getInformacao_11();

					} else if (inteiroCapturado == 12) {
						valor = movimento.getInformacao_12();

					} else if (inteiroCapturado == 13) {
						valor = movimento.getInformacao_13();

					} else if (inteiroCapturado == 14) {
						valor = movimento.getInformacao_14();

					} else if (inteiroCapturado == 15) {
						valor = movimento.getInformacao_15();

					} else if (inteiroCapturado == 16) {
						valor = movimento.getInformacao_16();

					} else if (inteiroCapturado == 17) {
						valor = movimento.getInformacao_17();

					} else if (inteiroCapturado == 18) {
						valor = movimento.getInformacao_18();

					} else if (inteiroCapturado == 19) {
						valor = movimento.getInformacao_19();

					} else if (inteiroCapturado == 20) {
						valor = movimento.getInformacao_20();

					} else if (inteiroCapturado == 21) {
						valor = movimento.getInformacao_21();

					} else if (inteiroCapturado == 22) {
						valor = movimento.getInformacao_22();

					} else if (inteiroCapturado == 23) {
						valor = movimento.getInformacao_23();

					} else if (inteiroCapturado == 24) {
						valor = movimento.getInformacao_24();

					} else if (inteiroCapturado == 25) {
						valor = movimento.getInformacao_25();

					} else if (inteiroCapturado == 26) {
						valor = movimento.getInformacao_26();

					} else if (inteiroCapturado == 27) {
						valor = movimento.getInformacao_27();

					} else if (inteiroCapturado == 28) {
						valor = movimento.getInformacao_28();

					} else if (inteiroCapturado == 29) {
						valor = movimento.getInformacao_29();

					} else if (inteiroCapturado == 30) {
						valor = movimento.getInformacao_30();

					} else if (inteiroCapturado == 31) {
						valor = movimento.getInformacao_31();

					} else if (inteiroCapturado == 32) {
						valor = movimento.getInformacao_32();

					} else if (inteiroCapturado == 33) {
						valor = movimento.getInformacao_33();

					} else if (inteiroCapturado == 34) {
						valor = movimento.getInformacao_34();

					} else if (inteiroCapturado == 35) {
						valor = movimento.getInformacao_35();

					} else if (inteiroCapturado == 36) {
						valor = movimento.getInformacao_36();

					} else if (inteiroCapturado == 37) {
						valor = movimento.getInformacao_37();

					} else if (inteiroCapturado == 38) {
						valor = movimento.getInformacao_38();

					} else if (inteiroCapturado == 39) {
						valor = movimento.getInformacao_39();

					} else if (inteiroCapturado == 40) {
						valor = movimento.getInformacao_40();

					} else if (inteiroCapturado == 41) {
						valor = movimento.getInformacao_41();

					} else if (inteiroCapturado == 42) {
						valor = movimento.getInformacao_42();

					} else if (inteiroCapturado == 43) {
						valor = movimento.getInformacao_43();

					} else if (inteiroCapturado == 44) {
						valor = movimento.getInformacao_44();

					} else if (inteiroCapturado == 45) {
						valor = movimento.getInformacao_45();

					} else if (inteiroCapturado == 46) {
						valor = movimento.getInformacao_46();

					} else if (inteiroCapturado == 47) {
						valor = movimento.getInformacao_47();

					} else if (inteiroCapturado == 48) {
						valor = movimento.getInformacao_48();

					} else if (inteiroCapturado == 49) {
						valor = movimento.getInformacao_49();

					} else if (inteiroCapturado == 50) {
						valor = movimento.getInformacao_50();

					}
					if (valor != "" && valor != null) {
						conteudo = valor + "|" + conteudo;
					}
					
					if (conteudo.length() > 400) {
						nrOrdem = nrOrdem + 1;
						conteudo = conteudo.replace(valor + "|", "");
						 preencheNoObjetoOcampoInformacao(movimento, nrOrdem, conteudo);
							//insereItemLayout(dao5,NomeLayout.PECAS.getNumero(),nrOrdem, "|");

					}
					if(conteudo.contains("| |")){
						conteudo = conteudo.replace("| |", "");
					}

					if (inteiroCapturado == nrOrdem) {
						if (!conteudo.isEmpty()) {

							atributo.set(objeto, conteudo);
							

							break;

						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return (Movimento) objeto;
	}

	private void Finalizar() {
		//insereItemLayout(dao2,NomeLayout.PECAS.getNumero(),i, "|");

		insereMovimento(dao, movimento);
		
		
		listaComValores = new ArrayList<String>();
		listaComValores = listaComValores_getLista2();
		//int nr_ordem = 2;
		int tamanho = 0;
		while(tamanho<listaComValores.size()){
			for (Cad_pecas cad_pecas : listaComCad_pecas) {
				String novovalor = listaComValores.get(tamanho).substring(0,6);
				String novoitemcod = cad_pecas.getIt_codigo();
				if (novoitemcod.trim().equals(novovalor.trim())){
					//insereItemLayout(dao5, NomeLayout.PECAS.getNumero(), nr_ordem,"("+cad_pecas.getIt_codigo()+listaComValores.get(tamanho).replace(cad_pecas.getIt_codigo()+"-",")-(") +")-"+ cad_pecas.getDesc_item());
				}
			}
			tamanho = tamanho+1;
		}
		new MeuAlerta(  "Ação efetuda", null, context).meuAlertaOk();

		//	Toast.makeText(context, "Ação efetuda", Toast.LENGTH_SHORT).show();
			
			
			setResult(444, new Intent());
		 	finish();
		 	

		
	}

	private void insereMovimento(Dao dao, Movimento mov) {

		dao.insereOUatualiza(mov,
				// Movimento.COLUMN_INTEGER_NR_PROGRAMACAO,
				// mov.getNr_programacao(),
				Movimento.COLUMN_INTEGER_NR_LAYOUT, mov.getNr_layout(), Movimento.COLUMN_INTEGER_NR_VISITA,
				mov.getNr_visita());
	}

	private void Atualizainformacao(String conteudo,String novo	) {

		try {
			
	
			if (movimento.getInformacao_1().contains(conteudo)) {
				movimento.setInformacao_1((movimento.getInformacao_1().replace(conteudo, "")).replace("||", ""));

			} else if (movimento.getInformacao_2().contains(conteudo)) {
				movimento.setInformacao_2((movimento.getInformacao_2().replace(conteudo, "")).replace("||", ""));
				

			} else if (movimento.getInformacao_3().contains(conteudo)) {
				movimento.setInformacao_3((movimento.getInformacao_3().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_4().contains(conteudo)) {
				movimento.setInformacao_4((movimento.getInformacao_4().replace(conteudo, "")).replace("||", ""));
			} else if (movimento.getInformacao_5().contains(conteudo)) {
				movimento.setInformacao_5((movimento.getInformacao_5().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_6().contains(conteudo)) {
				movimento.setInformacao_6((movimento.getInformacao_6().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_7().contains(conteudo)) {
				movimento.setInformacao_7((movimento.getInformacao_7().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_8().contains(conteudo)) {
				movimento.setInformacao_8((movimento.getInformacao_8().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_9().contains(conteudo)) {
				movimento.setInformacao_9((movimento.getInformacao_9().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_10().contains(conteudo)) {
				movimento.setInformacao_10((movimento.getInformacao_10().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_11().contains(conteudo)) {
				movimento.setInformacao_11((movimento.getInformacao_11().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_12().contains(conteudo)) {
				movimento.setInformacao_12((movimento.getInformacao_12().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_13().contains(conteudo)) {
				movimento.setInformacao_13((movimento.getInformacao_13().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_14().contains(conteudo)) {
				movimento.setInformacao_14((movimento.getInformacao_14().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_15().contains(conteudo)) {
				movimento.setInformacao_15((movimento.getInformacao_15().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_16().contains(conteudo)) {
				movimento.setInformacao_16((movimento.getInformacao_16().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_17().contains(conteudo)) {
				movimento.setInformacao_17((movimento.getInformacao_17().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_18().contains(conteudo)) {
				movimento.setInformacao_18((movimento.getInformacao_18().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_19().contains(conteudo)) {
				movimento.setInformacao_19((movimento.getInformacao_19().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_20().contains(conteudo)) {
				movimento.setInformacao_20((movimento.getInformacao_20().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_21().contains(conteudo)) {
				movimento.setInformacao_21((movimento.getInformacao_21().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_22().contains(conteudo)) {
				movimento.setInformacao_22((movimento.getInformacao_22().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_23().contains(conteudo)) {
				movimento.setInformacao_23((movimento.getInformacao_23().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_24().contains(conteudo)) {
				movimento.setInformacao_24((movimento.getInformacao_24().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_25().contains(conteudo)) {
				movimento.setInformacao_25((movimento.getInformacao_25().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_26().contains(conteudo)) {
				movimento.setInformacao_26((movimento.getInformacao_26().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_27().contains(conteudo)) {
				movimento.setInformacao_27((movimento.getInformacao_27().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_28().contains(conteudo)) {
				movimento.setInformacao_28((movimento.getInformacao_28().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_29().contains(conteudo)) {
				movimento.setInformacao_29((movimento.getInformacao_29().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_30().contains(conteudo)) {
				movimento.setInformacao_30((movimento.getInformacao_30().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_31().contains(conteudo)) {
				movimento.setInformacao_31((movimento.getInformacao_31().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_32().contains(conteudo)) {
				movimento.setInformacao_32((movimento.getInformacao_32().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_33().contains(conteudo)) {
				movimento.setInformacao_33((movimento.getInformacao_33().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_34().contains(conteudo)) {
				movimento.setInformacao_34((movimento.getInformacao_34().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_35().contains(conteudo)) {
				movimento.setInformacao_35((movimento.getInformacao_35().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_36().contains(conteudo)) {
				movimento.setInformacao_36((movimento.getInformacao_36().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_37().contains(conteudo)) {
				movimento.setInformacao_37((movimento.getInformacao_37().replace(conteudo, "")).replace("||", ""));
				
			} else if (movimento.getInformacao_38().contains(conteudo)) {
				movimento.setInformacao_38((movimento.getInformacao_38().replace(conteudo, "")).replace("||", ""));
				

			} else if (movimento.getInformacao_39().contains(conteudo)) {
				movimento.setInformacao_39((movimento.getInformacao_39().replace(conteudo, "")).replace("||", ""));
			

			} else if (movimento.getInformacao_40().contains(conteudo)) {
				movimento.setInformacao_40((movimento.getInformacao_40().replace(conteudo, "")).replace("||", ""));
			
			} else if (movimento.getInformacao_41().contains(conteudo)) {
				movimento.setInformacao_41((movimento.getInformacao_41().replace(conteudo, "")).replace("||", ""));
			
			} else if (movimento.getInformacao_42().contains(conteudo)) {
				movimento.setInformacao_42((movimento.getInformacao_42().replace(conteudo, "")).replace("||", ""));
			
			} else if (movimento.getInformacao_43().contains(conteudo)) {
				movimento.setInformacao_43((movimento.getInformacao_43().replace(conteudo, "")).replace("||", ""));
			
			} else if (movimento.getInformacao_44().contains(conteudo)) {
				movimento.setInformacao_44((movimento.getInformacao_44().replace(conteudo, "")).replace("||", ""));
			
			} else if (movimento.getInformacao_45().contains(conteudo)) {
				movimento.setInformacao_45((movimento.getInformacao_45().replace(conteudo, "")).replace("||", ""));
			
			} else if (movimento.getInformacao_46().contains(conteudo)) {
				movimento.setInformacao_46((movimento.getInformacao_46().replace(conteudo, "")).replace("||", ""));
			
			} else if (movimento.getInformacao_47().contains(conteudo)) {
				movimento.setInformacao_47((movimento.getInformacao_47().replace(conteudo, "")).replace("||", ""));
			
			} else if (movimento.getInformacao_48().contains(conteudo)) {
				movimento.setInformacao_48((movimento.getInformacao_48().replace(conteudo, "")).replace("||", ""));
			
			} else if (movimento.getInformacao_49().contains(conteudo)) {
				movimento.setInformacao_49((movimento.getInformacao_49().replace(conteudo, "")).replace("||", ""));
			
			} else if (movimento.getInformacao_50().contains(conteudo)) {
				movimento.setInformacao_50((movimento.getInformacao_50().replace(conteudo, "")).replace("||", ""));
		
			}
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Editar(String titulo, final EditText editText) {

		ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.item_menu_geral, listaComValores2);

		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setTitle(titulo);
		builder1.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int posicao) {
				
				 atual ="";
				for (int i = 0; i <= listaComValores.size(); i++) {
					if (posicao == i) {
				final EditText edite = new EditText(context);
				edite.setInputType(InputType.TYPE_CLASS_NUMBER);
				item2 = String.valueOf(listaComValores.get(i));
				item2 = item2.replace("-", ";");
				item2 = item2.substring(0,6);
				//final int k = 1;
			
			
					 j = posicao;
				Alert.showInputDialog("Digite a quantidade desejada para: " + item2 , context, edite,
						null, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								String t = String.valueOf(edite.getText());
								if(!t.equals("0")){
								
								if (j == 0 ){
									 atual = item2 + ";" + String.valueOf(edite.getText());

								}else {
									 atual = item2 + ";" + String.valueOf(edite.getText());

								}
								
								
								 if (atual != "" && atual != "0" ){
									 
										preencheNoObjetoOcampoInformacao(movimento, 1, atual);

								 }	

								 }else{
									 if(String.valueOf(edite.getText()).equals("0") ){
											Intent intent = new Intent(ActivityPecas.this, ActivityPecas.class);

											Bundle bundle = new Bundle();
											bundle.putSerializable("movimento2", movimento);
											bundle.putString("prova", "1");
											intent.putExtras(bundle);
											
											ActivityPecas.this.finish();
											startActivityForResult(intent, 444);
										 
									 }
								 }
								 
								
							}
				});

				
				}
				if (posicao == i) {
				
				if (posicao == 0) {

				//int t = listaComValores.size();
				//String y = listaComValores.get(i);
							if (listaComValores.size() > 1) {
								Atualizainformacao("|"+listaComValores.get(i).replace("-", ";") , atual );
							
							} else {
								Atualizainformacao(listaComValores.get(i).replace("-", ";"), atual);

							}
						}else {
							if(posicao == 0){
							Atualizainformacao("|" + listaComValores.get(i).replace("-", ";"),atual);
							}else{
							Atualizainformacao(  listaComValores.get(i).replace("-", ";") + "|",atual);

							}
						
						}

						editText.setText(listaComValores.get(i));
						

					}
				}
				
				dialogInterface.dismiss();

			}
		});
	
		builder1.show();
		 

	}

	public ArrayList<String> listaComValores_getLista2() {
		try {
			if (movimento.getInformacao_1().trim() == "") {
				return listaComValores;

			} else {

				populaLista(movimento.getInformacao_1());
				populaLista2(movimento.getInformacao_1());

			}

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return listaComValores;

	}
	public ArrayList<String> listaComValores_getLista3() {
		try {
			if (movimento.getInformacao_1().trim() == "") {
				return listaComValores;

			} else {

				populaLista(movimento.getInformacao_1());
				populaLista2(movimento.getInformacao_1());

			}

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return listaComValores2;

	}
	@Override
	public void onBackPressed() {
	
	insereMovimento(dao, movimento);
	
	listaComValores = new ArrayList<String>();
	listaComValores = listaComValores_getLista2();
	
	new MeuAlerta("Ação efetuda", null, context).meuAlertaOk();
		
	setResult(444, new Intent());
	finish();
	}
}

