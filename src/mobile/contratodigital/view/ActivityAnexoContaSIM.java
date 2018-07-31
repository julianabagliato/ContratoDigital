package mobile.contratodigital.view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.SequenciaMovAddedEmLista;
import mobile.contratodigital.enums.Tag;
import mobile.contratodigital.model.Assinatura;
import mobile.contratodigital.pdfword.GeraPDF_anexoContaSIM;
import mobile.contratodigital.pdfword.GeraWord_anexoContaSIM;
import mobile.contratodigital.util.ActAnexo;
import mobile.contratodigital.util.ActContrato;
import mobile.contratodigital.util.TelaBuilder;
import sharedlib.contratodigital.model.Movimento;

public class ActivityAnexoContaSIM extends Activity {

	private Context context;
	private TelaBuilder telaBuilder;
	private ActContrato contratoAct;
	private ActAnexo anexoAct;
	private String src;
	private static final int TOTAL_ASSIGN_GLOBAL = 4;
	private ArrayList<Movimento> listaComMovimentos;
	private String Caminho;
	private Movimento movimento1;
	private String Numero_contrato;
	private String Cliente = "";
	private String cCargo = "";
	private String cRG = "";
	private String cCpf = "";
	private String Testemunha1 = "";
	private String t1Cargo = "";
	private String t1RG = "";
	private String t1Cpf = "";
	private String Testemunha2 = "";
	private String t2Cargo = "";
	private String t2RG = "";
	private String t2Cpf = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();

		movimento1 = (Movimento) bundle.getSerializable("movimento");
		Numero_contrato = bundle.getString("Numero");
		Cliente = bundle.getString("Cliente");
		cCargo = bundle.getString("cCargo");
		cRG = bundle.getString("cRG");
		cCpf = bundle.getString("cCpf");
		Testemunha1 = bundle.getString("Testemunha1");
		t1Cargo = bundle.getString("t1Cargo");
		t1RG = bundle.getString("t1RG");
		t1Cpf = bundle.getString("t1Cpf");
		Testemunha2 = bundle.getString("Testemunha2");
		t2Cargo = bundle.getString("t2Cargo");
		t2RG = bundle.getString("t2RG");
		t2Cpf = bundle.getString("t2Cpf");

		context = ActivityAnexoContaSIM.this;

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		actionBar.setTitle("Conta SIM Anexo I");

		Intent intent = getIntent();

		String srcContrato = intent.getStringExtra("" + Tag.srcContrato);

		Caminho = srcContrato.replace("/ContratoContaSIM", "");
		src = srcContrato.replace("ContratoContaSIM", "AnexoContaSIM");

		listaComMovimentos = (ArrayList<Movimento>) intent.getSerializableExtra("" + Tag.listaComMovimentos);

		telaBuilder = new TelaBuilder(context);
		contratoAct = new ActContrato(context);
		anexoAct = new ActAnexo();

