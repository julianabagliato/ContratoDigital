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
/**
 * Classe para tratar a tela a lista de itens
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class ViewItemLista {

	private Context context;
	private TelaBuilder meusWidgetsBuilder;

	protected ImageView imageView;
	protected TextView tvDescMotivo;
	protected TextView tvObservacao;

	public ViewItemLista(Context _context){

		context = _context;
		meusWidgetsBuilder = new TelaBuilder(_context);
	}
		
	public LinearLayout criaTelaDoItem(ItemPeca itemPeca){
		
		LinearLayout llTela = new LinearLayout(context);	
		llTela.setOrientation(LinearLayout.HORIZONTAL);		
		llTela.setBackground(meusWidgetsBuilder.cria_GradientDrawable2(context.getResources().getColor(R.color.azul_claro_consigaz)));
		
		LinearLayout llQuadrado = new LinearLayout(context);
		llQuadrado.setOrientation(LinearLayout.VERTICAL);		
		LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0.80f);			
								   lllp.setMargins(10, 10, 10, 10);
		llQuadrado.setLayoutParams(lllp);
		
						   imageView = new ImageView(context);		  
						   imageView.setAdjustViewBounds(true);
		llQuadrado.addView(imageView);

		LinearLayout llretangulo = new LinearLayout(context);
		LinearLayout.LayoutParams lllp2 = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0.20f);			
								  	lllp2.setMargins(10, 10, 10, 10);
		llretangulo.setLayoutParams(lllp2);
		llretangulo.setOrientation(LinearLayout.VERTICAL);		
			
		
		llretangulo.addView(meusWidgetsBuilder.cria_TV_titulo(""+itemPeca.getCodigo()));
		llretangulo.addView(meusWidgetsBuilder.cria_TV_titulo(itemPeca.getNome()));			
	
		tvDescMotivo = meusWidgetsBuilder.cria_TV_conteudo13("");
		tvDescMotivo.setVisibility(View.GONE);
		tvDescMotivo.setTextColor(Color.RED); 
		
		
		tvObservacao = meusWidgetsBuilder.cria_TV_conteudo13("");
		tvObservacao.setVisibility(View.GONE);
			
		llretangulo.addView(tvDescMotivo);			
		llretangulo.addView(tvObservacao);			
		
		llTela.addView(llQuadrado);
		llTela.addView(llretangulo);
				
		return  llTela;		
	}

}
