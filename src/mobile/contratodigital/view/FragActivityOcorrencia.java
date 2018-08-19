package mobile.contratodigital.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.enums.Tag;
import mobile.contratodigital.enums.TipoView;
import mobile.contratodigital.model.CaminhoArquivo;
import mobile.contratodigital.model.ContratoUtil;
import mobile.contratodigital.util.ChamaAplicativo;
import mobile.contratodigital.util.Diminui_MB_imagens;
import mobile.contratodigital.util.GetChildCount;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.PermissaoActivity;
import mobile.contratodigital.util.TelaBuilder;
import mobile.contratodigital.util.TrabalhaComFotos;
import sharedlib.contratodigital.model.Layout;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.model.Representante;
import sharedlib.contratodigital.util.Generico;

public class FragActivityOcorrencia extends FragmentActivity {

	private Context context;
	private ActionBar actionBar;
	private Movimento movimento1;
	private FragmentManager fragmentManager;
	private LinearLayout linearLayout_obrigatorioDaScrollView;
	private LinearLayout linearLayout_telaParaAsLabels;
	private LinearLayout linearLayout_telaParaOsFormularios;
	private LayoutParams layoutParams_MATCH_MATCH;
	private LayoutParams layoutParams_MATCH_WRAP;
	private FragConteudoFormularioHolder fragConteudoFormularioHolder;
	private List<Integer> listaComNrLayoutNaoRepetidos;
	private List<Movimento> listaComMovimentos_NAO_obrigatorios;
	private List<Layout> listaComLayouts_NAO_Obrigatorio;
	private List<Layout> listaComlayouts_Obrigatorios;
	private TelaBuilder telaBuilder;
	private Dao dao;
	//private String srcContratos;
	private String nomeDoArquivoSelecionado;
	//private int nr = 0;
	//private TextView textFile;
	public static final int REQUISICAO_BUSCA_FOTO = 1234;
	public static final int REQUISICAO_PERMISSAO_TIRAR_FOTO = 111;
	public static final int REQUISICAO_PERMISSAO_LEITURA = 222;
	public static final int REQUISICAO_PERMISSAO_ESCRITA = 333;
	private static final int REQUISICAO_BUSCA_ARQUIVO = 33333;
	public static final int REQUISICAO_SIMULADOR = 444;
	
	private static final int GerarContratoPadrao = 2;
	private static final int GerarContratoContaSIM = 3;
	private static final int SimularPrecos = 4;
	private static final int ConsultarCNPJ = 5;
	private static final int ConsultarInscricaoEstadual = 6;
	private static final int InformarPecas = 7;	
	private static final int FOTO_CNPJ = 8;
	private static final int FOTO_CPF = 9;
	private static final int FOTO_RG = 10;
	private static final int FOTO_CONTRATO = 11;
	private static final int FOTOS_DIVERSAS = 12;
	private static final int SimularInstalacao = 13;
	private static final int AnexarPDF = 14;
	private static final int VISUALIZAR_ARQUIVOS_GERADOS = 15;
	private static final int GerarNumeroDeContrato = 16;
	private static final int FOTO_PAC = 17;
	private ContratoUtil contratoUtil;
	private static final String ADICIONOU_LAYOUT_REPRESENTACAO = "SIM";
	//private static final String NOME_DO_ARQUIVO = "nomeDoArquivo";
	//private PermissaoActivity permissaoActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();

		movimento1 = (Movimento) bundle.getSerializable("movimento");
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		actionBar.setTitle("" + movimento1.getNr_visita());

		fragmentManager = getSupportFragmentManager();

		context = FragActivityOcorrencia.this;

		telaBuilder = new TelaBuilder(context);
		dao = new Dao(context);
		
		listaComlayouts_Obrigatorios = dao.listaTodaTabela(Layout.class, 
														   Layout.COLUMN_INTEGER_IND_TIP_LAYOUT, TipoView.LAYOUT_FORMULARIO.getValor(), 
														   Layout.COLUMN_INTEGER_OBRIGATORIO, Generico.LAYOUT_OBRIGATORIO_SIM.getValor());
		
		
		Movimento movimentoRepresentacao = (Movimento)dao.devolveObjeto(Movimento.class,
																		Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita(), 
																		Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.REPRESENTACAO.getNumero());

		if(movimentoRepresentacao != null) {
			
			if(movimentoRepresentacao.getInformacao_50().equals(ADICIONOU_LAYOUT_REPRESENTACAO)) {
			
			Layout layout = (Layout) dao.devolveObjeto(Layout.class, Layout.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.REPRESENTACAO.getNumero());
			
			listaComlayouts_Obrigatorios.add(layout);	
			}	
		}
		
		contratoUtil = new ContratoUtil(dao, context);
		
	 	//permissaoActivity = new PermissaoActivity();

		
		setContentView(constroiTela());
		
