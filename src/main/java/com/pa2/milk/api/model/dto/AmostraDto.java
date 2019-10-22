package com.pa2.milk.api.model.dto;

import java.util.Collection;
import java.util.Date;

import com.pa2.milk.api.model.Amostra;
import com.pa2.milk.api.model.Analise;
import com.pa2.milk.api.model.enums.EnumAnalisesSolicitadas;
import com.pa2.milk.api.model.enums.EnumEspecie;
import com.pa2.milk.api.model.enums.EnumOrigemLeite;
import com.pa2.milk.api.model.enums.EnumProdutos;

public class AmostraDto {

	private Date dataColeta;

	private int numeroAmostra;

	private String qrCode;

	private String observacao;

	private String identificadorAmostra;

	private boolean finalizada;

	private EnumOrigemLeite origemLeite;

	private Collection<EnumProdutos> produtos;

	private EnumEspecie especie;

	private Analise analise;

	public Date getDataColeta() {
		return dataColeta;
	}

	public void setDataColeta(Date dataColeta) {
		this.dataColeta = dataColeta;
	}

	public String getIdentificadorAmostra() {
		return identificadorAmostra;
	}

	public void setIdentificadorAmostra(String identificadorAmostra) {
		this.identificadorAmostra = identificadorAmostra;
	}

	public boolean isFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
	}

	public int getNumeroAmostra() {
		return numeroAmostra;
	}

	public void setNumeroAmostra(int numeroAmostra) {
		this.numeroAmostra = numeroAmostra;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public EnumOrigemLeite getOrigemLeite() {
		return origemLeite;
	}

	public void setOrigemLeite(EnumOrigemLeite origemLeite) {
		this.origemLeite = origemLeite;
	}

	public Collection<EnumProdutos> getProdutos() {
		return produtos;
	}

	public void setProdutos(Collection<EnumProdutos> produtos) {
		this.produtos = produtos;
	}

	public EnumEspecie getEspecie() {
		return especie;
	}

	public void setEspecie(EnumEspecie especie) {
		this.especie = especie;
	}

	public Analise getAnalise() {
		return analise;
	}

	public void setAnalise(Analise analise) {
		this.analise = analise;
	}

	@Override
	public String toString() {
		return "AmostraDto [dataColeta=" + dataColeta + ", numeroAmostra=" + numeroAmostra + ", qrCode=" + qrCode
				+ ", observacao=" + observacao + ", identificadorAmostra=" + identificadorAmostra + ", finalizada="
				+ finalizada + ", origemLeite=" + origemLeite + ", produtos=" + produtos + ", especie=" + especie
				+ ", analise=" + analise + "]";
	}

}
