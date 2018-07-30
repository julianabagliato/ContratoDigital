package mobile.contratodigital.view;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
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
/**
 * Classe webview para tratar a captura da Inscrição Estadual
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 
 * 
 * @version 1.0
 */
public class Webview_Inscricao extends Activity {
private Context context;
private String srcContrato;
private Movimento movimento1;
	WebView w;
	   private ZoomButtonsController zoom_controll = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();

		movimento1 = (Movimento) bundle.getSerializable("movimento");
		context = Webview_Inscricao.this;
		
		w = new WebView(this); 
		disableControls();

		w.setWebViewClient(new WebViewClient() {

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
				            Class webview = Class.forName("android.webkit.WebView");
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
			Dao dao = new Dao(context);
			Intent intent = getIntent();
		
			
			Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class, 
						 Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(), 
						 Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());
if(!movimento1.getNr_contrato().trim().equals("")){
	 srcContrato = Environment.getExternalStorageDirectory()+"/ContratoDigital/"+"_"+movimento1.getNr_contrato()+"/Consulta_Inscricao_Estadual.jpg";	

}else{
			 srcContrato = Environment.getExternalStorageDirectory()+"/ContratoDigital/"+movimento1.getInformacao_1()+"_"+movimento1.getInformacao_4().replace("/","-")+"/Consulta_Inscricao_Estadual.jpg";	
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
		
   		
	
		setContentView(w);
		   WebSettings webSettings = w.getSettings();
		    webSettings.setJavaScriptEnabled(true);    
		w.loadUrl("http://www.sintegra.gov.br/new_bv.html");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.capturar, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.Capturar) {

			Capturar(w, w.getUrl());
		}
		return false;
}

	private void Capturar(WebView view, String url) {
		Picture picture = view.capturePicture();
		Bitmap b = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);

		picture.draw(c);
		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(srcContrato);
			new MeuAlerta("Consulta Gravada com sucesso!", null, context).meuAlertaOk();
			if (fos != null) {
				b.compress(Bitmap.CompressFormat.JPEG, 100, fos);

				fos.close();
			}
		} catch (Exception e) {

		}
	} 
	
    /* Disable the controls
    */
   private void disableControls(){
       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
           // Use the API 11+ calls to disable the controls
    	  w.getSettings().setBuiltInZoomControls(true);
    	  w.getSettings().setDisplayZoomControls(false);
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
           Class webview = Class.forName("android.webkit.WebView");
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