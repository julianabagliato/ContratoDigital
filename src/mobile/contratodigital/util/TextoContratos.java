package mobile.contratodigital.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import android.content.Context;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.enums.SequenciaMovAddedEmLista;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.model.Representante;

public class TextoContratos {
	   
	private Context context;
	private static Context context2;
	
	public TextoContratos(Context _context){
		this.context = _context;
		TextoContratos.context2 = _context;
	}
	public static String devolvecontrato(){
		
		Date data =  new Date();
		
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm"); 
    	
    	String dataFormatada = dateFormat.format(data);
    	
    	Dao dao = new Dao(context2);
    	Representante representante = (Representante) dao.devolveObjeto(Representante.class);

    	String numeroContrato = dataFormatada + representante.getCod_rep();
  
		return numeroContrato;
	}
	public String devolveDataAtualFormatada(){
    	
    	Date data =  new Date();
    	Locale locale = new Locale("pt","BR");
    	DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", locale); 
    	String dataFormatada = dateFormat.format(data);
    	String dataAtual = "Barueri, "+dataFormatada+". \n";
  
    	return dataAtual;
    }

	public String devolveTextoAnexo(){
	
		return "Este anexo é parte integrante do contrato, obrigando, deste modo, às partes quanto ao seu teor, o qual pode ser alterado sem a necessidade de modificação do instrumento principal.";
	}
	
	private String geraNumeroContrato() {
		
	  	Date data =  new Date();
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm"); 
    	String dataFormatada = dateFormat.format(data);
    	
    	Dao dao = new Dao(context);
    	Representante representante = (Representante) dao.devolveObjeto(Representante.class);

    	String numeroContrato = dataFormatada + representante.getCod_rep();
  
		return numeroContrato;
	}
	
