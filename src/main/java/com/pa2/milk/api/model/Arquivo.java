package com.pa2.milk.api.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "arquivo")
public class Arquivo {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String fileName;
	private String fileType;
	@Lob
	private byte[] data;
	private String fileDownloadUri;
	private long size;

	@OneToOne
	private Cliente fotoPerfil;

	@OneToOne
	private Solicitacao fotoSolicitacao;

	@OneToOne
	private Solicitacao comprovanteSolicitacao;

	public Arquivo() {
		super();
	}

	public Arquivo(String fileName, String fileType, byte[] data, String fileDownloadUri, long size, Cliente fotoPerfil,
			Solicitacao fotoSolicitacao, Solicitacao comprovanteSolicitacao) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
		this.fileDownloadUri = fileDownloadUri;
		this.size = size;
		this.fotoPerfil = fotoPerfil;
		this.fotoSolicitacao = fotoSolicitacao;
		this.comprovanteSolicitacao = comprovanteSolicitacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Cliente getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(Cliente fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public Solicitacao getFotoSolicitacao() {
		return fotoSolicitacao;
	}

	public void setFotoSolicitacao(Solicitacao fotoSolicitacao) {
		this.fotoSolicitacao = fotoSolicitacao;
	}

	public Solicitacao getComprovanteSolicitacao() {
		return comprovanteSolicitacao;
	}

	public void setComprovanteSolicitacao(Solicitacao comprovanteSolicitacao) {
		this.comprovanteSolicitacao = comprovanteSolicitacao;
	}

	@Override
	public String toString() {
		return "Arquivo [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", data="
				+ Arrays.toString(data) + ", fileDownloadUri=" + fileDownloadUri + ", size=" + size + ", fotoPerfil="
				+ fotoPerfil + ", fotoSolicitacao=" + fotoSolicitacao + ", comprovanteSolicitacao="
				+ comprovanteSolicitacao + "]";
	}

}
