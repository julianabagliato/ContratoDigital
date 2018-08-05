package mobile.contratodigital.util;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class Animacao {

	public void iniciaAnimacao(Context context, ImageView imageView, int animacaoDesejada) {

		Animation animation = AnimationUtils.loadAnimation(context, animacaoDesejada);
				  animation.reset();

		imageView.clearAnimation();
		imageView.startAnimation(animation);
	}
	
	public void piscaView(View view) {

		Animation animation = new AlphaAnimation(1, 0);
				  animation.setDuration(100);
				  animation.setInterpolator(new LinearInterpolator());
				  animation.setRepeatCount(20);
				  animation.setRepeatMode(Animation.REVERSE);

		view.startAnimation(animation);
	}


}
