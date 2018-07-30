package mobile.contratodigital.dao;

import java.util.ArrayList;
import java.util.List;

import sharedlib.contratodigital.model.Cad_atividade;
import sharedlib.contratodigital.model.Cad_canal_venda;
import sharedlib.contratodigital.model.Cad_cidades;
import sharedlib.contratodigital.model.Cad_eqpto;
import sharedlib.contratodigital.model.Cad_grupo_empres;
import sharedlib.contratodigital.model.Cad_pecas;
import sharedlib.contratodigital.model.Cad_precos;
import sharedlib.contratodigital.model.Item_layout;
import sharedlib.contratodigital.model.Layout;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.model.Representante;
import sharedlib.contratodigital.model.Retorno;
	/**
	 * Classe para criar a lista com as tabelas requisitadas pelo json na
	 * aplicação;
	 * 
	 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
	 *         Pereira Santos - Consigaz -2017
	 * 
	 * @version 1.0
	 */
public class ListaComTabelasModel {
	
	/**
	 * Metodo criado para devolver a lista com as tabelas requisitadas
	 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
	 *         Pereira Santos - Consigaz -2017
	 * 
	 * @param devolveListaComTabelasModel
	 */
	public static List<String> devolveListaComTabelasModel() {

		List<String> lista = new ArrayList<String>();
		lista.add(Item_layout.class.getSimpleName());
		lista.add(Layout.class.getSimpleName());
		lista.add(Movimento.class.getSimpleName());
		lista.add(Representante.class.getSimpleName());
		lista.add(Cad_cidades.class.getSimpleName());
		lista.add(Cad_eqpto.class.getSimpleName());
		lista.add(Cad_grupo_empres.class.getSimpleName());
		lista.add(Cad_pecas.class.getSimpleName());
		lista.add(Cad_precos.class.getSimpleName());
		lista.add(Retorno.class.getSimpleName());
		lista.add(Cad_atividade.class.getSimpleName());
		lista.add(Cad_canal_venda.class.getSimpleName());

		return lista;
	}

}
