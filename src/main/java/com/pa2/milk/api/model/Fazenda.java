package com.pa2.milk.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Fazenda extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FAZENDA")
	@SequenceGenerator(name = "SEQ_FAZENDA", sequenceName = "id_seq_fazenda", allocationSize = 1)
	private Integer id;

	@NotBlank
	private String nome;

	@NotBlank
	private String imagem;

	@CNPJ
	@NotBlank
	private String cnpj;

	@NotBlank
	private String cep;

	@NotBlank
	private String endereco;

	@NotNull
	private int numero;

	@NotBlank
	private String bairro;

	@NotBlank
	private String cidade;

	@NotBlank
	private String estado;

	@ManyToOne(optional = false)
	@JsonIgnore
	private Cliente cliente;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

	public void setNome(String nome) {
		this.nome = nome;
	}

}