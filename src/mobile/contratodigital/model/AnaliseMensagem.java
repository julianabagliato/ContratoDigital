package mobile.contratodigital.model;

import android.graphics.Color;
import android.widget.TextView;

public class AnaliseMensagem {

	public static void colorePayBack(TextView tv_paybackMeses, double payBackMeses1){
		
		String _payBackMeses = ""+ payBackMeses1;
		String string_paybackMeses = tv_paybackMeses.getText().toString();
		
		if(!string_paybackMeses.contains("Infinity") && !string_paybackMeses.contains("NaN") && 
				 !_payBackMeses.contains("Infinity") && !_payBackMeses.contains("NaN")){
			if(payBackMeses1 >=0 && payBackMeses1 <=24){
				tv_paybackMeses.setBackgroundColor(Color.GREEN);
				tv_paybackMeses.setTextColor(Color.GREEN);
			}
			else if(payBackMeses1 >24 && payBackMeses1 <=28){
				tv_paybackMeses.setBackgroundColor(Color.YELLOW);
				tv_paybackMeses.setTextColor(Color.YELLOW);

			}
			else if(payBackMeses1 >28){
				tv_paybackMeses.setBackgroundColor(Color.RED);
				tv_paybackMeses.setTextColor(Color.RED);

			}
			else{
				tv_paybackMeses.setBackgroundColor(Color.WHITE);
				tv_paybackMeses.setTextColor(Color.WHITE);

			}
		
		
		}		
		
	}

	public static void coloreValorFuturo(TextView tv_valorFuturo, double valorFuturo){
		
		String string_valorFuturo = ""+ valorFuturo;
		String string_tv_valorFuturo = tv_valorFuturo.getText().toString();
		
		if(!string_tv_valorFuturo.contains("Infinity") && !string_tv_valorFuturo.contains("NaN") && 
			  !string_valorFuturo.contains("Infinity") && !string_valorFuturo.contains("NaN")){
			
			if(valorFuturo > 200 ){
				tv_valorFuturo.setBackgroundColor(Color.GREEN);
				tv_valorFuturo.setTextColor(Color.GREEN);
			}
			else if(valorFuturo >= -199.999999 && valorFuturo <= 199.999999){
				tv_valorFuturo.setBackgroundColor(Color.YELLOW);
				tv_valorFuturo.setTextColor(Color.YELLOW);

			}
			else if(valorFuturo < -200){
				tv_valorFuturo.setBackgroundColor(Color.RED);
				tv_valorFuturo.setTextColor(Color.RED);

			}
			else{
				tv_valorFuturo.setBackgroundColor(Color.WHITE);
				tv_valorFuturo.setTextColor(Color.WHITE);

			}
		}					
	}

}
