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
	
		return "Este anexo � parte integrante do contrato, obrigando, deste modo, �s partes quanto ao seu teor, o qual pode ser alterado sem a necessidade de modifica��o do instrumento principal.";
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
			 
			 CPFouCNPJ = "CNPJ  sob o n� " + mov_informacoesDoCliente.getInformacao_4();
		 }else{
			 CPFouCNPJ = "CPF  sob o n� " + mov_informacoesDoCliente.getInformacao_4();
		 }
		 
		 
		 if(mov_informacoesDoCliente.getInformacao_5().length() >= 12){
			 
			 inscricaoEstadualOuRG = "Inscri��o Estadual  sob o n� " + mov_informacoesDoCliente.getInformacao_5();
		 }else{
			 inscricaoEstadualOuRG = "RG sob o n� " + mov_informacoesDoCliente.getInformacao_5();
		 }
		 
		 
		 Movimento mov_representacao = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_representacao.getPosicao());
			
		 if(mov_representacao == null) {
			 
			 representadoPor = mov_informacoesDoCliente.getInformacao_1(); 			 				
		 }else {
			 representadoPor = mov_representacao.getInformacao_1()+", representado por "+ mov_informacoesDoCliente.getInformacao_1(); 			 
		 }
		

	String textoContrato = "Contrato de Fornecimento de GLP e Comodato de Instala��es N� "+geraNumeroContrato()+"\n"
	//String textoContrato = "Contrato de Fornecimento de GLP e \nComodato de Instala��es N� 0000/2017\n"
	+
	"PRIMEIRA FORNECEDORA E COMODANTE: CONSIGAZ DISTRIBUIDORA DE G�S LTDA, com sede na Rua Jos� Pereira Sobrinho, n� 485 - S�tio Mutinga � Barueri/SP, CNPJ n� 01.597.589/0002-09 e Inscri��o Estadual n� 206.190.522.112, neste ato representada por seu representante abaixo assinado e identificado, a seguir designada FORNECEDORA, e.\n\n"
	+
	"SEGUNDA FORNECEDORA: GASBALL ARMAZENADORA E DISTRIBUIDORA LTDA, com sede na Rua Eduardo Elias Zahran, n�. 127 � Fazenda Bonfim � Paul�nia/SP, CNPJ n�. 02.430.968/0001-83 e Inscri��o Estadual n�. 513.060.943.110, neste ato representada por seu representante abaixo assinado e identificado, a seguir designada simplesmente FORNECEDORA.\n\n"
	+ 
	"CLIENTE, doravante simplesmente designado �CLIENTE�: "+representadoPor+", estabelecimento com sede em "+mov_enderecoPadrao.getInformacao_1() +" "+ mov_enderecoPadrao.getInformacao_2() +", "+ mov_enderecoPadrao.getInformacao_3() +", "+ mov_enderecoPadrao.getInformacao_4() +"/"+ mov_enderecoPadrao.getInformacao_5() +" "+ mov_enderecoPadrao.getInformacao_6()+ ", "+ CPFouCNPJ +" e "+ inscricaoEstadualOuRG +", neste ato por seu representante legal abaixo assinado e identificado.\n\n"
		+
	"CL�USULAS CONTRATUAIS\n"
	+
	"1. A FORNECEDORA se obriga, neste ato, a fornecer � CLIENTE g�s liquefeito de petr�leo, em quantidade necess�ria e suficiente ao desenvolvimento das atividades da CLIENTE, que dever� adquirir o GLP exclusivamente da FORNECEDORA. O g�s ser� fornecido por meio de entregas autom�ticas programadas pela FORNECEDORA ou, se necess�rio, mediante pedidos efetuados pela CLIENTE com anteced�ncia m�nima de 48 (quarenta e oito horas).\n\n"
	+ 
	"1.1 Na hip�tese da CLIENTE recusar alguma entrega programada de GLP, solicitando, por�m, posteriormente, abastecimento emergencial, antes das 48 horas previstas na cl�usula 1, fica a FORNECEDORA desde j� autorizada a cobrar da CLIENTE uma sobretaxa de 30% (trinta por cento) sobre o valor do g�s praticado no mencionado fornecimento emergencial.\n\n"
	+
	"1.2 Em caso de desabastecimento de GLP no mercado, a FORNECEDORA se reserva o direito de restringir as quantidades a fornecer � CLIENTE, ou mesmo de recusar o fornecimento, at� que o abastecimento seja normalizado, n�o cabendo � FORNECEDORA, nesta hip�tese, qualquer responsabilidade pelos preju�zos eventualmente advindos � CLIENTE pela falta de g�s, sendo que a FORNECEDORA autoriza a CLIENTE a adquirir o GLP de outro fornecedor, �nica e exclusivamente, durante o per�odo em que, comprovadamente, persistir tal situa��o.\n\n"
	+
	"2. O pre�o de venda do g�s ser� reajustado sempre que houver altera��o de qualquer um dos fatores que o comp�e, como custos de mat�ria prima, tributos, componentes do pre�o do GLP ou de qualquer dos custos operacionais de distribui��o e servi�os.\n\n"
	+
	"3. A CLIENTE pagar� o g�s que lhe foi fornecido no prazo previsto no Anexo I deste instrumento, mediante cobran�a banc�ria a cargo da FORNECEDORA ficando desde j� estipulado que o pagamento ap�s o vencimento implicar� na incid�ncia, sobre os valores em atraso, de multa de 2% (dois por cento) e juros por dia de atraso.\n\n"
	+
	"3.1 A FORNECEDORA se reserva o direito de modificar as condi��es de pagamento previstas no Anexo I do presente instrumento, inclusive exigindo pagamento � vista dos abastecimentos futuros, sempre que houver piora na qualidade do cr�dito da CLIENTE, indicada pela falta de pagamento de qualquer abastecimento, pela exist�ncia de apontamentos negativos junto a �rg�os de prote��o ao cr�dito, protesto de t�tulos, distribui��o de a��es que indiquem inadimplemento de obriga��es, etc.\n\n"
	+
	"4. A FORNECEDORA empresta neste ato � CLIENTE os equipamentos especificados no Anexo I, sendo que a CLIENTE se obriga a zelar e a conservar os equipamentos como se fossem seus, bem como a tomar todas as precau��es de seguran�a que lhe couberem, devendo devolv�-los � FORNECEDORA quando do t�rmino do presente contrato, no mesmo estado em que os recebeu, ressalvado o desgaste de uso natural.\n\n"
	+
	"4.1 A MANUTEN��O dos equipamentos ora emprestados, ser� efetuada exclusivamente pela FORNECEDORA ou por empresa por ela credenciada, devidamente identificada, correndo por sua conta todas as respectivas despesas, ressalvadas as despesas referentes a problemas ou defeitos apresentados nos bens emprestados em decorr�ncia de mau uso e de opera��o ou manuten��o dos referidos equipamentos por pessoas n�o autorizadas, as quais ser�o cobradas pela FORNECEDORA. Ademais, a CLIENTE se obriga a realizar a manuten��o preventiva e corretiva dos equipamentos especiais, prumadas, instala��es internas de unidades condominiais e dos equipamentos de sua propriedade ou n�o que utilizam o GLP, nestes casos as despesas correr�o exclusivamente por sua conta.\n\n"
	+
	"4.2 Obriga-se a CLIENTE a n�o permitir a manipula��o ou a opera��o dos equipamentos objeto deste contrato por pessoas n�o autorizadas ou credenciadas pela CONSIGAZ, respondendo a CLIENTE por todo e qualquer preju�zo causado aos equipamentos, � FORNECEDORA ou a terceiros em raz�o do descumprimento desta cl�usula, restando autorizada a FORNECEDORA a vistoriar os equipamentos emprestados sempre que julgar conveniente.\n\n"
	+
	"4.3 Observada qualquer irregularidade que impossibilite ou prejudique o fornecimento ou medi��o de GLP ou que afete de qualquer maneira a seguran�a, como vazamento na rede de g�s, defeito nos equipamentos, entre outros, a CLIENTE se obriga a imediatamente paralisar o seu funcionamento e solicitar os servi�os de assist�ncia t�cnica da FORNECEDORA.\n\n"
	+
	"5. De acordo com o �1�, do artigo 6� da Portaria ANP n� 47/99 a FORNECEDORA � respons�vel pelas instala��es de GLP at� o primeiro regulador de press�o existente na linha de abastecimento que operar enquanto as instala��es estiverem sendo abastecidas por ela.\n\n"
	+
	"6. Fica facultado � FORNECEDORA substituir, a seu crit�rio, os equipamentos ora emprestados por outros de mesmo g�nero, de capacidade superior ou inferior, adequada ao n�vel de consumo da CLIENTE, sempre que julgar necess�rio, sem que isto descaracterize o comodato ora firmado.\n\n"
	+
	"7. A FORNECEDORA poder� considerar rescindido de pleno direito o presente contrato, independentemente de pr�via notifica��o, interpela��o ou aviso, caso ocorra alguma das seguintes hip�teses: aquisi��o, pela CLIENTE, de GLP fornecido por empresa diversa da FORNECEDORA; superveni�ncia de interdi��o ou fechamento administrativo; pedido ou declara��o de fal�ncia, recupera��o judicial ou insolv�ncia da CLIENTE; manuten��o ou opera��o dos equipamentos por pessoas n�o autorizadas ou credenciadas pela FORNECEDORA; remo��o dos bens emprestados para outro local sem a autoriza��o expressa da FORNECEDORA; substitui��o do GLP por qualquer outra fonte de energia, inclusive g�s natural; aus�ncia de consumo por mais de 60 dias, falta e/ou insufici�ncia do consumo previsto no Anexo I; descumprimento pela CLIENTE de qualquer obriga��o por ela assumida no presente instrumento.\n\n"
	+
	"7.1 Caso a CLIENTE rescinda ou d� causa � rescis�o deste contrato durante sua vig�ncia responder� por multa rescis�ria calculada da seguinte forma: valor dos equipamentos emprestados constante no Anexo I, reduzido proporcionalmente ao tempo de cumprimento do contrato, adicionado de um ter�o do consumo previsto no Anexo I restante para o t�rmino do contrato, de acordo com o pre�o do quilo de GLP vigente no dia da rescis�o. Tal multa ser� paga em uma s� parcela, venc�vel no 5� (quinto) dia posterior a rescis�o.\n\n"
	+
	"7.2. Nas hip�teses previstas na Cl�usula 7.1, a FORNECEDORA poder� exigir da CLIENTE a compra dos equipamentos especiais e o pagamento dos investimentos especiais previstos no Anexo I do presente contrato, no estado em que se encontrem, pelo valor constante do Anexo I atualizado pelo IGPM.\n\n"
	+
	"8. A CLIENTE poder� dar por rescindido o presente contrato na hip�tese da FORNECEDORA n�o cumprir com as obriga��es aqui assumidas ou na superveni�ncia de interdi��o ou fechamento administrativo, pedido ou declara��o de fal�ncia, recupera��o judicial ou insolv�ncia da FORNECEDORA. Rescindido o contrato com base nesta cl�usula, responder� a FORNECEDORA por uma multa, em favor da CLIENTE, equivalente a dez por cento do valor dos bens emprestados constante do Anexo I\n\n"
	+
	"9. Fica expressamente ressalvado � parte inocente o direito de pleitear perdas e danos, caso as multas estipuladas neste contrato sejam insuficientes para a cobertura dos preju�zos sofridos, nos termos do par�grafo �nico, do art. 416, do novo C�digo Civil.\n\n"
	+
	"10 A CLIENTE desde j� reconhece que os equipamentos emprestados s�o de propriedade da FORNECEDORA, desta forma, obriga-se a CLIENTE a permitir � FORNECEDORA, de imediato, a retirada dos equipamentos emprestados. A inobserv�ncia desta disposi��o, a partir do t�rmino do prazo estipulado em notifica��o, acarretar� na incid�ncia de multa di�ria por reten��o no valor de 2% do valor dos equipamentos emprestados, at� a data da efetiva devolu��o ou retirada dos bens, limitado ao valor total dos equipamentos emprestados previsto no Anexo I, ocasi�o em que a referida multa constitu�ra titulo executivo extrajudicial nos termos do artigo 585 do C�digo de Processo Civil.\n\n"
	+
	"10.1 A aplica��o da multa prevista na Cl�usula 10 n�o transfere a propriedade dos bens, raz�o pela qual n�o prejudica a ado��o das medidas judiciais cab�veis em face do esbulho possess�rio que ficar� de pleno direito, caracterizado.\n\n"
	+
	"11. Na hip�tese de comodato de cilindros transport�veis, caso n�o ocorra a sua devolu��o ou sejam devolvidos cilindros de marca diversa da CONSIGAZ ou GASBALL, os cilindros n�o devolvidos ser�o cobrados de acordo com o valor da tabela de pre�os aplic�vel pela FORNECEDORA.\n\n"
	+
	"12. O presente contrato ter� a dura��o especificada no Anexo I deste instrumento, renovando-se por iguais per�odos sucessivos a menos que haja oposi��o de qualquer das partes, manifestada por escrito e com anteced�ncia m�nima de 90 (noventa) dias da data de seu vencimento, sendo que na sua aus�ncia renovam-se todas as suas cl�usulas.\n\n"
	+
	"12.1. Caso haja a den�ncia do contrato na forma especificada na cl�usula anterior, e ao final do t�rmino do contrato a CLIENTE ainda n�o tiver consumido a quantidade prevista no Anexo I, a diferen�a ser� cobrada pelo pre�o do quilo do GLP vigente na data de encerramento do neg�cio jur�dico.\n\n"
	+
	"13. Caso a CLIENTE se oponha � renova��o do contrato em raz�o de melhor oferta de terceiro, fica desde j� concedido � FORNECEDORA o direito de prefer�ncia.\n\n"
	+
	"14. Eventual toler�ncia de uma parte a infra��es ou descumprimento das disposi��es do presente contrato cometidas pela outra parte, ser� considerada como ato de mera liberalidade n�o se constituindo em perd�o, precedente, nova��o ou ren�ncia a direitos que a legisla��o ou o contrato assegurem �s partes.\n\n"
	+
	"15. Este contrato continuar� em vigor, ainda que qualquer das partes seja objeto de incorpora��o, fus�o, cis�o, cess�o ou qualquer altera��o contratual ou societ�ria, obrigando-se a parte a comunicar o fato, imediatamente a outra, bem como dar ci�ncia aos sucessores da exist�ncia do presente instrumento, sob pena de responsabilizar-se solidariamente pelos preju�zos causados por rescis�o imotivada do sucessor. A referida comunica��o ser� anexada ao presente contrato fazendo parte integrante deste.\n\n"
	+
	"16. Para os investimentos e equipamentos especiais poder� ser emitida nota fiscal com a natureza de bonifica��o, o que n�o prejudicar� o disposto na Cl�usula 7.2, ou seja, referida natureza ter� valor apenas para quest�es fiscais.\n \n"
	+
	"17. A CLIENTE declara que recebeu o �Manual do Cliente� elaborado pela FORNECEDORA, o qual tamb�m encontra-se dispon�vel para �download� no site www.consigaz.com.br. Ainda, a  CLIENTE se compromete a observar os procedimentos descritos no referido manual, com destaque aos relativos � seguran�a.\n\n "
	+
	"18. Fica eleito o Foro de Barueri-SP para dirimir quaisquer d�vidas ou lit�gios oriundos do presente contrato, com ren�ncia expressa de qualquer outro, por mais privilegiado que seja. \n \n  E por estarem assim justas e contratadas, assinam o presente instrumento nas suas duas vias de igual teor, na presen�a de duas testemunhas.\n\n";
	
	return textoContrato;
	}
	
	
	public String devolveContratoContaSIM(ArrayList<Movimento> listaComMovimentos){
		
		Movimento mov_informacoesDoCliente = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_informacoesCliente.getPosicao());
		Movimento mov_enderecoPadrao = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_enderecoPadrao.getPosicao());
		
		String representadoPor = "";
		String CPFouCNPJ = "";
		String inscricaoEstadualOuRG = "";
		 
		 if(mov_informacoesDoCliente.getInformacao_4().length() > 14){
			 
			 CPFouCNPJ = "CNPJ  sob o n� " + mov_informacoesDoCliente.getInformacao_4();
		 }else{
			 CPFouCNPJ = "CPF  sob o n� " + mov_informacoesDoCliente.getInformacao_4();
		 }
		 
		 
		 if(mov_informacoesDoCliente.getInformacao_5().length() >= 12){
			 
			 inscricaoEstadualOuRG = "Inscri��o Estadual  sob o n� " + mov_informacoesDoCliente.getInformacao_5();
		 }else{
			 inscricaoEstadualOuRG = "RG sob o n� " + mov_informacoesDoCliente.getInformacao_5();
		 }
	
		 
		 Movimento mov_representacao = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_representacao.getPosicao());
			
		 if(mov_representacao == null) {
			 
			 representadoPor = mov_informacoesDoCliente.getInformacao_1(); 			 				
		 }else {
			 representadoPor = mov_representacao.getInformacao_1()+", representado por "+ mov_informacoesDoCliente.getInformacao_1(); 			 
		 }

	
	String textoContrato = "Contrato de Fornecimento de GLP e Comodato de Instala��es N� "+geraNumeroContrato()+"\n" 
	//String textoContrato = "Contrato de Fornecimento de GLP e Comodato de Instala��es N� XXXX/2017\n\n" 
	+
	"PRIMEIRA FORNECEDORA: CONSIGAZ DISTRIBUIDORA DE G�S LTDA., com sede na Rua Jos� Pereira Sobrinho, n� 485 - S�tio Mutinga � Barueri/SP, CNPJ n� 01.597.589/0002-09 e Inscri��o Estadual n� 206.190.522.112, neste ato representada por seu representante abaixo assinado e identificado, a seguir designada simplesmente CONSIGAZ;\n\n"
	+
	"SEGUNDA FORNECEDORA: GASBALL ARMAZENADORA E DISTRIBUIDORA LTDA, com sede na Rua Eduardo Elias Zahran, n�. 127 � Fazenda Bonfim � Paul�nia/SP, CNPJ n�. 02.430.968/0001-83 e Inscri��o Estadual n�. 513.060.943.110, neste ato representada por seu representante abaixo assinado e identificado, a seguir designada simplesmente GASBALL;\n\n"
	+
	"TERCEIRA FORNECEDORA: PROPANG�S LTDA, com sede na Rodovia SP 332, km 146,6, S/N � Caixa Postal 95, CEP 13150-000, Cosm�polis/SP, CNPJ n� 03.013.594/0001-63 e Inscri��o Estadual n� 276.036.008.117, neste ato representada por seu representante abaixo assinado e identificado, a seguir designada simplesmente PROPANGAS.\n\n"
	+
	"CLIENTE, doravante simplesmente designado �CONDOM�NIO�: "+representadoPor+", estabelecimento com sede em "+mov_enderecoPadrao.getInformacao_1() +" "+ mov_enderecoPadrao.getInformacao_2() +", "+ mov_enderecoPadrao.getInformacao_3() +", "+ mov_enderecoPadrao.getInformacao_4() +"/"+ mov_enderecoPadrao.getInformacao_5() +" "+ mov_enderecoPadrao.getInformacao_6()+ ", "+CPFouCNPJ+" e "+inscricaoEstadualOuRG+", neste ato por seu representante legal abaixo assinado e identificado.\n\n"
	+
	"As tr�s fornecedoras, a seguir denominadas simplesmente FORNECEDORAS OU COMODANTES, e o CONDOM�NIO tem entre si justo e acordado o seguinte:\n" 
	+
	"I � Do Fornecimento do GLP\n\n"
	+
	"1. As FORNECEDORAS se obrigam, neste ato, a fornecer ao CONDOM�NIO g�s liquefeito de petr�leo. O g�s ser� fornecido por meio de entregas autom�ticas programadas pelas FORNECEDORAS ou, se necess�rio, mediante pedidos efetuados pelo CONDOM�NIO com anteced�ncia m�nima de 48 (quarenta e oito horas).\n\n"
	+
	"1.1. Na hip�tese do CONDOM�NIO recusar alguma entrega programada de GLP, solicitando, por�m, posteriormente, abastecimento emergencial, antes das 48 horas previstas na cl�usula 1, ficam as FORNECEDORAS desde j� autorizadas a cobrar do CONDOM�NIO uma sobretaxa de 30% (trinta por cento) sobre o valor do abastecimento emergencial.\n\n"
	+
	"1.2 Em caso de desabastecimento de GLP no mercado, as FORNECEDORAS se reservam o direito de restringir as quantidades a fornecer ao CONDOM�NIO, ou mesmo de recusar o fornecimento, at� que o abastecimento seja normalizado, n�o cabendo �s FORNECEDORAS, nesta hip�tese, qualquer responsabilidade pelos preju�zos eventualmente advindos ao CONDOM�NIO pela falta de g�s, sendo que as FORNECEDORAS autorizam o CONDOM�NIO a adquirir o GLP de outro fornecedor, �nica e exclusivamente, durante o per�odo em que, comprovadamente, persistir tal situa��o.\n\n"
	+
	"2. O pre�o de venda do g�s ser� reajustado sempre que houver altera��o de qualquer um dos fatores que o comp�e, como custos de mat�ria prima, tributos, componentes do pre�o do GLP ou de qualquer dos custos operacionais de distribui��o e servi�os.\n\n"
	+
	"3. Para fins deste contrato e para todos os efeitos legais, considera-se vendido e consumido o g�s pelo cond�mino ou morador t�o logo passe ele pelo medidor individual de cada unidade consumidora.\n"
	+

	"II � Do Comodato\n\n"
	+
	"4. As FORNECEDORAS emprestam neste ato ao CONDOM�NIO os equipamentos especificados no Anexo I. Em contrapartida ao comodato, o CONDOM�NIO concede �s FORNECEDORAS, pelo prazo deste contrato e de suas renova��es, EXCLUSIVIDADE no fornecimento de GLP (G�s Liquefeito de Petr�leo), desta forma, fica o CONDOM�NIO proibido de adquirir GLP, em qualquer quantidade, de outro fornecedor.\n\n"
	+
	"4.1. O CONDOM�NIO se obriga a zelar e a conservar os equipamentos como se fossem seus, bem como a tomar todas as precau��es de seguran�a que lhe couberem, devendo devolv�-los �s FORNECEDORAS quando do t�rmino do presente contrato, no mesmo estado em que os recebeu, ressalvado o desgaste de uso natural.\n\n"
	+
	"4.2 A MANUTEN��O dos equipamentos ora emprestados, ser� efetuada exclusivamente pelas FORNECEDORAS ou por empresa por ela credenciada, devidamente identificada, correndo por sua conta todas as respectivas despesas, ressalvado o disposto na Cl�usula 4.3.\n\n" 
	+
	"4.3. As FORNECEDORAS ficam autorizadas a cobrar do CONDOM�NIO as despesas referentes a problemas ou defeitos apresentados nos bens emprestados em decorr�ncia de mau uso, de opera��o ou manuten��o dos referidos equipamentos por pessoas n�o autorizadas. Ademais, a manuten��o dos equipamentos especiais, rede de g�s prim�ria, instala��es internas comuns (zelador, sal�o de festas, etc.) e prumadas s�o de responsabilidade do CONDOM�NIO, bem como as instala��es internas de unidades condominiais s�o de responsabilidade do CONDOM�NIO ou dos COND�MINOS, deste modo, as respectivas despesas correr�o exclusivamente por sua conta.\n\n" 
	+
	"4.4. Observada qualquer irregularidade que impossibilite ou prejudique o fornecimento ou medi��o de GLP ou que afete de qualquer maneira a seguran�a, como vazamento na rede de g�s, defeito nos equipamentos, entre outros, o CONDOM�NIO e os cond�minos ou moradores se obrigam a imediatamente paralisar o seu funcionamento e solicitar os servi�os de assist�ncia t�cnica das FORNECEDORAS.\n\n"
	+
	"5. De acordo com o �1�, do artigo 6� da Portaria ANP n� 47/99 a responsabilidade das FORNECEDORAS pelas instala��es vai at� o primeiro regulador de press�o existente na linha de abastecimento que operar enquanto as instala��es estiverem sendo abastecidas por ela.\n\n"
	+
	"6. Fica facultado �s FORNECEDORAS substituir, a seu crit�rio, os equipamentos ora emprestados por outros de mesmo g�nero, de capacidade superior ou inferior, adequada ao n�vel de consumo do CONDOM�NIO, sempre que julgar necess�rio, sem que isto descaracterize o comodato ora firmado.\n\n"
	+
	"7. Obriga-se o CONDOM�NIO a n�o permitir a manipula��o ou a opera��o dos equipamentos objeto deste contrato por pessoas n�o autorizadas ou credenciadas pelas FORNECEDORAS, respondendo o CONDOM�NIO por todo e qualquer preju�zo causado aos equipamentos, �s FORNECEDORAS ou a terceiros em raz�o do descumprimento desta cl�usula, restando autorizada as FORNECEDORAS a vistoriar os equipamentos emprestados sempre que julgar conveniente.\n\n"
	+
	"III � Da Rescis�o\n\n"
	+
	"8. As FORNECEDORAS poder�o considerar rescindido de pleno direito o presente contrato, independentemente de pr�via notifica��o, interpela��o ou aviso, caso ocorra alguma das seguintes hip�teses:\n\n"
	+
	"a)aquisi��o, pelo CONDOM�NIO, de GLP fornecido por empresa diversa das FORNECEDORAS;\n"
	+"b)superveni�ncia de interdi��o ou fechamento administrativo; pedido ou declara��o de fal�ncia, recupera��o judicial ou insolv�ncia do CONDOM�NIO;\n"
	+"c)manuten��o ou opera��o dos equipamentos por pessoas n�o autorizadas ou credenciadas pelas FORNECEDORAS;\n" 
	+"d)remo��o dos bens emprestados para outro local sem a autoriza��o expressa das FORNECEDORAS;\n"
	+"e)substitui��o do GLP por qualquer outra fonte de energia, inclusive g�s natural;\n"
	+"f)inadimpl�ncia de 20% (vinte por cento) das unidades aut�nomas ou caso o montante de contas em atraso, independentemente do n�mero de unidades com pend�ncia, atinja 30% (trinta por cento) do consumo m�dio mensal de g�s global do condom�nio;\n"
	+"g)descumprimento pelo CONDOM�NIO, pelos COND�MINOS ou MORADORES de qualquer obriga��o por ela assumida no presente instrumento.\n\n"
	+
	"8.1 Caso o CONDOM�NIO, os COND�MINOS OU MORADORES rescindam ou deem causa � rescis�o deste contrato antes do prazo, especialmente em virtude do estabelecido na cl�usula 8, responder� o CONDOM�NIO por multa rescis�ria calculada da seguinte forma: valor dos equipamentos emprestados constante Anexo I reduzido proporcionalmente ao tempo de cumprimento do contrato, adicionado de um ter�o do consumo previsto no Anexo I restante para o t�rmino, de acordo com o pre�o do quilo de GLP vigente no dia da rescis�o. Tal multa ser� paga em uma s� parcela, venc�vel no 5� (quinto) dia posterior a rescis�o.\n\n"
	+
	"8.2. Nas hip�teses previstas na Cl�usula 8.1, as FORNECEDORAS poder�o exigir do CONDOM�NIO a compra dos equipamentos especiais e o pagamento dos investimentos especiais previstos no Anexo I do presente contrato pelo valor constante no referido documento, atualizado pelo IGPM/FGV, ou outro �ndice que venha a substitu�-lo.\n\n"
	+
	"9. O CONDOM�NIO poder� dar por rescindido o presente contrato na hip�tese das FORNECEDORAS n�o cumprirem com as obriga��es aqui assumidas ou na superveni�ncia de interdi��o ou fechamento administrativo, pedido ou declara��o de fal�ncia, recupera��o judicial ou insolv�ncia das FORNECEDORAS. Rescindido o contrato com base nesta cl�usula, responder�o as FORNECEDORAS por uma multa, em favor do CONDOM�NIO, equivalente a dez por cento do valor dos bens emprestados constante do Anexo I atualizados pelo IGPM/FGV, ou outro �ndice que venha a substitu�-lo.\n\n" 
	+
	"10. Fica expressamente ressalvado � parte inocente o direito de pleitear perdas e danos, caso as multas estipuladas neste contrato sejam insuficientes para a cobertura dos preju�zos sofridos, nos termos do par�grafo �nico, do art. 416, do novo C�digo Civil.\n\n"
	
	+
	"IV � Do faturamento individualizado por unidade consumidora\n\n"
	+
	"11. As FORNECEDORAS efetuar�o, mensalmente, a leitura dos medidores individuais instalados no condom�nio, cobrando o g�s consumido diretamente do cond�mino ou morador de cada uma das unidades aut�nomas integrantes do condom�nio. A CONSIGAZ fica desde j� autorizada a efetuar, a seu crit�rio, a leitura ou a cobran�a cumulativa de contas em per�odos bimestrais ou trimestrais, caso entenda haver vantagem operacional com a ado��o desta sistem�tica.\n\n"
	+
	"11.1 O FORNECIMENTO do g�s s� ser� iniciado mediante o cadastramento dos COND�MINOS OU MORADORES  junto �s FORNECEDORAS, sendo que na aus�ncia deste procedimento, ficam desde j� autorizadas as FORNECEDORAS a suspenderem o fornecimento para as unidades aut�nomas que n�o possu�rem o devido cadastro.\n\n"
	+
	"12. O consumo em quilogramas ser� obtido pela multiplica��o do volume consumido, indicado pelo medidor em metros c�bicos (m�) pelo fator de convers�o a ser adotado, de acordo com a press�o de entrada no medidor, caracter�sticas do GLP fornecido e temperatura.\n\n"
	+
	"13. Na hip�tese de fornecimento de GLP para �reas comuns do condom�nio, tais como zeladoria, sal�o de festas, etc.,  este consumo ser� cobrado do CONDOM�NIO, sem preju�zo da cobran�a individualizada do GLP destinado a cada unidade consumidora.\n\n"
	+
	"14. CASO SEJA IMPEDIDA A LEITURA DOS MEDIDORES, A COBRAN�A SER� FEITA COM BASE NO CONSUMO DA �LTIMA CONTA CONHECIDA DE CADA UNIDADE, ACRESCIDO DE 20% (VINTE POR CENTO), OU, N�O HAVENDO CONSUMO CONHECIDO, SER� COBRADO DE CADA UNIDADE O EQUIVALENTE A 35KG (TRINTA E CINCO QUILOS) DE G�S, SEM PREJU�ZO DA COBRAN�A DA DIFEREN�A, CASO SEJA CONSTATADO CONSUMO SUPERIOR AO COBRADO.\n\n"
	+
	"15. O CONDOM�NIO e o MORADOR da unidade aut�noma se obrigam, solidariamente, a comunicar imediatamente �s FORNECEDORAS, por escrito e sob protocolo ou outro comprovante de inequ�voca ci�ncia pelas FORNECEDORAS, toda e qualquer altera��o na titularidade ou ocupa��o de cada unidade aut�noma, ficando o CONDOM�NIO solidariamente respons�vel pelo pagamento das contas das unidades cuja mudan�a n�o for comunicada, ficando sujeito � suspens�o do fornecimento at� que a situa��o seja regularizada, ocasi�o em que poder� ser cobrada a taxa de religue do g�s de acordo com o valor aplic�vel � �poca.\n\n"
	+
	"16. O CONDOM�NIO e os moradores das UNIDADES AUT�NOMAS, conforme o caso, pagar�o o g�s que lhe foi fornecido no prazo previsto no pre�mbulo deste instrumento, mediante boletos de cobran�a banc�ria enviados pelas FORNECEDORAS, de modo que cada consumidor receba sua conta com pelo menos 5 (cinco) dias de anteced�ncia ao vencimento, FICANDO DESDE J� ESTIPULADO QUE O PAGAMENTO AP�S O VENCIMENTO IMPLICAR� NA INCID�NCIA, SOBRE OS VALORES EM ATRASO, DE MULTA DE 8% (OITO POR CENTO) E JUROS CALCULADOS POR DIA DE ATRASO.\n\n"
	+
	"17. Faculta-se �s FORNECEDORAS disponibilizar aos consumidores modalidade de d�bito autom�tico das contas de g�s em conta corrente, mediante ades�o individual dos interessados.\n\n"
	+
	"18. Faculta-se tamb�m �s FORNECEDORAS disponibilizar aos consumidores outras op��es de data de vencimento da conta de g�s.\n\n"
	+
	"19. FICA DESDE J� ESTIPULADO QUE, QUALQUER QUE TENHA SIDO O CONSUMO, SER� ADICIONADO EM CADA CONTA O VALOR DA TARIFA DE SERVI�OS.\n\n"
	+
	"20. NA HIP�TESE DE SER SOLICITADA SEGUNDA VIA DA NOTA FISCAL/FATURA OU BOLETO DE PAGAMENTO, SER� COBRADA DO CONSUMIDOR, TAMB�M, A TARIFA DE EXPEDIENTE VIGENTE NO DIA DA SOLICITA��O.\n\n"
	+
	"21. Os valores da tarifa de servi�os e taxa de religue previstas no Anexo I deste contrato ser�o reajustados anualmente.\n\n" 
	+
	"V � Da inadimpl�ncia dos consumidores\n\n\n" 
	+
	"22. N�O PAGAS AS CONTAS DE G�S NOS SEUS VENCIMENTOS, FICA O CONSUMIDOR SUJEITO, AL�M DOS ACR�SCIMOS MORAT�RIOS PREVISTOS NA CL�USULA 16 DO PRESENTE CONTRATO, �S SEGUINTES PENALIDADES, QUE SER�O APLICADAS INDIVIDUAL OU CONJUNTAMENTE:\n\n"
	
	+"A) PROTESTO DE DUPLICATA;\n"
	+"B) INSCRI��O EM CADASTROS DE PROTE��O AO CR�DITO, TAIS COMO SERASA E SPC;\n"
	+"C) CORTE NO FORNECIMENTO, CASO PERDURE O ATRASO POR MAIS DE 15 (QUINZE) DIAS  E\n"
	+"D) COBRAN�A JUDICIAL DA D�VIDA.\n\n"
	+
	"23. CORTADO O FORNECIMENTO, O G�S SOMENTE SER� RELIGADO NO PRAZO DE 48H (QUARENTA E OITO HORAS) AP�S O PAGAMENTO DAS CONTAS ATRASADAS E SEUS ACR�SCIMOS, BEM COMO DA TAXA DE RELIGA��O VIGENTE NO DIA DA SOLICITA��O.\n\n"
	
	+
	"24. As FORNECEDORAS poder�o dar por rescindido o presente contrato caso haja a inadimpl�ncia de 20% (vinte por cento) das unidades aut�nomas ou caso o montante de contas em atraso, independentemente do n�mero de unidades com pend�ncia, atinja 30% (trinta por cento) do consumo m�dio mensal de g�s global do condom�nio, que ser� apurado mediante a m�dia aritm�tica do consumo global de g�s verificado nos �ltimos tr�s meses, ou em per�odo menor, caso o contrato n�o tenha ainda atingido esta vig�ncia.\n\n"
	+
	"25. Verificada a inadimpl�ncia prevista na cl�usula 24, as FORNECEDORAS notificar�o o ocorrido ao CONDOM�NIO, por carta simples, com 30 (trinta) dias de anteced�ncia da retirada dos tanques, prazo em que os consumidores com pend�ncia ou o CONDOM�NIO poder�o evitar a rescis�o, purgando na totalidade a mora dos inadimplentes, entretanto, ficar� assegurado, durante este per�odo, o fornecimento de g�s a todas as unidades, exceto �quelas com corte de fornecimento.\n\n"
	+
	"VI � Das disposi��es gerais\n\n"
	+
	"26. A manuten��o da rede interna de distribui��o de g�s e demais instala��es nas �reas comuns incumbe com exclusividade ao CONDOM�NIO. Os reguladores de press�o de segundo est�gio, se individualizados, e os medidores ser�o de responsabilidade dos COND�MINOS ou MORADORES, correndo �s suas expensas as despesas com sua manuten��o ou troca, que dever� ocorrer em at� 48h (quarenta e oito horas) ap�s a identifica��o do problema.\n\n"
	+
	"26.1 Caso o prazo estipulado na cl�usula 26 n�o seja atendido, as FORNECEDORAS ficam desde j� autorizadas a realizar a troca dos reguladores de press�o de segundo est�gio, se individualizados, e dos medidores com defeitos/problemas, sendo cobrado do respectivo COND�MINO ou MORADOR o valor do equipamento de acordo com a sua tabela vigente.\n\n" 
	+
	"27. Na hip�tese do COND�MINO ou MORADOR identificar qualquer situa��o que possa acarretar qualquer tipo de risco � sua seguran�a e dos outros cond�minos ou moradores, como vazamento de g�s, entre outros, este dever� desligar imediatamente todos os equipamentos que utilizam g�s, bem como acionar, imediatamente, a assist�ncia t�cnica das FORNECEDORAS.\n\n"
	+
	"27.1 Caso o COND�MINO ou MORADOR precise efetuar algum servi�o ou altera��o nas instala��es de g�s de sua unidade, ou mesmo desconectar seu fog�o ou aquecedor, DEVE SOLICITAR COM ANTECED�NCIA � ADMINISTRA��O DO EDIF�CIO O FECHAMENTO PR�VIO DO REGISTRO RELATIVO � SUA UNIDADE. JAMAIS EFETUE SERVI�OS OU MODIFICA��ES COM O G�S ABERTO, O QUE PODER� CAUSAR EXPLOS�O E INC�NDIO.\n\n"
	+
	"28. A MANUTEN��O DAS INSTALA��ES DE G�S INTERNAS DE CADA UNIDADE AUT�NOMA INCUMBIR�, COM EXCLUSIVIDADE, A CADA COND�MINO OU MORADOR.\n\n"
	+
	"29. Findo o presente contrato, qualquer que seja o motivo, obriga-se o CONDOM�NIO a permitir �s FORNECEDORAS, de imediato, a retirada dos equipamentos emprestados. A inobserv�ncia desta disposi��o acarretar� a incid�ncia imediata de multa di�ria por reten��o no valor de 2% (dois por cento) do valor dos equipamentos emprestados, limitado ao valor total dos bens emprestados constante do Anexo I, at� a data da efetiva devolu��o ou retirada dos bens, sem preju�zo da tomada das medidas judiciais cab�veis em face do esbulho possess�rio que ficar� de pleno direito, caracterizado.\n\n"
	+
	"30. No ato da retirada dos tanques as FORNECEDORAS efetuar�o a leitura final dos medidores, para fins de cobran�a do g�s consumido at� o �ltimo dia de vig�ncia do presente contrato.\n\n"
	+
	"30.1 CASO SEJA IMPEDIDA A LEITURA DOS MEDIDORES, A COBRAN�A SER� FEITA COM BASE NO CONSUMO DA �LTIMA CONTA CONHECIDA DE CADA UNIDADE, ACRESCIDO DE 20% (VINTE POR CENTO), OU, N�O HAVENDO CONSUMO CONHECIDO, SER� COBRADO DE CADA UNIDADE O EQUIVALENTE A 35KG (TRINTA E CINCO QUILOS) DE G�S, SEM PREJU�ZO DA COBRAN�A DA DIFEREN�A, CASO SEJA CONSTATADO CONSUMO SUPERIOR AO COBRADO.\n\n"
	+
	"31. O presente contrato ter� a dura��o especificada no Anexo I deste instrumento, renovando-se por iguais per�odos sucessivos a menos que haja oposi��o de qualquer das partes, manifestada por escrito e com anteced�ncia m�nima de 90 (noventa) dias da data de seu vencimento, sendo que na sua aus�ncia renovam-se todas as suas cl�usulas.\n\n"
	+
	"31.1. Caso haja a den�ncia do contrato na forma especificada na cl�usula anterior, e ao final do t�rmino do contrato o CONDOM�NIO ainda n�o tiver consumido a quantidade prevista no Anexo I, a diferen�a ser� cobrada do CONDOM�NIO pelo pre�o do quilo do GLP vigente na data de encerramento do neg�cio jur�dico.\n\n" 
	+
	"32. Caso o CONDOM�NIO se oponha � renova��o do contrato em raz�o de melhor oferta de terceiro, fica desde j� concedido �s FORNECEDORAS o direito de prefer�ncia.\n\n"
	+
	"33. Eventual toler�ncia de uma parte a infra��es ou descumprimento das disposi��es do presente contrato cometidas pela outra parte, ser� considerada como ato de mera liberalidade n�o se constituindo em perd�o, precedente, nova��o ou ren�ncia a direitos que a legisla��o ou o contrato assegurem �s partes.\n\n"
	+
	"34. Este contrato continuar� em vigor, ainda que qualquer das partes seja objeto de incorpora��o, fus�o, cis�o, cess�o ou qualquer altera��o contratual ou societ�ria, obrigando-se a parte a comunicar o fato, imediatamente a outra, bem como dar ci�ncia aos sucessores da exist�ncia do presente instrumento, sob pena de responsabilizar-se solidariamente pelos preju�zos causados por rescis�o imotivada do sucessor. A referida comunica��o ser� anexada ao presente contrato fazendo parte integrante deste.\n\n"
	+
	"35. Para os investimentos e equipamentos especiais poder� ser emitida nota fiscal com a natureza de bonifica��o, o que n�o prejudicar� o disposto na Cl�usula 8.2, ou seja, referida natureza ter� valor apenas para quest�es fiscais."	
	+
	"36. O CONDOM�NIO se obriga a disponibilizar uma c�pia deste instrumento para cada COND�MINO ou MORADOR logo ap�s a sua assinatura.\n\n "	
	+
	"37. O CONDOM�NIO declara que recebeu o �Manual do Cliente� elaborado pela FORNECEDORA, o qual tamb�m encontra-se dispon�vel para �download� no site www.consigaz.com.br. Ainda, a CONDOM�NIO se compromete a observar os procedimentos descritos no referido manual, com destaque aos relativos � seguran�a.\n\n "
	+
	"38. O abastecimento do GLP ser� realizado por qualquer uma das tr�s fornecedoras especificadas no pre�mbulo deste instrumento, ao exclusivo crit�rio das FORNECEDORAS, e, ainda, por outra empresa que venha a integrar este grupo econ�mico. \n\n "
	+
	"39. Fica eleito o Foro de S�o Paulo-SP para dirimir quaisquer d�vidas ou lit�gios oriundos do presente contrato, com ren�ncia expressa de qualquer outro, por mais privilegiado que seja.\n\n "
	+
	"E por estarem assim justas e contratadas, assinam o presente instrumento nas suas duas vias de igual teor, na presen�a de duas testemunhas.\n\n";
				
	return textoContrato;
	}

}
