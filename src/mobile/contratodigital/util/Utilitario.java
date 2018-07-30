package mobile.contratodigital.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import mobile.contratodigital.R;
/**
 * Classe criada para tratar quantidade de imagens e posicionamentos classe temporariamente inativo
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 
 * 
 * @version 1.0
 */
public class Utilitario {

	public void editaViewQEstaDentroDeUmFrameLayout(Context context, FrameLayout frameLayout, ArrayList<Integer> arrayList_itens) {

		if (frameLayout.getChildCount() > 0) {

			arrayList_itens.clear();

			mudaDeImagensParaNumeros(context, frameLayout, arrayList_itens);

			ordenaLista(arrayList_itens);
		
			mostraAlertDialog(context, frameLayout, arrayList_itens, "Editar");
		}
	}
	
	private void mudaDeImagensParaNumeros(Context context, FrameLayout frameLayout, ArrayList<Integer> arrayList_itens){
		
		for (int x = 0; x < frameLayout.getChildCount(); x++) {

			ImageView imageView = (ImageView) frameLayout.getChildAt(x);

			int numeroSalvoNoOnDrag = (Integer) imageView.getTag(1);
			
			arrayList_itens.add(numeroSalvoNoOnDrag);
			
		
		}
	}

	private void ordenaLista(ArrayList<Integer> arrayList_itens){
		
		Collections.sort(arrayList_itens, new Comparator<Integer>() {
			@Override
			public int compare(Integer intA, Integer intB) {

				return intA.compareTo(intB);
			}
		});
	}

	private void mostraAlertDialog(final Context context, final FrameLayout frameLayout, final ArrayList<Integer> arrayList_itens, final String titulo){
		
		ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.item_menu_geral, arrayList_itens);

		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setTitle(titulo + " imagem nº:");
		builder1.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int posicaoEscolhida) {

				arrayList_itens.clear();

				mudaDeNumerosParaImagens(context, frameLayout);
		
				jogaImagemEscolhidaParaFrente(context, frameLayout, posicaoEscolhida, titulo, arrayList_itens);
				
				dialogInterface.dismiss();
			}
		});
		builder1.show();
	}
		
	private void jogaImagemEscolhidaParaFrente(Context context, FrameLayout frameLayout, int posicaoEscolhida, String titulo, ArrayList<Integer> arrayList_itens){

		for (int x = 0; x < frameLayout.getChildCount(); x++) {

			ImageView imageView = (ImageView) frameLayout.getChildAt(x);
			
			int numeroSalvoNoOnDrag = (Integer) imageView.getTag(1);
	
			if (numeroSalvoNoOnDrag == posicaoEscolhida) {

			}		  		  
		}
	}
	
	private void renomeiaOsNumerosDasTags(FrameLayout frameLayout, ArrayList<Integer> arrayList_itens){
		
		for (int x = 0; x < frameLayout.getChildCount(); x++) {

			ImageView imageView = (ImageView) frameLayout.getChildAt(x);
					  imageView.setTag(1, x);
		}
	}

	private void mudaDeNumerosParaImagens(Context context, FrameLayout frameLayout){
		
		for (int x = 0; x < frameLayout.getChildCount(); x++) {

			ImageView imageView = (ImageView) frameLayout.getChildAt(x);
			  		  //imageView.setImageDrawable(context.getResources().getDrawable(Integer.parseInt(imageView.getTag().toString())));		  
			  		  imageView.setImageBitmap(Diminui_MB_imagens.decodeSampledBitmapFromResource(context.getResources(), Integer.parseInt(imageView.getTag().toString()), 100, 100));	  
		}
	}

	public void excluiViewSelecionada(Context context, final FrameLayout frameLayout, ArrayList<Integer> arrayList_itens) {
		
		if (frameLayout.getChildCount() > 0) {

			arrayList_itens.clear();

			mudaDeImagensParaNumeros(context, frameLayout, arrayList_itens);

			ordenaLista(arrayList_itens);
		
			mostraAlertDialog(context, frameLayout, arrayList_itens, "Deletar");
		}
	}

}
