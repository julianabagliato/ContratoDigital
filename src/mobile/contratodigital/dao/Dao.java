package mobile.contratodigital.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.view.ActivityLogin;
import sharedlib.contratodigital.model.Cad_atividade;
import sharedlib.contratodigital.model.Cad_canal_venda;
import sharedlib.contratodigital.model.Cad_eqpto;
import sharedlib.contratodigital.model.Cad_pecas;
import sharedlib.contratodigital.model.Cad_precos;
import sharedlib.contratodigital.model.Item_layout;
import sharedlib.contratodigital.model.Layout;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.model.Pda;
import sharedlib.contratodigital.model.Representante;
import sharedlib.contratodigital.model.Retorno;

public class Dao extends BancoSQLiteOpenHelper {

	private Context context;
	
	public Dao(Context context) {
		super(context);
		this.context = context;
	}

	public void insereOuAtualizaTabelas(Pda pda) {

		for (Representante representante : pda.getListaRepresentante()) {

			insereOUatualiza(representante, 
							 Representante.COLUMN_INTEGER_COD_REP, representante.getCod_rep());
		}

		for (Layout layout : pda.getListaLayout()) {

			insereOUatualiza(layout, 
							 Layout.COLUMN_INTEGER_NR_LAYOUT, layout.getNr_layout());
		}

		for (Item_layout item_layout : pda.getListaItem_layout()) {

			insereOUatualiza(item_layout, 
							 Item_layout.COLUMN_INTEGER_NR_LAYOUT, item_layout.getNr_layout(),
							 Item_layout.COLUMN_INTEGER_NR_ORDEM, item_layout.getNr_ordem());
		}

		for (Movimento movimento : pda.getListaMovimento()) {

			insereOUatualiza(movimento,
						     Movimento.COLUMN_INTEGER_NR_PROGRAMACAO, movimento.getNr_programacao(),
							 Movimento.COLUMN_INTEGER_NR_LAYOUT, movimento.getNr_layout(), 
							 Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita());
		}

		for (Cad_eqpto cad_eqpto : pda.getListaCad_eqpto()) {
			
			insereOUatualiza(cad_eqpto, 
							 Cad_eqpto.COLUMN_TEXT_it_codigo, cad_eqpto.getIt_codigo());
		}
		
		for (Cad_pecas cad_pecas : pda.getListaCad_pecas()) {
			
			insereOUatualiza(cad_pecas, 
							 Cad_pecas.COLUMN_TEXT_desc_item, cad_pecas.getDesc_item());
		}
		
		for (Cad_precos cad_precos : pda.getListaCad_precos()) {
			
			insereOUatualiza(cad_precos, 
							 Cad_precos.COLUMN_TEXT_Cod_estab, cad_precos.getCod_estab());
		}
		
		for (Cad_canal_venda cad_canal_venda : pda.getListaCad_canal_venda()) {
			
			insereOUatualiza(cad_canal_venda, 
							 Cad_canal_venda.COLUMN_TEXT_cod_canal_venda, cad_canal_venda.getCod_canal_venda());
		}

		//se nao entrar aqui eh porque quem esta chamando eh ActivityDashboard, entao NAO vou deletar tudo
		if(context instanceof ActivityLogin) {
		
			for(Cad_atividade cadAtividade : pda.getListaCad_atividade()) {
				this.insereOUatualiza(cadAtividade, 
									  Cad_atividade.COLUMN_TEXT_ATIVIDADE, cadAtividade.getAtividade());
			}
		}

		//nao tenho certeza se esta tabela irah provocar algum problema em alguma condicao especifica:
		for (Retorno retorno : pda.getListaRetorno()) {
			
			insereOUatualiza(retorno, 
							 Retorno.COLUMN_TEXT_nr_contrato, retorno.getNr_contrato());
		}//nao tenho certeza se esta tabela irah provocar algum problema em alguma condicao especifica.
		

	}

	public void insereOUatualiza(Object objeto, Object... parametros) {

		Class<?> classe = objeto.getClass();

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhereComParametrosString(parametros);

		String querySelect = select + condicaoWhere;

		insereOUatualizaObjeto_final(objeto, querySelect, condicaoWhere);
	}

	private void insereOUatualizaObjeto_final(Object objeto, String querySelect, String condicaoWhere) {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();

		Cursor cursor = sQLiteDatabase.rawQuery(querySelect, null);

		if (cursor.moveToFirst()) {
			atualizaObjeto_final(objeto, condicaoWhere);
		} else {
			insereObjeto_final(objeto);
		}
		cursor.close();
		sQLiteDatabase.close();
	}

