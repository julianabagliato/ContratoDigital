package mobile.contratodigital.model;

import java.io.Serializable;
import android.graphics.drawable.Drawable;

public class Assinatura implements Serializable{
	
	private transient Drawable recebeAssinatura;
	private transient Drawable caneta; 
	private String nome; 
	private String cargo; 
	private String rg;
	private String cpf;
	private String razaoSocial;
	
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public Drawable getRecebeAssinatura() {
		return recebeAssinatura;
	}
	public void setRecebeAssinatura(Drawable recebeAssinatura) {
		this.recebeAssinatura = recebeAssinatura;
	}
	public Drawable getCaneta() {
		return caneta;
	}
	public void setCaneta(Drawable caneta) {
		this.caneta = caneta;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
