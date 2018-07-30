package mobile.contratodigital.model;

public class AnaliseGerencial {

	private double precoDoGLPcolocadoNaCONSIGAZ;
	private double custoOpercional;
	private double taxa;

	public AnaliseGerencial(double precoDoGLPcolocadoNaCONSIGAZ, double custoOpercional, double taxa) {

		this.precoDoGLPcolocadoNaCONSIGAZ = precoDoGLPcolocadoNaCONSIGAZ;
		this.custoOpercional = custoOpercional;
		this.taxa = taxa;
	}
	
	private double devolveCustoTotalDoGLPporKg(){
		
		return precoDoGLPcolocadoNaCONSIGAZ + custoOpercional;		
	}
	
	public double devolvePGTOpersonalizado(int nper, double vp, double consumoPrevistoKg){
		
		double valorPresente = (-vp * 0.66);
		double resultadoPGTO = FormulaFinanceira.devolvePGTO(taxa, nper, valorPresente, 0, 0);
		double totalParcial = resultadoPGTO / consumoPrevistoKg;
		double totalFinal = totalParcial + devolveCustoTotalDoGLPporKg();

		return totalFinal;
	}

	public double devolveNPERpersonalizado(double vp, double precoNegociadoPorKG, double consumoPrevistoKg){
	
		double pgto = - (precoNegociadoPorKG - devolveCustoTotalDoGLPporKg()) * consumoPrevistoKg;
		double valorPresente = (vp * 0.66);
		double resultadoNPER = FormulaFinanceira.devolveNPER(taxa, pgto, valorPresente, 0, 0);
		
		return resultadoNPER;
	}
	
	public double devolveVFpersonalizado(int nper, double vp, double precoNegociadoPorKG, double consumoPrevistoKg){

		double pgto = - (precoNegociadoPorKG - devolveCustoTotalDoGLPporKg()) * consumoPrevistoKg;
		double valorPresente = (vp * 0.66);		
		double resultadoValorFuturo = FormulaFinanceira.devolveVF(taxa, nper, pgto, valorPresente, 0);
		
		return resultadoValorFuturo;
	}

}
