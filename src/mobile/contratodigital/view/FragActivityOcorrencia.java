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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Files;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
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
import mobile.contratodigital.util.StatusMemoria;
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
	private String srcContratos;
	private String nomearquivo;
	private int nr = 0;
	private TextView textFile;
	public static final int REQUISICAO_BUSCA_FOTO = 1234;
	public static final int REQUISICAO_PERMISSAO_TIRAR_FOTO = 111;
	public static final int REQUISICAO_PERMISSAO_LEITURA = 222;
	public static final int REQUISICAO_PERMISSAO_ESCRITA = 333;
	private static final int PICKFILE_RESULT_CODE = 33333;
	public static final int REQUISICAO_TIRAR_FOTO = 666;
	
	//private String numeroContrato;
	//private List<Movimento> listaTodosMovimentos;
	//private static final int AdicionarFormulario = 0;
	//private static final int RemoverFormulário = 1;
	
	private static final int GerarContratoPadrao = 2;
	private static final int GerarContratoContaSIM = 3;
	private static final int SimularPrecos = 4;
	private static final int ConsultarCNPJ = 5;
	private static final int ConsultarInscricaoEstadual = 6;
	
	private static final int InformarPecas = 7;
	private static final int InformarPecasNew = 27;
	
	private static final int FotoCNPJ = 8;
	private static final int FotoCPF = 9;
	private static final int FotoRG = 10;
	private static final int FotoContrato = 11;
	private static final int AdicionarFotos = 12;
	private static final int SimularInstalacao = 13;
	private static final int AnexarPDF = 14;
	private static final int VisualizarArquivosGerados = 15;
	private static final int GerarNumeroDeContrato = 16;
	private ContratoUtil contratoUtil;
	private static final String ADICIONOU_LAYOUT_REPRESENTACAO = "SIM";
	
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

		
		
		contratoUtil = new ContratoUtil(dao, movimento1.getNr_visita());
		
		
		setContentView(constroiTela());
		
		populaTodasAsListasEcriaLayoutDinamicamente();
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 0, 0, "Adicionar Formulário");
		menu.add(0, 1, 0, "Remover Formulário");
		menu.add(0, 2, 0, "Gerar Contrato Padrão");
		menu.add(0, 3, 0, "Gerar Contrato Conta SIM");
		menu.add(0, 4, 0, "Simular Preços");
		menu.add(0, 5, 0, "Consultar CNPJ");
		menu.add(0, 6, 0, "Consultar Inscrição Estadual");
		menu.add(0, 7, 0, "Informar Peças");
		menu.add(0, 8, 0, "Foto CNPJ");
		menu.add(0, 9, 0, "Foto CPF");
		menu.add(0, 10, 0, "Foto RG");
		menu.add(0, 11, 0, "Foto Contrato");
		menu.add(0, 12, 0, "Adicionar Fotos");
		menu.add(0, 13, 0, "Simular Instalação");
		menu.add(0, 14, 0, "Anexar pdf");
		menu.add(0, 15, 0, "Visualizar Arquivos Gerados");
		menu.add(0, 16, 0, "Gerar Nº de Contrato");
		menu.add(0, 27, 0, "Informar Peças New");

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

		case ConsultarCNPJ:	verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(ConsultarCNPJ);
			return true;

		case ConsultarInscricaoEstadual: verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(ConsultarInscricaoEstadual);
			return true;
						
		case InformarPecas: verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(InformarPecas);	
			return true;
		case InformarPecasNew: verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(InformarPecasNew);	
		return true;

		case FotoCNPJ: verificaPermissaoLeituraEFazAlgumaAcao(FotoCNPJ);
			return true;

		case FotoCPF: verificaPermissaoLeituraEFazAlgumaAcao(FotoCPF);	
			return true;

		case FotoRG: verificaPermissaoLeituraEFazAlgumaAcao(FotoRG);		
			return true;
			
		case FotoContrato: verificaPermissaoLeituraEFazAlgumaAcao(FotoContrato);	
			return true;

		case AdicionarFotos: verificaPermissaoLeituraEFazAlgumaAcao(AdicionarFotos);			
			return true;

		case SimularInstalacao: verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(SimularInstalacao);
			return true;

		case AnexarPDF: verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(AnexarPDF);		
			return true;

		case VisualizarArquivosGerados: verificaPermissaoLeituraEFazAlgumaAcao(VisualizarArquivosGerados);
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
	
	@SuppressLint("NewApi")
	private boolean permitiuEscrever(){
        
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUISICAO_PERMISSAO_ESCRITA);		      
 		
            return false;
        }
        return true;
    }

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
	 	 
		Log.i("tag", "onRequestPermissionsResult requestCode: " + requestCode);
		
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {	
	    	  
    	  if (requestCode == REQUISICAO_PERMISSAO_TIRAR_FOTO) {
	    	  
    		  //tirarFoto();  	  	    	
	      }
    	  
    	  if (requestCode == REQUISICAO_PERMISSAO_ESCRITA) {
   
    		  //salvarFoto();
    	  }
    	  
    	  if (requestCode == REQUISICAO_PERMISSAO_LEITURA) {
    			  
    		  //buscarFoto();		  
    	  }
    	  
	  }	 
	}

	private void verificaSeNaoTemNumeroDeContratoEFazAlgumaAcao(int acao) {
					
			if (contratoUtil.naoTemNumeroDeContrato()) {
				
				verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(acao);
			} 
			else {	
				new MeuAlerta("Contrato Já foi Finalizado", null, context).meuAlertaOk();
			}	
	}
		
	private void verificaSeLayoutsObrigatoriosForamPreenchidosEFazAlgumaAcao(int acao) {

						if (layoutsObrigatoriosForamPreenchidos()) {
	
							Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
																							Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
																							Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());

							srcContratos = usaRazaoSocialComCPF_CNPJ(mov_informacoesCliente);
							

							switch (acao) {

							case GerarContratoPadrao:
								geraContrato("ContratoPadrao", ActivityContratoPadrao.class, mov_informacoesCliente, srcContratos);
								break;
								
							case GerarContratoContaSIM:
								geraContrato("ContratoContaSIM", ActivityContratoContaSIM.class, mov_informacoesCliente, srcContratos);
								break;
								
							case FotoContrato:
								criaDiretorioComOuSemNumeroDeContrato();
								nomearquivo = "Contrato.jpg";
								buscarFoto(mov_informacoesCliente);
								break;
								
							case FotoCNPJ:
								criaDiretorioComOuSemNumeroDeContrato();
								nomearquivo = "Cnpj.jpg";
								//tirarFotoCnpj();
								buscarFoto(mov_informacoesCliente);
								break;
								
							case AdicionarFotos:
								criaDiretorioComOuSemNumeroDeContrato();
								nomearquivo = "foto1.jpg";
								buscarFoto(mov_informacoesCliente);
								break;
								
							case SimularPrecos :
								abrirSimulador();	
								break;
								
							case SimularInstalacao:
								criaDiretorioComOuSemNumeroDeContrato();
								chamaAplicativoFotoshop();
								break;
								
							case FotoCPF:
								criaDiretorioComOuSemNumeroDeContrato();
								nomearquivo = "CPF.jpg";
								buscarFoto(mov_informacoesCliente);
								break;
								
							case FotoRG:
								criaDiretorioComOuSemNumeroDeContrato();
								nomearquivo = "RG.jpg";
								buscarFoto(mov_informacoesCliente);
								break;

							case AnexarPDF:
								criaDiretorioComOuSemNumeroDeContrato();
								nomearquivo = "Doc_Cliente.pdf";
								buscarfile(mov_informacoesCliente);
								break;
								
							case InformarPecas: 		                 		
								irParaActivityPecas();
								break;
						
							case InformarPecasNew: 		                 		
								irParaActivityPecasNew();
								break;
						
							case ConsultarInscricaoEstadual:
								abrirWebview_InscricaoEstadual();
								break;
								
							case VisualizarArquivosGerados:
								mostraListaDeArquivos(mov_informacoesCliente);
								break;
	
							case ConsultarCNPJ :
								abrirWebview_Cnpj();
								break;
								
							case GerarNumeroDeContrato:
								solicitaConfirmacaoParaPoderGerarNumeroDeContrato(mov_informacoesCliente);
								break;
								
							}

				
				
						}
		
	}

	private boolean layoutsObrigatoriosForamPreenchidos() {

		int movimentosEncontrados = 0;
		
		for (Layout layout_obrigatorio : listaComlayouts_Obrigatorios) {
			
			if (layout_obrigatorio != null) {

				Movimento mov_obrigatorio = (Movimento) dao.devolveObjeto(Movimento.class,
																		  Movimento.COLUMN_INTEGER_NR_LAYOUT, layout_obrigatorio.getNr_layout(),
																		  Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());
			
					if (mov_obrigatorio == null) {
					
						avisaQformularioObrigatorioNaoFoiPreenchido(layout_obrigatorio.getNr_layout());
						break;
					}
					else {
						
						if(layout_obrigatorio.getNr_layout() == NomeLayout.REPRESENTACAO.getNumero()) {
							
							if(mov_obrigatorio.getInformacao_1().isEmpty()) {
								
								avisaQformularioObrigatorioNaoFoiPreenchido(layout_obrigatorio.getNr_layout());
								break;
							}
							else {
								movimentosEncontrados++;
							}
							
						}else {							
							movimentosEncontrados++;
						}
						
					} 
				
			}else {
				new MeuAlerta("Não foi encontrado Layout obrigatório", null, context).meuAlertaOk();
			}
		}
		
		
		if(movimentosEncontrados == listaComlayouts_Obrigatorios.size()){
			
			return true;
		}else {
			return false;
		}
		
	}
		
	private void solicitaConfirmacaoParaPoderGerarNumeroDeContrato(final Movimento mov_informacoesCliente){
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setMessage("Deseja Realmente Gerar Contrato?")
				.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						AtualizarContrato(mov_informacoesCliente);
						
					}
				}).setNegativeButton("Não", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		alertDialog.setCancelable(false);
		alertDialog.show();
	}
		
	private void AtualizarContrato(Movimento mov_informacoesCliente) {
		
		
		if(formularioEquipamentosSimuladosFoiPreenchido()) {
			
			String numeroContrato = GerarNumerodeContrato();
			
		String srcContrato = usaRazaoSocialComCPF_CNPJ(movimento1);
			
		try {

			File Antigo_caminho = new File(srcContrato);
			File novoCaminho = new File(srcContrato.replace(
					String.valueOf(movimento1.getInformacao_1() + "_" + movimento1.getInformacao_4().replace("/", "-")),
					"_" + numeroContrato));
			Antigo_caminho.renameTo(novoCaminho);

			String deletar = srcContrato.replace("_" + numeroContrato, String
					.valueOf(movimento1.getInformacao_1() + "_" + movimento1.getInformacao_4().replace("/", "-")));
				Runtime.getRuntime().exec("cmd /c net use k: \\\\NTI_X23\\C$\\Users /yes");
				
				Runtime.getRuntime().exec("cmd /c rd k:\\" + deletar + " /s /q");
				
				Runtime.getRuntime().exec("cmd /c net use k: /delete /yes");
		
		
			Dao dao = new Dao(context);

			ArrayList<Movimento> listaTodosMovimentos = dao.devolveListaComMovimentosPopulados(mov_informacoesCliente);
			
			int tamanho = listaTodosMovimentos.size();
			
			for (int i = 0; i < tamanho; i++) {
				
				Movimento movimento2 = new Movimento();
				
				movimento2 = listaTodosMovimentos.get(i);
				
				if(movimento2 != null) {
					
					movimento2.setNr_contrato(numeroContrato);

					preencheNoObjetoOcampoInformacao(movimento2, numeroContrato);
				
					insereMovimento(dao, movimento2);
				}
				
			}
			
			Antigo_caminho.renameTo(novoCaminho);
			
			
			new MeuAlerta("Número de contrato gerado: "+numeroContrato, null, context).meuAlertaOk();
		
		} 
		catch (IOException erro) {
			
			new MeuAlerta("Erro: "+erro, null, context).meuAlertaOk();

		}

			
			
			
			
			
		}	

		//}
	}

	private String GerarNumerodeContrato() {
		
		//if (layoutsObrigatoriosForamPreenchidos()) {

			Date data = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			String dataFormatada = dateFormat.format(data);

			Dao dao = new Dao(context);
			Representante representante = (Representante) dao.devolveObjeto(Representante.class);

			String numeroContrato = dataFormatada + representante.getCod_rep();
		//}
		return numeroContrato;

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		switch (requestCode) {
		
		case PICKFILE_RESULT_CODE:
			
			if (resultCode == RESULT_OK) {

				Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
																				Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
																				Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());
		
				if (contratoUtil.naoTemNumeroDeContrato()) {
					
					srcContratos = usaRazaoSocialComCPF_CNPJ(mov_informacoesCliente);
				} else {	
					srcContratos = usaNumeroDeContrato(mov_informacoesCliente);
				}
				
				
				String FilePath = intent.getData().getPath().replace("file/", "");
				textFile.setText(FilePath);
				File Antigo_caminho = new File(FilePath);
				// File novoCaminho = new File(FilePath.replace(FilePath., "_" +
				// Numero_contrato));

				//File src = new File(".");
				String dstPath = Environment.getExternalStorageDirectory() + srcContratos;
				File dst;

				dst = new File(dstPath + nomearquivo);
				try {
					InputStream in = new FileInputStream(Antigo_caminho);
					OutputStream out = new FileOutputStream(dst);
					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();
					Antigo_caminho.delete();
				} catch (Exception e) {

					//boolean deuErro = true;
					
					e.printStackTrace();

				}
				if (textFile.getText() != "") {

				}
			}
			break;
		}
		
		//if (resultCode == 444) {
			//finish();
			//startActivity(getIntent());
		//}
		
		if (resultCode != Activity.RESULT_CANCELED) {

			if (requestCode == REQUISICAO_BUSCA_FOTO) {

				new StatusMemoria().mostraStatusMemoria();

				buscaFotoEmostraNolinearLayout_tela(intent);

				new StatusMemoria().mostraStatusMemoria();
			}

			if (requestCode == REQUISICAO_TIRAR_FOTO) {

				new StatusMemoria().mostraStatusMemoria();

				buscaFotoEmostraNolinearLayout_tela(intent);

				new StatusMemoria().mostraStatusMemoria();
			}
		}
	}
	
	private String usaRazaoSocialComCPF_CNPJ(Movimento mov_informacoesCliente) {
		
		srcContratos = Environment.getExternalStorageDirectory()+"/ContratoDigital/"+mov_informacoesCliente.getInformacao_1()+"_"+mov_informacoesCliente.getInformacao_4().replace("/", "-")+"/";
		
		return srcContratos;
	}
	
	private String usaNumeroDeContrato(Movimento mov_informacoesCliente) {
		
		srcContratos = Environment.getExternalStorageDirectory() + "/ContratoDigital/"+"_"+mov_informacoesCliente.getNr_contrato()+"/";
		
		return srcContratos;
	}


	@Override
	public void onBackPressed() {
		
		// setResult(resultado, new Intent());

		startActivity(new Intent(this, ActivityListaClientes.class));

		finish();
	}

	private void irParaActivityPecas() {
		
		//if (layoutsObrigatoriosForamPreenchidos()) {

			Intent intent = new Intent(FragActivityOcorrencia.this, ActivityPecas.class);

			Bundle bundle = new Bundle();
			Dao dao = new Dao(context);

			Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
					Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
					Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());

			ArrayList<Movimento> listaTodosMovimentos = dao.devolveListaComMovimentosPopulados(mov_informacoesCliente);

			int tam = 0;
			int value = 0;
			int atua = 0;
			while (tam < listaTodosMovimentos.size()) {
				String movito = "";
				//String prog = "";
				String visit = "";
				if (listaTodosMovimentos.get(tam) != null) {
					movito = String.valueOf(listaTodosMovimentos.get(tam).getNr_layout());
					//prog = String.valueOf(listaTodosMovimentos.get(tam).getNr_programacao());
					visit = String.valueOf(listaTodosMovimentos.get(tam).getNr_visita());

				}
				if (movito.equals("668") & visit.equals(String.valueOf(mov_informacoesCliente.getNr_visita()))) {
					value = 1;
					atua = tam;
					tam = listaTodosMovimentos.size() + 1;
				}
				tam = tam + 1;
			}
			if (value == 1) {

				bundle.putSerializable("movimento2", listaTodosMovimentos.get(atua));
				bundle.putString("prova", "1");
				
				if (listaTodosMovimentos.get(atua).getNr_contrato().trim().equals("")) {
					
					bundle.putString("contrato", "");
				}
				if (listaTodosMovimentos.get(atua).getNr_contrato().trim().equals("")) {
					bundle.putString("contrato", listaTodosMovimentos.get(atua).getNr_contrato());
				}

			} else {
				bundle.putSerializable("movimento", mov_informacoesCliente);
				bundle.putString("prova", "2");

			}

			intent.putExtras(bundle);
			startActivityForResult(intent, 444);

		//}
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
			
			if (movimento.getNr_layout() != NomeLayout.DADOS_DATASUL.getNumero() && movimento.getNr_layout() != NomeLayout.SIMULADOR2.getNumero()) {
				
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

	
	/*
	private boolean formulario_INFORMACOES_CLIENTE_foiPreenchido2() {

		boolean layoutObrigatorioPreenchido = false;
		
		for (Layout layout_obrigatorio : listaComlayouts_Obrigatorios) {

			if (layout_obrigatorio != null) {

				if (layout_obrigatorio.getNr_layout() == NomeLayout.INFORMACOES_CLIENTE.getNumero()) {

					Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
																					 Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
																					 Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());

					if (mov_informacoesCliente != null) {
						
						layoutObrigatorioPreenchido = true;
					} 
					else {
						avisaQformularioObrigatorioNaoFoiPreenchido(layout_obrigatorio.getNr_layout());

						break;
					}
				}
			}
		}
		
		return layoutObrigatorioPreenchido;
	}
	*/
	
	private void chamaAplicativoFotoshop() {
		
		Dao dao = new Dao(context);

		Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
																		 Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
																		 Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());


		ChamaAplicativo chamaAplicativo = new ChamaAplicativo();
	
		if (contratoUtil.naoTemNumeroDeContrato()) {
			
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
		startActivityForResult(intent, 444);
	}

	private void abrirWebview_Cnpj() {
		
		//if (layoutsObrigatoriosForamPreenchidos()) {

			Intent intent = new Intent(FragActivityOcorrencia.this, WebViewCNPJ.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("movimento", movimento1);
			intent.putExtras(bundle);
			startActivityForResult(intent, 444);
		//}

	}

	private void abrirWebview_InscricaoEstadual() {
		
		//if (layoutsObrigatoriosForamPreenchidos()) {

			Intent intent = new Intent(FragActivityOcorrencia.this, Webview_Inscricao.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("movimento", movimento1);
			intent.putExtras(bundle);
			startActivityForResult(intent, 444);
		//}

	}


	private void avisaQformularioObrigatorioNaoFoiPreenchido(int nrLayout) {
		
		Layout layout = (Layout) dao.devolveObjeto(Layout.class, 
												   Layout.COLUMN_INTEGER_NR_LAYOUT, nrLayout);
		
		new MeuAlerta("Formulario ("+layout.getDescricao()+") precisa ser preenchido", null, context).meuAlertaOk();	
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
																Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.EQUIPAMENTOS_SIMULADOS.getNumero(),
																Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());

		if(mov_equipamentosSimulados == null) {
			
			avisaQformularioObrigatorioNaoFoiPreenchido(NomeLayout.EQUIPAMENTOS_SIMULADOS.getNumero());
			
			return false;
		}else {
			return true;
		}
		
	}
	
	private void mostraListaDeArquivos(Movimento mov_informacoesCliente) {
		
		String srcContrato = "";
		
		if (contratoUtil.naoTemNumeroDeContrato()) {
			
			srcContratos = usaRazaoSocialComCPF_CNPJ(mov_informacoesCliente);	
		} else {	
			srcContrato = usaNumeroDeContrato(mov_informacoesCliente);
		}

		ArrayList<CaminhoArquivo> lista = populaListaComNomeDeArquivosBaseadoEmDiretorio(srcContrato);

		if (lista.isEmpty()) {
			
			informaQnaoTemArquivos();
		} else {
			escolheApenasUmItemDaLista("Arquivos Gerados", lista);
		}
	}

	private void informaQnaoTemArquivos() {
	
		new MeuAlerta("Não contem arquivos", null, context).meuAlertaOk();	
	}

	private ArrayList<CaminhoArquivo> populaListaComNomeDeArquivosBaseadoEmDiretorio(String diretorioAserProcurado) {

		ArrayList<CaminhoArquivo> lista = new ArrayList<CaminhoArquivo>();

		File file2 = new File(diretorioAserProcurado);

		File[] listaComArquivos = file2.listFiles();

		if (listaComArquivos == null) {
			
			informaQnaoTemArquivos();
		} else {
			for (File arquivo : listaComArquivos) {

				String arq = arquivo.toString();

				if (arq.contains(".pdf")) {

					int posicaoDaUltimaBarra = arq.lastIndexOf("/");
					int tamanhoTotal = arq.length();

					String caminho = arq.substring(0, posicaoDaUltimaBarra + 1);
					String nomeDoArquivo = arq.substring(posicaoDaUltimaBarra + 1, tamanhoTotal);

					lista.add(new CaminhoArquivo(nomeDoArquivo, caminho));
				}
				if (arq.contains(".jpg")) {

					int posicaoDaUltimaBarra = arq.lastIndexOf("/");
					int tamanhoTotal = arq.length();

					String caminho = arq.substring(0, posicaoDaUltimaBarra + 1);
					String nomeDoArquivo = arq.substring(posicaoDaUltimaBarra + 1, tamanhoTotal);

					lista.add(new CaminhoArquivo(nomeDoArquivo, caminho));
				}
			}
		}
		return lista;
	}

	private void escolheApenasUmItemDaLista(String titulo, final ArrayList<CaminhoArquivo> lista) {

		ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.item_menu_geral, lista);

		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setTitle(titulo);
		builder1.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int posicao) {

				for (int i = 0; i < lista.size(); i++) {

					if (posicao == i) {
						
						//if (lista.get(i).getArquivo().contains(".pdf")) {
							
							CaminhoArquivo caminhoArquivo = lista.get(i);
							
							String caminho = caminhoArquivo.getCaminho();
							String arquivo = caminhoArquivo.getArquivo();
							
							String caminhoComArquivo = caminho + arquivo;
							
							int posicaoOndeEstaOPonto = caminhoComArquivo.indexOf(".");
							
							String diretorioDoArquivoSemExtensao = caminhoComArquivo.substring(0, posicaoOndeEstaOPonto);
							
							String apenasExtensao = caminhoComArquivo.substring(posicaoOndeEstaOPonto + 1, caminhoComArquivo.length());
							
							Log.i("tag","diretorioDoArquivoSemExtensao: "+diretorioDoArquivoSemExtensao);
							Log.i("tag","apenasExtensao: "+apenasExtensao);
							
							chamaVisualizadorBaseadoExtensao(context, diretorioDoArquivoSemExtensao, apenasExtensao);
							
						//} else {
							
							//new MeuAlerta("Foto cadastrada! porém visualizador não é \n compativel para essa versão do android!", null, context).meuAlertaOk();	
						//}
					}

				}
				dialogInterface.dismiss();
			}
		});

		builder1.show();
	}

	
	/*
	private void chamaVisualizadorPDF(String caminhoArquivo) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(caminhoArquivo)), "application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	*/
	private void chamaVisualizadorIMG(String caminhoArquivo) {

		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem:"), REQUISICAO_BUSCA_FOTO);
		intent.setDataAndType(Uri.fromFile(new File(caminhoArquivo)), "application/jpg");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	private void chamaVisualizadorBaseadoExtensao(Context context, String SRC_CONTRATO, String extensaoPDFouDOC) {

		String caminhoComExtensao = SRC_CONTRATO +"."+ extensaoPDFouDOC;

		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extensaoPDFouDOC);

	
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		
		//intent.setDataAndType(Uri.fromFile(new File(caminhoComExtensao)), mimeType);


		//Uri photoURI =                          FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider", new File(caminhoComExtensao));
		//intent = new Intent(Intent.ACTION_VIEW, FileProvider.getUriForFile(context, "mobile.contratodigital.view", new File(caminhoComExtensao)));

		
		// New Approach
	    				  Uri apkURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName(), new File(caminhoComExtensao));
	    				  
	    Log.i("tag","apkURI: "+apkURI);

	    Log.i("tag","mimeType: "+mimeType);
	    					 
	    				  
	    intent.setDataAndType(apkURI, mimeType);
	    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
	    // End New Approach
		
		
		
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		
		context.startActivity(intent);

		/*
		if (context instanceof ActivityContratoPadrao) {
			((ActivityContratoPadrao) context).finish();
		}
		if (context instanceof ActivityContratoContaSIM) {
			((ActivityContratoContaSIM) context).finish();
		}
		*/
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

			// Toast.makeText(context, "Não há formularios a serem removidos! ",
			// Toast.LENGTH_LONG).show();
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
									
									
									
									
									if(contratoUtil.naoTemNumeroDeContrato() ) {
						
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
		textFile = new TextView(context);
		
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

	/*
	 * @Override public void clicouFragConteudoFormulario(String mensagem, int
	 * nrProgrComNrForm) {
	 * 
	 * FragConteudoFormularioHolder fragConteudoFormularioHolder =
	 * (FragConteudoFormularioHolder)
	 * fragmentManager.findFragmentByTag("fragCFH" + nrProgrComNrForm);
	 * fragConteudoFormularioHolder.recebeInformacao(mensagem); }
	 */

	/*
	  private void tirarFotoCnpj() {
	  
	  FotoCNPJ trabalhaComFotos = new FotoCNPJ();
	 
	  Uri uri_diretorio = trabalhaComFotos.criaEDevolveDiretorioOndeAFotoSeraSalva(movimento1.getInformacao_1(),movimento1.getInformacao_4());
	  
	  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	  intent.putExtra("aaa", uri_diretorio);
	  
	  startActivityForResult(intent, REQUISICAO_TIRAR_FOTO);	  
	  }
	 */
	
	/* private void tirarFotoCPF() {
	 * 
	 * FotoCpf trabalhaComFotos = new FotoCpf();
	 * 
	 * Uri uri_diretorio =
	 * trabalhaComFotos.criaEDevolveDiretorioOndeAFotoSeraSalva(movimento1.
	 * getInformacao_1(), movimento1.getInformacao_4());
	 * 
	 * Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 * intent.putExtra("aaa", uri_diretorio);
	 * 
	 * startActivityForResult(intent, REQUISICAO_TIRAR_FOTO); new MeuAlerta(
	 * "CPF Gravado com sucesso", null, context).meuAlertaOk();
	 * 
	 * }
	 * 
	 * private void tirarFotoRG() {
	 * 
	 * FotoRG trabalhaComFotos = new FotoRG();
	 * 
	 * Uri uri_diretorio =
	 * trabalhaComFotos.criaEDevolveDiretorioOndeAFotoSeraSalva(movimento1.
	 * getInformacao_1(), movimento1.getInformacao_4());
	 * 
	 * Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 * intent.putExtra("aaa", uri_diretorio);
	 * 
	 * startActivityForResult(intent, REQUISICAO_TIRAR_FOTO); new MeuAlerta(
	 * "RG Gravado com sucesso", null, context).meuAlertaOk();
	 * 
	 * }
	 * 
	 * private void tirarFotoContrato() {
	 * 
	 * FotoContrato trabalhaComFotos = new FotoContrato();
	 * 
	 * Uri uri_diretorio =
	 * trabalhaComFotos.criaEDevolveDiretorioOndeAFotoSeraSalva(movimento1.
	 * getInformacao_1(), movimento1.getInformacao_4());
	 * 
	 * Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 * intent.putExtra("aaa", uri_diretorio);
	 * 
	 * startActivityForResult(intent, REQUISICAO_TIRAR_FOTO); new MeuAlerta(
	 * "Contrato Gravado com sucesso", null, context).meuAlertaOk();
	 * 
	 * }
	 */

	  private void criaDiretorioComOuSemNumeroDeContrato() {
		
		String srcContrato;
		
		Dao dao = new Dao(context);

		Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
																		Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
																		Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());

		if (contratoUtil.naoTemNumeroDeContrato()) {
			
			srcContrato = usaRazaoSocialComCPF_CNPJ(mov_informacoesCliente);		
		} else {
			srcContrato = usaNumeroDeContrato(mov_informacoesCliente);
		}

		try {
			File diretorio = new File(srcContrato);
				 diretorio.mkdir();
		} 
		catch (Exception erro) {
			new MeuAlerta(""+erro, null, context).meuAlertaOk();	
		}

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
							Movimento.COLUMN_INTEGER_NR_LAYOUT, mov.getNr_layout(), 
							Movimento.COLUMN_INTEGER_NR_VISITA, mov.getNr_visita());
	}

	private void tirarFoto() {
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);				
	   	   
		//startActivityForResult(intent, REQUISICAO_TIRAR_FOTO);	
		
		startActivityForResult(intent, REQUISICAO_PERMISSAO_TIRAR_FOTO);		
	}
	
	private void buscarFoto(Movimento mov_informacoesCliente) {
		
		if(formularioEquipamentosSimuladosFoiPreenchido()) {
		
			Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
	
			startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem:"), REQUISICAO_BUSCA_FOTO);
		}
		
		// finish();
	}

	private static Uri getFilesContentUri() {
		return Files.getContentUri("external");
	}

	private void buscarfile(Movimento mov_informacoesCliente) {

		if(formularioEquipamentosSimuladosFoiPreenchido()) {

			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("file/*");
			startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem:"), PICKFILE_RESULT_CODE);
			// startActivityForResult(intent,PICKFILE_RESULT_CODE);

		}

	}

	private void buscaFotoEmostraNolinearLayout_tela(Intent intent) {
		
		//boolean deuErro = false;
		
		Dao dao = new Dao(context);

		Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
				Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
				Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());

		if (contratoUtil.naoTemNumeroDeContrato()) {
			
			srcContratos = "/ContratoDigital/" + mov_informacoesCliente.getInformacao_1() + "_"
					+ mov_informacoesCliente.getInformacao_4().replace("/", "-") + "/";

		} else {
			srcContratos = "/ContratoDigital/" + "_" + mov_informacoesCliente.getNr_contrato() + "/";

		}

		Uri caminhoDaFotoSelecionada = intent.getData();

		TrabalhaComFotos trabalhaComFotos = new TrabalhaComFotos();

		String picturePath = trabalhaComFotos.devolveStringPicturePathBaseadoEmCaminhoDaFotoSelecionada(context,
				caminhoDaFotoSelecionada);
		Bitmap bitmap = Diminui_MB_imagens.decodeSampledBitmapFromPicturePath(picturePath, 200, 200);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);

		try {

			if (nomearquivo == "foto0.jpg") {
				File imageFile = new File(Environment.getExternalStorageDirectory() + srcContratos + "/" + nomearquivo);

				while (imageFile.exists()) {
					nr = nr + 1;
					imageFile = new File(Environment.getExternalStorageDirectory() + srcContratos + "/" + nomearquivo);
					// nomearquivo= "foto"+nr +".jpg";
					// nomearquivo = nomearquivo.replace(".jpg", "") + nr +
					// ".jpg";
				}
				if (!imageFile.exists()) {
					nr = nr - 1;
					nomearquivo = "foto" + nr + ".jpg";

					// nomearquivo = nomearquivo.replace(".jpg", "") + nr +
					// ".jpg";
					FileOutputStream fileOutputStream = new FileOutputStream(
							Environment.getExternalStorageDirectory() + srcContratos + "/" + nomearquivo);

					int quality = 100;

					bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);

					fileOutputStream.flush();
					fileOutputStream.close();
					new MeuAlerta("Imagem Gravada! ", null, context).meuAlertaOk();

					// Toast.makeText(context, "Imagem Gravada! ",
					// Toast.LENGTH_LONG).show();
				}
			} else if (nomearquivo == "Contrato.jpg") {
				File imageFile2 = new File(
						Environment.getExternalStorageDirectory() + srcContratos + "/" + nomearquivo);

				while (imageFile2.exists()) {
					nr = nr + 1;
					imageFile2 = new File(Environment.getExternalStorageDirectory() + srcContratos + "/" + nomearquivo);
					// nomearquivo= "foto"+nr +".jpg";
					// nomearquivo = nomearquivo.replace(".jpg", "") + nr +
					// ".jpg";
				}
				if (!imageFile2.exists()) {
					nr = nr - 1;
					nomearquivo = "Contrato" + nr + ".jpg";

					// nomearquivo = nomearquivo.replace(".jpg", "") + nr +
					// ".jpg";
					FileOutputStream fileOutputStream = new FileOutputStream(
							Environment.getExternalStorageDirectory() + srcContratos + "/" + nomearquivo);

					int quality = 100;

					bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);

					fileOutputStream.flush();
					fileOutputStream.close();
					new MeuAlerta("Imagem Gravada! ", null, context).meuAlertaOk();

					// Toast.makeText(context, "Imagem Gravada! ",
					// Toast.LENGTH_LONG).show();
				}
			} else {
				FileOutputStream fileOutputStream = new FileOutputStream(
						Environment.getExternalStorageDirectory() + srcContratos + "/" + nomearquivo);

				int quality = 100;

				bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);

				fileOutputStream.flush();
				fileOutputStream.close();
				new MeuAlerta("Imagem Gravada! ", null, context).meuAlertaOk();

			}

		} catch (Exception e) {

			//deuErro = true;
			e.printStackTrace();

		}

	}

}
