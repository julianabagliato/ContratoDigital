package mobile.contratodigital.view;

import org.json.JSONException;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.IpRS;
import mobile.contratodigital.enums.VersaoApp;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.RecebeJSON;
import mobile.contratodigital.util.RecebeJSONObjectimportar;
import mobile.contratodigital.ws.VolleySingleton;
import mobile.contratodigital.ws.VolleyTimeout;
import sharedlib.contratodigital.model.Layout;
import sharedlib.contratodigital.model.Representante;
import sharedlib.contratodigital.util.Generico;

public class ActivityDashboard extends Activity {

	private Context context;
	private ActionBar actionBar;
	//private static int SPLASH_TIME_OUT = 2000;
	//private TableLayout tableLayout_dashboard;	
	private Representante representante;
	//private static final int MENU_ITEM_ITEM1 = 1;
	private LayoutParams layoutParams_MATCH_WRAP;
	private LayoutParams layoutParams_WRAP_WRAP;
	private Dao dao;	
	//private boolean temInformacao = false;
	public static  String repres;
	private static String URLescolhida = IpRS.URL_SIVA_REST;
	private ProgressDialog progressDialog;
	private AlertDialog alertDialog;
	private 	RequestQueue requestQueue ;
	private Menu menu;
	

	private static final String RESOURCE_REST_AUTENTICAR = "/Autenticacao/Login/";


	public String getRepres() {
		return repres;
	}

	public static void setRepres(String represt) {
		repres = represt;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		context = ActivityDashboard.this;
				
		layoutParams_MATCH_WRAP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams_WRAP_WRAP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		 requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

		dao = new Dao(context);

		representante = (Representante) dao.devolveObjeto(Representante.class);
	
	
	
		if(representante != null){
		
		Layout layout = (Layout) dao.devolveObjeto(Layout.class, Layout.COLUMN_INTEGER_NR_LAYOUT, 20);
			
			if(layout != null){

				//temInformacao = true;
			
				actionBar = getActionBar();
				actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
				actionBar.setTitle(layout.getDescricao());			
			}
		}
		
		TextView versao = (TextView) findViewById(R.id.textView_versao);
				 versao.setText("Versão: "+ VersaoApp.VERSAO_NUMERO.getNumero() );
		
		//tableLayout_dashboard = (TableLayout) findViewById(R.id.tableLayout_dashboard);				
	}

