package mobile.contratodigital.util;

import java.math.BigDecimal;
import br.com.caelum.stella.inwords.FormatoDeReal;
import br.com.caelum.stella.inwords.InteiroSemFormato;
import br.com.caelum.stella.inwords.NumericToWordsConverter;
/**
 * Classe criada para tratar moedas
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class MoedaRS {

	public String converteNumeroParaExtensoInteiro(int valor){
	
		NumericToWordsConverter numericToWordsConverter = new NumericToWordsConverter(new InteiroSemFormato());  
		String valorPorExtenso = numericToWordsConverter.toWords(valor);  
		String valorPorExtensoSemAcentos = valorPorExtenso.replaceAll("[^a-zA-Z ]", "e");
			
		return valor+" ("+valorPorExtensoSemAcentos+") ";
	}

	public String converteNumeroParaExtensoReais(double valor){
		
		NumericToWordsConverter converter = new NumericToWordsConverter(new FormatoDeReal());  
		String valorPorExtenso = converter.toWords(valor);
		String valorPorExtensoSemAcentos = valorPorExtenso.replaceAll("[^a-zA-Z ]", "e");
		return desenhaReaisComPontoEvirgula(""+valor, 2, 1) + " ("+valorPorExtensoSemAcentos+")";
	}

	private String desenhaReaisComPontoEvirgula(String value, int qtdCasasDecimais, int divididoPor100) {

		BigDecimal parsed = null;

		StringBuilder stringBuilder = new StringBuilder();

		parsed = new BigDecimal(value).setScale(qtdCasasDecimais, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(divididoPor100), BigDecimal.ROUND_FLOOR);

		String parsedd = parsed.toString();

		String cleanStrin = parsedd.replace(".", ",");

		int tamanhoTotal = cleanStrin.length();

		int tamanhoAtehAvirgula = cleanStrin.substring(0, cleanStrin.indexOf(",")).length();
		
		stringBuilder.append(cleanStrin);

		if(tamanhoAtehAvirgula >= 4){

			stringBuilder.insert(tamanhoTotal - (stringBuilder.substring(cleanStrin.indexOf(",") - 3).length() ), ".");
		}

		if(tamanhoAtehAvirgula >= 7){

			stringBuilder.insert(tamanhoTotal - (stringBuilder.substring(cleanStrin.indexOf(",") - 5).length() ), ".");
		}
		return "R$ "+stringBuilder;
	}

}
