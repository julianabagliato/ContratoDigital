package mobile.contratodigital.model;

import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import sharedlib.contratodigital.model.Movimento;

public class ContratoUtil {

	private Dao dao;
	private int nrVisita;
	
	public ContratoUtil(Dao dao, int nrVisita) {
		
		this.dao = dao;
		this.nrVisita = nrVisita;
	}
	
	public boolean naoTemNumeroDeContrato() {
		
		Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
																		 Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
																		 Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita);
		
		if (mov_informacoesCliente == null || mov_informacoesCliente.getNr_contrato().trim().equals("")) {
			return true;
		}else {
			return false;
		}
	}

}
