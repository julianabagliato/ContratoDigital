package mobile.contratodigital.util;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Reflexao {

	public static String procuraEdevolveConteudoDoMovimentoBaseadoEmTexto(Object mov_equipamentosSimulados, String textoProcurado) {
		
		try {
			Class<?> classe = mov_equipamentosSimulados.getClass();
			
			for (Field atributo : classe.getDeclaredFields()) {

				atributo.setAccessible(true);

				if (atributo.getName().contains("informacao_")) {
					
					String conteudo = (String) atributo.get(mov_equipamentosSimulados);

					if(conteudo.contains(textoProcurado)){
						
						return conteudo;
					}					
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return "nao encontrou :(";
	}

	public static ArrayList<String> devolveListaComEquipamentosAdicionados(Object mov_equipamentosContratados_OU_mov_equipamentosSimulados) {
		
		ArrayList<String> lista = new ArrayList<String>();
		
		try {
			Class<?> classe = mov_equipamentosContratados_OU_mov_equipamentosSimulados.getClass();
			
			for (Field atributo : classe.getDeclaredFields()) {

				atributo.setAccessible(true);

				if (atributo.getName().contains("informacao_")) {
					
					String conteudo = (String) atributo.get(mov_equipamentosContratados_OU_mov_equipamentosSimulados);
					
					if(!conteudo.isEmpty()){
					
						if(!conteudo.contains("Preço") && 
								!conteudo.contains("Prazo") && 
									!conteudo.contains("Consumo") && 
										!conteudo.contains("pagamento") &&
											!conteudo.contains("Previsão") &&
												!conteudo.contains("serviço") &&
													!conteudo.contains("religação") &&
														!conteudo.contains("Custo total")){
								
							lista.add(conteudo);
					 	}			
					}			
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

}
