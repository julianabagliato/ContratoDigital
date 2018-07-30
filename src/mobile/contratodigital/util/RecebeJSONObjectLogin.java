package mobile.contratodigital.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import mobile.contratodigital.dao.Dao;
import sharedlib.contratodigital.model.*;

/**
 * Classe criada para receber o objeto de login via json
 * 
 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
 *         Pereira Santos - Consigaz -2017
 * 
 * @version 1.0
 */
public class RecebeJSONObjectLogin {

	private Context context;

	public RecebeJSONObjectLogin() {
	}
	public RecebeJSONObjectLogin(Context _context) {
		this.context = _context;
	}

	public boolean inserePdaComTodasTabelas(JSONObject jSONObject_resposta) {
		
		boolean erro = false;

		try {
			JSONArray jSONArray1 = jSONObject_resposta.getJSONArray("listaRepresentante");
			JSONArray jSONArray2 = jSONObject_resposta.getJSONArray("listaLayout");
			JSONArray jSONArray3 = jSONObject_resposta.getJSONArray("listaItem_layout");
			JSONArray jSONArray4 = jSONObject_resposta.getJSONArray("listaMovimento");
			//JSONArray jSONArray5 = jSONObject_resposta.getJSONArray("listaCad_cidades");
			JSONArray jSONArray6 = jSONObject_resposta.getJSONArray("listaCad_eqpto");
			//JSONArray jSONArray7 = jSONObject_resposta.getJSONArray("listaCad_grupo_empres");
			JSONArray jSONArray8 = jSONObject_resposta.getJSONArray("listaCad_pecas");
			JSONArray jSONArray9 = jSONObject_resposta.getJSONArray("listaCad_precos");
			JSONArray jSONArray10= jSONObject_resposta.getJSONArray("listaRetorno");
			//JSONArray jSONArray11= jSONObject_resposta.getJSONArray("listaCad_atividade");
			JSONArray jSONArray12= jSONObject_resposta.getJSONArray("listaCad_canal_venda");
					
			Pda pda = new Pda();					
			pda.setListaRepresentante(devolveListaDaClasseInformada(Representante.class, jSONArray1));	
			
			pda.setListaLayout(devolveListaDaClasseInformada(Layout.class, jSONArray2));
			
			pda.setListaItem_layout(devolveListaDaClasseInformada(Item_layout.class, jSONArray3));
			
			pda.setListaMovimento(devolveListaDaClasseInformada(Movimento.class, jSONArray4));
			
			//pda.setListaCad_cidades(devolveListaDaClasseInformada(Cad_cidades.class, jSONArray5));
			
			pda.setListaCad_eqpto(devolveListaDaClasseInformada(Cad_eqpto.class, jSONArray6));
			
			//pda.setListaCad_grupo_empres(devolveListaDaClasseInformada(Cad_grupo_empres.class, jSONArray7));
			
			pda.setListaCad_pecas(devolveListaDaClasseInformada(Cad_pecas.class, jSONArray8));
			
			pda.setListaCad_precos(devolveListaDaClasseInformada(Cad_precos.class, jSONArray9));
			
			pda.setListaRetorno(devolveListaDaClasseInformada(Retorno.class, jSONArray10));
			
			//pda.setListaCad_atividade(devolveListaDaClasseInformada(Cad_atividade.class, jSONArray11));
			
			pda.setListaCad_canal_venda(devolveListaDaClasseInformada(Cad_canal_venda.class, jSONArray12));
			
			Dao dao = new Dao(context);
				dao.deletaTodosDados();
				
				dao.insereTabelas(pda); 
		
				
		} 
		catch (Exception e) {

			erro = true;	
		}
		return erro;
	}

	private <E> List<E> devolveListaDaClasseInformada(Class<E> classe, JSONArray jSONArray) throws Exception {

		List<E> lista = new ArrayList<E>();

		for (int i = 0; i < jSONArray.length(); i++) {

			JSONObject jSONObject = jSONArray.getJSONObject(i);

			Object objectInstance = classe.getConstructor().newInstance();

			for (Field field : classe.getDeclaredFields()) {

				field.setAccessible(true);

				if (!field.getName().contains("COLUMN")) {

					if (field.getType() == int.class) {

						field.setInt(objectInstance, jSONObject.getInt(field.getName()));
					} else {
						field.set(objectInstance, jSONObject.getString(field.getName()));
					}
				}
			}
			lista.add((E) objectInstance);
		}
		return lista;
	}

}
