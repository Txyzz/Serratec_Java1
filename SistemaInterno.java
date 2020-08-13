package appInternetBanking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class SistemaInterno  {

	public static void main(String[] args) throws IOException   {
		
		//criação do HashMap de usuários 
		//criação do HashMap de contas
		Map<Long, Usuario> mapUsers = new HashMap<>();
		Map<Long, Conta> mapContas = new HashMap<>();
		Map<Integer, Conta> mapContasNumeroConta = new HashMap<>();
		//Map<Long, Conta> mapAgencias = new HashMap<>();
		List<Object> numeroAgencias = new ArrayList<>();
		
		
		String linha;
		Usuario user = null;
		Conta conta = null;
			
		BufferedReader csvReader = new BufferedReader(new FileReader("Accounts.csv"));
		
		linha = csvReader.readLine();
		while ((linha = csvReader.readLine()) != null) {
			String tipoUsuario;
			long CPF;
			long senha;
			String nome;
			String TipoConta;
			int numeroConta;
			int agencia;
			double saldo;
			double tarifacao;
			
			String[] data = linha.split(",");
			tipoUsuario = data[0];
			CPF = Long.parseLong(data[1]);
			senha = Long.parseLong(data[2]);
			nome = data[3];
			TipoConta = data[4];
			numeroConta = Integer.parseInt(data[5]);
			agencia = Integer.parseInt(data[6]);
			saldo = Double.parseDouble(data[7]);	
			tarifacao = Double.parseDouble(data[8]);
		
			switch (tipoUsuario) {
			
			case "Cliente":
				
				user = new Cliente(tipoUsuario, CPF, senha, nome, TipoConta);				
				mapUsers.put(CPF, user);
				
				break;
				
			case "Gerente":
				
				user = new Gerente(tipoUsuario, CPF, senha, nome, TipoConta, agencia);				
				mapUsers.put(CPF, user);
				break;
				
			case "Diretor":
	
				user = new Diretor(tipoUsuario, CPF, senha, nome, TipoConta);				
				mapUsers.put(CPF, user);
				break;
	
			case "Presidente":
	
				user = new Presidente(tipoUsuario, CPF, senha, nome, TipoConta);				
				mapUsers.put(CPF, user);
				break;
	
			}
			
			switch (TipoConta) {
			
			case "Corrente":
				
				conta = new Corrente(TipoConta, CPF,nome, tipoUsuario, numeroConta, agencia, saldo, tarifacao);
				mapContas.put(CPF, conta);
			
			mapContasNumeroConta.put(numeroConta, conta);
				numeroAgencias.add(conta);
				break;
				
			case "Poupanca":
				
				conta = new Poupanca(TipoConta, CPF, nome,tipoUsuario, numeroConta, agencia, saldo, tarifacao);
				mapContas.put(CPF, conta);			
				mapContasNumeroConta.put(numeroConta, conta);
				numeroAgencias.add(conta);
				break;

			
			}
			
		}		
			
		csvReader.close();
		
		//entra tela inicial
		//espera
		//limpa tel(?)		
		
		//LOGIN///
		//colar layout
		
		System.out.println("Digite seu CPF");
		Scanner scan = new Scanner(System.in); 
		long CPFParaLogar = scan.nextLong(); 
		
		Usuario logado = login(CPFParaLogar, mapUsers);
		Conta logada = mapContas.get(CPFParaLogar);			
		
		primeiroMenu(logado.getCPF(), logada, mapContas, mapContasNumeroConta, numeroAgencias);
		
		
		
		}
		
	

//LOGIN///
		public static Usuario login(long login, Map<Long, Usuario> mapUsers) {					
		
			Usuario logado = mapUsers.get(login);			
						
			if (mapUsers.get(login) != null) {
				System.out.println("Digite sua senha");
				Scanner scan2 = new Scanner(System.in);
				long senhaDigitada = scan2.nextLong();
				
				if (senhaDigitada == logado.getSenha()) {
					
					return logado;
					
				}
				else {
					System.out.println("Usuário e/ou senha incorreto. Inicie novamente");
					return null;
				}
					
			}
			else {
				System.out.println("Usu�rio e/ou senha incorreto. Inicie novamente");
				return null;
			}
			
		}

		
		public static void primeiroMenu(long c, Conta logada, Map<Long, Conta> mapContas, Map<Integer, Conta> mapContasNumeroConta, List<Object> numeroAgencias) {
			
			boolean sair = false;
			
				do {
					//Primeiro Menu 
					System.out.println("Escolha a operação desejada:"
							+ "\n1- Movimentações\n2- Relatórios\n3- Sair ");
					Scanner scan = new Scanner(System.in);
					int op = scan.nextInt();			
					
					switch (op) {
					
					//para Movimentações
					case 1:
								
						//chama menuMovimentaçoes
						Conta.movimentacoes(c, logada, mapContas, mapContasNumeroConta);		
															
						break;					
						
						//para Relatórios
					case 2:
						
						Conta.relatorios(c, logada, mapContas, mapContasNumeroConta,numeroAgencias);
						

						//realizar outra operação ou sair
						if (verificaSairDoPrimeiroMenu()) {
							System.out.println("Obrigado por utilizar o Internet Banking.");
							sair = true;
						}
						break;					
								
											
						//para sair	
					case 3:
						System.out.println("Obrigado por utilizar o Internet Banking.");
						sair = true;
						break;			
						
					default:
						System.out.println("Opção inválida.\n");
						break;
					}
				} while (sair != true);
				
			}
		



	//realizar outra operação ou sair
		public static boolean verificaSairDoPrimeiroMenu() {
			int input1;
			do {
				System.out.println("Deseja realizar outra operação? Digite 1 para SIM e 2 para NÃO\n");
				input1 = new Scanner(System.in).nextInt();
			} while (input1 < 1 || input1 > 2);
			
			if (input1 ==2) {
				return true;
			}
			else {
				
				
				return false;
			}
		}
		
		

	
}
				
				
				
				
				
