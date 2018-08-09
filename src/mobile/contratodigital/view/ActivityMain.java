package mobile.contratodigital.view;

import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import mobile.contratodigital.R;
import mobile.contratodigital.enums.IpRS;
import mobile.contratodigital.enums.VersaoApp;
import mobile.contratodigital.util.DownloadAplicativo;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.ws.VolleySingleton;
import mobile.contratodigital.ws.VolleyTimeout;
import sharedlib.contratodigital.model.Configuracao;

public class ActivityMain extends Activity {

	private static final String RESOURCE_REST_DOWNLOAD_APK = "/Configuracao/DownloadApk/";
	private static final String RESOURCE_REST_VERSAO = "/Configuracao/Versao/";
	private ActionBar actionBar;
	private RequestQueue queue;
	private ProgressDialog progressDialog;
	private AlertDialog alertDialog;

	public static String CONTEUDO_RECEBIDO_DO_PROSPECT;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
	
		//buscarVersao();
		vaiParaLogin();
	}
		
	private void buscarVersao() {

		queue = VolleySingleton.getInstance(this).getRequestQueue();

		String url = IpRS.URL_SIVA_REST + RESOURCE_REST_VERSAO;

		progressDialog = new ProgressDialog(ActivityMain.this);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setMessage("Aguarde, validando versao...");
		progressDialog.show();

		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(

				Request.Method.GET, url, null,

				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject resp) {

						continuar(resp);
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						encerraProgressDialog();
					//	new MeuAlerta( "Não possui dados para exportar", null, context).meuAlertaOk();
						new MeuAlerta("Sem Internet! "+error, null, ActivityMain.this).meuAlertaOk();


						//Toast.makeText(ActivityMain.this, "Sem Internet! "+error, Toast.LENGTH_LONG).show();

						vaiParaLogin();
					}
				});

		jsonObjRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());

		queue.add(jsonObjRequest);
	}

	private void continuar(JSONObject response) {

		try {

			Configuracao configuracaoExterna = new Configuracao();

			if (response.has("versao_app")) {

				configuracaoExterna.setVersao_app(response.getInt("versao_app"));
			}

			if (configuracaoExterna.getVersao_app() > VersaoApp.VERSAO_NUMERO.getNumero()) {

				atualizarVersao();
			} 
			else {
				encerraProgressDialog();

				vaiParaLogin();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();

			encerraProgressDialog();

			vaiParaLogin();
		}
	}

	private void vaiParaLogin() {
		

		//startActivity(new Intent(ActivityMain.this,Webview_Cnpj.class));
		//finish();	
		
			
	//startActivity(new Intent(ActivityMain.this, VisualizadorDeImagem.class));
		
	startActivity(new Intent(ActivityMain.this, ActivityLogin.class));
	finish();		
	}

	private void atualizarVersao() throws Exception {

		LayoutInflater inflater = (LayoutInflater) ActivityMain.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		LinearLayout linear = (LinearLayout) inflater.inflate(R.layout.item_atualizarapp, null);

		//CrudSqliteDAO crudSqliteDAO = new CrudSqliteDAO(MainActivity.this);

		int total = 0; // crudSqliteDAO.countProspects();

		Button button_atualizar = (Button) linear.findViewById(R.id.btnApp_atualizar);
		Button buttonAdiar = (Button) linear.findViewById(R.id.btnApp_adiar);
		TextView textView_desejaAtualizar = (TextView) linear.findViewById(R.id.desejaAtualizar);
		TextView textView_sincronizeSeusDadosParaAtualizar = (TextView) linear.findViewById(R.id.sincronizeSeusDadosParaAtualizar);

		if (total > 0) {

			textView_sincronizeSeusDadosParaAtualizar.setVisibility(View.VISIBLE);
			button_atualizar.setVisibility(View.GONE);
		} 
		else {
			textView_desejaAtualizar.setVisibility(View.VISIBLE);
		}


		button_atualizar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				encerraProgressDialog();
				encerraAlertDialog();

				new DownloadAplicativo(ActivityMain.this, "CadastroCliente", IpRS.URL_SIVA_REST + RESOURCE_REST_DOWNLOAD_APK);
			}
		});
		
		buttonAdiar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				encerraProgressDialog();
				encerraAlertDialog();

				vaiParaLogin();
			}
		});
		
		AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);
		builder.setView(linear);
		alertDialog = builder.show();
		alertDialog.setCanceledOnTouchOutside(false);
	}

	private void encerraProgressDialog() {
		
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	private void encerraAlertDialog() {
		
		if (alertDialog != null) {
			alertDialog.dismiss();
			alertDialog = null;
		}
	}

}
