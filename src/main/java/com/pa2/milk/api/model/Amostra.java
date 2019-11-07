package com.pa2.milk.api.model;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pa2.milk.api.model.Analise.Builder;
import com.pa2.milk.api.model.enums.EnumAnalisesSolicitadas;
import com.pa2.milk.api.model.enums.EnumEspecie;
import com.pa2.milk.api.model.enums.EnumLeite;
import com.pa2.milk.api.model.enums.EnumOrigemLeite;
import com.pa2.milk.api.model.enums.EnumProdutos;

@Entity(name = "amostra")
@Table
public class Amostra extends AbstractModel<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataColeta;

	// @NotNull(message = "O campo número de Amostra não pode ser nulo.")
	private int numeroAmostra;

	// @NotBlank(message = "O campo qrCode não pode ser nulo.")
	private String qrCode;

	private boolean finalizada;

	@Column(length = 2047)
	private String observacao;

	private static int cont = 0;

	@ManyToOne
	@JoinColumn(name = "analise_id")
	@JsonIgnore
	private Analise analise;

	private String identificadorAmostra = UUID.randomUUID().toString();



	public Amostra(Date dataColeta, int numeroAmostra, String qrCode, boolean finalizada, String observacao,
			Analise analise, String identificadorAmostra) {
		super();
		this.dataColeta = dataColeta;
		this.numeroAmostra = numeroAmostra;
		this.qrCode = qrCode;
		this.finalizada = finalizada;
		this.observacao = observacao;
		this.analise = analise;
		this.identificadorAmostra = identificadorAmostra;
	}

	public Amostra() {
		super();
	//	this.numeroAmostra = cont++;

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

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataColeta() {
		return dataColeta;
	}

	public void setDataColeta(Date dataColeta) {
		this.dataColeta = dataColeta;
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

	public Analise getAnalise() {
		return analise;
	}

	public void setAnalise(Analise analise) {
		this.analise = analise;
	}

	@Override
	public String toString() {
		return "Amostra [id=" + id + ", dataColeta=" + dataColeta + ", numeroAmostra=" + numeroAmostra + ", qrCode="
				+ qrCode + ", observacao=" + observacao + ", analise=" + analise + ", identificadorAmostra="
				+ identificadorAmostra + "]";
	}

}
