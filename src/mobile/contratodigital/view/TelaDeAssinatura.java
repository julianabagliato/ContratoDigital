package mobile.contratodigital.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import mobile.contratodigital.R;
/**
 * Classe para tratar a tela de assinaturas
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class TelaDeAssinatura {

	private Context context; 	
	private AlertDialog alertDialog;

	public TelaDeAssinatura(Context _context){
		this.context = _context;
	}
	
	public void mostraTelaDeAssinatura(final ImageView iv_recebeAssinatura, final int width_iv_recebeAss, final int height_iv_recebeAss){
		
		LinearLayout ll_telaDeAssinatura = new LinearLayout(context);
					 ll_telaDeAssinatura.setOrientation(LinearLayout.VERTICAL);
					 ll_telaDeAssinatura.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		LinearLayout ll_botoesHolder = new LinearLayout(context);
					 ll_botoesHolder.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		final LinearLayout ll_lousaHolder = new LinearLayout(context);			
						   ll_lousaHolder.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 500));
						   ll_lousaHolder.setBackgroundColor(Color.WHITE);
		
		final ViewLousa lousaView = new ViewLousa(context, null, ll_lousaHolder);

		int padding = 45;
		int margins = 17;
		
		final Button b_concluido = new Button(context);
		b_concluido.setText("Concluído");
		b_concluido.setPadding(padding, 0, padding, 0);
		b_concluido.setTextSize(24);
		b_concluido.setTextColor(Color.WHITE);
		b_concluido.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_btn_consigaz));
		b_concluido.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				ll_lousaHolder.setDrawingCacheEnabled(true);
				
				Bitmap bitmap = lousaView.retornaImagemDesenhada(ll_lousaHolder);
				
				iv_recebeAssinatura.setImageBitmap(bitmap);
				iv_recebeAssinatura.setLayoutParams(new LinearLayout.LayoutParams(width_iv_recebeAss, height_iv_recebeAss));		
				
				encerraAlertDialog();
			}
		});
		
		Button b_cancelar = new Button(context);
		b_cancelar.setText("Cancelar");
		b_cancelar.setPadding(padding, 0, padding, 0);
		b_cancelar.setTextSize(24);
		b_cancelar.setTextColor(Color.WHITE);
		b_cancelar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_btn_consigaz));
		b_cancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		
				encerraAlertDialog();
			}
		});

		Button b_limpar = new Button(context);
			   b_limpar.setText("Limpar");
		LinearLayout.LayoutParams rel_button1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								  rel_button1.setMargins(margins, 0, margins, 0);
		b_limpar.setLayoutParams(rel_button1);
		b_limpar.setPadding(padding, 0, padding, 0);
		b_limpar.setTextSize(24);
		b_limpar.setTextColor(Color.WHITE);
		b_limpar.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_btn_consigaz));
		b_limpar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				lousaView.clear();
			}
		});

		ll_botoesHolder.addView(b_cancelar);
		ll_botoesHolder.addView(b_limpar);
		ll_botoesHolder.addView(b_concluido);

		ll_lousaHolder.addView(lousaView);

		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	
									    ll_telaDeAssinatura.addView(ll_botoesHolder);
									    ll_telaDeAssinatura.addView(ll_lousaHolder);
						builder.setView(ll_telaDeAssinatura);
		alertDialog = builder.show();
		alertDialog.setCanceledOnTouchOutside(false);
	}
	
	private void encerraAlertDialog() {
		if (alertDialog != null) {
			alertDialog.dismiss();
			alertDialog = null;
		}
	}

}
