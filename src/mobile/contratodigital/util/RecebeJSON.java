package mobile.contratodigital.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.view.ActivityLogin;
import sharedlib.contratodigital.model.*;

public class RecebeJSON {

	//ActivityLogin e ActivityDashboard irao passar por aqui
	public boolean recebeDados(Context context, JSONObject response) {
		
		boolean erro = false;

		try {				
			Dao dao = new Dao(context);
			
			Pda pda = new Pda();	
			pda.setListaRepresentante(getListaDaClasseInformada(Representante.class, response.getJSONArray("listaRepresentante")));	
			pda.setListaLayout(getListaDaClasseInformada(Layout.class, response.getJSONArray("listaLayout")));
			pda.setListaItem_layout(getListaDaClasseInformada(Item_layout.class, response.getJSONArray("listaItem_layout")));
			pda.setListaMovimento(getListaDaClasseInformada(Movimento.class, response.getJSONArray("listaMovimento")));
			pda.setListaCad_eqpto(getListaDaClasseInformada(Cad_eqpto.class, response.getJSONArray("listaCad_eqpto")));
			pda.setListaCad_pecas(getListaDaClasseInformada(Cad_pecas.class, response.getJSONArray("listaCad_pecas")));
			pda.setListaCad_precos(getListaDaClasseInformada(Cad_precos.class, response.getJSONArray("listaCad_precos")));
			pda.setListaRetorno(getListaDaClasseInformada(Retorno.class, response.getJSONArray("listaRetorno")));
			pda.setListaCad_canal_venda(getListaDaClasseInformada(Cad_canal_venda.class, response.getJSONArray("listaCad_canal_venda")));

			//se nao entrar aqui eh porque quem esta chamando eh ActivityDashboard, entao NAO vou deletar tudo
			if(context instanceof ActivityLogin) {
				
				//pda.setListaCad_cidades(devolveListaDaClasseInformada(Cad_cidades.class, jSONObject_resposta.getJSONArray("listaCad_cidades")));
				//pda.setListaCad_grupo_empres(devolveListaDaClasseInformada(Cad_grupo_empres.class, jSONObject_resposta.getJSONArray("listaCad_grupo_empres")));
				pda.setListaCad_atividade(getListaDaClasseInformada(Cad_atividade.class, response.getJSONArray("listaCad_atividade")));
				
				dao.deletaTodosDados();
			}
			dao.insereOuAtualizaTabelas(pda); 
		} 
		catch (Exception ex) {
			Log.e("tag", ""+ex);
			erro = true;	
		}
		return erro;
	}

	private <E> List<E> getListaDaClasseInformada(Class<E> classe, JSONArray jSONArray) throws Exception {

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