		setContentView(constroiTela());
	}

	private ScrollView constroiTela() {

		ScrollView scrollView = new ScrollView(context);
		scrollView.setBackgroundColor(Color.WHITE);

		LinearLayout ll_principal = contratoAct.cria_ll_principal(telaBuilder);

		criaEadicionaViewsTextoContratoNa_ll_principal(ll_principal);

		criaAssinaturasBotaoRodape(ll_principal, "6");

		scrollView.addView(ll_principal);
		return scrollView;
	}

	private void criaEadicionaViewsTextoContratoNa_ll_principal(LinearLayout ll_principal) {

		anexoAct.desenhaLayoutDaTelaJahComInformacao(context, ll_principal, contratoAct, telaBuilder,
				listaComMovimentos, "AnexoContaSIM");
	}

	private void criaAssinaturasBotaoRodape(final LinearLayout ll_principal, String numeroPagina) {

		// cria assinaturas:
		LinearLayout ll_assinatura_coluna_holder = contratoAct.cria_ll_assinatura_coluna_holder(context);
		final LinearLayout ll_assinatura_coluna_esquerda = contratoAct.cria_ll_assinatura_coluna_esquerd(context);
		final LinearLayout ll_assinatura_coluna_direita = contratoAct.cria_ll_assinatura_coluna_direita(context);

		ll_assinatura_coluna_holder.addView(ll_assinatura_coluna_esquerda);
		ll_assinatura_coluna_holder.addView(ll_assinatura_coluna_direita);
		ll_principal.addView(ll_assinatura_coluna_holder);

		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_esquerda,
				"FORNECEDORAS (CONSIGAZ, GASBALL E PROPANGÁS)", "Nome:", "0", "2", telaBuilder, "", "", "", "");
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_esquerda, "Testemunha", "Nome:", "1", "2",
				telaBuilder, Testemunha1, t1Cargo, t1RG, t1Cpf);

		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_direita, "CONDOMÍNIO", "Nome:", "2", "2",
				telaBuilder, Cliente, cCargo, cRG, cCpf);
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_direita, "Testemunha", "Nome:", "3", "2",
				telaBuilder, Testemunha2, t2Cargo, t2RG, t2Cpf);
		// cria assinaturas:

		ll_principal.addView(contratoAct.devolve_TV_numeroPagina(numeroPagina));
		ll_principal.addView(contratoAct.devolve_TV_rodape());

		Button b_concluir = contratoAct.criaBotaoConcluirOUirParaAnexo(context, "Concluir");
		b_concluir.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!contratoAct.temCamposVaziosContratoPadrao(null, ll_principal, "AnexoContaSIM")) {
					
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
					alertDialog.setMessage("Deseja Realmente Gerar Contrato?")
							.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int id) {

									anexoAct.procura_etObservacaoEgravaConteudoInformadoPelousuario(ll_principal);

									boolean ehContratoContaSIM = false;

									List<Assinatura> listaComAssinaturas = contratoAct.procuraAssinaturasEpopulaListaContratoPadrao(
											null, ll_principal, ehContratoContaSIM);

									criaArquivoWord(listaComAssinaturas);
									criaArquivoPDF(listaComAssinaturas, ehContratoContaSIM);

									fechaActivity();
									// metodosContratoAct.chamaVisualizadorBaseadoExtensao(context,
									// srcContrato, "pdf");

								}
							}).setNegativeButton("Não", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int id) {
								}
							});
					alertDialog.setCancelable(false);
					alertDialog.show();

				}
			}
		});

		ll_principal.addView(b_concluir);
	}

	private void criaArquivoWord(List<Assinatura> listaComAssinaturas) {

		String novoSource = src + ".doc";

		GeraWord_anexoContaSIM geraWord_anexoContaSIM = new GeraWord_anexoContaSIM(context);
		geraWord_anexoContaSIM.criaWord(novoSource, listaComMovimentos, listaComAssinaturas);
		
	}

	private void criaArquivoPDF(List<Assinatura> listaComAssinaturas, boolean ehContratoContaSIM) {

		String novoSource = src + ".pdf";

		try {

			boolean ehContrato = false;

			GeraPDF_anexoContaSIM geraPDF_anexoContaSIM = new GeraPDF_anexoContaSIM(context);
			geraPDF_anexoContaSIM.criaPDF(novoSource, listaComMovimentos, listaComAssinaturas, ehContrato,
					ehContratoContaSIM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String Num = String.valueOf(listaComMovimentos.get(SequenciaMovAddedEmLista.mov_informacoesCliente.getPosicao()).getInformacao_1() + "_"
								  + listaComMovimentos.get(SequenciaMovAddedEmLista.mov_informacoesCliente.getPosicao()).getInformacao_4().replace("/", "-"));
		File Antigo_caminho = new File(Caminho);
		File novoCaminho = new File(Caminho.replace(Num, "_" + Numero_contrato));
		String deletar = Caminho.replace("_" + Numero_contrato, Num);

		Antigo_caminho.renameTo(novoCaminho);

		Dao dao = new Dao(context);

		int tamanho = listaComMovimentos.size();
		for (int i = 0; i < tamanho; i++) {
			if (listaComMovimentos.get(i) != null){
			Movimento movimento2 = new Movimento();
			movimento2 = listaComMovimentos.get(i);
			movimento2.setNr_contrato(Numero_contrato);
			movimento2.setStatus(0);
			preencheNoObjetoOcampoInformacao(movimento2, Numero_contrato);

			insereMovimento(dao, movimento2);
			}
		}

		try {
			Runtime.getRuntime().exec("cmd /c net use k: \\\\NTI_X23\\C$\\Users /yes");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Runtime.getRuntime().exec("cmd /c rd k:\\" + deletar + " /s /q");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Runtime.getRuntime().exec("cmd /c net use k: /delete /yes");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void fechaActivity() {

		Movimento mov_informacoesDoCliente = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_informacoesCliente.getPosicao());

		Intent intent = new Intent(context, FragActivityOcorrencia.class);

		Bundle bundle = new Bundle();
		bundle.putSerializable("movimento", mov_informacoesDoCliente);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}

	private Movimento preencheNoObjetoOcampoInformacao(Object objeto, String conteudo) {

		try {

			Class<?> classe = objeto.getClass();

			for (Field atributo : classe.getDeclaredFields()) {

				atributo.setAccessible(true);

				if (atributo.getName().contains("Nr_contrato")) {

					if (!conteudo.isEmpty()) {

						atributo.set(objeto, conteudo);

						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Movimento) objeto;
	}

	private void insereMovimento(Dao dao, Movimento mov) {

		dao.insereOUatualiza(mov,
				// Movimento.COLUMN_INTEGER_NR_PROGRAMACAO,
				// mov.getNr_programacao(),
				Movimento.COLUMN_INTEGER_NR_LAYOUT, mov.getNr_layout(), Movimento.COLUMN_INTEGER_NR_VISITA,
				mov.getNr_visita());
	}

}