	public String devolveContratoPadrao(ArrayList<Movimento> listaComMovimentos){
		
		Movimento mov_informacoesDoCliente = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_informacoesCliente.getPosicao());
		Movimento mov_enderecoPadrao = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_enderecoPadrao.getPosicao());
		
		String representadoPor = "";
		String CPFouCNPJ = "";
		String inscricaoEstadualOuRG = "";
		
		 
		 if(mov_informacoesDoCliente.getInformacao_4().length() > 14){
			 
			 CPFouCNPJ = "CNPJ  sob o nº " + mov_informacoesDoCliente.getInformacao_4();
		 }else{
			 CPFouCNPJ = "CPF  sob o nº " + mov_informacoesDoCliente.getInformacao_4();
		 }
		 
		 
		 if(mov_informacoesDoCliente.getInformacao_5().length() >= 12){
			 
			 inscricaoEstadualOuRG = "Inscrição Estadual  sob o nº " + mov_informacoesDoCliente.getInformacao_5();
		 }else{
			 inscricaoEstadualOuRG = "RG sob o nº " + mov_informacoesDoCliente.getInformacao_5();
		 }
		 
		 
		 Movimento mov_representacao = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_representacao.getPosicao());
			
		 if(mov_representacao == null) {
			 
			 representadoPor = mov_informacoesDoCliente.getInformacao_1(); 			 				
		 }else {
			 representadoPor = mov_representacao.getInformacao_1()+", representado por "+ mov_informacoesDoCliente.getInformacao_1(); 			 
		 }
		

	String textoContrato = "Contrato de Fornecimento de GLP e Comodato de Instalações Nº "+geraNumeroContrato()+"\n"
	//String textoContrato = "Contrato de Fornecimento de GLP e \nComodato de Instalações Nº 0000/2017\n"
	+
	"PRIMEIRA FORNECEDORA E COMODANTE: CONSIGAZ DISTRIBUIDORA DE GÁS LTDA, com sede na Rua José Pereira Sobrinho, nº 485 - Sítio Mutinga – Barueri/SP, CNPJ nº 01.597.589/0002-09 e Inscrição Estadual nº 206.190.522.112, neste ato representada por seu representante abaixo assinado e identificado, a seguir designada FORNECEDORA, e.\n\n"
	+
	"SEGUNDA FORNECEDORA: GASBALL ARMAZENADORA E DISTRIBUIDORA LTDA, com sede na Rua Eduardo Elias Zahran, nº. 127 – Fazenda Bonfim – Paulínia/SP, CNPJ nº. 02.430.968/0001-83 e Inscrição Estadual nº. 513.060.943.110, neste ato representada por seu representante abaixo assinado e identificado, a seguir designada simplesmente FORNECEDORA.\n\n"
	+ 
	"CLIENTE, doravante simplesmente designado “CLIENTE”: "+representadoPor+", estabelecimento com sede em "+mov_enderecoPadrao.getInformacao_1() +" "+ mov_enderecoPadrao.getInformacao_2() +", "+ mov_enderecoPadrao.getInformacao_3() +", "+ mov_enderecoPadrao.getInformacao_4() +"/"+ mov_enderecoPadrao.getInformacao_5() +" "+ mov_enderecoPadrao.getInformacao_6()+ ", "+ CPFouCNPJ +" e "+ inscricaoEstadualOuRG +", neste ato por seu representante legal abaixo assinado e identificado.\n\n"
		+
	"CLÁUSULAS CONTRATUAIS\n"
	+
	"1. A FORNECEDORA se obriga, neste ato, a fornecer à CLIENTE gás liquefeito de petróleo, em quantidade necessária e suficiente ao desenvolvimento das atividades da CLIENTE, que deverá adquirir o GLP exclusivamente da FORNECEDORA. O gás será fornecido por meio de entregas automáticas programadas pela FORNECEDORA ou, se necessário, mediante pedidos efetuados pela CLIENTE com antecedência mínima de 48 (quarenta e oito horas).\n\n"
	+ 
	"1.1 Na hipótese da CLIENTE recusar alguma entrega programada de GLP, solicitando, porém, posteriormente, abastecimento emergencial, antes das 48 horas previstas na cláusula 1, fica a FORNECEDORA desde já autorizada a cobrar da CLIENTE uma sobretaxa de 30% (trinta por cento) sobre o valor do gás praticado no mencionado fornecimento emergencial.\n\n"
	+
	"1.2 Em caso de desabastecimento de GLP no mercado, a FORNECEDORA se reserva o direito de restringir as quantidades a fornecer à CLIENTE, ou mesmo de recusar o fornecimento, até que o abastecimento seja normalizado, não cabendo à FORNECEDORA, nesta hipótese, qualquer responsabilidade pelos prejuízos eventualmente advindos à CLIENTE pela falta de gás, sendo que a FORNECEDORA autoriza a CLIENTE a adquirir o GLP de outro fornecedor, única e exclusivamente, durante o período em que, comprovadamente, persistir tal situação.\n\n"
	+
	"2. O preço de venda do gás será reajustado sempre que houver alteração de qualquer um dos fatores que o compõe, como custos de matéria prima, tributos, componentes do preço do GLP ou de qualquer dos custos operacionais de distribuição e serviços.\n\n"
	+
	"3. A CLIENTE pagará o gás que lhe foi fornecido no prazo previsto no Anexo I deste instrumento, mediante cobrança bancária a cargo da FORNECEDORA ficando desde já estipulado que o pagamento após o vencimento implicará na incidência, sobre os valores em atraso, de multa de 2% (dois por cento) e juros por dia de atraso.\n\n"
	+
	"3.1 A FORNECEDORA se reserva o direito de modificar as condições de pagamento previstas no Anexo I do presente instrumento, inclusive exigindo pagamento à vista dos abastecimentos futuros, sempre que houver piora na qualidade do crédito da CLIENTE, indicada pela falta de pagamento de qualquer abastecimento, pela existência de apontamentos negativos junto a órgãos de proteção ao crédito, protesto de títulos, distribuição de ações que indiquem inadimplemento de obrigações, etc.\n\n"
	+
	"4. A FORNECEDORA empresta neste ato à CLIENTE os equipamentos especificados no Anexo I, sendo que a CLIENTE se obriga a zelar e a conservar os equipamentos como se fossem seus, bem como a tomar todas as precauções de segurança que lhe couberem, devendo devolvê-los à FORNECEDORA quando do término do presente contrato, no mesmo estado em que os recebeu, ressalvado o desgaste de uso natural.\n\n"
	+
	"4.1 A MANUTENÇÃO dos equipamentos ora emprestados, será efetuada exclusivamente pela FORNECEDORA ou por empresa por ela credenciada, devidamente identificada, correndo por sua conta todas as respectivas despesas, ressalvadas as despesas referentes a problemas ou defeitos apresentados nos bens emprestados em decorrência de mau uso e de operação ou manutenção dos referidos equipamentos por pessoas não autorizadas, as quais serão cobradas pela FORNECEDORA. Ademais, a CLIENTE se obriga a realizar a manutenção preventiva e corretiva dos equipamentos especiais, prumadas, instalações internas de unidades condominiais e dos equipamentos de sua propriedade ou não que utilizam o GLP, nestes casos as despesas correrão exclusivamente por sua conta.\n\n"
	+
	"4.2 Obriga-se a CLIENTE a não permitir a manipulação ou a operação dos equipamentos objeto deste contrato por pessoas não autorizadas ou credenciadas pela CONSIGAZ, respondendo a CLIENTE por todo e qualquer prejuízo causado aos equipamentos, à FORNECEDORA ou a terceiros em razão do descumprimento desta cláusula, restando autorizada a FORNECEDORA a vistoriar os equipamentos emprestados sempre que julgar conveniente.\n\n"
	+
	"4.3 Observada qualquer irregularidade que impossibilite ou prejudique o fornecimento ou medição de GLP ou que afete de qualquer maneira a segurança, como vazamento na rede de gás, defeito nos equipamentos, entre outros, a CLIENTE se obriga a imediatamente paralisar o seu funcionamento e solicitar os serviços de assistência técnica da FORNECEDORA.\n\n"
	+
	"5. De acordo com o §1º, do artigo 6º da Portaria ANP nº 47/99 a FORNECEDORA é responsável pelas instalações de GLP até o primeiro regulador de pressão existente na linha de abastecimento que operar enquanto as instalações estiverem sendo abastecidas por ela.\n\n"
	+
	"6. Fica facultado à FORNECEDORA substituir, a seu critério, os equipamentos ora emprestados por outros de mesmo gênero, de capacidade superior ou inferior, adequada ao nível de consumo da CLIENTE, sempre que julgar necessário, sem que isto descaracterize o comodato ora firmado.\n\n"
	+
	"7. A FORNECEDORA poderá considerar rescindido de pleno direito o presente contrato, independentemente de prévia notificação, interpelação ou aviso, caso ocorra alguma das seguintes hipóteses: aquisição, pela CLIENTE, de GLP fornecido por empresa diversa da FORNECEDORA; superveniência de interdição ou fechamento administrativo; pedido ou declaração de falência, recuperação judicial ou insolvência da CLIENTE; manutenção ou operação dos equipamentos por pessoas não autorizadas ou credenciadas pela FORNECEDORA; remoção dos bens emprestados para outro local sem a autorização expressa da FORNECEDORA; substituição do GLP por qualquer outra fonte de energia, inclusive gás natural; ausência de consumo por mais de 60 dias, falta e/ou insuficiência do consumo previsto no Anexo I; descumprimento pela CLIENTE de qualquer obrigação por ela assumida no presente instrumento.\n\n"
	+
	"7.1 Caso a CLIENTE rescinda ou dê causa à rescisão deste contrato durante sua vigência responderá por multa rescisória calculada da seguinte forma: valor dos equipamentos emprestados constante no Anexo I, reduzido proporcionalmente ao tempo de cumprimento do contrato, adicionado de um terço do consumo previsto no Anexo I restante para o término do contrato, de acordo com o preço do quilo de GLP vigente no dia da rescisão. Tal multa será paga em uma só parcela, vencível no 5º (quinto) dia posterior a rescisão.\n\n"
	+
	"7.2. Nas hipóteses previstas na Cláusula 7.1, a FORNECEDORA poderá exigir da CLIENTE a compra dos equipamentos especiais e o pagamento dos investimentos especiais previstos no Anexo I do presente contrato, no estado em que se encontrem, pelo valor constante do Anexo I atualizado pelo IGPM.\n\n"
	+
	"8. A CLIENTE poderá dar por rescindido o presente contrato na hipótese da FORNECEDORA não cumprir com as obrigações aqui assumidas ou na superveniência de interdição ou fechamento administrativo, pedido ou declaração de falência, recuperação judicial ou insolvência da FORNECEDORA. Rescindido o contrato com base nesta cláusula, responderá a FORNECEDORA por uma multa, em favor da CLIENTE, equivalente a dez por cento do valor dos bens emprestados constante do Anexo I\n\n"
	+
	"9. Fica expressamente ressalvado à parte inocente o direito de pleitear perdas e danos, caso as multas estipuladas neste contrato sejam insuficientes para a cobertura dos prejuízos sofridos, nos termos do parágrafo único, do art. 416, do novo Código Civil.\n\n"
	+
	"10 A CLIENTE desde já reconhece que os equipamentos emprestados são de propriedade da FORNECEDORA, desta forma, obriga-se a CLIENTE a permitir à FORNECEDORA, de imediato, a retirada dos equipamentos emprestados. A inobservância desta disposição, a partir do término do prazo estipulado em notificação, acarretará na incidência de multa diária por retenção no valor de 2% do valor dos equipamentos emprestados, até a data da efetiva devolução ou retirada dos bens, limitado ao valor total dos equipamentos emprestados previsto no Anexo I, ocasião em que a referida multa constituíra titulo executivo extrajudicial nos termos do artigo 585 do Código de Processo Civil.\n\n"
	+
	"10.1 A aplicação da multa prevista na Cláusula 10 não transfere a propriedade dos bens, razão pela qual não prejudica a adoção das medidas judiciais cabíveis em face do esbulho possessório que ficará de pleno direito, caracterizado.\n\n"
	+
	"11. Na hipótese de comodato de cilindros transportáveis, caso não ocorra a sua devolução ou sejam devolvidos cilindros de marca diversa da CONSIGAZ ou GASBALL, os cilindros não devolvidos serão cobrados de acordo com o valor da tabela de preços aplicável pela FORNECEDORA.\n\n"
	+
	"12. O presente contrato terá a duração especificada no Anexo I deste instrumento, renovando-se por iguais períodos sucessivos a menos que haja oposição de qualquer das partes, manifestada por escrito e com antecedência mínima de 90 (noventa) dias da data de seu vencimento, sendo que na sua ausência renovam-se todas as suas cláusulas.\n\n"
	+
	"12.1. Caso haja a denúncia do contrato na forma especificada na cláusula anterior, e ao final do término do contrato a CLIENTE ainda não tiver consumido a quantidade prevista no Anexo I, a diferença será cobrada pelo preço do quilo do GLP vigente na data de encerramento do negócio jurídico.\n\n"
	+
	"13. Caso a CLIENTE se oponha à renovação do contrato em razão de melhor oferta de terceiro, fica desde já concedido à FORNECEDORA o direito de preferência.\n\n"
	+
	"14. Eventual tolerância de uma parte a infrações ou descumprimento das disposições do presente contrato cometidas pela outra parte, será considerada como ato de mera liberalidade não se constituindo em perdão, precedente, novação ou renúncia a direitos que a legislação ou o contrato assegurem às partes.\n\n"
	+
	"15. Este contrato continuará em vigor, ainda que qualquer das partes seja objeto de incorporação, fusão, cisão, cessão ou qualquer alteração contratual ou societária, obrigando-se a parte a comunicar o fato, imediatamente a outra, bem como dar ciência aos sucessores da existência do presente instrumento, sob pena de responsabilizar-se solidariamente pelos prejuízos causados por rescisão imotivada do sucessor. A referida comunicação será anexada ao presente contrato fazendo parte integrante deste.\n\n"
	+
	"16. Para os investimentos e equipamentos especiais poderá ser emitida nota fiscal com a natureza de bonificação, o que não prejudicará o disposto na Cláusula 7.2, ou seja, referida natureza terá valor apenas para questões fiscais.\n \n"
	+
	"17. A CLIENTE declara que recebeu o “Manual do Cliente” elaborado pela FORNECEDORA, o qual também encontra-se disponível para “download” no site www.consigaz.com.br. Ainda, a  CLIENTE se compromete a observar os procedimentos descritos no referido manual, com destaque aos relativos à segurança.\n\n "
	+
	"18. Fica eleito o Foro de Barueri-SP para dirimir quaisquer dúvidas ou litígios oriundos do presente contrato, com renúncia expressa de qualquer outro, por mais privilegiado que seja. \n \n  E por estarem assim justas e contratadas, assinam o presente instrumento nas suas duas vias de igual teor, na presença de duas testemunhas.\n\n";
	
	return textoContrato;
	}
	
	
	public String devolveContratoContaSIM(ArrayList<Movimento> listaComMovimentos){
		
		Movimento mov_informacoesDoCliente = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_informacoesCliente.getPosicao());
		Movimento mov_enderecoPadrao = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_enderecoPadrao.getPosicao());
		
		String representadoPor = "";
		String CPFouCNPJ = "";
		String inscricaoEstadualOuRG = "";
		 
		 if(mov_informacoesDoCliente.getInformacao_4().length() > 14){
			 
			 CPFouCNPJ = "CNPJ  sob o nº " + mov_informacoesDoCliente.getInformacao_4();
		 }else{
			 CPFouCNPJ = "CPF  sob o nº " + mov_informacoesDoCliente.getInformacao_4();
		 }
		 
		 
		 if(mov_informacoesDoCliente.getInformacao_5().length() >= 12){
			 
			 inscricaoEstadualOuRG = "Inscrição Estadual  sob o nº " + mov_informacoesDoCliente.getInformacao_5();
		 }else{
			 inscricaoEstadualOuRG = "RG sob o nº " + mov_informacoesDoCliente.getInformacao_5();
		 }
	
		 
		 Movimento mov_representacao = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_representacao.getPosicao());
			
		 if(mov_representacao == null) {
			 
			 representadoPor = mov_informacoesDoCliente.getInformacao_1(); 			 				
		 }else {
			 representadoPor = mov_representacao.getInformacao_1()+", representado por "+ mov_informacoesDoCliente.getInformacao_1(); 			 
		 }

	
	String textoContrato = "Contrato de Fornecimento de GLP e Comodato de Instalações Nº "+geraNumeroContrato()+"\n" 
	//String textoContrato = "Contrato de Fornecimento de GLP e Comodato de Instalações Nº XXXX/2017\n\n" 
	+
	"PRIMEIRA FORNECEDORA: CONSIGAZ DISTRIBUIDORA DE GÁS LTDA., com sede na Rua José Pereira Sobrinho, nº 485 - Sítio Mutinga – Barueri/SP, CNPJ nº 01.597.589/0002-09 e Inscrição Estadual nº 206.190.522.112, neste ato representada por seu representante abaixo assinado e identificado, a seguir designada simplesmente CONSIGAZ;\n\n"
	+
	"SEGUNDA FORNECEDORA: GASBALL ARMAZENADORA E DISTRIBUIDORA LTDA, com sede na Rua Eduardo Elias Zahran, nº. 127 – Fazenda Bonfim – Paulínia/SP, CNPJ nº. 02.430.968/0001-83 e Inscrição Estadual nº. 513.060.943.110, neste ato representada por seu representante abaixo assinado e identificado, a seguir designada simplesmente GASBALL;\n\n"
	+
	"TERCEIRA FORNECEDORA: PROPANGÁS LTDA, com sede na Rodovia SP 332, km 146,6, S/N – Caixa Postal 95, CEP 13150-000, Cosmópolis/SP, CNPJ nº 03.013.594/0001-63 e Inscrição Estadual nº 276.036.008.117, neste ato representada por seu representante abaixo assinado e identificado, a seguir designada simplesmente PROPANGAS.\n\n"
	+
	"CLIENTE, doravante simplesmente designado “CONDOMÍNIO”: "+representadoPor+", estabelecimento com sede em "+mov_enderecoPadrao.getInformacao_1() +" "+ mov_enderecoPadrao.getInformacao_2() +", "+ mov_enderecoPadrao.getInformacao_3() +", "+ mov_enderecoPadrao.getInformacao_4() +"/"+ mov_enderecoPadrao.getInformacao_5() +" "+ mov_enderecoPadrao.getInformacao_6()+ ", "+CPFouCNPJ+" e "+inscricaoEstadualOuRG+", neste ato por seu representante legal abaixo assinado e identificado.\n\n"
	+
	"As três fornecedoras, a seguir denominadas simplesmente FORNECEDORAS OU COMODANTES, e o CONDOMÍNIO tem entre si justo e acordado o seguinte:\n" 
	+
	"I – Do Fornecimento do GLP\n\n"
	+
	"1. As FORNECEDORAS se obrigam, neste ato, a fornecer ao CONDOMÍNIO gás liquefeito de petróleo. O gás será fornecido por meio de entregas automáticas programadas pelas FORNECEDORAS ou, se necessário, mediante pedidos efetuados pelo CONDOMÍNIO com antecedência mínima de 48 (quarenta e oito horas).\n\n"
	+
	"1.1. Na hipótese do CONDOMÍNIO recusar alguma entrega programada de GLP, solicitando, porém, posteriormente, abastecimento emergencial, antes das 48 horas previstas na cláusula 1, ficam as FORNECEDORAS desde já autorizadas a cobrar do CONDOMÍNIO uma sobretaxa de 30% (trinta por cento) sobre o valor do abastecimento emergencial.\n\n"
	+
	"1.2 Em caso de desabastecimento de GLP no mercado, as FORNECEDORAS se reservam o direito de restringir as quantidades a fornecer ao CONDOMÍNIO, ou mesmo de recusar o fornecimento, até que o abastecimento seja normalizado, não cabendo às FORNECEDORAS, nesta hipótese, qualquer responsabilidade pelos prejuízos eventualmente advindos ao CONDOMÍNIO pela falta de gás, sendo que as FORNECEDORAS autorizam o CONDOMÍNIO a adquirir o GLP de outro fornecedor, única e exclusivamente, durante o período em que, comprovadamente, persistir tal situação.\n\n"
	+
	"2. O preço de venda do gás será reajustado sempre que houver alteração de qualquer um dos fatores que o compõe, como custos de matéria prima, tributos, componentes do preço do GLP ou de qualquer dos custos operacionais de distribuição e serviços.\n\n"
	+
	"3. Para fins deste contrato e para todos os efeitos legais, considera-se vendido e consumido o gás pelo condômino ou morador tão logo passe ele pelo medidor individual de cada unidade consumidora.\n"
	+

	"II – Do Comodato\n\n"
	+
	"4. As FORNECEDORAS emprestam neste ato ao CONDOMÍNIO os equipamentos especificados no Anexo I. Em contrapartida ao comodato, o CONDOMÍNIO concede às FORNECEDORAS, pelo prazo deste contrato e de suas renovações, EXCLUSIVIDADE no fornecimento de GLP (Gás Liquefeito de Petróleo), desta forma, fica o CONDOMÍNIO proibido de adquirir GLP, em qualquer quantidade, de outro fornecedor.\n\n"
	+
	"4.1. O CONDOMÍNIO se obriga a zelar e a conservar os equipamentos como se fossem seus, bem como a tomar todas as precauções de segurança que lhe couberem, devendo devolvê-los às FORNECEDORAS quando do término do presente contrato, no mesmo estado em que os recebeu, ressalvado o desgaste de uso natural.\n\n"
	+
	"4.2 A MANUTENÇÃO dos equipamentos ora emprestados, será efetuada exclusivamente pelas FORNECEDORAS ou por empresa por ela credenciada, devidamente identificada, correndo por sua conta todas as respectivas despesas, ressalvado o disposto na Cláusula 4.3.\n\n" 
	+
	"4.3. As FORNECEDORAS ficam autorizadas a cobrar do CONDOMÍNIO as despesas referentes a problemas ou defeitos apresentados nos bens emprestados em decorrência de mau uso, de operação ou manutenção dos referidos equipamentos por pessoas não autorizadas. Ademais, a manutenção dos equipamentos especiais, rede de gás primária, instalações internas comuns (zelador, salão de festas, etc.) e prumadas são de responsabilidade do CONDOMÍNIO, bem como as instalações internas de unidades condominiais são de responsabilidade do CONDOMÍNIO ou dos CONDÔMINOS, deste modo, as respectivas despesas correrão exclusivamente por sua conta.\n\n" 
	+
	"4.4. Observada qualquer irregularidade que impossibilite ou prejudique o fornecimento ou medição de GLP ou que afete de qualquer maneira a segurança, como vazamento na rede de gás, defeito nos equipamentos, entre outros, o CONDOMÍNIO e os condôminos ou moradores se obrigam a imediatamente paralisar o seu funcionamento e solicitar os serviços de assistência técnica das FORNECEDORAS.\n\n"
	+
	"5. De acordo com o §1º, do artigo 6º da Portaria ANP nº 47/99 a responsabilidade das FORNECEDORAS pelas instalações vai até o primeiro regulador de pressão existente na linha de abastecimento que operar enquanto as instalações estiverem sendo abastecidas por ela.\n\n"
	+
	"6. Fica facultado às FORNECEDORAS substituir, a seu critério, os equipamentos ora emprestados por outros de mesmo gênero, de capacidade superior ou inferior, adequada ao nível de consumo do CONDOMÍNIO, sempre que julgar necessário, sem que isto descaracterize o comodato ora firmado.\n\n"
	+
	"7. Obriga-se o CONDOMÍNIO a não permitir a manipulação ou a operação dos equipamentos objeto deste contrato por pessoas não autorizadas ou credenciadas pelas FORNECEDORAS, respondendo o CONDOMÍNIO por todo e qualquer prejuízo causado aos equipamentos, às FORNECEDORAS ou a terceiros em razão do descumprimento desta cláusula, restando autorizada as FORNECEDORAS a vistoriar os equipamentos emprestados sempre que julgar conveniente.\n\n"
	+
	"III – Da Rescisão\n\n"
	+
	"8. As FORNECEDORAS poderão considerar rescindido de pleno direito o presente contrato, independentemente de prévia notificação, interpelação ou aviso, caso ocorra alguma das seguintes hipóteses:\n\n"
	+
	"a)aquisição, pelo CONDOMÍNIO, de GLP fornecido por empresa diversa das FORNECEDORAS;\n"
	+"b)superveniência de interdição ou fechamento administrativo; pedido ou declaração de falência, recuperação judicial ou insolvência do CONDOMÍNIO;\n"
	+"c)manutenção ou operação dos equipamentos por pessoas não autorizadas ou credenciadas pelas FORNECEDORAS;\n" 
	+"d)remoção dos bens emprestados para outro local sem a autorização expressa das FORNECEDORAS;\n"
	+"e)substituição do GLP por qualquer outra fonte de energia, inclusive gás natural;\n"
	+"f)inadimplência de 20% (vinte por cento) das unidades autônomas ou caso o montante de contas em atraso, independentemente do número de unidades com pendência, atinja 30% (trinta por cento) do consumo médio mensal de gás global do condomínio;\n"
	+"g)descumprimento pelo CONDOMÍNIO, pelos CONDÔMINOS ou MORADORES de qualquer obrigação por ela assumida no presente instrumento.\n\n"
	+
	"8.1 Caso o CONDOMÍNIO, os CONDÔMINOS OU MORADORES rescindam ou deem causa à rescisão deste contrato antes do prazo, especialmente em virtude do estabelecido na cláusula 8, responderá o CONDOMÍNIO por multa rescisória calculada da seguinte forma: valor dos equipamentos emprestados constante Anexo I reduzido proporcionalmente ao tempo de cumprimento do contrato, adicionado de um terço do consumo previsto no Anexo I restante para o término, de acordo com o preço do quilo de GLP vigente no dia da rescisão. Tal multa será paga em uma só parcela, vencível no 5º (quinto) dia posterior a rescisão.\n\n"
	+
	"8.2. Nas hipóteses previstas na Cláusula 8.1, as FORNECEDORAS poderão exigir do CONDOMÍNIO a compra dos equipamentos especiais e o pagamento dos investimentos especiais previstos no Anexo I do presente contrato pelo valor constante no referido documento, atualizado pelo IGPM/FGV, ou outro índice que venha a substituí-lo.\n\n"
	+
	"9. O CONDOMÍNIO poderá dar por rescindido o presente contrato na hipótese das FORNECEDORAS não cumprirem com as obrigações aqui assumidas ou na superveniência de interdição ou fechamento administrativo, pedido ou declaração de falência, recuperação judicial ou insolvência das FORNECEDORAS. Rescindido o contrato com base nesta cláusula, responderão as FORNECEDORAS por uma multa, em favor do CONDOMÍNIO, equivalente a dez por cento do valor dos bens emprestados constante do Anexo I atualizados pelo IGPM/FGV, ou outro índice que venha a substituí-lo.\n\n" 
	+
	"10. Fica expressamente ressalvado à parte inocente o direito de pleitear perdas e danos, caso as multas estipuladas neste contrato sejam insuficientes para a cobertura dos prejuízos sofridos, nos termos do parágrafo único, do art. 416, do novo Código Civil.\n\n"
	
	+
	"IV – Do faturamento individualizado por unidade consumidora\n\n"
	+
	"11. As FORNECEDORAS efetuarão, mensalmente, a leitura dos medidores individuais instalados no condomínio, cobrando o gás consumido diretamente do condômino ou morador de cada uma das unidades autônomas integrantes do condomínio. A CONSIGAZ fica desde já autorizada a efetuar, a seu critério, a leitura ou a cobrança cumulativa de contas em períodos bimestrais ou trimestrais, caso entenda haver vantagem operacional com a adoção desta sistemática.\n\n"
	+
	"11.1 O FORNECIMENTO do gás só será iniciado mediante o cadastramento dos CONDÔMINOS OU MORADORES  junto às FORNECEDORAS, sendo que na ausência deste procedimento, ficam desde já autorizadas as FORNECEDORAS a suspenderem o fornecimento para as unidades autônomas que não possuírem o devido cadastro.\n\n"
	+
	"12. O consumo em quilogramas será obtido pela multiplicação do volume consumido, indicado pelo medidor em metros cúbicos (m³) pelo fator de conversão a ser adotado, de acordo com a pressão de entrada no medidor, características do GLP fornecido e temperatura.\n\n"
	+
	"13. Na hipótese de fornecimento de GLP para áreas comuns do condomínio, tais como zeladoria, salão de festas, etc.,  este consumo será cobrado do CONDOMÍNIO, sem prejuízo da cobrança individualizada do GLP destinado a cada unidade consumidora.\n\n"
	+
	"14. CASO SEJA IMPEDIDA A LEITURA DOS MEDIDORES, A COBRANÇA SERÁ FEITA COM BASE NO CONSUMO DA ÚLTIMA CONTA CONHECIDA DE CADA UNIDADE, ACRESCIDO DE 20% (VINTE POR CENTO), OU, NÃO HAVENDO CONSUMO CONHECIDO, SERÁ COBRADO DE CADA UNIDADE O EQUIVALENTE A 35KG (TRINTA E CINCO QUILOS) DE GÁS, SEM PREJUÍZO DA COBRANÇA DA DIFERENÇA, CASO SEJA CONSTATADO CONSUMO SUPERIOR AO COBRADO.\n\n"
	+
	"15. O CONDOMÍNIO e o MORADOR da unidade autônoma se obrigam, solidariamente, a comunicar imediatamente às FORNECEDORAS, por escrito e sob protocolo ou outro comprovante de inequívoca ciência pelas FORNECEDORAS, toda e qualquer alteração na titularidade ou ocupação de cada unidade autônoma, ficando o CONDOMÍNIO solidariamente responsável pelo pagamento das contas das unidades cuja mudança não for comunicada, ficando sujeito à suspensão do fornecimento até que a situação seja regularizada, ocasião em que poderá ser cobrada a taxa de religue do gás de acordo com o valor aplicável à época.\n\n"
	+
	"16. O CONDOMÍNIO e os moradores das UNIDADES AUTÔNOMAS, conforme o caso, pagarão o gás que lhe foi fornecido no prazo previsto no preâmbulo deste instrumento, mediante boletos de cobrança bancária enviados pelas FORNECEDORAS, de modo que cada consumidor receba sua conta com pelo menos 5 (cinco) dias de antecedência ao vencimento, FICANDO DESDE JÁ ESTIPULADO QUE O PAGAMENTO APÓS O VENCIMENTO IMPLICARÁ NA INCIDÊNCIA, SOBRE OS VALORES EM ATRASO, DE MULTA DE 8% (OITO POR CENTO) E JUROS CALCULADOS POR DIA DE ATRASO.\n\n"
	+
	"17. Faculta-se às FORNECEDORAS disponibilizar aos consumidores modalidade de débito automático das contas de gás em conta corrente, mediante adesão individual dos interessados.\n\n"
	+
	"18. Faculta-se também às FORNECEDORAS disponibilizar aos consumidores outras opções de data de vencimento da conta de gás.\n\n"
	+
	"19. FICA DESDE JÁ ESTIPULADO QUE, QUALQUER QUE TENHA SIDO O CONSUMO, SERÁ ADICIONADO EM CADA CONTA O VALOR DA TARIFA DE SERVIÇOS.\n\n"
	+
	"20. NA HIPÓTESE DE SER SOLICITADA SEGUNDA VIA DA NOTA FISCAL/FATURA OU BOLETO DE PAGAMENTO, SERÁ COBRADA DO CONSUMIDOR, TAMBÉM, A TARIFA DE EXPEDIENTE VIGENTE NO DIA DA SOLICITAÇÃO.\n\n"
	+
	"21. Os valores da tarifa de serviços e taxa de religue previstas no Anexo I deste contrato serão reajustados anualmente.\n\n" 
	+
	"V – Da inadimplência dos consumidores\n\n\n" 
	+
	"22. NÃO PAGAS AS CONTAS DE GÁS NOS SEUS VENCIMENTOS, FICA O CONSUMIDOR SUJEITO, ALÉM DOS ACRÉSCIMOS MORATÓRIOS PREVISTOS NA CLÁUSULA 16 DO PRESENTE CONTRATO, ÀS SEGUINTES PENALIDADES, QUE SERÃO APLICADAS INDIVIDUAL OU CONJUNTAMENTE:\n\n"
	
	+"A) PROTESTO DE DUPLICATA;\n"
	+"B) INSCRIÇÃO EM CADASTROS DE PROTEÇÃO AO CRÉDITO, TAIS COMO SERASA E SPC;\n"
	+"C) CORTE NO FORNECIMENTO, CASO PERDURE O ATRASO POR MAIS DE 15 (QUINZE) DIAS  E\n"
	+"D) COBRANÇA JUDICIAL DA DÍVIDA.\n\n"
	+
	"23. CORTADO O FORNECIMENTO, O GÁS SOMENTE SERÁ RELIGADO NO PRAZO DE 48H (QUARENTA E OITO HORAS) APÓS O PAGAMENTO DAS CONTAS ATRASADAS E SEUS ACRÉSCIMOS, BEM COMO DA TAXA DE RELIGAÇÃO VIGENTE NO DIA DA SOLICITAÇÃO.\n\n"
	
	+
	"24. As FORNECEDORAS poderão dar por rescindido o presente contrato caso haja a inadimplência de 20% (vinte por cento) das unidades autônomas ou caso o montante de contas em atraso, independentemente do número de unidades com pendência, atinja 30% (trinta por cento) do consumo médio mensal de gás global do condomínio, que será apurado mediante a média aritmética do consumo global de gás verificado nos últimos três meses, ou em período menor, caso o contrato não tenha ainda atingido esta vigência.\n\n"
	+
	"25. Verificada a inadimplência prevista na cláusula 24, as FORNECEDORAS notificarão o ocorrido ao CONDOMÍNIO, por carta simples, com 30 (trinta) dias de antecedência da retirada dos tanques, prazo em que os consumidores com pendência ou o CONDOMÍNIO poderão evitar a rescisão, purgando na totalidade a mora dos inadimplentes, entretanto, ficará assegurado, durante este período, o fornecimento de gás a todas as unidades, exceto àquelas com corte de fornecimento.\n\n"
	+
	"VI – Das disposições gerais\n\n"
	+
	"26. A manutenção da rede interna de distribuição de gás e demais instalações nas áreas comuns incumbe com exclusividade ao CONDOMÍNIO. Os reguladores de pressão de segundo estágio, se individualizados, e os medidores serão de responsabilidade dos CONDÔMINOS ou MORADORES, correndo às suas expensas as despesas com sua manutenção ou troca, que deverá ocorrer em até 48h (quarenta e oito horas) após a identificação do problema.\n\n"
	+
	"26.1 Caso o prazo estipulado na cláusula 26 não seja atendido, as FORNECEDORAS ficam desde já autorizadas a realizar a troca dos reguladores de pressão de segundo estágio, se individualizados, e dos medidores com defeitos/problemas, sendo cobrado do respectivo CONDÔMINO ou MORADOR o valor do equipamento de acordo com a sua tabela vigente.\n\n" 
	+
	"27. Na hipótese do CONDÔMINO ou MORADOR identificar qualquer situação que possa acarretar qualquer tipo de risco à sua segurança e dos outros condôminos ou moradores, como vazamento de gás, entre outros, este deverá desligar imediatamente todos os equipamentos que utilizam gás, bem como acionar, imediatamente, a assistência técnica das FORNECEDORAS.\n\n"
	+
	"27.1 Caso o CONDÔMINO ou MORADOR precise efetuar algum serviço ou alteração nas instalações de gás de sua unidade, ou mesmo desconectar seu fogão ou aquecedor, DEVE SOLICITAR COM ANTECEDÊNCIA À ADMINISTRAÇÃO DO EDIFÍCIO O FECHAMENTO PRÉVIO DO REGISTRO RELATIVO À SUA UNIDADE. JAMAIS EFETUE SERVIÇOS OU MODIFICAÇÕES COM O GÁS ABERTO, O QUE PODERÁ CAUSAR EXPLOSÃO E INCÊNDIO.\n\n"
	+
	"28. A MANUTENÇÃO DAS INSTALAÇÕES DE GÁS INTERNAS DE CADA UNIDADE AUTÔNOMA INCUMBIRÁ, COM EXCLUSIVIDADE, A CADA CONDÔMINO OU MORADOR.\n\n"
	+
	"29. Findo o presente contrato, qualquer que seja o motivo, obriga-se o CONDOMÍNIO a permitir às FORNECEDORAS, de imediato, a retirada dos equipamentos emprestados. A inobservância desta disposição acarretará a incidência imediata de multa diária por retenção no valor de 2% (dois por cento) do valor dos equipamentos emprestados, limitado ao valor total dos bens emprestados constante do Anexo I, até a data da efetiva devolução ou retirada dos bens, sem prejuízo da tomada das medidas judiciais cabíveis em face do esbulho possessório que ficará de pleno direito, caracterizado.\n\n"
	+
	"30. No ato da retirada dos tanques as FORNECEDORAS efetuarão a leitura final dos medidores, para fins de cobrança do gás consumido até o último dia de vigência do presente contrato.\n\n"
	+
	"30.1 CASO SEJA IMPEDIDA A LEITURA DOS MEDIDORES, A COBRANÇA SERÁ FEITA COM BASE NO CONSUMO DA ÚLTIMA CONTA CONHECIDA DE CADA UNIDADE, ACRESCIDO DE 20% (VINTE POR CENTO), OU, NÃO HAVENDO CONSUMO CONHECIDO, SERÁ COBRADO DE CADA UNIDADE O EQUIVALENTE A 35KG (TRINTA E CINCO QUILOS) DE GÁS, SEM PREJUÍZO DA COBRANÇA DA DIFERENÇA, CASO SEJA CONSTATADO CONSUMO SUPERIOR AO COBRADO.\n\n"
	+
	"31. O presente contrato terá a duração especificada no Anexo I deste instrumento, renovando-se por iguais períodos sucessivos a menos que haja oposição de qualquer das partes, manifestada por escrito e com antecedência mínima de 90 (noventa) dias da data de seu vencimento, sendo que na sua ausência renovam-se todas as suas cláusulas.\n\n"
	+
	"31.1. Caso haja a denúncia do contrato na forma especificada na cláusula anterior, e ao final do término do contrato o CONDOMÍNIO ainda não tiver consumido a quantidade prevista no Anexo I, a diferença será cobrada do CONDOMÍNIO pelo preço do quilo do GLP vigente na data de encerramento do negócio jurídico.\n\n" 
	+
	"32. Caso o CONDOMÍNIO se oponha à renovação do contrato em razão de melhor oferta de terceiro, fica desde já concedido às FORNECEDORAS o direito de preferência.\n\n"
	+
	"33. Eventual tolerância de uma parte a infrações ou descumprimento das disposições do presente contrato cometidas pela outra parte, será considerada como ato de mera liberalidade não se constituindo em perdão, precedente, novação ou renúncia a direitos que a legislação ou o contrato assegurem às partes.\n\n"
	+
	"34. Este contrato continuará em vigor, ainda que qualquer das partes seja objeto de incorporação, fusão, cisão, cessão ou qualquer alteração contratual ou societária, obrigando-se a parte a comunicar o fato, imediatamente a outra, bem como dar ciência aos sucessores da existência do presente instrumento, sob pena de responsabilizar-se solidariamente pelos prejuízos causados por rescisão imotivada do sucessor. A referida comunicação será anexada ao presente contrato fazendo parte integrante deste.\n\n"
	+
	"35. Para os investimentos e equipamentos especiais poderá ser emitida nota fiscal com a natureza de bonificação, o que não prejudicará o disposto na Cláusula 8.2, ou seja, referida natureza terá valor apenas para questões fiscais."	
	+
	"36. O CONDOMÍNIO se obriga a disponibilizar uma cópia deste instrumento para cada CONDÔMINO ou MORADOR logo após a sua assinatura.\n\n "	
	+
	"37. O CONDOMÍNIO declara que recebeu o “Manual do Cliente” elaborado pela FORNECEDORA, o qual também encontra-se disponível para “download” no site www.consigaz.com.br. Ainda, a CONDOMÍNIO se compromete a observar os procedimentos descritos no referido manual, com destaque aos relativos à segurança.\n\n "
	+
	"38. O abastecimento do GLP será realizado por qualquer uma das três fornecedoras especificadas no preâmbulo deste instrumento, ao exclusivo critério das FORNECEDORAS, e, ainda, por outra empresa que venha a integrar este grupo econômico. \n\n "
	+
	"39. Fica eleito o Foro de São Paulo-SP para dirimir quaisquer dúvidas ou litígios oriundos do presente contrato, com renúncia expressa de qualquer outro, por mais privilegiado que seja.\n\n "
	+
	"E por estarem assim justas e contratadas, assinam o presente instrumento nas suas duas vias de igual teor, na presença de duas testemunhas.\n\n";
				
	return textoContrato;
	}

}
