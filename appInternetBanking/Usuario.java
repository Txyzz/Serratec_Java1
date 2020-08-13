package appInternetBanking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Usuario {
	
	String tipoUsuario;
	long CPF;
	long senha;
	String nome;
	String tipoConta;	
	
	
	public Usuario(String tipoUsuario, long CPF, long senha, String nome, String tipoConta) {
		
		this.tipoUsuario = tipoUsuario;
		this.CPF = CPF;
		this.senha = senha;
		this.nome = nome;
		this.tipoConta = tipoConta;
	}


	public String getTipoUsuario() {
		return tipoUsuario;
	}


	public long getCPF() {
		return CPF;
	}


	public long getSenha() {
		return senha;
	}


	public String getNome() {
		return nome;
	}


	public String getTipoConta() {
		return tipoConta;
	}


	
		

		
		
}	
		

		

		
		
		

