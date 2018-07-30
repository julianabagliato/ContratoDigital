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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import mobile.contratodigital.R;
import mobile.contratodigital.enums.SequenciaMovAddedEmLista;
import mobile.contratodigital.enums.Tag;
import mobile.contratodigital.model.Assinatura;
import mobile.contratodigital.pdfword.GeraPDF_contratoPadrao;
import mobile.contratodigital.pdfword.GeraWord_contratoPadrao;
import mobile.contratodigital.util.ActContrato;
import mobile.contratodigital.util.TelaBuilder;
import mobile.contratodigital.util.TextoContratos;
import sharedlib.contratodigital.model.Movimento;

public class ActivityContratoPadrao extends Activity {

	private Context context;
	private String srcContrato;
	private TelaBuilder telaBuilder;
	private ActContrato contratoAct;
	private static final int TOTAL_ASSIGN_GLOBAL = 6;
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

		context = ActivityContratoPadrao.this;

		ActionBar actionBar = getActionBar();
		  		  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		  		  actionBar.setTitle("Contrato Padrão");		

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
												 ll_principal.setPadding(10, 0, 0, 0);
		   				   ll_telaHolder.addView(ll_principal);	
		   				   
		   				   			LinearLayout ll_rubrica = cria_ll_rubrica_contratoPadrao();
		   				   ll_telaHolder.addView(ll_rubrica);			   
			
		   				   						 ll_principal.addView(contratoAct.devolve_IV_cabecalho());			
		
		criaEadicionaViewsTextoContratoNa_ll_principal(ll_principal);
		
		criaAssinaturasEbotao(ll_principal, ll_rubrica);
		
	
		
		//horizontalScrollView.addView(ll_telaHolder);
		   	   
		   	   
		   	   
		
		
		scrollView.addView(ll_telaHolder);
		
