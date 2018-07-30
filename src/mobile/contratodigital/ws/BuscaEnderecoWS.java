package mobile.contratodigital.ws;

import java.lang.reflect.Field;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
//import android.widget.Toast;
import mobile.contratodigital.model.Endereco;
import mobile.contratodigital.util.MeuAlerta;
/**
 * classe de busca de endereços via correio 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class BuscaEnderecoWS {
	
	public void buscarPorCEP(final Context context, final LinearLayout linearLayoutPrincipal, String cep) {
		
		RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();

		String url = "https://viacep.com.br/ws/"+cep.replace("-","" )+"/json/";

		final ProgressDialog progressDialog = new ProgressDialog(context);
							 progressDialog.setCanceledOnTouchOutside(false);
							 progressDialog.setMessage("Procurando endereço...");
							 progressDialog.show();

		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(

				Request.Method.GET, 
				url, 
				null,

				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject resp) {
		
						encerraProgressDialog(progressDialog);		

						if(resp.toString().contains("erro")) {
							
							new MeuAlerta("CEP não localizado" , null, context).meuAlertaOk();
						}else {
							
							Endereco endereco = (Endereco) devolveClassePreenchidaComJson(Endereco.class, resp);
						
							EditText et1 = (EditText) linearLayoutPrincipal.findViewWithTag("ceplogradouro");	
							 		 et1.setText( endereco.getLogradouro());
						 		 
							EditText et3 = (EditText) linearLayoutPrincipal.findViewWithTag("cepbairro");	
							 		 et3.setText(endereco.getBairro());
						
							EditText et4 = (EditText) linearLayoutPrincipal.findViewWithTag("cepcidade");	
							 		 et4.setText(endereco.getLocalidade());
							
							EditText et5 = (EditText) linearLayoutPrincipal.findViewWithTag("cepestado");	
							 		 et5.setText(endereco.getUf());
					
							new MeuAlerta("CEP localizado" , null, context).meuAlertaOk();
						}	
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						encerraProgressDialog(progressDialog);
						new MeuAlerta("CEP Não localizado" , null, context).meuAlertaOk();

						//Toast.makeText(context, "CEP não localizado", Toast.LENGTH_LONG).show();
					}
				});

		jsonObjRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());

		queue.add(jsonObjRequest);
	}
	private void encerraProgressDialog(ProgressDialog progressDialog) {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
	private <T> Object devolveClassePreenchidaComJson(Class<T> classe, JSONObject jSONObject){

		Object objectInstance = null;
		
		try {
			objectInstance = classe.getConstructor().newInstance();
	
			for (Field field : classe.getDeclaredFields()) {

				field.setAccessible(true);
						
				field.set(objectInstance, jSONObject.getString(field.getName()));
			}			
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		
		return objectInstance;
	}

}