	public void selecionarOpcao(View view) {
		
		switch (view.getId()) {
		
		case R.id.textView_visitas: 		
			//startActivity(new Intent(this, ActivityArrayRetorno.class));

			startActivity(new Intent(this, ActivityListaClientes.class));
			finish();	
			break;
			
		case R.id.textView_comunicar:
			startActivity(new Intent(this, ActivityListaClientesExportacao.class));
			finish();
			break;			
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration configuration) {
	    super.onConfigurationChanged(configuration);

	    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	 
	       	//tableLayout_dashboard.setBackground(getResources().getDrawable(R.drawable.consigaz_bg_g_blue));  	        
	    } 
	    else if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT){

	    	//tableLayout_dashboard.setBackground(getResources().getDrawable(R.drawable.consigaz_bg_g_roxo));
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menuinterno) {
		menu = menuinterno;
		
		menu.add(0,0,0, "Oi");
		menu.add(0,1,0,"Vogel");
		menu.add(0,2,0,"WCS");
		menu.add(0,3,0,"Local");

		menu.add(0,4,0, "Detalhar");
		menu.add(0,5,0,"ImportarDados");
		menu.getItem(0).setEnabled(false);
		
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){

		case 0: URLescolhida = IpRS.acaoLinkOi(menu);
		return true;
		
		case 1: URLescolhida = IpRS.acaoLinkvogel(menu);
		return true;
		
		case 2: URLescolhida = IpRS.acaoLinWCS(menu);
		return true;
		
		case 3: URLescolhida = IpRS.acaoLinkLocal(menu);
		return true;
		
		case 4: mostraDetalhes();
		return true;
		
		case 5: criarFluxo();
		return true;
		}
		return super.onOptionsItemSelected(item);

	}
	
	private void criarFluxo() {
		
		new MeuAlerta("Este fluxo ainda não foi definido", null, context).meuAlertaOk();

		//talvez usar:
		//buscarNoWebService2();
	}
	
	public void mostraDetalhes(){
		
		LinearLayout linearLayout_tela = new LinearLayout(context);
					 linearLayout_tela.setOrientation(LinearLayout.VERTICAL);					
					 linearLayout_tela.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
							 	
		criaEpopulaLinhaIndividualmente(linearLayout_tela, "Código Representante", String.valueOf(representante.getCod_rep()));
			
		criaEpopulaLinhaIndividualmente(linearLayout_tela, "Nome", String.valueOf(representante.getNome()));
		
		criaEpopulaLinhaIndividualmente(linearLayout_tela, "Base", String.valueOf(representante.getCod_estab()));
		
		criaEpopulaLinhaIndividualmente(linearLayout_tela, "Empresa", String.valueOf(representante.getEmpresa()));
		
				
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
						    builder.setView(linearLayout_tela);
						    builder.show();			
	}
	
	private void criaEpopulaLinhaIndividualmente(LinearLayout linearLayout_tela, String titulo, String conteudo){
		
		LinearLayout linearLayout_linha1_HORIZONTAL = criaLinearLayout_HORIZONTAL();
		
		TextView textView_titulo1 = new TextView(context);
		textView_titulo1.setText(Html.fromHtml("<b>" + titulo + ": " + "</b>"));
		textView_titulo1.setTextSize(21);
		textView_titulo1.setTextColor(getResources().getColor(R.color.White));
		textView_titulo1.setLayoutParams(layoutParams_WRAP_WRAP);
		
		linearLayout_linha1_HORIZONTAL.addView(textView_titulo1);
		
		TextView textView_conteudo1 = new TextView(context);
		textView_conteudo1.setTextSize(18);
		textView_conteudo1.setTextColor(getResources().getColor(R.color.Black));
		textView_conteudo1.setLayoutParams(layoutParams_WRAP_WRAP);
		textView_conteudo1.setText(conteudo);
		
		linearLayout_linha1_HORIZONTAL.addView(textView_conteudo1);
		
		linearLayout_tela.addView(linearLayout_linha1_HORIZONTAL);
	}
	
	private LinearLayout criaLinearLayout_HORIZONTAL() {

		LinearLayout linearLayoutlinha_HORIZONTAL = new LinearLayout(context);
					 linearLayoutlinha_HORIZONTAL.setOrientation(LinearLayout.HORIZONTAL);
					 linearLayoutlinha_HORIZONTAL.setLayoutParams(layoutParams_MATCH_WRAP);

		return linearLayoutlinha_HORIZONTAL;
	}
	
	@Override
	public void onBackPressed() {

		alert();
	}

	public void alert() {
	
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
							builder.setTitle("Sair")
								   .setIcon(R.drawable.signout)
								   .setMessage("Deseja sair do aplicativo?")
								   .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
									   @Override
									public void onClick(DialogInterface dialog, int id) {

										   final ProgressDialog progressDialog = new ProgressDialog(context);
													 			progressDialog.setMessage("Saindo...");
													 			progressDialog.show();

											new android.os.Handler().postDelayed(new Runnable() {
												@Override
												public void run() {

												progressDialog.dismiss();

												finishAffinity();

												}
											}, Generico.SPLASH_TIME_OUT.getValor());
									   }
								   })
								   .setNegativeButton("Não", new DialogInterface.OnClickListener() {
									   @Override
									public void onClick(DialogInterface dialog, int id) {
									   }
								   });
		
							builder.show();
	}
	
	private void buscarNoWebService2() {

		Dao crudSqliteDAO = new Dao(context);

		int codigoRepresentante = crudSqliteDAO.selectDistinct_codRep(Representante.class);

		try {
			JSONObject jSONObject_params = new JSONObject();
			jSONObject_params.put("cod_rep", codigoRepresentante);

			String url =URLescolhida + RESOURCE_REST_AUTENTICAR;

			progressDialog = new ProgressDialog(context);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setMessage("Autenticando PDA...");
			progressDialog.show();

			JsonObjectRequest jsonObjRequest = new JsonObjectRequest(

					Request.Method.POST, url, jSONObject_params,

					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject resposta) {

							try {
								if (resposta.getInt("achou_cod_rep") == Generico.ENCONTROU_REPRESENTANTE.getValor()) {

									boolean deuErro = new RecebeJSON().recebeDados(context, resposta);
									
									if (!deuErro) {

										encerraProgressDialog();
									}
								} else if (resposta.getInt("achou_cod_rep") == Generico.NAO_ENCONTROU_REPRESENTANTE
										.getValor()) {

									encerraProgressDialog();

									Toast.makeText(context, "Não encontrou o representante informado",
											Toast.LENGTH_LONG).show();
								} else if (resposta.getInt("achou_cod_rep") == Generico.OCORREU_ERRO.getValor()) {

									encerraProgressDialog();

									Toast.makeText(context, "Caiu na @ Exceção @ do webservice", Toast.LENGTH_LONG)
											.show();
								} else {
									encerraProgressDialog();

									Toast.makeText(context, "Erro desconhecido :(", Toast.LENGTH_LONG).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {

							encerraProgressDialog();

							Toast.makeText(context, "VolleyError" + error, Toast.LENGTH_SHORT).show();
						}
					});

			jsonObjRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());

			requestQueue.add(jsonObjRequest);
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
