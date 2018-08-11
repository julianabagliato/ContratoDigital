package mobile.contratodigital.ws;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.model.ContratoUtil;
import mobile.contratodigital.util.Aviso;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.MontaJSONObjectExportar;
import mobile.contratodigital.util.TrabalhaComArquivos;
import sharedlib.contratodigital.model.Movimento;
import  mobile.contratodigital.view.ActivityListaClientesExportacao;

public class ExportarDadosWS {

	private String URLescolhida;
	private static final String RESOURCE_REST_RETORNO_INSERE_RETORNOS = "/Retorno/insereRetornos/";
	private ProgressDialog progressDialog;
	private Context context;
	
	public ExportarDadosWS(Context _context, String URLescolhida) {
		this.context = _context;
		this.URLescolhida = URLescolhida;
	}

	public void exportar(final int nrVisita, final String diretorioDoCliente, final Movimento movimento) {

		RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

		iniciaProgressDialog();

		String url = URLescolhida + RESOURCE_REST_RETORNO_INSERE_RETORNOS;

		MontaJSONObjectExportar montaJSONObjectExportar = new MontaJSONObjectExportar(context);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

				Request.Method.POST, 
				url, 
				montaJSONObjectExportar.montaJSONObject(nrVisita),

				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jSONObject_response) {

						encerraProgressDialog();

						if (jSONObject_response.has("terminouDeInserir")) {

							try {
								if (jSONObject_response.getInt("terminouDeInserir") == 1) {
									
									
									Aviso aviso = (ActivityListaClientesExportacao) context;
										  aviso.avisaQueTerminou(movimento);
									
									//deletaCliente(movimento);
								} 
								else if (jSONObject_response.getInt("terminouDeInserir") == -1) {

									new MeuAlerta("ERRO, favor tentar novamente", null, context).meuAlertaOk();
								} else {
									new MeuAlerta("Terminou de inserir diferente de 1 e -1:", null, context).meuAlertaOk();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						encerraProgressDialog();
						new MeuAlerta("VolleyError: "+volleyError, null, context).meuAlertaOk();
					}
				});
						 jsonObjectRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());
		requestQueue.add(jsonObjectRequest);
	}

	private void iniciaProgressDialog() {
		progressDialog = new ProgressDialog(context);
		//progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Sincronizando...");
		progressDialog.show();
	}

	private void encerraProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

}
