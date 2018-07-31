package mobile.contratodigital.pdfword;

import java.util.ArrayList;
import java.util.List;

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

public class GeraPDF_contratoPadrao extends GeraPDF{
		
    public GeraPDF_contratoPadrao(Context context) {
		super(context);
	}

	@Override
	protected void desenhaCorpo(PdfWriter pdfWriter, Document document, TextoContratos textoContratos, ArrayList<Movimento> listaComMovimentos, 
																									   List<Assinatura> listaComAssinaturas) throws DocumentException {
		
		String textoContrato1 = textoContratos.devolveContratoPadrao(listaComMovimentos);	
		String titulo1 = textoContrato1.substring(0, 78);
		
		//tratamento necessario por causa do paragrafo variavel
		int posicaoFinalDoParagrafoVariavel = 24 + textoContrato1.indexOf("assinado e identificado.");
		String conteudo1 = textoContrato1.substring(78, posicaoFinalDoParagrafoVariavel);
		int tamanhoDoTitulo2 = 23;
		int t2 = posicaoFinalDoParagrafoVariavel + tamanhoDoTitulo2;
		//tratamento necessario por causa do paragrafo variavel
		
		String titulo2 = textoContrato1.substring(posicaoFinalDoParagrafoVariavel, t2);
		String conteudo2 = textoContrato1.substring(t2, textoContrato1.length());
		
		document.add(devolveConteudo("\n"));
        document.add(devolveTitulo(titulo1));
        document.add(devolveConteudo("\n"));
        document.add(devolveConteudo(conteudo1));
        document.add(devolveConteudo("\n"));
        document.add(devolveTitulo(titulo2));
        document.add(devolveConteudo("\n"));
        document.add(devolveConteudo(conteudo2));
        document.add(devolveConteudo("\n"));
        document.add(devolveData());		
	}
	
	@Override
	protected void criaEadicionaAssinatura(PdfWriter pdfWriter, List<Assinatura> listaComAssinaturas) {
	
    	PdfContentByte pdfContentByte = pdfWriter.getDirectContentUnder();
		
		Assinatura assinatura_2 = listaComAssinaturas.get(2);
		Assinatura assinatura_3 = listaComAssinaturas.get(3);
		Assinatura assinatura_4 = listaComAssinaturas.get(4);
				
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
	
	    float posicaoLinha13 = posicaoLinha11 - espacoImagemAssinatura;  
	    float posicaoLinha14 = posicaoLinha13 - (espacoEntreLinhas * 1);
	    float posicaoLinha15 = posicaoLinha13 - (espacoEntreLinhas * 2);
	    float posicaoLinha16 = posicaoLinha13 - (espacoEntreLinhas * 3);
	    float posicaoLinha17 = posicaoLinha13 - (espacoEntreLinhas * 4);
	    
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("<Assinatura_empresa>", font_conteudo), posicaoInicialColuna1, posicaoLinha1, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("FORNECEDORAS (CONSIGAZ, GASBALL E PROPANGÁS)", font_conteudo), posicaoInicialColuna1, posicaoLinha2, rotacao);
      
		geraImagem(pdfContentByte, assinatura_3.getRecebeAssinatura(), 0, width, height, posicaoInicialColuna2, posicaoLinha1);        
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase(assinatura_3.getRazaoSocial(), font_conteudo), posicaoInicialColuna2, posicaoLinha2, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Cliente: "+assinatura_3.getNome(), font_conteudo), posicaoInicialColuna2, posicaoLinha3, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Cargo: "+assinatura_3.getCargo(), font_conteudo), posicaoInicialColuna2, posicaoLinha4, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("RG: "+assinatura_3.getRg(), font_conteudo), posicaoInicialColuna2, posicaoLinha5, rotacao);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("CPF: "+assinatura_3.getCpf(), font_conteudo), posicaoInicialColuna2, posicaoLinha6, rotacao);

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
      
        if(assinatura_4.getRecebeAssinatura() != null) { 	
        	geraImagem(pdfContentByte, assinatura_4.getRecebeAssinatura(), 0, width, height, posicaoInicialColuna2, posicaoLinha07);
        }       
        if(assinatura_4.getNome() != null) {
        	ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Testemunha: "+assinatura_4.getNome(), font_conteudo), posicaoInicialColuna2, posicaoLinha08, rotacao);
        }
        if(assinatura_4.getRg() != null) {
        	ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("RG: "+assinatura_4.getRg(), font_conteudo), posicaoInicialColuna2, posicaoLinha10, rotacao);	
        }
        if(assinatura_4.getCpf() != null) {
        	ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("CPF: "+assinatura_4.getCpf(), font_conteudo), posicaoInicialColuna2, posicaoLinha11, rotacao);	
        }

	}

}