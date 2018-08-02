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
	protected void organizaSequenciaAssinaturas(PdfWriter pdfWriter, List<Assinatura> listaComAssinaturas) {
	
		Assinatura assinatura_2 = listaComAssinaturas.get(2);
		Assinatura assinatura_3 = listaComAssinaturas.get(3);
		Assinatura assinatura_4 = listaComAssinaturas.get(4);
				
		criaEadicionaAssinaturas(pdfWriter, assinatura_2, assinatura_3, assinatura_4);			 
	}

}