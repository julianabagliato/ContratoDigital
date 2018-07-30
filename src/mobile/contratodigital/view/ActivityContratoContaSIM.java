package mobile.contratodigital.view;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import mobile.contratodigital.R;
import mobile.contratodigital.enums.Tag;
import mobile.contratodigital.model.Assinatura;
import mobile.contratodigital.pdfword.GeraPDF_contratoContaSIM;
import mobile.contratodigital.pdfword.GeraWord_contratoContaSIM;
import mobile.contratodigital.util.ActContrato;
import mobile.contratodigital.util.TelaBuilder;
import mobile.contratodigital.util.TextoContratos;
import sharedlib.contratodigital.model.Movimento;

public class ActivityContratoContaSIM extends Activity {

	private Context context;
	private String srcContrato;
	private TelaBuilder telaBuilder;
	private ActContrato contratoAct;
	private static final int TOTAL_ASSIGN_GLOBAL = 8;
	private ArrayList<Movimento> listaComMovimentos;
	private String nr_contrato;
	private String Cliente ="";
	private String cCargo ="";
	private String cRG ="";
	private String cCpf ="";
	private String Testemunha1 ="";
	private String t1Cargo ="";
	private String t1RG ="";
	private String t1Cpf ="";
	private String Testemunha2 ="";
	private String t2Cargo ="";
	private String t2RG ="";
	private String t2Cpf ="";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = ActivityContratoContaSIM.this;

		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
				  actionBar.setTitle("Contrato Conta SIM");		

		Intent intent = getIntent();
		
		srcContrato = intent.getStringExtra(""+Tag.srcContrato);
		
		listaComMovimentos = (ArrayList<Movimento>) intent.getSerializableExtra(""+Tag.listaComMovimentos);
		
		telaBuilder = new TelaBuilder(context);
		
		contratoAct = new ActContrato(context);

