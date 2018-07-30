package mobile.contratodigital.util;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Mascaras {
	
	public static void adicionaMascaraCPFouCNPJ(final EditText editText, String string) {
		
		editText.addTextChangedListener(new TextWatcher() {

			boolean isUpdating;
			String old = "";

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				String mask = "";
				
				if (editText.getText().toString().length() <= 14) {
					
					mask = Mask.CPF_MASK;
				} else {
					mask = Mask.CNPJ_mask;
				}

				String str = Mask.unmask(s.toString());
				
				String mascara = "";
				
				if (isUpdating) {
					
					old = str;
					isUpdating = false;
					return;
				}

				int index = 0;
				
				for (int i = 0; i < mask.length(); i++) {
					
					char m = mask.charAt(i);
					
					if (m != '#') {
						
						if (index == str.length() && str.length() < old.length()) {
							
							continue;
						}
						mascara += m;
						continue;
					}

					try {
						mascara += str.charAt(index);
						
					} catch (Exception e) {
						break;
					}

					index++;
				}

				if (mascara.length() > 0) {
					
					char last_char = mascara.charAt(mascara.length() - 1);
					boolean hadSign = false;
					
					while (ehUmSinalValido(last_char) && str.length() == old.length()) {
						
						mascara = mascara.substring(0, mascara.length() - 1);
						last_char = mascara.charAt(mascara.length() - 1);
						hadSign = true;
					}

					if (mascara.length() > 0 && hadSign) {
						mascara = mascara.substring(0, mascara.length() - 1);
					}
				}

				isUpdating = true;
				editText.setText(mascara);
				editText.setSelection(mascara.length());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

	}

	public static void adicionaMascaraTelefone(final EditText editText, String string) {
		
		editText.addTextChangedListener(new TextWatcher() {

			boolean isUpdating;
			String old = "";

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				String mask = "";

				mask = Mask.telefone;

				String str = Mask.unmask(s.toString());
				
				String mascara = "";
				
				if (isUpdating) {
					
					old = str;
					isUpdating = false;
					return;
				}

				int index = 0;
				
				for (int i = 0; i < mask.length(); i++) {
					
					char m = mask.charAt(i);
					
					if (m != '#') {
						
						if (index == str.length() && str.length() < old.length()) {
							
							continue;
						}
						mascara += m;
						
						continue;
					}

					try {
						
						mascara += str.charAt(index);
					} catch (Exception e) {
						break;
					}

					index++;
				}

				if (mascara.length() > 0) {
					
					char last_char = mascara.charAt(mascara.length() - 1);
					
					boolean hadSign = false;
					
					while (ehUmSinalValido(last_char) && str.length() == old.length()) {
						
						mascara = mascara.substring(0, mascara.length() - 1);
						last_char = mascara.charAt(mascara.length() - 1);
						hadSign = true;
					}

					if (mascara.length() > 0 && hadSign) {
						mascara = mascara.substring(0, mascara.length() - 1);
					}
				}

				isUpdating = true;
				editText.setText(mascara);
				editText.setSelection(mascara.length());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	public static void adicionaMascaraCelular(final EditText editText, String string) {
		
		
		//editText.setBackgroundColor(Color.BLUE);

		editText.addTextChangedListener(new TextWatcher() {

			boolean isUpdating;
			String old = "";

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				String mask = "";

				mask = Mask.CELULAR_MASK;

				String str = Mask.unmask(s.toString());
				
				String mascara = "";
				
				if (isUpdating) {
					
					old = str;
					isUpdating = false;
					return;
				}

				int index = 0;
				
				for (int i = 0; i < mask.length(); i++) {
					
					char m = mask.charAt(i);
					
					if (m != '#') {
						
						if (index == str.length() && str.length() < old.length()) {
							
							continue;
						}
						mascara += m;
						continue;
					}

					try {
						mascara += str.charAt(index);
						
					} catch (Exception e) {
						break;
					}

					index++;
				}

				if (mascara.length() > 0) {
					
					char last_char = mascara.charAt(mascara.length() - 1);
					
					boolean hadSign = false;
					
					while (ehUmSinalValido(last_char) && str.length() == old.length()) {
						
						mascara = mascara.substring(0, mascara.length() - 1);
						last_char = mascara.charAt(mascara.length() - 1);
						hadSign = true;
					}

					if (mascara.length() > 0 && hadSign) {
						
						mascara = mascara.substring(0, mascara.length() - 1);
					}
				}

				isUpdating = true;
				editText.setText(mascara);
				editText.setSelection(mascara.length());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	public static void adicionaMascaraCEP(final EditText editText, String string) {
		
		editText.addTextChangedListener(new TextWatcher() {

			boolean isUpdating;
			String old = "";

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				String mask = "";

				mask = Mask.CEP_MASK;

				String str = Mask.unmask(s.toString());
				
				String mascara = "";
				
				if (isUpdating) {
					
					old = str;
					isUpdating = false;
					return;
				}

				int index = 0;
				
				for (int i = 0; i < mask.length(); i++) {
					
					char m = mask.charAt(i);
					
					if (m != '#') {
						
						if (index == str.length() && str.length() < old.length()) {
							
							continue;
						}
						mascara += m;
						continue;
					}

					try {
						mascara += str.charAt(index);
						
					} catch (Exception e) {
						break;
					}

					index++;
				}

				if (mascara.length() > 0) {
					
					char last_char = mascara.charAt(mascara.length() - 1);
					
					boolean hadSign = false;
					
					while (ehUmSinalValido(last_char) && str.length() == old.length()) {
						
						mascara = mascara.substring(0, mascara.length() - 1);
						last_char = mascara.charAt(mascara.length() - 1);
						hadSign = true;
					}

					if (mascara.length() > 0 && hadSign) {
						
						mascara = mascara.substring(0, mascara.length() - 1);
					}
				}

				isUpdating = true;
				editText.setText(mascara);
				editText.setSelection(mascara.length());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private static boolean ehUmSinalValido(char c) {
		
		if (c == '.' || c == '-' || c == '/' || c == '(' || c == ')' || c == ',' || c == ' ') {
			return true;
		} else {
			return false;
		}
	}

	
}
