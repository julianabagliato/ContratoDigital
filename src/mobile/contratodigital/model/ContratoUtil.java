package mobile.contratodigital.model;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import mobile.contratodigital.util.TrabalhaComArquivos;
import mobile.contratodigital.util.TrabalhaComFotos;
import mobile.contratodigital.view.ArrayAdapterCliente;
import sharedlib.contratodigital.model.Layout;
import sharedlib.contratodigital.model.Movimento;
import sharedlib.contratodigital.model.Representante;
import sharedlib.contratodigital.util.Generico;

public class ContratoUtil {

	private Dao dao;
	private Context context;
	
	public ContratoUtil(Dao dao, Context context) {
		
		this.dao = dao;
		this.context = context;
	}
	
	public void deletaCliente(Movimento movimento, List<Movimento> listaComMovimentos, ArrayAdapterCliente adapterCliente) {
		
		//ContratoUtil contratoUtil = new ContratoUtil(dao, context);
		
		String srcContrato = this.devolveDiretorioAserUtilizado(movimento.getNr_visita());
		
		TrabalhaComArquivos trabalhaComArquivos = new TrabalhaComArquivos();
							trabalhaComArquivos.removeDiretorioDoCliente(context, srcContrato);
							
		listaComMovimentos.remove(movimento);
							
		adapterCliente.notifyDataSetChanged();	
							
		dao.deletaObjeto(Movimento.class, Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita());
	}

	
	public String criaNumeroDeContrato() {

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String dataFormatada = dateFormat.format(new Date());

		Representante representante = (Representante) dao.devolveObjeto(Representante.class);

		return dataFormatada + representante.getCod_rep();
	}

	public void addNumeroDeContratoEAtualizaMovimento(Movimento movimento, String numeroContrato) {
		
		if(movimento != null) {
			
			movimento.setNr_contrato(numeroContrato);
			
			dao.insereOUatualiza(movimento,
								 Movimento.COLUMN_INTEGER_NR_LAYOUT, movimento.getNr_layout(), 
								 Movimento.COLUMN_INTEGER_NR_VISITA, movimento.getNr_visita());
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
			return false;
		}else {
			movimentosEncontrados++;
		}
		
		if (mov_consumoCliente == null) {
			
			avisaQformularioObrigatorioNaoFoiPreenchido(NomeLayout.CONSUMO_CLIENTE.getNumero());
			return false;
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
				new MeuAlerta("N�o foi encontrado Layout obrigat�rio", null, context).meuAlertaOk();
			}
		}
		
		//Estou somando 2 porque foi adicionado 2 execoes no [if] [INFORMA��O SOBRE CONSUMO DO CLIENTE] e [DADOS PARA CADASTRO]
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

	public String devolveDiretorioAserUtilizado(int nrVisita) {

		Movimento mov_informacoesCliente = (Movimento) dao.devolveObjeto(Movimento.class,
																		Movimento.COLUMN_INTEGER_NR_LAYOUT, NomeLayout.INFORMACOES_CLIENTE.getNumero(),
																		Movimento.COLUMN_INTEGER_NR_VISITA, nrVisita);

		String srcContrato = "";
		
		if (naoTemNumeroDeContrato(nrVisita)) {
			
			srcContrato = usaRazaoSocialComCPF_CNPJ(mov_informacoesCliente.getInformacao_1(), mov_informacoesCliente.getInformacao_4());	
		} else {	
			srcContrato = usaNumeroDeContrato(mov_informacoesCliente.getNr_contrato());
		}
		
		return srcContrato;
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

	public String usaRazaoSocialComCPF_CNPJ(String info_1, String info_4) {
		
		return Environment.getExternalStorageDirectory()+"/ContratoDigital/"+info_1+"_"+info_4.replace("/", "-")+"/";
	}
	
	private String usaNumeroDeContrato(String nrContrato) {
		
		return Environment.getExternalStorageDirectory() + "/ContratoDigital/"+"_"+nrContrato+"/";
	}
		
}
