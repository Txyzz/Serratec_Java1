package appInternetBanking;

public class Gerente extends Administracao  {

	String cargo = "Gerente";
	
	int agencia;	

	public Gerente(String tipoUsuario, long CPF, long senha, String nome, String tipoConta, int agencia) {
		super(tipoUsuario, CPF, senha, nome, tipoConta);
		this.agencia = agencia;
	}


	@Override
	public String toString() {
		return "[Cargo = " + cargo + ", CPF = " + CPF + ", nome = " + nome
				+ ", agencia=" + agencia + "]";
	}
	

	



/*
	@Override
	public void saca(double valor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deposito(double valor) {
		// TODO Auto-generated method stub
		
	}
*/
	
}