package mobile.contratodigital.pdfword;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import mobile.contratodigital.enums.TamanhoAssinatura;
import mobile.contratodigital.model.Assinatura;
import mobile.contratodigital.util.TextoContratos;
import sharedlib.contratodigital.model.Movimento;
import word.api.interfaces.IDocument;
import word.w2004.elements.Table;
import word.w2004.elements.tableElements.TableEle;

public class GeraWord_contratoContaSIM extends GeraWord{
	
	private Context context;
	public GeraWord_contratoContaSIM(Context _context) {
		super(_context);
		this.context = _context;
	}
	
	@Override
	protected void desenhaCorpo(IDocument iDocument, ArrayList<Movimento> listaComMovimentos, List<Assinatura> listaComAssinaturas) {
			
		TextoContratos textoContratos = new TextoContratos(context);

		String textoContrato1 = textoContratos.devolveContratoContaSIM(listaComMovimentos);	
	
		 NRContrato();
		
		String tituloPrincipal = textoContrato1.substring(0, 78);

		escreveTituloPrincipal(iDocument, tituloPrincipal);

		final int QTD_PARAGRAFOS = 67;

		int[] posicaoTexto = new int[QTD_PARAGRAFOS]; 
		
		posicaoTexto[1] = textoContrato1.indexOf("PRIMEIRA FORNECEDORA:");
		posicaoTexto[2] = textoContrato1.indexOf("SEGUNDA FORNECEDORA:");
		posicaoTexto[3] = textoContrato1.indexOf("TERCEIRA FORNECEDORA:");
		posicaoTexto[4] = textoContrato1.indexOf("CLIENTE, doravante");
		posicaoTexto[5] = textoContrato1.indexOf("As três fornecedoras,");	
		posicaoTexto[6] = textoContrato1.indexOf("I – Do Fornecimento do GLP");
		posicaoTexto[7] = textoContrato1.indexOf("1. As FORNECEDORAS se obrigam,");
		posicaoTexto[8] = textoContrato1.indexOf("1.1. Na hipótese");
		posicaoTexto[9] = textoContrato1.indexOf("1.2 Em caso");
		posicaoTexto[10] = textoContrato1.indexOf("2. O preço");
		posicaoTexto[11] = textoContrato1.indexOf("3. Para fins");
		posicaoTexto[12] = textoContrato1.indexOf("II – Do Comodato");
		posicaoTexto[13] = textoContrato1.indexOf("4. As FORNECEDORAS emprestam");
		posicaoTexto[14] = textoContrato1.indexOf("4.1. O CONDOMÍNIO");
		posicaoTexto[15] = textoContrato1.indexOf("4.2 A MANUTENÇÃO");
		posicaoTexto[16] = textoContrato1.indexOf("4.3. As FORNECEDORAS");
		posicaoTexto[17] = textoContrato1.indexOf("4.4. Observada");
		posicaoTexto[18] = textoContrato1.indexOf("5. De acordo");
		posicaoTexto[19] = textoContrato1.indexOf("6. Fica facultado");
		posicaoTexto[20] = textoContrato1.indexOf("7. Obriga-se");
		posicaoTexto[21] = textoContrato1.indexOf("III – Da Rescisão");
		posicaoTexto[22] = textoContrato1.indexOf("8. As FORNECEDORAS");
		posicaoTexto[23] = textoContrato1.indexOf("a)aquisição,");
		posicaoTexto[24] = textoContrato1.indexOf("8.1 Caso");
		posicaoTexto[25] = textoContrato1.indexOf("8.2. Nas hipóteses");
		posicaoTexto[26] = textoContrato1.indexOf("9. O CONDOMÍNIO");
		posicaoTexto[27] = textoContrato1.indexOf("10. Fica");
		posicaoTexto[28] = textoContrato1.indexOf("IV – Do faturamento");
		posicaoTexto[29] = textoContrato1.indexOf("11. As FORNECEDORAS");	
		posicaoTexto[30] = textoContrato1.indexOf("11.1 O FORNECIMENTO");
		posicaoTexto[31] = textoContrato1.indexOf("12. O consumo");
		posicaoTexto[32] = textoContrato1.indexOf("13. Na hipótese");
		posicaoTexto[33] = textoContrato1.indexOf("14. CASO");
		posicaoTexto[34] = textoContrato1.indexOf("15. O CONDOMÍNIO");
		posicaoTexto[35] = textoContrato1.indexOf("16. O CONDOMÍNIO");
		posicaoTexto[36] = textoContrato1.indexOf("17. Faculta-se");
		posicaoTexto[37] = textoContrato1.indexOf("18. Faculta-se");
		posicaoTexto[38] = textoContrato1.indexOf("19. FICA");
		posicaoTexto[39] = textoContrato1.indexOf("20. NA HIPÓTESE");
		posicaoTexto[40] = textoContrato1.indexOf("21. Os valores");
		posicaoTexto[41] = textoContrato1.indexOf("V – Da inadimplência");
		posicaoTexto[42] = textoContrato1.indexOf("22. NÃO PAGAS");
		posicaoTexto[43] = textoContrato1.indexOf("A) PROTESTO");
		posicaoTexto[44] = textoContrato1.indexOf("23. CORTADO");
		posicaoTexto[45] = textoContrato1.indexOf("24. As FORNECEDORAS");
		posicaoTexto[46] = textoContrato1.indexOf("25. Verificada");
		posicaoTexto[47] = textoContrato1.indexOf("VI – Das disposições gerais");
		posicaoTexto[48] = textoContrato1.indexOf("26. A manutenção");
		posicaoTexto[49] = textoContrato1.indexOf("26.1 Caso");
		posicaoTexto[50] = textoContrato1.indexOf("27. Na hipótese");
		posicaoTexto[51] = textoContrato1.indexOf("27.1 Caso");
		posicaoTexto[52] = textoContrato1.indexOf("28. A MANUTENÇÃO");
		posicaoTexto[53] = textoContrato1.indexOf("29. Findo");
		posicaoTexto[54] = textoContrato1.indexOf("30. No ato");
		posicaoTexto[55] = textoContrato1.indexOf("30.1 CASO");
		posicaoTexto[56] = textoContrato1.indexOf("31. O presente");
		posicaoTexto[57] = textoContrato1.indexOf("31.1. Caso");
		posicaoTexto[58] = textoContrato1.indexOf("32. Caso");
		posicaoTexto[59] = textoContrato1.indexOf("33. Eventual");
		posicaoTexto[60] = textoContrato1.indexOf("34. Este contrato");
		posicaoTexto[61] = textoContrato1.indexOf("35. Para os investimentos ");
		posicaoTexto[62] = textoContrato1.indexOf("36. O CONDOMÍNIO se obriga ");
		posicaoTexto[63] = textoContrato1.indexOf("37. O CONDOMÍNIO declara ");
		posicaoTexto[64] = textoContrato1.indexOf("38. O abastecimento do GLP");
		posicaoTexto[65] = textoContrato1.indexOf("39. Fica eleito o Foro");

		posicaoTexto[66] = textoContrato1.indexOf("E por estarem");
		
		String altimaLinhaDoContrato = textoContrato1.substring(posicaoTexto[66], textoContrato1.length());
		
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
		  		String height = "35";
	  			String width = "50";	
		  		  		
			  	//imprime assinatura:
		  		switch(i){
		  		
		  		case 17:
		  			table.addTableEle(TableEle.TD,  escreveConteudoEmTabela2(iDocument,paragrafos[i]), devolveImagem2(iDocument,listaComAssinaturas.get(0).getRecebeAssinatura(), height, width));    
		  				 break;
		  		case 33:
		  			table.addTableEle(TableEle.TD,  escreveConteudoEmTabela2(iDocument,paragrafos[i]), devolveImagem2(iDocument,listaComAssinaturas.get(1).getRecebeAssinatura(), height, width));    
		  				 break;
		  				 
		  		case 50: 
		  			table.addTableEle(TableEle.TD,  escreveConteudoEmTabela2(iDocument,paragrafos[i]), devolveImagem2(iDocument,listaComAssinaturas.get(2).getRecebeAssinatura(), height, width));    
		  				 break;
			  	case 65: 
		  			table.addTableEle(TableEle.TD,  escreveConteudoEmTabela2(iDocument,paragrafos[i]), devolveImagem2(iDocument,listaComAssinaturas.get(3).getRecebeAssinatura(), height, width));    
		  				 break;
		  		}		
			}
			else{
		  		//imprime paragrafo normal:
		  		table.addTableEle(TableEle.TD, escreveConteudoEmTabela2(iDocument,paragrafos[i])); 	  	  	
		  	}
		  	//pula linha
		  	table.addTableEle(TableEle.TD,  escreveConteudoEmTabela2(iDocument," "));   
		}		
		table.addTableEle(TableEle.TD,  escreveConteudoEmTabela2(iDocument,altimaLinhaDoContrato));   
	//  	table.addTableEle(TableEle.TD, escreveConteudoEmTabela_AlignRIGHT(textoContratos.devolveDataAtualFormatada()));   
		 
	 //   iDocument.addEle(table);
	//    iDocument.addEle(BreakLine.times(1).create());       
	}
	
	public static String NRContrato() {
	

		

		String Numero_contrato = TextoContratos.devolvecontrato();
		return Numero_contrato;
		
		
	}

	private boolean precisaSairDoPadrao(int i) {

		boolean entrouNoIf = false;
		
		if(i == 6 || i == 12 || i == 17 || i == 21 || i == 28 || i == 29 || i == 41 || i == 33 || i == 47 || i == 65 || i == 50 ){
			entrouNoIf = true;
		}
		return entrouNoIf;
	}

	private boolean precisaImprimirTitulo(int i) {
		
		boolean entrouNoIf = false;
		
		if(i == 6 || i == 12 || i == 21 || i == 28 || i == 41 || i == 47){
			entrouNoIf = true;
		}
		return entrouNoIf;
	}
	
	@Override
	protected void organizaSequencia(IDocument iDocument, List<Assinatura> listaComAssinaturas) {
		
	    Assinatura assinatura_4 = listaComAssinaturas.get(4);
	    Assinatura assinatura_5 = listaComAssinaturas.get(5);
	    Assinatura assinatura_6 = listaComAssinaturas.get(6);
	   
	    desenhaAssinaturas(iDocument, assinatura_4, assinatura_5, assinatura_6);
	}
			
}
