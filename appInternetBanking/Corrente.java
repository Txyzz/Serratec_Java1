package appInternetBanking;

public class Corrente extends Conta {		
	

	
	

	public Corrente(String TipoConta, long CPFDoTitular, String nome, String tipoUsuario, int numeroDaConta,
			int agencia, double saldo, double tarifacao) {
		super(TipoConta, CPFDoTitular, nome, tipoUsuario, numeroDaConta, agencia, saldo, tarifacao);
		// TODO Auto-generated constructor stub
	}



	@Override
	public String toString() {
		return " [" + (TipoConta != null ? "TipoConta=" + TipoConta + ", " : "") + "CPFDoTitular="
				+ CPFDoTitular + ", " + (tipoUsuario != null ? "tipoUsuario=" + tipoUsuario + ", " : "")
				+ "numeroDaConta=" + numeroDaConta + ", agencia=" + agencia + ", saldo=" + saldo + ", tarifacao="
				+ tarifacao + "]";
	}



	public double getTarifacao() {
		return tarifacao;
	}

	public void setTarifacao(double tarifacao) {
		this.tarifacao = tarifacao;
	}

	
	
	
	/*

	@Override
	public void saque(Movimentacoes c, double valor) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void transferencia(Conta c, double valor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deposito(double valor) {
		// TODO Auto-generated method stub
		
	}

	// verifica se valor é positivo
		public boolean verificaValor(double valor) {
			if (valor > 0.0) {
				return true;
			}
			else {
				return false;
			}
		}

		@Override
		public double getSaldo() {
			return this.saldo;
		}

	*/
	
	

}
