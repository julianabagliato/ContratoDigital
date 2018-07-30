package mobile.contratodigital.model;

public class FormulaFinanceira {
	
	protected static double devolvePGTO(double taxa, int numeroParcelas, double valorPresente, double valorFuturo, int tipo) {
		return -taxa * (valorPresente * Math.pow(1 + taxa, numeroParcelas) + valorFuturo) / ((1 + taxa*tipo) * (Math.pow(1 + taxa, numeroParcelas) - 1));
	}
		
	protected static double devolveNPER(double taxa, double pagamentoPorMes, double valorPresente, double valorFuturo, int tipo) {
		
	  double num = pagamentoPorMes * (1 + taxa * tipo) - valorFuturo * taxa;
	  double den = (valorPresente * taxa + pagamentoPorMes * (1 + taxa * tipo));
		  
	  return Math.log(num / den) / Math.log(1 + taxa);
	}
	
	protected static double devolveVF(double taxa, int numeroParcelas, double pagamentoPorMes, double valorPresente, int tipo) {
		return -(valorPresente * Math.pow(1 + taxa, numeroParcelas) + pagamentoPorMes * (1+taxa*tipo) * (Math.pow(1 + taxa, numeroParcelas) - 1) / taxa);
	}	
}