package mobile.contratodigital.pdfword;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.SequenciaMovAddedEmLista;
import mobile.contratodigital.enums.TamanhoAssinatura;
import mobile.contratodigital.model.Assinatura;
import mobile.contratodigital.model.PrecoPrazoConsumoPagamento;
import mobile.contratodigital.util.MoedaRS;
import mobile.contratodigital.util.Reflexao;
import mobile.contratodigital.util.TextoContratos;
import mobile.contratodigital.util.TrabalhaComImagens;
import sharedlib.contratodigital.model.Movimento;
import word.api.interfaces.IDocument;
import word.w2004.Document2004;
import word.w2004.Document2004.Encoding;
import word.w2004.elements.BreakLine;
import word.w2004.elements.Image;
import word.w2004.elements.Paragraph;
import word.w2004.elements.ParagraphPiece;
import word.w2004.elements.Table;
import word.w2004.elements.tableElements.TableEle;
import word.w2004.style.ParagraphStyle.Align;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;

public abstract class GeraWord {

	private final String FONTSIZE_TITULO = "9";
	private final String FONTSIZE_CONTEUDO = "9";
	private TextoContratos textoContratos;
	private Context context;

	public GeraWord(Context _context) {
		this.context = _context;
		textoContratos = new TextoContratos(context);
	}

	public void criaWord(String SRC_CONTRATO, ArrayList<Movimento> listaComMovimentos, List<Assinatura> listaComAssinaturas) {

		File file = new File(SRC_CONTRATO);

		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		IDocument iDocument = new Document2004();
		iDocument.encoding(Encoding.UTF_8);

		desenhaCabecalho(context, iDocument);

		// implementar:
		desenhaCorpo(iDocument, listaComMovimentos, listaComAssinaturas);

		// implementar:
		organizaSequencia(iDocument, listaComAssinaturas);

		desenhaRodape(iDocument);

		String myWord = iDocument.getContent();
		writer.println(myWord);
		writer.close();
	}

