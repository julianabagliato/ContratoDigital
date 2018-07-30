package mobile.contratodigital.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import mobile.contratodigital.util.MeuAlerta;

	/**
	 * Classe para tratar dados do banco conexão entre o WS e o Mysql Necessário
	 * aumentar uma versão no banco, quando houver buscas atuais no banco
	 * 
	 * @author Ana Carolina Oliveira Barbosa - Mir Consultoria - 2018 & Fernando
	 *         Pereira Santos - Consigaz -2017
	 * @version 1.0
	 */

public class BancoSQLiteOpenHelper extends SQLiteOpenHelper {

	public static final String BANCO_NOME = "contrato_digital";
	public static final int BANCO_VERSAO = 107;
	private Context context;

	public BancoSQLiteOpenHelper(Context _context) {
		super(_context, BANCO_NOME, null, BANCO_VERSAO);
		this.context = _context;
	}

	@Override


	/**
	 * Metodo onCreate para criar tabelas com chaves.
	 * 
	 * @param onCreate
	 * @author Fernando Pereira Santos - Consigaz -2017
	 */
	public void onCreate(SQLiteDatabase sQLiteDatabase) {

		for (String tabela : ListaComTabelasModel.devolveListaComTabelasModel()) {

			try {
				Class<?> classe = Class.forName("sharedlib.contratodigital.model." + tabela);

				sQLiteDatabase.execSQL(Query.criaCreateTableComKeyy(classe));
			} catch (Exception erro) {
				new MeuAlerta("Erro onCreate", erro.toString(), context).meuAlertaOk();
			}
		}

	}

	/**
	 * Metodo onUpgrade para atualizar tabelas com chaves. aqui passa a versão
	 * antiga do banco e a nova versão para definir se a atualização é realmente
	 * necessaria
	 * 
	 * @param onUpgrade
	 * @author Fernando Pereira Santos - Consigaz -2017
	 */
	@Override
	public void onUpgrade(SQLiteDatabase sQLiteDatabase, int oldVersion, int newVersion) {

		for (String tabela : ListaComTabelasModel.devolveListaComTabelasModel()) {

			try {
				Class<?> classe = Class.forName("sharedlib.contratodigital.model." + tabela);

				sQLiteDatabase.execSQL(Query.criaDropTable_final(classe));
			} catch (Exception erro) {
				new MeuAlerta("Erro onUpgrade", erro.toString(), context).meuAlertaOk();
			}
		}

		onCreate(sQLiteDatabase);
	}
}