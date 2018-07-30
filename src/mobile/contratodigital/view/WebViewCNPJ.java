package mobile.contratodigital.view;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.util.MeuAlerta;
import sharedlib.contratodigital.model.Movimento;

public class WebViewCNPJ extends Activity {
	
	private Context context;
	private String srcContrato;
	private Movimento movimento;
	private WebView webView;
	private ZoomButtonsController zoomButtonsController = null;
	   
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = WebViewCNPJ.this;

		Bundle bundle = getIntent().getExtras();

		movimento = (Movimento) bundle.getSerializable("movimento");
	
		if(movimento.getNr_contrato() == null) {
			
			Dao dao = new Dao(context);
			
			movimento = (Movimento) dao.devolveObjeto(Movimento.class, 
													  Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(), 
													  Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita());
		}
		
		webView = new WebView(this); 
		
		disableControls();

		webView.setWebViewClient(new WebViewClient() {

			public void onTouchEvent(){
				class NoZoomControllWebView extends WebView {

				    private ZoomButtonsController zoom_controll = null;

				    public NoZoomControllWebView(Context context) {
				        super(context);
				        disableControls();
				    }

				    public NoZoomControllWebView(Context context, AttributeSet attrs, int defStyle) {
				        super(context, attrs, defStyle);
				        disableControls();
				    }

				    public NoZoomControllWebView(Context context, AttributeSet attrs) {
				        super(context, attrs);
				        disableControls();
				    }

				    /**
				     * Disable the controls
				     */
				    private void disableControls(){
				    	
				        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
				            // Use the API 11+ calls to disable the controls
				            this.getSettings().setBuiltInZoomControls(true);
				            this.getSettings().setDisplayZoomControls(false);
				        } else {
				            // Use the reflection magic to make it work on earlier APIs
				            getControlls();
				        }
				    }

				    /**
				     * This is where the magic happens :D
				     */
				    private void getControlls() {
				    	
				        try {
				            Class<?> webview = Class.forName("android.webkit.WebView");
				            Method method = webview.getMethod("getZoomButtonsController");
				            zoom_controll = (ZoomButtonsController) method.invoke(this, null);
				        } catch (Exception e) {
				            e.printStackTrace();
				        }
				    }

				    @Override
				    public boolean onTouchEvent(MotionEvent ev) {
				        super.onTouchEvent(ev);
				        if (zoom_controll != null){
				            // Hide the controlls AFTER they where made visible by the default implementation.
				            zoom_controll.setVisible(false);
				        }
				        return true;
				    }
				}
			}
			
		@Override
		public void onPageFinished(WebView view, String url) {
		
			if(!movimento.getNr_contrato().trim().equals("")){
				
				srcContrato = Environment.getExternalStorageDirectory()
								+"/ContratoDigital/"+"_"+movimento.getNr_contrato()+"/Consulta_cnpj.jpg";	

			}else{
				srcContrato = Environment.getExternalStorageDirectory()
								+"/ContratoDigital/"+movimento.getInformacao_1()
								                +"_"+movimento.getInformacao_4().replace("/","-")+"/Consulta_cnpj.jpg";	
			}
			 File file = new File(srcContrato);
				
			    if(!file.exists()){
			    	file.getParentFile().mkdirs();  	
			    }
			    
				Picture picture = view.capturePicture();
				Bitmap b = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
				Canvas c = new Canvas(b);

				picture.draw(c);
				FileOutputStream fos = null;
				try {

					fos = new FileOutputStream(srcContrato );
					if (fos != null) {
						b.compress(Bitmap.CompressFormat.JPEG, 100, fos);

						fos.close();
					}
				} catch (Exception e) {

				}
			}
		});
		
   		
	
		setContentView(webView);
		
		WebSettings webSettings = webView.getSettings();
		    		webSettings.setJavaScriptEnabled(true);    
		    		
		webView.loadUrl("http://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/Cnpjreva_Solicitacao.asp");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.capturar, menu);
		
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.Capturar) {

			Capturar(webView, webView.getUrl());
		}
		return false;
	}

	private void Capturar(WebView view, String url) {
		
		Picture picture = view.capturePicture();
		Bitmap bitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);

		picture.draw(canvas);
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(srcContrato);
			
			new MeuAlerta("Consulta Gravada com sucesso!", null, context).meuAlertaOk();
			
			if (fileOutputStream != null) {
				
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

				fileOutputStream.close();
			}
		} 
		catch (Exception ex) {
			Log.e("tag","Erro: "+ex);
		}
	} 
	
   private void disableControls(){
	   
       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
           // Use the API 11+ calls to disable the controls
    	  webView.getSettings().setBuiltInZoomControls(true);
    	  webView.getSettings().setDisplayZoomControls(false);
       } else {
           // Use the reflection magic to make it work on earlier APIs
           getControlls();
       }
   }
 
   private void getControlls() {
       try {
           Class<?> classeWebview = Class.forName("android.webkit.WebView");
           Method method = classeWebview.getMethod("getZoomButtonsController");
           zoomButtonsController = (ZoomButtonsController) method.invoke(this, null);
       } catch (Exception ex) {           
			Log.e("tag","Erro: "+ex);
       }
   }

   @Override
   public boolean onTouchEvent(MotionEvent ev) {
       super.onTouchEvent(ev);
       
       if (zoomButtonsController != null){
           // Hide the controlls AFTER they where made visible by the default implementation.
           zoomButtonsController.setVisible(false);
       }
       return true;
   }
		
	}