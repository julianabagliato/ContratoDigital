package mobile.contratodigital.ws;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import android.app.ProgressDialog;
import android.content.Context;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.IpURL;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.MontaJSONObjectExportar;
import sharedlib.contratodigital.model.Movimento;
/**
 * Classe para tratar a exportação via webservice
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class BotaoExportarWS {

	private static final String RESOURCE_REST_RETORNO_INSERE_RETORNOS = "/Retorno/insereRetornos/";
	private static final String RESOURCE_REST_ARQUIVOS = "/Retorno/Arquivo/";
	private ProgressDialog progressDialog;
	private Context context;
	
	public BotaoExportarWS(Context _context) {
		this.context = _context;
	}

	public void exportar() {

		RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

		iniciaProgressDialog();

		String url = IpURL.URL_SERVER_REST.getValor() + RESOURCE_REST_RETORNO_INSERE_RETORNOS;

		MontaJSONObjectExportar montaJSONObjectExportar = new MontaJSONObjectExportar(context);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

				Request.Method.POST, 
				url, 
				montaJSONObjectExportar.montaJSONObject(),

				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jSONObject_response) {

						encerraProgressDialog();

						if (jSONObject_response.has("terminouDeInserir")) {

							try {
								if (jSONObject_response.getInt("terminouDeInserir") == 1) {
									
									Dao dao = new Dao(context);	
									dao.deletaObjeto(Movimento.class, 1, 1);
										
									//Toast.makeText(context, "Dados enviados!", Toast.LENGTH_SHORT).show();	
									
								
									
								} 
								else if (jSONObject_response.getInt("terminouDeInserir") == -1) {

									new MeuAlerta("ERRO, favor tentar novamente", null, context).meuAlertaOk();

								//	Toast.makeText(context, "ERRO, favor tentar novamente", Toast.LENGTH_SHORT).show();
								} else {
									new MeuAlerta("Terminou de inserir diferente de 1 e -1:", null, context).meuAlertaOk();

									//Toast.makeText(context, "Terminou de inserir diferente de 1 e -1: " + jSONObject_response, Toast.LENGTH_SHORT).show();
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
						new MeuAlerta("onErrorResponse volleyError: " + volleyError, null, context).meuAlertaOk();

						//Toast.makeText(context, "onErrorResponse volleyError: " + volleyError, Toast.LENGTH_SHORT).show();
					}
				});
						 jsonObjectRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());
		requestQueue.add(jsonObjectRequest);
	}

	private void iniciaProgressDialog() {

		progressDialog = new ProgressDialog(context);
		progressDialog.setCanceledOnTouchOutside(false);
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
