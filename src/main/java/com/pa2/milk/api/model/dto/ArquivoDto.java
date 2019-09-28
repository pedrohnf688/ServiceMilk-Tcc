package com.pa2.milk.api.model.dto;

public class ArquivoDto {

	private String email;
	private String linkUrlFoto;

	public ArquivoDto() {
		super();
	}

	public ArquivoDto(String email, String linkUrlFoto) {
		super();
		this.email = email;
		this.linkUrlFoto = linkUrlFoto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLinkUrlFoto() {
		return linkUrlFoto;
	}

	public void setLinkUrlFoto(String linkUrlFoto) {
		this.linkUrlFoto = linkUrlFoto;
	}

	@Override
	public String toString() {
		return "ArquivoDto [email=" + email + ", linkUrlFoto=" + linkUrlFoto + "]";
	}

}
