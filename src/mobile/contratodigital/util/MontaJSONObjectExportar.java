package mobile.contratodigital.util;

import java.lang.reflect.Field;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import sharedlib.contratodigital.model.*;

public class MontaJSONObjectExportar {

	private Context context;

	public MontaJSONObjectExportar() {
	}

	public MontaJSONObjectExportar(Context _context) {
		this.context = _context;
	}

	public JSONObject montaJSONObject(int nrVisita) {

		JSONObject jSONObject_retorno = new JSONObject();

		try {
			Dao dao = new Dao(context);

			int codigoRepresentante = dao.selectDistinct_codRep(Representante.class);

			jSONObject_retorno.put("codigoRepresentante", codigoRepresentante);

			// ####################################################################################
			JSONArray jSONArray_listaComMovimentos = new JSONArray();
			JSONArray jSONArray_listaComLayouts = new JSONArray();

			for (Layout layout : dao.listaTodaTabela(Layout.class)) {
			
				if (layout.getNr_layout() == NomeLayout.SIMULADOR_FER.getNumero()) {

					jSONArray_listaComLayouts.put(devolveJsonObjectDeUmaClasse(layout));	
				}
				
				if (layout.getNr_layout() == NomeLayout.SIMULADOR_ANA.getNumero()) {

					jSONArray_listaComLayouts.put(devolveJsonObjectDeUmaClasse(layout));					
				}

				for (Movimento movimento : dao.listaTodaTabela(Movimento.class, 
															   Movimento.COLUMN_INTEGER_NR_LAYOUT, layout.getNr_layout(),
															   Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita)) {

					jSONArray_listaComMovimentos.put(devolveJsonObjectDeUmaClasse(movimento));
				}
			}

			jSONObject_retorno.put("listaComMovimentos", jSONArray_listaComMovimentos);
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