	private void atualizaObjeto_final(Object objeto, String condicaoWhere) {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
		sQLiteDatabase.execSQL(Query.criaUpdate_final(objeto, condicaoWhere));
		sQLiteDatabase.close();
	}

	public void insereObjeto_final(Object objeto) {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
		sQLiteDatabase.execSQL(Query.criaInsert_final(objeto));
		sQLiteDatabase.close();
	}
	
	public <T> List<T> listaTodaTabela(Class<T> classe) {

		String querySelect = "select * from " + classe.getSimpleName();

		return devolveListaBaseadoEmSQL_final(classe, querySelect);
	}

	public <T> List<T> listaTodaTabela(Class<T> classe, Object... parametros) {

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhereComParametrosString(parametros);

		String querySelect = select + condicaoWhere;

		return devolveListaBaseadoEmSQL_final(classe, querySelect);
	}

	public <T> Object devolveObjeto(Class<T> classe, Object... parametros) {

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhereComParametrosString(parametros);

		String querySelect = select + condicaoWhere;

		return devolveObjetoBaseadoEmSQL_final(classe, querySelect);
	}
	
	public void deletaTodosDados() {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();

		for (String tabela : ListaComTabelasModel.devolveListaComTabelasModel()) {

			sQLiteDatabase.execSQL("delete from " + tabela);
		}
		sQLiteDatabase.close();
	}
	
	public void deletaObjeto(Class<?> classe, Object... parametros) {

		String delete = "delete from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhereComParametrosString(parametros);

		String queryDelete = delete + condicaoWhere ;

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
		sQLiteDatabase.execSQL(queryDelete);
		sQLiteDatabase.close();
	}
	
	private Object devolveObjetoBaseadoEmSQL_final(Class<?> classe, String querySelect) {

		SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();

		Cursor cursor = sQLiteDatabase.rawQuery(querySelect, null);

		Object objeto = null;

		if (cursor.moveToNext()) {

			objeto = PreencheObjetoUsandoCursor.devolveObjetoPreenchido(classe, cursor);
		}
		cursor.close();
		sQLiteDatabase.close();

		return objeto;
	}
	
