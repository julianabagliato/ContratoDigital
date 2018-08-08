package mobile.contratodigital.model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;
import mobile.contratodigital.dao.Dao;
import mobile.contratodigital.enums.NomeLayout;
import mobile.contratodigital.enums.TipoView;
import mobile.contratodigital.util.MeuAlerta;
import sharedlib.contratodigital.model.Layout;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.util.Generico;

public class ContratoUtil {

	private Dao dao;
	private Context context;
	
	public ContratoUtil(Dao dao, Context context) {
		
		this.dao = dao;
		this.context = context;
	}
	
	public boolean naoTemNumeroDeContrato(int nrVisita) {
		
		Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
																		 Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
																		 Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita);
		
		if (mov_informacoesCliente == null || mov_informacoesCliente.getNr_contrato().trim().equals("")) {
			return true;
		}else {
			return false;
		}
	}

	public boolean preencheu2LayoutsObrigatoriosAntesDeExportar(int nrVisita) {
		
		int movimentosEncontrados = 0;
								
		Movimento mov_dadosCadastro = (Movimento) dao.devolveObjeto(Movimento.class,
															Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.DADOS_CADASTRO.getNumero(),
															Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita);

		Movimento mov_consumoCliente = (Movimento) dao.devolveObjeto(Movimento.class,
															Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.CONSUMO_CLIENTE.getNumero(),
															Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita);
			
		if (mov_dadosCadastro == null) {
					
			avisaQformularioObrigatorioNaoFoiPreenchido(NomeLayout.DADOS_CADASTRO.getNumero());
		}else {
			movimentosEncontrados++;
		}
		
		if (mov_consumoCliente == null) {
			
			avisaQformularioObrigatorioNaoFoiPreenchido(NomeLayout.CONSUMO_CLIENTE.getNumero());
		}else {
			movimentosEncontrados++;
		}
		
		if(movimentosEncontrados == 2){
			
			return true;
		}else {
			return false;
		}
	}
		
	public boolean layoutsObrigatoriosForamPreenchidos(List<Layout> listaComlayouts_Obrigatorios, Movimento movimento1) {

		int movimentosEncontrados = 0;
		
		for (Layout layout_obrigatorio : listaComlayouts_Obrigatorios) {
			
			if (layout_obrigatorio != null) {
				
				if(NomeLayout.DADOS_CADASTRO.getNumero() != layout_obrigatorio.getNr_layout() 
						&& NomeLayout.CONSUMO_CLIENTE.getNumero() != layout_obrigatorio.getNr_layout()) {
					

					Movimento mov_obrigatorio = (Movimento) dao.devolveObjeto(Movimento.class,
																		  Movimento.COLUMN_INTEGER_NR_LAYOUT, layout_obrigatorio.getNr_layout(),
																		  Movimento.COLUMN_INTEGER_NR_VISITA, movimento1.getNr_visita());
			
					if (mov_obrigatorio == null) {
					
						avisaQformularioObrigatorioNaoFoiPreenchido(layout_obrigatorio.getNr_layout());
						break;
					}
					else {
						if(layout_obrigatorio.getNr_layout() == NomeLayout.REPRESENTACAO.getNumero()) {
							
							if(mov_obrigatorio.getInformacao_1().isEmpty()) {
								
								avisaQformularioObrigatorioNaoFoiPreenchido(layout_obrigatorio.getNr_layout());
								break;
							}
							else {
								movimentosEncontrados++;
							}
							
						}else {							
							movimentosEncontrados++;
						}
					} 
				}	
			}
			else {
				new MeuAlerta("Não foi encontrado Layout obrigatório", null, context).meuAlertaOk();
			}
		}
		
		//Estou somando 2 porque foi adicionado 2 execoes no [if] [INFORMAÇÃO SOBRE CONSUMO DO CLIENTE] e [DADOS PARA CADASTRO]
		movimentosEncontrados = movimentosEncontrados + 2;
		
		Log.i("tag","movimentosEncontrados: "+movimentosEncontrados);
		Log.i("tag","s_Obrigatorios.size(): "+listaComlayouts_Obrigatorios.size());
		
		if(movimentosEncontrados == listaComlayouts_Obrigatorios.size()){
			
			return true;
		}else {
			return false;
		}
		
	}
	
	public void avisaQformularioObrigatorioNaoFoiPreenchido(int nrLayout) {
		
		Layout layout = (Layout) dao.devolveObjeto(Layout.class, 
												   Layout.COLUMN_INTEGER_NR_LAYOUT, nrLayout);
		
		new MeuAlerta("Formulario ("+layout.getDescricao()+") precisa ser preenchido", null, context).meuAlertaOk();	
	}

}
