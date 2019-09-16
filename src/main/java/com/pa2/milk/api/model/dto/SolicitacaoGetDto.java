package com.pa2.milk.api.model.dto;

import java.util.Date;
import java.util.List;

import com.pa2.milk.api.model.Amostra;
import com.pa2.milk.api.model.Analise;
import com.pa2.milk.api.model.Cliente;
import com.pa2.milk.api.model.Fazenda;
import com.pa2.milk.api.model.LaudoMedia;
import com.pa2.milk.api.model.enums.EnumStatusSolicitacao;

public class SolicitacaoGetDto {

	private Integer id;

	private Cliente cliente;

	private Fazenda fazenda;

	private List<Analise> listaAnalise;

	private EnumStatusSolicitacao status;

	private Date dataCriada;

	private String observacao;

	private List<LaudoMedia> listaLaudoMedia;

	private List<Amostra> amostras;

	public SolicitacaoGetDto() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Fazenda getFazenda() {
		return fazenda;
	}

	public void setFazenda(Fazenda fazenda) {
		this.fazenda = fazenda;
	}

	public List<Analise> getListaAnalise() {
		return listaAnalise;
	}

	public void setListaAnalise(List<Analise> listaAnalise) {
		this.listaAnalise = listaAnalise;
	}

	public EnumStatusSolicitacao getStatus() {
		return status;
	}

	public void setStatus(EnumStatusSolicitacao status) {
		this.status = status;
	}

	public Date getDataCriada() {
		return dataCriada;
	}

	public void setDataCriada(Date dataCriada) {
		this.dataCriada = dataCriada;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<LaudoMedia> getListaLaudoMedia() {
		return listaLaudoMedia;
	}

	public void setListaLaudoMedia(List<LaudoMedia> listaLaudoMedia) {
		this.listaLaudoMedia = listaLaudoMedia;
	}

	public List<Amostra> getAmostras() {
		return amostras;
	}

	public void setAmostra(List<Amostra> amostras) {
		this.amostras = amostras;
	}

	@Override
	public String toString() {
		return "SolicitacaoGetDto [id=" + id + ", cliente=" + cliente + ", fazenda=" + fazenda + ", listaAnalise="
				+ listaAnalise + ", status=" + status + ", dataCriada=" + dataCriada + ", observacao=" + observacao
				+ ", listaLaudoMedia=" + listaLaudoMedia + ", amostras=" + amostras + "]";
	}

}
