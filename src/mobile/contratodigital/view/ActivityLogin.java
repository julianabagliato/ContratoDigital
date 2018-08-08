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
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.IpRS;
import mobile.contratodigital.util.DetectaConexao;
import mobile.contratodigital.util.Mascara;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.RecebeJSON;
import mobile.contratodigital.ws.ExportarDadosWS;
import mobile.contratodigital.ws.VolleySingleton;
import mobile.contratodigital.ws.VolleyTimeout;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.model.Representante;
import sharedlib.contratodigital.util.Generico;

public class ActivityLogin extends Activity {

	private static final String RESOURCE_REST_AUTENTICAR = "/Autenticacao/Login/";
	private Context context;
	private ActionBar actionBar;
	private RequestQueue requestQueue;
	private ProgressDialog progressDialog;
	private AlertDialog alertDialog;
	private int codigoRepresentante;
	private int idPda_informado;
	private static String URLescolhida = IpRS.URL_SIVA_REST;
	private Menu menu;
	public static final int REQUISICAO_PERMISSAO_TIRAR_FOTO = 111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = ActivityLogin.this;
		
		requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		actionBar.setTitle(R.string.app_name);

		
		LinearLayout llTela = new LinearLayout(context);
		LayoutParams lp_MATCH = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);			
							   lp_MATCH.setMargins(24, 480, 0, 24);		
		llTela.setLayoutParams(lp_MATCH);
		llTela.setOrientation(LinearLayout.VERTICAL);
		llTela.setBackground(context.getResources().getDrawable(R.drawable.fundogaz12));
		//llTela.setPadding(left, top, right, bottom);

		TextView tvCodigoRepresentante = new TextView(context);
		LayoutParams lp_MATCH_WRAP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);			
											  lp_MATCH_WRAP.setMargins(24, 480, 0, 24);		
		tvCodigoRepresentante.setLayoutParams(lp_MATCH_WRAP);
		tvCodigoRepresentante.setText("Código de Representante:");
		tvCodigoRepresentante.setTextColor(Color.WHITE);
		tvCodigoRepresentante.setTextSize(24);
		//android:textStyle="bold"/>
		
		
        final EditText etCodigoRepresentante = new EditText(context);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);			
											  lp.setMargins(0, 0, 0, 24);		
		etCodigoRepresentante.setLayoutParams(lp);
        etCodigoRepresentante.setInputType(InputType.TYPE_CLASS_NUMBER);
        etCodigoRepresentante.addTextChangedListener(Mascara.insert("######", etCodigoRepresentante));
        etCodigoRepresentante.setBackground(context.getResources().getDrawable(R.drawable.style_edit_text_consigaz));

        
		Button buttonEntrar = new Button(context);
		buttonEntrar.setBackground(context.getResources().getDrawable(R.drawable.style_btn_consigaz));
		buttonEntrar.setText("Entrar");
		buttonEntrar.setTextSize(30);	
		buttonEntrar.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
							
			acaoAposCliqueNoBotaoEntrar(etCodigoRepresentante);
			}
		});

		llTela.addView(tvCodigoRepresentante);
		llTela.addView(etCodigoRepresentante);	
		llTela.addView(buttonEntrar);
					
		setContentView(llTela);		
	}
	
	private void acaoAposCliqueNoBotaoEntrar(EditText etCodigoRepresentante) {
		
		if (!etCodigoRepresentante.getText().toString().equals("")) {

			idPda_informado = Integer.parseInt(etCodigoRepresentante.getText().toString());

			Dao crudSqliteDAO = new Dao(context);

			codigoRepresentante = crudSqliteDAO.selectDistinct_codRep(Representante.class);

			if (codigoRepresentante == 0) {

				buscarNoWebService(idPda_informado, etCodigoRepresentante);
			} else {
				if (codigoRepresentante == idPda_informado) {

					encerraAlertDialog();

					abrirSistema(etCodigoRepresentante);
				} else {
					etCodigoRepresentante.setText("");
					new MeuAlerta("Código incorreto!", null, context).meuAlertaOk();
				}
			}
		} else {
			new MeuAlerta("Favor informar o código de representante", null, context).meuAlertaOk();
		}

	}

	private void buscarNoWebService(final int cod_rep, final EditText etCodigoRepresentante) {

		try {
			JSONObject jSONObject_params = new JSONObject();
			jSONObject_params.put("cod_rep", cod_rep);

			String url = URLescolhida + RESOURCE_REST_AUTENTICAR;

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
									
									if(deuErro) {

										encerraProgressDialog();

										new MeuAlerta("Erro no serviço, contate a equipe de TI", null, context).meuAlertaOk();
									}else {

										encerraProgressDialog();
										
										abrirSistema(etCodigoRepresentante);
									}
								} else if (resposta.getInt("achou_cod_rep") == Generico.NAO_ENCONTROU_REPRESENTANTE
										.getValor()) {

									etCodigoRepresentante.setText("");

									encerraProgressDialog();

									new MeuAlerta("Não encontrou o representante informado", null, context).meuAlertaOk();

									
								} else if (resposta.getInt("achou_cod_rep") == Generico.OCORREU_ERRO.getValor()) {

									encerraProgressDialog();
									new MeuAlerta("Caiu na @ Exceção @ do webservice", null, context).meuAlertaOk();

									
									
								} else {
									encerraProgressDialog();
									new MeuAlerta( "Erro desconhecido :(", null, context).meuAlertaOk();

									
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {

							encerraProgressDialog();

							new MeuAlerta("Favor tentar acessar com outro link \n" + error, null, context).meuAlertaOk();
							
						}
					});

			jsonObjRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());

			requestQueue.add(jsonObjRequest);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menuinterno) {
		menu = menuinterno;
		
		menu.add(0,0,0, "Oi");
		menu.add(0,1,0,"Vogel");
		menu.add(0,2,0,"WCS");
		menu.add(0,3,0,"Local");
		menu.add(0,4,0, "Trocar Usuário");
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
		
		case 2:
			 URLescolhida = IpRS.acaoLinWCS(menu);
			return true;
			
		case 3:
			 URLescolhida = IpRS.acaoLinkLocal(menu);
		return true;
		case 4: 
			trocarUsuario();
		
		return true;
		}
	//	if (item.getItemId() == R.id.trocar_usuario) {

		//	trocarUsuario();
		//}
		return super.onOptionsItemSelected(item);
	}
	
	private void trocarUsuario() {

		DetectaConexao connectionDetector = new DetectaConexao(context);

		final Dao dao = new Dao(context);

		if (connectionDetector.estaConectadoNaInternet()) {

			// int qtdDeMovimento =
			// dao.selectCount_where_nrSeqMovto_naoEhZERO(Movimento.class);
			int qtdDeMovimento = dao.selectCount(Movimento.class);

			if (qtdDeMovimento > 0) {

				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				LinearLayout linear = (LinearLayout) inflater.inflate(R.layout.item_trocarusuario, null);

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(linear);
				alertDialog = builder.show();
				alertDialog.setCanceledOnTouchOutside(false);

				Button button_OK = (Button) linear.findViewById(R.id.btn_item_sincro_trocaUsuario_sair);
				button_OK.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						ExportarDadosWS botaoExportar = new ExportarDadosWS(context);
						botaoExportar.exportar();

						alertDialog.dismiss();
					}
				});
			} else {
				progressDialog = new ProgressDialog(context);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setMessage("Aguarde");
				progressDialog.show();

				new android.os.Handler().postDelayed(new Runnable() {
					@Override
					public void run() {

						//dao.deletaTodosDados();
						new MeuAlerta( "Dados removidos!", null, context).meuAlertaOk();

						//Toast.makeText(context, "Dados removidos!", Toast.LENGTH_LONG).show();

						encerraProgressDialog();
					}
				}, 1000);
			}
		} else {
			new MeuAlerta( "Sem conexao com a internet", null, context).meuAlertaOk();

			//final Toast toast = Toast.makeText(ActivityLogin.this, "Sem conexao com a internet", Toast.LENGTH_LONG);

			//toast.show();

			new CountDownTimer(4000, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					new MeuAlerta( "Sem conexao com a internet", null, context).meuAlertaOk();

				//	toast.show();
				}

				@Override
				public void onFinish() {
					new MeuAlerta( "Sem conexao com a internet", null, context).meuAlertaOk();

					//toast.show();
				}
			}.start();
		}
	}

	public void fecharApp() {

		startActivity(new Intent(ActivityLogin.this, ActivityMain.class));

		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();

		encerraProgressDialog();
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

	private void abrirSistema(EditText etCodigoRepresentante) {
		
		ActivityDashboard.setRepres(etCodigoRepresentante.getText().toString());

		startActivity(new Intent(ActivityLogin.this, ActivityDashboard.class));

		finish();
	}
}