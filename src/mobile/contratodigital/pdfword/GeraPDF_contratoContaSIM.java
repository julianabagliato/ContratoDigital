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

public class GeraPDF_contratoContaSIM extends GeraPDF{
	
	public GeraPDF_contratoContaSIM(Context context) {
		super(context);
	}
    
	@Override
	void desenhaCorpo(PdfWriter pdfWriter, Document document, TextoContratos textoContratos, ArrayList<Movimento> listaComMovimentos, 
			 		  																		 List<Assinatura> listaComAssinaturas) throws DocumentException {

		String textoContrato1 = textoContratos.devolveContratoContaSIM(listaComMovimentos);	
		String tituloPrincipal = textoContrato1.substring(0, 78);
			
		int posicaoFinalDoParagrafoVariavel = 9 + textoContrato1.indexOf("seguinte:");
		String conteudo1 = textoContrato1.substring(78, posicaoFinalDoParagrafoVariavel);
		
		int tamanhoDoTitulo1 = 28;
		int t1 = posicaoFinalDoParagrafoVariavel + tamanhoDoTitulo1;
		String titulo1 = textoContrato1.substring(posicaoFinalDoParagrafoVariavel, t1);
	
		int tamanhoDoConteudo2 = 28 + textoContrato1.indexOf("de cada unidade consumidora.");
		String conteudo2 = textoContrato1.substring(t1, tamanhoDoConteudo2);
		
		int tamanhoDoTitulo2 = 18;
		int t2 = tamanhoDoConteudo2 + tamanhoDoTitulo2;	
		String titulo2 = textoContrato1.substring(tamanhoDoConteudo2, t2);

		int tamanhoDoConteudo3 = 13 + textoContrato1.indexOf("conveniente.");		
		String conteudo3 = textoContrato1.substring(t2, tamanhoDoConteudo3);
	
		int tamanhoDoTitulo3 = 19;
		int t3 = tamanhoDoConteudo3 + tamanhoDoTitulo3;	
		String titulo3 = textoContrato1.substring(tamanhoDoConteudo3, t3);

		int tamanhoDoConteudo4 = 6 + textoContrato1.indexOf("Civil.");		
		String conteudo4 = textoContrato1.substring(t3, tamanhoDoConteudo4);

		int tamanhoDoTitulo4 = 61;
		int t4 = tamanhoDoConteudo4 + tamanhoDoTitulo4;	
		String titulo4 = textoContrato1.substring(tamanhoDoConteudo4, t4);

		int tamanhoDoConteudo5 = 11 + textoContrato1.indexOf("anualmente.");		
		String conteudo5 = textoContrato1.substring(t4, tamanhoDoConteudo5);

		int tamanhoDoTitulo5 = 40;
		int t5 = tamanhoDoConteudo5 + tamanhoDoTitulo5;	
		String titulo5 = textoContrato1.substring(tamanhoDoConteudo5, t5);

		int tamanhoDoConteudo6 = 13 + textoContrato1.indexOf("fornecimento.");		
		String conteudo6 = textoContrato1.substring(t5, tamanhoDoConteudo6);
		
		int tamanhoDoTitulo6 = 29;
		int t6 = tamanhoDoConteudo6 + tamanhoDoTitulo6;	
		String titulo6 = textoContrato1.substring(tamanhoDoConteudo6, t6);

		String conteudo7 = textoContrato1.substring(t6, textoContrato1.length());
  				  
        document.add(devolveTitulo(tituloPrincipal));
		document.add(devolveConteudo("\n"));
        document.add(devolveConteudo(conteudo1));
        document.add(devolveTitulo(titulo1));        		  
        document.add(devolveConteudo(conteudo2));
        document.add(devolveTitulo(titulo2));        		  
        document.add(devolveConteudo(conteudo3));
        document.add(devolveTitulo(titulo3));        		  
        document.add(devolveConteudo(conteudo4));
        document.add(devolveTitulo(titulo4));        		  
        document.add(devolveConteudo(conteudo5));
        document.add(devolveTitulo(titulo5));        		  
        document.add(devolveConteudo(conteudo6));
        document.add(devolveTitulo(titulo6));  
        document.add(devolveConteudo(conteudo7));
        document.add(devolveData());		
	}

	@Override	
	void organizaSequenciaAssinaturas(PdfWriter pdfWriter, List<Assinatura> listaComAssinaturas) {
		
	    Assinatura assinatura_4 = listaComAssinaturas.get(4);
	    Assinatura assinatura_5 = listaComAssinaturas.get(5);
	    Assinatura assinatura_6 = listaComAssinaturas.get(6);
	    
	    criaEadicionaAssinaturas(pdfWriter, assinatura_4, assinatura_5, assinatura_6);	    
	}
	
}