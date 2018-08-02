package mobile.contratodigital.pdfword;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;

import android.content.Context;
import mobile.contratodigital.enums.TamanhoAssinatura;
import mobile.contratodigital.model.Assinatura;
import mobile.contratodigital.util.TextoContratos;
import sharedlib.contratodigital.model.Movimento;
import word.api.interfaces.IDocument;
import word.w2004.elements.Table;
import word.w2004.elements.tableElements.TableEle;

public class GeraWord_contratoPadrao extends GeraWord {
	
	private Context context;
	
	public GeraWord_contratoPadrao(Context _context) {
		super(_context);
		this.context = _context;
	}
	public static String NRContrato() {

		String Numero_contrato = TextoContratos.devolvecontrato();
		return Numero_contrato;		
	}
	
	@Override
	protected void desenhaCorpo(IDocument iDocument, ArrayList<Movimento> listaComMovimentos, List<Assinatura> listaComAssinaturas) {
		
		TextoContratos textoContratos = new TextoContratos(context);

		String textoContrato1 = textoContratos.devolveContratoPadrao(listaComMovimentos);	

		String titulo_parte1 = textoContrato1.substring(0, 78);
		//String titulo_parte2 = textoContrato1.substring(33, 71);
		//escreveTituloPrincipal(iDocument, textoContratos.devolveTituloDosContratos());
		escreveTituloPrincipal(iDocument, titulo_parte1);
		//escreveTituloPrincipal(iDocument, titulo_parte2);
	    
		
		final int QTD_PARAGRAFOS = 34;

		int[] posicaoTexto = new int[QTD_PARAGRAFOS]; 

		posicaoTexto[1] = textoContrato1.indexOf("PRIMEIRA FORNECEDORA");
		posicaoTexto[2] = textoContrato1.indexOf("SEGUNDA FORNECEDORA");
		posicaoTexto[3] = textoContrato1.indexOf("CLIENTE, doravante");
		posicaoTexto[4] = textoContrato1.indexOf("CLÁUSULAS CONTRATUAIS");
		posicaoTexto[5] = textoContrato1.indexOf("1. A FORNECEDORA");
		posicaoTexto[6] = textoContrato1.indexOf("1.1 Na hipótese");
		posicaoTexto[7] = textoContrato1.indexOf("1.2 Em caso");
		posicaoTexto[8] = textoContrato1.indexOf("2. O preço");
		posicaoTexto[9] = textoContrato1.indexOf("3. A CLIENTE");
		posicaoTexto[10] = textoContrato1.indexOf("3.1 A FORNECEDORA");
		posicaoTexto[11] = textoContrato1.indexOf("4. A FORNECEDORA");
		posicaoTexto[12] = textoContrato1.indexOf("4.1 A MANUTENÇÃO");
		posicaoTexto[13] = textoContrato1.indexOf("4.2 Obriga-se");
		posicaoTexto[14] = textoContrato1.indexOf("4.3 Observada");
		posicaoTexto[15] = textoContrato1.indexOf("5. De acordo");
		posicaoTexto[16] = textoContrato1.indexOf("6. Fica facultado");
		posicaoTexto[17] = textoContrato1.indexOf("7. A FORNECEDORA");
		posicaoTexto[18] = textoContrato1.indexOf("7.1 Caso a CLIENTE");
		posicaoTexto[19] = textoContrato1.indexOf("7.2. Nas hipóteses");
		posicaoTexto[20] = textoContrato1.indexOf("8. A CLIENTE");
		posicaoTexto[21] = textoContrato1.indexOf("9. Fica expressamente");
		posicaoTexto[22] = textoContrato1.indexOf("10 A CLIENTE");
		posicaoTexto[23] = textoContrato1.indexOf("10.1 A aplicação");
		posicaoTexto[24] = textoContrato1.indexOf("11. Na hipótese");
		posicaoTexto[25] = textoContrato1.indexOf("12. O presente");
		posicaoTexto[26] = textoContrato1.indexOf("12.1. Caso haja");
		posicaoTexto[27] = textoContrato1.indexOf("13. Caso a CLIENTE");
		posicaoTexto[28] = textoContrato1.indexOf("14. Eventual");
		posicaoTexto[29] = textoContrato1.indexOf("15. Este contrato");
		posicaoTexto[30] = textoContrato1.indexOf("16. Para os investimentos");
		posicaoTexto[31] = textoContrato1.indexOf("17. A CLIENTE declara");
		posicaoTexto[32] = textoContrato1.indexOf("Fica eleito o Foro");
		posicaoTexto[33] = textoContrato1.indexOf("E por estarem");
		
		String altimaLinhaDoContrato = textoContrato1.substring(posicaoTexto[33], textoContrato1.length());
		
	    String[] paragrafos = new String[QTD_PARAGRAFOS];

	    Table table = new Table(); 
	  	  	  table.addTableEle(TableEle.TD, escreveUnderline()); 

	    for(int i=1; i<QTD_PARAGRAFOS - 1; i++){
		
			int i_MaisUm = i+1;
				
			paragrafos[i] = textoContrato1.substring(posicaoTexto[i], posicaoTexto[i_MaisUm]);
		
			if(precisaSairDoPadrao(i)){
		
		  		if(precisaImprimirTitulo(i)){
		  			table.addTableEle(TableEle.TD, escreveConteudoEmTabela3(iDocument,paragrafos[i]));
		  		}		  	
		  	
			  	//imprime assinatura:
		  		if(i== 15){
		  			String height = "35";
		  			String width = "50";
		  			table.addTableEle(TableEle.TD,  escreveConteudoEmTabela2(iDocument,paragrafos[i]), devolveImagem2(iDocument,listaComAssinaturas.get(0).getRecebeAssinatura(), height, width));    

		  		// addLinhaComImagemEmTabela(iDocument,table, paragrafos[i], listaComAssinaturas.get(0).getRecebeAssinatura());
			  		//table.addTableEle(TableEle.TD, escreveConteudoEmTabela2(iDocument,paragrafos[i])); 	  	  	

		  		}else if (i== 30){
		  			String height = "35";
		  			String width = "50";
		  		//	addLinhaComImagemEmTabela(iDocument,table, paragrafos[i], listaComAssinaturas.get(1).getRecebeAssinatura());
		  			table.addTableEle(TableEle.TD,  escreveConteudoEmTabela2(iDocument,paragrafos[i]), devolveImagem2(iDocument,listaComAssinaturas.get(1).getRecebeAssinatura(), height, width));    

		  		}			  	
			}
			else{
		  		//imprime paragrafo normal:
		  		table.addTableEle(TableEle.TD, escreveConteudoEmTabela2(iDocument,paragrafos[i])); 	  	  	
		  	}
		  	//pula linha
		  	table.addTableEle(TableEle.TD, escreveConteudoEmTabela2(iDocument," "));   
		}		
		table.addTableEle(TableEle.TD, escreveConteudoEmTabela2(iDocument,altimaLinhaDoContrato));   
	  	//table.addTableEle(TableEle.TD, escreveConteudoEmTabela_AlignRIGHT(textoContratos.devolveDataAtualFormatada()));   
		 
	    //iDocument.addEle(table);
	   // iDocument.addEle(BreakLine.times(1).create());      
	}
	
	private boolean precisaSairDoPadrao(int i) {
		
		boolean entrouNoIf = false;
		
		if(i == 4 || i == 15 || i == 30){
			entrouNoIf = true;
		}
		return entrouNoIf;
	}

	private boolean precisaImprimirTitulo(int i) {
		
		boolean entrouNoIf = false;
		
		if(i == 4){
			entrouNoIf = true;
		}
		return entrouNoIf;
	}

	@Override	
	protected void organizaSequencia(IDocument iDocument, List<Assinatura> listaComAssinaturas) {
		
	    Assinatura assinatura_2 = listaComAssinaturas.get(2);
	    Assinatura assinatura_3 = listaComAssinaturas.get(3);
	    Assinatura assinatura_4 = listaComAssinaturas.get(4);
	
	    desenhaAssinaturas(iDocument, assinatura_2, assinatura_3, assinatura_4);
	}
	
}
