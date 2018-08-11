package mobile.contratodigital.util;

import android.content.Context;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.model.ContratoUtil;
import mobile.contratodigital.ws.ExportaArquivosWS;
import sharedlib.contratodigital.model.Movimento;

public class AcaoExportarArquivosEDados implements AcaoAlertDialog{

	private Movimento movimento;
	private Dao dao;
	private Context context;
	private String URLescolhida;
	
	public AcaoExportarArquivosEDados(Movimento movimento, Dao dao, Context context, String URLescolhida) {
		this.movimento = movimento;
		this.dao = dao;
		this.context = context;
		this.URLescolhida = URLescolhida;
	}
	
	@Override
	public void fazAcaoSIM(Context context) {
		
		acaoAposCliqueNoBotaoExportar(movimento);
	}

	@Override
	public void fazAcaoNAO(Context context) {
		// TODO Auto-generated method stub		
	}

	private void acaoAposCliqueNoBotaoExportar(Movimento movimento) {
		
		ContratoUtil contratoUtil = new ContratoUtil(dao, context);
				
		if(contratoUtil.naoTemNumeroDeContrato(movimento.getNr_visita())){
			
			new MeuAlerta("Cliente não possui contrato gerado.", null, context).meuAlertaOk();
		}
		else {
			if(contratoUtil.preencheu2LayoutsObrigatoriosAntesDeExportar(movimento.getNr_visita())) {
								
				String diretorioDoCliente = contratoUtil.devolveDiretorioAserUtilizado(movimento.getNr_visita());
				
				String pastaDoCliente = "_"+movimento.getNr_contrato()+"/";
				
				Movimento mov_dadosCadastro = (Movimento) dao.devolveObjeto(Movimento.class,
															Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.DADOS_CADASTRO.getNumero(),
															Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita());
													
				contratoUtil.addNumeroDeContratoEAtualizaMovimento(mov_dadosCadastro, movimento.getNr_contrato());

				Movimento mov_consumoCliente = (Movimento) dao.devolveObjeto(Movimento.class,
															Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.CONSUMO_CLIENTE.getNumero(),
															Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita());

				contratoUtil.addNumeroDeContratoEAtualizaMovimento(mov_consumoCliente, movimento.getNr_contrato());

				ExportaArquivosWS exportaArquivosWS = new ExportaArquivosWS(context, URLescolhida);
								  exportaArquivosWS.exportar(movimento.getNr_visita(), diretorioDoCliente, pastaDoCliente, movimento);
			}
		}
	}

}
