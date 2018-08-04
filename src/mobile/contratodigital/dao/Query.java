package mobile.contratodigital.dao;

import java.lang.reflect.Field;

	/**
	 * Classe criada para criar a estrutura de query;
	 * 
	 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
	 *         Pereira Santos - Consigaz -2017
	 * 
	 * @version 1.0
	 */
public class Query {

	public static final String CAMPO_PREFIXO = "COLUMN";
	public static final String COLUMN_ID = "id integer primary key autoincrement";
	public static final String COLUMN_KEYY = "keyy integer primary key autoincrement";

	/**
	 * Metodo criado para criar  query de criação de tabela de acordo com os valores da classe
	 * 
	 * @author Fernando Pereira Santos - Consigaz -2017
	 * 
	 * @param criaCreateTableComKeyy
	 */
	public static String criaCreateTableComKeyy(Class<?> classe) {

		String query = "create table " + classe.getSimpleName().toLowerCase() + " (";

		String queryDoMeio = COLUMN_KEYY;

		for (Field field : classe.getDeclaredFields()) {

			field.setAccessible(true);

			if (field.getName().contains(CAMPO_PREFIXO)) {

				String tipo = "";

				if (field.getName().contains("INTEGER")) {

					tipo = "INTEGER";
				} else {
					tipo = "TEXT";
				}

				try {
					queryDoMeio += "," + field.get(null).toString() + " " + tipo;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		query += queryDoMeio;
		query += ")";

		return query;
	}

	public static String criaCondicaoWhereComParametrosString(Object... parametros) {

		StringBuilder sb = new StringBuilder();

		sb.append(" where ");

		for (int i = 0; i < parametros.length; i++) {

			String parImpar = "";

			if (i % 2 == 0) {

				parImpar = "par";

				sb.append(parametros[i].toString() + "='");
			} else {
				sb.append(parametros[i].toString() + "");
			}

			if (parImpar != "par") {

				sb.append("' and ");
			}
		}
		sb.delete(sb.length() - 5, sb.length());

		return sb.toString();
	}

	/**
	 * Metodo criado para criar query de criar tabelas com base na classe.
	 * 
	 * @author Fernando Pereira Santos - Consigaz -2017
	 * 
	 * @param criaCreateTable_final
	 */
	public static String criaCreateTable_final(Class<?> classe) {

		String query = "create table " + classe.getSimpleName().toLowerCase() + " (";

		String queryDoMeio = COLUMN_ID;

		for (Field field : classe.getDeclaredFields()) {

			field.setAccessible(true);

			if (field.getName().contains(CAMPO_PREFIXO)) {

				String tipo = "";

				if (field.getName().contains("INTEGER")) {

					tipo = "INTEGER";
				} else {
					tipo = "TEXT";
				}

				try {
					queryDoMeio += "," + field.get(null).toString() + " " + tipo;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		query += queryDoMeio;
		query += ")";

		return query;
	}

	/**
	 * Metodo criado para criar query de dropar tabelas com base na classe.
	 * 
	 * @author Fernando Pereira Santos - Consigaz -2017
	 * 
	 * @param criaCreateTable_final
	 */
	public static String criaDropTable_final(Class<?> classe) {

		return "DROP TABLE IF EXISTS " + classe.getSimpleName().toLowerCase();
	}

	/*
	public static String criaCondicaoWhere_final(Object... parametros) {

		StringBuilder sb = new StringBuilder();
		if (parametros.length > 0) {
			sb.append(" where ");
			for (int i = 0; i < parametros.length; i++) {
				String parImpar = "";
				if (i % 2 == 0) {
					parImpar = "par";
					sb.append(parametros[i].toString() + "=");
				} else {
					sb.append(parametros[i].toString() + " ");
				}
				if (parImpar != "par") {
					sb.append("and ");
				}
			}
			sb.delete(sb.length() - 5, sb.length());
		}
		return sb.toString();
	}
	*/
	
	public static String criaUpdate_final(Object objeto, String condicaoWhere) {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("update ");

		Class<?> classe = objeto.getClass();

		stringBuilder.append(classe.getSimpleName());

		stringBuilder.append(" set ");

		try {

			for (Field field : classe.getDeclaredFields()) {

				field.setAccessible(true);

				if (!field.getName().contains("COLUMN")) {

					if (field.getType() == int.class || field.getType() == java.lang.String.class) {

						if (field.get(objeto) == null) {

							field.set(objeto, "");
						}

						if (field.getType() == String.class) {

							stringBuilder.append(field.getName() + "='" + field.get(objeto) + "',");
						} else {
							stringBuilder.append(field.getName() + "=" + field.get(objeto) + ",");
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);

		StringBuilder stringBuilder2 = new StringBuilder();
		stringBuilder2.append(condicaoWhere);

		stringBuilder.append(stringBuilder2);

		return stringBuilder.toString();
	}
	/**
	 * Metodo criado para criar query de inserção das tabelas de acordo com os valores do objeto
	 * 
	 * @author Fernando Pereira Santos - Consigaz -2017
	 * 
	 * @param criaUpdate_final 
	 */
	public static String criaInsert_final(Object objeto) {

		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder stringBuilder2 = new StringBuilder();

		stringBuilder.append("insert into ");

		Class<?> classe = objeto.getClass();

		stringBuilder.append(classe.getSimpleName().toLowerCase() + " (");

		try {

			for (Field field : classe.getDeclaredFields()) {

				field.setAccessible(true);

				if (!field.getName().contains("COLUMN")) {

					if (field.getType() == int.class || field.getType() == java.lang.String.class) {

						stringBuilder.append(field.getName() + ",");

						if (field.get(objeto) == null) {

							field.set(objeto, "");
						}

						if (field.getType() == String.class) {

							stringBuilder2.append("'" + field.get(objeto) + "',");
						} else {
							stringBuilder2.append(field.get(objeto) + ",");
						}

					}

				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		stringBuilder.append(") values (");

		stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
		stringBuilder2.append(");");

		stringBuilder.append(stringBuilder2);

		// Log.i("tag", "stringBuilder: "+stringBuilder.toString());

		return stringBuilder.toString();
	}

}
