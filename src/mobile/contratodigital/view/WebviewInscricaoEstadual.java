package mobile.contratodigital.view;

import java.lang.reflect.Method;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import sharedlib.contratodigital.model.Movimento;
	
	public class WebviewInscricaoEstadual extends Activity {
	private Context context;
	private Movimento mov_informacoesCliente;
	private WebView webView;
	private ZoomButtonsController zoom_controll = null;
	private Dao dao;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = WebviewInscricaoEstadual.this;

				 Bundle bundle = getIntent().getExtras();
		mov_informacoesCliente = (Movimento) bundle.getSerializable("movimento");
		
		dao = new Dao(context);
		
		if(mov_informacoesCliente.getNr_contrato() == null) {
		
		
		mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class, 
											  Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(), 
											  Movimento.COLUMN_INTEGER_NR_VISITA, mov_informacoesCliente.getNr_visita());
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

				    private void disableControls(){
				        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
				            this.getSettings().setBuiltInZoomControls(true);
				            this.getSettings().setDisplayZoomControls(false);
				        } else {
				            getControlls();
				        }
				    }

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
				            zoom_controll.setVisible(false);
				        }
				        return true;
				    }
				}
			}
		@Override
		public void onPageFinished(WebView view, String url) {				
			}
		});
	
		setContentView(webView);
		   WebSettings webSettings = webView.getSettings();
		    webSettings.setJavaScriptEnabled(true);    
		webView.loadUrl("http://www.sintegra.gov.br/new_bv.html");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.capturar, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.Capturar) {

			WebViewUtil webViewUtil = new WebViewUtil();
			webViewUtil.copiaImagemDaTelaAtual(webView, dao, context, mov_informacoesCliente, "ConsultaInscricaoEstadual.jpg");
		}
		return false;
	}
		
   private void disableControls(){
       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
    	  webView.getSettings().setBuiltInZoomControls(true);
    	  webView.getSettings().setDisplayZoomControls(false);
       } else {
           getControlls();
       }
   }

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
           zoom_controll.setVisible(false);
       }
       return true;
   }
		
	}