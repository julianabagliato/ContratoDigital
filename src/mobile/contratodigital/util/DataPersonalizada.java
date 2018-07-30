package mobile.contratodigital.util;

import java.text.SimpleDateFormat;
import java.util.Date;
	/**
	 * Classe criada para retornar data formatada
	 *  @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
	 *         Pereira Santos - Consigaz -2017
	 * 
	 * @version 1.0
	 */
public class DataPersonalizada {
	
	private static String DDMMYYYY_HHMMSS = "dd-MM-yyyy HH:mm:ss";
	private static String DDMMYYYY = "dd-MM-yyyy";
	
	public String pegaDataAtual_DDMMYYYY_HHMMSS() {
			
		return devolveDataAtualNoFormato(DDMMYYYY_HHMMSS);
	}
	
	public String pegaDataAtual_DDMMYYYY() {
		
		return devolveDataAtualNoFormato(DDMMYYYY);
	}

	private String devolveDataAtualNoFormato(String formato){

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
		
		return simpleDateFormat.format(new Date());
	}
	
}

