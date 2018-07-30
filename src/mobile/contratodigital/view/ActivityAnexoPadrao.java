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
//import android.widget.Toast;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.SequenciaMovAddedEmLista;
import mobile.contratodigital.enums.Tag;
import mobile.contratodigital.model.Assinatura;
import mobile.contratodigital.pdfword.GeraPDF_anexoPadrao;
import mobile.contratodigital.pdfword.GeraWord_anexoPadrao;
import mobile.contratodigital.util.ActAnexo;
import mobile.contratodigital.util.ActContrato;
import mobile.contratodigital.util.TelaBuilder;
import sharedlib.contratodigital.model.Movimento;

public class ActivityAnexoPadrao extends Activity {

	private Context context;
	private TelaBuilder telaBuilder;
	private ActContrato contratoAct;
	private ActAnexo anexoAct;
	private String src;		
	private String Caminho;
	//private Movimento movimento1;
	private String Numero_contrato;
	private static final int TOTAL_ASSIGN_GLOBAL = 4;
	private ArrayList<Movimento> listaComMovimentos;
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
		
		Bundle bundle = getIntent().getExtras();

		//movimento1 = (Movimento) bundle.getSerializable("movimento");
		Numero_contrato= bundle.getString("Numero");
		 Cliente = bundle.getString("Cliente");
		 cCargo  = bundle.getString("cCargo");
		 cRG  = bundle.getString("cRG");
		 cCpf  = bundle.getString("cCpf");
		 Testemunha1  = bundle.getString("Testemunha1");
		 t1Cargo  = bundle.getString("t1Cargo");
		 t1RG  = bundle.getString("t1RG");
		 t1Cpf  = bundle.getString("t1Cpf");
		 Testemunha2  = bundle.getString("Testemunha2");
		 t2Cargo  = bundle.getString("t2Cargo");
		 t2RG  = bundle.getString("t2RG");
		 t2Cpf  = bundle.getString("t2Cpf");
		
		context = ActivityAnexoPadrao.this;

		ActionBar actionBar = getActionBar();
		  		  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		  		  actionBar.setTitle("Padrão Anexo I");		
		
   		Intent intent = getIntent();
					
   		String srcContrato = intent.getStringExtra(""+Tag.srcContrato);

	    Caminho = srcContrato.replace("/ContratoPadrao" , "");

	    src = srcContrato.replace("ContratoPadrao", "AnexoPadrao");
		
	    //Dao dao = new Dao(context);

	    listaComMovimentos = (ArrayList<Movimento>) intent.getSerializableExtra(""+Tag.listaComMovimentos);
	
	    //	ArrayList<Movimento> listaComMovimentos = dao.devolveListaComMovimentosPopulados(movimento1);

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
		
		criaAssinaturasBotaoRodape(ll_principal, "4");
		
