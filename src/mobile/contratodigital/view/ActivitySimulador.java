package mobile.contratodigital.view;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import mobile.contratodigital.R;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.dao.ListaComTodosOsProdutos;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.enums.TipoView;
import mobile.contratodigital.model.AnaliseGerencial;
import mobile.contratodigital.model.AnaliseMensagem;
import mobile.contratodigital.model.AnaliseOficial;
import mobile.contratodigital.model.Carrinho;
import mobile.contratodigital.model.CentralEmComodato;
import mobile.contratodigital.model.Cilindro;
import mobile.contratodigital.model.Equipamento;
import mobile.contratodigital.model.Granel;
import mobile.contratodigital.model.MaoDeObra;
import mobile.contratodigital.model.Material;
import mobile.contratodigital.model.Produto;
import mobile.contratodigital.model.RedeEmComodato;
import mobile.contratodigital.util.DataPersonalizada;
import mobile.contratodigital.util.Item;
import mobile.contratodigital.util.MeuAlerta;
import mobile.contratodigital.util.MoedaRS;
import mobile.contratodigital.util.TelaBuilder;
import sharedlib.contratodigital.model.Cad_precos;
import sharedlib.contratodigital.model.Item_layout;
import sharedlib.contratodigital.model.Layout;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.model.Representante;

public class ActivitySimulador extends Activity{

	private Context context;
	private TelaBuilder telaBuilder;
	private MaoDeObra maoDeObra;
	private Material material;
	private CentralEmComodato centralEmComodato;
	private LinearLayout ll_tituloCentralEmComodato;
	private LinearLayout ll_custoTotalDoInvestimento;
	private RedeEmComodato redeEmComodato;
	private Carrinho carrinho;
	private MoedaRS reais;
	private Movimento movimento1;
	private String valorOS;
	private double precoLucroZeroOficial;
	private double valorFuturoOficial;
	private double payBackMesesOficial;
	private double precoLucroZeroGerencial;
	private double valorFuturoGerencial;
	private double payBackMesesGerencial;
	private String VALOR_EquipamentosEspeciais;
	private String NOME_EquipamentosEspeciais;
	private String rede;
	private String comodato;	
	private static final int VAZIO = 0;
	private static final int NAO = 1;
	private AnaliseGerencial analiseGerencial;
	private AnaliseOficial analiseOficial;	
	private String ultimoDiametroEscolhido = "";
	public static final int REQUISICAO_SIMULADOR = 444;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		
		movimento1 = (Movimento) bundle.getSerializable("movimento");
		
		rede = "2;0;0";	
		comodato = "2";
		NOME_EquipamentosEspeciais ="Nenhum";
		VALOR_EquipamentosEspeciais ="0";
	
		context = ActivitySimulador.this;

		ActionBar actionBar = getActionBar();
		  		  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		  		  actionBar.setTitle("Informações de Empréstimo");		
	 	
		telaBuilder = new TelaBuilder(context);
		maoDeObra = new MaoDeObra();
		material = new Material();
		centralEmComodato = new CentralEmComodato();	
		
		carrinho = new Carrinho();
		reais = new MoedaRS();

		Dao dao = new Dao(context);

		Representante representante = (Representante) dao.devolveObjeto(Representante.class);

		Cad_precos cad_preco = (Cad_precos)dao.devolveObjeto(Cad_precos.class, Cad_precos.COLUMN_TEXT_Cod_estab, representante.getCod_estab());
			
		if(cad_preco == null) {
			
			new MeuAlerta("Não foram encontrados preços cadastrados para o estabelecimento: "+representante.getCod_estab(), null, context).meuAlertaOk();
			
			return;
		}else {
			analiseGerencial = new AnaliseGerencial(Double.parseDouble(cad_preco.getPreco_gerencial()), Double.parseDouble(cad_preco.getCusto_gerencial()), Double.parseDouble(cad_preco.getTaxa_juros()));
			analiseOficial = new AnaliseOficial(Double.parseDouble(cad_preco.getPreco_oficial()), Double.parseDouble(cad_preco.getCusto_oficial()), Double.parseDouble(cad_preco.getTaxa_juros()));	
		}
			
		Cilindro cilindro = new Cilindro(context);
		ArrayList<Produto> listaDeCilindros = cilindro.getLista();
		
		Granel granel = new Granel(context);
		ArrayList<Produto> listaDeTanques = granel.getLista();
		
		Equipamento equipamento = new Equipamento(context);
		ArrayList<Produto> listaDeEquipamentos = equipamento.getLista();
		
		ArrayList<Produto> listaSimNao = ListaComTodosOsProdutos.devolveListaSIM_NAO();				

		redeEmComodato = new RedeEmComodato();
		ArrayList<Produto> listaDeDiametros = redeEmComodato.getListaDeDiametros();
		
