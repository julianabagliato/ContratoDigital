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
	protected void desenhaAssinaturas(IDocument iDocument, List<Assinatura> listaComAssinaturas) {
		
	    Assinatura assinatura_0 = listaComAssinaturas.get(0);
	    Assinatura assinatura_1 = listaComAssinaturas.get(1);
	    Assinatura assinatura_2 = listaComAssinaturas.get(2);
	    Assinatura assinatura_3 = listaComAssinaturas.get(3);
		 
	    String height_img = String.valueOf(TamanhoAssinatura.ALTURA.getTamanho());
	    String width_img = String.valueOf(TamanhoAssinatura.LARGURA.getTamanho());
	    
	    Table tbl = new Table(); 
	    
	    tbl.addTableEle(TableEle.TD,escreveConteudoEmTabela("#Assinatura_empresa#"), devolveImagem(assinatura_2.getRecebeAssinatura(), height_img, width_img));    
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela(""+assinatura_2.getRazaoSocial()));
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("Cliente: "+assinatura_2.getNome()));
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("Cargo: "+assinatura_2.getCargo()));   
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("RG: "+assinatura_2.getRg()));   
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("CPF: "+assinatura_2.getCpf()));   

	    
	    tbl.addTableEle(TableEle.TD, devolveImagem(assinatura_1.getRecebeAssinatura(), height_img, width_img), devolveImagem(assinatura_3.getRecebeAssinatura(), height_img, width_img));    
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("Testemunha: "+assinatura_1.getNome()), escreveConteudoEmTabela("Testemunha: "+assinatura_3.getNome()));
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("Cargo: "+assinatura_1.getCargo()), escreveConteudoEmTabela("Cargo: "+assinatura_3.getCargo()));   
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("RG: "+assinatura_1.getRg()), escreveConteudoEmTabela("RG: "+assinatura_3.getRg()));   
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("CPF: "+assinatura_1.getCpf()), escreveConteudoEmTabela("CPF: "+assinatura_3.getCpf()));   
		
	    iDocument.addEle(tbl);
	}
	
}