	private <T> List<T> devolveListaBaseadoEmSQL_final(Class<T> classe, String querySelect) {

		List<T> lista = new ArrayList<T>();

		try {
			SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();

			Cursor cursor = sQLiteDatabase.rawQuery(querySelect, null);

			while (cursor.moveToNext()) {

				lista.add((T) PreencheObjetoUsandoCursor.devolveObjetoPreenchido(classe, cursor));
			}
			cursor.close();
			sQLiteDatabase.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public int selectDistinct_nrLayout(Class<?> classe, String descricao) {

		String sql = " SELECT distinct " + Layout.COLUMN_INTEGER_NR_LAYOUT + " FROM " + classe.getSimpleName()+ " where " + Layout.COLUMN_TEXT_DESCRICAO + " = '" + descricao + "' ";

		return devolveQtdEncontrada(sql);
	}

	public int selectDistinct_indTipLayout(Class<?> classe, int nrLayout) {

		String sql = " SELECT distinct " + Layout.COLUMN_INTEGER_IND_TIP_LAYOUT + " FROM " + classe.getSimpleName()+ " where " + Layout.COLUMN_INTEGER_NR_LAYOUT + " = " + nrLayout;

		return devolveQtdEncontrada(sql);
	}

	public int selectDistinct_codRep(Class<?> classe) {

		String sql = " SELECT distinct " + Representante.COLUMN_INTEGER_COD_REP + " FROM " + classe.getSimpleName();

		return devolveQtdEncontrada(sql);
	}
	
	public int selectCount(Class<?> classe) {

		String sql = "SELECT count(*) FROM " + classe.getSimpleName() + " WHERE nr_layout !=30 and nr_layout !=23";
	//	String teste ="SELECT * FROM " + classe.getSimpleName() + " WHERE nr_layout !=30 and nr_layout !=23";

		return devolveQtdEncontrada(sql);
	}

	private int devolveQtdEncontrada(String sql) {

		int qtd = 0;

		SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();

		Cursor cursor = sQLiteDatabase.rawQuery(sql, null);

		if (cursor.moveToFirst()) {

			qtd = cursor.getInt(0);
		}
		cursor.close();
		sQLiteDatabase.close();

		return qtd;
	}
	
	public ArrayList<Movimento> devolveListaComMovimentosPopulados(Movimento mov_informacoesCliente) {
		
		int nrVisita = mov_informacoesCliente.getNr_visita();

		ArrayList<Movimento> lista = new ArrayList<Movimento>();
		
		
		//estava nulo
		Movimento mov_contratoDigital = (Movimento) devolveObjeto(Movimento.class, 
																  Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita,
																  Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.CONTRATO_DIGITAL.getNumero());
		
		Movimento mov_enderecoPadrao = (Movimento) devolveObjeto(Movimento.class, 
																 Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita, 
																 Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.ENDERECO_PADRAO.getNumero());

		Movimento mov_enderecoEntrega = (Movimento) devolveObjeto(Movimento.class, 
																  Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita, 
																  Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.ENDERECO_ENTREGA.getNumero());

		Movimento mov_equipamentosSimulados = (Movimento) devolveObjeto(Movimento.class, 
																		Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita, 
																		Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.EQUIPAMENTOS_SIMULADOS.getNumero());

		Movimento mov_simulador = (Movimento) devolveObjeto(Movimento.class, 
															Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita, 
															Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.SIMULADOR2.getNumero());
		//estrava nulo
		Movimento mov_pecas = (Movimento) devolveObjeto(Movimento.class, 
														Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita,
														Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.PECAS.getNumero());

		Movimento mov_dadosDatasul = (Movimento) devolveObjeto(Movimento.class, 
															   Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita, 
															   Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.DADOS_DATASUL.getNumero());
		
		Movimento mov_dadosCadastro = (Movimento) devolveObjeto(Movimento.class, 
																Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita,
																Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.DADOS_CADASTRO.getNumero());
		
		Movimento mov_representacao = (Movimento) devolveObjeto(Movimento.class, 
																Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita,
																Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.REPRESENTACAO.getNumero());
		
		Movimento mov_consumoCliente = (Movimento) devolveObjeto(Movimento.class, 
																 Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita,
																 Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.CONSUMO_CLIENTE.getNumero());
		
		Movimento mov_clienteContaSim = (Movimento) devolveObjeto(Movimento.class, 
																  Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita,
																  Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.CLIENTE_CONTASIM.getNumero());
		
		Movimento mov_observacoesGerais = (Movimento) devolveObjeto(Movimento.class, 
																    Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita,
																    Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.OBSERVACOES_GERAIS.getNumero());
		
		//Movimento mov_equipamentosContratados = (Movimento) devolveObjeto(Movimento.class, 
																		  //Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita,
																		  //Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.EQUIPAMENTOS_CONTRATADOS.getNumero());
	
		
		lista.add(mov_contratoDigital);
		lista.add(mov_informacoesCliente);
		lista.add(mov_dadosCadastro);
		lista.add(mov_representacao);
		lista.add(mov_enderecoPadrao);
		lista.add(mov_enderecoEntrega);
		lista.add(mov_consumoCliente);
		lista.add(mov_clienteContaSim);
		lista.add(mov_observacoesGerais);
		lista.add(null);
		lista.add(mov_equipamentosSimulados);
		lista.add(mov_simulador);
		lista.add(mov_pecas);
		lista.add(mov_dadosDatasul);
		
		return lista;
	}
	
	public <T> List<T> listaTodosMovimentos_NAO_obrigatorios(Class<T> classe, int nr_visita) {

		String querySelect = "SELECT * FROM movimento mov inner join layout lay on mov.nr_layout = lay.nr_layout where lay.obrigatorio = 0 and mov.nr_visita = "+ nr_visita;

		return devolveListaBaseadoEmSQL_final(classe, querySelect);
	}
	
	public <T> List<T> listaTodaTabela_GroupBy_NrVisita(Class<T> classe, int nrLayoutObrigatorio) {

		String querySelect = " SELECT * FROM " + classe.getSimpleName() 
						   + " where " + Movimento.COLUMN_INTEGER_NR_LAYOUT+ " = " + nrLayoutObrigatorio
						 //+ " and " + Movimento.COLUMN_INTEGER_NR_VISITA + " != 0 "
						   + " group by " + Movimento.COLUMN_INTEGER_NR_VISITA;

		return devolveListaBaseadoEmSQL_final(classe, querySelect);
	}
	  
	  public int selectCount_where_nrVisita_naoEhZERO(Class<?> classe, int nr_layout){
	  
	  String sql = " SELECT count(*) FROM " + classe.getSimpleName() 
	  			 + " where "+ Movimento.COLUMN_INTEGER_NR_LAYOUT + " = " + nr_layout 
	  			 + " and " + Movimento.COLUMN_INTEGER_NR_VISITA + " != 0";
	  
	  return devolveQtdEncontrada(sql);
	  }
	  	 
	  public int devolveUltimoNrVisitaDoMovimento() {
		  
		  String query = " SELECT distinct "+Movimento.COLUMN_INTEGER_NR_VISITA+" FROM "+Movimento.class.getSimpleName() 
				  	   + " order by "+Movimento.COLUMN_INTEGER_NR_VISITA+" desc limit 1";

		  return devolveQtdEncontrada(query);
	  }
	  

}
