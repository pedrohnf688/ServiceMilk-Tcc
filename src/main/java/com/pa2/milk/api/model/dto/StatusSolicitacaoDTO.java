package com.pa2.milk.api.model.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.pa2.milk.api.model.enums.EnumStatusSolicitacao;

public class StatusSolicitacaoDTO {
	@Id
	private Integer solicitacaoId;

	// @NotNull
	@Enumerated(EnumType.STRING)
	private EnumStatusSolicitacao status;
	private String observacao;
	private double temperatura;
	private String emissaoLaudo;
	private String analiseLaboratorial;
	private String entregaAmostras;
	private double valorPreco;
	private String dataRecebimento;
	private String dataAnalise;
	private String dataProcessamento;
	private double amostrasRecebidas;
	private double amostrasNaoAnalisadas;

	public StatusSolicitacaoDTO() {
	}

	public Integer getSolicitacaoId() {
		return solicitacaoId;
	}

	public void setSolicitacaoId(Integer solicitacaoId) {
		this.solicitacaoId = solicitacaoId;
	}

	public EnumStatusSolicitacao getStatus() {
		return status;
	}

	public void setStatus(EnumStatusSolicitacao status) {
		this.status = status;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public String getEmissaoLaudo() {
		return emissaoLaudo;
	}

	public void setEmissaoLaudo(String emissaoLaudo) {
		this.emissaoLaudo = emissaoLaudo;
	}

	public String getAnaliseLaboratorial() {
		return analiseLaboratorial;
	}

	public void setAnaliseLaboratorial(String analiseLaboratorial) {
		this.analiseLaboratorial = analiseLaboratorial;
	}

	public String getEntregaAmostras() {
		return entregaAmostras;
	}

	public void setEntregaAmostras(String entregaAmostras) {
		this.entregaAmostras = entregaAmostras;
	}

	public double getValorPreco() {
		return valorPreco;
	}

	public void setValorPreco(double valorPreco) {
		this.valorPreco = valorPreco;
	}

	public String getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(String dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public String getDataAnalise() {
		return dataAnalise;
	}

	public void setDataAnalise(String dataAnalise) {
		this.dataAnalise = dataAnalise;
	}

	public String getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(String dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public double getAmostrasRecebidas() {
		return amostrasRecebidas;
	}

	public void setAmostrasRecebidas(double amostrasRecebidas) {
		this.amostrasRecebidas = amostrasRecebidas;
	}

	public double getAmostrasNaoAnalisadas() {
		return amostrasNaoAnalisadas;
	}

	public void setAmostrasNaoAnalisadas(double amostrasNaoAnalisadas) {
		this.amostrasNaoAnalisadas = amostrasNaoAnalisadas;
	}

	@Override
	public String toString() {
		return "StatusSolicitacaoDTO [solicitacaoId=" + solicitacaoId + ", status=" + status + ", observacao="
				+ observacao + ", temperatura=" + temperatura + ", emissaoLaudo=" + emissaoLaudo
				+ ", analiseLaboratorial=" + analiseLaboratorial + ", entregaAmostras=" + entregaAmostras
				+ ", valorPreco=" + valorPreco + ", dataRecebimento=" + dataRecebimento + ", dataAnalise=" + dataAnalise
				+ ", dataProcessamento=" + dataProcessamento + ", amostrasRecebidas=" + amostrasRecebidas
				+ ", amostrasNaoAnalisadas=" + amostrasNaoAnalisadas + "]";
	}

}