		setContentView(constroiTela());
	}

	private ScrollView constroiTela() {

		ScrollView scrollView = new ScrollView(context);
		   		   scrollView.setBackgroundColor(Color.WHITE);
	
		   	  LinearLayout ll_telaHolder = telaBuilder.cria_LL(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT), 0);
						   ll_telaHolder.setOrientation(LinearLayout.HORIZONTAL);
		
						   			LinearLayout ll_principal = telaBuilder.cria_LL(new LayoutParams(1065, LayoutParams.MATCH_PARENT), 0);
						   						 //ll_principal.setBackgroundColor(Color.YELLOW);
						   						 ll_principal.setPadding(10, 0, 0, 0);
						   ll_telaHolder.addView(ll_principal);	
						   
						   			LinearLayout ll_rubrica = cria_ll_rubrica_contratoContaSIM();
						   ll_telaHolder.addView(ll_rubrica);			   
			
						   						 ll_principal.addView(contratoAct.devolve_IV_cabecalho());			
		
		criaEadicionaViewsTextoContratoNa_ll_principal(ll_principal);
		
		criaAssinaturasEbotao(ll_principal, ll_rubrica);
		
		   	   scrollView.addView(ll_telaHolder);
		return scrollView;
	}
	
	private LinearLayout cria_ll_rubrica_contratoContaSIM(){
	
		LinearLayout ll_rubrica = telaBuilder.cria_LL(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT), 0);
					 ll_rubrica.setPadding(0, 0, 0, 0);
					 ll_rubrica.addView(contratoAct.devolve_view_PosicaoDaRubrica(1450));
					 ll_rubrica.addView(contratoAct.devolve_ll_imgRubricaHolder("0"));		
					 ll_rubrica.addView(contratoAct.devolve_TV_numeroPagina("1"));
					 ll_rubrica.addView(contratoAct.devolve_view_PosicaoDaRubrica(1550));
					 ll_rubrica.addView(contratoAct.devolve_ll_imgRubricaHolder("1"));		
					 ll_rubrica.addView(contratoAct.devolve_TV_numeroPagina("2"));
					 ll_rubrica.addView(contratoAct.devolve_view_PosicaoDaRubrica(1550));
					 ll_rubrica.addView(contratoAct.devolve_ll_imgRubricaHolder("2"));		
					 ll_rubrica.addView(contratoAct.devolve_TV_numeroPagina("3"));
					 ll_rubrica.addView(contratoAct.devolve_view_PosicaoDaRubrica(1750));
					 ll_rubrica.addView(contratoAct.devolve_ll_imgRubricaHolder("3"));		
					 ll_rubrica.addView(contratoAct.devolve_TV_numeroPagina("4"));
					 ll_rubrica.addView(contratoAct.devolve_view_PosicaoDaRubrica(1800));
					 ll_rubrica.addView(contratoAct.devolve_TV_numeroPagina("5"));

		return ll_rubrica;
	}
		
	private void criaEadicionaViewsTextoContratoNa_ll_principal(LinearLayout ll_principal){
		
		TextoContratos textoContratos = new TextoContratos(context);
		nr_contrato = TextoContratos.devolvecontrato();

			
		String textoContratoContaSIM = textoContratos.devolveContratoContaSIM(listaComMovimentos);
		
		String tituloPrincipal = textoContratoContaSIM.substring(0, 78);
		
		int posicaoFinalDoParagrafoVariavel = 20 + textoContratoContaSIM.indexOf("acordado o seguinte:");
		String conteudo1 = textoContratoContaSIM.substring(78, posicaoFinalDoParagrafoVariavel);
		
		int tamanhoDoTitulo1 = 28;
		int t1 = posicaoFinalDoParagrafoVariavel + tamanhoDoTitulo1;
		String titulo1 = textoContratoContaSIM.substring(posicaoFinalDoParagrafoVariavel, t1);
		
		int tamanhoDoConteudo2 = 28 + textoContratoContaSIM.indexOf("de cada unidade consumidora.");
		String conteudo2 = textoContratoContaSIM.substring(t1, tamanhoDoConteudo2);
		
		int tamanhoDoTitulo2 = 18;
		int t2 = tamanhoDoConteudo2 + tamanhoDoTitulo2;	
		String titulo2 = textoContratoContaSIM.substring(tamanhoDoConteudo2, t2);
		
		int tamanhoDoConteudo3_1 = 13 + textoContratoContaSIM.indexOf("Cláusula 4.3.");		
		String conteudo3_1 = textoContratoContaSIM.substring(t2, tamanhoDoConteudo3_1);
		
		int tamanhoDoConteudo3_2 = 19 + textoContratoContaSIM.indexOf("julgar conveniente.");		
		String conteudo3_2 = textoContratoContaSIM.substring(tamanhoDoConteudo3_1, tamanhoDoConteudo3_2);
	
		int tamanhoDoTitulo3 = 19;
		int t3 = tamanhoDoConteudo3_2 + tamanhoDoTitulo3;	
		String titulo3 = textoContratoContaSIM.substring(tamanhoDoConteudo3_2, t3);

		int tamanhoDoConteudo4 = 13 + textoContratoContaSIM.indexOf("Código Civil.");		
		String conteudo4 = textoContratoContaSIM.substring(t3, tamanhoDoConteudo4);

		int tamanhoDoTitulo4 = 61;
		int t4 = tamanhoDoConteudo4 + tamanhoDoTitulo4;	
		String titulo4 = textoContratoContaSIM.substring(tamanhoDoConteudo4, t4);

		int tamanhoDoConteudo5 = 23 + textoContratoContaSIM.indexOf("reajustados anualmente.");		
		String conteudo5 = textoContratoContaSIM.substring(t4, tamanhoDoConteudo5);

		int tamanhoDoTitulo5 = 39;
		int t5 = tamanhoDoConteudo5 + tamanhoDoTitulo5;	
		String titulo5 = textoContratoContaSIM.substring(tamanhoDoConteudo5, t5);

		int tamanhoDoConteudo6_1 = 40 + textoContratoContaSIM.indexOf("RELIGAÇÃO VIGENTE NO DIA DA SOLICITAÇÃO.");		
		String conteudo6_1 = textoContratoContaSIM.substring(t5, tamanhoDoConteudo6_1);

		int tamanhoDoConteudo6_2 = 22 + textoContratoContaSIM.indexOf("corte de fornecimento.");		
		String conteudo6_2 = textoContratoContaSIM.substring(tamanhoDoConteudo6_1, tamanhoDoConteudo6_2);
		
		int tamanhoDoTitulo6 = 29;
		int t6 = tamanhoDoConteudo6_2 + tamanhoDoTitulo6;	
		String titulo6 = textoContratoContaSIM.substring(tamanhoDoConteudo6_2, t6);

		int tamanhoDoConteudo7_1 = 10 + textoContratoContaSIM.indexOf("às partes.");		
		String conteudo7_1 = textoContratoContaSIM.substring(t6, tamanhoDoConteudo7_1);
		
		String conteudo7_2 = textoContratoContaSIM.substring(tamanhoDoConteudo7_1, textoContratoContaSIM.length());

		ll_principal.addView(telaBuilder.cria_TV_titulo("\n" +tituloPrincipal));				
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo1));			   
		ll_principal.addView(telaBuilder.cria_TV_titulo(titulo1));					
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo2));			   
		ll_principal.addView(telaBuilder.cria_TV_titulo(titulo2));			
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo3_1 + "\n\n"));			   
		contratoAct.adiciona_rodape_espacoEntrePaginas_cabecalho(ll_principal);		
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo3_2));			   
		ll_principal.addView(telaBuilder.cria_TV_titulo(titulo3));									
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo4 + "\n\n"));			
		contratoAct.adiciona_rodape_espacoEntrePaginas_cabecalho(ll_principal);			
		ll_principal.addView(telaBuilder.cria_TV_titulo(titulo4));				
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo5));			   
		ll_principal.addView(telaBuilder.cria_TV_titulo(titulo5));				
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo6_1 + "\n\n"));			   		
		contratoAct.adiciona_rodape_espacoEntrePaginas_cabecalho(ll_principal);		
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo6_2));			   
		ll_principal.addView(telaBuilder.cria_TV_titulo(titulo6));				
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo7_1 + "\n\n"));			   		
		contratoAct.adiciona_rodape_espacoEntrePaginas_cabecalho(ll_principal);
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo7_2));			   
		ll_principal.addView(contratoAct.devolve_TV_DataAtualFormatada());		
	}

	private void criaAssinaturasEbotao(final LinearLayout ll_principal, final LinearLayout ll_rubrica){
		
		//cria assinaturas:
		LinearLayout ll_assinatura_coluna_holder = contratoAct.cria_ll_assinatura_coluna_holder(context);								
		final LinearLayout ll_assinatura_coluna_esquerda = contratoAct.cria_ll_assinatura_coluna_esquerd(context);					       
		final LinearLayout ll_assinatura_coluna_direita = contratoAct.cria_ll_assinatura_coluna_direita(context);
					 	   	 	   
							 ll_assinatura_coluna_holder.addView(ll_assinatura_coluna_esquerda);
							 ll_assinatura_coluna_holder.addView(ll_assinatura_coluna_direita);
		ll_principal.addView(ll_assinatura_coluna_holder);
		
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_esquerda, "FORNECEDORAS (CONSIGAZ, GASBALL E PROPANGÁS)", "Nome:", "4","1", telaBuilder,"","","","");
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_esquerda, "", "Testemunha:", "5","1", telaBuilder,Testemunha1,t1Cargo ,t1RG,t1Cpf);
		
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_direita, "CONDOMÍNIO", "Nome:", "6","1", telaBuilder,Cliente,cCargo ,cRG,cCpf);
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_direita, "", "Testemunha:", "7","1", telaBuilder,Testemunha2,t2Cargo ,t2RG,t2Cpf);
		//cria assinaturas:
		

		Button b_irParaAnexo = contratoAct.criaBotaoConcluirOUirParaAnexo(context, "Ir para anexo");		
		b_irParaAnexo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(!contratoAct.temCamposVazios(ll_rubrica, ll_principal, TOTAL_ASSIGN_GLOBAL, "ContratoContaSIM","1")){
							
					boolean ehContratoContaSIM = true;

					List<Assinatura> listaComAssinaturas = contratoAct.procuraAssinaturasEpopulaLista(ll_rubrica, ll_principal, 
																											 TOTAL_ASSIGN_GLOBAL, ehContratoContaSIM, "1");
							
					criaArquivoWord(listaComAssinaturas);
					criaArquivoPDF(listaComAssinaturas, ehContratoContaSIM);	
					chamaActivityAnexo(listaComAssinaturas);
					//metodosContratoAct.chamaVisualizadorBaseadoExtensao(context, srcContrato, "pdf");
					
				}		
			}
		});			
		
		
		ll_principal.addView(b_irParaAnexo);
	}
	

	private void criaArquivoWord(List<Assinatura> listaComAssinaturas) {

		GeraWord_contratoContaSIM geraWord_contratoContaSIM = new GeraWord_contratoContaSIM(context);
								  geraWord_contratoContaSIM.criaWord(srcContrato+".doc", listaComMovimentos, listaComAssinaturas);
	}

	private void criaArquivoPDF(List<Assinatura> listaComAssinaturas, boolean ehContratoContaSIM) {
			
		try {
		boolean ehContrato = true;
		
		GeraPDF_contratoContaSIM geraPDF_contratoContaSIM = new GeraPDF_contratoContaSIM(context);
								 geraPDF_contratoContaSIM.criaPDF(srcContrato+".pdf", listaComMovimentos, listaComAssinaturas, ehContrato, ehContratoContaSIM);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void chamaActivityAnexo(List<Assinatura> listaComAssinaturas) {
		Movimento mov_informacoesDoCliente = listaComMovimentos.get(0);

	    
		 					
			   Intent intent = new Intent(context, ActivityAnexoContaSIM.class);
				   	  intent = contratoAct.preencheIntent(intent, srcContrato, listaComMovimentos);
				   	Bundle bundle = new Bundle();	
				   		bundle.putString("Numero",nr_contrato );
				   	  bundle.putString("Cliente",listaComAssinaturas.get(6).getNome());
					   bundle.putString("cCargo", listaComAssinaturas.get(6).getCargo());
					   bundle.putString("cRG", listaComAssinaturas.get(6).getRg());
					   bundle.putString("cCpf", listaComAssinaturas.get(6).getCpf());
					   bundle.putString("Testemunha1", listaComAssinaturas.get(5).getNome());
					   bundle.putString("t1Cargo", listaComAssinaturas.get(5).getCargo());
					   bundle.putString("t1RG", listaComAssinaturas.get(5).getRg());
					   bundle.putString("t1Cpf", listaComAssinaturas.get(5).getCpf());
					   bundle.putString("Testemunha2", listaComAssinaturas.get(7).getNome());
					   bundle.putString("t2Cargo", listaComAssinaturas.get(7).getCargo());
					   bundle.putString("t2RG", listaComAssinaturas.get(7).getRg());
					   bundle.putString("t2Cpf", listaComAssinaturas.get(7).getCpf());
					   bundle.putSerializable("movimento", mov_informacoesDoCliente);	    
	  intent.putExtras(bundle);
		startActivity(intent);	
		finish(); 		
	}

}