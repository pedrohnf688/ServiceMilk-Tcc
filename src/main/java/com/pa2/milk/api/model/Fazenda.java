package com.pa2.milk.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;

@Entity(name = "fazenda")
@Table
public class Fazenda extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "O campo nome não pode ser nulo.")
	private String nomeFazenda;

	// @CNPJ(message = "O campo CNPJ é invalido")
	@NotBlank(message = "O campo CNPJ não pode ser nulo.")
	private String cpfcnpj;

	@NotBlank(message = "O campo cep não pode ser nulo.")
	private String cep;

	@NotBlank(message = "O campo endereço não pode ser nulo.")
	private String endereco;

	@NotNull(message = "O campo numero não pode ser nulo.")
	private int numero;

	@NotBlank(message = "O campo bairro não pode ser nulo.")
	private String bairro;

	@NotBlank(message = "O campo cidade não pode ser nulo.")
	private String cidade;

	@NotBlank(message = "O campo estado não pode ser nulo.")
	private String estado;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@OneToOne
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	private Arquivo fotoFazenda;

	public Fazenda() {
	}

	public Fazenda(Integer id, @NotBlank(message = "O campo nome não pode ser nulo.") String nomeFazenda,
			@NotBlank(message = "O campo CNPJ não pode ser nulo.") String cpfcnpj,
			@NotBlank(message = "O campo cep não pode ser nulo.") String cep,
			@NotBlank(message = "O campo endereço não pode ser nulo.") String endereco,
			@NotNull(message = "O campo numero não pode ser nulo.") int numero,
			@NotBlank(message = "O campo bairro não pode ser nulo.") String bairro,
			@NotBlank(message = "O campo cidade não pode ser nulo.") String cidade,
			@NotBlank(message = "O campo estado não pode ser nulo.") String estado, Cliente cliente,
			Arquivo fotoFazenda) {
		super();
		this.id = id;
		this.nomeFazenda = nomeFazenda;
		this.cpfcnpj = cpfcnpj;
		this.cep = cep;
		this.endereco = endereco;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.cliente = cliente;
		this.fotoFazenda = fotoFazenda;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpfcnpj() {
		return cpfcnpj;
	}

	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNomeFazenda() {
		return nomeFazenda;
	}

	public void setNomeFazenda(String nomeFazenda) {
		this.nomeFazenda = nomeFazenda;
	}

	public Arquivo getFotoFazenda() {
		return fotoFazenda;
	}

	public void setFotoFazenda(Arquivo fotoFazenda) {
		this.fotoFazenda = fotoFazenda;
	}

	@Override
	public String toString() {
		return "Fazenda [id=" + id + ", nomeFazenda=" + nomeFazenda + ", cpfcnpj=" + cpfcnpj + ", cep=" + cep
				+ ", endereco=" + endereco + ", numero=" + numero + ", bairro=" + bairro + ", cidade=" + cidade
				+ ", estado=" + estado + ", cliente=" + cliente + ", fotoFazenda=" + fotoFazenda + "]";
	}

}