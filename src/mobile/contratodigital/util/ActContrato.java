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

	public void criaEadicionaFormularioDeAssinatura(LinearLayout ll_assinatura_coluna, 
													String razaoSocial,
													String nomeDoTextView, 
													String qtd, 
													String act_tela, 
													TelaBuilder telaBuilder, 
													String nome, String cargo, String rg, String cpf) {

		final LinearLayout ll_assinaturaHolderTudo = new LinearLayout(context);
		// ll_assinaturaHolderTudo.setTag("ll_assinatura"+qtd);
		ll_assinaturaHolderTudo.setOrientation(LinearLayout.VERTICAL);
		ll_assinaturaHolderTudo.setLayoutParams(new LinearLayout.LayoutParams(500, LayoutParams.WRAP_CONTENT));
		
		if (qtd.equals("4") && act_tela.equals("1")) {
			
			ll_assinaturaHolderTudo.addView(telaBuilder.cria_TV_conteudo40(""));
		} 
		else if (qtd.equals("0") && act_tela.equals("2")) {
			ll_assinaturaHolderTudo.addView(telaBuilder.cria_TV_conteudo40(""));
		} 
		else if (qtd.equals("2") && act_tela.equals("3")) {
			ll_assinaturaHolderTudo.addView(telaBuilder.cria_TV_conteudo40(""));
		} 
		else if (qtd.equals("0") && act_tela.equals("4")) {
			ll_assinaturaHolderTudo.addView(telaBuilder.cria_TV_conteudo40(""));
		} 
		else {
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

			if(!nomeDoTextView.contains("Testemunha")) {
							
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
				
			}

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

	public boolean temCamposVaziosContratoPadrao(LinearLayout ll_rubrica, LinearLayout ll_principal, String nomeActivity) {

			ImageView iv_recebeRublica_0 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica0");
			if (iv_recebeRublica_0.getDrawable() == null) {

				iv_recebeRublica_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));

				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

				return true;
			} else {
				iv_recebeRublica_0.setBackgroundDrawable(null);
			}

			ImageView iv_recebeRublica_1 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica1");
			if (iv_recebeRublica_1.getDrawable() == null) {

				iv_recebeRublica_1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				
				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();

				return true;
			} else {
				iv_recebeRublica_1.setBackgroundDrawable(null);
			}

			
			
			
			ImageView iv_recebeAssinatura_4 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura4");
			if (iv_recebeAssinatura_4.getDrawable() == null) {
				iv_recebeAssinatura_4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();
				return true;
			} else {
				iv_recebeAssinatura_4.setBackgroundDrawable(null);
			}
		
			EditText et_assNome_4 = (EditText) ll_principal.findViewWithTag("et_assNome4");
			if (et_assNome_4.getText().toString().isEmpty()) {
				et_assNome_4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
				return true;
			} else {
				et_assNome_4.setBackgroundDrawable(null);
			}

			EditText et_assCargo_4 = (EditText) ll_principal.findViewWithTag("et_assCargo4");
			if (et_assCargo_4.getText().toString().isEmpty()) {
				et_assCargo_4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
				return true;
			} else {
				et_assCargo_4.setBackgroundDrawable(null);
			}

			EditText et_assRg_4 = (EditText) ll_principal.findViewWithTag("et_assRg4");
			if (et_assRg_4.getText().toString().isEmpty()) {
				et_assRg_4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
				return true;
			} else {
				et_assRg_4.setBackgroundDrawable(null);
			}
	
			EditText et_asscpf_4 = (EditText) ll_principal.findViewWithTag("et_asscpf4");
			if (et_asscpf_4.getText().toString().isEmpty()) {
				et_asscpf_4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
				return true;
			} else {
				et_asscpf_4.setBackgroundDrawable(null);
			}

		return false;
	}

	public boolean temCamposVaziosContratoContaSIM(LinearLayout ll_rubrica, LinearLayout ll_principal) {
		
			ImageView iv_recebeRublica_0 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica0");
			if (iv_recebeRublica_0.getDrawable() == null) {
				iv_recebeRublica_0.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();
				return true;
			} else {
				iv_recebeRublica_0.setBackgroundDrawable(null);
			}

			ImageView iv_recebeRublica_1 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica1");
			if (iv_recebeRublica_1.getDrawable() == null) {
				iv_recebeRublica_1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();
				return true;
			} else {
				iv_recebeRublica_1.setBackgroundDrawable(null);
			}

			ImageView iv_recebeRublica_2 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica2");
			if (iv_recebeRublica_2.getDrawable() == null) {
				iv_recebeRublica_2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();
				return true;
			} else {
				iv_recebeRublica_2.setBackgroundDrawable(null);
			}

			ImageView iv_recebeRublica_3 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica3");
			if (iv_recebeRublica_3.getDrawable() == null) {
				iv_recebeRublica_3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();
				return true;
			} else {
				iv_recebeRublica_3.setBackgroundDrawable(null);
			}

			
			
			ImageView iv_recebeAssinatura_4 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura6");
			if (iv_recebeAssinatura_4.getDrawable() == null) {
				iv_recebeAssinatura_4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();
				return true;
			} else {
				iv_recebeAssinatura_4.setBackgroundDrawable(null);
			}
		
			EditText et_assNome_4 = (EditText) ll_principal.findViewWithTag("et_assNome6");
			if (et_assNome_4.getText().toString().isEmpty()) {
				et_assNome_4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
				return true;
			} else {
				et_assNome_4.setBackgroundDrawable(null);
			}

			EditText et_assCargo_4 = (EditText) ll_principal.findViewWithTag("et_assCargo6");
			if (et_assCargo_4.getText().toString().isEmpty()) {
				et_assCargo_4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
				return true;
			} else {
				et_assCargo_4.setBackgroundDrawable(null);
			}

			EditText et_assRg_4 = (EditText) ll_principal.findViewWithTag("et_assRg6");
			if (et_assRg_4.getText().toString().isEmpty()) {
				et_assRg_4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
				return true;
			} else {
				et_assRg_4.setBackgroundDrawable(null);
			}
	
			EditText et_asscpf_4 = (EditText) ll_principal.findViewWithTag("et_asscpf6");
			if (et_asscpf_4.getText().toString().isEmpty()) {
				et_asscpf_4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
				new MeuAlerta("Campos em vermelho são de preenchimento obrigatório", null, context).meuAlertaOk();
				return true;
			} else {
				et_asscpf_4.setBackgroundDrawable(null);
			}

		return false;
	}

	public List<Assinatura> procuraAssinaturasEpopulaListaContratoPadrao(LinearLayout ll_rubrica, LinearLayout ll_principal) {

		List<Assinatura> listaComAssinaturas = new ArrayList<Assinatura>();

			ImageView iv_0 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica0");
			Assinatura assinatura_0 = new Assinatura();
					   assinatura_0.setRecebeAssinatura(iv_0.getDrawable());
			listaComAssinaturas.add(assinatura_0);

			ImageView iv_1 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica1");
			Assinatura assinatura_1 = new Assinatura();
					   assinatura_1.setRecebeAssinatura(iv_1.getDrawable());
			listaComAssinaturas.add(assinatura_1);
	


			Assinatura assinatura_3 = new Assinatura();
				
				ImageView iv_recebeAssinatura_3 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura3");
				//if(iv_recebeAssinatura_3 != null) {					
					assinatura_3.setRecebeAssinatura(iv_recebeAssinatura_3.getDrawable());
				//}
	
				EditText et_assNome_3 = (EditText) ll_principal.findViewWithTag("et_assNome3");
			//	if(et_assNome_3 != null) {					
					assinatura_3.setNome(et_assNome_3.getText().toString());
			//	}

				EditText et_assRg_3 = (EditText) ll_principal.findViewWithTag("et_assRg3");
				//if(et_assRg_3 != null) {					
					assinatura_3.setRg(et_assRg_3.getText().toString());
				//}	

				EditText et_asscpf_3 = (EditText) ll_principal.findViewWithTag("et_asscpf3");
				//if(et_asscpf_3 != null) {					
					assinatura_3.setCpf(et_asscpf_3.getText().toString());
				//}

			listaComAssinaturas.add(assinatura_3);

			
			Assinatura assinatura_4 = new Assinatura();
			
			ImageView iv_recebeAssinatura_4 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura4");
			assinatura_4.setRecebeAssinatura(iv_recebeAssinatura_4.getDrawable());
			
			TextView tv_assRazaoSocial_4 = (TextView) ll_principal.findViewWithTag("tv_assRazaoSocial4");
			assinatura_4.setRazaoSocial(tv_assRazaoSocial_4.getText().toString());

			EditText et_assNome_4 = (EditText) ll_principal.findViewWithTag("et_assNome4");
			assinatura_4.setNome(et_assNome_4.getText().toString());

			EditText et_assCargo_4 = (EditText) ll_principal.findViewWithTag("et_assCargo4");
			assinatura_4.setCargo(et_assCargo_4.getText().toString());

			EditText et_assRg_4 = (EditText) ll_principal.findViewWithTag("et_assRg4");
			assinatura_4.setRg(et_assRg_4.getText().toString());

			EditText et_asscpf_4 = (EditText) ll_principal.findViewWithTag("et_asscpf4");
			assinatura_4.setCpf(et_asscpf_4.getText().toString());

		listaComAssinaturas.add(assinatura_4);

		Assinatura assinatura_5 = new Assinatura();
		
		ImageView iv_recebeAssinatura_5 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura5");
		//if(iv_recebeAssinatura_5 != null) {					
			assinatura_5.setRecebeAssinatura(iv_recebeAssinatura_5.getDrawable());
		//}

		EditText et_assNome_5 = (EditText) ll_principal.findViewWithTag("et_assNome5");
		//if(et_assNome_5 != null) {					
			assinatura_5.setNome(et_assNome_5.getText().toString());
		//}

		EditText et_assRg_5 = (EditText) ll_principal.findViewWithTag("et_assRg5");
		//if(et_assRg_5 != null) {					
			assinatura_5.setRg(et_assRg_5.getText().toString());
		//}	

		EditText et_asscpf_5 = (EditText) ll_principal.findViewWithTag("et_asscpf5");
		//if(et_asscpf_5 != null) {					
			assinatura_5.setCpf(et_asscpf_5.getText().toString());
		//}

		listaComAssinaturas.add(assinatura_5);

		return listaComAssinaturas;
	}

	public ArrayList<Assinatura> procuraAssinaturasEpopulaListaContratoContaSIM(LinearLayout ll_rubrica, LinearLayout ll_principal) {

		ArrayList<Assinatura> listaComAssinaturas = new ArrayList<Assinatura>();

			ImageView iv_0 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica0");
			Assinatura assinatura_0 = new Assinatura();
					   assinatura_0.setRecebeAssinatura(iv_0.getDrawable());
			listaComAssinaturas.add(assinatura_0);

			ImageView iv_1 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica1");
			Assinatura assinatura_1 = new Assinatura();
					   assinatura_1.setRecebeAssinatura(iv_1.getDrawable());
			listaComAssinaturas.add(assinatura_1);


				ImageView iv_2 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica2");
				Assinatura assinatura_2 = new Assinatura();
				assinatura_2.setRecebeAssinatura(iv_2.getDrawable());
				listaComAssinaturas.add(assinatura_2);

				ImageView iv_3 = (ImageView) ll_rubrica.findViewWithTag("iv_recebeRubrica3");
				Assinatura assinatura_3 = new Assinatura();
				assinatura_3.setRecebeAssinatura(iv_3.getDrawable());
				listaComAssinaturas.add(assinatura_3);
	
			


			Assinatura assinatura_5 = new Assinatura();
				
				ImageView iv_recebeAssinatura_5 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura5");
				assinatura_5.setRecebeAssinatura(iv_recebeAssinatura_5.getDrawable());
	
				EditText et_assNome_5 = (EditText) ll_principal.findViewWithTag("et_assNome5");
				//if(et_assNome_3 != null) {					
					assinatura_5.setNome(et_assNome_5.getText().toString());
				//}

				EditText et_assRg_5 = (EditText) ll_principal.findViewWithTag("et_assRg5");
				//if(et_assRg_3 != null) {					
					assinatura_5.setRg(et_assRg_5.getText().toString());
				//}	

				EditText et_asscpf_5 = (EditText) ll_principal.findViewWithTag("et_asscpf5");
				//if(et_asscpf_3 != null) {					
					assinatura_5.setCpf(et_asscpf_5.getText().toString());
				//}

			listaComAssinaturas.add(assinatura_5);

			
			Assinatura assinatura_6 = new Assinatura();
			
			ImageView iv_recebeAssinatura_6 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura6");
			assinatura_6.setRecebeAssinatura(iv_recebeAssinatura_6.getDrawable());
			
			TextView tv_assRazaoSocial_6 = (TextView) ll_principal.findViewWithTag("tv_assRazaoSocial6");
			assinatura_6.setRazaoSocial(tv_assRazaoSocial_6.getText().toString());

			EditText et_assNome_6 = (EditText) ll_principal.findViewWithTag("et_assNome6");
			assinatura_6.setNome(et_assNome_6.getText().toString());

			EditText et_assCargo_6 = (EditText) ll_principal.findViewWithTag("et_assCargo6");
			assinatura_6.setCargo(et_assCargo_6.getText().toString());

			EditText et_assRg_6 = (EditText) ll_principal.findViewWithTag("et_assRg6");
			assinatura_6.setRg(et_assRg_6.getText().toString());

			EditText et_asscpf_6 = (EditText) ll_principal.findViewWithTag("et_asscpf6");
			assinatura_6.setCpf(et_asscpf_6.getText().toString());

		listaComAssinaturas.add(assinatura_6);

		Assinatura assinatura_7 = new Assinatura();
		
		ImageView iv_recebeAssinatura_7 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura7");
		//if(iv_recebeAssinatura_7 != null) {					
			assinatura_7.setRecebeAssinatura(iv_recebeAssinatura_7.getDrawable());
		//}

		EditText et_assNome_7 = (EditText) ll_principal.findViewWithTag("et_assNome7");
		//if(et_assNome_7 != null) {					
			assinatura_7.setNome(et_assNome_7.getText().toString());
		//}

		EditText et_assRg_7 = (EditText) ll_principal.findViewWithTag("et_assRg7");
		//if(et_assRg_7 != null) {					
			assinatura_7.setRg(et_assRg_7.getText().toString());
		//}	

		EditText et_asscpf_7 = (EditText) ll_principal.findViewWithTag("et_asscpf7");
		//if(et_asscpf_7 != null) {					
			assinatura_7.setCpf(et_asscpf_7.getText().toString());
		//}

		listaComAssinaturas.add(assinatura_7);

		return listaComAssinaturas;
	}

	public boolean temCamposVaziosAnexo(LinearLayout ll_principal) {
		
		ImageView iv_recebeAssinatura_2 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura2");
		if (iv_recebeAssinatura_2.getDrawable() == null) {
			iv_recebeAssinatura_2.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_edittext_erro));
			new MeuAlerta("Favor assinar o contrato", null, context).meuAlertaOk();
			return true;
		} else {
			iv_recebeAssinatura_2.setBackgroundDrawable(null);
		}

		return false;
	}

	public List<Assinatura> procuraAssinaturasEpopulaListaAnexo(LinearLayout ll_principal) {

		List<Assinatura> listaComAssinaturas = new ArrayList<Assinatura>();

			Assinatura assinatura_1 = new Assinatura();
				
				ImageView iv_recebeAssinatura_1 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura1");
					assinatura_1.setRecebeAssinatura(iv_recebeAssinatura_1.getDrawable());
	
				EditText et_assNome_1 = (EditText) ll_principal.findViewWithTag("et_assNome1");
					assinatura_1.setNome(et_assNome_1.getText().toString());

				EditText et_assRg_1 = (EditText) ll_principal.findViewWithTag("et_assRg1");
					assinatura_1.setRg(et_assRg_1.getText().toString());

				EditText et_asscpf_1 = (EditText) ll_principal.findViewWithTag("et_asscpf1");
					assinatura_1.setCpf(et_asscpf_1.getText().toString());

			listaComAssinaturas.add(assinatura_1);

			
			Assinatura assinatura_2 = new Assinatura();
			
			ImageView iv_recebeAssinatura_2 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura2");
			assinatura_2.setRecebeAssinatura(iv_recebeAssinatura_2.getDrawable());
			
			TextView tv_assRazaoSocial_2 = (TextView) ll_principal.findViewWithTag("tv_assRazaoSocial2");
			assinatura_2.setRazaoSocial(tv_assRazaoSocial_2.getText().toString());

			EditText et_assNome_2 = (EditText) ll_principal.findViewWithTag("et_assNome2");
			assinatura_2.setNome(et_assNome_2.getText().toString());

			EditText et_assCargo_2 = (EditText) ll_principal.findViewWithTag("et_assCargo2");
			assinatura_2.setCargo(et_assCargo_2.getText().toString());

			EditText et_assRg_2 = (EditText) ll_principal.findViewWithTag("et_assRg2");
			assinatura_2.setRg(et_assRg_2.getText().toString());

			EditText et_asscpf_2 = (EditText) ll_principal.findViewWithTag("et_asscpf2");
			assinatura_2.setCpf(et_asscpf_2.getText().toString());

		listaComAssinaturas.add(assinatura_2);

		Assinatura assinatura_3 = new Assinatura();
		
		ImageView iv_recebeAssinatura_3 = (ImageView) ll_principal.findViewWithTag("iv_recebeAssinatura3");
			assinatura_3.setRecebeAssinatura(iv_recebeAssinatura_3.getDrawable());

		EditText et_assNome_3 = (EditText) ll_principal.findViewWithTag("et_assNome3");
			assinatura_3.setNome(et_assNome_3.getText().toString());

		EditText et_assRg_3 = (EditText) ll_principal.findViewWithTag("et_assRg3");
			assinatura_3.setRg(et_assRg_3.getText().toString());

		EditText et_asscpf_3 = (EditText) ll_principal.findViewWithTag("et_asscpf3");
			assinatura_3.setCpf(et_asscpf_3.getText().toString());

		listaComAssinaturas.add(assinatura_3);

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
