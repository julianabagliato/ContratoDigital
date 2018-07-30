package mobile.contratodigital.pdfword;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.LinearLayout;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.SequenciaMovAddedEmLista;
import mobile.contratodigital.model.Assinatura;
import mobile.contratodigital.model.PrecoPrazoConsumoPagamento;
import mobile.contratodigital.util.MoedaRS;
import mobile.contratodigital.util.Reflexao;
import mobile.contratodigital.util.TelaBuilder;
import mobile.contratodigital.util.TextoContratos;
import mobile.contratodigital.util.TrabalhaComImagens;
import sharedlib.contratodigital.model.Movimento;
import word.w2004.elements.tableElements.TableEle;

/**
 * Classe criada para gerar o pdf geral
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public abstract class GeraPDF {
	
	private Context context;
	private Font font_titulo;
	protected Font font_conteudo;
	private float TAMANHO_FONTE_RODAPE = 7;
	private float TAMANHO_FONTE_TITULO = 9;
	private float TAMANHO_FONTE_CONTEUDO = 8;
	private TextoContratos textoContratos;
	private TrabalhaComImagens trabalhaComImagens;

	public GeraPDF(Context context){
		this.context = context;
		trabalhaComImagens = new TrabalhaComImagens(); 
		textoContratos = new TextoContratos(context);
		font_titulo = new Font(FontFamily.TIMES_ROMAN, TAMANHO_FONTE_TITULO, Font.BOLD);
		font_conteudo = new Font(FontFamily.TIMES_ROMAN, TAMANHO_FONTE_CONTEUDO);
	}
	
	public void criaPDF(String SRC_CONTRATO, ArrayList<Movimento> listaComMovimentos, 
											 List<Assinatura> listaComAssinaturas, boolean ehContrato, boolean ehContratoContaSIM) throws Exception {
    	
    	File file = new File(SRC_CONTRATO);
    	
    	if(!file.exists()){
    		file.getParentFile().mkdirs();  	
    	}
    	     
    	float margemEsquerda = 30;
    	float margemDireita = 60;
    	float margemEmCima = 60;
    	float margemEmBaixo = 40;
    	
    	Document document = new Document();
    			 document.setMargins(margemEsquerda, margemDireita, margemEmCima, margemEmBaixo);
    	
    	PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(SRC_CONTRATO));
    			  pdfWriter.setPageEvent(new CabecalhoRodape(listaComAssinaturas, ehContrato, ehContratoContaSIM));
    			  
        document.open();    
	    
        //implementar:
        desenhaCorpo(pdfWriter, document, textoContratos, listaComMovimentos, listaComAssinaturas);   
              
        //implementar:
        criaEadicionaAssinatura(pdfWriter, listaComAssinaturas);
        
        document.close();        
    }
			
	protected void desenhaLayoutDaTelaJahComInformacao(Document document, ArrayList<Movimento> listaComMovimentos, String tipoAnexo) throws DocumentException{
				
		document.add(devolveTitulo("Anexo I"));
		document.add(devolveConteudo(textoContratos.devolveTextoAnexo()));
        document.add(devolveConteudo("\n"));
		document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getLogradouro_titulo() + PrecoPrazoConsumoPagamento.getLogradouro_conteudo()));
        document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getNumero_titulo() + PrecoPrazoConsumoPagamento.getNumero_conteudo()));
        document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getBairro_titulo() + PrecoPrazoConsumoPagamento.getBairro_conteudo()));
        document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getCidade_titulo() + PrecoPrazoConsumoPagamento.getCidade_conteudo())); 
        document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getEstado_titulo() + PrecoPrazoConsumoPagamento.getEstado_conteudo()));
        document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getCep_titulo() + PrecoPrazoConsumoPagamento.getCep_conteudo()));
        document.add(devolveConteudo("\n"));
        document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getPrecoNegociado()));
		
        if(tipoAnexo.equals("AnexoContaSIM")){
   		
			document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getTaxaServico()));
			document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getTaxaReligue()));
		}
        document.add(devolveConteudo("\n"));
        document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getPrazoVigencia()));
        document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getDataInicio()));
        document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getPrazoPagamento()));
        document.add(devolveConteudo("\n"));
		document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getConsumoPrevistoEmKg()));
		document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getPrevConsumoAteFimContrato()));
	    document.add(devolveConteudo("\n"));
	    
	    
	    
		
		
		

	    Movimento mov_equipamentosSimulados = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_equipamentosSimulados.getPosicao());

	

	

		List<String> listaTanques = new ArrayList<String>();
		List<String> listaCilindros = new ArrayList<String>();
		List<String> listaEquipamentos = new ArrayList<String>();
		List<String> listaCentral = new ArrayList<String>();
		List<String> listaRede = new ArrayList<String>();
		List<String> listaOutros = new ArrayList<String>();
		List<String> listaInvestimentosEspeciais = new ArrayList<String>();
		 
		//INICIO: ordenaItensAntesDeAdicionarNaView();
		for(String equipamento : Reflexao.devolveListaComEquipamentosAdicionados(mov_equipamentosSimulados)){
			
			if(equipamento.contains("TANQUES")) {
				
				listaTanques.add(equipamento);
			}
			else if(equipamento.contains("CILINDROS")) {
				
				listaCilindros.add(equipamento);
			}
			else if(equipamento.contains("EQUIPAMENTOS")) {
		
				listaEquipamentos.add(equipamento);
			}
			else if(equipamento.contains("CENTRAL")) {
				
				listaCentral.add(equipamento);
			}
			else if(equipamento.contains("REDE")) {
				
				listaRede.add(equipamento);
			}
			else if(equipamento.contains("investimentos especiais")) {
				
				listaInvestimentosEspeciais.add(equipamento);
			}	
			else {
				listaOutros.add(equipamento);		
			}
		}
		//FIM ordenaItensAntesDeAdicionarNaView
		
		
		//ll_holder5.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo("Equipamentos objeto de comodato", ":"));	
	    document.add(devolveConteudo1("Equipamentos Objetos em comodato:"));

		
		double subtotalTanques = adicionaItensNaActivityConformeListaRecebida(listaTanques, document);
		double subtotalCilindros = adicionaItensNaActivityConformeListaRecebida(listaCilindros, document);
		double subtotalEquipamentos = adicionaItensNaActivityConformeListaRecebida(listaEquipamentos, document);
		
		

		double subtotal1 = subtotalTanques + subtotalCilindros + subtotalEquipamentos;
				
		MoedaRS moedaRS = new MoedaRS();
		
		String subtotalEmExtenso1 = moedaRS.converteNumeroParaExtensoReais(subtotal1);

		document.add(devolveConteudo("\n"));
		document.add(devolveConteudo(" Subtotal A: "+subtotalEmExtenso1));
		document.add(devolveConteudo("\n"));
		
		//INICIO: a tratativa abaixo é apenas por causa dos equipamentos especiais
		String eqpEspecial = listaInvestimentosEspeciais.get(0);
		
		int posicaoDivisoria = eqpEspecial.indexOf(":");
		
		String titulo = eqpEspecial.substring(0, posicaoDivisoria + 1);
		String linhaComItemEValor = eqpEspecial.substring(posicaoDivisoria + 1, eqpEspecial.length());
		
		document.add(devolveConteudo1(""+titulo));
	
		double subtotalCentral = adicionaItensNaActivityConformeListaRecebida(listaCentral, document);
		double subtotalRede = adicionaItensNaActivityConformeListaRecebida(listaRede, document);
		double valorDoEquipamentoEspecial = separaItemDeValorEAdicionaNoLinearLayout(linhaComItemEValor, document);	
		
		double subtotal2 = subtotalCentral + subtotalRede + valorDoEquipamentoEspecial;
		
		String subtotal2EmExtenso = moedaRS.converteNumeroParaExtensoReais(subtotal2);

		document.add(devolveConteudo("\n"));
		document.add(devolveConteudo(" Subtotal B: "+subtotal2EmExtenso));
		document.add(devolveConteudo("\n"));
		
		//FIM:   a tratativa abaixo é apenas por causa dos equipamentos especiais
		
		//INICIO: a lista <OUTROS> apenas serve para caso sobre alguma sujeira nao planejada
		adicionaItensNaActivityConformeListaRecebida(listaOutros, document);
		//FIM:    a lista <OUTROS> apenas serve para caso sobre alguma sujeira nao planejada


        document.add(devolveConteudo("\n"));
		document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getCustoTotalInvestimento()));
		document.add(devolveConteudo("\n"));
		
        document.add(devolveConteudo(PrecoPrazoConsumoPagamento.getObservacoes_titulo() + PrecoPrazoConsumoPagamento.getObservacoes_conteudo()));
        document.add(devolveData());		
	}
		
	private double adicionaItensNaActivityConformeListaRecebida(List<String> lista, Document document) throws DocumentException {
		
		double subtotal = 0.0;
		
		for(String linhaComItemEValor : lista) {

			double valorDoItem = separaItemDeValorEAdicionaNoLinearLayout(linhaComItemEValor, document);		
			
			subtotal = subtotal + valorDoItem;
		}

		return subtotal;
	}
	
	private double separaItemDeValorEAdicionaNoLinearLayout(String linhaComItemEValor, Document document) throws DocumentException {
		
		double valorDoItem = 0.0;
		
		int posicaoDaDivisoria = linhaComItemEValor.indexOf("R$");
		
		String item = linhaComItemEValor.substring(0, posicaoDaDivisoria);
		String valor = linhaComItemEValor.substring(posicaoDaDivisoria - 1, linhaComItemEValor.length());

		
		
		try {
					
			int posicaoDosCentavos = valor.indexOf("(");
			
			String numerosComPontoVirgulaRS = valor.substring(0, posicaoDosCentavos);
			
			String numerosComPontoVirgula = numerosComPontoVirgulaRS.replace("R$", "");
			
			String numerosComVirgula = numerosComPontoVirgula.replace(".", "");
			
			String valorProntoParaUso = numerosComVirgula.replace(",", ".");
			
			valorDoItem = Double.parseDouble(valorProntoParaUso);	
		}
		catch(Exception erro) {
			

			Log.i("tag","nao conseguiu calcular o sub total: "+erro);			
		}
		
		//ll_holder.addView(telaBuilder.cria_LL_LLTVtitulo_LLTVconteudo(" "+item, " "+valor));
	    document.add(devolveConteudo(""+item+"                           "+valor));

		return valorDoItem;
	}

	abstract void desenhaCorpo(PdfWriter pdfWriter, Document document, TextoContratos textoContratos, 
							   ArrayList<Movimento> listaComMovimentos, List<Assinatura> listaComAssinaturas) throws DocumentException;
	
    abstract void criaEadicionaAssinatura(PdfWriter pdfWriter, List<Assinatura> listaComAssinaturas);
	
    public Paragraph devolveConteudo(String conteudo){
    	
    	Paragraph paragraph_conteudo1 = new Paragraph(conteudo, font_conteudo);
				  paragraph_conteudo1.setAlignment(Element.ALIGN_JUSTIFIED);
     
     return paragraph_conteudo1;
    }
    
    public Paragraph devolveConteudo1(String titulo){
	 
		Paragraph paragraph_tituloPrincipal = new Paragraph(titulo, font_titulo);
				  paragraph_tituloPrincipal.setAlignment(Element.ALIGN_JUSTIFIED);			  

		return paragraph_tituloPrincipal;
    }
    
    public Paragraph devolveTitulo(String titulo){
		
    	Paragraph paragraph_tituloPrincipal = new Paragraph(titulo, font_titulo);
		  		  paragraph_tituloPrincipal.setAlignment(Element.ALIGN_CENTER);		
		  		  
       return paragraph_tituloPrincipal;
    }

    public Paragraph devolveData(){

    	Paragraph paragraph_dataAtual = new Paragraph(textoContratos.devolveDataAtualFormatada() + "\n\n\n\n\n", font_conteudo);
		 		  paragraph_dataAtual.setAlignment(Element.ALIGN_RIGHT);
    	
	  return paragraph_dataAtual;	 		  
    }
 
    class CabecalhoRodape extends PdfPageEventHelper {
    	
    	private List<Assinatura> listaComAssinaturas;
    	private boolean ehContrato;
    	private boolean ehContratoContaSIM;
    	
    	public CabecalhoRodape(List<Assinatura> listaComAssinaturas, boolean _ehContrato, boolean _ehContratoContaSIM){
    		this.listaComAssinaturas = listaComAssinaturas;
    		this.ehContrato = _ehContrato;
    		this.ehContratoContaSIM = _ehContratoContaSIM;
    	}
    	
        @Override
		public void onEndPage(PdfWriter pdfWriter, Document document){
        	 	
            PdfContentByte pdfContentByte = pdfWriter.getDirectContentUnder();
      
            float rotacao = 0; 	  
            
            float width = 200;//300
            float height = 30;//43 	      
            float posicaoInicialColuna = 200; //150	  
            float posicaoInicialLinha = 790; 	  
        
            geraImagem(pdfContentByte, null, R.drawable.logo_cabecalho, width, height, posicaoInicialColuna, posicaoInicialLinha);
             
            if(ehContrato){
            	
            	geraRubricas(pdfContentByte, document, listaComAssinaturas, ehContratoContaSIM);
            }
            
            geraNumeroPagina(pdfContentByte, document, rotacao);
		    
            geraRodape(pdfContentByte, rotacao);
        }
    }
    
	private void geraRubricas(PdfContentByte pdfContentByte, Document document, List<Assinatura> listaComAssinaturas, boolean ehContratoContaSIM){
		
		int numeroPagina = document.getPageNumber();
		
		switch(numeroPagina){
		
		case 1: geraRubricaIndividual(pdfContentByte, listaComAssinaturas, 0);
				break;
				
		case 2: geraRubricaIndividual(pdfContentByte, listaComAssinaturas, 1);
				break;
				
		case 3: if(ehContratoContaSIM) geraRubricaIndividual(pdfContentByte, listaComAssinaturas, 2);
				break;
				
		case 4: if(ehContratoContaSIM) geraRubricaIndividual(pdfContentByte, listaComAssinaturas, 3);
				break;
		}	
	} 
	private void geraRubricaIndividual(PdfContentByte pdfContentByte, List<Assinatura> listaComAssinaturas, int posicao){

		Assinatura assinatura_x = listaComAssinaturas.get(posicao);

        float width1 = 40;
        float height1 = 25; 	      
        float posicaoInicialColuna1 = 540;	  
        float posicaoInicialLinha1 = 70;  
        geraImagem(pdfContentByte, assinatura_x.getRecebeAssinatura(), 0, width1, height1, posicaoInicialColuna1, posicaoInicialLinha1);	
	}

    public void geraImagem(PdfContentByte pdfContentByte, Drawable imgDrawable, int imgInteger, float width, float height, float posicaoInicialColuna, float posicaoInicialLinha){

        float rotacao = 0;
        float esticar = 0; 	  
        
        Image image = null;
        
        if(imgInteger != 0){
        	
        	image = trabalhaComImagens.transformaDrawableEmImagem(context, imgInteger);
        }else{	
        	image = trabalhaComImagens.transformaDrawableEmImagem(context, imgDrawable);        	 
        }
        
		try {
			pdfContentByte.addImage(image, width, rotacao, esticar, height, posicaoInicialColuna, posicaoInicialLinha);
		}
		catch (DocumentException e) {
			e.printStackTrace();
		}
    }
    
    public void geraNumeroPagina(PdfContentByte pdfContentByte, Document document, float rotacao){
    	
		float posicaoInicialColunaNpagina = 565;    
		float posicaoInicialLinhaNpagina = 30;
        
        Phrase numeroPagina = new Phrase(""+document.getPageNumber(), font_conteudo);
        
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, numeroPagina, posicaoInicialColunaNpagina, posicaoInicialLinhaNpagina, rotacao);
    }
    
    public void geraRodape(PdfContentByte pdfContentByte, float rotacao){
    	
		float posicaoInicialColunaRodape = 112;
		float posicaoInicialLinhaRodape = 20;
		
		Font fontRodape = new Font(Font.FontFamily.TIMES_ROMAN, TAMANHO_FONTE_RODAPE, Font.ITALIC);
        
        Phrase rodape = new Phrase("Consigaz Distribuidora de Gas Ltda – CAC 11 4197-9300", fontRodape);
        
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_CENTER, rodape, posicaoInicialColunaRodape, posicaoInicialLinhaRodape, rotacao);
    }

}
