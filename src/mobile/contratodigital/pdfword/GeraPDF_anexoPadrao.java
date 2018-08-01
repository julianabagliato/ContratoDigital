package mobile.contratodigital.pdfword;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import android.content.Context;
import mobile.contratodigital.enums.TamanhoAssinatura;
import mobile.contratodigital.model.Assinatura;
import mobile.contratodigital.util.TextoContratos;
import sharedlib.contratodigital.model.Movimento;
import java.util.ArrayList;
import java.util.List;

public class GeraPDF_anexoPadrao extends GeraPDF{
		
	public GeraPDF_anexoPadrao(Context context) {
		super(context);
	}

	@Override
	void desenhaCorpo(PdfWriter pdfWriter, Document document, TextoContratos textoContratos, ArrayList<Movimento> listaComMovimentos, 
																							 List<Assinatura> listaComAssinaturas) throws DocumentException {

		desenhaLayoutDaTelaJahComInformacao(document, listaComMovimentos, "AnexoPadrao");
	}
   
	@Override
	void criaEadicionaAssinatura(PdfWriter pdfWriter, List<Assinatura> listaComAssinaturas) {
		
    	PdfContentByte pdfContentByte = pdfWriter.getDirectContentUnder();
		
		Assinatura assinatura_0 = listaComAssinaturas.get(0);
		Assinatura assinatura_1 = listaComAssinaturas.get(1);
		Assinatura assinatura_2 = listaComAssinaturas.get(2);
        	
		float rotacao = 0;
	    float height = TamanhoAssinatura.ALTURA.getTamanho(); 	      
	    float width = TamanhoAssinatura.LARGURA.getTamanho();
	    
	    float posicaoInicialColuna1 = 30;	  
        float posicaoInicialColuna2 = 300;	  
               
	    float posicaoUltimaLinhaEscrita = pdfWriter.getVerticalPosition(true);
	    float espacoEntreLinhas = 10;

	    float posicaoLinha1 = posicaoUltimaLinhaEscrita - (espacoEntreLinhas * 1);
	    float posicaoLinha2 = posicaoUltimaLinhaEscrita - (espacoEntreLinhas * 2);
	    float posicaoLinha3 = posicaoUltimaLinhaEscrita - (espacoEntreLinhas * 3);
	    float posicaoLinha4 = posicaoUltimaLinhaEscrita - (espacoEntreLinhas * 4);
	    float posicaoLinha5 = posicaoUltimaLinhaEscrita - (espacoEntreLinhas * 5);
	    float posicaoLinha6 = posicaoUltimaLinhaEscrita - (espacoEntreLinhas * 6);

	    float espacoImagemAssinatura = 60;	    
	    float posicaoLinha07 = posicaoLinha5 - espacoImagemAssinatura;  
	    float posicaoLinha08 = posicaoLinha07 - (espacoEntreLinhas * 1);
	    float posicaoLinha09 = posicaoLinha07 - (espacoEntreLinhas * 2);
	    float posicaoLinha10 = posicaoLinha07 - (espacoEntreLinhas * 3);
	    float posicaoLinha11 = posicaoLinha07 - (espacoEntreLinhas * 4);
	    float posicaoLinha12 = posicaoLinha07 - (espacoEntreLinhas * 5);

	
	    float posicaoLinha13 = posicaoLinha11 - espacoImagemAssinatura;  
	    float posicaoLinha14 = posicaoLinha13 - (espacoEntreLinhas * 1);
	    float posicaoLinha15 = posicaoLinha13 - (espacoEntreLinhas * 2);
	    float posicaoLinha16 = posicaoLinha13 - (espacoEntreLinhas * 3);
	    float posicaoLinha17 = posicaoLinha13 - (espacoEntreLinhas * 4);
	    
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("<Assinatura_empresa>", font_conteudo), posicaoInicialColuna1, posicaoLinha1, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("FORNECEDORAS (CONSIGAZ, GASBALL E PROPANGÁS)", font_conteudo), posicaoInicialColuna1, posicaoLinha2, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("", font_conteudo), posicaoInicialColuna1, posicaoLinha3, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("", font_conteudo), posicaoInicialColuna1, posicaoLinha4, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("", font_conteudo), posicaoInicialColuna1, posicaoLinha5, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("", font_conteudo), posicaoInicialColuna1, posicaoLinha6, rotacao);
        

		geraImagem(pdfContentByte, assinatura_1.getRecebeAssinatura(), 0, width, height, posicaoInicialColuna2, posicaoLinha1);        
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase(assinatura_1.getRazaoSocial(), font_conteudo), posicaoInicialColuna2, posicaoLinha2, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Cliente: "+assinatura_1.getNome(), font_conteudo), posicaoInicialColuna2, posicaoLinha3, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Cargo: "+assinatura_1.getCargo(), font_conteudo), posicaoInicialColuna2, posicaoLinha4, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("RG: "+assinatura_1.getRg(), font_conteudo), posicaoInicialColuna2, posicaoLinha5, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("CPF: "+assinatura_1.getCpf(), font_conteudo), posicaoInicialColuna2, posicaoLinha6, rotacao);
 
        
        if(assinatura_0.getRecebeAssinatura() != null) { 	
        	geraImagem(pdfContentByte, assinatura_0.getRecebeAssinatura(), 0, width, height, posicaoInicialColuna2, posicaoLinha07);
        }
        if(assinatura_0.getNome() != null) {
        	ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Testemunha: "+assinatura_0.getNome(), font_conteudo), posicaoInicialColuna2, posicaoLinha08, rotacao);
        }
        if(assinatura_0.getRg() != null) {
        	ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("RG: "+assinatura_0.getRg(), font_conteudo), posicaoInicialColuna2, posicaoLinha10, rotacao);
        }
        if(assinatura_0.getCpf() != null) {
        	ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("CPF: "+assinatura_0.getCpf(), font_conteudo), posicaoInicialColuna2, posicaoLinha11, rotacao);
        }


        if(assinatura_2.getRecebeAssinatura() != null) { 	
        	geraImagem(pdfContentByte, assinatura_2.getRecebeAssinatura(), 0, width, height, posicaoInicialColuna1, posicaoLinha07);
        }
        if(assinatura_2.getNome() != null) {
        	ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Testemunha: "+assinatura_2.getNome(), font_conteudo), posicaoInicialColuna1, posicaoLinha08, rotacao);
        }
        if(assinatura_2.getRg() != null) {
        	ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("RG: "+assinatura_2.getRg(), font_conteudo), posicaoInicialColuna1, posicaoLinha10, rotacao);	
        }
        if(assinatura_2.getCpf() != null) {
        	ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("CPF: "+assinatura_2.getCpf(), font_conteudo), posicaoInicialColuna1, posicaoLinha11, rotacao);	
        }

	}

}