		populaTodasAsListasEcriaLayoutDinamicamente();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0,  0, 0, "Adicionar Formulário");
		menu.add(0,  1, 0, "Remover Formulário");
		menu.add(0,  2, 0, "Gerar Contrato Padrão");
		menu.add(0,  3, 0, "Gerar Contrato Conta SIM");
		menu.add(0,  4, 0, "Simular Preços");
		menu.add(0,  5, 0, "Consultar CNPJ");
		menu.add(0,  6, 0, "Consultar Inscrição Estadual");
		menu.add(0,  7, 0, "Informar Peças");
		menu.add(0, 10, 0, "Anexar Foto RG");
		menu.add(0,  9, 0, "Anexar Foto CPF");
		menu.add(0,  8, 0, "Anexar Foto CNPJ");
		menu.add(0, 17, 0, "Anexar Foto PAC");
		menu.add(0, 11, 0, "Anexar Foto Contrato");
		menu.add(0, 12, 0, "Anexar Fotos");
		menu.add(0, 14, 0, "Anexar PDFs");
		menu.add(0, 13, 0, "Simular Instalação");
		menu.add(0, 15, 0, "Visualizar Arquivos Gerados");
		menu.add(0, 16, 0, "Gerar Nº de Contrato");

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case 0: adicionaFormulario();
			return true;

		case 1:	removeFormulario();
			return true;
			
		case GerarContratoPadrao: verificaPermissaoEscritaEFazAlgumaAcao(GerarContratoPadrao);	
			return true;
			
		case GerarContratoContaSIM:	verificaPermissaoEscritaEFazAlgumaAcao(GerarContratoContaSIM);			
			return true;
			
		case SimularPrecos:	verificaSeNaoTemNumeroDeContratoEFazAlgumaAcao(SimularPrecos);	
			return true;

		case ConsultarCNPJ:	verificaPermissaoEscritaEFazAlgumaAcao(ConsultarCNPJ);
			return true;

		case ConsultarInscricaoEstadual: verificaPermissaoEscritaEFazAlgumaAcao(ConsultarInscricaoEstadual);
			return true;
						
		case InformarPecas: verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(InformarPecas);	
			return true;
	
		case FOTO_CNPJ: verificaPermissaoLeituraEFazAlgumaAcao(FOTO_CNPJ);
			return true;
		
		case FOTO_PAC: verificaPermissaoLeituraEFazAlgumaAcao(FOTO_PAC);
			return true;

		case FOTO_CPF: verificaPermissaoLeituraEFazAlgumaAcao(FOTO_CPF);	
			return true;

		case FOTO_RG: verificaPermissaoLeituraEFazAlgumaAcao(FOTO_RG);		
			return true;
			
		case FOTO_CONTRATO: verificaPermissaoLeituraEFazAlgumaAcao(FOTO_CONTRATO);	
			return true;

		case FOTOS_DIVERSAS: verificaPermissaoLeituraEFazAlgumaAcao(FOTOS_DIVERSAS);			
			return true;

		case SimularInstalacao: verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(SimularInstalacao);
			return true;

		case AnexarPDF: verificaPermissaoLeituraEFazAlgumaAcao(AnexarPDF);		
			return true;

		case VISUALIZAR_ARQUIVOS_GERADOS: verificaPermissaoLeituraEFazAlgumaAcao(VISUALIZAR_ARQUIVOS_GERADOS);
			return true;

		case GerarNumeroDeContrato: verificaSeNaoTemNumeroDeContratoEFazAlgumaAcao(GerarNumeroDeContrato);
			return true;

		}
		
		 return super.onOptionsItemSelected(item);
		//return false;
	}

	private void verificaPermissaoEscritaEFazAlgumaAcao(int acao){

		if (Build.VERSION.SDK_INT >= 23) {
			
			if (permitiuEscrever()){
				
				verificaSeNaoTemNumeroDeContratoEFazAlgumaAcao(acao);
			}		
	    } 
		else {
		
			verificaSeNaoTemNumeroDeContratoEFazAlgumaAcao(acao);
	    }
		   
	}
	
	@SuppressLint("NewApi")
	public boolean permitiuEscrever(){
        
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            
        	requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUISICAO_PERMISSAO_ESCRITA);		      
 		
            return false;
        }
        return true;
    }

	private void verificaPermissaoLeituraEFazAlgumaAcao(int acao){

		if (Build.VERSION.SDK_INT >= 23) {
			
			if (permitiuLer()){
				 
				verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(acao);
			}		
	    } 
		else {
			verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(acao);
	    }
		   
	}
	
	@SuppressLint("NewApi")
	private boolean permitiuLer(){
	    
		if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUISICAO_PERMISSAO_LEITURA);		      
 	        
            return false;
        }
        return true;
    }
	
	private void verificaSeNaoTemNumeroDeContratoEFazAlgumaAcao(int acao) {
					
			if (contratoUtil.naoTemNumeroDeContrato(movimento1.getNr_visita())) {
				
				verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(acao);
			} 
			else {	
				new MeuAlerta("Contrato Já foi Finalizado", null, context).meuAlertaOk();
			}	
	}
		
	private void verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(int acao) {

						if (contratoUtil.layoutsObrigatoriosForamPreenchidos(listaComlayouts_Obrigatorios, movimento1)) {
	
							Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
																							Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
																							Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());

							String srcContrato = contratoUtil.usaRazaoSocialComCPF_CNPJ(mov_informacoesCliente.getInformacao_1(), mov_informacoesCliente.getInformacao_4());

							switch (acao) {

							case GerarContratoPadrao:
								geraContrato("ContratoPadrao", ActivityContratoPadrao.class, mov_informacoesCliente, srcContrato);
								break;
								
							case GerarContratoContaSIM:
								geraContrato("ContratoContaSIM", ActivityContratoContaSIM.class, mov_informacoesCliente, srcContrato);
								break;
								
							case FOTO_CONTRATO: buscarArquivo("Contrato.jpg");
								break;
								
							case FOTO_CNPJ: buscarArquivo("CNPJ.jpg");								
								break;
							
							case FOTO_PAC: buscarArquivo("PAC.jpg");
								break;
							
							case FOTO_CPF: buscarArquivo("CPF.jpg");
								break;
								
							case FOTO_RG: buscarArquivo("RG.jpg");
								break;
								
							case FOTOS_DIVERSAS: buscarArquivo("Foto_0.jpg");
								break;
							
							case AnexarPDF: buscarArquivo("Doc_0.pdf");
								break;
								
							case SimularInstalacao: chamaAplicativoFotoshop();
								break;
								
							case SimularPrecos: abrirSimulador();	
								break;

							case InformarPecas: irParaActivityPecasNew();
								break;
						
							case ConsultarInscricaoEstadual: abrirWebview_InscricaoEstadual();
								break;
								
							case VISUALIZAR_ARQUIVOS_GERADOS: visualizarArquivosGerados();
								break;
	
							case ConsultarCNPJ: abrirWebview_Cnpj();
								break;
								
							case GerarNumeroDeContrato: solicitaConfirmacaoParaPoderGerarNumeroDeContrato(mov_informacoesCliente);
								break;
							}				
						}
	}
	
	private void buscarArquivo(String nomeDoArquivo) {
		
		nomeDoArquivoSelecionado = nomeDoArquivo;
		
		if(formularioEquipamentosSimuladosFoiPreenchido()) {
				  
			if(nomeDoArquivoSelecionado.contains(".jpg")) {
				
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);	
				startActivityForResult(Intent.createChooser(intent, "Selecione um arquivo:"), REQUISICAO_BUSCA_FOTO);				
			}else {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");            
				startActivityForResult(Intent.createChooser(intent, "Selecione um arquivo:"), REQUISICAO_BUSCA_ARQUIVO);					
			}	
		}
	}

	private void visualizarArquivosGerados() {
		
		String srcContrato = contratoUtil.devolveDiretorioAserUtilizado(movimento1.getNr_visita());
		
		CaminhoArquivo caminhoArquivo = new CaminhoArquivo(context);
					   caminhoArquivo.mostraListaDeArquivos(srcContrato);
	}
		
	private void solicitaConfirmacaoParaPoderGerarNumeroDeContrato(final Movimento mov_informacoesCliente){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setMessage("Deseja Gerar Contrato?")
				.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						renomeiaPastaDoCliente(mov_informacoesCliente);
						
					}
				}).setNegativeButton("Não", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		alertDialog.setCancelable(false);
		alertDialog.show();
	}
		
	private void renomeiaPastaDoCliente(Movimento mov_informacoesCliente) {
		
		if(formularioEquipamentosSimuladosFoiPreenchido()) {
			
			String srcContrato = contratoUtil.usaRazaoSocialComCPF_CNPJ(mov_informacoesCliente.getInformacao_1(), 
																		mov_informacoesCliente.getInformacao_4());
			
			try {
	
				File pastaAntigaCPFCNPJ = new File(srcContrato);
			    if (!pastaAntigaCPFCNPJ.exists()) {	    	
			    	 pastaAntigaCPFCNPJ.mkdirs();
			    }

			    String numeroContrato = contratoUtil.criaNumeroDeContrato();
				
				File pastaNovaNumeroContrato = new File(srcContrato.replace(mov_informacoesCliente.getInformacao_1()
														   			   +"_"+mov_informacoesCliente.getInformacao_4().replace("/", "-"),
														   			    "_"+numeroContrato));
				
				pastaAntigaCPFCNPJ.renameTo(pastaNovaNumeroContrato);
		
				ArrayList<Movimento> listaTodosMovimentos = dao.devolveListaComMovimentosPopulados(mov_informacoesCliente);
				for (Movimento movimento : listaTodosMovimentos) {
					
					contratoUtil.addNumeroDeContratoEAtualizaMovimento(movimento, numeroContrato);	
				}
				
				new MeuAlerta("Número de contrato gerado:", numeroContrato, context).meuAlertaOk();
			} 
			catch (Exception erro) {
				new MeuAlerta(""+erro, null, context).meuAlertaOk();
			}
		}	
	}
	
	private void irParaActivityPecasNew() {
			
			Dao dao = new Dao(context);

			Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
														Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
														Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());

			Intent intent = new Intent(context, ActivityPecasNew.class);
							 Bundle bundle = new Bundle();
									bundle.putSerializable("mov_informacoesCliente", mov_informacoesCliente);
				   intent.putExtras(bundle);
				   
			startActivity(intent);		
	}

	
	private ScrollView constroiTela() {

		layoutParams_MATCH_MATCH = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParams_MATCH_WRAP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		ScrollView scrollView = new ScrollView(context);
		scrollView.setLayoutParams(layoutParams_MATCH_MATCH);

		linearLayout_obrigatorioDaScrollView = telaBuilder.cria_LL(layoutParams_MATCH_MATCH, Generico.SEM_COR.getValor());

		linearLayout_telaParaAsLabels = telaBuilder.cria_LL(layoutParams_MATCH_WRAP, Generico.SEM_COR.getValor());

		linearLayout_telaParaOsFormularios = telaBuilder.cria_LL(layoutParams_MATCH_WRAP, Generico.SEM_COR.getValor());

		scrollView.addView(linearLayout_obrigatorioDaScrollView);

		linearLayout_obrigatorioDaScrollView.addView(linearLayout_telaParaAsLabels);
		linearLayout_obrigatorioDaScrollView.addView(linearLayout_telaParaOsFormularios);

		return scrollView;
	}

	private void populaTodasAsListasEcriaLayoutDinamicamente() {

		for (Layout layoutObrigatorio : listaComlayouts_Obrigatorios) {

			if(layoutObrigatorio.getNr_layout() != NomeLayout.REPRESENTACAO.getNumero()) {
				
				criaLayoutDinamicamente(Generico.NR_PROGRAMACAO.getValor(), layoutObrigatorio.getNr_layout(), layoutObrigatorio.getObrigatorio());
			}
			
		}

		listaComNrLayoutNaoRepetidos = new ArrayList<Integer>();

		listaComLayouts_NAO_Obrigatorio = dao.listaTodaTabela(Layout.class, 
															  Layout.COLUMN_INTEGER_IND_TIP_LAYOUT, TipoView.LAYOUT_FORMULARIO.getValor(), 
															  Layout.COLUMN_INTEGER_OBRIGATORIO, Generico.LAYOUT_OBRIGATORIO_NAO.getValor());

		listaComMovimentos_NAO_obrigatorios = dao.listaTodosMovimentos_NAO_obrigatorios(Movimento.class, movimento1.getNr_visita());

		for (Movimento movimento : listaComMovimentos_NAO_obrigatorios) {
			
			if (movimento.getNr_layout() != NomeLayout.SIMULADOR_DATASUL.getNumero() && movimento.getNr_layout() != NomeLayout.SIMULADOR_ANA.getNumero()) {
				
				criaLayoutDinamicamente(movimento.getNr_programacao(), movimento.getNr_layout(), Generico.LAYOUT_OBRIGATORIO_NAO.getValor());
			}
		}

	}

	private void criaLayoutDinamicamente(int nrProgramacao, int nrLayout, int layoutObrigatorio) {

		final int nrProgrComNrForm = Integer.valueOf(nrProgramacao + "" + nrLayout);

		Dao dao = new Dao(context);

		int indTipLayout = dao.selectDistinct_indTipLayout(Layout.class, nrLayout);

		if (indTipLayout == TipoView.LAYOUT_CONSULTA.getValor()) {

			linearLayout_telaParaAsLabels.addView(criaLinearLayoutTitulo(nrProgrComNrForm, nrLayout, getResources().getColor(R.color.tabs), getResources().getColor(R.color.tabs)));
			linearLayout_telaParaAsLabels.addView(criaLinearLayoutAquiVaoOsFragsHolders(nrProgrComNrForm, nrLayout, TipoView.LAYOUT_CONSULTA.getValor()));
			linearLayout_telaParaAsLabels.addView(criaViewDivisoria(nrProgrComNrForm));

		} else {

			if (layoutObrigatorio == 1) {

				linearLayout_telaParaOsFormularios.addView(criaLinearLayoutTitulo(nrProgrComNrForm, nrLayout, getResources().getColor(R.color.vermelho), getResources().getColor(R.color.Verde)));
				linearLayout_telaParaOsFormularios.addView(criaLinearLayoutAquiVaoOsFragsHolders(nrProgrComNrForm, nrLayout, TipoView.LAYOUT_FORMULARIO.getValor()));
				linearLayout_telaParaOsFormularios.addView(criaViewDivisoria(nrProgrComNrForm));
				
				linearLayout_obrigatorioDaScrollView.requestFocus(View.FOCUS_DOWN);
			} else {
				linearLayout_telaParaOsFormularios.addView(criaLinearLayoutTitulo(nrProgrComNrForm, nrLayout, getResources().getColor(R.color.plano_de_fundo_layout), getResources().getColor(R.color.Verde)));
				linearLayout_telaParaOsFormularios.addView(criaLinearLayoutAquiVaoOsFragsHolders(nrProgrComNrForm, nrLayout, TipoView.LAYOUT_FORMULARIO.getValor()));
				linearLayout_telaParaOsFormularios.addView(criaViewDivisoria(nrProgrComNrForm));
				
				linearLayout_obrigatorioDaScrollView.requestFocus(View.FOCUS_DOWN);

			}
		}
	}
	
	private void chamaAplicativoFotoshop() {
		
		String srcContrato = contratoUtil.devolveDiretorioAserUtilizado(movimento1.getNr_visita());
	    
		File diretorioDestino = new File(srcContrato);
	    if (!diretorioDestino.exists()) {	    	
	    	 diretorioDestino.mkdirs();
	    }

		
		Dao dao = new Dao(context);

		Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
																		 Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
																		 Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());


		ChamaAplicativo chamaAplicativo = new ChamaAplicativo();
	
		if (contratoUtil.naoTemNumeroDeContrato(movimento1.getNr_visita())) {
			
			chamaAplicativo.chamaOApp(context, "br.com.extend.fotoshop", "nomeEmpresa_cnpj", ""+mov_informacoesCliente.getInformacao_1()+"_"+mov_informacoesCliente.getInformacao_4().replace("/", "-"));	
		} else {
			chamaAplicativo.chamaOApp(context, "br.com.extend.fotoshop", "nomeEmpresa_cnpj", "_"+mov_informacoesCliente.getNr_contrato());
		}
	}

	private void abrirSimulador() {

		Intent intent = new Intent(FragActivityOcorrencia.this, ActivitySimulador.class);
		Bundle bundle = new Bundle();	
		bundle.putSerializable("movimento", movimento1);    
		intent.putExtras(bundle);	
		startActivityForResult(intent, REQUISICAO_SIMULADOR);
	}

	private void abrirWebview_Cnpj() {

			Intent intent = new Intent(FragActivityOcorrencia.this, WebViewCNPJ.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("movimento", movimento1);
			intent.putExtras(bundle);
			//startActivityForResult(intent, 444);
			startActivity(intent);
	}

	private void abrirWebview_InscricaoEstadual() {

			Intent intent = new Intent(FragActivityOcorrencia.this, WebviewInscricaoEstadual.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("movimento", movimento1);
			intent.putExtras(bundle);
			//startActivityForResult(intent, 444);
			startActivity(intent);
	}
	
	private void geraContrato(String nomeDoContrato, Class<? extends Activity> activityAserChamada, Movimento mov_informacoesCliente, String srcContrato) {

		if(formularioEquipamentosSimuladosFoiPreenchido()) {
					
			ArrayList<Movimento> listaTodosMovimentos = dao.devolveListaComMovimentosPopulados(mov_informacoesCliente);
			Intent intent = new Intent(FragActivityOcorrencia.this, activityAserChamada);
			intent.putExtra("" + Tag.srcContrato, srcContrato + nomeDoContrato);
			intent.putExtra("" + Tag.listaComMovimentos, listaTodosMovimentos);
			startActivity(intent);
		}
	}

	private boolean formularioEquipamentosSimuladosFoiPreenchido() {
		
		Movimento mov_equipamentosSimulados = (Movimento) dao.devolveObjeto(Movimento.class,
																Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.SIMULADOR_FER.getNumero(),
																Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());

		if(mov_equipamentosSimulados == null) {
			
			contratoUtil.avisaQformularioObrigatorioNaoFoiPreenchido(NomeLayout.SIMULADOR_FER.getNumero());
			
			return false;
		}else {
			return true;
		}
		
	}
				
	private void adicionaFormulario() {

		listaComNrLayoutNaoRepetidos.clear();

		for (Layout layout : listaComLayouts_NAO_Obrigatorio) {

			listaComNrLayoutNaoRepetidos.add(layout.getNr_layout());
		}

		listaComMovimentos_NAO_obrigatorios.clear();
		listaComMovimentos_NAO_obrigatorios = dao.listaTodosMovimentos_NAO_obrigatorios(Movimento.class, movimento1.getNr_visita());

		for (Movimento movimento : listaComMovimentos_NAO_obrigatorios) {

			int x = listaComNrLayoutNaoRepetidos.indexOf(movimento.getNr_layout());

			if (x != -1) {
				listaComNrLayoutNaoRepetidos.remove(x);
			}

		}

		final CharSequence[] items = new CharSequence[listaComNrLayoutNaoRepetidos.size()];

		for (int i = 0; i < listaComNrLayoutNaoRepetidos.size(); i++) {

			Layout form = (Layout) dao.devolveObjeto(Layout.class, Layout.COLUMN_INTEGER_NR_LAYOUT, listaComNrLayoutNaoRepetidos.get(i));

			items[i] = form.getDescricao();
		}

		final boolean[] selecionados = new boolean[listaComNrLayoutNaoRepetidos.size()];

		for (int i = 0; i < listaComNrLayoutNaoRepetidos.size(); i++) {

			selecionados[i] = false;
		}

		chamaListaMultiplaEscolha("Adicionar", items, selecionados);
	}

	private void removeFormulario() {

		// listaComMovimentos_NAO_obrigatorios.clear();
		listaComMovimentos_NAO_obrigatorios = dao.listaTodosMovimentos_NAO_obrigatorios(Movimento.class,
				movimento1.getNr_visita());
		
		if (!listaComMovimentos_NAO_obrigatorios.isEmpty()) {
			final CharSequence[] items = new CharSequence[listaComMovimentos_NAO_obrigatorios.size()];

			for (int i = 0; i < listaComMovimentos_NAO_obrigatorios.size(); i++) {
				Movimento movimento = new Movimento();
				movimento = listaComMovimentos_NAO_obrigatorios.get(i);

				Layout form = (Layout) dao.devolveObjeto(Layout.class, Layout.COLUMN_INTEGER_NR_LAYOUT,
						movimento.getNr_layout());

				items[i] = form.getDescricao();

			}

			final boolean[] selecionados = new boolean[listaComMovimentos_NAO_obrigatorios.size()];
			int listaComMov = listaComMovimentos_NAO_obrigatorios.size() - 1;
			for (int i = 0; i < listaComMov; i++) {

				selecionados[i] = false;
			}

			chamaListaMultiplaEscolha("Remover", items, selecionados);
		} else {
			new MeuAlerta("Não há formularios a serem removidos!", null, context).meuAlertaOk();
		}
	}

	private void chamaListaMultiplaEscolha(final String titulo, final CharSequence[] items, final boolean[] selecionados) {

		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setTitle(titulo);
		builder1.setMultiChoiceItems(items, selecionados, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int item, boolean boolea) {
				// Log.i("Myactivity", "items[item]: "+ items[item] +" boolea:
				// "+ boolea);
			}

		});
		builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				final Dao dao = new Dao(context);

				for (int i = 0; i < selecionados.length; i++) {

					boolean selecionado = selecionados[i];

					if (selecionado) {

						final int nrLayout = dao.selectDistinct_nrLayout(Layout.class, items[i].toString());

						Movimento movimento = new Movimento();							
								  movimento.setNr_programacao(movimento1.getNr_programacao());
								  movimento.setNr_visita(movimento1.getNr_visita());
								  movimento.setNr_layout(nrLayout);
									
							if (movimento1.getNr_contrato() != null && !movimento1.getNr_contrato().equals("")) {
								
								movimento.setNr_contrato(movimento1.getNr_contrato());
							}
							
							if (titulo.equals("Adicionar")) {

								if(nrLayout == NomeLayout.REPRESENTACAO.getNumero()) {
										
									Layout layout = (Layout) dao.devolveObjeto(Layout.class, Layout.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.REPRESENTACAO.getNumero());
									
									listaComlayouts_Obrigatorios.add(layout);
										
									movimento.setInformacao_50(ADICIONOU_LAYOUT_REPRESENTACAO);
									
									
									if(contratoUtil.naoTemNumeroDeContrato(movimento1.getNr_visita()) ) {
						
										insereMovimentoECriaLayoutDinamicamente(movimento);
									}else {
										new MeuAlerta("Contrato Já foi Finalizado", null, context).meuAlertaOk();
									}
								}else {
									insereMovimentoECriaLayoutDinamicamente(movimento);			
								}
								
							}

							if (titulo.equals("Remover")) {
								
								perguntaSeConfirmaRemocao(movimento, items[i].toString());
							}
						
					}

				}
			}
		});
		builder1.show();

	}
	
	private void insereMovimentoECriaLayoutDinamicamente(Movimento movimento){
		
		dao.insereObjeto_final(movimento);
		
		criaLayoutDinamicamente(movimento.getNr_programacao(), movimento.getNr_layout(), Generico.LAYOUT_OBRIGATORIO_NAO.getValor());
	}
	
	private void perguntaSeConfirmaRemocao(final Movimento movimento, String nomelayout) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("Confirma exclusão de toda informação referente ao formulário "+nomelayout+ " ?")
				.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
		
						
							for(Layout layout : listaComlayouts_Obrigatorios) {
								
								 if(layout.getNr_layout() == NomeLayout.REPRESENTACAO.getNumero()){

									 listaComlayouts_Obrigatorios.remove(layout);

							         break;
								 }
							}

						dao.deletaObjeto(Movimento.class,
										 Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita(),
										 Movimento.COLUMN_INTEGER_NR_LAYOUT, movimento.getNr_layout());

						final int nrProgrComNrForm = Integer.valueOf(movimento.getNr_programacao() + "" + movimento.getNr_layout());

						GetChildCount utilitarios = new GetChildCount(linearLayout_obrigatorioDaScrollView);
									  utilitarios.removeTituloHolderDivisoria(nrProgrComNrForm);
						
						
					}
				}).setNegativeButton("Não", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		builder.show();

	}
	
	private LinearLayout criaLinearLayoutTitulo(final int nrProgrComNrForm, int nrLayout, int color, int color2) {

		LinearLayout linearLayout_titulo = new LinearLayout(context);
		linearLayout_titulo.setTag("llTitulo" + nrProgrComNrForm);
		linearLayout_titulo.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout_titulo.setLayoutParams(layoutParams_MATCH_WRAP);
		linearLayout_titulo.setBackgroundColor(color);
		linearLayout_titulo.addView(criaTextViewTitulo(nrLayout));
		final ImageView imageView_seta = criaImageViewSeta();
		linearLayout_titulo.addView(imageView_seta);
		// linearLayout_titulo.requestFocus(View.FOCUS_DOWN);
		
		//textFile = new TextView(context);
		
		if (nrLayout != 25) {
			
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

			FragConteudoFormularioHolder fragCFH = (FragConteudoFormularioHolder) fragmentManager
					.findFragmentByTag("fragCFH" + nrProgrComNrForm);
			fragmentTransaction.attach(fragCFH);
			// fragmentTransaction.commit();

		}
		linearLayout_titulo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

				FragConteudoFormularioHolder fragCFH = (FragConteudoFormularioHolder) fragmentManager
						.findFragmentByTag("fragCFH" + nrProgrComNrForm);

				if (fragCFH.isVisible()) {

					fragmentTransaction.detach(fragCFH);

					imageView_seta.setImageResource(R.drawable.seta_baixo);
				} else {
					fragmentTransaction.attach(fragCFH);

					imageView_seta.setImageResource(R.drawable.seta_cima);
				}
				fragmentTransaction.commit();
			}

		});

		return linearLayout_titulo;

	}

	private LinearLayout criaLinearLayoutAquiVaoOsFragsHolders(int nrProgrComNrForm, int nrLayout, int tipoLayout) {

		LinearLayout linearLayout_aquiVaoOsFragsHolders = telaBuilder.cria_LL(layoutParams_MATCH_MATCH, Generico.SEM_COR.getValor());
		linearLayout_aquiVaoOsFragsHolders.setId(nrProgrComNrForm);
		linearLayout_aquiVaoOsFragsHolders.setTag("llFragHolder" + nrProgrComNrForm);

		fragConteudoFormularioHolder = new FragConteudoFormularioHolder(fragmentManager, movimento1, nrLayout, tipoLayout);
		
		if (nrLayout != NomeLayout.ENDERECO_ENTREGA.getNumero() && tipoLayout != TipoView.LAYOUT_CONSULTA.getValor()) {
			
			fragmentManager.beginTransaction().add(linearLayout_aquiVaoOsFragsHolders.getId(), fragConteudoFormularioHolder, "fragCFH" + nrProgrComNrForm).attach(fragConteudoFormularioHolder).commit();
		} else {
			fragmentManager.beginTransaction().add(linearLayout_aquiVaoOsFragsHolders.getId(), fragConteudoFormularioHolder, "fragCFH" + nrProgrComNrForm).detach(fragConteudoFormularioHolder).commit();
		}

		return linearLayout_aquiVaoOsFragsHolders;
	}

	private View criaViewDivisoria(int nrProgrComNrForm) {

		View viewDivisoria = new View(context);
		viewDivisoria.setTag("viewDivisoria" + nrProgrComNrForm);
		viewDivisoria.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 5));
		viewDivisoria.setBackgroundColor(getResources().getColor(R.color.divisoria));

		return viewDivisoria;
	}

	private TextView criaTextViewTitulo(int nrFormulario) {

		Dao dao = new Dao(context);

		Layout form = (Layout) dao.devolveObjeto(Layout.class, Layout.COLUMN_INTEGER_NR_LAYOUT, nrFormulario);

		TextView textView_titulo = new TextView(context);
		textView_titulo.setTextSize(23);
		textView_titulo.setTextColor(getResources().getColor(R.color.Preto));
		textView_titulo.setText(Html.fromHtml("<b>" + form.getDescricao() + "</b>"));

		return textView_titulo;
	}

	private ImageView criaImageViewSeta() {

		ImageView imageView_seta = new ImageView(context);
		LinearLayout.LayoutParams imageView_params = new LinearLayout.LayoutParams(18, 18);
		imageView_params.gravity = Gravity.CENTER_VERTICAL;
		imageView_seta.setLayoutParams(imageView_params);
		imageView_seta.setImageResource(R.drawable.seta_baixo);

		return imageView_seta;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    
		if(intent != null) {
			
		    Uri caminhoDoArquivoEscolhido = intent.getData();
	
			if (requestCode == REQUISICAO_BUSCA_ARQUIVO) {
				
				if (resultCode == RESULT_OK) {
					buscaArquivoESalvaNoDiretorioDoCliente(caminhoDoArquivoEscolhido);		    
				}
			}
			
			if (resultCode != Activity.RESULT_CANCELED) {
				
				if (requestCode == REQUISICAO_BUSCA_FOTO) {
					buscaArquivoESalvaNoDiretorioDoCliente(caminhoDoArquivoEscolhido);
				}
			}
			
			if (resultCode == REQUISICAO_SIMULADOR) {
				destroiERecriaActivity();
			}
		}
	}
	
	private void destroiERecriaActivity() {
		finish();
		startActivity(getIntent());
	}
	private void buscaArquivoESalvaNoDiretorioDoCliente(Uri caminhoDoArquivoEscolhido) {
		
		String srcContrato = contratoUtil.devolveDiretorioAserUtilizado(movimento1.getNr_visita());
	    
	    TrabalhaComFotos trabalhaComFotos = new TrabalhaComFotos();

	    String diretorioOrigem = trabalhaComFotos.devolveDiretorioDoArquivoSelecionado(context, caminhoDoArquivoEscolhido);	
	   
		File diretorioDestino = new File(srcContrato);
	    if (!diretorioDestino.exists()) {	    	
	    	 diretorioDestino.mkdirs();
	    }
	    
	    String nomeDoArquivoAUtilizar = nomeDoArquivoSelecionado;
	    
	    if(nomeDoArquivoSelecionado.contains("Doc_")) {
	    	
	    	nomeDoArquivoAUtilizar = devolveUltimoArquivoDocSalvo(srcContrato, nomeDoArquivoSelecionado);
	    }
	    if(nomeDoArquivoSelecionado.contains("Foto_")) {
	    	
	    	nomeDoArquivoAUtilizar = devolveUltimoArquivoFotoSalvo(srcContrato, nomeDoArquivoSelecionado);
	    }
	    
	    try {
	    	if(nomeDoArquivoAUtilizar.contains(".jpg")) {
	    		
	    		Bitmap bitmap = Diminui_MB_imagens.decodeSampledBitmapFromPicturePath(diretorioOrigem, 200, 200);
	    		trabalhaComFotos.escreveNoBitmap(bitmap, diretorioDestino+"/"+nomeDoArquivoAUtilizar);
	    		new MeuAlerta("Foto salva!", null, context).meuAlertaOk();
	    	}
	    	else if(nomeDoArquivoAUtilizar.contains(".pdf")) {
		    	trabalhaComFotos.escreveNoArquivo(diretorioOrigem, srcContrato, nomeDoArquivoAUtilizar);
	    		new MeuAlerta("Arquivo salvo!", null, context).meuAlertaOk();
	    	}
	    	else {	    		
	    		new MeuAlerta("Arquivo não suportado", null, context).meuAlertaOk();
	    	}
			    	
		} 
		catch (Exception erro) {
			 erro.printStackTrace();
			 new MeuAlerta("Arquivo invalido", null, context).meuAlertaOk();
		}
	}
	
	private String devolveUltimoArquivoDocSalvo(String srcContrato, String nomeDoArquivoSelecionado) {
	    String[] listaParaPegarNumero = new String[3];
		CaminhoArquivo caminhoArquivo = new CaminhoArquivo(context);
		ArrayList<CaminhoArquivo> lista = caminhoArquivo.populaListaComNomeDeArquivosBaseadoEmDiretorio(srcContrato);
		boolean achouAlgumArquivoDoc = false;
			for(CaminhoArquivo cArquivo : lista) {
				if(cArquivo.getArquivo().contains("Doc_")) {
					listaParaPegarNumero = desmontaStringEProcuraNumero(cArquivo.getArquivo());
					achouAlgumArquivoDoc = true;
				}		
			}
		if(!achouAlgumArquivoDoc) {
			listaParaPegarNumero = desmontaStringEProcuraNumero(nomeDoArquivoSelecionado);						
		}
		return montaStringComNumeroSomado(listaParaPegarNumero);
	}
	private String devolveUltimoArquivoFotoSalvo(String srcContrato, String nomeDoArquivoSelecionado) {
	    String[] listaParaPegarNumero = new String[3];
		CaminhoArquivo caminhoArquivo = new CaminhoArquivo(context);
		ArrayList<CaminhoArquivo> lista = caminhoArquivo.populaListaComNomeDeArquivosBaseadoEmDiretorio(srcContrato);
		boolean achouAlgumArquivoDoc = false;
			for(CaminhoArquivo cArquivo : lista) {
				if(cArquivo.getArquivo().contains("Foto_")) {
					listaParaPegarNumero = desmontaStringEProcuraNumero(cArquivo.getArquivo());
					achouAlgumArquivoDoc = true;
				}		
			}
		if(!achouAlgumArquivoDoc) {
			listaParaPegarNumero = desmontaStringEProcuraNumero(nomeDoArquivoSelecionado);						
		}
		return montaStringComNumeroSomado(listaParaPegarNumero);
	}
	private String montaStringComNumeroSomado(String[] lista) {
   		int numeroEncontrado = Integer.parseInt(lista[1]);
		int numeroAserUtilizado = numeroEncontrado + 1;
		return lista[0]+String.valueOf(numeroAserUtilizado)+lista[2];
	}
	private String[] desmontaStringEProcuraNumero(String nomeArquivo) {
		String[] lista = new String[3];
		if(nomeArquivo.contains("_")) {
			int posicaoDoUnderline = nomeArquivo.indexOf("_");
			String ladoEsquerdo = nomeArquivo.substring(0, posicaoDoUnderline+1);
			int tamanhoTotal = nomeArquivo.length();
			String numeroEncontrado = nomeArquivo.substring(posicaoDoUnderline+1, tamanhoTotal-4);
			int posicaoDoPonto = nomeArquivo.indexOf(".");
			String ladoDireito = nomeArquivo.substring(posicaoDoPonto, tamanhoTotal);
			lista[0] = ladoEsquerdo;
			lista[1] = numeroEncontrado;
			lista[2] = ladoDireito;
		}
		return lista;
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(context, ActivityListaClientes.class));
		finish();
	}
	
}
