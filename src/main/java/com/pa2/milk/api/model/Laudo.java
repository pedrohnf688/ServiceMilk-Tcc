package com.pa2.milk.api.model;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Laudo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer laudo_id;

	@JsonProperty("BatchId")
	private String batchId;

	@JsonProperty("Sequence")
	private String sequence;

	@JsonProperty("Date")
	private String date;

	@JsonProperty("SampleID")
	private String sampleid;

	@JsonProperty("Fat")
	private String fat; // GORDURA

	@JsonProperty("Tru.Pro.")
	private String trupro; // PROTEINA VERDADEIRA

	@JsonProperty("Tot.Pro.")
	private String totpro; // PROTEINA TOTAL

	@JsonProperty("Casein")
	@Column(name = "casein")
	private String casein; // CASEINA

	@JsonProperty("Solids")
	private String solids; // Solidos Totais

	@JsonProperty("SNF")
	private String snf; // esd Teor de Extrato Seco Desengordurado

	@JsonProperty("FPD")
	private String fpd; // Ponto Crioscópico

	@JsonProperty("Urea")
	private String urea; // Teor de Nitrogênio Ureico (mg/dL)

	private String ccs; // OK

	private String cel; // OK

	private String ph; // OK

	private String den; // OK

	private String rant;

	private String cbt;

	private String cmt;

	@JsonProperty("Lactose")
	private String lact;  //OK

	private String identificadorLaudo; //OK

	public Laudo() {
	}

	public Integer getLaudo_id() {
		return laudo_id;
	}

	public void setLaudo_id(Integer laudo_id) {
		this.laudo_id = laudo_id;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSampleid() {
		return sampleid;
	}

	public void setSampleid(String sampleid) {
		this.sampleid = sampleid;
	}

	public String getFat() {
		return fat;
	}

	public void setFat(String fat) {
		this.fat = fat;
	}

	public String getTrupro() {
		return trupro;
	}

	public void setTrupro(String trupro) {
		this.trupro = trupro;
	}

	public String getTotpro() {
		return totpro;
	}

	public void setTotpro(String totpro) {
		this.totpro = totpro;
	}

	public String getCasein() {
		return casein;
	}

	public void setCasein(String casein) {
		this.casein = casein;
	}

	public String getSolids() {
		return solids;
	}

	public void setSolids(String solids) {
		this.solids = solids;
	}

	public String getSnf() {
		return snf;
	}

	public void setSnf(String snf) {
		this.snf = snf;
	}

	public String getFpd() {
		return fpd;
	}

	public void setFpd(String fpd) {
		this.fpd = fpd;
	}

	public String getUrea() {
		return urea;
	}

	public void setUrea(String urea) {
		this.urea = urea;
	}

	public String getCcs() {
		return ccs;
	}

	public void setCcs(String ccs) {
		this.ccs = ccs;
	}

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getDen() {
		return den;
	}

	public void setDen(String den) {
		this.den = den;
	}

	public String getRant() {
		return rant;
	}

	public void setRant(String rant) {
		this.rant = rant;
	}

	public String getCbt() {
		return cbt;
	}

	public void setCbt(String cbt) {
		this.cbt = cbt;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public String getLact() {
		return lact;
	}

	public void setLact(String lact) {
		this.lact = lact;
	}

	public String getIdentificadorLaudo() {
		return identificadorLaudo;
	}

	public void setIdentificadorLaudo(String identificadorLaudo) {
		this.identificadorLaudo = identificadorLaudo;
	}

	@Override
	public String toString() {
		return "Laudo [laudo_id=" + laudo_id + ", batchId=" + batchId + ", sequence=" + sequence + ", date=" + date
				+ ", sampleid=" + sampleid + ", fat=" + fat + ", trupro=" + trupro + ", totpro=" + totpro + ", casein="
				+ casein + ", solids=" + solids + ", snf=" + snf + ", fpd=" + fpd + ", urea=" + urea + ", ccs=" + ccs
				+ ", cel=" + cel + ", ph=" + ph + ", den=" + den + ", rant=" + rant + ", cbt=" + cbt + ", cmt=" + cmt
				+ ", lact=" + lact + ", identificadorLaudo=" + identificadorLaudo + "]";
	}

	public LocalDate conversao(String dataRecebida) throws ParseException {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate data = LocalDate.parse(dataRecebida, formato);
		System.out.println(data);
		return data;
	}

}