		setContentView(constroiTela(listaDeCilindros, listaDeTanques, listaDeEquipamentos, listaSimNao, listaDeDiametros, cilindro, granel, equipamento));
	}
	
	private ScrollView constroiTela(ArrayList<Produto> listaCilindros,
									ArrayList<Produto> listaTanques, 
									ArrayList<Produto> listaEquipamentos, 
									ArrayList<Produto> listaSimNao, 
									ArrayList<Produto> listaDiametro, 
									Cilindro cilindro, 
									Granel granel, 
									Equipamento equipamento 
									) {

	ScrollView sv = new ScrollView(context);	

	LinearLayout ll_telaHolder = telaBuilder.cria_LL_v_MATCH_MATCH();
				 		  ll_telaHolder.addView(criaSvTituloConteudo("CILINDROS", listaCilindros, cilindro));
				 		  ll_telaHolder.addView(criaSvTituloConteudo("TANQUES", listaTanques, granel));
				 	      ll_telaHolder.addView(criaSvTituloConteudo("EQUIPAMENTOS", listaEquipamentos, equipamento));   
				 		  ll_telaHolder.addView(criaLLTituloCentralEmComodato("CENTRAL EM COMODATO?", listaSimNao));
				 		  ll_telaHolder.addView(criaLLTituloRedeEmComodato("REDE EM COMODATO?", listaSimNao, listaDiametro));
				 	 	  ll_telaHolder.addView(criaLLComboBox("Gerar OS Automaticamente?",listaSimNao));
				 		  ll_telaHolder.addView(criaLLCustoTotalDoInvestimento());		  
				 	 	  ll_telaHolder.addView(criaButtonAplicar());
			   sv.addView(ll_telaHolder);
		return sv;
	}
	
	private Button criaButtonAplicar(){
		
		  Button botao = new Button(context);
 		  botao.setText("Aplicar");
 		  botao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	
				if(temCilindroOuTanque()) {
					
					acaoAposCliqueBotaoAplicar();							
				}else {
					new MeuAlerta("Favor informar no mínimo 1 CILINDRO OU 1 TANQUE", null, context).meuAlertaOk();
				}
				
			}
 		  });
 		  return botao;
	}
	
	private boolean temCilindroOuTanque() {
		
		for(Produto produto : carrinho.devolveProdutosAdicionados()){
			
			if(produto.getApelido().equals("CILINDROS") || produto.getApelido().equals("TANQUES")) {
				
				if(produto.getQuantidade() > 0) {
					
					return true;
				}	
			}	
		}
		return false;
	}
	
	private void acaoAposCliqueBotaoAplicar(){
		
		TextView tv_custoTotalDoInvestimento = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_custoTotalDoInvestimento");
				 tv_custoTotalDoInvestimento.setTextColor(Color.BLUE);
				 
		EditText et_precoNegociadoPorKG = (EditText) ll_custoTotalDoInvestimento.findViewWithTag("et_precoNegociadoPorKG"); 
		EditText et_prazoContratualMeses = (EditText) ll_custoTotalDoInvestimento.findViewWithTag("et_prazoContratualMeses"); 
		EditText et_consumoPrevistoEmKG = (EditText) ll_custoTotalDoInvestimento.findViewWithTag("et_consumoPrevistoEmKG"); 
		EditText et_condicaoPagamento = (EditText) ll_custoTotalDoInvestimento.findViewWithTag("et_condicaoPagamento"); 
		EditText et_taxaServico = (EditText) ll_custoTotalDoInvestimento.findViewWithTag("et_taxaServico"); 
		EditText et_taxaReligacao = (EditText) ll_custoTotalDoInvestimento.findViewWithTag("et_taxaReligacao"); 
		double custoTotalDoInvestimento = devolveZeroDoubleCasoEstejaVazia(tv_custoTotalDoInvestimento.getText().toString());
		String precoNegociadoPorKG = devolveZeroStringCasoEstejaVazia(et_precoNegociadoPorKG.getText().toString());
		int prazoContratualMeses = (int) devolveZeroDoubleCasoEstejaVazia(et_prazoContratualMeses.getText().toString());
		double consumoPrevistoEmKG = devolveZeroDoubleCasoEstejaVazia(et_consumoPrevistoEmKG.getText().toString());
		int condicaoPagamento = (int) devolveZeroDoubleCasoEstejaVazia(et_condicaoPagamento.getText().toString());
		String taxaServico = devolveZeroStringCasoEstejaVazia(et_taxaServico.getText().toString());
		String taxaReligacao = devolveZeroStringCasoEstejaVazia(et_taxaReligacao.getText().toString());
		insereProdutosNoBanco(custoTotalDoInvestimento, precoNegociadoPorKG, prazoContratualMeses, consumoPrevistoEmKG, condicaoPagamento, taxaServico, taxaReligacao,valorOS);
		
		
		//new MeuAlerta(  "Ação efetuda", null, context).meuAlertaOk();
		Toast.makeText(context, "Ação efetuda!", Toast.LENGTH_SHORT).show();
	
			
		setResult(REQUISICAO_SIMULADOR, new Intent());
	 	finish();
	}
	
	private void insereProdutosNoBanco(double custoTotalDoInvestimento, String precoNegociadoPorKG, int prazoContratualMeses, 
									   double consumoPrevistoEmKG, int condicaoPagamento, String taxaServico, String taxaReligacao, String valorOS2){
	
		Dao dao = new Dao(context);

		Representante representante = (Representante) dao.devolveObjeto(Representante.class);
		//Layout layoutObrigatorio = (Layout) dao.devolveObjeto(Layout.class, Layout.COLUMN_INTEGER_OBRIGATORIO, Generico.LAYOUT_OBRIGATORIO_SIM.getValor());
		Layout layout_simuladorANA = new Layout();
		layout_simuladorANA.setNr_layout(NomeLayout.SIMULADOR_ANA.getNumero());
		layout_simuladorANA.setInd_tip_layout(TipoView.LAYOUT_CONSULTA.getValor());
		layout_simuladorANA.setDescricao("SIMULADOR");
		layout_simuladorANA.setObrigatorio(0);
		layout_simuladorANA.setInd_tip_visualiz(TipoView.VISUALIZACAO_NORMAL.getValor());
					 
		dao.insereOUatualiza(layout_simuladorANA, Layout.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.SIMULADOR_ANA.getNumero());
		
		dao.deletaObjeto(Item_layout.class, Item_layout.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.SIMULADOR_ANA.getNumero());

		Representante representante2 = (Representante) dao.devolveObjeto(Representante.class);
		
		Layout layout_simuladorFER = new Layout();
		layout_simuladorFER.setNr_layout(NomeLayout.SIMULADOR_FER.getNumero());
		layout_simuladorFER.setInd_tip_layout(TipoView.LAYOUT_CONSULTA.getValor());
		layout_simuladorFER.setDescricao("EQUIPAMENTOS SIMULADOS");
		layout_simuladorFER.setObrigatorio(0);
		layout_simuladorFER.setInd_tip_visualiz(TipoView.VISUALIZACAO_NORMAL.getValor());
		 
		dao.insereOUatualiza(layout_simuladorFER, Layout.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.SIMULADOR_FER.getNumero());

		dao.deletaObjeto(Item_layout.class, Item_layout.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.SIMULADOR_FER.getNumero());

		Movimento mov_simuladorANA = new Movimento();
		mov_simuladorANA.setNr_programacao(movimento1.getNr_programacao());
		mov_simuladorANA.setNr_layout(NomeLayout.SIMULADOR_ANA.getNumero());		   
		mov_simuladorANA.setNr_visita(movimento1.getNr_visita());
		mov_simuladorANA.setCod_rep(representante.getCod_rep());
		mov_simuladorANA.setData_cadastro(new DataPersonalizada().pegaDataAtual_DDMMYYYY_HHMMSS());						  
				  
	    Movimento mov_simuladosFER = new Movimento();
		mov_simuladosFER.setNr_programacao(movimento1.getNr_programacao());
		mov_simuladosFER.setNr_layout(NomeLayout.SIMULADOR_FER.getNumero());		   
		mov_simuladosFER.setNr_visita(movimento1.getNr_visita());
		mov_simuladosFER.setCod_rep(representante2.getCod_rep());
		mov_simuladosFER.setData_cadastro(new DataPersonalizada().pegaDataAtual_DDMMYYYY_HHMMSS());						  

	    Movimento mov_equipamentos = new Movimento();
		mov_equipamentos.setNr_programacao(movimento1.getNr_programacao());
		mov_equipamentos.setNr_layout(NomeLayout.SIMULADOR_DATASUL.getNumero());		   
		mov_equipamentos.setNr_visita(movimento1.getNr_visita());
		mov_equipamentos.setCod_rep(representante2.getCod_rep());
		mov_equipamentos.setData_cadastro(new DataPersonalizada().pegaDataAtual_DDMMYYYY_HHMMSS());						  
		  		  
		int nr_ordem = 1;
		int nr_ordem2 = 1;
		int nr_ordem3 = 1;
		
//		Collections.sort(carrinho);
		
		for(Produto produto : carrinho.devolveProdutosAdicionados()){
			
			if(produto.getQuantidade() > 0){
				
				String info = "";
				String info2 = "";
				
				String valorTotalEmExtenso = reais.converteNumeroParaExtensoReais(produto.getValorTotal());
				
				if(produto.getApelido().equals("REDE")){
					
					info = produto.getQuantidade()+" metros de "+produto.getNome()+" polegadas "+valorTotalEmExtenso;
					info2 = produto.getNome()+";" +String.valueOf(produto.getQuantidade()) + ";"+ produto.getValorTotal();
					rede = "1" +";" +	produto.getNome()+";"+ produto.getQuantidade();
					
				}else if(produto.getApelido().equals("CENTRAL")){
					
					info = produto.getQuantidade()+" (uma) central completa "+valorTotalEmExtenso;				
					info2 = produto.getNome()+";" +String.valueOf(produto.getQuantidade()) + ";"+ produto.getValorTotal();
					comodato = "1";
				}else{
					info = reais.converteNumeroParaExtensoInteiro(produto.getQuantidade()) +produto.getNome()+" "+valorTotalEmExtenso;
					info2 = produto.getNome()+";" +String.valueOf(produto.getQuantidade()) + ";"+ produto.getValorTotal();
				}
	
				preencheNoObjetoOcampoInformacao(mov_simuladosFER, nr_ordem2, produto.getApelido()+": "+info);
				preencheNoObjetoOcampoInformacao(mov_equipamentos, nr_ordem3, info2);

				insereItemLayout2(dao, NomeLayout.SIMULADOR_FER.getNumero(), nr_ordem2, "|");
//				insereItemLayout2(dao3, NomeLayout.DADOS_DATASUL.getNumero(), nr_ordem3, "|");

				nr_ordem2++;
				nr_ordem3++;

			}			
		}		
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+8, String.valueOf(valorOS));

		insereItemLayout(dao, NomeLayout.SIMULADOR_FER.getNumero(), nr_ordem2,   "|");
		insereItemLayout(dao, NomeLayout.SIMULADOR_FER.getNumero(), nr_ordem2+1, "|");
		insereItemLayout(dao, NomeLayout.SIMULADOR_FER.getNumero(), nr_ordem2+2, "|");
		insereItemLayout(dao, NomeLayout.SIMULADOR_FER.getNumero(), nr_ordem2+3, "|");
		insereItemLayout(dao, NomeLayout.SIMULADOR_FER.getNumero(), nr_ordem2+4, "|");
		insereItemLayout(dao, NomeLayout.SIMULADOR_FER.getNumero(), nr_ordem2+5, "|");
		insereItemLayout(dao, NomeLayout.SIMULADOR_FER.getNumero(), nr_ordem2+6, "|");
		insereItemLayout(dao, NomeLayout.SIMULADOR_FER.getNumero(), nr_ordem2+7, "|");
		insereItemLayout(dao, NomeLayout.SIMULADOR_FER.getNumero(), nr_ordem2+8, "|");
				
		preencheNoObjetoOcampoInformacao(mov_simuladosFER, nr_ordem2,   "Custo total do investimento (A+B): "+reais.converteNumeroParaExtensoReais(custoTotalDoInvestimento));
		preencheNoObjetoOcampoInformacao(mov_simuladosFER, nr_ordem2+1, "Preço negociado por KG: R$:"+precoNegociadoPorKG);
		preencheNoObjetoOcampoInformacao(mov_simuladosFER, nr_ordem2+2, "Prazo contratual: "+reais.converteNumeroParaExtensoInteiro(prazoContratualMeses) + " meses");
		preencheNoObjetoOcampoInformacao(mov_simuladosFER, nr_ordem2+3, "Consumo previsto por mês: "+reais.converteNumeroParaExtensoInteiro((int)consumoPrevistoEmKG) + " kg");
		preencheNoObjetoOcampoInformacao(mov_simuladosFER, nr_ordem2+4, "Condição pagamento: "+reais.converteNumeroParaExtensoInteiro(condicaoPagamento) + " dias");		
		preencheNoObjetoOcampoInformacao(mov_simuladosFER, nr_ordem2+5, "Previsão de consumo até o fim do contrato: "+reais.converteNumeroParaExtensoInteiro(calculaPrevConsumoAtehFimContrato((int)consumoPrevistoEmKG, prazoContratualMeses)) + " kg");
		preencheNoObjetoOcampoInformacao(mov_simuladosFER, nr_ordem2+6, "Taxa de serviço: R$: "+taxaServico);
		preencheNoObjetoOcampoInformacao(mov_simuladosFER, nr_ordem2+7, "Taxa de religação: R$: "+taxaReligacao);	
															  String valorEquipamentosEspeciais = "0.00,00";
															  String tt = valorEquipamentosEspeciais.replace(".","").replace(",", ".");
															  double ff = devolveZeroDoubleCasoEstejaVazia(tt);
		preencheNoObjetoOcampoInformacao(mov_simuladosFER, nr_ordem2+8, "Equipamentos e investimentos especiais: "+NOME_EquipamentosEspeciais+" "+reais.converteNumeroParaExtensoReais(ff));
		
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem,   "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+1, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+2, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+3, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+4, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+5, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+6, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+7, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+8, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+9, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+10, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+11, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+12, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+13, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+14, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+15, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+16, "|");
		insereItemLayout3(dao, NomeLayout.SIMULADOR_ANA.getNumero(), nr_ordem+17, "|");
		
		DecimalFormat decimalFormat = new DecimalFormat("#0.0000");
		
		String d1 =	decimalFormat.format(Double.parseDouble(String.valueOf(precoLucroZeroOficial)));
		String d2 =	decimalFormat.format(Double.parseDouble(String.valueOf(valorFuturoOficial)));
		String d3 =	decimalFormat.format(Double.parseDouble(String.valueOf(payBackMesesOficial)));
		String d4 =	decimalFormat.format(Double.parseDouble(String.valueOf(precoLucroZeroGerencial)));
		String d5 =	decimalFormat.format(Double.parseDouble(String.valueOf(valorFuturoGerencial)));
		String d6 =	decimalFormat.format(Double.parseDouble(String.valueOf(payBackMesesGerencial)));
		
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem,    String.valueOf((custoTotalDoInvestimento)));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+1,  String.valueOf((precoNegociadoPorKG.replace(".","").replace(",", "."))));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+2,  String.valueOf((prazoContratualMeses)));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+3,  String.valueOf(((int)consumoPrevistoEmKG)));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+4,  String.valueOf((condicaoPagamento)));		
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+5,  String.valueOf((calculaPrevConsumoAtehFimContrato((int)consumoPrevistoEmKG, prazoContratualMeses))).replace(".","").replace(",", "."));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+6,  String.valueOf((taxaServico.replace(".","").replace(",", "."))));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+7,  String.valueOf((taxaReligacao.replace(".","").replace(",", "."))));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+8,  String.valueOf(valorOS.replace(".","").replace(",", ".")));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+9,  String.valueOf(d1.replace(".","").replace(",", ".")));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+10, String.valueOf(d2.replace(".","").replace(",", ".")));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+11, String.valueOf(d3.replace(".","").replace(",", ".")));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+12, String.valueOf(d4.replace(".","").replace(",", ".")));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+13, String.valueOf(d5.replace(".","").replace(",", ".")));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+14, String.valueOf(d6.replace(".","").replace(",", ".")));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+15, String.valueOf(NOME_EquipamentosEspeciais+";"+VALOR_EquipamentosEspeciais.replace(".","").replace(",", ".")));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+16, String.valueOf(comodato.replace(".","").replace(",", ".")));
		preencheNoObjetoOcampoInformacao(mov_simuladorANA, nr_ordem+17, String.valueOf(rede.replace(".","").replace(",", ".")));
		
		insereMovimento(dao, mov_simuladorANA);	
		insereMovimento(dao, mov_simuladosFER);
		insereMovimento(dao, mov_equipamentos);		
	}
		
	private int calculaPrevConsumoAtehFimContrato(int consumoPrevistoPorMes, int prazoVigencia){
		
		return  consumoPrevistoPorMes * prazoVigencia;
	}
		
	private Movimento preencheNoObjetoOcampoInformacao(Object objeto, int nrOrdem, String conteudo) {
		try {
			Class<?> classe = objeto.getClass();
			for (Field atributo : classe.getDeclaredFields()) {
				atributo.setAccessible(true);
				if (atributo.getName().contains("informacao_")) {
					int tamanhoTotal = atributo.getName().length();
					String stringCapturada = atributo.getName().substring(11, tamanhoTotal);
					int inteiroCapturado = Integer.parseInt(stringCapturada);
					if (inteiroCapturado == nrOrdem) {
						if(!conteudo.isEmpty()){
							atributo.set(objeto, conteudo);
							break;
						}
					}
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return (Movimento) objeto;
	}
	
	private void insereItemLayout(Dao dao, int nrLayout, int nr_ordem, String apelido){
		
		Item_layout item_layout = new Item_layout();
				    item_layout.setNr_layout(nrLayout);
					item_layout.setNr_ordem(nr_ordem);
					item_layout.setDescricao(apelido);
					item_layout.setInd_tip_dado(TipoView.CAMPO_TEXTO.getValor());
					item_layout.setInd_tip_visualiz(TipoView.VISUALIZACAO_NORMAL.getValor());
						   
		dao.insereObjeto_final(item_layout);				
	}
private void insereItemLayout2(Dao dao2, int nrLayout, int nr_ordem, String apelido){
		
		Item_layout item_layout2 = new Item_layout();
				    item_layout2.setNr_layout(nrLayout);
					item_layout2.setNr_ordem(nr_ordem);
					item_layout2.setDescricao(apelido);
					item_layout2.setInd_tip_dado(TipoView.CAMPO_TEXTO.getValor());
					item_layout2.setInd_tip_visualiz(TipoView.VISUALIZACAO_NORMAL.getValor());
						   
		dao2.insereObjeto_final(item_layout2);				
	}
	
	private void insereItemLayout3(Dao dao3, int nrLayout, int nr_ordem, String apelido){
	
		Item_layout item_layout3 = new Item_layout();
				    item_layout3.setNr_layout(nrLayout);
					item_layout3.setNr_ordem(nr_ordem);
					item_layout3.setDescricao(apelido);
					item_layout3.setInd_tip_dado(TipoView.CAMPO_TEXTO.getValor());
					item_layout3.setInd_tip_visualiz(TipoView.VISUALIZACAO_NORMAL.getValor());
						   
		dao3.insereObjeto_final(item_layout3);				

	}
	
	private void insereMovimento(Dao dao, Movimento mov){
	
		dao.insereOUatualiza(mov, 
				 			 //Movimento.COLUMN_INTEGER_NR_PROGRAMACAO, mov.getNr_programacao(), 
				 			 Movimento.COLUMN_INTEGER_NR_LAYOUT, mov.getNr_layout(),
				 			 Movimento.COLUMN_INTEGER_NR_VISITA, mov.getNr_visita()
								);
	}
	
	private LinearLayout criaLLCustoTotalDoInvestimento(){
	
		ll_custoTotalDoInvestimento = new LinearLayout(context);
		ll_custoTotalDoInvestimento.setOrientation(LinearLayout.VERTICAL);		
		ll_custoTotalDoInvestimento.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
	     			
		LinearLayout ll1 = criaLLOcupaLinhaInteira();
		ll1.addView(criaTvTitulo("Custo total do investimento: "));	
		final TextView tv_custoTotalDoInvestimento = criaTvConteudoComTamanhoMaior("0");
				 tv_custoTotalDoInvestimento.setTag("tv_custoTotalDoInvestimento");
				 tv_custoTotalDoInvestimento.addTextChangedListener(new TextWatcher() {
	 			        @Override
	 			        public void onTextChanged(CharSequence s, int start, int before, int count) {      	
	 			        }
	 			        @Override
	 			        public void beforeTextChanged(CharSequence s, int start, int count, int after) {  	     
	 			        }
	 			        @Override
	 			        public void afterTextChanged(Editable s) {
	 			        	
	 						procuraViewsParaUtilizarOsValoresInformados();									
	 			        }
	 			    });
		ll1.addView(tv_custoTotalDoInvestimento);
	
		LinearLayout ll2 = criaLLOcupaLinhaInteira();
		ll2.addView(criaTvTitulo("Preço negociado por Kg:"));	 
		EditText et_precoNegociadoPorKG  = criaEtComListenerDeAcaoAposClick(1);
				 et_precoNegociadoPorKG.setTag("et_precoNegociadoPorKG");
				 et_precoNegociadoPorKG.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });
 		ll2.addView(et_precoNegociadoPorKG);
  	
 		final LinearLayout ll8 = criaLLOcupaLinhaInteira();
 		ll8.addView(criaTvTitulo("Custo Adicional:"));	
 		
 		final EditText et_custoadd2  = new EditText(context);
		//et_custoadd2.setTag("et_custoadd");
		et_custoadd2.setHint("Digite adicional");
		et_custoadd2.setLayoutParams(new LinearLayout.LayoutParams(300, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));		
		et_custoadd2.addTextChangedListener(new TextWatcher() {
		private boolean estaAtualizando = false;
		int k = 1;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {     
	        if (k == 1){
	        	// Evita que o método seja executado varias vezes.
				if (estaAtualizando) {
					estaAtualizando = false;
					return;
				}else{
				estaAtualizando = true;
				}
				// Se tirar ele entra em loop
				NOME_EquipamentosEspeciais=String.valueOf(et_custoadd2.getText());	
	        }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {  	
        }
        @Override
        public void afterTextChanged(Editable s) {
        	
    		NOME_EquipamentosEspeciais = String.valueOf(et_custoadd2.getText());      								
        }
		
    });
 		ll8.addView(et_custoadd2);
		
		final EditText et_valorEquipamentosEspeciais  = new EditText(context);
		et_valorEquipamentosEspeciais.setTag("et_custoadd");
		//et_valorEquipamentosEspeciais.setText("0");
		et_valorEquipamentosEspeciais.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);	
		et_valorEquipamentosEspeciais.setLayoutParams(new LinearLayout.LayoutParams(300, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));		
		et_valorEquipamentosEspeciais.addTextChangedListener(new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {     

        	double custoAdicional = devolveCustoAdicional(charSequence);
    					
		TextView tv_custoTotalDoInvestimento = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_custoTotalDoInvestimento");
			if(tv_custoTotalDoInvestimento != null){	
				
				double custoTotalDoInvestimento = Double.parseDouble(tv_custoTotalDoInvestimento.getText().toString()); 
				double totalGeral = custoTotalDoInvestimento + custoAdicional;
				
				tv_custoTotalDoInvestimento.setText(""+totalGeral);			
			}	
		
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {  
        	
        	double custoAdicional = devolveCustoAdicional(charSequence);
            
		TextView tv_custoTotalDoInvestimento = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_custoTotalDoInvestimento");
			if(tv_custoTotalDoInvestimento != null){	
				
				double custoTotalDoInvestimento = Double.parseDouble(tv_custoTotalDoInvestimento.getText().toString()); 
				double totalGeral = custoTotalDoInvestimento - custoAdicional;
				
				tv_custoTotalDoInvestimento.setText(""+totalGeral);			
			}	

            
        }
        @Override
        public void afterTextChanged(Editable sss) {
        }
    });
		
		et_valorEquipamentosEspeciais.setFilters(new InputFilter[] { new InputFilter.LengthFilter(14) });
		//et_valorEquipamentosEspeciais.setVisibility(View.INVISIBLE);
 		ll8.addView(et_valorEquipamentosEspeciais);
 		
		LinearLayout ll3 = criaLLOcupaLinhaInteira();
		ll3.addView(criaTvTitulo("Prazo contratual (meses):"));
		EditText et_prazoContratualMeses  = criaEtComListenerDeAcaoAposClick(0);
				 et_prazoContratualMeses.setTag("et_prazoContratualMeses");	  	   
				 et_prazoContratualMeses.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });
 		ll3.addView(et_prazoContratualMeses);
	  	   	
		LinearLayout ll4 = criaLLOcupaLinhaInteira();
		ll4.addView(criaTvTitulo("Consumo previsto mensal (Kg):"));
		EditText et_consumoPrevistoEmKG  = criaEtComListenerDeAcaoAposClick(0);
				 et_consumoPrevistoEmKG.setTag("et_consumoPrevistoEmKG"); 	     
				 et_consumoPrevistoEmKG.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
		ll4.addView(et_consumoPrevistoEmKG);
		
		

		LinearLayout ll5 = criaLLOcupaLinhaInteira();
		ll5.addView(criaTvTitulo("Condição de pagamento (Dias):"));
		EditText et_condicaoPagamento = criaEtComListenerDeAcaoAposClick(0);
				 et_condicaoPagamento.setTag("et_condicaoPagamento"); 	     
				 et_condicaoPagamento.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });
		ll5.addView(et_condicaoPagamento);

		
		LinearLayout ll6 = criaLLOcupaLinhaInteira();
		ll6.addView(criaTvTitulo("Taxa de serviço:"));
		EditText et_taxaServico = criaEtComListenerDeAcaoAposClick(1);
				 et_taxaServico.setTag("et_taxaServico"); 	     
				 et_taxaServico.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });
		ll6.addView(et_taxaServico);

		LinearLayout ll7 = criaLLOcupaLinhaInteira();
		ll7.addView(criaTvTitulo("Taxa de religação:"));
		EditText et_taxaReligacao = criaEtComListenerDeAcaoAposClick(1);
				 et_taxaReligacao.setTag("et_taxaReligacao"); 	     
		  		 et_taxaReligacao.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });
		ll7.addView(et_taxaReligacao);

		
		TextView tv_precoLucroZero_oficial = criaTvConteudoComTamanhoMaior("ainda nao tem resultado");
				 tv_precoLucroZero_oficial.setTag("tv_precoLucroZero_oficial");
				 //tv_precoLucroZero_oficial.setTextColor(Color.WHITE);
				 tv_precoLucroZero_oficial.setVisibility(View.GONE);
				
		TextView tv_paybackMeses_oficial = criaTvConteudoComTamanhoMaior("ainda nao tem resultado");
				 tv_paybackMeses_oficial.setTag("tv_paybackMeses_oficial");
				 tv_paybackMeses_oficial.setVisibility(View.GONE);
				 //tv_paybackMeses_oficial.setTextColor(Color.WHITE);
				 
		TextView tv_valorFuturo_oficial = criaTvConteudoComTamanhoMaior("ainda nao tem resultado");
				 tv_valorFuturo_oficial.setTag("tv_valorFuturo_oficial");
				 tv_valorFuturo_oficial.setVisibility(View.GONE);
				 //tv_valorFuturo_oficial.setTextColor(Color.WHITE);
				 
		TextView tv_precoLucroZero_gerencial = criaTvConteudoComTamanhoMaior("ainda nao tem resultado");
				 tv_precoLucroZero_gerencial.setTag("tv_precoLucroZero_gerencial");
				 //tv_precoLucroZero_gerencial.setTextColor(Color.WHITE);
				 tv_precoLucroZero_gerencial.setVisibility(View.GONE);
					
		TextView tv_paybackMeses_gerencial = criaTvConteudoComTamanhoMaior("ainda nao tem resultado");
				 tv_paybackMeses_gerencial.setTag("tv_paybackMeses_gerencial");
				 tv_paybackMeses_gerencial.setTextColor(Color.WHITE);
				 //tv_paybackMeses_gerencial.setVisibility(View.GONE);
				 
		TextView tv_valorFuturo_gerencial = criaTvConteudoComTamanhoMaior("ainda nao tem resultado");
				 tv_valorFuturo_gerencial.setTag("tv_valorFuturo_gerencial");
				 tv_valorFuturo_gerencial.setVisibility(View.GONE);
				 //tv_valorFuturo_gerencial.setTextColor(Color.WHITE);

		ll_custoTotalDoInvestimento.addView(ll1);
		ll_custoTotalDoInvestimento.addView(ll2);
		ll_custoTotalDoInvestimento.addView(ll3);
		ll_custoTotalDoInvestimento.addView(ll4);
		ll_custoTotalDoInvestimento.addView(ll5);
		ll_custoTotalDoInvestimento.addView(ll6);
		ll_custoTotalDoInvestimento.addView(ll7);
		ll_custoTotalDoInvestimento.addView(ll8);

		
		ll_custoTotalDoInvestimento.addView(tv_precoLucroZero_oficial);
		ll_custoTotalDoInvestimento.addView(tv_paybackMeses_oficial);
		ll_custoTotalDoInvestimento.addView(tv_valorFuturo_oficial);	

		ll_custoTotalDoInvestimento.addView(tv_precoLucroZero_gerencial);
		ll_custoTotalDoInvestimento.addView(tv_paybackMeses_gerencial);
		ll_custoTotalDoInvestimento.addView(tv_valorFuturo_gerencial);	

		return ll_custoTotalDoInvestimento;
	}
	
	private double devolveCustoAdicional(CharSequence charSequence) {

		double custoAdicional = 0;

		if (!charSequence.toString().equals("")) {

			String char0 = String.valueOf(charSequence.charAt(0));

			if (!char0.equals(".")){
				
				custoAdicional = Double.parseDouble(charSequence.toString());
			}
		} 
		else {
			return 0;
		}

		return custoAdicional;
	}

	private EditText criaEtComListenerDeAcaoAposClick(final int k){
		
		final EditText editText  = new EditText(context);
				 editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);	
				 editText.setLayoutParams(new LinearLayout.LayoutParams(200, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));		
				 editText.addTextChangedListener(new TextWatcher() {
						private boolean estaAtualizando = false;
				

		        @Override
		        public void onTextChanged(CharSequence s, int start, int before, int count) {      
		        if (k == 1){
		        	// Evita que o método seja executado varias vezes.
					if (estaAtualizando) {
						estaAtualizando = false;
						return;
					}else{
					estaAtualizando = true;
					}
					// Se tirar ele entra em loop
				//	DecimalFormat df = new DecimalFormat("#0.0000");
					//String d =	df.format(devolveZero_double_CasoEstejaVazia2(editText.getText().toString));
					
					//tv_valorUnitario.setText(""+d.replace(",","."));
				//	editText.setText(""+d.replace(",","."));
					
		        	editText.setText(devolveZeroStringCasoEstejaVazia(editText.getText().toString()));
		    		editText.setSelection(editText.getText().length());
		    		
		        }
		        
		        }
		        @Override
		        public void beforeTextChanged(CharSequence s, int start, int count, int after) {  	     
		        }
		        @Override
		        public void afterTextChanged(Editable s) {
		    	
					procuraViewsParaUtilizarOsValoresInformados();										
		        }
		    });
		return editText;
	}
	
	private LinearLayout criaLLOcupaLinhaInteira(){
		
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.HORIZONTAL);		
		ll.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));

		return ll;
	}
	
	private void procuraViewsParaUtilizarOsValoresInformados(){
		
		TextView tv_custoTotalDoInvestimento = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_custoTotalDoInvestimento");
		EditText et_precoNegociadoPorKG = (EditText) ll_custoTotalDoInvestimento.findViewWithTag("et_precoNegociadoPorKG"); 
		EditText et_prazoContratualMeses = (EditText) ll_custoTotalDoInvestimento.findViewWithTag("et_prazoContratualMeses"); 
		EditText et_consumoPrevistoEmKG = (EditText) ll_custoTotalDoInvestimento.findViewWithTag("et_consumoPrevistoEmKG"); 
	
	
		double vp = devolveZeroDoubleCasoEstejaVazia(tv_custoTotalDoInvestimento.getText().toString());
		
		String precoNegociadoPorKGstring = devolveZeroStringCasoEstejaVazia(et_precoNegociadoPorKG.getText().toString());
		
		String precoNegociadoPorKGstringComPonto = precoNegociadoPorKGstring.replace(",", ".");
		
		//Log.i("tag","precoNegociadoPorKG: "+precoNegociadoPorKGstringComPonto);
		
		double precoNegociadoPorKG = Double.parseDouble(precoNegociadoPorKGstringComPonto);
		
		
		int nper = (int) devolveZeroDoubleCasoEstejaVazia(et_prazoContratualMeses.getText().toString());
		
		double consumoPrevistoKg = devolveZeroDoubleCasoEstejaVazia(et_consumoPrevistoEmKG.getText().toString());

		aplicaValoresReferenteAnaliseOficial(vp, precoNegociadoPorKG, nper, consumoPrevistoKg);
		
		aplicaValoresReferenteAnaliseGerencial(vp, precoNegociadoPorKG, nper, consumoPrevistoKg);
	}
	
	private void aplicaValoresReferenteAnaliseOficial(double vp, double precoNegociadoPorKG, int nper, double consumoPrevistoKg){
		
		TextView tv_precoLucroZero_oficial = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_precoLucroZero_oficial");
		TextView tv_paybackMeses_oficial = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_paybackMeses_oficial");
		TextView tv_valorFuturo_oficial = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_valorFuturo_oficial");

	
		//AnaliseOficial
		tv_precoLucroZero_oficial.setText("OFICIAL PGTO: "+ analiseOficial.devolvePGTOpersonalizado(nper, vp, consumoPrevistoKg));
		
		 payBackMesesOficial = analiseOficial.devolveNPERpersonalizado(vp, precoNegociadoPorKG, consumoPrevistoKg);
		 
		tv_paybackMeses_oficial.setText("OFICIAL PAYBACK: "+ payBackMesesOficial);
		//AnaliseMensagem.colorePayBack(tv_paybackMeses_oficial, payBackMeses_oficial);
		
		 valorFuturoOficial = analiseOficial.devolveVFpersonalizado(nper, vp, precoNegociadoPorKG, consumoPrevistoKg);
		tv_valorFuturo_oficial.setText("OFICIAL VF: "+ valorFuturoOficial);
		//AnaliseMensagem.coloreValorFuturo(tv_valorFuturo_oficial, valorFuturo_oficial);
	}
	
	private void aplicaValoresReferenteAnaliseGerencial(double vp, double precoNegociadoPorKG, int nper, double consumoPrevistoKg){
		
		TextView tv_precoLucroZero_gerencial = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_precoLucroZero_gerencial");
		TextView tv_paybackMeses_gerencial = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_paybackMeses_gerencial");
		TextView tv_valorFuturo_gerencial = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_valorFuturo_gerencial");
		
		//AnaliseGerencial
		tv_precoLucroZero_gerencial.setText("GERENCIAL PGTO: "+ analiseGerencial.devolvePGTOpersonalizado(nper, vp, consumoPrevistoKg));

		payBackMesesGerencial = analiseGerencial.devolveNPERpersonalizado(vp, precoNegociadoPorKG, consumoPrevistoKg);
		 
		tv_paybackMeses_gerencial.setText("GERENCIA PAYBACK: "+ payBackMesesGerencial);
		AnaliseMensagem.colorePayBack(tv_paybackMeses_gerencial, payBackMesesGerencial);
		
		 valorFuturoGerencial = analiseGerencial.devolveVFpersonalizado(nper, vp, precoNegociadoPorKG, consumoPrevistoKg);
		tv_valorFuturo_gerencial.setText("GERENCIAL VF: "+ valorFuturoGerencial);
		//AnaliseMensagem.coloreValorFuturo(tv_valorFuturo_gerencial, valorFuturo_gerencial);
	}	
	
	
	private LinearLayout criaLLTituloCentralEmComodato(String titulo, final ArrayList<Produto> listaSimNao){

		ll_tituloCentralEmComodato = new LinearLayout(context);
		ll_tituloCentralEmComodato.setOrientation(LinearLayout.HORIZONTAL);		
		ll_tituloCentralEmComodato.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		ll_tituloCentralEmComodato.setBackgroundColor(Color.GRAY);
		ll_tituloCentralEmComodato.addView(criaTvTitulo(titulo));
		     	
	     			Spinner spinnerSimNao = new Spinner(context);
	     			spinnerSimNao.setAdapter(new ArrayAdapter(context, R.layout.item_menu_geral, listaSimNao));
	     			spinnerSimNao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	     				@Override
	     				public void onItemSelected(AdapterView<?> parent, View view, int posicaoSelecionada, long id) {
	     					
	     					chamaConteudoLLCentralComodato(posicaoSelecionada);
	     				}
	     				@Override
	     				public void onNothingSelected(AdapterView<?> arg0) {		
	     				}
	     			});

	     	   ll_tituloCentralEmComodato.addView(spinnerSimNao);
	     	   
		return ll_tituloCentralEmComodato;
	}
	
	private ScrollView criaSvTituloConteudo(String titulo, ArrayList<Produto> lista, final Item item ){
		
		ScrollView scrollView = new ScrollView(context);

		LinearLayout ll_tituloEconteudoHolder = new LinearLayout(context);
			 	     ll_tituloEconteudoHolder.setOrientation(LinearLayout.VERTICAL);		
			 	     ll_tituloEconteudoHolder.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			 	   				  
			 	     ll_tituloEconteudoHolder.addView(criaLLTitulo(titulo, ll_tituloEconteudoHolder, lista, item));
			 	     
			 	     for(Produto produto : lista) {
			 	    	 
			 	    ll_tituloEconteudoHolder.addView(criaLLConteudo(ll_tituloEconteudoHolder, item, titulo, produto));	
			 	     }
				   	 
			   scrollView.addView(ll_tituloEconteudoHolder);    
		return scrollView;		   		    
	} 	
	
	private LinearLayout criaLLTitulo(final String titulo, final LinearLayout ll_tituloEconteudoHolder, final ArrayList<Produto> lista, final Item item){
		
		LinearLayout ll_titulo = new LinearLayout(context);
		 ll_titulo.setOrientation(LinearLayout.HORIZONTAL);		
		 ll_titulo.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		 ll_titulo.setBackgroundColor(Color.LTGRAY);

			String espaco = "            ";
			
	 			  //TextView tv_titulo = criaTvTitulo(titulo+espaco+"Quantidade"+espaco+"Valor Unitário"+espaco+"Valor Total"+espaco);
	 			  TextView tv_titulo = criaTvTitulo(titulo);
	 			  		   tv_titulo.setMinWidth(300);
		 ll_titulo.addView(tv_titulo);
		 
		return ll_titulo;
	}
		
	private LinearLayout criaLLConteudo(final LinearLayout ll_tituloEconteudoHolder, 
			final Item item, final String apelido, final Produto produto){
					
					int left = 0;
					int top = 0;
					int right = 30;
					int bottom = 0;

					final LinearLayout ll_conteudo1 = new LinearLayout(context);
					ll_conteudo1.setOrientation(LinearLayout.HORIZONTAL);			
					ll_conteudo1.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
					
					LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
											  ll.setMargins(left, top, right, bottom);

					final TextView tv_valorUnitario = criaTvConteudo3(""+produto.getValorUnitario());
					tv_valorUnitario.setLayoutParams(ll);

					final TextView tv_valorTotalDeUmProduto = criaTvConteudo3("");
					tv_valorTotalDeUmProduto.setLayoutParams(ll);
					tv_valorTotalDeUmProduto.setTag("tv_valorTotal");
									
		final EditText et_quantidade = new EditText(context);
		//et_quantidade.setMinWidth(200);
		LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams(200, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		 							ll2.setMargins(left, top, right, bottom);
		et_quantidade.setLayoutParams(ll2);
		et_quantidade.setTag("et_quantidade");
		et_quantidade.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
		et_quantidade.setInputType(InputType.TYPE_CLASS_NUMBER);			
		et_quantidade.addTextChangedListener(new TextWatcher() {
	        @Override
	        public void onTextChanged(CharSequence s, int start, int before, int count) {      	
	        }
	        @Override
	        public void beforeTextChanged(CharSequence s, int start, int count, int after) {  	     
	        }
	        @Override
	        public void afterTextChanged(Editable s) {
	        	
	        acaoAposCliqueEtQuantidade(ll_conteudo1, item, et_quantidade, tv_valorUnitario, tv_valorTotalDeUmProduto, apelido, produto.getNome());
	        }
	    });
		
			   ll_conteudo1.addView(telaBuilder.cria_TV_titulo(produto.getNome()));
			   ll_conteudo1.addView(et_quantidade);
			   ll_conteudo1.addView(tv_valorUnitario);
			   ll_conteudo1.addView(tv_valorTotalDeUmProduto);
			   //ll_conteudo1.addView(b_remover);
			   
	return ll_conteudo1;
	}
	
	private void acaoAposCliqueEtQuantidade(LinearLayout ll_conteudo1, Item item, EditText et_quantidade, TextView tv_valorUnitario, 
														TextView tv_valorTotalDeUmProduto, String apelido, String nomeDaOpcaoEscolhida){
		
    	double valorTot = procuraValorTotalDeUmProdutoNoLLConteudo(ll_conteudo1);
    	
    	removeTvTemp(ll_conteudo1, valorTot);
		
    	double valorTotalDeUmProduto = calculaValorTotalDeUmProduto(et_quantidade, tv_valorUnitario);
    	    	
    	tv_valorTotalDeUmProduto.setText(formataParaDuasCasasDecimais(valorTotalDeUmProduto));
    	
    	zeraOCampoCustoAdicional();

		setEscolhidoNomeCilindroEquantidade(ll_conteudo1, item, et_quantidade, nomeDaOpcaoEscolhida);
		
		adicionaTvConteudoAuxiliar(ll_conteudo1, item, valorTotalDeUmProduto, apelido);
	}
	
	private void zeraOCampoCustoAdicional() {
		EditText et_custoadd = (EditText) ll_custoTotalDoInvestimento.findViewWithTag("et_custoadd");
		if(et_custoadd != null){	
			et_custoadd.setText("");
		}   		
	}
		
	private double procuraValorTotalDeUmProdutoNoLLConteudo(LinearLayout ll_conteudo1){
		
		TextView tv_valorTotalDeUmProduto = (TextView) ll_conteudo1.findViewWithTag("tv_valorTotal");
		
		double valorTotalDeUmProduto = 0;		
		
		if(tv_valorTotalDeUmProduto != null){
			
			valorTotalDeUmProduto = devolveZeroDoubleCasoEstejaVazia(tv_valorTotalDeUmProduto.getText().toString());
		}
		return valorTotalDeUmProduto;
	}
	
	private Item setEscolhidoNomeCilindroEquantidade(LinearLayout ll_conteudo1, Item item, EditText et_quantidade, 
																							 String nomeDaOpcaoEscolhida){
	
    	//Spinner spinner = (Spinner) ll_conteudo1.findViewWithTag("spinnerConteudo");
    	
    	//String nomeDaOpcaoEscolhida = ""+spinner.getSelectedItem();
    	
    	item.setItemEscolhido(nomeDaOpcaoEscolhida);
    	
    	int quantidade = 0;	
		if(!et_quantidade.getText().toString().isEmpty()){
			quantidade = devolveZeroIntCasoEstejaVazia(et_quantidade.getText().toString());
		}
		item.setQuantidadeEscolhida(quantidade);
		
		return item;
	}
	
	private double procuraItemSelecionadoEfazAlgo(ArrayList<Produto> lista, int posicaoSelecionada, EditText et_quantidade, TextView tv_valorUnitario, Item item){

		double valorTotal = 0;

		for (int i = 0; i < lista.size(); i++) {
			
			if (posicaoSelecionada == i) {
				
				Produto produto = lista.get(i);
				
				tv_valorUnitario.setText(formataParaDuasCasasDecimais(produto.getValorUnitario()));
				
				String nomeDaOpcaoEscolhida = produto.getNome();
				
				item.setItemEscolhido(nomeDaOpcaoEscolhida);
				
				int quantidade = 0;
				
				if(!et_quantidade.getText().toString().isEmpty()){
					
					quantidade = devolveZeroIntCasoEstejaVazia(et_quantidade.getText().toString());
				}
				item.setQuantidadeEscolhida(quantidade);
		
				valorTotal = calculaValorTotalDeUmProduto(et_quantidade, tv_valorUnitario);		
			}	
		}
		return valorTotal;
	}
	
	private double calculaValorTotalDeUmProduto(EditText et_quantidade, TextView tv_valorUnitario){
		
		int quantidade = 0;
		
		double valorUnitario = 0;
		
		if(!et_quantidade.getText().toString().isEmpty()){
			
			quantidade = devolveZeroIntCasoEstejaVazia(et_quantidade.getText().toString());
		}
		
		if(!tv_valorUnitario.getText().toString().isEmpty()){
		   
			valorUnitario = devolveZeroDoubleCasoEstejaVazia(tv_valorUnitario.getText().toString());			
		}		
		
		double valorTotal = quantidade * valorUnitario;
		
		return valorTotal;
	}
		
	private LinearLayout criaLLTituloRedeEmComodato(String titulo, final ArrayList<Produto> listaSimNao, final ArrayList<Produto> listaDiametro){

		final LinearLayout ll_tituloRedeEmComodato = new LinearLayout(context);
		ll_tituloRedeEmComodato.setOrientation(LinearLayout.HORIZONTAL);		
		ll_tituloRedeEmComodato.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		ll_tituloRedeEmComodato.setBackgroundColor(Color.GRAY);
		ll_tituloRedeEmComodato.addView(criaTvTitulo(titulo));
		     	
	     			Spinner spinnerSimNao = new Spinner(context);
	     			spinnerSimNao.setAdapter(new ArrayAdapter(context, R.layout.item_menu_geral, listaSimNao));
	     			spinnerSimNao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	     				@Override
	     				public void onItemSelected(AdapterView<?> parent, View view, int posicaoSelecionada, long id) {
	     					
	     					chamaConteudoLLRedeComodato(posicaoSelecionada, listaDiametro, ll_tituloRedeEmComodato);
	     				}
	     				@Override
	     				public void onNothingSelected(AdapterView<?> arg0) {		
	     				}
	     			});

	     			ll_tituloRedeEmComodato.addView(spinnerSimNao);
	     	   
		return ll_tituloRedeEmComodato;
	}
	private LinearLayout criaLLComboBox(String titulo, final ArrayList<Produto> listaSimNao){

		LinearLayout ll_Combobox = new LinearLayout(context);
		ll_Combobox.setOrientation(LinearLayout.HORIZONTAL);		
		ll_Combobox.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		ll_Combobox.setBackgroundColor(Color.GRAY);
		ll_Combobox.addView(criaTvTitulo(titulo));
		     	
	     			final Spinner spinnerSimNao2 = new Spinner(context);
	     			spinnerSimNao2.setAdapter(new ArrayAdapter(context, R.layout.item_menu_geral, listaSimNao));
	     			spinnerSimNao2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	     			
	     				@Override
						public void onItemSelected(AdapterView<?> parent, View view, int posicaoSelecionada, long id) {
	     					
	     					valorOS = String.valueOf(spinnerSimNao2.getSelectedItemPosition());
	     					if (spinnerSimNao2.getSelectedItemPosition() == 0){
	     						valorOS = String.valueOf("2");
	     					}
	     					
	     				}
	     				@Override
						public void onNothingSelected(AdapterView<?> arg0) {	
	     				}
	     			});

	     			ll_Combobox.addView(spinnerSimNao2);
	     	   
		return ll_Combobox ;
	}

	
	private void chamaConteudoLLRedeComodato(int posicaoSelecionada, ArrayList<Produto> listaDiametro, LinearLayout ll_tituloRedeEmComodato){
		
		if (posicaoSelecionada == NAO || posicaoSelecionada == VAZIO) {
			
			LinearLayout ll_conteudoRedeComodato = (LinearLayout) ll_tituloRedeEmComodato.findViewWithTag("ll_conteudoRedeComodato");
			
			if(ll_conteudoRedeComodato != null){
				
				//###########################
				ll_tituloRedeEmComodato.removeView(ll_conteudoRedeComodato);	
				
				double custoRede = procuraCustoRedeNoLLConteudoRedeComodato(ll_conteudoRedeComodato);
	
	         	Spinner spinnerDiametro = (Spinner) ll_conteudoRedeComodato.findViewWithTag("spinnerDiametro");
				  
				String nomeDaOpcaoEscolhida = ""+spinnerDiametro.getSelectedItem();
		    	
		    					String chave = nomeDaOpcaoEscolhida+custoRede;			  		    
				carrinho.removeProduto(chave);
	
				informaCustoTotalDoInvestimento();
				//###########################
	
				
			}
			
		}else {
			ll_tituloRedeEmComodato.addView(criaLLConteudoRedeComodato(listaDiametro));   	     	 	     				
		}
	
	}
	
	private LinearLayout criaLLConteudoRedeComodato(final ArrayList<Produto> listaDiametro){
		
		final LinearLayout ll_conteudoRedeComodato = new LinearLayout(context);
		ll_conteudoRedeComodato.setOrientation(LinearLayout.HORIZONTAL);		
		ll_conteudoRedeComodato.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		ll_conteudoRedeComodato.setTag("ll_conteudoRedeComodato");
				
				final TextView tv_custoRede = criaTvConteudo("0");
							   tv_custoRede.setTag("tv_custoRede");
				
				final EditText et_metragem = new EditText(context);
				LinearLayout.LayoutParams ll2 = new LinearLayout.LayoutParams(100, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
				 						   //ll2.setMargins(left, top, right, bottom);
				et_metragem.setLayoutParams(ll2);
				et_metragem.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
				et_metragem.setInputType(InputType.TYPE_CLASS_NUMBER);			
				et_metragem.addTextChangedListener(new TextWatcher() {
			        @Override
			        public void onTextChanged(CharSequence s, int start, int before, int count) {      	
			        }
			        @Override
			        public void beforeTextChanged(CharSequence s, int start, int count, int after) {  	     
			        }
			        @Override
			        public void afterTextChanged(Editable s) {
			        	
			           	Spinner spinnerDiametro = (Spinner) ll_conteudoRedeComodato.findViewWithTag("spinnerDiametro");
			            
			        	acaoAposCliqueRedeComodato(ll_conteudoRedeComodato, et_metragem, tv_custoRede, listaDiametro, spinnerDiametro);		
			        }
			    });
		
				final Spinner spinnerDiametro = new Spinner(context);
				spinnerDiametro.setAdapter(new ArrayAdapter(context, R.layout.item_menu_geral, listaDiametro));
				spinnerDiametro.setTag("spinnerDiametro");
				spinnerDiametro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int posicaoSelecionada, long id) {
						
						acaoAposCliqueRedeComodato(ll_conteudoRedeComodato, et_metragem, tv_custoRede, listaDiametro, spinnerDiametro);
					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {		
					}
				});
		
				ll_conteudoRedeComodato.addView(criaTvTitulo("Diâmetro"));
				ll_conteudoRedeComodato.addView(spinnerDiametro);
				ll_conteudoRedeComodato.addView(criaTvTitulo("Metragem:"));
				ll_conteudoRedeComodato.addView(et_metragem);
				ll_conteudoRedeComodato.addView(criaTvTitulo("Custo rede:"));
				ll_conteudoRedeComodato.addView(tv_custoRede);
	
		return ll_conteudoRedeComodato;
	}
		
	private double procuraCustoRedeNoLLConteudoRedeComodato(LinearLayout ll_conteudoRedeComodato){
		
		TextView tv_custoRede = (TextView) ll_conteudoRedeComodato.findViewWithTag("tv_custoRede");
		
		double custoRede = 0;		
		
		if(tv_custoRede != null){
			
			custoRede = devolveZeroDoubleCasoEstejaVazia(tv_custoRede.getText().toString());
		}
		return custoRede;
	}

	private double calculaCustoRede(ArrayList<Produto> listaDiametro, int posicaoSelecionada, EditText et_metragem){
		
		double totalCustoRede = 0;
		
		for (int i = 0; i < listaDiametro.size(); i++) {

			if (posicaoSelecionada == i) {
			
				double metragem = 0;
				double diametro = 0;
				
				diametro = listaDiametro.get(i).getValorUnitario();
				
 				if(!et_metragem.getText().toString().isEmpty()){
 					
 					metragem = devolveZeroDoubleCasoEstejaVazia(et_metragem.getText().toString());
 				}
 	
				//double totalCustoRede = redeEmComodato.devolveTotalCustoRede(diametro, metragem);
				//tv_custoRede.setText(""+totalCustoRede);
 				totalCustoRede = redeEmComodato.devolveTotalCustoRede(diametro, metragem);
			}
		}
		return totalCustoRede;
	}

	private double devolveZeroDoubleCasoEstejaVazia(String valor){
		
		if(valor.isEmpty() || valor.equals(".")){
			return  0;
		}else{
			return Double.parseDouble((valor));
		}
	}
	
	private int devolveZeroIntCasoEstejaVazia(String valor){
		
		if(valor.isEmpty()){
			return 0;
		}else{
			return Integer.parseInt(valor);
		}
	}
	
	private String devolveZeroStringCasoEstejaVazia(String valor){
		
		if(valor.isEmpty() || valor.equals(".")){
			return "0.0000";
		}else{
			
			String stringComSimbolos = valor;
			
			String stringLimpa = stringComSimbolos.replaceAll("[.]", "").replaceAll("[,]", "");

			BigDecimal parsed = null;

			StringBuilder stringBuilder = new StringBuilder();

			parsed = new BigDecimal(stringLimpa).setScale(4, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(10000), BigDecimal.ROUND_FLOOR);

			String parsedd = parsed.toString();

			String cleanStrin = parsedd.replace(".", ",");

			int tamanhoTotal = cleanStrin.length();

			int tamanhoAtehAvirgula = cleanStrin.substring(0, cleanStrin.indexOf(",")).length();
			
			stringBuilder.append(cleanStrin);

			if(tamanhoAtehAvirgula >= 4){

				stringBuilder.insert(tamanhoTotal - (stringBuilder.substring(cleanStrin.indexOf(",") - 3).length() ), ".");
			}

			if(tamanhoAtehAvirgula >= 7){

				stringBuilder.insert(tamanhoTotal - (stringBuilder.substring(cleanStrin.indexOf(",") - 5).length() ), ".");
			}
					
			valor = stringBuilder.substring(0,stringBuilder.length());
		}
		return valor;
	}
	
	
	
	private LinearLayout criaLLConteudoCentralComodato(){
	
		LinearLayout ll_conteudoCentralComodato = new LinearLayout(context);
		ll_conteudoCentralComodato.setOrientation(LinearLayout.HORIZONTAL);		
		ll_conteudoCentralComodato.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		ll_conteudoCentralComodato.setGravity(Gravity.CENTER_VERTICAL);
		ll_conteudoCentralComodato.setTag("ll_conteudoCentralComodato");
		
		TextView tv_maoDeObra = criaTvConteudo("0.0");
				 tv_maoDeObra.setTag("tv_maoDeObra");
					   
		TextView tv_material = criaTvConteudo("0.0");
				 tv_material.setTag("tv_material");
		
		TextView tv_custoCentral = criaTvConteudo("0.0");
				 tv_custoCentral.setTag("tv_custoCentral");
		
		ll_conteudoCentralComodato.addView(criaTvTitulo(" Mão de obra: "));
 		ll_conteudoCentralComodato.addView(tv_maoDeObra);
 		ll_conteudoCentralComodato.addView(criaTvTitulo(" Material: "));
 		ll_conteudoCentralComodato.addView(tv_material);
 		ll_conteudoCentralComodato.addView(criaTvTitulo(" Custo central: "));
 		ll_conteudoCentralComodato.addView(tv_custoCentral);
 		
		return ll_conteudoCentralComodato;
	}
	
	private double procuraCustoCentralNoLLConteudoCentralComodato(LinearLayout ll_conteudoCentralComodato){
		
		TextView tv_custoCentral = (TextView) ll_conteudoCentralComodato.findViewWithTag("tv_custoCentral");
		
		double custoCentral = 0;		
		
		if(tv_custoCentral != null){
			
			custoCentral = devolveZeroDoubleCasoEstejaVazia(tv_custoCentral.getText().toString());
		}
		return custoCentral;
	}
	
	private TextView criaTvTitulo(String nome){
		
		TextView tv = new TextView(context);
			 	 tv.setText(nome);
			 	 tv.setTextSize(16);

		return tv;	
	}
	
	private TextView criaTvConteudo(String nome){
		
		 TextView tv = new TextView(context);
			  	  tv.setText(nome);
			  	  tv.setTextSize(13);
			  	  
		return tv;	
	}

	private TextView criaTvConteudo3(String nome){
		
		 TextView tv = new TextView(context);
			  	  tv.setText(nome);
			  	  tv.setTextSize(25);
			  	tv.setMinWidth(200);
			  	
		return tv;	
	}

	private TextView criaTvConteudoComTamanhoMaior(String nome){
		
		 TextView tv = new TextView(context);
			  	  tv.setText(nome);
			  	  tv.setTextSize(25);
			  	  
		return tv;	
	}

	private void removeTvTemp(LinearLayout ll_conteudo1, double custoTotal){
		
		TextView tv_temp = (TextView) ll_conteudo1.findViewWithTag("tv_temp");
		
		if(tv_temp != null){
			
			ll_conteudo1.removeView(tv_temp);
			
			String temp = tv_temp.getText().toString();
			 
			String[] listaComValores = temp.split(";");
			
			tv_temp.setTextColor(Color.BLUE);

			double maoDeObraCustoUmaQTD = devolveZeroDoubleCasoEstejaVazia(listaComValores[3]);
			double maoDeObraTotalAdicional = devolveZeroDoubleCasoEstejaVazia(listaComValores[5]);
			
			double materialCustoUmaQTD = devolveZeroDoubleCasoEstejaVazia(listaComValores[6]);
			double materialTotalAdicional = devolveZeroDoubleCasoEstejaVazia(listaComValores[8]);

			
			maoDeObra.removeSubTotalCusto1(maoDeObraCustoUmaQTD);
			maoDeObra.removeSubTotalAdicional(maoDeObraTotalAdicional);
			
			material.removeSubTotalCusto1(materialCustoUmaQTD);
			material.removeSubTotalAdicional(materialTotalAdicional);
				
							String chave = listaComValores[0]+custoTotal;			  		
	 		carrinho.removeProduto(chave);
		}		
	}

	private void chamaConteudoLLCentralComodato(int posicaoSelecionada){
		
		//se clicou em nao
		if (posicaoSelecionada == NAO || posicaoSelecionada == VAZIO) {
			
			LinearLayout ll_conteudoCentralComodato = (LinearLayout) ll_tituloCentralEmComodato.findViewWithTag("ll_conteudoCentralComodato");
			
			//se ll_conteudoCentralComodato esta visivel
			if(ll_conteudoCentralComodato != null){
				
				
				//esta faltando: depois que remover a view, tambem remover o produto
				//###########################
				ll_tituloCentralEmComodato.removeView(ll_conteudoCentralComodato);	
				
				double custoCentral = procuraCustoCentralNoLLConteudoCentralComodato(ll_conteudoCentralComodato);
	
							    String chave = "CENTRAL"+custoCentral;		 		   		   
				carrinho.removeProduto(chave);
	
				informaCustoTotalDoInvestimento();
				//###########################
							
				
			}  	   
		}
		else{

			ll_tituloCentralEmComodato.addView(criaLLConteudoCentralComodato());  
			
			informaValorDaMaoDeObra();
			informaValorDoMaterial();
			informaValorCustoDaCentral();		
		}
		
	}

	private void informaValorCustoDaCentral(){

		LinearLayout ll_conteudoCentralComodato = (LinearLayout) ll_tituloCentralEmComodato.findViewWithTag("ll_conteudoCentralComodato");
		if(ll_conteudoCentralComodato != null){
			
			double custoCentral = procuraCustoCentralNoLLConteudoCentralComodato(ll_conteudoCentralComodato);
				
							String chave = "CENTRAL"+custoCentral;							  
		   	carrinho.removeProduto(chave);

			
		}
		
		TextView tv_custoCentral = (TextView) ll_tituloCentralEmComodato.findViewWithTag("tv_custoCentral");
		if(tv_custoCentral != null){
			
			double custoDaCentral = centralEmComodato.devolveCustoDaCentral(maoDeObra.totalMaoDeobra(), material.totalMaterial());
			
			//tv_valorUnitario.setText(""+d.replace(",","."));
			
			tv_custoCentral.setText(formataParaDuasCasasDecimais(custoDaCentral));
			
			String chave = "CENTRAL"+custoDaCentral;
			
				  			 		Produto produto2 = ListaComTodosOsProdutos.devolveProdutoOndeNomeEh("CENTRAL",context);
			  		  				 		produto2.setQuantidade(1);
			  		  				 		produto2.setValorTotal(custoDaCentral);
			  		  				 		produto2.setApelido("CENTRAL");	 	
			carrinho.adicionaProduto(chave, produto2);		
		}
		
		informaCustoTotalDoInvestimento();		
	}

	private void acaoAposCliqueRedeComodato(LinearLayout ll_conteudoRedeComodato, EditText et_metragem, TextView tv_custoRede, ArrayList<Produto> listaDiametro, Spinner spinnerDiametro){
		
		double custoRede = procuraCustoRedeNoLLConteudoRedeComodato(ll_conteudoRedeComodato);		

	  	//Log.i("tag","ultimo item escolhido ANTES: "+ultimoDiametroEscolhido);

    					String chave1 = ultimoDiametroEscolhido+custoRede;			  		   
	  	carrinho.removeProduto(chave1);

		int posicaoSelecionada = spinnerDiametro.getSelectedItemPosition();
		double totalCustoRede = calculaCustoRede(listaDiametro, posicaoSelecionada, et_metragem);	 	
		tv_custoRede.setText(""+totalCustoRede);

		String nomeDaOpcaoEscolhida = ""+spinnerDiametro.getSelectedItem();
		
		String chave2 = nomeDaOpcaoEscolhida + totalCustoRede;
		
		   				 		 Produto produto2 = ListaComTodosOsProdutos.devolveProdutoOndeNomeEh(nomeDaOpcaoEscolhida, context);
		   				 		 		 produto2.setQuantidade(devolveZeroIntCasoEstejaVazia(et_metragem.getText().toString()));
		   				 		 		 produto2.setValorTotal(totalCustoRede);
		   				 		 		 produto2.setApelido("REDE");	 	
	  	carrinho.adicionaProduto(chave2, produto2);
		
	  	ultimoDiametroEscolhido = nomeDaOpcaoEscolhida;
	  	//Log.i("tag","ultimo item escolhido DEPOIS: "+ultimoDiametroEscolhido);
	  	
	  	
		informaValorCustoDaCentral();
	}

	private void adicionaTvConteudoAuxiliar(LinearLayout ll_conteudo1, Item item, double valorTotalDeUmProduto, String apelido){
		
		TextView tv_temp = criaTvConteudo(item.getItemEscolhido() + 
									    ";"+item.getQuantidadeEscolhida() +
									    ";"+item.quantidadAdicional() + 
									    ";"+item.maoDeObrCustoInstalacao1() + 
									    ";"+item.maoDeObrCustoInstalacaoAdicional() + 
									    ";"+item.maoDeObrCustoTotalAdicional() + 
									    ";"+item.materiaCustoInstalacao1() + 
									    ";"+item.materiaCustoInstalacaoAdicional() + 
									    ";"+item.materiaCustoTotalAdicional()
										);
		
		tv_temp.setTag("tv_temp");
		tv_temp.setTextColor(Color.GREEN);
		
		String temp = tv_temp.getText().toString();

		String[] listaComValores = temp.split(";");

		double maoDeObraCustoUmaQTD = devolveZeroDoubleCasoEstejaVazia(listaComValores[3]);
		double maoDeObraTotalAdicional = devolveZeroDoubleCasoEstejaVazia(listaComValores[5]);
		double materialCustoUmaQTD = devolveZeroDoubleCasoEstejaVazia(listaComValores[6]);		
		double materialTotalAdicional = devolveZeroDoubleCasoEstejaVazia(listaComValores[8]);
				
		maoDeObra.adicionaSubTotalCusto1(maoDeObraCustoUmaQTD);
		maoDeObra.adicionaSubTotalCustoAdicional(maoDeObraTotalAdicional);

		material.adicionaSubTotalCusto1(materialCustoUmaQTD);
		material.adicionaSubTotalCustoAdicional(materialTotalAdicional);
		
			String chave = listaComValores[0]+valorTotalDeUmProduto;
			
								Produto produto = ListaComTodosOsProdutos.devolveProdutoOndeNomeEh(listaComValores[0], context);
										produto.setQuantidade(devolveZeroIntCasoEstejaVazia(listaComValores[1]));
										produto.setValorTotal(valorTotalDeUmProduto);
									    produto.setApelido(apelido);	 	
		carrinho.adicionaProduto(chave, produto);
		
		informaValorDaMaoDeObra();
		informaValorDoMaterial();
		informaValorCustoDaCentral();
		
		
		//tv_temp.setVisibility(View.GONE);
		
	ll_conteudo1.addView(tv_temp);
	}

	private void informaCustoTotalDoInvestimento(){
		TextView tv_custoTotalDoInvestimento = (TextView) ll_custoTotalDoInvestimento.findViewWithTag("tv_custoTotalDoInvestimento");
		if(tv_custoTotalDoInvestimento != null){	
			
	    	zeraOCampoCustoAdicional();
			
			tv_custoTotalDoInvestimento.setText(formataParaDuasCasasDecimais(carrinho.devolveCustoTotal()));	
		}		
	}
	
	private void informaValorDaMaoDeObra(){

		TextView tv_maoDeObra = (TextView) ll_tituloCentralEmComodato.findViewWithTag("tv_maoDeObra");

		if(tv_maoDeObra != null){
			
			//tv_valorUnitario.setText(""+d.replace(",","."));
			
			tv_maoDeObra.setText(formataParaDuasCasasDecimais(maoDeObra.totalMaoDeobra()));
		}
	}
	
	private void informaValorDoMaterial(){

		TextView tv_material = (TextView) ll_tituloCentralEmComodato.findViewWithTag("tv_material");

		if(tv_material != null){
			
			//tv_valorUnitario.setText(""+d.replace(",","."));
			
			tv_material.setText(formataParaDuasCasasDecimais(material.totalMaterial()));
		}
	}
	
	private String formataParaDuasCasasDecimais(double valor) {

		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		
		String numeroFormatado = decimalFormat.format(Double.parseDouble(String.valueOf(valor)));
		
		String valorUtilizar = numeroFormatado.replace(",",".");

		return valorUtilizar;
	}

}

