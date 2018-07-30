package mobile.contratodigital.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.Toast;
import mobile.contratodigital.R;
import mobile.contratodigital.enums.Tag;
import mobile.contratodigital.model.Assinatura;
import mobile.contratodigital.view.ActivityContratoContaSIM;
import mobile.contratodigital.view.ActivityContratoPadrao;
import mobile.contratodigital.view.TelaDeAssinatura;
import mobile.contratodigital.view.TelaDeRubrica;
import sharedlib.contratodigital.model.Movimento;

/**
 * Classe criada para tratar a criação das activitys de contratos de modo
 * generico
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class ActContrato {

	private Context context;

	public ActContrato(Context _context) {
		this.context = _context;
	}

	public Button criaBotaoConcluirOUirParaAnexo(Context context, String nomeBotao) {

		Button botao = new Button(context);
		botao.setText(nomeBotao);
		botao.setTextSize(24);
		botao.setTextColor(context.getResources().getColor(R.color.branco));
		botao.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_btn_consigaz));
		LinearLayout.LayoutParams lp_button = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		lp_button.setMargins(0, 100, 0, 0);
		botao.setLayoutParams(lp_button);

		return botao;
	}

	public Intent preencheIntent(Intent intent, String srcContrato, ArrayList<Movimento> listaComMovimentos) {

		intent.putExtra("" + Tag.srcContrato, srcContrato);
		intent.putExtra("" + Tag.listaComMovimentos, listaComMovimentos);

		return intent;
	}

	public LinearLayout cria_ll_assinatura_coluna_holder(Context context) {

		LinearLayout ll_assinatura_coluna_holder = new LinearLayout(context);
		ll_assinatura_coluna_holder.setTag("ll_assinatura_coluna_holder");

		return ll_assinatura_coluna_holder;
	}

	public LinearLayout cria_ll_assinatura_coluna_esquerd(Context context) {

		final LinearLayout ll_assinatura_coluna_esquerda = new LinearLayout(context);
		ll_assinatura_coluna_esquerda.setTag("ll_assinatura_coluna_esquerda");
		ll_assinatura_coluna_esquerda.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams rel_button1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		rel_button1.setMargins(0, 0, 60, 0);
		ll_assinatura_coluna_esquerda.setLayoutParams(rel_button1);

		return ll_assinatura_coluna_esquerda;
	}

	public LinearLayout cria_ll_assinatura_coluna_direita(Context context) {

		final LinearLayout ll_assinatura_coluna_direita = new LinearLayout(context);
		ll_assinatura_coluna_direita.setTag("ll_assinatura_coluna_direita");
		ll_assinatura_coluna_direita.setOrientation(LinearLayout.VERTICAL);

		return ll_assinatura_coluna_direita;
	}

	public LinearLayout cria_ll_principal(TelaBuilder telaBuilder) {

		LinearLayout ll_principal = telaBuilder.cria_LL_v_MATCH_MATCH();
		ll_principal.setPadding(10, 0, 10, 0);
		ll_principal.addView(devolve_IV_cabecalho());
		return ll_principal;
	}

	public ImageView devolve_IV_cabecalho() {

		ImageView iv_cabecalho = new ImageView(context);
		iv_cabecalho.setImageDrawable(context.getResources().getDrawable(R.drawable.logo_cabecalho));
		iv_cabecalho.setPadding(0, 30, 0, 0);
		return iv_cabecalho;
	}

	public TextView devolve_TV_numeroPagina(String numeroPagina) {

		TextView tv_numeroPagina = new TextView(context);
		tv_numeroPagina.setGravity(Gravity.LEFT);
		tv_numeroPagina.setText(numeroPagina);
		return tv_numeroPagina;
	}

	public View devolve_view_PosicaoDaRubrica(int posicaoAltura) {

		View view2 = new View(context);
		view2.setLayoutParams(new LayoutParams(10, posicaoAltura));

		return view2;
	}

	public TextView devolve_TV_DataAtualFormatada() {

		TextoContratos textoContratos = new TextoContratos(context);

		TextView tv_dataAtual = new TextView(context);
		tv_dataAtual.setText(textoContratos.devolveDataAtualFormatada());
		tv_dataAtual.setGravity(Gravity.RIGHT);
		return tv_dataAtual;
	}

	public TextView devolve_TV_rodape() {

		TextView tv_rodape = new TextView(context);
		tv_rodape.setText("Consigaz Distribuidora de Gas Ltda – CAC 11 4197-9300");
		tv_rodape.setPadding(0, 0, 0, 30);
		return tv_rodape;
	}

	public View devolve_view_espacoEntrePaginas() {

		View viewEspacoEntrePaginas = new View(context);
		viewEspacoEntrePaginas.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 5));
		viewEspacoEntrePaginas.setBackgroundColor(Color.BLACK);
		return viewEspacoEntrePaginas;
	}

	public void adiciona_rodape_espacoEntrePaginas_cabecalho(LinearLayout ll_principal) {

		ll_principal.addView(devolve_TV_rodape());
		ll_principal.addView(devolve_view_espacoEntrePaginas());
		ll_principal.addView(devolve_IV_cabecalho());
	}

	public void criaEadicionaFormularioDeAssinatura(LinearLayout ll_assinatura_coluna, String razaoSocial,
			String nomeDoTextView, String qtd, String act_tela, TelaBuilder telaBuilder, String nome, String cargo,
			String rg, String cpf) {

		final LinearLayout ll_assinaturaHolderTudo = new LinearLayout(context);
		// ll_assinaturaHolderTudo.setTag("ll_assinatura"+qtd);
		ll_assinaturaHolderTudo.setOrientation(LinearLayout.VERTICAL);
		ll_assinaturaHolderTudo.setLayoutParams(new LinearLayout.LayoutParams(500, LayoutParams.WRAP_CONTENT));
		if (qtd.equals("4") && act_tela.equals("1")) {
			ll_assinaturaHolderTudo.addView(telaBuilder.cria_TV_conteudo40(""));
		} else if (qtd.equals("0") && act_tela.equals("2")) {
			ll_assinaturaHolderTudo.addView(telaBuilder.cria_TV_conteudo40(""));
		} else if (qtd.equals("2") && act_tela.equals("3")) {
			ll_assinaturaHolderTudo.addView(telaBuilder.cria_TV_conteudo40(""));
		} else if (qtd.equals("0") && act_tela.equals("4")) {
			ll_assinaturaHolderTudo.addView(telaBuilder.cria_TV_conteudo40(""));
		} else {
			ll_assinaturaHolderTudo.addView(devolve_ll_imgAssinaturaHolder(qtd));
			TextView tv_assRazaoSocial = telaBuilder.cria_TV_assinatura(razaoSocial);
			// tv_assRazaoSocial.setTag(qtd + razaoSocial);
			tv_assRazaoSocial.setTag("tv_assRazaoSocial" + qtd);
			ll_assinaturaHolderTudo.addView(tv_assRazaoSocial);

			LinearLayout ll_nomeHolder = new LinearLayout(context);
			// ll_nomeHolder.setTag("ll_nomeHolder"+qtd);

			TextView tv_assNome = telaBuilder.cria_TV_assinatura(nomeDoTextView);
			EditText et_assNome = telaBuilder.cria_ET_assinatura();
			et_assNome.setText(nome);
			if (!et_assNome.getText().toString().equals("")) {
				et_assNome.setEnabled(false);
			}
			et_assNome.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
			et_assNome.setTag("et_assNome" + qtd);

			ll_nomeHolder.addView(tv_assNome);
			ll_nomeHolder.addView(et_assNome);
			ll_assinaturaHolderTudo.addView(ll_nomeHolder);

			LinearLayout ll_cargoHolder = new LinearLayout(context);
			// ll_cargoHolder.setTag("ll_cargoHolder"+qtd);

			TextView tv_assCargo = telaBuilder.cria_TV_assinatura("Cargo:");
			EditText et_assCargo = telaBuilder.cria_ET_assinatura();
			et_assCargo.setText(cargo);
			if (!et_assCargo.getText().toString().equals("")) {
				et_assCargo.setEnabled(false);
			}
			et_assCargo.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
			et_assCargo.setTag("et_assCargo" + qtd);

			ll_cargoHolder.addView(tv_assCargo);
			ll_cargoHolder.addView(et_assCargo);
			ll_assinaturaHolderTudo.addView(ll_cargoHolder);

			LinearLayout ll_rgHolder = new LinearLayout(context);
			// ll_rgHolder.setTag("ll_rgHolder"+qtd);

			TextView tv_assRg = telaBuilder.cria_TV_assinatura("RG:");
			EditText et_assRg = telaBuilder.cria_ET_assinatura();
			et_assRg.setText(rg);
			if (!et_assRg.getText().toString().equals("")) {
				et_assRg.setEnabled(false);
			}
			et_assRg.setTag("et_assRg" + qtd);
			et_assRg.setFilters(new InputFilter[] { new InputFilter.AllCaps() });

			ll_rgHolder.addView(tv_assRg);

			ll_rgHolder.addView(et_assRg);

			ll_assinaturaHolderTudo.addView(ll_rgHolder);

			LinearLayout ll_cpfHolder = new LinearLayout(context);
			TextView tv_asscpf = telaBuilder.cria_TV_assinatura("CPF:");
			final EditText et_asscpf = telaBuilder.cria_ET_assinatura();
			et_asscpf.setTag("et_asscpf" + qtd);
			et_asscpf.setText(cpf);
			if (!et_asscpf.getText().toString().equals("")) {
				et_asscpf.setEnabled(false);
			}
			et_asscpf.addTextChangedListener(new TextWatcher() {
				

				 boolean isUpdating;
	           String old = "";

	           @Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
	           	String mask ="";
	       		if(et_asscpf.getText().toString().length()<=14){
	       			mask = Mask.CPF_MASK;
	       		}else{
	       			mask = Mask.CNPJ_mask;
	       		}
	       		
	               String str = Mask.unmask(s.toString());
	               String mascara = "";
	               if (isUpdating) {
	                   old = str;
	                   isUpdating = false;
	                   return;
	               }

	               int index = 0;
	               for (int i = 0; i < mask.length(); i++) {
	                   char m = mask.charAt(i);
	                   if (m != '#') {
	                       if (index == str.length() && str.length() < old.length()) {
	                           continue;
	                       }
	                       mascara += m;
	                       continue;
	                   }

	                   try {
	                       mascara += str.charAt(index);
	                   } catch (Exception e) {
	                       break;
	                   }

	                   index++;
	               }

	               if (mascara.length() > 0) {
	                   char last_char = mascara.charAt(mascara.length() - 1);
	                   boolean hadSign = false;
	                   while (isASign(last_char) && str.length() == old.length()) {
	                       mascara = mascara.substring(0, mascara.length() - 1);
	                       last_char = mascara.charAt(mascara.length() - 1);
	                       hadSign = true;
	                   }

	                   if (mascara.length() > 0 && hadSign) {
	                       mascara = mascara.substring(0, mascara.length() - 1);
	                   }
	               }

	               isUpdating = true;
	               et_asscpf.setText(mascara);
	               et_asscpf.setSelection(mascara.length());
	           }

	           @Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	           @Override
			public void afterTextChanged(Editable s) {
	             }
			});
			
			et_asscpf.setInputType(InputType.TYPE_CLASS_NUMBER);
			et_asscpf.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
			ll_cpfHolder.addView(tv_asscpf);
			ll_cpfHolder.addView(et_asscpf);
			ll_assinaturaHolderTudo.addView(ll_cpfHolder);

		}

		// LinearLayout ll_cpfHolder = new LinearLayout(context);
		// ll_cpfHolder.setTag("ll_cpfHolder"+qtd);

		// TextView tv_assCpf = telaBuilder.criaTextViewAssinatura("CPF:");
		// EditText et_assCpf = telaBuilder.criaEditTextAssinatura();
		// et_assCpf.setTag("et_assCpf"+qtd);
		// et_assCpf.setInputType(InputType.TYPE_CLASS_NUMBER);
		// et_assCpf.setFilters(new InputFilter[] {new
		// InputFilter.LengthFilter(11)});

		// ll_cpfHolder.addView(tv_assCpf);
		// ll_cpfHolder.addView(et_assCpf);
		// ll_assinaturaHolderTudo.addView(ll_cpfHolder);

		ll_assinatura_coluna.addView(ll_assinaturaHolderTudo);
	}

	private LinearLayout devolve_ll_imgAssinaturaHolder(String qtd) {

		LinearLayout ll_imgAssinaturaHolder = new LinearLayout(context);
		// ll_imgAssinaturaHolder.setTag("ll_imgAssinaturaHolder"+qtd);

		final int width_iv_recebeAss = 380;
		final int height_iv_recebeAss = 250;
		final ImageView iv_recebeAssinatura = new ImageView(context);
		iv_recebeAssinatura.setLayoutParams(new LinearLayout.LayoutParams(width_iv_recebeAss, height_iv_recebeAss));
		// iv_recebeAssinatura.setLayoutParams(new
		// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		iv_recebeAssinatura.setTag("iv_recebeAssinatura" + qtd);

		ImageView iv_caneta = new ImageView(context);
		iv_caneta.setTag("iv_pen" + qtd);
		LinearLayout.LayoutParams LayoutParams_WRAP_WRAP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		LayoutParams_WRAP_WRAP.gravity = Gravity.BOTTOM;
		iv_caneta.setLayoutParams(LayoutParams_WRAP_WRAP);
		iv_caneta.setImageDrawable(context.getResources().getDrawable(R.drawable.pen));
		iv_caneta.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				TelaDeAssinatura telaDeAssinatura = new TelaDeAssinatura(context);
				telaDeAssinatura.mostraTelaDeAssinatura(iv_recebeAssinatura, width_iv_recebeAss, height_iv_recebeAss);
			}
		});

		ll_imgAssinaturaHolder.addView(iv_recebeAssinatura);
		ll_imgAssinaturaHolder.addView(iv_caneta);
		return ll_imgAssinaturaHolder;
	}

	public LinearLayout devolve_ll_imgRubricaHolder(String qtd) {

		LinearLayout ll_imgAssinaturaHolder = new LinearLayout(context);
		// ll_imgAssinaturaHolder.setTag("ll_imgRubricaHolder"+qtd);
		ll_imgAssinaturaHolder.setOrientation(LinearLayout.VERTICAL);

		final int width_iv_recebeAss = 140;
		final int height_iv_recebeAss = 120;
		final int width_iv_recebeAss2 = 60;
		final int height_iv_recebeAss2 = 120;
		final ImageView iv_recebeRubrica = new ImageView(context);
		iv_recebeRubrica.setLayoutParams(new LinearLayout.LayoutParams(width_iv_recebeAss2, height_iv_recebeAss2));
		iv_recebeRubrica.setTag("iv_recebeRubrica" + qtd);

		ImageView iv_caneta = new ImageView(context);
		// iv_caneta.setTag("iv_pen");
		LinearLayout.LayoutParams LayoutParams_WRAP_WRAP = new LinearLayout.LayoutParams(60, 60);
		// LayoutParams_WRAP_WRAP.gravity = Gravity.START;
		iv_caneta.setLayoutParams(LayoutParams_WRAP_WRAP);
		iv_caneta.setImageDrawable(context.getResources().getDrawable(R.drawable.pen));
		iv_caneta.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				TelaDeRubrica telaDeRubrica = new TelaDeRubrica(context);
				telaDeRubrica.mostraTelaDeAssinatura(iv_recebeRubrica, width_iv_recebeAss, height_iv_recebeAss);
			}
		});

		ll_imgAssinaturaHolder.addView(iv_recebeRubrica);
		ll_imgAssinaturaHolder.addView(iv_caneta);
		return ll_imgAssinaturaHolder;
	}

	public boolean temCamposVazios(LinearLayout ll_rubrica, LinearLayout ll_principal, int TOTAL_ASSIGN_GLOBAL,
			String nomeActivity, String act_tela) {

		boolean campoVazio = false;
		int comecaCom = 1;

		if (ll_rubrica != null) {

			ImageView iv_recebeRublica_0 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica0");
			if (iv_recebeRublica_0.getDrawable() == null) {

				iv_recebeRublica_0
						.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

				//				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

				campoVazio = true;

			} else {
				iv_recebeRublica_0.setBackgroundDrawable(null);
			}

			ImageView iv_recebeRublica_1 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica1");
			if (iv_recebeRublica_1.getDrawable() == null) {

				iv_recebeRublica_1
						.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

				//				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

				campoVazio = true;

			} else {
				iv_recebeRublica_1.setBackgroundDrawable(null);
			}

			comecaCom = 2;

			if (nomeActivity.equals("ContratoContaSIM")) {

				ImageView iv_recebeRublica_2 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica2");
				if (iv_recebeRublica_2.getDrawable() == null) {

					iv_recebeRublica_2
							.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
					new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

				//					new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

					campoVazio = true;

				} else {
					iv_recebeRublica_2.setBackgroundDrawable(null);
				}

				ImageView iv_recebeRublica_3 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica3");
				if (iv_recebeRublica_3.getDrawable() == null) {

					iv_recebeRublica_3
							.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

									new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

					campoVazio = true;

				} else {
					iv_recebeRublica_3.setBackgroundDrawable(null);
				}

				comecaCom = 4;
			}
		}

		for (int numeroAssinatura = comecaCom; numeroAssinatura < TOTAL_ASSIGN_GLOBAL; numeroAssinatura++) {
			if (numeroAssinatura != 4 && act_tela.equals("1")) {

				ImageView iv_recebeAssinatura_0 = (ImageView) ll_principal
						.findViewWithTag("iv_recebeAssinatura" + numeroAssinatura);
				if (iv_recebeAssinatura_0.getDrawable() == null) {

					iv_recebeAssinatura_0
							.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

									new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

					campoVazio = true;
					break;
				} else {
					iv_recebeAssinatura_0.setBackgroundDrawable(null);
				}
				EditText et_assNome_0 = (EditText) ll_principal.findViewWithTag("et_assNome" + numeroAssinatura);
				if (et_assNome_0.getText().toString().isEmpty()) {

					et_assNome_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
					new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();

				

					campoVazio = true;
					break;
				} else {
					et_assNome_0.setBackgroundDrawable(null);
				}

				EditText et_assCargo_0 = (EditText) ll_principal.findViewWithTag("et_assCargo" + numeroAssinatura);
				if (et_assCargo_0.getText().toString().isEmpty()) {

					et_assCargo_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
						//	.show();

					campoVazio = true;
					break;
				} else {
					et_assCargo_0.setBackgroundDrawable(null);
				}

				EditText et_assRg_0 = (EditText) ll_principal.findViewWithTag("et_assRg" + numeroAssinatura);
				if (et_assRg_0.getText().toString().isEmpty()) {

					et_assRg_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_assRg_0.setBackgroundDrawable(null);
				}
		
			EditText et_asscpf_0 = (EditText) ll_principal.findViewWithTag("et_asscpf" + numeroAssinatura);
			if (et_asscpf_0.getText().toString().isEmpty()) {

				et_asscpf_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

									new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
						//.show();

				campoVazio = true;
				break;
			} else {
				et_asscpf_0.setBackgroundDrawable(null);
			}
			}
			if (numeroAssinatura != 0 && act_tela.equals("2")) {
				ImageView iv_recebeAssinatura_0 = (ImageView) ll_principal
						.findViewWithTag("iv_recebeAssinatura" + numeroAssinatura);
				if (iv_recebeAssinatura_0.getDrawable() == null) {

					iv_recebeAssinatura_0
							.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

									new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

					campoVazio = true;
					break;
				} else {
					iv_recebeAssinatura_0.setBackgroundDrawable(null);
				}
				EditText et_assNome_0 = (EditText) ll_principal.findViewWithTag("et_assNome" + numeroAssinatura);
				if (et_assNome_0.getText().toString().isEmpty()) {

					et_assNome_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_assNome_0.setBackgroundDrawable(null);
				}

				EditText et_assCargo_0 = (EditText) ll_principal.findViewWithTag("et_assCargo" + numeroAssinatura);
				if (et_assCargo_0.getText().toString().isEmpty()) {

					et_assCargo_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_assCargo_0.setBackgroundDrawable(null);
				}

				EditText et_assRg_0 = (EditText) ll_principal.findViewWithTag("et_assRg" + numeroAssinatura);
				if (et_assRg_0.getText().toString().isEmpty()) {

					et_assRg_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_assRg_0.setBackgroundDrawable(null);
				}
				EditText et_asscpf_0 = (EditText) ll_principal.findViewWithTag("et_asscpf" + numeroAssinatura);

				if (et_asscpf_0.getText().toString().isEmpty()) {

					et_asscpf_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_asscpf_0.setBackgroundDrawable(null);
				}
			}

			if (numeroAssinatura != 2 && act_tela.equals("3")) {
				ImageView iv_recebeAssinatura_0 = (ImageView) ll_principal
						.findViewWithTag("iv_recebeAssinatura" + numeroAssinatura);
				if (iv_recebeAssinatura_0.getDrawable() == null) {

					iv_recebeAssinatura_0
							.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

									new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

					campoVazio = true;
					break;
				} else {
					iv_recebeAssinatura_0.setBackgroundDrawable(null);
				}
				EditText et_assNome_0 = (EditText) ll_principal.findViewWithTag("et_assNome" + numeroAssinatura);
				if (et_assNome_0.getText().toString().isEmpty()) {

					et_assNome_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_assNome_0.setBackgroundDrawable(null);
				}

				EditText et_assCargo_0 = (EditText) ll_principal.findViewWithTag("et_assCargo" + numeroAssinatura);
				if (et_assCargo_0.getText().toString().isEmpty()) {

					et_assCargo_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_assCargo_0.setBackgroundDrawable(null);
				}

				EditText et_assRg_0 = (EditText) ll_principal.findViewWithTag("et_assRg" + numeroAssinatura);
				if (et_assRg_0.getText().toString().isEmpty()) {

					et_assRg_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_assRg_0.setBackgroundDrawable(null);
				}
			}
			if (numeroAssinatura != 0 && act_tela.equals("4")) {
				ImageView iv_recebeAssinatura_0 = (ImageView) ll_principal
						.findViewWithTag("iv_recebeAssinatura" + numeroAssinatura);
				if (iv_recebeAssinatura_0.getDrawable() == null) {

					iv_recebeAssinatura_0
							.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

									new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

					campoVazio = true;
					break;
				} else {
					iv_recebeAssinatura_0.setBackgroundDrawable(null);
				}
				EditText et_assNome_0 = (EditText) ll_principal.findViewWithTag("et_assNome" + numeroAssinatura);
				if (et_assNome_0.getText().toString().isEmpty()) {

					et_assNome_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_assNome_0.setBackgroundDrawable(null);
				}

				EditText et_assCargo_0 = (EditText) ll_principal.findViewWithTag("et_assCargo" + numeroAssinatura);
				if (et_assCargo_0.getText().toString().isEmpty()) {

					et_assCargo_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_assCargo_0.setBackgroundDrawable(null);
				}

				EditText et_assRg_0 = (EditText) ll_principal.findViewWithTag("et_assRg" + numeroAssinatura);
				if (et_assRg_0.getText().toString().isEmpty()) {

					et_assRg_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_assRg_0.setBackgroundDrawable(null);
				}
				EditText et_asscpf_0 = (EditText) ll_principal.findViewWithTag("et_asscpf" + numeroAssinatura);

				if (et_asscpf_0.getText().toString().isEmpty()) {

					et_asscpf_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

										new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
							//.show();

					campoVazio = true;
					break;
				} else {
					et_asscpf_0.setBackgroundDrawable(null);
				}
			}
		

			/*
			 * EditText et_assCpf_0 = (EditText)
			 * ll_principal.findViewWithTag("et_assCpf"+numeroAssinatura); if
			 * (et_assCpf_0.getText().toString().isEmpty()) {
			 * 
			 * et_assCpf_0.setBackgroundDrawable(context.getResources().
			 * getDrawable(R.drawable.style_edittext_erro));
			 * 
			 * Toast.makeText(context,
			 * "Campos em vermelho são de preenchimento obrigatório",
			 * Toast.LENGTH_SHORT)//.show();
			 * 
			 * campoVazio = true; break; } else{
			 * et_assCpf_0.setBackgroundDrawable(null); }
			 */

		}

		return campoVazio;
	}

	public List<Assinatura> procuraAssinaturasEpopulaLista(LinearLayout ll_rubrica, LinearLayout ll_principal,
			int TOTAL_ASSIGN_GLOBAL, boolean ehContratoContaSIM, String act_telas) {

		int comecaCom = 0;

		List<Assinatura> listaComAssinaturas = new ArrayList<Assinatura>();

		if (ll_rubrica != null) {

			ImageView iv_0 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica0");
			Assinatura ass_0 = new Assinatura();
			ass_0.setRecebeAssinatura(iv_0.getDrawable());
			listaComAssinaturas.add(ass_0);

			ImageView iv_1 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica1");
			Assinatura ass_1 = new Assinatura();
			ass_1.setRecebeAssinatura(iv_1.getDrawable());
			listaComAssinaturas.add(ass_1);

			comecaCom = 2;

			if (ehContratoContaSIM) {

				ImageView iv_2 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica2");
				Assinatura ass_2 = new Assinatura();
				ass_2.setRecebeAssinatura(iv_2.getDrawable());
				listaComAssinaturas.add(ass_2);

				ImageView iv_3 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica3");
				Assinatura ass_3 = new Assinatura();
				ass_3.setRecebeAssinatura(iv_3.getDrawable());
				listaComAssinaturas.add(ass_3);

				comecaCom = 4;
			}
		}

		for (int numeroAssinatura = comecaCom; numeroAssinatura < TOTAL_ASSIGN_GLOBAL; numeroAssinatura++) {

			Assinatura assinatura_0 = new Assinatura();
			if (numeroAssinatura != 4 && act_telas.equals("1")) {
				ImageView iv_recebeAssinatura_0 = (ImageView) ll_principal
						.findViewWithTag("iv_recebeAssinatura" + numeroAssinatura);
				assinatura_0.setRecebeAssinatura(iv_recebeAssinatura_0.getDrawable());
				TextView tv_assRazaoSocial = (TextView) ll_principal
						.findViewWithTag("tv_assRazaoSocial" + numeroAssinatura);
				assinatura_0.setRazaoSocial(tv_assRazaoSocial.getText().toString());

				EditText et_assNome_0 = (EditText) ll_principal.findViewWithTag("et_assNome" + numeroAssinatura);
				assinatura_0.setNome(et_assNome_0.getText().toString());

				EditText et_assCargo_0 = (EditText) ll_principal.findViewWithTag("et_assCargo" + numeroAssinatura);
				assinatura_0.setCargo(et_assCargo_0.getText().toString());

				EditText et_assRg_0 = (EditText) ll_principal.findViewWithTag("et_assRg" + numeroAssinatura);
				assinatura_0.setRg(et_assRg_0.getText().toString());

				EditText et_asscpf_0 = (EditText) ll_principal.findViewWithTag("et_asscpf" + numeroAssinatura);
				assinatura_0.setCpf(et_asscpf_0.getText().toString());

				// EditText et_assCpf_0 = (EditText)
				// ll_principal.findViewWithTag("et_assCpf"+numeroAssinatura);
				// assinatura_0.setCpf(et_assCpf_0.getText().toString());

			}
			if (numeroAssinatura != 0 && act_telas.equals("2")) {
				ImageView iv_recebeAssinatura_0 = (ImageView) ll_principal
						.findViewWithTag("iv_recebeAssinatura" + numeroAssinatura);
				assinatura_0.setRecebeAssinatura(iv_recebeAssinatura_0.getDrawable());
				TextView tv_assRazaoSocial = (TextView) ll_principal
						.findViewWithTag("tv_assRazaoSocial" + numeroAssinatura);
				assinatura_0.setRazaoSocial(tv_assRazaoSocial.getText().toString());

				EditText et_assNome_0 = (EditText) ll_principal.findViewWithTag("et_assNome" + numeroAssinatura);
				assinatura_0.setNome(et_assNome_0.getText().toString());

				EditText et_assCargo_0 = (EditText) ll_principal.findViewWithTag("et_assCargo" + numeroAssinatura);
				assinatura_0.setCargo(et_assCargo_0.getText().toString());

				EditText et_assRg_0 = (EditText) ll_principal.findViewWithTag("et_assRg" + numeroAssinatura);
				assinatura_0.setRg(et_assRg_0.getText().toString());

				EditText et_asscpf_0 = (EditText) ll_principal.findViewWithTag("et_asscpf" + numeroAssinatura);
				assinatura_0.setCpf(et_asscpf_0.getText().toString());

				// EditText et_assCpf_0 = (EditText)
				// ll_principal.findViewWithTag("et_assCpf"+numeroAssinatura);
				// assinatura_0.setCpf(et_assCpf_0.getText().toString());

			}
			if (numeroAssinatura != 2 && act_telas.equals("3")) {
				ImageView iv_recebeAssinatura_0 = (ImageView) ll_principal
						.findViewWithTag("iv_recebeAssinatura" + numeroAssinatura);
				assinatura_0.setRecebeAssinatura(iv_recebeAssinatura_0.getDrawable());
				TextView tv_assRazaoSocial = (TextView) ll_principal
						.findViewWithTag("tv_assRazaoSocial" + numeroAssinatura);
				assinatura_0.setRazaoSocial(tv_assRazaoSocial.getText().toString());

				EditText et_assNome_0 = (EditText) ll_principal.findViewWithTag("et_assNome" + numeroAssinatura);
				assinatura_0.setNome(et_assNome_0.getText().toString());

				EditText et_assCargo_0 = (EditText) ll_principal.findViewWithTag("et_assCargo" + numeroAssinatura);
				assinatura_0.setCargo(et_assCargo_0.getText().toString());

				EditText et_assRg_0 = (EditText) ll_principal.findViewWithTag("et_assRg" + numeroAssinatura);
				assinatura_0.setRg(et_assRg_0.getText().toString());

				EditText et_asscpf_0 = (EditText) ll_principal.findViewWithTag("et_asscpf" + numeroAssinatura);
				assinatura_0.setCpf(et_asscpf_0.getText().toString());

				// EditText et_assCpf_0 = (EditText)
				// ll_principal.findViewWithTag("et_assCpf"+numeroAssinatura);
				// assinatura_0.setCpf(et_assCpf_0.getText().toString());

			}
			if (numeroAssinatura != 0 && act_telas.equals("4")) {
				ImageView iv_recebeAssinatura_0 = (ImageView) ll_principal
						.findViewWithTag("iv_recebeAssinatura" + numeroAssinatura);
				assinatura_0.setRecebeAssinatura(iv_recebeAssinatura_0.getDrawable());
				TextView tv_assRazaoSocial = (TextView) ll_principal
						.findViewWithTag("tv_assRazaoSocial" + numeroAssinatura);
				assinatura_0.setRazaoSocial(tv_assRazaoSocial.getText().toString());

				EditText et_assNome_0 = (EditText) ll_principal.findViewWithTag("et_assNome" + numeroAssinatura);
				assinatura_0.setNome(et_assNome_0.getText().toString());

				EditText et_assCargo_0 = (EditText) ll_principal.findViewWithTag("et_assCargo" + numeroAssinatura);
				assinatura_0.setCargo(et_assCargo_0.getText().toString());

				EditText et_assRg_0 = (EditText) ll_principal.findViewWithTag("et_assRg" + numeroAssinatura);
				assinatura_0.setRg(et_assRg_0.getText().toString());

				EditText et_asscpf_0 = (EditText) ll_principal.findViewWithTag("et_asscpf" + numeroAssinatura);
				assinatura_0.setCpf(et_asscpf_0.getText().toString());

				// EditText et_assCpf_0 = (EditText)
				// ll_principal.findViewWithTag("et_assCpf"+numeroAssinatura);
				// assinatura_0.setCpf(et_assCpf_0.getText().toString());

			}

			listaComAssinaturas.add(assinatura_0);

		}

		return listaComAssinaturas;
	}
	  public static boolean isASign(char c) {
	        if (c == '.' || c == '-' || c == '/' || c == '(' || c == ')' || c == ',' || c == ' ') {
	            return true;
	        } else {
	            return false;
	        }
	    }

}
