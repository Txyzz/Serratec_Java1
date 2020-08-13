package appInternetBanking;

public class Cliente extends Usuario   {

	

	public Cliente(String tipoUsuario, long CPF, long senha, String nome, String TipoConta) {
		super(tipoUsuario, CPF, senha, nome, TipoConta);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Cliente [" + (tipoUsuario != null ? "tipoUsuario=" + tipoUsuario + ", " : "") + "CPF=" + CPF
				+ ", senha=" + senha + ", " + (nome != null ? "nome=" + nome + ", " : "")
				+ (tipoConta != null ? "tipoConta=" + tipoConta : "") + "]";
	}

	

}
