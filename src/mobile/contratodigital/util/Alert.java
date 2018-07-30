package mobile.contratodigital.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

	/**
	 * Classe criada para tratar a criação das activitys de contratos de modo generico
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
	        INFO(android.R.drawable.ic_dialog_info, "Informação"), WARN(
	                android.R.drawable.ic_dialog_alert, "Alerta"), SUCESS(
	                android.R.drawable.checkbox_on_background, "Sucesso"), ERROR(
	                android.R.drawable.ic_delete, "Erro");
	 
	        // número inteiro que guarda o valor do ícone que será mostrado no
	        // dialog
	        private int drawable;
	 
	        // Título do dialog
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
	     * Mostra um dialog simples com um botão OK
	     * 
	     * @param message
	     *            mensagem/conteúdo que aparecerá no dialog
	     * @param context
	     *            contexto da aplicação
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
	     * Mostra um dialog simples com um botão OK, com ação ao clicar no OK
	     * 
	     * @param message
	     *            mensagem/conteúdo que aparecerá no dialog
	     * @param context
	     *            contexto da aplicação
	     * @param alertType
	     *            tipo de alerta(INFO, WARN, SUCESS, ERROR)
	     * @param okButton
	     *            ação do botão ok
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
	     * Mostra um dialog com um botão Sim e um botão Não, com ação no botão Sim e
	     * outra ação no botão Não
	     * 
	     * @param message
	     *            mensagem/conteúdo que aparecerá no dialog
	     * @param context
	     *            contexto da aplicação
	     * @param yesButton
	     *            ação do botão Sim
	     * @param noButton
	     *            ação do botão Não
	     * @param alertType
	     *            tipo de alerta(INFO, WARN, SUCESS, ERROR)
	     */
	    public static void showYesNoDialog(String message, Context context,
	            OnClickListener yesButton, OnClickListener noButton,
	            AlertType alertType) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	        alertDialog.setMessage(message);
	        alertDialog.setPositiveButton("Sim", yesButton);
	        alertDialog.setNegativeButton("Não", noButton);
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
	     * Mostra um dialog com um botão Sim e um botão Não, com ação somente no
	     * botão Sim
	     * 
	     * @param message
	     *            mensagem/conteúdo que aparecerá no dialog
	     * @param context
	     *            contexto da aplicação
	     * @param yesButton
	     *            ação do botão Sim
	     * @param alertType
	     *            tipo de alerta(INFO, WARN, SUCESS, ERROR)
	     */
	    public static void showYesNoDialog(String message, Context context,
	            OnClickListener yesButton, AlertType alertType) {
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
	        alertDialog.setMessage(message);
	        alertDialog.setPositiveButton("Sim", yesButton);
	        alertDialog.setNegativeButton("Não", null);
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
	     * Mostra um dialog com um botão Ok e um botão Cancelar, com ação ao clicar
	     * no botão Ok e outra ação ao clicar em Cancelar
	     * 
	     * @param message
	     *            mensagem/conteúdo que aparecerá no dialog
	     * @param context
	     *            contexto da aplicação
	     * @param okButton
	     *            ação do botão Ok
	     * @param cancelButton
	     *            ação do botão Cancelar
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
	     * Mostra um dialog com um botão Ok e um botão Cancelar, com ação somente no
	     * botão Ok
	     * 
	     * @param message
	     *            mensagem/conteúdo que aparecerá no dialog
	     * @param context
	     *            contexto da aplicação
	     * @param okButton
	     *            ação do botão Ok
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

