package appInternetBanking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public abstract class Conta {

	String TipoConta;
	long CPFDoTitular;
	String nome;
	String tipoUsuario;
	int numeroDaConta;
	int agencia;
	double saldo;
	public double tarifacao;
	DecimalFormat df = new DecimalFormat("#.##");

	Conta(String TipoConta, long CPFDoTitular, String nome, String tipoUsuario, int numeroDaConta, int agencia,
			double saldo, double tarifacao) {
		this.TipoConta = TipoConta;
		this.CPFDoTitular = CPFDoTitular;
		this.nome = nome;
		this.tipoUsuario = tipoUsuario;
		this.numeroDaConta = numeroDaConta;
		this.agencia = agencia;
		this.saldo = saldo;
		this.tarifacao = tarifacao;
	}

	@Override
	public String toString() {
		return "[TipoConta=" + TipoConta + ", CPFDoTitular=" + CPFDoTitular + ", numeroDaConta=" + numeroDaConta
				+ ", agencia=" + agencia + ", saldo=" + saldo + "]";
	}

	public static void movimentacoes(long c, Conta logada, Map<Long, Conta> mapContas,
			Map<Integer, Conta> mapContasNumeroConta) {
		boolean sair = false;

		do {
			// Menu inicial
			System.out.println(
					"Escolha a opera��o desejada:" + "\n1- Saque\n2- Dep�sito\n3- Transfer�ncia\n4- Voltar ");
			Scanner scan = new Scanner(System.in);
			int op = scan.nextInt();

			switch (op) {

			// para saque
			case 1:
				System.out.println("Digite o Valor do saque:");
				double valorASacar = new Scanner(System.in).nextDouble();
				// saque(valorASacar);

				if (verificaValor(valorASacar) && valorASacar <= logada.getSaldo()
						&& logada.getTipoConta().equals("Corrente")) {
					saque(valorASacar, logada);
					logada.saldo = logada.saldo - 0.1;
					logada.tarifacao += 0.1;
					System.out.println(appUtil.formataMoeda(logada.tarifacao));

				} else if (verificaValor(valorASacar) && valorASacar <= logada.getSaldo()
						&& logada.getTipoConta().equals("Poupanca")) {
					saque(valorASacar, logada);
				}

				else {
					System.out.println("N�o foi poss�vel realizar a opera��o. Digite um valor v�lido.\n");
				}

				// realizar outra operação ou sair
				if (verificaSairDoSegundoMenu()) {
					System.out.println("Obrigado por utilizar o Internet Banking.");
					sair = true;
					System.exit(0);
				}
				break;

			// para depósito
			case 2:
				System.out.println("Digite o valor a depositar em sua conta");
				double valorADepositar = new Scanner(System.in).nextDouble();

				if (verificaValor(valorADepositar) && logada.getTipoConta().equals("Corrente")) {
					deposito(valorADepositar, logada);
					logada.saldo = logada.saldo - 0.1;
					logada.tarifacao += 0.1;
					System.out.println(appUtil.formataMoeda(logada.tarifacao));
				} else if (verificaValor(valorADepositar) && logada.getTipoConta().equals("Poupanca")) {
					deposito(valorADepositar, logada);

				}

				else {
					System.out.println("N�o foi poss�vel realizar a opera��o. Digite um valor v�lido.\n");
				}

				// realizar outra operação ou sair
				if (verificaSairDoSegundoMenu()) {
					System.out.println("Obrigado por utilizar o Internet Banking.");
					sair = true;
					System.exit(0);

				}

				break;

			// para Transferencia
			case 3:

				System.out.println("Digite a conta de destino");
				int contaDestino = new Scanner(System.in).nextInt();

				if (mapContasNumeroConta.get(contaDestino) != null) {
					Conta temporaria = mapContasNumeroConta.get(contaDestino);
					System.out.println("Digite o valor a transferir");
					double valorATransferir = new Scanner(System.in).nextDouble();

					if (verificaValor(valorATransferir) && valorATransferir <= logada.getSaldo()
							&& logada.getTipoConta().equals("Corrente")) {

						logada.saldo -= valorATransferir;
						temporaria.saldo += valorATransferir;

						logada.saldo = logada.saldo - 0.2;
						logada.tarifacao += 0.2;
						System.out.println("Transferencia efetuada. ");
						if (verificaSairDoSegundoMenu()) {
							System.out.println("Obrigado por utilizar o Internet Banking.");
							sair = true;
							System.exit(0);
						}
						break;

					} else if (verificaValor(valorATransferir) && valorATransferir <= logada.getSaldo()
							&& logada.getTipoConta().equals("Poupanca")) {
						logada.saldo -= valorATransferir;
						temporaria.saldo += valorATransferir;

					} else {
						System.out.println("N�o foi poss�vel realizar a opera��o. Digite um valor v�lido.\n");
					}

				}

				break;

			// para Voltar
			case 4:

				sair = true;

				break;

			}
		} while (!sair);
	}

	protected static void relatorios(long c, Conta logada, Map<Long, Conta> mapContas,
			Map<Integer, Conta> mapContasNumeroConta, List<Object> numeroAgencias) {

		if (logada.getTipoUsuario().equals("Cliente")) {
			if (logada.getTipoConta().equals("Corrente")) {
				System.out.println("Escolha a opera��o desejada:"
						+ "\n1- Saldo\n2- Relat�rio de Tarifa��o da Conta Corrente\n3- Informa��es sobre tarifas ");
				Scanner scan = new Scanner(System.in);
				int op = scan.nextInt();

				switch (op) {
				case 1:
					System.out.println(appUtil.formataMoeda(logada.getSaldo()));
					break;
				case 2:
					System.out.println(appUtil.formataMoeda(logada.getTarifacao()));
					break;

				case 3:
					System.out.println("Para cada saque ser� cobrado o valor de R$0.10 (dez centavos);\n"
							+ "Para cada dep�sito ser� cobrado o valor de R$0.10 (dez centavos);\n"
							+ "Para cada transfer�ncia ser� cobrado o valor de R$0.20 (vinte centavos)+"
							+ "Valores v�lidos para conta corrente. Conta poupan�a n�o ser� tarifada");
					break;
				default:
					break;
				}
			} else if (logada.getTipoConta().equals("Poupanca")) {
				System.out.println(
						"Escolha a opera��o desejada:" + "\n1- Saldo\n2- Relat�rio de de Rendimentos da Poupan�a");
				Scanner scan = new Scanner(System.in);
				int op = scan.nextInt();

				switch (op) {
				case 1:
					System.out.println(appUtil.formataMoeda(logada.getSaldo()));
					break;
				case 2:
					relRendimentosPoup(logada);
					break;

				default:
					break;
				}
			}
		}

		if (logada.getTipoUsuario().equals("Gerente")) {
			if (logada.getTipoConta().equals("Corrente")) {
				System.out.println("Escolha a opera��o desejada:"
						+ "\n1- Saldo\n2- Relat�rio de Tarifa��o da Conta Corrente\n3- Informa��es sobre tarifas "
						+ "\n4- Relat�rio de contas gerenciadas");
				Scanner scan = new Scanner(System.in);
				int op = scan.nextInt();

				switch (op) {
				case 1:
					System.out.println(appUtil.formataMoeda(logada.getSaldo()));
					break;
				case 2:
					System.out.println(appUtil.formataMoeda(logada.getTarifacao()));
					break;

				case 3:
					System.out.println("Para cada saque ser� cobrado o valor de R$0.10 (dez centavos);\n"
							+ "Para cada dep�sito ser� cobrado o valor de R$0.10 (dez centavos);\n"
							+ "Para cada transfer�ncia ser� cobrado o valor de R$0.20 (vinte centavos)+"
							+ "Valores v�lidos para conta corrente. Conta poupan�a n�o ser� tarifada");
					break;

				case 4:
					System.out.println("N�mero de contas gerenciadas na mesma ag�ncia:");
					int contador = 0;

					for (Object object : numeroAgencias) {
						Conta temp = (Conta) object;
						if (temp.getAgencia() == logada.getAgencia()) {
							contador++;
						}
					}

					System.out.println(appUtil.formataMoeda(contador));

					break;
				default:
					break;
				}
			} else if (logada.getTipoConta().equals("Poupanca")) {
				System.out.println(
						"Escolha a opera��o desejada:" + "\n1- Saldo\n2- Relat�rio de Rendimentos da Poupan�a"
								+ "\n3- Relat�rio de contas gerenciadas");
				Scanner scan = new Scanner(System.in);
				int op = scan.nextInt();

				switch (op) {
				case 1:
					System.out.println(appUtil.formataMoeda(logada.getSaldo()));
					break;
				case 2:
					relRendimentosPoup(logada);
					break;
				case 3:
					System.out.println("N�mero de contas gerenciadas na mesma ag�ncia:");
					int contador = 0;
					
					for (Object object : numeroAgencias) {
						Conta temp = (Conta) object;
						if (temp.getAgencia() == logada.getAgencia()) {
							contador++;
						}
					}

					System.out.println(appUtil.formataMoeda(contador));
					break;
				default:
					break;
				}
			}
		}

		if (logada.getTipoUsuario().equals("Diretor")) {
			if (logada.getTipoConta().equals("Corrente")) {
				System.out.println("Escolha a opera��o desejada:"
						+ "\n1- Saldo\n2- Relat�rio de Tarifa��o da Conta Corrente\n3- Informa��es sobre tarifas "
						+ "\n4- Informa��es dos Clientes do Sistema");
				Scanner scan = new Scanner(System.in);
				int op = scan.nextInt();

				switch (op) {
				case 1:
					System.out.println(appUtil.formataMoeda(logada.getSaldo()));
					break;
				case 2:
					System.out.println(appUtil.formataMoeda(logada.getTarifacao()));
					break;

				case 3:
					System.out.println("Para cada saque ser� cobrado o valor de R$0.10 (dez centavos);\n"
							+ "Para cada dep�sito ser� cobrado o valor de R$0.10 (dez centavos);\n"
							+ "Para cada transfer�ncia ser� cobrado o valor de R$0.20 (vinte centavos)+"
							+ "Valores v�lidos para conta corrente. Conta poupan�a n�o ser� tarifada");
					break;

				case 4:
					System.out.println("Informa��es dos Clientes do Sistema:");
					for (Object object : numeroAgencias) {
						Conta temp = (Conta) object;
						System.out.println(temp.getNome() + temp.getCPFDoTitular() + temp.getAgencia());
					}
					break;
				default:
					break;
				}
			} else if (logada.getTipoConta().equals("Poupanca")) {
				System.out.println(
						"Escolha a opera��o desejada:" + "\n1- Saldo\n2- Relat�rio de de Rendimentos da Poupan�a"
								+ "\n3- Informa��es dos Clientes do Sistema");
				Scanner scan = new Scanner(System.in);
				int op = scan.nextInt();

				switch (op) {
				case 1:
					System.out.println(appUtil.formataMoeda(logada.getSaldo()));
					break;
				case 2:
					relRendimentosPoup(logada);
					break;
				case 3:
					System.out.println("Informa��es dos Clientes do Sistema:");
					for (Object object : numeroAgencias) {
						Conta temp = (Conta) object;
						System.out.println(temp.getNome() + temp.getCPFDoTitular() + temp.getAgencia());
					}
					break;

				default:
					break;
				}
			}
		}

		if (logada.getTipoUsuario().equals("Presidente")) {
			double capitalTotal = 0;

			if (logada.getTipoConta().equals("Corrente")) {
				System.out.println("Escolha a opera��o desejada:"
						+ "\n1- Saldo\n2- Relat�rio de Tarifa��o da Conta Corrente\n3- Informa��es sobre tarifas "
						+ "\n4- Informa��es dos Clientes do Sistema\n5- Relat�rio - Capital Total do Banco");
				Scanner scan = new Scanner(System.in);
				int op = scan.nextInt();

				switch (op) {
				case 1:
					System.out.println(appUtil.formataMoeda(logada.getSaldo()));
					break;
				case 2:
					System.out.println(appUtil.formataMoeda(logada.getTarifacao()));
					break;

				case 3:
					System.out.println("Para cada saque ser� cobrado o valor de R$0.10 (dez centavos);\n"
							+ "Para cada dep�sito ser� cobrado o valor de R$0.10 (dez centavos);\n"
							+ "Para cada transfer�ncia ser� cobrado o valor de R$0.20 (vinte centavos);\n"
							+ "Valores v�lidos para conta corrente. Conta poupan�a n�o ser� tarifada");
					break;

				case 4:
					System.out.println("Informa��es dos Clientes do Sistema:");
					for (Object object : numeroAgencias) {
						Conta temp = (Conta) object;
						System.out.println(temp.getNome() + temp.getCPFDoTitular() + temp.getAgencia());
					}
					break;

				case 5:
					System.out.println("Capital Total =");
					for (Object object : numeroAgencias) {
						Conta temp = (Conta) object;
						capitalTotal += temp.getSaldo() + temp.getTarifacao();
					}
					System.out.println(appUtil.formataMoeda(capitalTotal));

					break;
				default:
					break;
				}
			} else if (logada.getTipoConta().equals("Poupanca")) {
				System.out.println(
						"Escolha a opera��o desejada:" + "\n1- Saldo\n2- Relat�rio de de Rendimentos da Poupan�a"
								+ "\n3- Informa��es dos Clientes do Sistema\n4- Relat�rio - Capital Total do Banco");
				Scanner scan = new Scanner(System.in);
				int op = scan.nextInt();

				switch (op) {
				case 1:
					System.out.println(appUtil.formataMoeda(logada.getSaldo()));
					;
					break;
				case 2:
					relRendimentosPoup(logada);
					break;
				case 3:
					System.out.println("Informa��es dos Clientes do Sistema:");
					for (Object object : numeroAgencias) {
						Conta temp = (Conta) object;
						System.out.println(temp.getNome() + temp.getCPFDoTitular() + temp.getAgencia());
					}
					break;
				case 4:
					System.out.println("Capital Total =");
					for (Object object : numeroAgencias) {
						Conta temp = (Conta) object;
						capitalTotal += temp.getSaldo() + temp.getTarifacao();
					}
					System.out.println(appUtil.formataMoeda(capitalTotal));

					break;

				default:
					break;
				}
			}
		}

	}

	// realizar outra operação ou voltar
	public static boolean verificaSairDoSegundoMenu() {

		int input1;
		do {
			System.out.println("Deseja realizar outra opera��o? Digite 1 para SIM e 2 para N�O\n");
			input1 = new Scanner(System.in).nextInt();
		} while (input1 < 1 || input1 > 2);

		if (input1 == 2) {
			return true;
		} else {

			return false;
		}
	}

	public static void saque(double valorASacar, Conta logada) {

		logada.saldo -= valorASacar;
	}

	public static void deposito(double valorASacar, Conta logada) {

		logada.saldo += valorASacar;
	}

	public static boolean verificaValor(double valor) {
		if (valor > 0.0) {
			return true;
		} else {
			return false;
		}
	}

	public static void relRendimentosPoup(Conta logada) {
		System.out.println("Simule no prazo desejado:" + "\n1- 7 dias\n2- 15 dias\n3 - 30 dias");
		Scanner scan = new Scanner(System.in);
		int op = scan.nextInt();

		switch (op) {
		case 1:
			System.out.println(appUtil.formataMoeda(logada.getSaldo() * 1.0003));
			break;
		case 2:
			System.out.println(appUtil.formataMoeda(logada.getSaldo() * 1.0006));
			break;
		case 3:
			System.out.println(appUtil.formataMoeda(logada.getSaldo() * 1.001303));
			break;
		default:
			break;
		}
	}

	public long getCPFDoTitular() {
		return CPFDoTitular;
	}

	public String getNome() {
		return nome;
	}

	public String getTipoConta() {
		return TipoConta;
	}

	public int getNumeroDaConta() {
		return numeroDaConta;
	}

	public int getAgencia() {
		return agencia;
	}

	public double getSaldo() {
		return saldo;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public double getTarifacao() {
		return tarifacao;
	}

}
