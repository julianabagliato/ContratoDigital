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
		 
	    String height_img = String.valueOf(TamanhoAssinatura.ALTURA.getTamanho());
	    String width_img = String.valueOf(TamanhoAssinatura.LARGURA.getTamanho());
	    
	    Table tbl = new Table(); 
	    
	    tbl.addTableEle(TableEle.TD,  escreveConteudoEmTabela("#Assinatura_empresa#"),devolveImagem(assinatura_1.getRecebeAssinatura(), height_img, width_img));    
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela(assinatura_1.getRazaoSocial()));
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("Cliente: "+assinatura_1.getNome()));
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("Cargo: "+assinatura_1.getCargo()));   
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("RG: "+assinatura_1.getRg()));   
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("CPF: "+assinatura_1.getCpf()));   

	    if(assinatura_0.getRecebeAssinatura() != null) { 	
	    	tbl.addTableEle(TableEle.TD, devolveImagem(assinatura_0.getRecebeAssinatura(), height_img, width_img), escreveConteudoEmTabela(""));      
	    }
	    if(assinatura_0.getNome() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("Testemunha: "+assinatura_0.getNome()), escreveConteudoEmTabela(""));
	    }
	    if(assinatura_0.getRg() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("RG: "+assinatura_0.getRg()), escreveConteudoEmTabela(""));   
	    }
	    if(assinatura_0.getCpf() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("CPF: "+assinatura_0.getCpf()), escreveConteudoEmTabela(""));   
	    }


	    if(assinatura_2.getRecebeAssinatura() != null) { 	
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), devolveImagem(assinatura_2.getRecebeAssinatura(), height_img, width_img));      
	    }
	    if(assinatura_2.getNome() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("Testemunha: "+assinatura_2.getNome()));
	    }
	    if(assinatura_2.getRg() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("RG: "+assinatura_2.getRg()));   
	    }
	    if(assinatura_2.getCpf() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("CPF: "+assinatura_2.getCpf()));   
	    }

	    iDocument.addEle(tbl);
	}
	
}
