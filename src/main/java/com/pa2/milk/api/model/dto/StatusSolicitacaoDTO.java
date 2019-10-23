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
	private String emailBolsista;

	public StatusSolicitacaoDTO() {
	}

	public StatusSolicitacaoDTO(Integer solicitacaoId, EnumStatusSolicitacao status, String observacao,
			double temperatura, String emailBolsista) {
		super();
		this.solicitacaoId = solicitacaoId;
		this.status = status;
		this.observacao = observacao;
		this.temperatura = temperatura;
		this.emailBolsista = emailBolsista;
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

	public String getEmailBolsista() {
		return emailBolsista;
	}

	public void setEmailBolsista(String emailBolsista) {
		this.emailBolsista = emailBolsista;
	}

	@Override
	public String toString() {
		return "StatusSolicitacaoDTO [solicitacaoId=" + solicitacaoId + ", status=" + status + ", observacao="
				+ observacao + ", temperatura=" + temperatura + ", emailBolsista=" + emailBolsista + "]";
	}

}
