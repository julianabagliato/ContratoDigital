package mobile.contratodigital.pdfword;

import java.util.ArrayList;

import java.util.List;

import com.itextpdf.text.DocumentException;

import android.content.Context;
import android.util.Log;
import mobile.contratodigital.enums.TamanhoAssinatura;
import mobile.contratodigital.model.Assinatura;
import sharedlib.contratodigital.model.Movimento;
import word.api.interfaces.IDocument;
import word.w2004.elements.BreakLine;
import word.w2004.elements.Table;
import word.w2004.elements.tableElements.TableEle;

public class GeraWord_anexoContaSIM extends GeraWord {
	
	public GeraWord_anexoContaSIM(Context context) {
		super(context);
	}
	
	@Override
	protected void desenhaCorpo(IDocument iDocument, ArrayList<Movimento> listaComMovimentos, List<Assinatura> listaComAssinaturas) {
	
		try {
			
			Table table1 = adicionaTituloComConteudoComEndereco(iDocument, listaComMovimentos, "AnexoContaSIM");
		  	iDocument.addEle(table1);	    
			iDocument.addEle(BreakLine.times(1).create());
			
		} catch (DocumentException e) {
			Log.i("tag",""+e);
		} 			  
		
	}
		
	@Override
	protected void organizaSequencia(IDocument iDocument, List<Assinatura> listaComAssinaturas) {
		
	    Assinatura assinatura_0 = listaComAssinaturas.get(0);
	    Assinatura assinatura_1 = listaComAssinaturas.get(1);
	    Assinatura assinatura_2 = listaComAssinaturas.get(2);
		 
	    desenhaAssinaturas(iDocument, assinatura_0, assinatura_1, assinatura_2);
	}
	
}