		   	   scrollView.addView(ll_principal);
		return scrollView;
	}
			
	private void criaEadicionaViewsTextoContratoNa_ll_principal(LinearLayout ll_principal){

 		 anexoAct.desenhaLayoutDaTelaJahComInformacao(context, ll_principal, contratoAct, telaBuilder, listaComMovimentos, "AnexoPadrao");		
	}
	
	private void criaAssinaturasBotaoRodape(final LinearLayout ll_principal, String numeroPagina){
		
		
		//cria assinaturas:
		LinearLayout ll_assinatura_coluna_holder = contratoAct.cria_ll_assinatura_coluna_holder(context);
		final LinearLayout ll_assinatura_coluna_esquerda = contratoAct.cria_ll_assinatura_coluna_esquerd(context);				       
		final LinearLayout ll_assinatura_coluna_direita = contratoAct.cria_ll_assinatura_coluna_direita(context);
					 	   
							 ll_assinatura_coluna_holder.addView(ll_assinatura_coluna_esquerda);
							 ll_assinatura_coluna_holder.addView(ll_assinatura_coluna_direita);
		ll_principal.addView(ll_assinatura_coluna_holder);
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_esquerda, "FORNECEDORAS (CONSIGAZ, GASBALL E PROPANGÁS)", "Nome:", "0","4", telaBuilder,"","","","");
	
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_esquerda, "", "Testemunha: ", "1","4", telaBuilder,Testemunha1,t1Cargo,t1RG,t1Cpf);
	    contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_direita, "", "Cliente: ", "2","4", telaBuilder,Cliente,cCargo,cRG,cCpf);
		
		contratoAct.criaEadicionaFormularioDeAssinatura(ll_assinatura_coluna_direita, "", "Testemunha: ", "3","4", telaBuilder,Testemunha2,t2Cargo,t2RG,t2Cpf);
		//cria assinaturas:
		
 		ll_principal.addView(contratoAct.devolve_TV_numeroPagina(numeroPagina));		   
 		ll_principal.addView(contratoAct.devolve_TV_rodape());		
		
		Button b_concluir = contratoAct.criaBotaoConcluirOUirParaAnexo(context, "Concluir");	
		b_concluir.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(!contratoAct.temCamposVazios(null, ll_principal, TOTAL_ASSIGN_GLOBAL, "AnexoPadrao","4")){
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
					alertDialog.setMessage("Deseja Realmente Gerar Contrato?")
							.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int id) {

									anexoAct.procura_etObservacaoEgravaConteudoInformadoPelousuario(ll_principal);
									
									boolean ehContratoContaSIM = false;

									List<Assinatura> listaComAssinaturas = contratoAct.procuraAssinaturasEpopulaLista(null, ll_principal, TOTAL_ASSIGN_GLOBAL, ehContratoContaSIM,"4");
									
									criaArquivoWord(listaComAssinaturas);
									criaArquivoPDF(listaComAssinaturas, ehContratoContaSIM);
									
									fechaActivity();
									//metodosContratoAct.chamaVisualizadorBaseadoExtensao(context, srcContrato, "pdf");


						
								}
							})
							.setNegativeButton("Não", new DialogInterface.OnClickListener() {
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

		String novoSource = src+".doc";

		GeraWord_anexoPadrao geraWord_anexoPadrao = new GeraWord_anexoPadrao(context);
							 geraWord_anexoPadrao.criaWord(novoSource, listaComMovimentos, listaComAssinaturas);
								
			}
	
	private void criaArquivoPDF(List<Assinatura> listaComAssinaturas, boolean ehContratoContaSIM) {

		String novoSource = src+".pdf";

		try {
		
		boolean ehContrato = false;
			
		GeraPDF_anexoPadrao geraPDF_anexoPadrao = new GeraPDF_anexoPadrao(context);
							geraPDF_anexoPadrao.criaPDF(novoSource, listaComMovimentos, listaComAssinaturas, ehContrato, ehContratoContaSIM);


		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		  String Num = String.valueOf(listaComMovimentos.get(SequenciaMovAddedEmLista.mov_informacoesCliente.getPosicao()).getInformacao_1()+"_"+listaComMovimentos.get(SequenciaMovAddedEmLista.mov_informacoesCliente.getPosicao()).getInformacao_4().replace("/","-"));
		  File Antigo_caminho = new File(Caminho);
		  File novoCaminho = new File(Caminho.replace(Num,"_"+Numero_contrato));
		String deletar = Caminho.replace("_"+Numero_contrato,Num);

		  Antigo_caminho.renameTo(novoCaminho);


		  Dao dao = new Dao(context);

			
		  int tamanho = listaComMovimentos.size();
			for(int i = 0; i<tamanho; i++){
				if(listaComMovimentos.get(i)!=null){
			Movimento movimento2 = new Movimento();
			movimento2 =listaComMovimentos.get(i) ;					
			movimento2.setNr_contrato(Numero_contrato);
			movimento2.setStatus(0);
			preencheNoObjetoOcampoInformacao(movimento2, Numero_contrato);

			insereMovimento(dao, movimento2);
				}
			}/*
			
			Movimento movimento2 = new Movimento();
			movimento2 =listaComMovimentos.get(0) ;					
			movimento2.setNr_contrato(Numero_contrato);
			movimento2.setStatus(0);
			
			Movimento movimento3 = new Movimento();
			movimento3 = listaComMovimentos.get(1);
			movimento3.setNr_contrato(Numero_contrato);

			Movimento movimento4 = new Movimento();
			movimento4 = listaComMovimentos.get(2);
			movimento4.setNr_contrato(Numero_contrato);

			Movimento movimento5 = new Movimento();
			movimento5 = listaComMovimentos.get(3);
			movimento5.setNr_contrato(Numero_contrato);

			Movimento movimento6 = new Movimento();
			movimento6 = listaComMovimentos.get(4);
			movimento6.setNr_contrato(Numero_contrato);

			Movimento movimento7 = new Movimento();
			movimento7 = listaComMovimentos.get(5);
			movimento7.setNr_contrato(Numero_contrato);
			  
		
			preencheNoObjetoOcampoInformacao(movimento2, Numero_contrato);
			preencheNoObjetoOcampoInformacao(movimento3, Numero_contrato);
			preencheNoObjetoOcampoInformacao(movimento4, Numero_contrato);
			preencheNoObjetoOcampoInformacao(movimento5, Numero_contrato);
			preencheNoObjetoOcampoInformacao(movimento6, Numero_contrato);
			preencheNoObjetoOcampoInformacao(movimento7, Numero_contrato);

			
			insereMovimento(dao, movimento2);
			insereMovimento(dao, movimento3);
			insereMovimento(dao, movimento4);
			insereMovimento(dao, movimento5);
			insereMovimento(dao, movimento6);
			insereMovimento(dao, movimento7);
*/

		  try {
			Runtime.getRuntime().exec("cmd /c net use k: \\\\NTI_X23\\C$\\Users /yes");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			Runtime.getRuntime().exec("cmd /c rd k:\\"+ deletar +" /s /q");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			Runtime.getRuntime().exec("cmd /c net use k: /delete /yes");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fechaActivity(){

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

						
						if(!conteudo.isEmpty()){
										
							atributo.set(objeto, conteudo);
							
							break;
						}
					}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return (Movimento) objeto;
	}
	
private void insereMovimento(Dao dao, Movimento mov){
		
		dao.insereOUatualiza(mov, 
				 			 //Movimento.COLUMN_INTEGER_NR_PROGRAMACAO, mov.getNr_programacao(), 
				 			 Movimento.COLUMN_INTEGER_NR_LAYOUT, mov.getNr_layout(),
				 			 Movimento.COLUMN_INTEGER_NR_VISITA, mov.getNr_visita()
								);
	}
	

}