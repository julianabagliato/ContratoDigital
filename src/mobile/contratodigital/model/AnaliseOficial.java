package mobile.contratodigital.model;
	
public class AnaliseOficial {

	private double precoDoGLPcolocadoNaCONSIGAZ; 
	private double custoOpercional; 
	private double taxa; 
	
	public AnaliseOficial(double precoDoGLPcolocadoNaCONSIGAZ, double custoOpercional, double taxa) {

		this.precoDoGLPcolocadoNaCONSIGAZ = precoDoGLPcolocadoNaCONSIGAZ;
		this.custoOpercional = custoOpercional;
		this.taxa = taxa;
	}

	private double devolveCustoTotalDoGLPporKg(){
		
		return precoDoGLPcolocadoNaCONSIGAZ + custoOpercional;	
	}
	
	public double devolvePGTOpersonalizado(int nper, double vp, double consumoPrevistoKg){
		
		double  resultadoPGTO = FormulaFinanceira.devolvePGTO(taxa, nper, -vp, 0, 0);
		double totalParcial = resultadoPGTO / consumoPrevistoKg;
		double totalFinal = totalParcial + devolveCustoTotalDoGLPporKg();

		return totalFinal;
	}

	public double devolveNPERpersonalizado(double vp, double precoNegociadoPorKG, double consumoPrevistoKg){
	
		double pgto = - (precoNegociadoPorKG - devolveCustoTotalDoGLPporKg()) * consumoPrevistoKg;
		
		double  resultadoNPER = FormulaFinanceira.devolveNPER(taxa, pgto, vp, 0, 0);
		
		return resultadoNPER;
	}
	
	public double devolveVFpersonalizado(int nper, double vp, double precoNegociadoPorKG, double consumoPrevistoKg){

		double pgto = - (precoNegociadoPorKG - devolveCustoTotalDoGLPporKg()) * consumoPrevistoKg;

		double resultadoValorFuturo = FormulaFinanceira.devolveVF(taxa, nper, pgto, vp, 0);
		
		return resultadoValorFuturo;
	}

}