		return scrollView;
	}
	private LinearLayout cria_ll_rubrica_contratoPadrao(){
		
		LinearLayout ll_rubrica = telaBuilder.cria_LL(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT), 0);
				     ll_rubrica.setPadding(0, 0, 0, 0);	
				     ll_rubrica.addView(contratoAct.devolve_view_PosicaoDaRubrica(1450));
				     ll_rubrica.addView(contratoAct.devolve_ll_imgRubricaHolder("0"));					
				     ll_rubrica.addView(contratoAct.devolve_TV_numeroPagina("1"));
				     ll_rubrica.addView(contratoAct.devolve_view_PosicaoDaRubrica(1550));
				     ll_rubrica.addView(contratoAct.devolve_ll_imgRubricaHolder("1"));		
				     ll_rubrica.addView(contratoAct.devolve_TV_numeroPagina("2"));
				     ll_rubrica.addView(contratoAct.devolve_view_PosicaoDaRubrica(1750));
				     ll_rubrica.addView(contratoAct.devolve_TV_numeroPagina("3"));

		return ll_rubrica;
	}
		
	private void criaEadicionaViewsTextoContratoNa_ll_principal(LinearLayout ll_principal){
		
		TextoContratos textoContratos = new TextoContratos(context);
		nr_contrato = TextoContratos.devolvecontrato();

		String textoContratoPadrao = textoContratos.devolveContratoPadrao(listaComMovimentos);
		String titulo1 = textoContratoPadrao.substring(0, 78);
		//tratamento necessario por causa do paragrafo variavel
		int posicaoFinalDoParagrafoVariavel = 24 + textoContratoPadrao.indexOf("assinado e identificado.");
		String conteudo1 = textoContratoPadrao.substring(78, posicaoFinalDoParagrafoVariavel);
		int tamanhoDoTitulo2 = 23;
		int t2 = posicaoFinalDoParagrafoVariavel + tamanhoDoTitulo2;
		int tamanhoDoConteudo2 = 3491;
		int t3 = posicaoFinalDoParagrafoVariavel + tamanhoDoTitulo2 + tamanhoDoConteudo2;
		int tamanhoDoConteudo3 = 5525;
		int t4 = posicaoFinalDoParagrafoVariavel + tamanhoDoTitulo2 + tamanhoDoConteudo2 + tamanhoDoConteudo3;
		//tratamento necessario por causa do paragrafo variavel	
		String titulo2 = textoContratoPadrao.substring(posicaoFinalDoParagrafoVariavel, t2);
		String conteudo2 = textoContratoPadrao.substring(t2, t3);
		String conteudo3 = textoContratoPadrao.substring(t3, t4);
		String conteudo4 = textoContratoPadrao.substring(t4, textoContratoPadrao.length());
			   
		ll_principal.addView(telaBuilder.cria_TV_titulo("\n"+ titulo1));				
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo1));			   
		ll_principal.addView(telaBuilder.cria_TV_titulo(titulo2));		
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo2));		
		contratoAct.adiciona_rodape_espacoEntrePaginas_cabecalho(ll_principal);
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo3));	
		contratoAct.adiciona_rodape_espacoEntrePaginas_cabecalho(ll_principal);
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(conteudo4));
		ll_principal.addView(contratoAct.devolve_TV_DataAtualFormatada());
	}
	
	private void criaAssinaturasEbotao(final LinearLayout ll_principal, final LinearLayout ll_rubrica){
		
		//int ID_CRIACAO = 1;
		
		//cria assinaturas:
		LinearLayout ll_assinatura_coluna_holder = contratoAct.cria_ll_assinatura_coluna_holder(context);
		final LinearLayout ll_assinatura_coluna_esquerda = contratoAct.cria_ll_assinatura_coluna_esquerd(context);				       
		final LinearLayout ll_assinatura_coluna_direita = contratoAct.cria_ll_assinatura_coluna_direita(context);
					 	   
							 ll_assinatura_coluna_holder.addView(ll_assinatura_coluna_esquerda);
							 ll_assinatura_coluna_holder.addView(ll_assinatura_coluna_direita);
		ll_principal.addView(ll_assinatura_coluna_holder);
		
		Movimento mov_informacoesDoCliente = listaComMovimentos.get(1);
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_esquerda, "FORNECEDORAS (CONSIGAZ, GASBALL E PROPANGÁS)", "Nome:", "2","3", telaBuilder,"","","","");
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_esquerda, "", "Testemunha: ", "3","3", telaBuilder,Cliente,cCargo ,cRG,cCpf);

		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_direita, mov_informacoesDoCliente.getInformacao_1(), "Cliente: ", "4","3", telaBuilder,Testemunha1,t1Cargo ,t1RG,t1Cpf);
		
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_direita, "", "Testemunha: ", "5","3", telaBuilder,Testemunha2,t2Cargo ,t2RG,t2Cpf);
		//cria assinaturas:

		

		Button b_irParaAnexo = contratoAct.criaBotaoConcluirOUirParaAnexo(context, "Ir para anexo");		
		b_irParaAnexo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(!contratoAct.temCamposVazios(ll_rubrica, ll_principal, TOTAL_ASSIGN_GLOBAL, "ContratoPadrao","3")){
					
					boolean ehContratoContaSIM = false;

						List<Assinatura> listaComAssinaturas = contratoAct.procuraAssinaturasEpopulaLista(ll_rubrica, ll_principal, 
																												 TOTAL_ASSIGN_GLOBAL, ehContratoContaSIM,"3");
							
						criaArquivoWord(listaComAssinaturas);
						criaArquivoPDF(listaComAssinaturas, ehContratoContaSIM);
						
						chamaActivityAnexo(listaComAssinaturas);
						//metodosContratoAct.chamaVisualizadorBaseadoExtensao(context, srcContrato, "pdf");
					//}
					
				}
			}
		});			
	
		ll_principal.addView(b_irParaAnexo);
	}
		
	private void criaArquivoWord(List<Assinatura> listaComAssinaturas) {

		GeraWord_contratoPadrao geraWord_contratoPadrao = new GeraWord_contratoPadrao(context);
								geraWord_contratoPadrao.criaWord(srcContrato+".doc", listaComMovimentos, listaComAssinaturas);
	}

	private void criaArquivoPDF(List<Assinatura> listaComAssinaturas, boolean ehContratoContaSIM) {
	
		try {
		
		boolean ehContrato = true;
		
		GeraPDF_contratoPadrao geraPDF_contratoPadrao = new GeraPDF_contratoPadrao(context);
							   geraPDF_contratoPadrao.criaPDF(srcContrato+".pdf", listaComMovimentos, listaComAssinaturas, ehContrato, ehContratoContaSIM);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void chamaActivityAnexo(List<Assinatura> listaComAssinaturas) {
		
		Movimento mov_informacoesDoCliente = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_informacoesCliente.getPosicao());
		
	   		   Intent intent = new Intent(context, ActivityAnexoPadrao.class);
	   		   	
	   			      intent = contratoAct.preencheIntent(intent, srcContrato, listaComMovimentos);	 	   
	   			  	Bundle bundle = new Bundle();	
					   bundle.putSerializable("movimento", mov_informacoesDoCliente);	
					   bundle.putString("Numero", nr_contrato);
					   bundle.putString("Cliente",listaComAssinaturas.get(4).getNome());
					   bundle.putString("cCargo", listaComAssinaturas.get(4).getCargo());
					   bundle.putString("cRG", listaComAssinaturas.get(4).getRg());
					   bundle.putString("cCpf", listaComAssinaturas.get(4).getCpf());
					   bundle.putString("Testemunha1", listaComAssinaturas.get(3).getNome());
					   bundle.putString("t1Cargo", listaComAssinaturas.get(3).getCargo());
					   bundle.putString("t1RG", listaComAssinaturas.get(3).getRg());
					   bundle.putString("t1Cpf", listaComAssinaturas.get(3).getCpf());
					   bundle.putString("Testemunha2", listaComAssinaturas.get(5).getNome());
					   bundle.putString("t2Cargo", listaComAssinaturas.get(5).getCargo());
					   bundle.putString("t2RG", listaComAssinaturas.get(5).getRg());
					   bundle.putString("t2Cpf", listaComAssinaturas.get(5).getCpf());
					

					   bundle.putString("Numero", nr_contrato);


	  intent.putExtras(bundle);  
	   			      startActivity(intent);					   
	   	finish(); 		
	}

}