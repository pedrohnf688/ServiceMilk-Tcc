package com.pa2.milk.api.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LaudoMedia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMedia;
	private double fatMedia;
	private double truproMedia;
	private double totproMedia;
	private double caseinMedia;
	private double solidsMedia;
	private double snfMedia;
	private double fpdMedia;
	private double ureaMedia;
	private double ccsMedia;
	private double celMedia;
	private double phMedia;
	private double denMedia;
	private double rantMedia;
	private double cbtMedia;
	private double cmtMedia;
	
	private double gordMedia;
	private double protMedia;
	private double lactMedia;
	private double esdMedia;
	private double pcMedia;


	@OneToMany(orphanRemoval = true)
	@Cascade({ CascadeType.ALL })
	private List<Laudo> listaLaudos;

	@ManyToOne
	@JoinColumn(name = "solicitacao_id")
	@JsonIgnore
	private Solicitacao solicitacao;

	public LaudoMedia() {
		super();
	}

	public Integer getIdMedia() {
		return idMedia;
	}

	public void setIdMedia(Integer idMedia) {
		this.idMedia = idMedia;
	}

	public double getFatMedia() {
		return fatMedia;
	}

	public void setFatMedia(double fatMedia) {
		this.fatMedia = fatMedia;
	}

	public double getTruproMedia() {
		return truproMedia;
	}

	public void setTruproMedia(double truproMedia) {
		this.truproMedia = truproMedia;
	}

	public double getTotproMedia() {
		return totproMedia;
	}

	public void setTotproMedia(double totproMedia) {
		this.totproMedia = totproMedia;
	}

	public double getCaseinMedia() {
		return caseinMedia;
	}

	public void setCaseinMedia(double caseinMedia) {
		this.caseinMedia = caseinMedia;
	}

	public double getSolidsMedia() {
		return solidsMedia;
	}

	public void setSolidsMedia(double solidsMedia) {
		this.solidsMedia = solidsMedia;
	}

	public double getSnfMedia() {
		return snfMedia;
	}

	public void setSnfMedia(double snfMedia) {
		this.snfMedia = snfMedia;
	}

	public double getFpdMedia() {
		return fpdMedia;
	}

	public void setFpdMedia(double fpdMedia) {
		this.fpdMedia = fpdMedia;
	}

	public double getUreaMedia() {
		return ureaMedia;
	}

	public void setUreaMedia(double ureaMedia) {
		this.ureaMedia = ureaMedia;
	}

	public double getCcsMedia() {
		return ccsMedia;
	}

	public void setCcsMedia(double ccsMedia) {
		this.ccsMedia = ccsMedia;
	}

	public double getCelMedia() {
		return celMedia;
	}

	public void setCelMedia(double celMedia) {
		this.celMedia = celMedia;
	}

	public double getPhMedia() {
		return phMedia;
	}

	public void setPhMedia(double phMedia) {
		this.phMedia = phMedia;
	}

	public double getDenMedia() {
		return denMedia;
	}

	public void setDenMedia(double denMedia) {
		this.denMedia = denMedia;
	}

	public double getRantMedia() {
		return rantMedia;
	}

	public void setRantMedia(double rantMedia) {
		this.rantMedia = rantMedia;
	}

	public double getCbtMedia() {
		return cbtMedia;
	}

	public void setCbtMedia(double cbtMedia) {
		this.cbtMedia = cbtMedia;
	}

	public double getCmtMedia() {
		return cmtMedia;
	}

	public void setCmtMedia(double cmtMedia) {
		this.cmtMedia = cmtMedia;
	}

	public List<Laudo> getListaLaudos() {
		return listaLaudos;
	}

	public void setListaLaudos(List<Laudo> listaLaudos) {
		this.listaLaudos = listaLaudos;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public double getGordMedia() {
		return gordMedia;
	}

	public void setGordMedia(double gordMedia) {
		this.gordMedia = gordMedia;
	}

	public double getProtMedia() {
		return protMedia;
	}

	public void setProtMedia(double protMedia) {
		this.protMedia = protMedia;
	}

	public double getLactMedia() {
		return lactMedia;
	}

	public void setLactMedia(double lactMedia) {
		this.lactMedia = lactMedia;
	}

	public double getEsdMedia() {
		return esdMedia;
	}

	public void setEsdMedia(double esdMedia) {
		this.esdMedia = esdMedia;
	}

	public double getPcMedia() {
		return pcMedia;
	}

	public void setPcMedia(double pcMedia) {
		this.pcMedia = pcMedia;
	}

	@Override
	public String toString() {
		return "LaudoMedia [idMedia=" + idMedia + ", fatMedia=" + fatMedia + ", truproMedia=" + truproMedia
				+ ", totproMedia=" + totproMedia + ", caseinMedia=" + caseinMedia + ", solidsMedia=" + solidsMedia
				+ ", snfMedia=" + snfMedia + ", fpdMedia=" + fpdMedia + ", ureaMedia=" + ureaMedia + ", ccsMedia="
				+ ccsMedia + ", celMedia=" + celMedia + ", phMedia=" + phMedia + ", denMedia=" + denMedia
				+ ", rantMedia=" + rantMedia + ", cbtMedia=" + cbtMedia + ", cmtMedia=" + cmtMedia + ", gordMedia="
				+ gordMedia + ", protMedia=" + protMedia + ", lactMedia=" + lactMedia + ", esdMedia=" + esdMedia
				+ ", pcMedia=" + pcMedia + ", listaLaudos=" + listaLaudos + ", solicitacao=" + solicitacao + "]";
	}

	
	
	

}