package com.pa2.milk.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "ordem_servico")
@Table
public class OrdemServico extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataHora;

	private String emissaoLaudo;

	private String analiseLaboratorial;

	@OneToOne
	@JoinColumn(name = "solicitacao_id")
	private Solicitacao solicitacao;

	// @NotNull(message = "O campo valor não pode ser nulo.")
	private double valorPreco;

	private String ordem;

	private String entregaAmostras;

	private String dataRecebimento;
	private String dataAnalise;
	private String dataProcessamento;
	private int amostrasRecebidas;
	private int amostrasNaoAnalisadas;
	private int amostrasNaoColetadas;

	public OrdemServico() {
	}

	public OrdemServico(Date dataHora, String emissaoLaudo, String analiseLaboratorial, Solicitacao solicitacao,
			double valorPreco, String ordem, String entregaAmostras, String dataRecebimento, String dataAnalise,
			String dataProcessamento, int amostrasRecebidas, int amostrasNaoAnalisadas, int amostrasNaoColetadas) {
		super();
		this.dataHora = dataHora;
		this.emissaoLaudo = emissaoLaudo;
		this.analiseLaboratorial = analiseLaboratorial;
		this.solicitacao = solicitacao;
		this.valorPreco = valorPreco;
		this.ordem = ordem;
		this.entregaAmostras = entregaAmostras;
		this.dataRecebimento = dataRecebimento;
		this.dataAnalise = dataAnalise;
		this.dataProcessamento = dataProcessamento;
		this.amostrasRecebidas = amostrasRecebidas;
		this.amostrasNaoAnalisadas = amostrasNaoAnalisadas;
		this.amostrasNaoColetadas = amostrasNaoColetadas;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public double getValorPreco() {
		return valorPreco;
	}

	public void setValorPreco(double valorPreco) {
		this.valorPreco = valorPreco;
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

	public String getOrdem() {
		return ordem;
	}

	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}

	public String getEntregaAmostras() {
		return entregaAmostras;
	}

	public void setEntregaAmostras(String entregaAmostras) {
		this.entregaAmostras = entregaAmostras;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
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

	public int getAmostrasRecebidas() {
		return amostrasRecebidas;
	}

	public void setAmostrasRecebidas(int amostrasRecebidas) {
		this.amostrasRecebidas = amostrasRecebidas;
	}

	public int getAmostrasNaoAnalisadas() {
		return amostrasNaoAnalisadas;
	}

	public void setAmostrasNaoAnalisadas(int amostrasNaoAnalisadas) {
		this.amostrasNaoAnalisadas = amostrasNaoAnalisadas;
	}

	public int getAmostrasNaoColetadas() {
		return amostrasNaoColetadas;
	}

	public void setAmostrasNaoColetadas(int amostrasNaoColetadas) {
		this.amostrasNaoColetadas = amostrasNaoColetadas;
	}

	@Override
	public String toString() {
		return "OrdemServico [id=" + id + ", dataHora=" + dataHora + ", emissaoLaudo=" + emissaoLaudo
				+ ", analiseLaboratorial=" + analiseLaboratorial + ", solicitacao=" + solicitacao + ", valorPreco="
				+ valorPreco + ", ordem=" + ordem + ", entregaAmostras=" + entregaAmostras + ", dataRecebimento="
				+ dataRecebimento + ", dataAnalise=" + dataAnalise + ", dataProcessamento=" + dataProcessamento
				+ ", amostrasRecebidas=" + amostrasRecebidas + ", amostrasNaoAnalisadas=" + amostrasNaoAnalisadas
				+ ", amostrasNaoColetadas=" + amostrasNaoColetadas + "]";
	}

}
