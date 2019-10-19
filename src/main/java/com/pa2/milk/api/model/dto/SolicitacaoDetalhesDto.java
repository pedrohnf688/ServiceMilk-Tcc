package com.pa2.milk.api.model.dto;

import com.pa2.milk.api.model.enums.EnumStatusSolicitacao;

public class SolicitacaoDetalhesDto {

	private EnumStatusSolicitacao status;
	private String observacao;
	private double temperatura;

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

	@Override
	public String toString() {
		return "SolicitacaoDetalhesDto [status=" + status + ", observacao=" + observacao + ", temperatura="
				+ temperatura + "]";
	}

}
