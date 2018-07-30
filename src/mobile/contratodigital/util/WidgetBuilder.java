package mobile.contratodigital.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.AbsListView.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
/**
 * Classe criada para trabalhar o widget de criação usado para movimentar as imagens.
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class WidgetBuilder {

	private Context context;

	public WidgetBuilder(Context _context) {
		this.context = _context;
	}

	public LinearLayout criaLinearLayout(int orientation, LinearLayout.LayoutParams layoutParams, int cor) {

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(layoutParams);
		linearLayout.setOrientation(orientation);

		if (cor != 0) {

			linearLayout.setBackgroundColor(cor);
		}
		return linearLayout;
	}

	public FrameLayout criaFrameLayout(FrameLayout.LayoutParams layoutParams, int cor) {

		FrameLayout frameLayout = new FrameLayout(context);
		frameLayout.setLayoutParams(layoutParams);

		if (cor != 0) {

			frameLayout.setBackgroundColor(cor);
		}
		return frameLayout;
	}

	public ScrollView criaScrollView(LinearLayout.LayoutParams layoutParams) {

		ScrollView scrollView = new ScrollView(context);
		scrollView.setLayoutParams(layoutParams);

		return scrollView;
	}

	public TextView criaTextView(String nome) {

		TextView textView = new TextView(context);
		textView.setText(nome);
		textView.setGravity(Gravity.CENTER);

		return textView;
	}

	public ImageView criaImageView(int drawable, int linearLayout_paleta_width) {

		// estou deixando a imagem quadrada por isso uso width nos dois paramentros
		LayoutParams layoutParams_imageView = new LayoutParams(linearLayout_paleta_width, linearLayout_paleta_width);

		ImageView imageView = new ImageView(context);
		imageView.setLayoutParams(layoutParams_imageView);
		//imageView.setOnLongClickListener(new Meu_OnLongClickListener());
		imageView.setTag(drawable);	
		//imageView.setImageDrawable(context.getResources().getDrawable(drawable));
		imageView.setImageBitmap(Diminui_MB_imagens.decodeSampledBitmapFromResource(context.getResources(), drawable, 100, 100));

		return imageView;
	}


	

}
