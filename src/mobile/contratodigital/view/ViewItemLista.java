package mobile.contratodigital.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import mobile.contratodigital.R;
import mobile.contratodigital.model.ItemPeca;
import mobile.contratodigital.util.TelaBuilder;

public class ViewItemLista {

	private Context context;
	private TelaBuilder telaBuilder;

	//protected ImageView imageView;
	protected TextView tvDescMotivo;
	protected TextView tvObservacao;
	protected TextView tvQuantidade;

	public ViewItemLista(Context _context){

		context = _context;
		telaBuilder = new TelaBuilder(_context);
	}
		
	public LinearLayout criaTelaDoItem(ItemPeca itemPeca){
		
		LinearLayout llTela = new LinearLayout(context);	
		llTela.setOrientation(LinearLayout.HORIZONTAL);		
		llTela.setBackground(telaBuilder.cria_GradientDrawable2(context.getResources().getColor(R.color.azul_claro_consigaz)));
		
		LinearLayout llQuadrado = new LinearLayout(context);
		llQuadrado.setOrientation(LinearLayout.VERTICAL);		
		LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0.80f);			
								   lllp.setMargins(10, 10, 10, 10);
		llQuadrado.setLayoutParams(lllp);
		
		if(!itemPeca.getQuantidade().isEmpty()) {
			
								tvQuantidade = telaBuilder.cria_TV_titulo(itemPeca.getQuantidade());
								tvQuantidade.setTextSize(25);
								tvQuantidade.setTextColor(Color.BLUE);
			llQuadrado.addView(tvQuantidade);	
		} 
		else {		
			ImageView imageView = new ImageView(context);		  
								imageView.setAdjustViewBounds(true);
								imageView.setMaxWidth(100);
								imageView.setMaxHeight(100);
			
								//if(!itemPeca.getQuantidade().isEmpty()) {
								
									//viewItemLista.imageView.setImageResource(R.drawable.signout);
								imageView.setImageResource(R.drawable.signout);
									
								//}

			llQuadrado.addView(imageView);
		}
		

		LinearLayout llretangulo = new LinearLayout(context);
		LinearLayout.LayoutParams lllp2 = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0.20f);			
								  	lllp2.setMargins(10, 10, 10, 10);
		llretangulo.setLayoutParams(lllp2);
		llretangulo.setOrientation(LinearLayout.VERTICAL);		
			
		
		llretangulo.addView(telaBuilder.cria_TV_titulo(""+itemPeca.getCodigo()));
		llretangulo.addView(telaBuilder.cria_TV_titulo(itemPeca.getNome()));			
	
		tvDescMotivo = telaBuilder.cria_TV_conteudo13("");
		tvDescMotivo.setVisibility(View.GONE);
		tvDescMotivo.setTextColor(Color.RED); 
		
		
		tvObservacao = telaBuilder.cria_TV_conteudo13("");
		tvObservacao.setVisibility(View.GONE);
			
		llretangulo.addView(tvDescMotivo);			
		llretangulo.addView(tvObservacao);			
		
		llTela.addView(llQuadrado);
		llTela.addView(llretangulo);
				
		return  llTela;		
	}

}
