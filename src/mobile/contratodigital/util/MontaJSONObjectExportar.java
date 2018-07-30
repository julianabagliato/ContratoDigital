package mobile.contratodigital.util;

import java.lang.reflect.Field;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import sharedlib.contratodigital.model.*;

/**
 * Classe criada para montar o json de exportação
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class MontaJSONObjectExportar {

	private Context context;

	public MontaJSONObjectExportar() {
	}

	public MontaJSONObjectExportar(Context _context) {
		this.context = _context;
	}

	public JSONObject montaJSONObject() {

		JSONObject jSONObject_retorno = new JSONObject();

		try {
			Dao dao = new Dao(context);

			int codigoRepresentante = dao.selectDistinct_codRep(Representante.class);

			jSONObject_retorno.put("codigoRepresentante", codigoRepresentante);

			// ####################################################################################
			JSONArray jSONArray_listaComMovimentos = new JSONArray();
			// JSONArray jSONArray_listaComItemLayouts = new JSONArray();
			JSONArray jSONArray_listaComLayouts = new JSONArray();

			JSONArray jSONArray_listaComCad_cidades = new JSONArray();
			JSONArray jSONArray_listaComCad_eqpto = new JSONArray();

			jSONArray_listaComCad_eqpto.put(devolveJsonObjectDeUmaClasse(Cad_eqpto.class));

			jSONArray_listaComCad_cidades.put(devolveJsonObjectDeUmaClasse(Cad_cidades.class));

			for (Layout layout : dao.listaTodaTabela(Layout.class)) {// ,
																		// Layout.COLUMN_INTEGER_IND_TIP_LAYOUT,
																		// TipoView.LAYOUT_FORMULARIO.getValor()

				if (layout.getNr_layout() == NomeLayout.EQUIPAMENTOS_SIMULADOS.getNumero()) {

					jSONArray_listaComLayouts.put(devolveJsonObjectDeUmaClasse(layout));

					// for(Item_layout item_layout :
					// dao.listaTodaTabela(Item_layout.class,
					// Item_layout.COLUMN_INTEGER_NR_LAYOUT,
					// layout.getNr_layout())){
					// jSONArray_listaComItemLayouts.put(devolveJsonObjectDeUmaClasse(item_layout));
					// }
				}
				if (layout.getNr_layout() == NomeLayout.SIMULADOR2.getNumero()) {

					jSONArray_listaComLayouts.put(devolveJsonObjectDeUmaClasse(layout));

					// for(Item_layout item_layout :
					// dao.listaTodaTabela(Item_layout.class,
					// Item_layout.COLUMN_INTEGER_NR_LAYOUT,
					// layout.getNr_layout())){
					// jSONArray_listaComItemLayouts.put(devolveJsonObjectDeUmaClasse(item_layout));
					// }
				}

				// for(Movimento movimento :
				// dao.listaTodaTabela_where_nrSeqMovto_naoEhZERO(Movimento.class,
				// Movimento.COLUMN_INTEGER_NR_LAYOUT, layout.getNr_layout())){
				for (Movimento movimento : dao.listaTodaTabela(Movimento.class, Movimento.COLUMN_INTEGER_NR_LAYOUT,
						layout.getNr_layout())) {

					jSONArray_listaComMovimentos.put(devolveJsonObjectDeUmaClasse(movimento));
				}
			}

			jSONObject_retorno.put("listaComMovimentos", jSONArray_listaComMovimentos);
			// jSONObject_retorno.put("listaComItemLayouts",
			// jSONArray_listaComItemLayouts);
			jSONObject_retorno.put("listaComLayouts", jSONArray_listaComLayouts);
			// ####################################################################################

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jSONObject_retorno;
	}

	private JSONObject devolveJsonObjectDeUmaClasse(Object objeto) {

		Class<?> classe = objeto.getClass();

		JSONObject jSONObject = new JSONObject();

		try {

			for (Field field : classe.getDeclaredFields()) {

				field.setAccessible(true);
				jSONObject.put(field.getName(), field.get(objeto));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jSONObject;
	}
}
