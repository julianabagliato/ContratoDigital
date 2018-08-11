package mobile.contratodigital.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.dao.ListaComTodasAtividadesEconomica;
import mobile.contratodigital.dao.ListaComTodasCidadesEstados;
import mobile.contratodigital.dao.ListaComTodosGrupoEmpresa;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.enums.TipoView;
import mobile.contratodigital.model.ContratoUtil;
import mobile.contratodigital.model.Endereco_temp;
import mobile.contratodigital.util.DataPersonalizada;
import mobile.contratodigital.util.GetChildCount;
import mobile.contratodigital.util.MascaraDinheiro;
import mobile.contratodigital.util.Mascaras;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.MeuDatePicker;
import mobile.contratodigital.util.ValidaCNPJ;
import mobile.contratodigital.util.ValidaCPF;
import mobile.contratodigital.ws.BuscaEnderecoWS;
import sharedlib.contratodigital.model.Cad_atividade;
import sharedlib.contratodigital.model.Cad_canal_venda;
import sharedlib.contratodigital.model.Cad_cidades;
import sharedlib.contratodigital.model.Cad_grupo_empres;
import sharedlib.contratodigital.model.Item_layout;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.model.Representante;
import sharedlib.contratodigital.util.Generico;

public class FragConteudoFormulario extends Fragment implements View.OnClickListener {

	private LinearLayout linearLayoutPrincipal;
	private Button button_salvar;
	private Button button_editar;
	private Context context;
	private GetChildCount utilitariosComViewGroup;
	private FragmentManager fragmentManager;
	private Movimento movimento_1;
	private LayoutParams layoutParams_MATCH_MATCH;
	private LayoutParams layoutParams_MATCH_WRAP;
	private LayoutParams layoutParams_WRAP_WRAP;
	private int nr_layout;
	private int nrVisita;
	private int tipoLayout;
	private int tipoVisualizacao;
	private String valore;
	private String Cid;
	private String Est;
	private String End;
	private String Num;
	private String Cep;
	private String Rot;
	private String Bai;
	private Endereco_temp endereco_temp;
	private List<Cad_cidades> listaComTodasAsCidades;
	private List<Cad_cidades> listaComTodosOsEstados;
	private List<Cad_atividade> listaComCad_Atividade;
	private List<Cad_canal_venda> listaComCad_canal_venda;
	private List<Cad_grupo_empres> listaComCad_grupo_empres;
	private ArrayList<String> EstadoLista = new ArrayList<String>();
	private ArrayList<String> CidadeLista = new ArrayList<String>();
	private ArrayList<String> AtividadeLista = new ArrayList<String>();
	private ArrayList<String> cad_canal_venda = new ArrayList<String>();
	private ArrayList<String> cad_grupo_empresLista = new ArrayList<String>();
	private ContratoUtil contratoUtil;

