package mobile.contratodigital.view;

import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.TipoView;
import sharedlib.contratodigital.model.*;

public class FragConteudoFormularioHolder extends Fragment {

	private ScrollView scrollView;
	private LinearLayout linearLayout_obrigatorioDaScrollView;
	private LinearLayout linearLayout_aquiVaoOsFragsFormularios;
	private FragConteudoFormulario fragConteudoFormulario;
	//private Button button_adicionar;
	private Context context;	
	private FragmentManager fragmentManager;
	private Movimento movimento;
	private int nr_layout;
	private int tipoLayout;		
	private Layout layout;
	
	public FragConteudoFormularioHolder() {
	}
	public FragConteudoFormularioHolder(FragmentManager _fragmentManager, Movimento _movimento, int _nr_layout, int _tipoLayout) {
		
		this.fragmentManager = _fragmentManager;
		this.movimento = _movimento;
		this.nr_layout = _nr_layout;
		this.tipoLayout = _tipoLayout;
	}
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
			
		context = getActivity();

		LayoutParams LayoutParams_MATCH_MATCH = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		scrollView = new ScrollView(context);
		scrollView.setLayoutParams(LayoutParams_MATCH_MATCH);

		linearLayout_obrigatorioDaScrollView = new LinearLayout(context);
		linearLayout_obrigatorioDaScrollView.setOrientation(LinearLayout.VERTICAL);

		int nrProgrComNrForm = Integer.valueOf(movimento.getNr_programacao() + "" + nr_layout);
		
		int duplicado_nrProgrComNrForm = Integer.valueOf(nrProgrComNrForm +""+ nrProgrComNrForm);
				
		linearLayout_aquiVaoOsFragsFormularios = new LinearLayout(context);
		linearLayout_aquiVaoOsFragsFormularios.setId(duplicado_nrProgrComNrForm);
		linearLayout_aquiVaoOsFragsFormularios.setOrientation(LinearLayout.VERTICAL);
		 		
		Dao dao = new Dao(context);

		layout = (Layout) dao.devolveObjeto(Layout.class, 
											//Layout_progr_repres.COLUMN_INTEGER_NR_PROGRAMACAO, movimento.getNr_programacao(), 
											Layout.COLUMN_INTEGER_NR_LAYOUT, nr_layout);
		
		
		scrollView.addView(linearLayout_obrigatorioDaScrollView);
						   linearLayout_obrigatorioDaScrollView.addView(linearLayout_aquiVaoOsFragsFormularios);			
						   //linearLayout_obrigatorioDaScrollView.addView(button_adicionar);

		adicionaFragmentBaseadoEmInformacoesDaTabelaMovimento();
		
		return scrollView;
	}
			
	private void adicionaFragmentBaseadoEmInformacoesDaTabelaMovimento(){
		
		Dao dao = new Dao(context);
		
		if(layout.getInd_tip_visualiz() == TipoView.VISUALIZACAO_TABELA.getValor()){
			
			adicionaFragment(1, TipoView.VISUALIZACAO_TABELA.getValor());
		}
		else{
			
			List<Movimento> lista = dao.listaTodaTabela(Movimento.class, 
					   									//Movimento.COLUMN_INTEGER_NR_PROGRAMACAO, movimento.getNr_programacao(),
					   									Movimento.COLUMN_INTEGER_NR_LAYOUT, nr_layout,
					   									Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita());
		
			if (lista.size() == 0) {
	
			adicionaFragment(0, TipoView.VISUALIZACAO_NORMAL.getValor());	
			} 
			else {
				for (Movimento movimento : lista) {
					
					adicionaFragment(movimento.getNr_visita(), TipoView.VISUALIZACAO_NORMAL.getValor());	
				}
			}
		}		
	}

	private void adicionaFragment(int nrVisita, int tipoVisualizacao) {
				
		fragConteudoFormulario = new FragConteudoFormulario(fragmentManager, movimento, nr_layout, nrVisita, tipoLayout, tipoVisualizacao);

		fragmentManager.beginTransaction()
					   .add(linearLayout_aquiVaoOsFragsFormularios.getId(), fragConteudoFormulario, "fragConteudoFormulario"+nrVisita)
					   .commit();
	}

}