	protected void adicionaTituloComConteudoComEndereco(IDocument iDocument, ArrayList<Movimento> listaComMovimentos, String tipoAnexo) throws DocumentException {

		escreveTituloPrincipal(iDocument, "Anexo I");

		//table1.addTableEle(TableEle.TD, escreveUnderline1(devolveunderline1()));
		//table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(textoContratos.devolveTextoAnexo()));
		escreveConteudoJust(iDocument, textoContratos.devolveTextoAnexo());
		
		Table table1 = new Table(false);
		table1.addTableEle(TableEle.TD, "");
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getLogradouro_titulo()+ PrecoPrazoConsumoPagamento.getLogradouro_conteudo()));
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getNumero_titulo() + PrecoPrazoConsumoPagamento.getNumero_conteudo()));
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getBairro_titulo() + PrecoPrazoConsumoPagamento.getBairro_conteudo()));
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getCidade_titulo() + PrecoPrazoConsumoPagamento.getCidade_conteudo()));
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getEstado_titulo() + PrecoPrazoConsumoPagamento.getEstado_conteudo()));
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getCep_titulo() + PrecoPrazoConsumoPagamento.getCep_conteudo()));
		table1.addTableEle(TableEle.TD, "");
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getPrecoNegociado()));
		if (tipoAnexo.equals("AnexoContaSIM")) {
			table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getTaxaServico()));
			table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getTaxaReligue()));
		}
		table1.addTableEle(TableEle.TD, "");
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getPrazoVigencia()));
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getDataInicio()));
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getPrazoPagamento()));
		table1.addTableEle(TableEle.TD, "");
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getConsumoPrevistoEmKg()));
		table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getPrevConsumoAteFimContrato()));
		table1.addTableEle(TableEle.TD, "");
		
	    Movimento mov_equipamentosSimulados = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_equipamentosSimulados.getPosicao());

		List<String> listaTanques = new ArrayList<String>();
		List<String> listaCilindros = new ArrayList<String>();
		List<String> listaEquipamentos = new ArrayList<String>();
		List<String> listaCentral = new ArrayList<String>();
		List<String> listaRede = new ArrayList<String>();
		List<String> listaOutros = new ArrayList<String>();
		List<String> listaInvestimentosEspeciais = new ArrayList<String>();
		 
		//INICIO: ordenaItensAntesDeAdicionarNaView();
		for(String equipamento : Reflexao.devolveListaComEquipamentosAdicionados(mov_equipamentosSimulados)){
			if(equipamento.contains("TANQUES")) {
				listaTanques.add(equipamento);
			}else if(equipamento.contains("CILINDROS")) {
				listaCilindros.add(equipamento);
			}else if(equipamento.contains("EQUIPAMENTOS")) {
				listaEquipamentos.add(equipamento);
			}else if(equipamento.contains("CENTRAL")) {
				listaCentral.add(equipamento);
			}else if(equipamento.contains("REDE")) {
				listaRede.add(equipamento);
			}else if(equipamento.contains("investimentos especiais")) {
				listaInvestimentosEspeciais.add(equipamento);
			}else {
				listaOutros.add(equipamento);		
			}
		}
		//FIM ordenaItensAntesDeAdicionarNaView
		
		Table table2 = new Table(false); 
		table2.addTableEle(TableEle.TD, escreveUnderline1("____________________________________________________________________"));
		table2.addTableEle(TableEle.TD, escreveTituloEmTabelaBoldLeft("Equipamentos Objetos de Comodato:"), escreveConteudoEmTabela(""));
					
		Table table3 = new Table(true); 
		double subtotalTanques = adicionaItensNaActivityConformeListaRecebida(listaTanques, table3);
		double subtotalCilindros = adicionaItensNaActivityConformeListaRecebida(listaCilindros, table3);
		double subtotalEquipamentos = adicionaItensNaActivityConformeListaRecebida(listaEquipamentos, table3);
		double subtotal1 = subtotalTanques + subtotalCilindros + subtotalEquipamentos;
		MoedaRS moedaRS = new MoedaRS();
		String subtotalEmExtenso1 = moedaRS.converteNumeroParaExtensoReais(subtotal1);
		table3.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela(""));
		table3.addTableEle(TableEle.TD, escreveConteudoEmTabela("Subtotal A:"), escreveConteudoEmTabela(subtotalEmExtenso1));
		
		String eqpEspecial = listaInvestimentosEspeciais.get(0);
		int posicaoDivisoria = eqpEspecial.indexOf(":");
		String titulo = eqpEspecial.substring(0, posicaoDivisoria + 1);
		String linhaComItemEValor = eqpEspecial.substring(posicaoDivisoria + 1, eqpEspecial.length());
		
		Table table4 = new Table(false); 
		table4.addTableEle(TableEle.TD, escreveTituloEmTabelaBoldLeft(""), escreveConteudoEmTabela(""));
		table4.addTableEle(TableEle.TD, escreveTituloEmTabelaBoldLeft(titulo), escreveConteudoEmTabela(""));
			
		Table table5 = new Table(true); 
		double subtotalCentral = adicionaItensNaActivityConformeListaRecebida(listaCentral, table5);
		double subtotalRede = adicionaItensNaActivityConformeListaRecebida(listaRede, table5);
		double valorDoEquipamentoEspecial = separaItemDeValorEAdicionaNoLinearLayout(linhaComItemEValor, table5);	
		double subtotal2 = subtotalCentral + subtotalRede + valorDoEquipamentoEspecial;
		String subtotal2EmExtenso = moedaRS.converteNumeroParaExtensoReais(subtotal2);
		table5.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela(""));
		table5.addTableEle(TableEle.TD, escreveConteudoEmTabela("Subtotal B:"), escreveConteudoEmTabela(subtotal2EmExtenso));
	
		Table table6 = new Table(false); 
		table6.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela(""));
		
	    Table table7 = new Table(true); 
		String totalInvestimento = PrecoPrazoConsumoPagamento.getCustoTotalInvestimento();
		String[] lista = totalInvestimento.split(":");
		table7.addTableEle(TableEle.TD, escreveConteudoEmTabela(lista[0]+":"), escreveConteudoEmTabela(lista[1]));

	    Table table8 = new Table(false); 
		table8.addTableEle(TableEle.TD, escreveConteudoEmTabela(PrecoPrazoConsumoPagamento.getObservacoes_titulo() + PrecoPrazoConsumoPagamento.getObservacoes_conteudo()));
		table8.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabelaRight(textoContratos.devolveDataAtualFormatada()));
		
	  	iDocument.addEle(table1);	    
	  	iDocument.addEle(table2);	    
	  	iDocument.addEle(table3);
	  	iDocument.addEle(table4);	    
	  	iDocument.addEle(table5);	    
	  	iDocument.addEle(table6);
	  	iDocument.addEle(table7);	    
	  	iDocument.addEle(table8);
	}
	
	private double adicionaItensNaActivityConformeListaRecebida(List<String> lista, Table tableX) throws DocumentException {
		
		double subtotal = 0.0;
		
		for(String linhaComItemEValor : lista) {

			double valorDoItem = separaItemDeValorEAdicionaNoLinearLayout(linhaComItemEValor, tableX);		
			
			subtotal = subtotal + valorDoItem;
		}

		return subtotal;
	}

	private double separaItemDeValorEAdicionaNoLinearLayout(String linhaComItemEValor, Table tableX) throws DocumentException {
		
		double valorDoItem = 0.0;
		
		int posicaoDaDivisoria = linhaComItemEValor.indexOf("R$");
		
		String item = linhaComItemEValor.substring(0, posicaoDaDivisoria);
		String valor = linhaComItemEValor.substring(posicaoDaDivisoria - 1, linhaComItemEValor.length());

		try {
			int posicaoDosCentavos = valor.indexOf("(");
			String numerosComPontoVirgulaRS = valor.substring(0, posicaoDosCentavos);
			String numerosComPontoVirgula = numerosComPontoVirgulaRS.replace("R$", "");
			String numerosComVirgula = numerosComPontoVirgula.replace(".", "");
			String valorProntoParaUso = numerosComVirgula.replace(",", ".");
			valorDoItem = Double.parseDouble(valorProntoParaUso);	
		}
		catch(Exception erro) {
			Log.i("tag","nao conseguiu calcular o sub total: "+erro);			
		}
		//table1.addTableEle(TableEle.TD, escreveConteudoEmTabela(""+item+"                           "+valor));	
		tableX.addTableEle(TableEle.TD, escreveConteudoEmTabela(item), escreveConteudoEmTabela(valor));
		
		return valorDoItem;
	}

	protected Paragraph escreveUnderline1(String underline) {

		return (Paragraph) Paragraph.withPieces(ParagraphPiece.with(underline).withStyle().textColor("FFFFFF").create()).withStyle().align(word.w2004.style.ParagraphStyle.Align.CENTER).create();
	}

	private void desenhaCabecalho(Context context, IDocument iDocument) {

		byte[] imgEmBytes = TrabalhaComImagens.pegaBytesDoDrawable(context,
				context.getResources().getDrawable(R.drawable.logo_cabecalho));
		InputStream inputStream = new ByteArrayInputStream(imgEmBytes);
		iDocument
				.getHeader().addEle(
						Paragraph
								.with(Image.from_STREAM("logo", inputStream).setHeight("30").setWidth("150")
										.getContent())
								.withStyle().align(word.w2004.style.ParagraphStyle.Align.CENTER).create());
	}

	protected abstract void desenhaCorpo(IDocument iDocument, ArrayList<Movimento> listaComMovimentos,
			List<Assinatura> listaComAssinaturas);

	protected abstract void organizaSequencia(IDocument iDocument, List<Assinatura> listaComAssinaturas);
	
	protected void desenhaAssinaturas(IDocument iDocument, Assinatura ass_0, Assinatura ass_1, Assinatura ass_2) {
				 
	    String height_img = String.valueOf(TamanhoAssinatura.ALTURA.getTamanho());
	    String width_img = String.valueOf(TamanhoAssinatura.LARGURA.getTamanho());
	    
	    Table tbl = new Table(false); 
	    
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("#Assinatura_empresa#"),devolveImagem(ass_1.getRecebeAssinatura(), height_img, width_img));    
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela(ass_1.getRazaoSocial()));
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("Cliente: "+ass_1.getNome()));
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("Cargo: "+ass_1.getCargo()));   
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("RG: "+ass_1.getRg()));   
	    tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("CPF: "+ass_1.getCpf()));   

	    if(ass_0.getRecebeAssinatura() == null) { 
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("#Testemunha_1#"), escreveConteudoEmTabela(""));
	    }else {
	    	tbl.addTableEle(TableEle.TD, devolveImagem(ass_0.getRecebeAssinatura(), height_img, width_img), escreveConteudoEmTabela(""));      
	    }if(ass_0.getNome() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("Testemunha: "+ass_0.getNome()), escreveConteudoEmTabela(""));
	    }if(ass_0.getRg() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("RG: "+ass_0.getRg()), escreveConteudoEmTabela(""));   
	    }if(ass_0.getCpf() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela("CPF: "+ass_0.getCpf()), escreveConteudoEmTabela(""));   
	    }

	    if(ass_2.getRecebeAssinatura() == null) { 
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("#Testemunha_2#"));
	    }else {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), devolveImagem(ass_2.getRecebeAssinatura(), height_img, width_img));      
	    }if(ass_2.getNome() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("Testemunha: "+ass_2.getNome()));
	    }if(ass_2.getRg() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("RG: "+ass_2.getRg()));   
	    }if(ass_2.getCpf() != null) {
	    	tbl.addTableEle(TableEle.TD, escreveConteudoEmTabela(""), escreveConteudoEmTabela("CPF: "+ass_2.getCpf()));   
	    }

	    iDocument.addEle(tbl);
	}

	private void desenhaRodape(IDocument iDocument) {

		iDocument.getFooter().addEle(Paragraph.with("Consigaz Distribuidora de Gas Ltda � CAC 11 4197-9300").create());

	}

	protected void escreveTituloPrincipal(IDocument iDocument, String texto) {

		iDocument.addEle(
				Paragraph.withPieces(ParagraphPiece.with(texto).withStyle().fontSize(FONTSIZE_TITULO).bold().create())
						.withStyle().align(word.w2004.style.ParagraphStyle.Align.CENTER).create());
	}
	protected void escreveTituloBoldLeft(IDocument iDocument, String texto) {

		iDocument.addEle(
				Paragraph.withPieces(ParagraphPiece.with(texto).withStyle().fontSize(FONTSIZE_TITULO).bold().create())
						.withStyle().align(word.w2004.style.ParagraphStyle.Align.LEFT).create());
	}
	
	protected void escreveConteudoJust(IDocument iDocument, String texto) {

		iDocument.addEle(Paragraph.withPieces(ParagraphPiece.with(texto).withStyle().fontSize(FONTSIZE_TITULO).create())
				.withStyle().align(word.w2004.style.ParagraphStyle.Align.JUSTIFIED).create());
	}

	protected void addLinhaComImagemEmTabela(IDocument iDocument, Table table, String paragrafoPosicao,
			Drawable drawableAssinatura) {
		String height = "35";
		String width = "50";
		// iDocument.addEle(Paragraph.withPieces(ParagraphPiece.with(paragrafoPosicao).withStyle().fontSize(FONTSIZE_TITULO).create()).withStyle().align(word.w2004.style.ParagraphStyle.Align.JUSTIFIED).create());

		// table.addTableEle(TableEle.TD,
		// escreveConteudoEmTabela(paragrafoPosicao),
		// devolveImagem(drawableAssinatura, height, width));
		table.addTableEle(TableEle.TD, devolveImagem(drawableAssinatura, height, width));

	}

	protected Paragraph devolveImagem2(IDocument iDocument, Drawable drawableAssinatura, String height, String width) {

		byte[] imgEmBytes = TrabalhaComImagens.pegaBytesDoDrawable(context, drawableAssinatura);
		InputStream inputStream = new ByteArrayInputStream(imgEmBytes);
		iDocument.addEle(
				Paragraph.with(Image.from_STREAM("logo", inputStream).setHeight(height).setWidth(width).getContent())
						.create().withStyle().align(word.w2004.style.ParagraphStyle.Align.RIGHT).create());
		return null;
	}

	protected Paragraph devolveImagem(Drawable drawableAssinatura, String height, String width) {

		byte[] imgEmBytes = TrabalhaComImagens.pegaBytesDoDrawable(context, drawableAssinatura);
		InputStream inputStream = new ByteArrayInputStream(imgEmBytes);

		return Paragraph.with(Image.from_STREAM("logo", inputStream).setHeight(height).setWidth(width).getContent())
				.create();
	}

	protected Paragraph devolveLinhaEstilizada(String titulo, String Conteudo) {

		return Paragraph.withPieces(ParagraphPiece.with(titulo).withStyle().bold().create(),
				ParagraphPiece.with(Conteudo).create());
	}

	protected Paragraph escreveConteudoEmTabela(String paragrafo) {

		return (Paragraph) Paragraph
				.withPieces(ParagraphPiece.with(paragrafo).withStyle().fontSize(FONTSIZE_CONTEUDO).create()).withStyle()
				.align(Align.JUSTIFIED).create();
	}

	protected Paragraph escreveConteudoEmTabela2(IDocument iDocument, String paragrafo) {
		iDocument.addEle(
				Paragraph.withPieces(ParagraphPiece.with(paragrafo).withStyle().fontSize(FONTSIZE_TITULO).create())
						.withStyle().align(word.w2004.style.ParagraphStyle.Align.JUSTIFIED).create());
		return null;

	}

	protected Paragraph escreveConteudoEmTabela3(IDocument iDocument, String paragrafo) {
		iDocument
				.addEle(Paragraph
						.withPieces(
								ParagraphPiece.with(paragrafo).withStyle().bold().fontSize(FONTSIZE_TITULO).create())
						.withStyle().align(word.w2004.style.ParagraphStyle.Align.CENTER).create());
		return null;

	}

	protected Paragraph escreveConteudoEmTabelaRight(String paragrafo) {
		return (Paragraph) Paragraph
				.withPieces(ParagraphPiece.with(paragrafo).withStyle().fontSize(FONTSIZE_CONTEUDO).create()).withStyle()
				.align(Align.RIGHT).create();
	}

	protected Paragraph escreveUnderline() {

		String underline = "________________________________________________________________________________";

		return (Paragraph) Paragraph.withPieces(ParagraphPiece.with(underline).withStyle().textColor("FFFFFF").create())
				.withStyle().align(word.w2004.style.ParagraphStyle.Align.CENTER).create();
	}

	protected Paragraph escreveTituloEmTabelaBoldCenter(String paragrafo) {

		return (Paragraph) Paragraph
				.withPieces(ParagraphPiece.with(paragrafo).withStyle().fontSize(FONTSIZE_TITULO).bold().create())
				.withStyle().align(word.w2004.style.ParagraphStyle.Align.CENTER).create();
	}
	protected Paragraph escreveTituloEmTabelaBoldJust(String paragrafo) {
		return (Paragraph) Paragraph
				.withPieces(ParagraphPiece.with(paragrafo).withStyle().fontSize(FONTSIZE_TITULO).bold().create())
				.withStyle().align(word.w2004.style.ParagraphStyle.Align.JUSTIFIED).create();
	}
	protected Paragraph escreveTituloEmTabelaBoldLeft(String paragrafo) {
		return (Paragraph) Paragraph
				.withPieces(ParagraphPiece.with(paragrafo).withStyle().fontSize(FONTSIZE_TITULO).bold().create())
				.withStyle().align(word.w2004.style.ParagraphStyle.Align.LEFT).create();
	}
	

	private String devolveZero_double_CasoEstejaVazia(String valor) {
		if (valor.isEmpty() || valor.equals(".")) {
			return "0.0000";
		} else {

			String stringComSimbolos = valor;

			String stringLimpa = stringComSimbolos.replaceAll("[.]", "").replaceAll("[,]", "");

			BigDecimal parsed = null;

			StringBuilder stringBuilder = new StringBuilder();

			parsed = new BigDecimal(stringLimpa).setScale(4, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(10000),
					BigDecimal.ROUND_FLOOR);

			String parsedd = parsed.toString();

			String cleanStrin = parsedd.replace(".", ",");

			int tamanhoTotal = cleanStrin.length();

			int tamanhoAtehAvirgula = cleanStrin.substring(0, cleanStrin.indexOf(",")).length();

			stringBuilder.append(cleanStrin);

			if (tamanhoAtehAvirgula >= 4) {

				stringBuilder.insert(tamanhoTotal - (stringBuilder.substring(cleanStrin.indexOf(",") - 3).length()),
						".");
			}

			if (tamanhoAtehAvirgula >= 7) {

				stringBuilder.insert(tamanhoTotal - (stringBuilder.substring(cleanStrin.indexOf(",") - 5).length()),
						".");
			}

			valor = stringBuilder.substring(0, stringBuilder.length());
		}
		return valor;
	}
}
