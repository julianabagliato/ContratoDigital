package mobile.contratodigital.pdfword;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import android.content.Context;
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
	void organizaSequenciaAssinaturas(PdfWriter pdfWriter, List<Assinatura> listaComAssinaturas) {
		
		Assinatura assinatura_0 = listaComAssinaturas.get(0);
		Assinatura assinatura_1 = listaComAssinaturas.get(1);
		Assinatura assinatura_2 = listaComAssinaturas.get(2);
        	
		criaEadicionaAssinaturas(pdfWriter, assinatura_0, assinatura_1, assinatura_2);
	}

}