package mobile.contratodigital.dao;

import java.lang.reflect.Field;
import android.database.Cursor;
	
	/**
	 * Classe criada para percorrer o cursor nas tabelas e preencher o objeto
	 * solicitado;
	 * 
	 * 
	 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
	 *         Pereira Santos - Consigaz -2017
	 * 
	 * @version 1.0
	 */
public class PreencheObjetoUsandoCursor {

	/**
	 * Cursor criado para devolver os objetos solicitados preenchido 
	 *  
	 * @author Fernando Pereira Santos - Consigaz -2017
	 * 
	 * @param devolveListaComTabelasModel
	 */
	public static Object devolveObjetoPreenchido(Class<?> classe, Cursor cursor) {

		Object objectInstance = null;

		try {
			objectInstance = classe.getConstructor().newInstance();

			for (Field field : classe.getDeclaredFields()) {

				field.setAccessible(true);

				if (!field.getName().contains("COLUMN")) {

					if (field.getType() == int.class || field.getType() == java.lang.String.class) {

						if (field.getType() == int.class) {

							field.setInt(objectInstance, cursor.getInt(cursor.getColumnIndex(field.getName())));
						} else {

							if (null == cursor.getString(cursor.getColumnIndex(field.getName()))) {

								field.set(objectInstance, "equals(null)");
							} else {
								field.set(objectInstance, cursor.getString(cursor.getColumnIndex(field.getName())));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectInstance;
	}

}