	public FragConteudoFormulario() {
	}
	public FragConteudoFormulario(FragmentManager _fragmentManager, Movimento _movimento, int _nr_layout, int nrVisita, int _tipoLayout, int _tipoVisualizacao) {
		
		this.fragmentManager = _fragmentManager;
		this.movimento_1 = _movimento;
		this.nr_layout = _nr_layout;
		this.nrVisita = nrVisita;
		this.tipoLayout = _tipoLayout;
		this.tipoVisualizacao = _tipoVisualizacao;
		valore = "";
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {

		context = getActivity();
		
		Dao dao = new Dao(context);

		contratoUtil = new ContratoUtil(dao, context);
		
		endereco_temp = new Endereco_temp();

		Representante representante = (Representante) dao.devolveObjeto(Representante.class);

		Movimento movimentoEnderecoPadrao = (Movimento) dao.devolveObjeto(Movimento.class, 
																		  Movimento.COLUMN_INTEGER_NR_PROGRAMACAO, movimento_1.getNr_programacao(), 
																		  Movimento.COLUMN_INTEGER_NR_VISITA, movimento_1.getNr_visita(),										  
																		  Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.ENDERECO_PADRAO.getNumero(), 
																		  Movimento.COLUMN_INTEGER_COD_REP, representante.getCod_rep());
		
		Movimento movimentoEnderecoEntrega = (Movimento) dao.devolveObjeto(Movimento.class, 
																		   Movimento.COLUMN_INTEGER_NR_PROGRAMACAO, movimento_1.getNr_programacao(), 
																		   Movimento.COLUMN_INTEGER_NR_VISITA, movimento_1.getNr_visita(),
																		   Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.ENDERECO_ENTREGA.getNumero(), 
																		   Movimento.COLUMN_INTEGER_COD_REP, representante.getCod_rep());

		if (movimentoEnderecoPadrao != null) {

			if (movimentoEnderecoPadrao.getNr_layout() == NomeLayout.ENDERECO_PADRAO.getNumero()) {

				endereco_temp.setEnd(movimentoEnderecoPadrao.getInformacao_2());
				endereco_temp.setNum(movimentoEnderecoPadrao.getInformacao_3());
				endereco_temp.setBai(movimentoEnderecoPadrao.getInformacao_4());
				endereco_temp.setEst(movimentoEnderecoPadrao.getInformacao_5());
				endereco_temp.setCid(movimentoEnderecoPadrao.getInformacao_6());
				endereco_temp.setCep(movimentoEnderecoPadrao.getInformacao_1());
				endereco_temp.setRot(movimentoEnderecoPadrao.getInformacao_7());
			}

			if (movimentoEnderecoEntrega != null) {
				
				endereco_temp.setEnd(movimentoEnderecoEntrega.getInformacao_2());
				endereco_temp.setNum(movimentoEnderecoEntrega.getInformacao_3());
				endereco_temp.setBai(movimentoEnderecoEntrega.getInformacao_4());
				endereco_temp.setEst(movimentoEnderecoEntrega.getInformacao_5());
				endereco_temp.setCid(movimentoEnderecoEntrega.getInformacao_6());
				endereco_temp.setCep(movimentoEnderecoEntrega.getInformacao_1());
				endereco_temp.setRot(movimentoEnderecoEntrega.getInformacao_7());

			}

		}

		layoutParams_MATCH_MATCH = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParams_WRAP_WRAP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams_MATCH_WRAP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		linearLayoutPrincipal = new LinearLayout(context);
		linearLayoutPrincipal.setOrientation(LinearLayout.VERTICAL);
		linearLayoutPrincipal.setLayoutParams(layoutParams_MATCH_MATCH);

		if (tipoLayout == TipoView.LAYOUT_CONSULTA.getValor()) {

			linearLayoutPrincipal.setBackgroundColor(getResources().getColor(R.color.branco));
		} else {

			int totalDeFragmentsAtivos = fragmentManager.getFragments().size();
			// se for par:
			if (totalDeFragmentsAtivos % 2 == 0) {

				linearLayoutPrincipal.setBackgroundColor(getResources().getColor(R.color.branco));
			} else {
				linearLayoutPrincipal.setBackgroundColor(getResources().getColor(R.color.branco));
			}
		}

		utilitariosComViewGroup = new GetChildCount(linearLayoutPrincipal);

		criaWidgetsBaseadoEmCamposDaTabelaItem();

		button_salvar = new Button(context);
		button_salvar.setTag("button_salvar");
		button_salvar.setBackground(getResources().getDrawable(R.drawable.floppy));
		button_salvar.setLayoutParams(layoutParams_WRAP_WRAP);
		button_salvar.setOnClickListener(this);

		button_editar = new Button(context);
		button_editar.setTag("button_editar");
		button_editar.setBackground(getResources().getDrawable(R.drawable.edit));
		button_editar.setLayoutParams(layoutParams_WRAP_WRAP);
		button_editar.setOnClickListener(this);
		button_editar.setVisibility(View.GONE);

		
		linearLayoutPrincipal.addView(button_salvar);
		linearLayoutPrincipal.addView(button_editar);

		if (tipoVisualizacao == TipoView.VISUALIZACAO_TABELA.getValor()) {

			criaEpopulaTabela();
		} else {
			populaWidgetsCasoExistaInformacoesSalvasNaTabelaMovimento();
		}

		
		verificaStatusCheckBox();
		
		
		return linearLayoutPrincipal;
	}

	private void verificaStatusCheckBox() {
		
		CheckBox checkBox_ordem_2_layout_21 = (CheckBox)linearLayoutPrincipal.findViewWithTag("checkBox_ordem_2_layout_21");
		CheckBox checkBox_ordem_13_layout_26 = (CheckBox)linearLayoutPrincipal.findViewWithTag("checkBox_ordem_13_layout_26");
		CheckBox checkBox_ordem_16_layout_26 = (CheckBox)linearLayoutPrincipal.findViewWithTag("checkBox_ordem_16_layout_26");
		
		if(checkBox_ordem_2_layout_21 != null) {
			
			LinearLayout linearLayout_ordem_3_layout_21 = (LinearLayout) linearLayoutPrincipal.findViewWithTag("linearLayout_ordem_3_layout_21");
			
			if(linearLayout_ordem_3_layout_21 != null) {
				
				if(checkBox_ordem_2_layout_21.isChecked()) {
			
					linearLayout_ordem_3_layout_21.setVisibility(View.VISIBLE);
				}else {					
					linearLayout_ordem_3_layout_21.setVisibility(View.GONE);
					
					//EditText editText_ordem_3_layout_21 = (EditText) linearLayoutPrincipal.findViewWithTag("editText_ordem_3_layout_21");
					
					//if(editText_ordem_3_layout_21 != null) {
						
						//editText_ordem_3_layout_21.setText("");
					//}			
				}			
			}
		}
	
		if(checkBox_ordem_13_layout_26 != null) {
			
			LinearLayout linearLayout_ordem_14_layout_26 = (LinearLayout) linearLayoutPrincipal.findViewWithTag("linearLayout_ordem_14_layout_26");
			LinearLayout linearLayout_ordem_15_layout_26 = (LinearLayout) linearLayoutPrincipal.findViewWithTag("linearLayout_ordem_15_layout_26");
			
			if(linearLayout_ordem_14_layout_26 != null && linearLayout_ordem_15_layout_26 != null ) {
				
				if(checkBox_ordem_13_layout_26.isChecked()) {
					
					linearLayout_ordem_14_layout_26.setVisibility(View.VISIBLE);
					linearLayout_ordem_15_layout_26.setVisibility(View.VISIBLE);
				}else {					
					linearLayout_ordem_14_layout_26.setVisibility(View.GONE);
					linearLayout_ordem_15_layout_26.setVisibility(View.GONE);
					
					//EditText editText_ordem_14_layout_26 = (EditText)linearLayoutPrincipal.findViewWithTag("editText_ordem_14_layout_26");
					//EditText editText_ordem_15_layout_26 = (EditText)linearLayoutPrincipal.findViewWithTag("editText_ordem_15_layout_26");
					
					//if(editText_ordem_14_layout_26 != null && editText_ordem_15_layout_26 != null) {
						
						//editText_ordem_14_layout_26.setText("");
						//editText_ordem_15_layout_26.setText("");
					//}
				}				
			}	
		}
	
		if(checkBox_ordem_16_layout_26 != null) {
			
			LinearLayout linearLayout_ordem_17_layout_26 = (LinearLayout) linearLayoutPrincipal.findViewWithTag("linearLayout_ordem_17_layout_26");
			
			if(linearLayout_ordem_17_layout_26 != null) {
				
				if(checkBox_ordem_16_layout_26.isChecked()) {
					
					linearLayout_ordem_17_layout_26.setVisibility(View.VISIBLE);
				}else {					
					linearLayout_ordem_17_layout_26.setVisibility(View.GONE);
					
					//EditText editText_ordem_17_layout_26 = (EditText) linearLayoutPrincipal.findViewWithTag("editText_ordem_17_layout_26");
					
					//if(editText_ordem_17_layout_26 != null) {
						
						//editText_ordem_17_layout_26.setText("");
					//}
				}			
			}
		}
	}

	private void criaEpopulaTabela() {

		Dao dao = new Dao(context);

		HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
		horizontalScrollView.setLayoutParams(layoutParams_MATCH_WRAP);

		LinearLayout linearLayout_H_Tabela = criaLinearLayout_H_Tabela();

		for (Item_layout item : dao.listaTodaTabela(Item_layout.class,	
													Item_layout.COLUMN_INTEGER_NR_LAYOUT, nr_layout)) {

			LinearLayout linearLayout_V_Coluna = criaLinearLayout_V_Coluna(item.getNr_ordem());
			linearLayout_V_Coluna.addView(criaTextViewTituloDaColuna(item));
			linearLayout_H_Tabela.addView(linearLayout_V_Coluna);
		}
		horizontalScrollView.addView(linearLayout_H_Tabela);
		linearLayoutPrincipal.addView(horizontalScrollView);

		for (Movimento movimento : dao.listaTodaTabela(Movimento.class, 
													   Movimento.COLUMN_INTEGER_NR_PROGRAMACAO, movimento_1.getNr_programacao(), 
													   Movimento.COLUMN_INTEGER_NR_LAYOUT, nr_layout,
													   Movimento.COLUMN_INTEGER_NR_VISITA, movimento_1.getNr_visita())) {
													
			procuraTabela_e_Colunas(movimento);
			
		}
	}

	private void procuraTabela_e_Colunas(Movimento movimentoRecebido) {

		for (int i = 0; i < linearLayoutPrincipal.getChildCount(); i++) {

			View view_A = linearLayoutPrincipal.getChildAt(i);

			if (view_A instanceof HorizontalScrollView) {

				HorizontalScrollView horizontalScrollView = (HorizontalScrollView) view_A;

				for (int x = 0; x < horizontalScrollView.getChildCount(); x++) {

					View view_B = horizontalScrollView.getChildAt(x);

					if (view_B instanceof LinearLayout) {

						LinearLayout linearLayoutTabela = (LinearLayout) view_B;

						for (int y = 0; y < linearLayoutTabela.getChildCount(); y++) {

							View view_C = linearLayoutTabela.getChildAt(y);

							if (view_C instanceof LinearLayout) {

								LinearLayout linearLayoutColuna = (LinearLayout) view_C;

								populaColuna(movimentoRecebido, linearLayoutColuna);
							}
						}
					}
				}
			}
		}
	}

	private void populaColuna(Movimento movimento, LinearLayout linearLayoutColuna) {

		try {

			Class<?> classe = movimento.getClass();

			for (Field atributo : classe.getDeclaredFields()) {

				atributo.setAccessible(true);

				if (atributo.getName().contains("informacao_")) {

					int tamanhoTotal = atributo.getName().length();

					String stringCapturada = atributo.getName().substring(11, tamanhoTotal);

					int inteiroCapturado = Integer.parseInt(stringCapturada);

					if (inteiroCapturado == linearLayoutColuna.getId()) {

						TextView textView = criaTextViewConteudoTabela(movimento);
						textView.setText(String.valueOf(atributo.get(movimento)));

						linearLayoutColuna.addView(textView);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TextView criaTextViewConteudoTabela(final Movimento movimentoConteudo) {

		int left = 10, top = 0, right = 10, bottom = 0;

		TextView textView_conteudo = new TextView(context);
		textView_conteudo.setTextSize(20);

		LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		llparams.setMargins(left, top, right, bottom);
		textView_conteudo.setLayoutParams(llparams);

		return textView_conteudo;
	}

	private TextView criaTextViewTituloDaColuna(Item_layout item) {

		int left = 10, top = 0, right = 10, bottom = 0;

		TextView textView = new TextView(context);
		textView.setId(item.getNr_ordem());
		textView.setText(item.getDescricao());
		textView.setTextColor(getResources().getColor(R.color.azul_consigaz));
		textView.setTextSize(20);
		LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		llparams.setMargins(left, top, right, bottom);
		textView.setLayoutParams(llparams);

		return textView;
	}

	private LinearLayout criaLinearLayout_H_Tabela() {

		LinearLayout linearLayout_HORIZONTAL = new LinearLayout(context);
		linearLayout_HORIZONTAL.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout_HORIZONTAL.setLayoutParams(layoutParams_MATCH_WRAP);

		return linearLayout_HORIZONTAL;
	}

	private LinearLayout criaLinearLayout_V_Coluna(int nrOrdem) {

		LinearLayout linearLayout_V_Coluna = new LinearLayout(context);
		linearLayout_V_Coluna.setId(nrOrdem);
		linearLayout_V_Coluna.setTag("coluna");
		linearLayout_V_Coluna.setOrientation(LinearLayout.VERTICAL);
		linearLayout_V_Coluna.setLayoutParams(layoutParams_WRAP_WRAP);

		return linearLayout_V_Coluna;
	}

	private void populaWidgetsCasoExistaInformacoesSalvasNaTabelaMovimento() {

		Dao dao = new Dao(context);
	
		if (nrVisita > 0) {
	
			Movimento movimento = (Movimento) dao.devolveObjeto(Movimento.class, 
															    //Movimento.COLUMN_INTEGER_NR_PROGRAMACAO, movimento_1.getNr_programacao(), 
															    Movimento.COLUMN_INTEGER_NR_VISITA, movimento_1.getNr_visita(),
															    Movimento.COLUMN_INTEGER_NR_LAYOUT, nr_layout);

			if (movimento != null) {
				
				procuraWidGetsEpreencheOcampoInformacao(linearLayoutPrincipal, movimento);

				if (utilitariosComViewGroup.faltaPreencherEditTextObrigatorios()) {
					
					habilitaViews();
				}else {					
					desabilitaViews();	
				}
	
				//##############################
				if (tipoLayout == TipoView.LAYOUT_FORMULARIO.getValor()) {
					
					trataEnderecoPadraoTemporario(nr_layout);	
				}
				
				if (tipoLayout == TipoView.LAYOUT_CONSULTA.getValor()) {

					button_salvar.setVisibility(View.GONE);
					button_editar.setVisibility(View.GONE);
				}
				//##############################
				
				Endereco_temp endereco_temp = new Endereco_temp();
				endereco_temp.setEnd("");
				endereco_temp.setNum("");
				endereco_temp.setBai("");
				endereco_temp.setEst("");
				endereco_temp.setCid("");
				endereco_temp.setCep("");
				endereco_temp.setRot("");
				
				if (movimento.getNr_layout() == NomeLayout.ENDERECO_PADRAO.getNumero()) {

					endereco_temp.setEnd(movimento.getInformacao_1());
					endereco_temp.setNum(movimento.getInformacao_2());
					endereco_temp.setBai(movimento.getInformacao_3());
					endereco_temp.setEst(movimento.getInformacao_4());
					endereco_temp.setCid(movimento.getInformacao_5());
					endereco_temp.setCep(movimento.getInformacao_6());
					endereco_temp.setRot(movimento.getInformacao_7());
				}
				
				
				
			}
		}
	}
	
	public ArrayList<String> cad_grupo_empres_getLista() {
		
		cad_grupo_empresLista.clear();
		
		listaComCad_grupo_empres = ListaComTodosGrupoEmpresa.devolveLista();
		
		for (Cad_grupo_empres cad_grupo_empres : listaComCad_grupo_empres) {
			
			cad_grupo_empresLista.add(cad_grupo_empres.getNome_grupo()+";"+cad_grupo_empres.getCod_grupo());
		}
		
		Collections.sort(cad_grupo_empresLista);
		
		return cad_grupo_empresLista;
	}

	public ArrayList<String> cad_canal_venda_getLista() {
		
		cad_canal_venda.clear();
		
		Dao dao = new Dao(context);
		
		listaComCad_canal_venda = dao.listaTodaTabela(Cad_canal_venda.class);
		
		for (Cad_canal_venda canal_venda : listaComCad_canal_venda) {
			
			cad_canal_venda.add(canal_venda.getDescricao() + ";" + canal_venda.getCod_canal_venda());
		}
		
		Collections.sort(cad_canal_venda);
		
		return cad_canal_venda;
	}
	
	public ArrayList<String> Atividade_getLista() {
		
		AtividadeLista.clear();
			
		Dao dao = new Dao(context);
		
		listaComCad_Atividade = dao.listaTodaTabela(Cad_atividade.class);
		//listaComCad_Atividade = ListaComTodasAtividadesEconomica.devolveLista();

		for (Cad_atividade cad_atividade : listaComCad_Atividade) {
			
			AtividadeLista.add(cad_atividade.getDescricao()+";"+cad_atividade.getAtividade());
		}
		
		Collections.sort(AtividadeLista);
		
		return AtividadeLista;
	}

	private ArrayList<String> Estado_getLista() {
		
		EstadoLista.clear();
		
		listaComTodosOsEstados = ListaComTodasCidadesEstados.devolveLista();
		
		for (Cad_cidades cad_cidades : listaComTodosOsEstados) {
			
			if (EstadoLista.contains(cad_cidades.getEstado())) {
				
			} 
			else {
				EstadoLista.add(cad_cidades.getEstado());				
			}
		}
		
		Collections.sort(EstadoLista);
		
		return EstadoLista;
	}
	
	private ArrayList<String> Cidade_getLista() {

		CidadeLista.clear();
		
		listaComTodasAsCidades = ListaComTodasCidadesEstados.devolveLista();
				
		for (Cad_cidades cad_cidades : listaComTodasAsCidades) {

			CidadeLista.add(cad_cidades.getCidade());
		}

		//Collections.sort(CidadeLista);
		
		return CidadeLista;
	}

	private void criaWidgetsBaseadoEmCamposDaTabelaItem() {

		Dao dao = new Dao(context);

		for (final Item_layout item : dao.listaTodaTabela(Item_layout.class,	
														  Item_layout.COLUMN_INTEGER_NR_LAYOUT, nr_layout)) {

			if (tipoLayout == TipoView.LAYOUT_CONSULTA.getValor()) {

				if (tipoVisualizacao == TipoView.VISUALIZACAO_NORMAL.getValor()) {

					Button botao = criaBotao();
					
					LinearLayout linearLayoutlinha_0_HORIZONTAL = criaLinearLayout_HORIZONTAL();
					TextView textViewTitulo = criaTextViewTituloLayoutConsulta(item);
					TextView textViewConteudo = criaTextViewConteudo(item);
					
					adicionaViewEmLayoutLocalEprincipal(linearLayoutlinha_0_HORIZONTAL, textViewTitulo, null, textViewConteudo, botao);
				}
				
			} else {

				if (item.getInd_tip_visualiz() == TipoView.CAMPO_TEXTO.getValor()) {

					LinearLayout linearLayoutlinha_HORIZONTAL = criaLinearLayout_HORIZONTAL();
					linearLayoutlinha_HORIZONTAL.setTag("linearLayout_ordem_"+item.getNr_ordem()+"_layout_"+item.getNr_layout());
					
					
					Button botao = criaBotao();
					
					final EditText editText_data = criaEditText(item, botao);
					
					TextView textView = criaTextViewTitulo(item);
					
					if (item.getDescricao().contains("E-mail")) {
						textView.setFilters(new InputFilter[] { new InputFilter.AllCaps() {
							@Override
							public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
									int dstart, int dend) {
								return String.valueOf(source).toLowerCase();
							}
						} });
						
					} else {
						editText_data.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
					}
					
					if (item.getInd_tip_dado() == TipoView.TIPO_DADO_DATA.getValor()) {

						editText_data.setEnabled(false);

						textView.setText(Html.fromHtml("<b><u>" + item.getDescricao() + "</u></b>"));
						textView.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {

								MeuDatePicker meuDatePicker = new MeuDatePicker();
								meuDatePicker.criaEmostraDataPicker(context, editText_data, item.getDescricao());
							}
						});
					}
					adicionaViewEmLayoutLocalEprincipal(linearLayoutlinha_HORIZONTAL, textView, editText_data, null, botao);
				}

				if (item.getInd_tip_visualiz() == TipoView.CAIXA_TEXTO_EDICAO.getValor()) {

					LinearLayout linearLayoutlinha_2_VERTICAL = criaLinearLayout_VERTICAL();
					linearLayoutlinha_2_VERTICAL.setTag("linearLayout_ordem_"+item.getNr_ordem()+"_layout_"+item.getNr_layout());
					
					TextView textView2 = criaTextViewTitulo(item);

					Button botao = criaBotao();
					
					EditText editText2 = criaEditText(item, botao);
					editText2.setFilters(new InputFilter[] { new InputFilter.AllCaps() });

					adicionaViewEmLayoutLocalEprincipal(linearLayoutlinha_2_VERTICAL, textView2, editText2, null, botao);
				}

				if (item.getInd_tip_visualiz() == TipoView.CAIXA_OPCAO.getValor()) {

					LinearLayout linearLayoutlinha_3_HORIZONTAL = criaLinearLayout_HORIZONTAL();
					linearLayoutlinha_3_HORIZONTAL.setTag("linearLayout_ordem_"+item.getNr_ordem()+"_layout_"+item.getNr_layout());
					
					Button botao = criaBotao();
					
					final EditText editText3 = criaEditText(item, botao);
					editText3.setTag(R.id.tres, TipoView.CAIXA_OPCAO.getValor());
					editText3.setEnabled(false);

					TextView textView3 = criaTextViewTitulo(item);
					
					if (item.getObrigatorio() == 1) {
						
						textView3.setTextColor(getResources().getColor(R.color.vermelho));
						textView3.setText(Html.fromHtml("<b><u>" + item.getDescricao() + "</u>" + ":" + "</b>"));

					} else {
						textView3.setText(Html.fromHtml("<b><u>" + item.getDescricao() + ": " + "</u></b>"));
					}
					
					textView3.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

							if (item.getDescricao().equals("Cidade")) {
								
								escolheApenasUmItemDaListaCidade(item.getDescricao(), Cidade_getLista(), editText3);
							} else if (item.getDescricao().equals("Estado")) {
								
								valore = "";
								escolheApenasUmItemDaLista(item.getDescricao(), Estado_getLista(), editText3);
								
							} else if (item.getDescricao().equals("Atividade economica")) {
								
								escolheApenasUmItemDaLista(item.getDescricao(), Atividade_getLista(), editText3);
								
							} else if (item.getDescricao().equals("Qual Grupo")) {
								
								escolheApenasUmItemDaLista(item.getDescricao(), cad_grupo_empres_getLista(), editText3);
								
							} else if (item.getDescricao().equals("Canal de venda")) {
								
								escolheApenasUmItemDaLista(item.getDescricao(), cad_canal_venda_getLista(), editText3);
							} else {
								escolheApenasUmItemDaLista(item.getDescricao(), populaLista(item.getLista_opcao()), editText3);
							}

						}
					});

					adicionaViewEmLayoutLocalEprincipal(linearLayoutlinha_3_HORIZONTAL, textView3, editText3, null, botao);
				}


				if(item.getInd_tip_visualiz() == TipoView.CAIXA_VERIFICACAO.getValor()){

					CheckBox checkBox = new CheckBox(context);
					checkBox.setId(item.getNr_ordem());
					checkBox.setTag("checkBox_ordem_"+item.getNr_ordem()+"_layout_"+item.getNr_layout());
					checkBox.setText(item.getDescricao());
					checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					       @Override
					       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

					    	   verificaStatusCheckBox();
					       }
					   }
					);
					
					
					linearLayoutPrincipal.addView(checkBox);
				}
	
			}
		}
		
	}
	
	private Button criaBotao() {
		
		Button botao = new Button(context);
		//bb.setText("teste");
		
		botao.setVisibility(View.GONE);
		
		return botao;
	}
	
	private void trataEnderecoPadraoTemporario(int nr_layout) {
		
		if (nr_layout == NomeLayout.ENDERECO_PADRAO.getNumero()) {
			
			Endereco_temp endereco_temp = new Endereco_temp();
			
			for (int i = 0; i < linearLayoutPrincipal.getChildCount(); i++) {

				Object child = linearLayoutPrincipal.getChildAt(i);

				if (child instanceof LinearLayout) {

					LinearLayout linearLayoutChild = (LinearLayout) child;

					for (int x = 0; x < linearLayoutChild.getChildCount(); x++) {

						View view = linearLayoutChild.getChildAt(x);

						if (view instanceof EditText) {

							EditText editTextChild = (EditText) view;
							
							if (editTextChild.getTag().equals("ceplogradouro")) {
								
								End = String.valueOf(editTextChild.getText());
								endereco_temp.setEnd(End);
								
							} else if (editTextChild.getTag().equals("cepbairro")) {
								
								Bai = String.valueOf(editTextChild.getText());
								endereco_temp.setBai(Bai);
								
							} else if (editTextChild.getTag().equals("cepcidade")) {
								
								Cid = String.valueOf(editTextChild.getText());
								endereco_temp.setCid(Cid);
								
							} else if (editTextChild.getTag().equals("cepestado")) {
								
								Est = String.valueOf(editTextChild.getText());
								endereco_temp.setEst(Est);
								
							} else if (editTextChild.getTag().equals("numero")) {
								
								Num = String.valueOf(editTextChild.getText());
								endereco_temp.setNum(Num);
								
							} else if (editTextChild.getTag().equals("rota")) {
								
								Rot = String.valueOf(editTextChild.getText());
								endereco_temp.setRot(Rot);
								
							} else if (editTextChild.getTag().equals("cep")) {
								
								Cep = String.valueOf(editTextChild.getText());
								endereco_temp.setCep(Cep);
							}

						}
					}
				}
			}
		}	
	}

	private Movimento procuraWidGetsEpreencheOcampoInformacao(LinearLayout linearLayoutPrincipal, Movimento movimentoRecebido) {

		Movimento movimentoPreenchido = null;

		for (int i = 0; i < linearLayoutPrincipal.getChildCount(); i++) {

			Object child = linearLayoutPrincipal.getChildAt(i);

			if (child instanceof LinearLayout) {

				LinearLayout linearLayoutChild = (LinearLayout) child;

				for (int x = 0; x < linearLayoutChild.getChildCount(); x++) {

					View view = linearLayoutChild.getChildAt(x);

					if (view instanceof EditText) {

						EditText editTextChild = (EditText) view;

						movimentoPreenchido = preencheNoObjetoOcampoInformacao(movimentoRecebido, editTextChild, null,
								null);
					}

					// adicionado essa regra apenas para os casos onde o layout
					// é "LAYOUT_CONSULTA"
					if (view instanceof TextView) {

						TextView textViewConteudo = (TextView) view;

						if (textViewConteudo.getTag() == "textViewConteudo") {

							movimentoPreenchido = preencheNoObjetoOcampoInformacao(movimentoRecebido, null, null, textViewConteudo);
						}
					}
					// adicionado essa regra apenas para os casos onde o layout
					// é "LAYOUT_CONSULTA"
				}
			}

			if (child instanceof CheckBox) {

				CheckBox checkBoxChild = (CheckBox) child;

				movimentoPreenchido = preencheNoObjetoOcampoInformacao(movimentoRecebido, null, checkBoxChild, null);
			}
		}
		return movimentoPreenchido;
	}

	private Movimento preencheNoObjetoOcampoInformacao(Object objeto, EditText editText, CheckBox checkBox, TextView textViewConteudo) {

		int nrOrdem = 0;
		String conteudo = "";

		if (editText != null) {

			nrOrdem = editText.getId();
			conteudo = editText.getText().toString();
		}

		if (checkBox != null) {

			nrOrdem = checkBox.getId();
			conteudo = String.valueOf(checkBox.isChecked());
		}

		// incluido essa regra apenas para os casos onde o layout eh
		// Visualizacao
		if (textViewConteudo != null) {

			nrOrdem = textViewConteudo.getId();
			// conteudo = textViewConteudo.getText().toString();
		}
		// incluido essa regra apenas para os casos onde o layout eh
		// Visualizacao

		try {

			Class<?> classe = objeto.getClass();

			for (Field atributo : classe.getDeclaredFields()) {

				atributo.setAccessible(true);

				if (atributo.getName().contains("informacao_")) {

					int tamanhoTotal = atributo.getName().length();

					String stringCapturada = atributo.getName().substring(11, tamanhoTotal);

					int inteiroCapturado = Integer.parseInt(stringCapturada);

					if (inteiroCapturado == nrOrdem) {

						if (checkBox != null) {

							if (Boolean.parseBoolean(String.valueOf(atributo.get(objeto))) == true) {

								checkBox.setChecked(true);
							}
						}

						if (!conteudo.isEmpty()) {

							atributo.set(objeto, conteudo);
						}

						if (editText != null) {

							if (String.valueOf(atributo.get(objeto)) != "null") {

								editText.setText(String.valueOf(atributo.get(objeto)));
							}
						}

						// incluido essa regra apenas para os casos onde o
						// layout eh Visualizacao
						if (textViewConteudo != null) {

							// if(String.valueOf(atributo.get(objeto)) !=
							// "null"){

							textViewConteudo.setText(String.valueOf(atributo.get(objeto)));
							// }
						}
						// incluido essa regra apenas para os casos onde o
						// layout eh Visualizacao

						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Movimento) objeto;
	}

	private ArrayList<String> populaLista(String stringDaListaComItens) {

		ArrayList<String> arrayList_itens = new ArrayList<String>();

		String listaComItens = stringDaListaComItens;

		String[] arrayDaListaComItens = listaComItens.split(",");

		for (String itemDaLista : arrayDaListaComItens) {

			arrayList_itens.add(itemDaLista);
		}
		return arrayList_itens;
	}

	private void escolheApenasUmItemDaLista(final String titulo, final ArrayList<String> arrayList_itens, final EditText editText) {

		ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.item_menu_geral, arrayList_itens);
		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setTitle(titulo);
		builder1.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int posicao) {

				for (int i = 0; i < arrayList_itens.size(); i++) {

					if (posicao == i) {
						
						String valornovo = arrayList_itens.get(i);
						
						if (valornovo.equals("OUTRO")) {
							
							editText.setEnabled(true);
							editText.setHint("Qual Outro?");

						} else {
							if (titulo.equals("Estado")) {
								
								valore = arrayList_itens.get(i);
							}
							editText.setText(arrayList_itens.get(i));

						}
					}
				}
				dialogInterface.dismiss();
			}
		});
		builder1.show();
	}

	private void escolheApenasUmItemDaListaCidade(final String titulo, final ArrayList<String> arrayList_itens,
			final EditText editText) {
		final AlertDialog.Builder builder2 = new AlertDialog.Builder(context);

		final EditText editText_pesquiza = new EditText(context);

		editText_pesquiza.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		editText_pesquiza.setHint("Informe o nome da cidade");
		editText_pesquiza.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if (count < before) {
					// We're deleting char so we need to reset the adapter data
					CidadeLista.clear();

				}
				if (editText_pesquiza.getText().length() >= 3) {
					pesquisacidade(String.valueOf(editText_pesquiza.getText()).toUpperCase(), CidadeLista);

					escolheApenasUmItemDaLista(titulo, arrayList_itens, editText);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.item_menu_geral, arrayList_itens);

		builder2.setTitle(titulo);
		builder2.setView(editText_pesquiza);
		builder2.show();
	}

	private ArrayList<String> pesquisacidade(String valor, ArrayList<String> arrayList_itens) {

		CidadeLista.clear();
		
		listaComTodasAsCidades = ListaComTodasCidadesEstados.devolveLista();

		for (Cad_cidades cad_cidades : listaComTodasAsCidades) {
			
			if (cad_cidades.getCidade().contains(valor)) {
				
				if (!valore.trim().equals("")) {
					
					if (cad_cidades.getEstado().equals(valore)) {
						
						CidadeLista.add(cad_cidades.getCidade());

					}
				} 
				else {
					CidadeLista.add(cad_cidades.getCidade());
				}
			}
		}
		
		// Collections.sort(CidadeLista);
		
		return CidadeLista;

	}

	private ArrayList<String> pesquisaCanal(String valor, ArrayList<String> arrayList_itens) {

		cad_canal_venda.clear();
		
		Dao dao = new Dao(context);
		
		listaComCad_canal_venda = dao.listaTodaTabela(Cad_canal_venda.class);
		
		for (Cad_canal_venda cad_canal_vendas : listaComCad_canal_venda) {
			
			String Compare = cad_canal_vendas.getDescricao() + ";" + cad_canal_vendas.getCod_canal_venda();
			
			if (Compare.contains(valor)) {
				
				cad_canal_venda.add(cad_canal_vendas.getDescricao() + ";" + cad_canal_vendas.getCod_canal_venda());
			}
		}
		
		Collections.sort(cad_canal_venda);
		
		return cad_canal_venda;
	}

	private ArrayList<String> pesquisAtividade(String valor, ArrayList<String> arrayList_itens) {

		AtividadeLista.clear();
		
		Dao dao = new Dao(context);
		
		listaComCad_Atividade = dao.listaTodaTabela(Cad_atividade.class);
		//listaComCad_Atividade = ListaComTodasAtividadesEconomica.devolveLista();

		for (Cad_atividade cad_atividade : listaComCad_Atividade) {
			
			String compare = cad_atividade.getDescricao()+";"+cad_atividade.getAtividade();
			
			if (compare.contains(valor)) {
				
				AtividadeLista.add(cad_atividade.getDescricao() + ";" + cad_atividade.getAtividade());
			}
		}
		
		Collections.sort(AtividadeLista);
		
		return AtividadeLista;
	}

	private ArrayList<String> pesquisaGrupo(String valor, ArrayList<String> arrayList_itens) {

		cad_grupo_empresLista.clear();
		
		listaComCad_grupo_empres = ListaComTodosGrupoEmpresa.devolveLista();
		
		for (Cad_grupo_empres cad_grupo_empres : listaComCad_grupo_empres) {
			
			String compare = cad_grupo_empres.getNome_grupo() + ";" + String.valueOf(cad_grupo_empres.getCod_grupo());
			
			if (compare.contains(valor)) {
				
				cad_grupo_empresLista.add(cad_grupo_empres.getNome_grupo()+";"+cad_grupo_empres.getCod_grupo());
			}			
		}
		
		Collections.sort(cad_grupo_empresLista);
		
		return cad_grupo_empresLista;
	}

	private TextView criaTextViewTitulo(Item_layout item) {

		TextView textView = new TextView(context);
		textView.setId(item.getNr_ordem());
		textView.setText(Html.fromHtml("<b>" + item.getDescricao() + ":" + "</b>"));
		textView.setTextSize(21);
		textView.setTextColor(getResources().getColor(R.color.azul_consigaz));
		textView.setLayoutParams(layoutParams_WRAP_WRAP);
		if (item.getObrigatorio() == 1) {
			textView.setTextColor(getResources().getColor(R.color.vermelho));
			textView.setText(Html.fromHtml("<b>" + item.getDescricao() + ":" + "</b>"));
		}

		return textView;
	}

	private TextView criaTextViewTituloLayoutConsulta(Item_layout item) {

		TextView textView = new TextView(context);
		textView.setId(item.getNr_ordem());
		textView.setText(Html.fromHtml("<b>" + item.getDescricao() + ": " + "</b>"));
		textView.setTextSize(21);
		textView.setTextColor(getResources().getColor(R.color.azul_consigaz));
		textView.setLayoutParams(layoutParams_WRAP_WRAP);

		return textView;
	}

	private TextView criaTextViewConteudo(Item_layout item) {

		TextView textView = new TextView(context);
		textView.setId(item.getNr_ordem());
		textView.setTag("textViewConteudo");
		textView.setTextSize(18);
		textView.setTextColor(getResources().getColor(R.color.azul_consigaz));
		textView.setLayoutParams(layoutParams_WRAP_WRAP);

		return textView;
	}
	
	private EditText criaEditText(Item_layout item, Button botao) {

		final EditText editText = new EditText(context);
		editText.setId(item.getNr_ordem());
		editText.setTag("editText_ordem_"+item.getNr_ordem()+"_layout_"+item.getNr_layout());
		editText.setLayoutParams(layoutParams_MATCH_WRAP);
		editText.setFilters(new InputFilter[] { new InputFilter.AllCaps() });

		if (item.getTamanho() > 0) {

			editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(item.getTamanho()) });
		}

		if (item.getInd_tip_dado() == TipoView.TIPO_DADO_MONETARIO.getValor()) {

			editText.setInputType(InputType.TYPE_CLASS_NUMBER);

			if (item.getQt_casas_decimais() > 0) {

				editText.addTextChangedListener(new MascaraDinheiro(editText, item.getQt_casas_decimais()));
			}
		}

		if (item.getObrigatorio() == 1) {

			editText.setTag(R.id.dez, Generico.OBRIGATORIO_SIM.getValor());
		} else {
			editText.setTag(R.id.dez, Generico.OBRIGATORIO_NAO.getValor());
		}
		
		if (item.getNr_layout() == NomeLayout.REPRESENTACAO.getNumero()) {

			switch (item.getNr_ordem()) {

			//case 3:
				//aplicaOnLongClickParaValidar(botao, editText, "validaCPF");
				//editText.setTag("cpfcnpj");
				//Mascaras.adicionaMascaraCPFouCNPJ(editText, "validaDado");
				//break;

			//case 5:
				//editText.setTag("ceplogradouro");
				//break;
				
			//case 7:
				//editText.setTag("cepbairro");
				//break;
				
			//case 9:
				//editText.setTag("cepcidade");
				//editText.setHint("Digite um trecho, Pressione e segure o campo para pesquisar!");
				//aplicaOnLongClickParaListar(botao, item.getDescricao(), Cidade_getLista(), editText, "BuscaCidade");
				//break;
				
			//case 8:
				//editText.setTag("cepestado");
				//break;
				
			case 1:
				//aplicaOnLongClickParaValidar(botao, editText, "buscaCEP");
				//Mascaras.adicionaMascaraCEP(editText, "valid");
				editText.setHint("Informe o nome do condomínio a ser representado");
				break;
			}
		}

	
		if (item.getNr_layout() == NomeLayout.ENDERECO_PADRAO.getNumero()) {

			switch (item.getNr_ordem()) {

			case 2:
				editText.setTag("ceplogradouro");
				break;
			case 3:
				editText.setTag("numero");
				break;
			case 4:
				editText.setTag("cepbairro");
				break;
			case 5:
				editText.setTag("cepestado");
				break;
			case 6:
				editText.setTag("cepcidade");
				editText.setHint(msgTocarNaLupaParaPesquisar());

				aplicaOnLongClickParaListar(botao, item.getDescricao(), Cidade_getLista(), editText, "BuscaCidade");

				break;

			case 1:
				editText.setTag("cep");
				aplicaOnLongClickParaValidar(botao, editText, "buscaCEP");
				Mascaras.adicionaMascaraCEP(editText, "valid");

				break;
			case 7:
				editText.setTag("rota");

			}
		}
		
		if(item.getNr_layout() == NomeLayout.DADOS_CADASTRO.getNumero()){

			switch (item.getNr_ordem()) {
			
		case 10:
			editText.setHint(msgTocarNaLupaParaPesquisar());
			aplicaOnLongClickParaListar(botao, item.getDescricao(), cad_canal_venda_getLista(), editText, "BuscaCanal");
			break;
		case 11:
			editText.setHint(msgTocarNaLupaParaPesquisar());

			aplicaOnLongClickParaListar(botao, item.getDescricao(), Atividade_getLista(), editText, "BuscaAtividade");
			break;
		}
			}
		
		
		if (item.getNr_layout() == NomeLayout.ENDERECO_ENTREGA.getNumero()) {
			
			switch (item.getNr_ordem()) {

			case 2:
				editText.setTag("ceplogradouro");
				editText.setText(endereco_temp.getEnd());

				break;
			case 3:
				editText.setTag("numero");
				editText.setText(endereco_temp.getNum());

				break;
			case 4:
				editText.setTag("cepbairro");
				editText.setText(endereco_temp.getBai());

				break;
			case 5:
				editText.setTag("cepestado");
				editText.setText(endereco_temp.getEst());

				break;
			case 6:
				editText.setTag("cepcidade");
				editText.setText(endereco_temp.getCid());

				editText.setHint(msgTocarNaLupaParaPesquisar());

				aplicaOnLongClickParaListar(botao, item.getDescricao(), Cidade_getLista(), editText, "BuscaCidade");

				break;

			case 1:
				editText.setTag("cep");
				editText.setText(endereco_temp.getCep());
				aplicaOnLongClickParaValidar(botao, editText, "buscaCEP");
				Mascaras.adicionaMascaraCEP(editText, "valid");
				break;
				
		
			case 9:
				editText.setTag("fone");
				Mascaras.adicionaMascaraCelular(editText, "valid");
				break;
		
			case 10:
				editText.setTag("fax");
				Mascaras.adicionaMascaraCelular(editText, "valid");
				break;

			case 11:
				editText.setText(endereco_temp.getRot());
				editText.setTag("rota");
				break;

			}

		}
		
		if (item.getNr_layout() == NomeLayout.CLIENTE_CONTASIM.getNumero()) {
			
			switch (item.getNr_ordem()) {

			case 2:
				editText.setTag("telefone");
				Mascaras.adicionaMascaraCelular(editText, "valid");
				break;
			
			case 3:
				aplicaOnLongClickParaValidar(botao, editText, "validaCPF");
				editText.setTag("cpfcnpj");
				Mascaras.adicionaMascaraCPFouCNPJ(editText, "validaDado");
				break;
			}
		}

		if (item.getNr_layout() == NomeLayout.INFORMACOES_CLIENTE.getNumero()) {

			switch (item.getNr_ordem()) {

			case 3:
				editText.setHint(msgTocarNaLupaParaPesquisar());
				aplicaOnLongClickParaListar(botao, item.getDescricao(), cad_grupo_empres_getLista(), editText, "BuscaGrupo");
				break;
			
			case 4:
				aplicaOnLongClickParaValidar(botao, editText, "validaCPF");
				editText.setTag("cpfcnpj");
				Mascaras.adicionaMascaraCPFouCNPJ(editText, "validaDado");
				break;

			case 9:
				editText.setTag("tel");
				Mascaras.adicionaMascaraTelefone(editText, "valid");
				break;
				
			case 10:
				editText.setTag("telefone");
				Mascaras.adicionaMascaraCelular(editText, "valid");
				break;
			}
		}

		return editText;
	}

	private String msgTocarNaLupaParaPesquisar() {
		return "Digite um trecho e toque na lupa para pesquisar.";
	}
	
	private void aplicaOnLongClickParaValidar(Button botao, final EditText editText, final String acaoAserExecutada) {

		botao.setVisibility(View.VISIBLE);
		botao.setBackgroundDrawable(getResources().getDrawable(R.drawable.check));
		botao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (acaoAserExecutada.equals("buscaCEP")) {

					buscaCEP(editText);
				}
				if (acaoAserExecutada.equals("validaCNPJ")) {

					validaCNPJ(editText);
				}
				if (acaoAserExecutada.equals("validaCPF")) {
					
					if (editText.getText().toString().length() == 14) {

						if (ValidaCPF.isCPFValido(editText.getText().toString()) == false) {
							
							new MeuAlerta("Erro CPF: Incorreto ", null, context).meuAlertaOk();

						} else {

							new MeuAlerta(" CPF ok!", null, context).meuAlertaOk();
						}
					} 
					else if (editText.getText().toString().length() > 14) {
						
						if (ValidaCNPJ.isCNPJValido((editText.getText().toString())) == false) {
							
							new MeuAlerta("Erro CNPJ: Incorreto ", null, context).meuAlertaOk();
						} else {
							new MeuAlerta(" CNPJ ok! ", null, context).meuAlertaOk();
						}
					}
				}
	
			}
		});
	}
	
	private void aplicaOnLongClickParaListar(Button botao, final String titulo, final ArrayList<String> arrayList_itens, final EditText editText, final String acaoAserExecutada) {

		botao.setVisibility(View.VISIBLE);
		botao.setBackgroundDrawable(getResources().getDrawable(R.drawable.search));
		botao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (acaoAserExecutada.equals("BuscaCanal")) {
					
					pesquisaCanal(String.valueOf(editText.getText()).toUpperCase(), cad_canal_venda);

					escolheApenasUmItemDaLista(titulo, arrayList_itens, editText);
				}
				
				if (acaoAserExecutada.equals("BuscaCidade")) {
					
					pesquisacidade(String.valueOf(editText.getText()).toUpperCase(), CidadeLista);

					escolheApenasUmItemDaLista(titulo, arrayList_itens, editText);
				}
			
				if (acaoAserExecutada.equals("BuscaGrupo")) {
					
					pesquisaGrupo(String.valueOf(editText.getText()).toUpperCase(), cad_grupo_empresLista);

					escolheApenasUmItemDaLista(titulo, arrayList_itens, editText);
				}

				if (acaoAserExecutada.equals("BuscaAtividade")) {
					
					pesquisAtividade(String.valueOf(editText.getText()).toUpperCase(), AtividadeLista);

					escolheApenasUmItemDaLista(titulo, arrayList_itens, editText);
				}
	
			}
		});
	}

	private void buscaCEP(EditText editText) {

		String cep = editText.getText().toString();
		
		new BuscaEnderecoWS().buscarPorCEP(context, linearLayoutPrincipal, cep);
	}

	private void validaCNPJ(EditText editText) {

		String cnpj = editText.getText().toString();
		
		CNPJValidator cnpjValidator = new CNPJValidator();
		
		try {
			cnpjValidator.assertValid(cnpj);
			new MeuAlerta(" CNJP é valido! ", null, context).meuAlertaOk();
			
		} catch (InvalidStateException erro) {
			new MeuAlerta(" Erro CNJP!  ", null, context).meuAlertaOk();
		}
	}
	
	private LinearLayout criaLinearLayout_HORIZONTAL() {

		LinearLayout linearLayoutlinha_HORIZONTAL = new LinearLayout(context);
		linearLayoutlinha_HORIZONTAL.setOrientation(LinearLayout.HORIZONTAL);
		linearLayoutlinha_HORIZONTAL.setLayoutParams(layoutParams_MATCH_WRAP);

		return linearLayoutlinha_HORIZONTAL;
	}

	private LinearLayout criaLinearLayout_VERTICAL() {

		LinearLayout linearLayoutlinha_VERTICAL = new LinearLayout(context);
		linearLayoutlinha_VERTICAL.setOrientation(LinearLayout.VERTICAL);
		linearLayoutlinha_VERTICAL.setLayoutParams(layoutParams_MATCH_WRAP);

		return linearLayoutlinha_VERTICAL;
	}

	private void adicionaViewEmLayoutLocalEprincipal(LinearLayout linearLayoutLocal, TextView textViewTitulo, EditText editText, TextView textViewConteudo, Button botao) {
		
		linearLayoutLocal.addView(botao);

		linearLayoutLocal.addView(textViewTitulo);

		if (editText != null) {

			linearLayoutLocal.addView(editText);
		} else {
			linearLayoutLocal.addView(textViewConteudo);
		}

		linearLayoutPrincipal.addView(linearLayoutLocal);		
	}
	
	@Override
	public void onClick(View view) {

		final Dao dao = new Dao(context);

		final Movimento movimento1 = new Movimento();
		movimento1.setNr_programacao(movimento_1.getNr_programacao());
		movimento1.setNr_visita(movimento_1.getNr_visita());
		movimento1.setNr_layout(nr_layout);
		String t = "OK";

		if (view.getTag() == "button_salvar") {

			if (utilitariosComViewGroup.faltaPreencherEditTextObrigatorios()) {

				new MeuAlerta("Campos Obrigatórios não preenchidos!", null, context).meuAlertaOk();

			} else {
				
				if (nr_layout == NomeLayout.INFORMACOES_CLIENTE.getNumero() || nr_layout == NomeLayout.REPRESENTACAO.getNumero()) {
					
					t = utilitariosComViewGroup.faltaPreencherEditTextObrigatorioscnpj();
				}
				
				if (!t.contains("OK")) {
					
					new MeuAlerta("Verificar CPF/CNPJ ", null, context).meuAlertaOk();
					
				} else {
					
					Representante representante = (Representante) dao.devolveObjeto(Representante.class);

					movimento1.setData_cadastro(new DataPersonalizada().pegaDataAtual_DDMMYYYY_HHMMSS());
					movimento1.setCod_rep(representante.getCod_rep());

					Movimento movimentoPreenchido = procuraWidGetsEpreencheOcampoInformacao(linearLayoutPrincipal, movimento1);

					dao.insereOUatualiza(movimentoPreenchido,
										 Movimento.COLUMN_INTEGER_NR_LAYOUT, movimentoPreenchido.getNr_layout(),
										 Movimento.COLUMN_INTEGER_NR_VISITA, movimentoPreenchido.getNr_visita());
					
					
					
					//##############################
					if (tipoLayout == TipoView.LAYOUT_FORMULARIO.getValor()) {	
						trataEnderecoPadraoTemporario(nr_layout);	
					}	
					//##############################
					
					desabilitaViews();	
					
					new MeuAlerta("Informação salva!", null, context).meuAlertaOk();					
				}
			}
		}

		if (view.getTag() == "button_editar") {

			
			if(nr_layout == NomeLayout.INFORMACOES_CLIENTE.getNumero() || nr_layout == NomeLayout.ENDERECO_PADRAO.getNumero() || nr_layout == NomeLayout.ENDERECO_ENTREGA.getNumero()) {
			
				if(contratoUtil.naoTemNumeroDeContrato(movimento_1.getNr_visita())) {
					
					habilitaViews();			
				}else {
					new MeuAlerta("Contrato Já foi Finalizado", null, context).meuAlertaOk();
				}
			
			}else {
				habilitaViews();			
			}

		}
		
	}

	private void habilitaViews() {
		
		button_salvar.setVisibility(View.VISIBLE);
		button_editar.setVisibility(View.GONE);

		utilitariosComViewGroup.setEnabled_Todas_EditText_TextView(true);
		utilitariosComViewGroup.setEnabled_TodasCheckBox(true);
	}

	private void desabilitaViews() {
		
		button_salvar.setVisibility(View.GONE);
		button_editar.setVisibility(View.VISIBLE);

		utilitariosComViewGroup.setEnabled_Todas_EditText_TextView(false);
		utilitariosComViewGroup.setEnabled_TodasCheckBox(false);
	}


}
