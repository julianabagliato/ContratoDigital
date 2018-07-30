package mobile.contratodigital.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.Toast;
import mobile.contratodigital.R;
import mobile.contratodigital.enums.TipoView;
import sharedlib.contratodigital.util.Generico;

/**
 * Classe criada para tratar todos os itens de viewgrop
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017 
 *          @version 1.0
 */
public class GetChildCount {

	private ViewGroup viewGroup;

	public GetChildCount() {
	}

	public GetChildCount(ViewGroup _viewGroup) {
		this.viewGroup = _viewGroup;
	}

	public void setEnabled_Todas_EditText_TextView(boolean trueFalse) {

		for (int i = 0; i < viewGroup.getChildCount(); i++) {

			Object child = viewGroup.getChildAt(i);

			if (child instanceof LinearLayout) {

				LinearLayout childLinearLayout = (LinearLayout) child;

				for (int x = 0; x < childLinearLayout.getChildCount(); x++) {

					Object child2 = childLinearLayout.getChildAt(x);

					if (child2 instanceof TextView) {

						TextView childTextView = (TextView) child2;
						childTextView.setEnabled(trueFalse);
					}

					if (child2 instanceof EditText) {

						EditText childEditText = (EditText) child2;
						// childEditText.setEnabled(trueFalse);

						if (childEditText.getTag(R.id.tres) != null) {

							int valorDaTag = Integer.valueOf(childEditText.getTag(R.id.tres).toString());

							if (valorDaTag == TipoView.CAIXA_OPCAO.getValor()) {

								childEditText.setEnabled(false);
							}
						}
					}

				}
			}
		}
	}

	public void setEnabled_TodasCheckBox(boolean trueFalse) {

		for (int i = 0; i < viewGroup.getChildCount(); i++) {

			Object child = viewGroup.getChildAt(i);

			if (child instanceof CheckBox) {

				CheckBox childCheckBox = (CheckBox) child;
				childCheckBox.setEnabled(trueFalse);
			}
		}
	
	}
	public String faltaPreencherEditTextObrigatorioscnpj() {

		String temVazio = "";
int valida = 0;

		for (int i = 0; i < viewGroup.getChildCount(); i++) {

			Object child = viewGroup.getChildAt(i);

			if (child instanceof LinearLayout) {

				LinearLayout linearLayoutChild = (LinearLayout) child;

				for (int x = 0; x < linearLayoutChild.getChildCount(); x++) {

					View view = linearLayoutChild.getChildAt(x);

					if (view instanceof EditText) {

						EditText editTextChild = (EditText) view;

						int valorDaTag = Integer.valueOf(editTextChild.getTag(R.id.dez).toString());

						if (valorDaTag == Generico.OBRIGATORIO_SIM.getValor()) {
							if(editTextChild.getTag()=="cpfcnpj"){
								valida = 1;
								if(editTextChild.getText().toString().length() == 14){
									
									if(mobile.contratodigital.util.ValidaCPF.isCPFValido(editTextChild.getText().toString())== false){
										temVazio = "CPF Invalido";

										return temVazio;
									}else{
										//Toast.makeText( " CPF ok! ", Toast.LENGTH_LONG).show();
										temVazio = "CPF OK";

										return temVazio;
									}
									}else if(editTextChild.getText().toString().length() > 14){
										if(mobile.contratodigital.util.ValidaCNPJ.isCNPJValido((editTextChild.getText().toString()))== false){
											//Toast.makeText(context, "Erro CNPJ: Incorreto ", Toast.LENGTH_LONG).show();
											temVazio = "CNPJ Invalido";

											return temVazio;
										}else{
											//Toast.makeText(context, " CNPJ ok! ", Toast.LENGTH_LONG).show();
											temVazio = "CNPJ OK";

											return temVazio;
										}
									}
							}
							if (editTextChild.getText().toString().isEmpty()) {
								

								temVazio = "Campo Vazio!";

								return temVazio;
							}
							
						}
					}
				}
			}
		}
		if (valida==0){
			temVazio = "OK";

			return temVazio;
		}
		return temVazio;
	}
	public boolean faltaPreencherEditTextObrigatorios() {

		boolean temVazio = false;

		for (int i = 0; i < viewGroup.getChildCount(); i++) {

			Object child = viewGroup.getChildAt(i);

			if (child instanceof LinearLayout) {

				LinearLayout linearLayoutChild = (LinearLayout) child;

				for (int x = 0; x < linearLayoutChild.getChildCount(); x++) {

					View view = linearLayoutChild.getChildAt(x);

					if (view instanceof EditText) {

						EditText editTextChild = (EditText) view;

						int valorDaTag = Integer.valueOf(editTextChild.getTag(R.id.dez).toString());

						if (valorDaTag == Generico.OBRIGATORIO_SIM.getValor()) {
							
							if (editTextChild.getText().toString().isEmpty()) {

								temVazio = true;

								return temVazio;
							}
						}
					}
				}
			}
		}
		return temVazio;
	}

	public void removeTituloHolderDivisoria(int nrProgrComNrForm) {

		for (int i = 0; i < viewGroup.getChildCount(); i++) {

			Object child = viewGroup.getChildAt(i);

			if (child instanceof LinearLayout) {

				LinearLayout linearLayoutChild = (LinearLayout) child;

				LinearLayout linearLayout_titulo = (LinearLayout) linearLayoutChild
						.findViewWithTag("llTitulo" + nrProgrComNrForm);
				LinearLayout linearLayout_aquiVaoOsFragsHolders = (LinearLayout) linearLayoutChild
						.findViewWithTag("llFragHolder" + nrProgrComNrForm);

				View viewDivisoria = linearLayoutChild.findViewWithTag("viewDivisoria" + nrProgrComNrForm);

				linearLayoutChild.removeView(linearLayout_titulo);
				linearLayoutChild.removeView(linearLayout_aquiVaoOsFragsHolders);
				linearLayoutChild.removeView(viewDivisoria);
			}
		}
	}

}
