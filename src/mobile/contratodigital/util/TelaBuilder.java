package mobile.contratodigital.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import mobile.contratodigital.R;
import mobile.contratodigital.enums.Tag;

/**
 * Classe construtora de components 
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class TelaBuilder {

	private Context context;
	private LayoutParams lp_MATCH_MATCH;
	private LayoutParams lp_WRAP_WRAP;
	private LayoutParams lp_MATCH_WRAP;
	
	public TelaBuilder(Context _context) {
		this.context = _context;
		
		lp_MATCH_MATCH = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp_MATCH_WRAP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp_WRAP_WRAP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	public LinearLayout cria_LL(LayoutParams layoutParams, int cor) {

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);		
		linearLayout.setLayoutParams(layoutParams);
		
		if(cor != 0){
			
			linearLayout.setBackgroundColor(context.getResources().getColor(cor));
		}

		return linearLayout;
	}

	public LinearLayout cria_LL_v_MATCH_MATCH() {

		LinearLayout linearLayout = new LinearLayout(context);
					 linearLayout.setOrientation(LinearLayout.VERTICAL);		
					 linearLayout.setLayoutParams(lp_MATCH_MATCH);

		return linearLayout;
	}
	
	public LinearLayout cria_LL_HOLDER(float peso) {

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, peso);

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(params);

		return linearLayout;
	}
	
	public LinearLayout cria_LL_TVtitulo_ETconteudo(String titulo, LinearLayout.LayoutParams params){
		
		LinearLayout ll_linha = new LinearLayout(context);
					 ll_linha.setLayoutParams(params);
					 
        TextView tv_titulo = cria_TV_assinatura(titulo);

        EditText et_conteudo = new EditText(context);    
        		 et_conteudo.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        		 et_conteudo.setTag(Tag.observacao);
        		 
	                 ll_linha.addView(tv_titulo);
	                 ll_linha.addView(et_conteudo);	

	 return ll_linha;
	}
	
	public LinearLayout cria_LL_TVtitulo_TVconteudo(String titulo, String conteudo){
		
		LinearLayout ll_linha = new LinearLayout(context);
					 
        TextView tv_titulo = cria_TV_assinatura(titulo);
        
        TextView tv_conteudo = new TextView(context); 
        		 tv_conteudo.setText(conteudo);

	                 ll_linha.addView(tv_titulo);
	                 ll_linha.addView(tv_conteudo);	

	 return ll_linha;
	}

	
	public LinearLayout cria_LL_LLTVtitulo_LLTVconteudo(String titulo, String conteudo){


		LinearLayout ll_esquerda = new LinearLayout(context);
					 ll_esquerda.setBackgroundDrawable(cria_GradientDrawable());
					 ll_esquerda.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0.50f));
		
		   						TextView tv_titulo = new TextView(context); 
		   								 tv_titulo.setText(titulo);
					 ll_esquerda.addView(tv_titulo);
	 
		LinearLayout ll_direita = new LinearLayout(context);
					 ll_direita.setBackgroundDrawable(cria_GradientDrawable());
					 ll_direita.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 0.50f));
		
							   TextView tv_conteudo = new TextView(context); 
		 								tv_conteudo.setText(conteudo);
					 ll_direita.addView(tv_conteudo);	

		
		LinearLayout ll_linha = new LinearLayout(context);
		ll_linha.addView(ll_esquerda);   
		ll_linha.addView(ll_direita);   
                 
	 return ll_linha;
	}

	public LinearLayout cria_LL_TVtitulo_TVconteudo1(String titulo, String conteudo){
		
		LinearLayout ll_linha = new LinearLayout(context);
					 
        TextView tv_titulo = cria_TV_assinatura(titulo);
        
        TextView tv_conteudo = new TextView(context); 
        		 tv_conteudo.setText(conteudo);
        		 
                 //if(titulo.contains("fim do contrato")){
        	        	
                	 //tv_conteudo.setTag(Tag.totalPrevisaoConsumoAtehFimDoContrato);
                 //}

	                 ll_linha.addView(tv_titulo);
	                 ll_linha.addView(tv_conteudo);	

	 return ll_linha;
	}
	public LinearLayout cria_LL_comBordaArredondada(){
		
		LinearLayout ll_holder = new LinearLayout(context);
		//ll_holder.setBackgroundDrawable(devolveGradientDrawable());
		ll_holder.setPadding(10, 10, 10, 10);
		ll_holder.setOrientation(LinearLayout.VERTICAL);

		return ll_holder;
	}
	
	public LinearLayout cria_LL_ocupaLinhaInteira(){
		
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.HORIZONTAL);		
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		return ll;
	}

	public GradientDrawable cria_GradientDrawable(){
		
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setStroke(1, Color.BLACK);
		//drawable.setCornerRadius(8);
		//drawable.setColor(Color.BLUE);

		return drawable;
	}
	
	public GradientDrawable cria_GradientDrawable2(int cor){
		
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setStroke(3, Color.BLACK);
		drawable.setCornerRadius(8);
		drawable.setColor(cor);
		
		return drawable;
	}
	
	public ListView cria_LV() {

		ListView listView = new ListView(context);
		listView.setLayoutParams(lp_WRAP_WRAP);
		listView.setBackgroundColor(context.getResources().getColor(R.color.plano_de_fundo_lista));
		listView.setDivider(context.getResources().getDrawable(R.color.divisoria));
		listView.setDividerHeight(10);
		listView.setScrollingCacheEnabled(false);

		return listView;
	}

	public Button cria_BT_paraListaDeVendas(String conteudo) {

		Button button = new Button(context);
		button.setLayoutParams(lp_MATCH_MATCH);
		button.setText(conteudo);
		//button.setGravity(Gravity.END);
		button.setTextSize(30);
		button.setTextColor(context.getResources().getColor(R.color.branco));
		button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_btn_consigaz));

		return button;
	}

	public EditText cria_ET_assinatura(){
		
		EditText et_assRgCpf = new EditText(context); 		  
 		 		 et_assRgCpf.setLayoutParams(lp_MATCH_WRAP); 
		 		 
   	 	return et_assRgCpf;
	}

	public TextView cria_TV_assinatura(String razaoSocial){
		
		TextView tv = new TextView(context); 
   	 			 tv.setText(razaoSocial);
   	 			 tv.setTextSize(16);
   	 			 tv.setTypeface(null, Typeface.BOLD);
				 
   	 	return tv;
	}
	
	public TextView cria_TV_titulo(String titulo){
		
		//TextView tv_titulo = new TextView(context);	
		//String textoEmHTML = "<CENTER><p>This text is and <b>bold</b></p></CENTER>";		
		//tv_titulo.setText(Html.fromHtml(textoEmHTML));
		
		TextView tv = new TextView(context);
				 tv.setText(titulo);
				 tv.setTextSize(16);
				 tv.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
				 tv.setGravity(Gravity.CENTER);
		
		return tv;
	}
	
	public TextView cria_TV_conteudo13(String nome){
		
		 TextView tv = new TextView(context);
			  	  tv.setText(nome);
			  	  tv.setTextSize(13);
			  	  
		return tv;	
	}

	public TextView cria_TV_conteudo30(String nome){
		
		 TextView tv = new TextView(context);
			  	  tv.setText(nome);
			  	  tv.setTextSize(30);
			  	  
		return tv;	
	}
	public TextView cria_TV_conteudo40(String nome){
		
		 TextView tv = new TextView(context);
			  	  tv.setText(nome);
			  	  tv.setTextSize(110);
			  	  
		return tv;	
	}

}
