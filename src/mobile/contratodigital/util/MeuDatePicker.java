package mobile.contratodigital.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import mobile.contratodigital.R;
import android.widget.DatePicker.OnDateChangedListener;
/**
 * Classe criada para tratar datas  e calendarios de forma geral
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class MeuDatePicker {

	private int int_ano_escolhido;
	private int int_mes_escolhido;
	private int int_dia_escolhido;
	private String string_dataEscolhida_ddMMyyyy;

	public void criaEmostraDataPicker(Context context, final EditText editText_data, String titulo){
		
		final FormaterDatePicker formaterDatePicker = new FormaterDatePicker();

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout linearLayout_tela = (LinearLayout) inflater.inflate(R.layout.datapicker, null);

		final Calendar calendar_dataAtual = Calendar.getInstance();
		final int int_ano_atual = calendar_dataAtual.get(Calendar.YEAR);
		final int int_mes_atual = calendar_dataAtual.get(Calendar.MONTH);
		final int int_dia_atual = calendar_dataAtual.get(Calendar.DAY_OF_MONTH);

		TextView textView_titulo = (TextView) linearLayout_tela.findViewById(R.id.datePickerTitulo);
				 textView_titulo.setText(titulo);
				 
		DatePicker datePicker = (DatePicker) linearLayout_tela.findViewById(R.id.datePicker);
				   datePicker.init(int_ano_atual, int_mes_atual, int_dia_atual, new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int _ano_escolhido, int _mes_escolhido, int _dia_escolhido) {

				int_ano_escolhido = _ano_escolhido;
				int_mes_escolhido = _mes_escolhido;
				int_dia_escolhido = _dia_escolhido;

				string_dataEscolhida_ddMMyyyy = formaterDatePicker.devolveData_ddMMyyyy(int_dia_escolhido, int_mes_escolhido, int_ano_escolhido);
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
							builder.setView(linearLayout_tela);
		final AlertDialog dialog = builder.show();

		Button buttonOk = (Button) linearLayout_tela.findViewById(R.id.button_datePicker_ok);
		buttonOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String string_dataAtual_yyyyMMdd = formaterDatePicker.devolveData_yyyyMMdd(int_dia_atual, int_mes_atual, int_ano_atual);

				String string_dataEscolhida_yyyyMMdd = formaterDatePicker.devolveData_yyyyMMdd(int_dia_escolhido, int_mes_escolhido, int_ano_escolhido);

				if (string_dataEscolhida_yyyyMMdd.length() < 8) {

					string_dataEscolhida_yyyyMMdd = string_dataAtual_yyyyMMdd;
				}

				SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat simpleDateFormat_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
				String dataAtualFormatada = simpleDateFormat_ddMMyyyy.format(new Date());

				try {

					Date date_dataAtual_yyyyMMdd = new Date(simpleDateFormat_yyyyMMdd.parse(string_dataAtual_yyyyMMdd).getTime());
					Date date_dataEscolhida_yyyyMMdd = new Date(simpleDateFormat_yyyyMMdd.parse(string_dataEscolhida_yyyyMMdd).getTime());

					if (date_dataEscolhida_yyyyMMdd.after(date_dataAtual_yyyyMMdd)) {

						editText_data.setText(string_dataEscolhida_ddMMyyyy);
					} else {
						editText_data.setText(dataAtualFormatada);
					}
				} 
				catch (ParseException e) {
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		});
	}

}
