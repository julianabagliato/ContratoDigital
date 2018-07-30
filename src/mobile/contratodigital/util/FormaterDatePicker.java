package mobile.contratodigital.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * Classe criada para retornar a data em ddmmyyyy com barras
 *  @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class FormaterDatePicker {

	public String devolveData_ddMMyyyy(int dia, int mes, int ano) {

		int mesCerto = mes + 1;

		String string_dia;
		String string_mes;
		String string_ano = "" + ano;

		if (dia < 10) {

			string_dia = "0" + dia;
		} else {
			string_dia = "" + dia;
		}
		
		if (mesCerto < 10) {

			string_mes = "0" + mesCerto;
		} else {
			string_mes = "" + mesCerto;
		}

		return string_dia + "/" + string_mes + "/" + string_ano; // + "00:00:00"
	}

	public String devolveData_yyyyMMdd(int dia, int mes, int ano) {

		int mesCerto = mes + 1;

		String string_dia;
		String string_mes;
		String string_ano = "" + ano;

		if (dia < 10) {

			string_dia = "0" + dia;
		} else {
			string_dia = "" + dia;
		}

		if (mesCerto < 10) {

			string_mes = "0" + mesCerto;
		} else {
			string_mes = "" + mesCerto;
		}

		return string_ano +""+ string_mes +""+ string_dia;
	}
	
    public String devolveDataAtualComBarras(){
    	
    	Date data =  new Date();
    	Locale locale = new Locale("pt","BR");
    	DateFormat dateFormat = new SimpleDateFormat("dd '/' MM '/' yyyy", locale); 
    	String dataFormatada = dateFormat.format(data);
  
    	return dataFormatada;
    }

}
