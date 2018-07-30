package mobile.contratodigital.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

	/**
	 * Classe criada para tratar a cria��o das activitys de contratos de modo generico
	 * 
	 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 
	 * 
	 * @version 1.0
	 */
	public class Alert {
	    /**
	     * Enum criado para formalizar os diversos tipos de alertas que podem ser
	     * mostrados.
	     * 
	     * @see INFO
	     * @see WARN
	     * @see SUCESS
	     * @see ERROR
	     */
	    public enum AlertType {
	        INFO(android.R.drawable.ic_dialog_info, "Informa��o"), WARN(
	                android.R.drawable.ic_dialog_alert, "Alerta"), SUCESS(
	                android.R.drawable.checkbox_on_background, "Sucesso"), ERROR(
	                android.R.drawable.ic_delete, "Erro");
	 
	        // n�mero inteiro que guarda o valor do �cone que ser� mostrado no
	        // dialog
	        private int drawable;
	 
	        // T�tulo do dialog
	        private String title;
	 
	        AlertType(int drawable, String title) {
	            this.drawable = drawable;
	            this.title = title;
	        }
	 
	        public int getDrawable() {
	            return this.drawable;
	        }
	 
	        public String getTitle() {
	            return this.title;
	        }
	    }
	 
	    /**
	     * Mostra um dialog simples com um bot�o OK
	     * 
	     * @param message
	     *            mensagem/conte�do que aparecer� no dialog
	     * @param context
	     *            contexto da aplica��o
	     * @param alertType
	     *            tipo de alerta(INFO, WARN, SUCESS, ERROR)
	     */
	    public static void showSimpleDialog(String message, Context context,
	            AlertType alertType) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	        alertDialog.setMessage(message);
	        alertDialog.setNeutralButton("OK", null);
	        if (alertType == null) {
	            alertDialog.setIcon(AlertType.INFO.getDrawable());
	            alertDialog.setTitle(AlertType.INFO.getTitle());
	        } else {
	            alertDialog.setIcon(alertType.getDrawable());
	            alertDialog.setTitle(alertType.getTitle());
	        }
	        alertDialog.show();
	    }
	    public static void showInputDialog(String message, final Context context,
	            EditText edt, AlertType alertType, OnClickListener okClick) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	        alertDialog.setMessage(message);
	        alertDialog.setView(edt);
	        alertDialog.setPositiveButton("Ok", okClick);
	        if (alertType == null) {
	            alertDialog.setIcon(AlertType.INFO.getDrawable());
	            alertDialog.setTitle(AlertType.INFO.getTitle());
	        } else {
	            alertDialog.setIcon(alertType.getDrawable());
	            alertDialog.setTitle(alertType.getTitle());
	        }
	        alertDialog.show();
	    }
	    /**
	     * Mostra um dialog simples com um bot�o OK, com a��o ao clicar no OK
	     * 
	     * @param message
	     *            mensagem/conte�do que aparecer� no dialog
	     * @param context
	     *            contexto da aplica��o
	     * @param alertType
	     *            tipo de alerta(INFO, WARN, SUCESS, ERROR)
	     * @param okButton
	     *            a��o do bot�o ok
	     */
	    public static void showSimpleDialog(String message, Context context,
	            AlertType alertType, OnClickListener okButton) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	        alertDialog.setMessage(message);
	        alertDialog.setNeutralButton("OK", okButton);
	        if (alertType == null) {
	            alertDialog.setIcon(AlertType.INFO.getDrawable());
	            alertDialog.setTitle(AlertType.INFO.getTitle());
	        } else {
	            alertDialog.setIcon(alertType.getDrawable());
	            alertDialog.setTitle(alertType.getTitle());
	        }
	        alertDialog.show();
	    }
	 
	    /**
	     * Mostra um dialog com um bot�o Sim e um bot�o N�o, com a��o no bot�o Sim e
	     * outra a��o no bot�o N�o
	     * 
	     * @param message
	     *            mensagem/conte�do que aparecer� no dialog
	     * @param context
	     *            contexto da aplica��o
	     * @param yesButton
	     *            a��o do bot�o Sim
	     * @param noButton
	     *            a��o do bot�o N�o
	     * @param alertType
	     *            tipo de alerta(INFO, WARN, SUCESS, ERROR)
	     */
	    public static void showYesNoDialog(String message, Context context,
	            OnClickListener yesButton, OnClickListener noButton,
	            AlertType alertType) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	        alertDialog.setMessage(message);
	        alertDialog.setPositiveButton("Sim", yesButton);
	        alertDialog.setNegativeButton("N�o", noButton);
	        if (alertType == null) {
	            alertDialog.setIcon(AlertType.INFO.getDrawable());
	            alertDialog.setTitle(AlertType.INFO.getTitle());
	        } else {
	            alertDialog.setIcon(alertType.getDrawable());
	            alertDialog.setTitle(alertType.getTitle());
	        }
	        alertDialog.show();
	    }
	 
	    /**
	     * Mostra um dialog com um bot�o Sim e um bot�o N�o, com a��o somente no
	     * bot�o Sim
	     * 
	     * @param message
	     *            mensagem/conte�do que aparecer� no dialog
	     * @param context
	     *            contexto da aplica��o
	     * @param yesButton
	     *            a��o do bot�o Sim
	     * @param alertType
	     *            tipo de alerta(INFO, WARN, SUCESS, ERROR)
	     */
	    public static void showYesNoDialog(String message, Context context,
	            OnClickListener yesButton, AlertType alertType) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	        alertDialog.setMessage(message);
	        alertDialog.setPositiveButton("Sim", yesButton);
	        alertDialog.setNegativeButton("N�o", null);
	        if (alertType == null) {
	            alertDialog.setIcon(AlertType.INFO.getDrawable());
	            alertDialog.setTitle(AlertType.INFO.getTitle());
	        } else {
	            alertDialog.setIcon(alertType.getDrawable());
	            alertDialog.setTitle(alertType.getTitle());
	        }
	        alertDialog.show();
	    }
	 
	    /**
	     * Mostra um dialog com um bot�o Ok e um bot�o Cancelar, com a��o ao clicar
	     * no bot�o Ok e outra a��o ao clicar em Cancelar
	     * 
	     * @param message
	     *            mensagem/conte�do que aparecer� no dialog
	     * @param context
	     *            contexto da aplica��o
	     * @param okButton
	     *            a��o do bot�o Ok
	     * @param cancelButton
	     *            a��o do bot�o Cancelar
	     * @param alertType
	     *            tipo de alerta(INFO, WARN, SUCESS, ERROR)
	     */
	    public static void showConfirmDialog(String message, Context context,
	            OnClickListener okButton, OnClickListener cancelButton,
	            AlertType alertType) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	        alertDialog.setMessage(message);
	        alertDialog.setPositiveButton("Ok", okButton);
	        alertDialog.setNegativeButton("Cancelar", cancelButton);
	        if (alertType == null) {
	            alertDialog.setIcon(AlertType.INFO.getDrawable());
	            alertDialog.setTitle(AlertType.INFO.getTitle());
	        } else {
	            alertDialog.setIcon(alertType.getDrawable());
	            alertDialog.setTitle(alertType.getTitle());
	        }
	        alertDialog.show();
	    }
	 
	    /**
	     * Mostra um dialog com um bot�o Ok e um bot�o Cancelar, com a��o somente no
	     * bot�o Ok
	     * 
	     * @param message
	     *            mensagem/conte�do que aparecer� no dialog
	     * @param context
	     *            contexto da aplica��o
	     * @param okButton
	     *            a��o do bot�o Ok
	     * @param alertType
	     *            tipo de alerta(INFO, WARN, SUCESS, ERROR)
	     */
	    public static void showConfirmDialog(String message, Context context,
	            OnClickListener okButton, AlertType alertType) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	        alertDialog.setMessage(message);
	        alertDialog.setPositiveButton("Ok", okButton);
	        alertDialog.setNegativeButton("Cancelar", null);
	        if (alertType == null) {
	            alertDialog.setIcon(AlertType.INFO.getDrawable());
	            alertDialog.setTitle(AlertType.INFO.getTitle());
	        } else {
	            alertDialog.setIcon(alertType.getDrawable());
	            alertDialog.setTitle(alertType.getTitle());
	        }
	        alertDialog.show();
	    }
	 
	}

