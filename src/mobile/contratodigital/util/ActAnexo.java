package mobile.contratodigital.util;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.text.InputFilter;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import mobile.contratodigital.enums.SequenciaMovAddedEmLista;
import mobile.contratodigital.enums.Tag;
import mobile.contratodigital.model.PrecoPrazoConsumoPagamento;
import sharedlib.contratodigital.model.Movimento;

public class ActAnexo {
	
	//private double subtotal = 0.0;

	public void desenhaLayoutDaTelaJahComInformacao(Context context, LinearLayout ll_principal, ActContrato contratoAct,
												    TelaBuilder telaBuilder, ArrayList<Movimento> listaComMovimentos, String tipoAnexo) {
		
		Movimento mov_enderecoEntrega = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_enderecoEntrega.getPosicao());
		Movimento mov_equipamentosSimulados = listaComMovimentos.get(SequenciaMovAddedEmLista.mov_equipamentosSimulados.getPosicao());

		// PrecoPrazoConsumoPagamento.setTituloEConteudoNasTabelasItemLayoutEMovimentoRespectivamente(mov_simulador,
		 //dao);
		
		// desenha o layout da tela jah com informacoes:
		ll_principal.addView(telaBuilder.cria_TV_titulo("\n" + "ANEXO I"));
		ll_principal.addView(telaBuilder.cria_TV_conteudo13(new TextoContratos(context).devolveTextoAnexo() + "\n"));
		ll_principal.addView(devolve_LL_holder1(telaBuilder, mov_enderecoEntrega));
		ll_principal.addView(devolve_LL_holder2(telaBuilder, mov_equipamentosSimulados, tipoAnexo));
		ll_principal.addView(devolve_LL_holder3(telaBuilder, mov_equipamentosSimulados));
		ll_principal.addView(devolve_LL_holder4(telaBuilder, mov_equipamentosSimulados));
		//ll_principal.addView(devolve_LL_holder5(telaBuilder, mov_equipamentosContratados, dao, SequenciaMovAddedEmLista.mov_equipamentosContratados.getPosicao()));
		ll_principal.addView(devolve_LL_holder5(telaBuilder, mov_equipamentosSimulados));
		ll_principal.addView(devolve_LL_holder6(telaBuilder, mov_equipamentosSimulados));
		ll_principal.addView(devolve_LL_holder7(telaBuilder));
		ll_principal.addView(contratoAct.devolve_TV_DataAtualFormatada());		 
		// desenha o layout da tela jah com informacao
		
	}

	private LinearLayout devolve_LL_holder1(TelaBuilder telaBuilder, Movimento mov_enderecoEntrega) {

		PrecoPrazoConsumoPagamento.setCep_titulo("CEP: ");
		PrecoPrazoConsumoPagamento.setCep_conteudo(mov_enderecoEntrega.getInformacao_1());
		
		PrecoPrazoConsumoPagamento.setLogradouro_titulo("Endereço de entrega: ");
		PrecoPrazoConsumoPagamento.setLogradouro_conteudo(mov_enderecoEntrega.getInformacao_2());

		PrecoPrazoConsumoPagamento.setNumero_titulo("Número: ");
		PrecoPrazoConsumoPagamento.setNumero_conteudo(mov_enderecoEntrega.getInformacao_3());

		PrecoPrazoConsumoPagamento.setBairro_titulo("Bairro: ");
		PrecoPrazoConsumoPagamento.setBairro_conteudo(mov_enderecoEntrega.getInformacao_4());


		PrecoPrazoConsumoPagamento.setEstado_titulo("Estado: ");
		PrecoPrazoConsumoPagamento.setEstado_conteudo(mov_enderecoEntrega.getInformacao_5());

		PrecoPrazoConsumoPagamento.setCidade_titulo("Cidade: ");
		PrecoPrazoConsumoPagamento.setCidade_conteudo(mov_enderecoEntrega.getInformacao_6());


		LinearLayout ll_holder1 = telaBuilder.cria_LL_comBordaArredondada();
		ll_holder1.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo(PrecoPrazoConsumoPagamento.getLogradouro_titulo(),
				PrecoPrazoConsumoPagamento.getLogradouro_conteudo()));
		ll_holder1.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo(PrecoPrazoConsumoPagamento.getNumero_titulo(),
				PrecoPrazoConsumoPagamento.getNumero_conteudo()));
		ll_holder1.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo(PrecoPrazoConsumoPagamento.getBairro_titulo(),
				PrecoPrazoConsumoPagamento.getBairro_conteudo()));
		ll_holder1.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo(PrecoPrazoConsumoPagamento.getCidade_titulo(),
				PrecoPrazoConsumoPagamento.getCidade_conteudo()));
		ll_holder1.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo(PrecoPrazoConsumoPagamento.getEstado_titulo(),
				PrecoPrazoConsumoPagamento.getEstado_conteudo()));
		ll_holder1.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo(PrecoPrazoConsumoPagamento.getCep_titulo(),
				PrecoPrazoConsumoPagamento.getCep_conteudo()));

		return ll_holder1;
	}

	private LinearLayout devolve_LL_holder2(TelaBuilder telaBuilder, Movimento mov_equipamentosSimulados, String tipoAnexo) {

		LinearLayout ll_holder2 = telaBuilder.cria_LL_comBordaArredondada();

		PrecoPrazoConsumoPagamento.setPrecoNegociado(Reflexao.procuraEdevolveConteudoDoMovimentoBaseadoEmTexto(mov_equipamentosSimulados, "Preço"));
		ll_holder2.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo("", PrecoPrazoConsumoPagamento.getPrecoNegociado()));

		if (tipoAnexo.equals("AnexoContaSIM")) {

			PrecoPrazoConsumoPagamento.setTaxaServico(
					Reflexao.procuraEdevolveConteudoDoMovimentoBaseadoEmTexto(mov_equipamentosSimulados, "serviço"));
			PrecoPrazoConsumoPagamento.setTaxaReligue(
					Reflexao.procuraEdevolveConteudoDoMovimentoBaseadoEmTexto(mov_equipamentosSimulados, "religação"));

			ll_holder2
					.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo("", PrecoPrazoConsumoPagamento.getTaxaServico()));
			ll_holder2
					.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo("", PrecoPrazoConsumoPagamento.getTaxaReligue()));
		}

		return ll_holder2;
	}

	private LinearLayout devolve_LL_holder3(TelaBuilder telaBuilder, Movimento mov_equipamentosSimulados) {

		LinearLayout ll_holder3 = telaBuilder.cria_LL_comBordaArredondada();

		PrecoPrazoConsumoPagamento
				.setPrazoVigencia(Reflexao.procuraEdevolveConteudoDoMovimentoBaseadoEmTexto(mov_equipamentosSimulados, "Prazo"));
		PrecoPrazoConsumoPagamento.setDataInicio("Início: " + new FormaterDatePicker().devolveDataAtualComBarras());
		PrecoPrazoConsumoPagamento.setPrazoPagamento(
				Reflexao.procuraEdevolveConteudoDoMovimentoBaseadoEmTexto(mov_equipamentosSimulados, "pagamento"));

		ll_holder3.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo("", PrecoPrazoConsumoPagamento.getPrazoVigencia()));
		ll_holder3.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo("", PrecoPrazoConsumoPagamento.getDataInicio()));
		ll_holder3.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo("", PrecoPrazoConsumoPagamento.getPrazoPagamento()));

		return ll_holder3;
	}

	private LinearLayout devolve_LL_holder4(TelaBuilder telaBuilder, Movimento mov_equipamentosSimulados) {

		PrecoPrazoConsumoPagamento.setConsumoPrevistoEmKg(
				Reflexao.procuraEdevolveConteudoDoMovimentoBaseadoEmTexto(mov_equipamentosSimulados, "Consumo"));
		PrecoPrazoConsumoPagamento.setPrevConsumoAteFimContrato(
				Reflexao.procuraEdevolveConteudoDoMovimentoBaseadoEmTexto(mov_equipamentosSimulados, "Previsão"));

		LinearLayout ll_holder4 = telaBuilder.cria_LL_comBordaArredondada();
		ll_holder4.addView(
				telaBuilder.cria_LL_TVtitulo_TVconteudo("", PrecoPrazoConsumoPagamento.getConsumoPrevistoEmKg()));
		ll_holder4.addView(
				telaBuilder.cria_LL_TVtitulo_TVconteudo("", PrecoPrazoConsumoPagamento.getPrevConsumoAteFimContrato()));
		return ll_holder4;
	}

	
	private LinearLayout devolve_LL_holder5(TelaBuilder telaBuilder, Movimento mov_equipamentosSimulados) {
		
		List<String> listaTanques = new ArrayList<String>();
		List<String> listaCilindros = new ArrayList<String>();
		List<String> listaEquipamentos = new ArrayList<String>();
		List<String> listaCentral = new ArrayList<String>();
		List<String> listaRede = new ArrayList<String>();
		List<String> listaOutros = new ArrayList<String>();
		List<String> listaInvestimentosEspeciais = new ArrayList<String>();
		 
		//INICIO: ordenaItensAntesDeAdicionarNaView();
		for(String equipamento : Reflexao.devolveListaComEquipamentosAdicionados(mov_equipamentosSimulados)){
			
			if(equipamento.contains("TANQUES")) {
				
				listaTanques.add(equipamento);
			}
			else if(equipamento.contains("CILINDROS")) {
				
				listaCilindros.add(equipamento);
			}
			else if(equipamento.contains("EQUIPAMENTOS")) {
		
				listaEquipamentos.add(equipamento);
			}
			else if(equipamento.contains("CENTRAL")) {
				
				listaCentral.add(equipamento);
			}
			else if(equipamento.contains("REDE")) {
				
				listaRede.add(equipamento);
			}
			else if(equipamento.contains("investimentos especiais")) {
				
				listaInvestimentosEspeciais.add(equipamento);
			}	
			else {
				listaOutros.add(equipamento);		
			}
		}
		//FIM ordenaItensAntesDeAdicionarNaView
		
		LinearLayout ll_holder5 = telaBuilder.cria_LL_comBordaArredondada();
		ll_holder5.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo("Equipamentos objeto de comodato", ":"));	
		
		double subtotalTanques = adicionaItensNaActivityConformeListaRecebida(listaTanques, ll_holder5, telaBuilder);
		double subtotalCilindros = adicionaItensNaActivityConformeListaRecebida(listaCilindros, ll_holder5, telaBuilder);
		double subtotalEquipamentos = adicionaItensNaActivityConformeListaRecebida(listaEquipamentos, ll_holder5, telaBuilder);

		double subtotal1 = subtotalTanques + subtotalCilindros + subtotalEquipamentos;
				
		MoedaRS moedaRS = new MoedaRS();
		
		String subtotal1EmExtenso = moedaRS.converteNumeroParaExtensoReais(subtotal1);

		ll_holder5.addView(telaBuilder.cria_LL_LLTVtitulo_LLTVconteudo("", ""));
		ll_holder5.addView(telaBuilder.cria_LL_LLTVtitulo_LLTVconteudo(" Subtotal A:", " "+subtotal1EmExtenso));
		ll_holder5.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo("", ""));

		
		//INICIO: a tratativa abaixo é apenas por causa dos equipamentos especiais

		String eqpEspecial = listaInvestimentosEspeciais.get(0);
		
		int posicaoDivisoria = eqpEspecial.indexOf(":");
		
		String titulo = eqpEspecial.substring(0, posicaoDivisoria + 1);
		String linhaComItemEValor = eqpEspecial.substring(posicaoDivisoria + 1, eqpEspecial.length());
		
		ll_holder5.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo(""+titulo, ""));
	
		double subtotalCentral = adicionaItensNaActivityConformeListaRecebida(listaCentral, ll_holder5, telaBuilder);
		double subtotalRede = adicionaItensNaActivityConformeListaRecebida(listaRede, ll_holder5, telaBuilder);
		double valorDoEquipamentoEspecial = separaItemDeValorEAdicionaNoLinearLayout(linhaComItemEValor, ll_holder5, telaBuilder);		
		
		double subtotal2 = subtotalCentral + subtotalRede + valorDoEquipamentoEspecial;
		
		String subtotal2EmExtenso = moedaRS.converteNumeroParaExtensoReais(subtotal2);

		ll_holder5.addView(telaBuilder.cria_LL_LLTVtitulo_LLTVconteudo("", ""));
		ll_holder5.addView(telaBuilder.cria_LL_LLTVtitulo_LLTVconteudo(" Subtotal B:", " "+subtotal2EmExtenso));
		ll_holder5.addView(telaBuilder.cria_LL_TVtitulo_TVconteudo("", ""));

		
		
		//FIM:   a tratativa abaixo é apenas por causa dos equipamentos especiais
		
		//INICIO: a lista <OUTROS> apenas serve para caso sobre alguma sujeira nao planejada
		adicionaItensNaActivityConformeListaRecebida(listaOutros, ll_holder5, telaBuilder);
		//FIM:    a lista <OUTROS> apenas serve para caso sobre alguma sujeira nao planejada
			
		return ll_holder5;
			
	}

	private double adicionaItensNaActivityConformeListaRecebida(List<String> lista, LinearLayout ll_holder5, TelaBuilder telaBuilder) {
		
		double subtotal = 0.0;
		
		for(String linhaComItemEValor : lista) {

			double valorDoItem = separaItemDeValorEAdicionaNoLinearLayout(linhaComItemEValor, ll_holder5, telaBuilder);		
			
			subtotal = subtotal + valorDoItem;
		}

		return subtotal;
	}
	
	private double separaItemDeValorEAdicionaNoLinearLayout(String linhaComItemEValor, LinearLayout ll_holder, TelaBuilder telaBuilder) {
		
		double valorDoItem = 0.0;
		
		int posicaoDaDivisoria = linhaComItemEValor.indexOf("R$");
		
		String item = linhaComItemEValor.substring(0, posicaoDaDivisoria);
		String valor = linhaComItemEValor.substring(posicaoDaDivisoria - 1, linhaComItemEValor.length());

		try {
					
			int posicaoDosCentavos = valor.indexOf("(");
			
			String numerosComPontoVirgulaRS = valor.substring(0, posicaoDosCentavos);
			
			String numerosComPontoVirgula = numerosComPontoVirgulaRS.replace("R$", "");
			
			String numerosComVirgula = numerosComPontoVirgula.replace(".", "");
			
			String valorProntoParaUso = numerosComVirgula.replace(",", ".");
			
			valorDoItem = Double.parseDouble(valorProntoParaUso);	
		}
		catch(Exception erro) {

			Log.i("tag","nao conseguiu calcular o sub total: "+erro);			
		}
		
		ll_holder.addView(telaBuilder.cria_LL_LLTVtitulo_LLTVconteudo(" "+item, " "+valor));
		
		return valorDoItem;
	}
	
	private LinearLayout devolve_LL_holder6(TelaBuilder telaBuilder, Movimento mov_equipamentosSimulados) {

		PrecoPrazoConsumoPagamento.setCustoTotalInvestimento(Reflexao.procuraEdevolveConteudoDoMovimentoBaseadoEmTexto(mov_equipamentosSimulados, "Custo total"));

		LinearLayout ll_holder6 = telaBuilder.cria_LL_comBordaArredondada();
					 
		String linhaComItemEValor = PrecoPrazoConsumoPagamento.getCustoTotalInvestimento();

		separaItemDeValorEAdicionaNoLinearLayout(linhaComItemEValor, ll_holder6, telaBuilder);
					 
		return ll_holder6;
	}

	private LinearLayout devolve_LL_holder7(TelaBuilder telaBuilder) {

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		PrecoPrazoConsumoPagamento.setObservacoes_titulo("Observações: ");

		LinearLayout ll_holder7 = telaBuilder.cria_LL_comBordaArredondada();
		ll_holder7.addView(
				telaBuilder.cria_LL_TVtitulo_ETconteudo(PrecoPrazoConsumoPagamento.getObservacoes_titulo(), params));

		return ll_holder7;
	}
	
	public void procura_etObservacaoEgravaConteudoInformadoPelousuario(LinearLayout ll_principal) {

		EditText et_observacao = (EditText) ll_principal.findViewWithTag(Tag.observacao);
				 et_observacao.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
				 
		PrecoPrazoConsumoPagamento.setObservacoes_conteudo(et_observacao.getText().toString());
	}

}
