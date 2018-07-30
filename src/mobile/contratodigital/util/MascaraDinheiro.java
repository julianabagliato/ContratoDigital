package mobile.contratodigital.util;

import java.math.BigDecimal;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
/**
 * Classe criada para tratar mascaras de dinheiro
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class MascaraDinheiro implements TextWatcher {

	final EditText editText;
	private int qtdCasasDecimais;
	
	public MascaraDinheiro(EditText _editText, int _qtdCasasDecimais) {
		super();
		this.editText = _editText;
		this.qtdCasasDecimais = _qtdCasasDecimais;
	}

	private boolean estaAtualizando = false;
		
	@Override
	public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

		// Evita que o método seja executado varias vezes.
		if (estaAtualizando) {
			estaAtualizando = false;
			return;
		}
		estaAtualizando = true;
		// Se tirar ele entra em loop

		int divididoPor;

		switch(qtdCasasDecimais){
					
		case 3: divididoPor = 1000;
			break;
		case 4: divididoPor = 10000;
			break;
		case 5: divididoPor = 100000;
			break;
		default: divididoPor = 100;
        	break;
		}
		if(divididoPor == 100){
			qtdCasasDecimais = 2;
		}
			
		String stringComSimbolos = charSequence.toString();
		
		String stringLimpa = stringComSimbolos.replaceAll("[.]", "").replaceAll("[,]", "");

		BigDecimal parsed = null;

		StringBuilder stringBuilder = new StringBuilder();

		parsed = new BigDecimal(stringLimpa).setScale(qtdCasasDecimais, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(divididoPor), BigDecimal.ROUND_FLOOR);

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
				
		editText.setText(stringBuilder);
		editText.setSelection(editText.getText().length());
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}
	@Override
	public void afterTextChanged(Editable s) {
	}
}
